#!/usr/bin/perl
print "Form Mailer 0.1\n";
print "Where's your data file?\n";
$datafile=<>;
print "What's the subject of your message?\n";
$subject=<>;
print "Who do you want the message to be from? (your e-mail)\n";
$from=<>;
print "Where's the message located?\n";
$messageloc= <>;
chomp($from,$messageloc,$datafile);
open MESSAGE,"<$messageloc";
$messagebody=join '', <MESSAGE>;
close MESSAGE;
open DATA, "<$datafile";
$header=<DATA>;
chomp $header;
@header=split(/\|/,$header);
while($line=<DATA>){
	chomp $line;
   @linesplit=split(/\|/,$line);
	for(my $i=0; $i<@header; $i++){
		$header{$header[$i]}=$linesplit[$i];
	}
	$message=$messagebody;
	foreach(keys %header){
		print "key $_ => $header{$_}\n";
		$message=~s/$_/$header{$_}/g;
	}
	open(MAIL, "|/usr/lib/sendmail -oem -t -oi");
	print MAIL "From: $from\n";
	print MAIL "To: $header{EMAIL}\n";
	print MAIL "Subject: $subject\n\n";
	print MAIL "$message\n";
	close MAIL;
}
close DATA;

