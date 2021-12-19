use strict;

####
#### comment_reader.pl
####
#### Reads a comment file from command-line arg or standard input
#### and outputs the result as a fragment of HTML suitable for
#### Perlmonks.
####

use IO::File;
use Getopt::Long;

use Template;

###################### Constants ######################

use constant DEFAULT_TEMPLATE => 'comment_tmpl.tt2';

###################### Main Program ###################

MAIN: {
  ## Get the options
  my $template_file = DEFAULT_TEMPLATE;
  my $outfile = undef;
  GetOptions('outfile=s' => \$outfile, 'template=s' => \$template_file);

  my $entries = read_entries();
  my $template = Template->new();
  $template->process($template_file, { files => $entries }, $outfile)
    or die $Template::ERROR;
}

###################### Subroutines #####################

##
## read_entries()
##
## Reads whatever entries are present on the command line,
## then returns them parsed and sorted as a reference
## to an array of hashes, like so:
##   [         # List of files
##     { 'name' => 
##
sub read_entries
{
  my %comments = ();
  while (<>) {
    chomp;
    
    # Bare-bones attempt at avoiding missing closing tags
    while (m{<(?!\/)([^>]+)>}g) {
      my $tag = $1;
      m{</$tag>}i or warn "Missing closing tag for $tag at $.\n";
    }

    my ($file_list, $comment) = split(/\s+/, $_, 2);
    foreach my $lineref (split(/;/, $file_list) ) {
      my ($file, $lines) = split(/:/, $lineref, 2);
      foreach my $line (split(/,/, $lines)) {
	push( @{ $comments{$file}{$line} }, $comment );
      }
    }
  }

  return sort_files(\%comments);
}

##
## Given a hash reference, turns it into a list of hashrefs.
##
sub sort_files {
  my ($comments) = @_;

  my @files = ();
  foreach my $file (sort keys %$comments) {
    my @lines = ();
    foreach my $line (sort {$a <=> $b} keys %{$comments->{$file}}) {
      push(@lines, { 'number' => $line, 'comments' => $comments->{$file}{$line} });
    }
    push (@files, { name => $file, lines => \@lines });
  }

  return \@files;
}

