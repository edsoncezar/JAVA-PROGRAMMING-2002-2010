#! Perl -w
##                                       ##
#shortcuts.pl -shortcut maintaining engine#
##                                       ##
use strict;
use Text::xSV;

my $shortcut_file = 'scuts'; #Define file that contains shortcuts
my $output_file = 'sfile'; #Define file for output

########END OF USER INPUT########

my($sf,$of)=($shortcut_file,$output_file);

sub set_shortcut {
	my $is_taken=0;
	print "Which shortcut character would you like (1 letter!)? (will appear in brackets) ";
	chomp(my $char=<STDIN>);
	print "What text do you want it to print?\n";
	chomp(my $text=<STDIN>);
	my $csv = new Text::xSV;
	$csv->open_file("$sf");
	$csv->bind_header();
	while ($csv->get_row()) {
		my ($shortcut) = $csv->extract(qw(shortcut));
		if ($char eq $shortcut) {
			warn "Shortcut character already used!";
			$is_taken=1;
			last;
		} 
	}
	unless ($is_taken) {
		open(SF, ">>$sf");
		print SF "$char,$text\n";
		close(SF);
	}
}
sub get_shortcuts {
	print"\n";
	my $csv = new Text::xSV;
	$csv->open_file("$sf");
	$csv->bind_header();
	while ($csv->get_row()) {
		my ($shortcut, $output) = $csv->extract(qw(shortcut output));
	  	print "\{$shortcut\} produces $output\n";
	}
}
sub new_doc {
	print "Type your note/doc (type {e} on a new line to end):\n";
	open(OF, ">>$of");
	while (my $line = <STDIN>) {
		last if $line =~ /\{e\}/;
		my %shortcuts;
		my $csv = new Text::xSV;
		$csv->open_file("$sf");
		$csv->bind_header();
		while ($csv->get_row()) {
			my ($shortcut, $output) = $csv->extract(qw(shortcut output));
			$shortcuts{$shortcut} = $output;
		}
		$line =~ s/\{(\w)\}+/$shortcuts{$1}/g;
		print OF $line;
	}
	close(OF);
}
sub get_doc {
	open(OF, "<$of");
	while (<OF>) {
		print;
	}
	close(OF);
}
		
	
unless (-s $sf) {
	open(SF, ">>$sf");
	print SF "shortcut,output\n";
	close(SF);
}

print "Welcome to S.S.E. \{Steven's Shortcut Engine\}\n";
print "\t{s} to set a shortcut\n\t{o} to view the output file's contents\n\t{n} to start typing to the output file\n\t{g} to view the shortcuts you've already made\n";
while(1) {
	print "\%> ";
	chomp(my $choice=<STDIN>);

	if ($choice =~ /\{s\}/) {
		set_shortcut();
	} elsif ($choice =~ /\{n\}/) {
		new_doc();
	} elsif ($choice =~ /\{g\}/) {
		get_shortcuts();
	} elsif ($choice =~ /\{o\}/) {
		get_doc();
	}
}

