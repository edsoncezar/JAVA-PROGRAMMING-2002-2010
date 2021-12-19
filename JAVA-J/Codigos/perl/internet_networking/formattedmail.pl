#!/usr/bin/perl -Tw

use strict;
use CGI;
use CGI::Carp qw(fatalsToBrowser);
use FileHandle;

$ENV{PATH} = '/usr/sbin/';

my $q = new CGI;
my $mail_prog = '/usr/sbin/sendmail';

# Get the format to be used

my $mail_format;

if($q->param('mail_format')){
	$mail_format = $q->param('mail_format');
	$q->delete('mail_format');
}

# If none specified, use default

else{
	$mail_format = 'default';
}

# Grab headers for mail and delete em so they won't
# show up in the body when using default

my $recipient = $q->param('recipient');
$q->delete('recipient');

my $subject = $q->param('subject');
$q->delete('subject');

my $from = $q->param('from');
$q->delete('from');

my $redirect = $q->param('redirect');
$q->delete('redirect');

open(MAIL,"|$mail_prog -t") || die ("Cannot open sendmail: $!\n");

my @all_params;

if($mail_format eq 'default'){
	foreach($q->param()){
		$q->param_fetch("$_")->[0] =~ s/</&lt;/g;
        	push(@all_params,$_);
	}	

	# Invoke the default format for MAIL

	formatDefault(\@all_params,*MAIL{IO});
}
else{
	foreach($q->param()){
		$q->param_fetch("$_")->[0] =~ s/</&lt;/g;
		push(@all_params,$q->param_fetch("$_")->[0]);
	}

	# Invoke the user-specified format
	
	&selectFormat($mail_format,*MAIL{IO},\@all_params);
}

print MAIL "To: $recipient\n";
print MAIL "From: $from\n";
print MAIL "Subject: $subject\n\n";

write(MAIL);

close(MAIL) || die ("Cannot close MAIL: $!\n");

print $q->redirect("http://$redirect");

# The default format

sub formatDefault{
	
	my $params=shift;
	my $fh=shift;
	my $time=localtime();

	format_name $fh "default";
	format_top_name $fh "default_top";

format default_top=
  
------------------------------------------------------------
 Default Format 	     @>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                             $time
------------------------------------------------------------
.

format default=  
@*
&formatDefaultParams($params);
.
}

sub formatDefaultParams{
       my $incoming = shift;
       my $outgoing;
     
       foreach(@$incoming){
         $outgoing .= "$_ : ". $q->param("$_") . "\n";
       }
       
	return($outgoing);
}

# The other formats

sub selectFormat{

	my $format = shift;
	my $fh = shift;
	my $params = shift;
	my $time = localtime();
	
	format_name $fh "$format";
	format_top_name $fh "${format}_top";

format example_top=

+----------------------------------------------------------+
| An example format        @>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  |
			   $time
+----------------------------------------------------------+
.

format example=
| Something user said:   |  Yada yada blah blah            |
|  @<<<<<<<<<<<<         |    	^<<<<<<<<<<<               |
  $params->[0],		        $params->[1]	
+------------------------+    	^<<<<<<<<<<<               |
			        $params->[1]
|  ..and so on           |                                 |
+------------------------+---------------------------------+
.

# Insert own formats here..

}


