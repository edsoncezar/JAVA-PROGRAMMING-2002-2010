use strict;
use LWP::UserAgent;
use HTTP::Request::Common;
use HTML::Form;

my $agent = new LWP::UserAgent;
my $url = 'http://www.transl8it.com/index.cgi?convert';
if($ARGV[0] eq '-to') {
	#Translates from english to text lingo
	print "English to lingo - \n";
	print "Type Message: ";
	my $input = <STDIN>;
	chomp($input);

	#Actual FORM post data
	my %data = (	'txtMessage' => $input,
			'direction' => '<--'
		);
	my $request = $agent->request(POST $url, [%data]);
	my $content = $request->content();
	my @forms = HTML::Form->parse($content, $url);
	foreach my $form (@forms) {
		eval{
			if(defined($form->value('txtTranslation'))) {
				print "\nTranslation: \n" . $form->value('txtTranslation');
				last;
			}
		};
		if(@_) {next;}
	}
} elsif($ARGV[0] eq '-from') {
	#Translates from text lingo to english
	print "lingo to English - \n";
	print "Type Message: ";
	my $input = <STDIN>;
	chomp($input);

	#Actual FORM post data
	my %data = (	'txtMessage' => $input,
			'direction' => '-->'
		);
	my $request = $agent->request(POST $url, [%data]);
	my $content = $request->content();
	my @forms = HTML::Form->parse($content, $url);
	foreach my $form (@forms) {
		eval{
			if(defined($form->value('txtTranslation'))) {
				print "\nTranslation: \n" . $form->value('txtTranslation');
				last;
			}
		};
		if(@_) {next;}
	}
} else{
	print 'perl trans.pl (-to|-from)';
}
