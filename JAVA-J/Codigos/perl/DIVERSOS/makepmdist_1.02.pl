#!/usr/bin/perl
require 5.004;
#   Use perldoc filename to read this program's embedded documentation.

package Makepmdist;
 # Time-stamp: "2002-04-11 08:05:53 MDT"
use strict;
use vars qw($VERSION);
$VERSION = '1.02';
run() unless caller;

use File::Basename;
use File::Copy;

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#
#  NO USER-SERVICEABLE PARTS INSIDE.  (Pod is at the end.)
#
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

my($Package, $Me, $In_filename, $About, $Dist_dir,
   $File_last_bit, @Manifest,
);

sub run {
  $Me = basename($0);
  die "Usage:  $Me Foo.pm\n" unless @ARGV == 1 and length($ARGV[0]);
  my $it = $ARGV[0];
  if($ARGV[0] =~ m/^-(?:[vV]|-version|h|-help|\?)$/s ) {
    print "$Me (", __PACKAGE__, ") version $VERSION, sburke\@cpan.org\n\n",
      "Usage:  $Me Foo.pm\n",
      "          creates a CPAN-ready dist for the single module Foo.pm\n",
      "\nSee \"perldoc $Me\" for documentation.\n";
    exit;
  }
  
  die "No such file as $it\n"     unless -e $it;
  die "But $it isn't a file!\n"   unless -f $it;
  die "But $it is zero-length!\n" unless -s $it;
  die "But $it isn't readable!\n" unless -r $it;
  die "But filename \"$it\" doesn't end in \".pm\"!\n"
   unless $it =~ m/\.pm$/s;
  $In_filename = $it;
  $About = parse_file($it);
  
  # Sanity check the naming:
  $Package = $About->{'module_name'};
  unless($Package =~ m/([a-zA-Z_][a-zA-Z_0-9]+)$/s) {
    die "Package-name \"$Package\" makes no sense!";
  } else {
    my $package_last_bit = $1;
    $File_last_bit = basename($it);
    unless("$package_last_bit.pm" eq $File_last_bit) {
      die sprintf(
        "Filename \"%s\" seems incompatible with package name \"%s\".\n"
        . " I'd expect a file name of \"%s\" instead.\n",
        $File_last_bit,
        $Package,
        $package_last_bit . '.pm',
      );
    }
  }
  
  make_dist_dir();
  make_dist();
  clean_dist_dir();
  
  return;
}

#--------------------------------------------------------------------------
###########################################################################
#--------------------------------------------------------------------------

sub make_dist_dir {

  {
    my $thing = $File_last_bit;
    $thing =~ s/\.pm$//s;
    $Dist_dir = "dist_" . $thing . "_$$";
  }
  mkdir($Dist_dir, 0777)     || die "Can't mkdir \"$Dist_dir\": $!";

  my $out_file = "$Dist_dir/$File_last_bit";
  if(! $About->{'nl'}) {
    copy($In_filename, $out_file);
  } else {
    print "Correcting newline format to Unix format, during copy!\n";
    open(FROM_PM, "<$In_filename")
     or die "Can't read-open $In_filename: $!";
    binmode(FROM_PM);
    local $/ = $About->{'nl'};
    open(TO_PM, ">$out_file")
     or die "Can't write-open $out_file: $!";
    binmode(TO_PM);

    while(<FROM_PM>) { chomp; print TO_PM $_, "\n"; }
    close(TO_PM);
    close(FROM_PM);

    sleep 1;
    die "But $out_file came out 0-length!" unless -s $out_file;
  }
  
  chmod 0666, $out_file;
  push @Manifest, $File_last_bit;

  chdir $Dist_dir or die "Can't chdir to $Dist_dir: $!";
  make_makefile();
  make_test();
  make_readme();
  make_manifest();
  
  return;
}

#--------------------------------------------------------------------------

sub make_test {
  mkdir("t", 0777) || die "Can't mkdir \"t\": $!";
  push @Manifest, \"t";

  my $test_file = 't/test.t';
  open(TEST, ">$test_file") or die "Can't write-open $test_file : $!";
  binmode(TEST);
  
  printf TEST q~
# This -*-perl-*- file is run as part of "make test".
# See "perldoc Test" (or the appropriate Test::* module) for details.
#
BEGIN { print "# Perl version $] under $^O\n" }
BEGIN { require %s } 
print 	'',
  "# %s version $%s::VERSION\n",
  "# Time now: " . scalar(gmtime), " GMT\n",
  "# I'm ", ((chr(65) eq 'A') ? '' : 'not '), "in an ASCII world.\n",
  "#------------------------\n",
;
~,
    ($Package) x 3,
  ;

  if($About->{'tests'}) {
    print TEST $About->{'tests'};
  } else {
    print TEST qq~
use Test;
BEGIN { plan tests => 1 }
require $Package;
ok(1);
print "# No real tests to run.\n";
~;
      print TEST "# See \"perldoc $Me\" for info",
       " on inserting\n#  \"=for makepmdist-tests\"",
       " blocks into your module.\n\n";
  }

  print TEST "\n\n";
  close(TEST);

  die "$test_file doesn't exist?!" unless -e $test_file;
  push @Manifest, $test_file;
  die "$test_file is 0-length?!"   unless -s $test_file;

  return;
}

#--------------------------------------------------------------------------

sub make_makefile {
  open(MAKEFILE, ">Makefile.PL") or die "Can't write-open Makefile.PL: $!";
  binmode(MAKEFILE);
  
  my $my_id = __PACKAGE__;
  $my_id .= " ($Me)" unless lc($Me) eq lc(__PACKAGE__);
  
  my $prereqs = $About->{'prereqs'}
   || "  # And $Package has no module pre-requisites";
  
  print MAKEFILE '',  
qq[## This -*- perl -*- script writes the Makefile for $Package
# You should read "perldoc perlmodinstall" for instructions on
#  how to install modules like this.

require 5.004;
use strict;
use ExtUtils::MakeMaker;

WriteMakefile(
  'NAME' => '$Package',
  'VERSION_FROM' => '$File_last_bit', # finds \$VERSION
  'dist'=> { COMPRESS => 'gzip -6f', SUFFIX => 'gz', },
$prereqs
);
# generated by $my_id v$VERSION
],

'_', '_END_', "_\n\n";

  close(MAKEFILE);

  #Sanity checking:
  die "Makefile.PL doesn't exist?!" unless -e 'Makefile.PL';
  push @Manifest, "Makefile.PL";
  die "Makefile.PL is 0-length?!"   unless -s 'Makefile.PL';
  
  return;
}

#--------------------------------------------------------------------------

sub make_readme {
  _try("pod2text $File_last_bit > README");
  
  die "README doesn't exist?!" unless -e 'README';
  push @Manifest, 'README';
  die "README is 0-length?!"   unless -s 'README';

  return;
}

#--------------------------------------------------------------------------

sub make_manifest {
  open(MANIFEST, ">MANIFEST") or die "Can't write-open MANIFEST: $!";
  binmode(MANIFEST);
  push @Manifest, "MANIFEST";
  for(@Manifest) { print MANIFEST $_, "\n" unless ref $_ }
  close(MANIFEST);
  return;
}

###########################################################################

sub make_dist {
  print "// Making dist...\n";
  _try("perl Makefile.PL && make test && make dist && make clean");
  print "\\\\ Done making dist.\n";
  
  opendir(HERE, ".") || die "Can't opendir '.': $!";
  my(@gz) = grep m/\.gz$/s, readdir(HERE);
  closedir(HERE);
  die "I can't find the new .gz dist!" unless @gz;
  die "There's more than one .gz dist?!" if @gz > 1;
  my $gz = $gz[0];
  die "$gz is unreadable!" unless -r $gz;
  die "$gz isn't a file!"  unless -f $gz;
  die "$gz is 0-length!"   unless -s $gz;
  chmod(0644, $gz) or warn "Can't chmod 0644 $gz : $!";
  if(-e "../$gz") {;
    if(-e "../$gz~") {
      unlink("../$gz~") or die "Can't unlink old $gz~: $!";
    }
    rename("../$gz", "../$gz~") or die "Can't rename old $gz to $gz~ : $!";
  }
  rename($gz, "../$gz")
   or die "Can't move $gz up to parent directory\n";
  print "\nDIST MADE:  $gz  --  ", -s "../$gz", " bytes.\n";
  return;
}

###########################################################################

sub clean_dist_dir {
  foreach my $x (reverse @Manifest) {
    if(ref $x) { rmdir($$x) or warn "Can't rmdir $$x: $!" }
    else {       unlink($x)  or warn "Can't unlink $x: $!" }
  }
  opendir(HERE, ".") || die "Can't opendir '.': $!";
  my(@left) = grep( ($_ ne '.' and $_ ne '..'), readdir(HERE));
  closedir(HERE);

  foreach my $l (@left) {
    unlink($l) or warn "  Can't unlink $l: $!";
  }
  chdir("..") or die "Can't chdir to parent: $!";
  rmdir($Dist_dir) or warn "Can't rmdir $Dist_dir : $!";
  
  return;
}

###########################################################################

sub _try {
  my $command = $_[0];
  print "\% $command\n";
  system($command) == 0 or die "system \"$command\" failed: $?";
  return;
}

###########################################################################

sub parse_file {
  my $in_file = $_[0];
  my $content;
  {
    local *FH;
    local $/; # for slurp mode
    open(FH,$in_file) or die "Couldn't read-open '$in_file': $!";
    binmode(FH);
    $content = <FH>;
    close(FH);
  }
  
  # Deduce newline format of this file
  my $nl_format;
  die "SANITY FAILURE: \\n isn't \\cj!" unless "\n" eq "\cj";
  die "SANITY FAILURE: ", __FILE__, " isn't in Unix newline format!"
    unless "\n" eq "
"; # Altho apparently modern perls correct for this, in 
   #  which case that test passes just fine.
  
  if($content =~ m/([\cm\cj]+)/) {
    my $nl = $1;
    if($nl =~ m/^\cj+$/s) {
      $nl_format = ''; # flag for "normal"
    } elsif($nl =~ m/^(\cm\cj)+$/s) {
      $nl_format = "\cm\cj";
      $content =~ s/\cm\cj/\n/g;
    } elsif(m/^\cm+$/s) {
      $nl_format = "\cm"; # mac_format
      $content =~ tr/\cm/\n/;
    } else {
      # What the hell is this?  Psychobilly freak out!
      $nl =~ s/\cm/CR /g;
      $nl =~ s/\cj/LF /g;
      $nl =~ s/ $//s;
      die "$in_file is in some unknown newline format -- $nl !?";
    }
  } else {
    die "Module $in_file has no newlines in it!?\n";
  }
  
  # Warn about overlong lines, which are (at least) bad style:
  {
    my $max_okay = 132;
    my($long_count) = 0;
    foreach my $l ($content =~ m/([^\n]+)/g) {
      ++$long_count if length($l) > $max_okay;
    }
    if($long_count) {
      print
       "** $in_file has $long_count line", ($long_count == 1) ? '' : 's',
       " over $max_okay characters long\n",
       "**  You might want to fix that.\n",
      ;
    }
  }
  
  my $module_name;
  if($content =~ m/^[ \t]*package[ \t]+([a-zA-Z_0-9:]+)[ \t]*\;/m) {
    #print "This module is $1\n";
    $module_name = $1;
  } else {
    die "Can't find \"package Foo;\" line in $in_file\n";
  }

  my $seen_version;
  unless($content =~ m/[\$\:\'\*]VERSION\s*=\s*/s) {
    die "$in_file doesn't seem to set \$VERSION anywhere.  Aborting.\n";
  }

  my(@prereqs, @tests);
  while($content =~
    m/\n[ \t]*\n=for[ \t]+(\S+)[ \t]*\n(.*?)(\n[ \t]*\n)/gs
  ) {
    #print "Seeing =for $1 ...\n";
    if($1 eq 'prereq' or $1 eq 'prereqs') {
      push @prereqs, $2;
    } elsif($1 eq 'makepmdist-test' or $1 eq 'makepmdist-tests') {
      push @tests, $2;
    }
    pos($content) = pos($content) - length $3;
      # so we aren't blind to consecutive '=for' sections.
  }

  my $prereqs = '';
  foreach (map split("\n",$_), @prereqs) {
    s/\#.*//s; # nix comments
    next unless m/\S/s;
    
    if( m/^\s*([a-zA-Z_0-9:]+)\s*$/ ) {   # simple name
      $prereqs .= "    '$1' => 0, # any version \n";
    } elsif ( m/^\s*([a-zA-Z_0-9:]+)\s+([\.0-9]+)\s*$/ ) { # name + vernum
      $prereqs .= "    '$1' => '$2',\n";
    }
  }
  $prereqs = "  'PREREQ_PM' => {\n$prereqs  },\n" if $prereqs;

  return {
    'module_name' => $module_name,
    'prereqs' => $prereqs,
    'tests'   => join("\n", @tests),
    'nl'      => $nl_format,
  };
}

#--------------------------------------------------------------------------
1;

__END__

=head1 NAME

makepmdist -- one-step CPAN distribution-maker for simple modules

=head1 SYNOPSIS

  % makepmdist Foo.pm
  [many lines of output, and then]
  DIST MADE:  Thing-Foo-4.56.tar.gz  --  31415 bytes.


=head1 DESCRIPTION

This program, F<makepmdist> is for simplifying the creation of CPAN
distributions of simple Perl modules. By a "simple module", I mean a
module that is the B<only module> in its dist, and which is pure-Perl
(i.e., no XS), and which has either just simple tests (yay), or no tests at
all (boo hiss).

When you give makepmdist a single module file (called, for example,
F<Foo.pm>), it creates a temporary subdirectory (named like
F<dist_Foo_123>), with six items in/under it:

  README
  Makefile.PL
  Foo.pm
  t/
  t/test.t
  MANIFEST

F<Foo.pm> is just whatever Perl module you mean to distribute.

F<Makefile.PL> conveys the information needed to
distribute/test/install this module.

The F<README> is just the textification of the POD -- i.e., the results
of having fed it thru F<pod2text>.  (Note that there is currently no
way to get only I<part> of the output of F<pod2text>'ing your module --
you just always get the whole thing saved there.)

The F<MANIFEST> is just a list of all the (five) files to me put into the
distribution.

Note that there is no F<Changes> or F<ChangeLog> file created, nor
(currently) any way to add one. I suggest just making a "C<=head1 CHANGE
LOG>" section in your Pod and describing your changes there.



=head1 DECLARING PREREQUISITES

For most modules, just about the only interesting thing that the
Makefile declares, is the list of all other modules that your module
relies on. So if your module needs XML::Parser of any version, an
HTML::Tagset version of 3.02 or later, and an HTML::Element of version
3.08 or later, this should be declared, so that people installing your
module will be aware of this -- and probably so that their installation
system (like C<CPAN.pm>) can auto-install the prerequisites.

The way you declare prerequisites is by having a blank line, a C<=for>
block like this, inside your Pod:

  =for prereqs
    XML::Parser
    HTML::Element 3.08
    HTML::Tagset 3.02

...and then a blank line. (Note that there must be no blank lines
I<within> the prereqs block, or else Pod readers will interpret this as
a new paragraph that will actually show up in the rendered Pod.)

In other words, you list each module prerequisite on a line of its own,
with an optional version number after it.  So this, on a line of its own,
inside a C<=for prereqs> block,

   File::HomeDir

declares to makepmdist (and thereby in the F<Makefile.PL>) that this
module needs any version of the module C<File::Homedir>. If you have a
line consisting of just a modulename, some space, and a version number,
like this:

   File::HomeDir  0.03

then this declares to makepmdist (and thereby in the F<Makefile.PL>)
that this needs a version of File::Homedir of number 0.03 I<or higher>.

Note that the C<=for prereqs> paragraph(s) are not visible to normal Pod
formatters -- they won't show up with perldoc, pod2man, pod2html, etc.



=head1 TESTING WITH C<=for makepmdist-tests> BLOCKS

By default, the F<t/test.t> test-file that makepmdist creates, just
makes sure that your module can be loaded.

But you should really add at least some simple tests for your module, to
make sure it not only parses, but actually works. Consider, for example,
the simple CPAN module C<Lingua::EN::Numbers::Ordinate>, which basically
just provides a function C<ordinate(I<number>)>, which spits returns the
given number plus an ordinal suffix. For example, C<ordinate(3)> returns
the strict "3rd". To make sure that this module actually works, I need
only insert this C<=for makepmdist-tests> block into the
F<Ordinate.pm>'s source:

  =for makepmdist-tests
  use strict;
  use Test;
  BEGIN { plan tests => 14 };
  use Lingua::EN::Numbers::Ordinate;
  ok 1;  # get some credit just for showing up
  ok ordinate(3) eq '3rd';
  ok ordinate(-3) eq '-3rd';
  ok ordinate(13) eq '13th';
  ok ordinate(33) eq '33rd';
  ok ordinate(-513) eq '-513th';
  ok ordinate(1) eq '1st';
  ok ordinate(2) eq '2nd';
  ok ordinate(4) eq '4th';
  ok ordinate(5) eq '5th';
  ok ordinate(0) eq '0th';
  ok ordinate('') eq '0th';
  ok ordinate(undef) eq '0th';
  ok ordinate(22) eq '22nd';

As with the C<=for prereqs> paragraph, there mustn't be any blank lines
in this paragraph; and the paragraph should have a blank line before and
a blank line after.

What C<=for makepmdist-tests> paragraphs are for, is specifying tests
that will appear in the F<t/test.t> file that makepmdist will create.
You can have multiple C<=for makepmdist-test> paragraphs.  They will
just appear in order in the F<t/test.t> file.


=head1 DIAGNOSTICS

makepmdist will try to enforce its idea of sanity on you in a few
incidental ways. For example, at time of writing, it tries to make sure
that the module being distributed is in Unix newline format. It will
also complain if it sees absurdly long lines in your file. But
makepmdist doesn't go out of its way to enforce general style points on
you. For example, it doesn't make sure your module has a "use strict;"
(altho I hope it does!), nor does it check the validity of your Pod
syntax -- you should really have done that already by using
C<podchecker>, for example.

makepmdist will also insist that your module have a $VERSION definition.

If makepmdist hits a fatal error, it will leave a temp directory in the
current directory. You might be able to examine the contents of that
directory to further diagnose what went wrong. Otherwise just delete it.


=head1 ADVICE

I advise you to include a section in your Pod where you describe any
special installation details, since that will appear in the C<README>,
which will then show up as a F<Thing-Foo-4.56.tar.readme> text file in
your CPAN directory.

Personally, I just use:

  =head1 INSTALLATION

  See perlmodinstall for information and options onf
  installing Perl modules.

You might also want to add this:

  =head1 AVAILABILITY

  The latest version of this module is available from the
  Comprehensive Perl Archive Network (CPAN).  Visit
  <http://www.perl.com/CPAN/> to find a CPAN site near you.
  Or see http://www.perl.com/CPAN/authors/id/X/XY/XYZIGMUND/
  or http://www.cpan.org/modules/by-module/Thing/

[or whatever the path is to your CPAN authors directory and the
by-module directory that your module will live in. That's assuming
you're putting it in CPAN -- which you are, aren't you?]

=head1 SWITCHES

C<makepmdist -v> will show the current version, along with a note
about usage, and then exit.


=head1 ENVIRONMENT

makepmdist doesn't consult any environment variables.


=head1 CAVEAT

This program is meant to run under Unix. But I would be interested to
hear how/whether it runs under any other reasonably Unix-like
environments.

The first "package I<whatever>;" line in the file had better be what
declares the package name of this module.  Otherwise you will get
angry complaints from makepmdist.


=head1 SEE ALSO

L<perlmodstyle>
(
C<http://infotrope.net/opensource/software/perl6/modules/perlmodstyle.pod>
) -- advice about writing modules

L<perlnewmod> - about preparing a new module for distribution

L<perlmodlib> -- about modules

L<perlmodinstall> -- info on installing modules

L<Test> -- library for simplifying writing tests for modules

L<podchecker> (L<Pod::Checker>) -- for sanity-checking your Pod

L<Pod::Spell> -- for spell-checking your Pod

L<perlpod> -- about the Pod format


=head1 CHANGE LOG

=over

=item v1.02 2002-04-11

First real release.  Only minor changes, tho.

=item v1.01 2002-02-16

First draft-release.

=back


=head1 INSTALLATION

This is a program, not a module.  You can always run it with just

  perl /path/to/thing/makepmdist.pl Foo.pm

but you probably want to rename it to F<makepmdist>, make it executable,
and put it in a directory in your path.


=head1 LICENSE AND DISCLAIMERS

Copyright (c) 2002 Sean M. Burke.  All rights reserved.

This program is free software; you can redistribute it and/or modify it
under the same terms as Perl itself.

The programs and documentation in this dist are distributed in the hope
that they will be useful, but without any warranty; without even the
implied warranty of merchantability or fitness for a particular purpose.


=head1 AUTHOR

Sean M. Burke C<sburke@cpan.org>

=head1 README

Simplifies the creation of CPAN distributions for pure-Perl modules
which are the only module in their dist.

=head1 SCRIPT CATEGORIES

CPAN

=cut


