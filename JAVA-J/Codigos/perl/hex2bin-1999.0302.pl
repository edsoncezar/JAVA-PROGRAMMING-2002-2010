#!/usr/local/bin/perl
#
# @(#) $Id: hex2bin.pl,v 1.3 1999/03/02 12:28:08 jaalto Exp $
# @(#) $Contactid: <jari.aalto@poboxes.com> $
#
# {{{ Initial setup

BEGIN { require 5.004 }

#       A U T O L O A D
#
#       The => operator quotes only words, and File::Basename is not
#       Perl "word"

use autouse 'Pod::Text'     => qw( pod2text                 );

use strict;
use integer;

#   Standard perl modules

use Env;
use English;
use File::Basename;
use Getopt::Long;

    use vars qw ( $VERSION );

    #   This is for use of Makefile.PL and ExtUtils::MakeMaker
    #   So that it puts the tardist number in format YYYY.MMDD
    #   The REAL version number is defined later

    #   The following variable is updated by my Emacs setup whenever
    #   this file is saved

    $VERSION = '1999.0302';


# ****************************************************************************
#
#   DESCRIPTION
#
#       Set global variables for the program
#
#   INPUT PARAMETERS
#
#       none
#
#   RETURN VALUES
#
#       none
#
# ****************************************************************************

sub Initialize ()
{
    use vars qw
    (
        $PROGNAME
        $LIB

        $RCS_ID
        $VERSION
        $CONTACT
        $URL
    );

    $LIB	= basename $PROGRAM_NAME;
    $PROGNAME   = $LIB;

    $RCS_ID   = '$Id: hex2bin.pl,v 1.3 1999/03/02 12:28:08 jaalto Exp $';
    $VERSION  = (split (' ', $RCS_ID))[2];   # version number in format N.NN+
    $CONTACT  = "<jari.aalto\@poboxes.com>"; # Who is the maintainer
    $URL      = "ftp://cs.uta.fi/pub/ssjaaa/";

    $OUTPUT_AUTOFLUSH = 1;
}


# ***************************************************************** &help ****
#
#   DESCRIPTION
#
#       Print help and exit.
#
#   INPUT PARAMETERS
#
#       $msg    [optional] Reason why function was called.
#
#   RETURN VALUES
#
#       none
#
# ****************************************************************************

=pod

=head1 NAME

@(#) hex2bin.pl - Convert hex string file into binary file

=head1 SYNOPSIS

    hex2bin.pl --extension .bin file.hex
    hex2bin.pl --extension-eval 's,\.bin,\.hex,' file.hex

=head1 OPTIONS

=head2 General options

=over 4

=item B<--beg-column COL>

Ignore N characters from the beginning. Start reading at column COL.
Together with B<--end-column> you can slice the portion from the text file
eg. if input contains constant indentation and garbage after column N.

=item B<--end-column COL>

Read until column COL and discard everything after that.

=item B<--extension EXTENSION>

Append EXTENSION to each outputted file. Use along with B<--File> to change
the default extension

=item B<--File>

Dump the binary to file with extension .bin

=item B<--file FILE>

Dump the binary to FILE.

=item B<--input-decimal -D>

Input data is represented in decimal format

=item B<--input-hex -H>

Input data is represented in hex format. This the default option.

=item B<--input-octal -O>

Input data is represented in octal format

=item B<--raw>

Rip only the hex data from file and don't generate binary.

=back

=head2 Miscellaneous options

=over 4

=item B<--debug -d LEVEL>

Turn on debug with positive LEVEL number. Zero means no debug.

=item B<--help> B<-h>

Print help page.

=item B<--Version -V>

Print program's version information.

=back

=head1 README

This program makes a binary file out of HEX input data. The default
input format is:

    XXXX: 00 00 01 01 01 # This is comment
    XXXX: 00 00 00 00 00   Deletes eveythinbg after 3 spaces

Ie. There must be line numbers at the beginning of hex lines.
The line numbers are stripped and the hex numbers are read to
form the file. Input can also be in this format

    8B/00/45/19/10/00/01/00/FF/35/49/44/58/16/17/03/
    00/07/39/17/00/0D/1E/09/60/13/FF/FF/FF/FF/6D/00/

This program is quite forgiving, any extra lines that don't look
like hexdump will be skipped, so you can have the data in following
format too and program will only pick the hexdump out of it.

    00000200  A3 03 AD B6 10 00 01 00 FF 35 49 44 58 01 04 05   .....
    00000210  00 07 19 21 05 09 13 0B 61 13 FF FF FF FF 85 03   ...!.

=head1 EXAMPLES

To output to a file that is derived from the filename

    hex2bin.pl --extension ".bin" fileh.hex # output to file.hex.bin

This changes all .hex names into .bin when composing the output filename.

    hex2bin.pl --extension-eval 's,\.hex,\.bin,' file.hex

To give explicit filename where output is put:

    hex2bin --file out.bin fie.hex	    # output to out.bin

=head1 ENVIRONMENT

No environment settings.

=head1 SEE ALSO

od(1)

=head1 AVAILABILITY

CPAN entry is http://www.perl.com/CPAN-local//scripts/
Reach author at jari.aalto@poboxes.com or
http://www.netforward.com/poboxes/?jari.aalto

=head1 SCRIPT CATEGORIES

CPAN/Administrative

=head1 PREREQUISITES

No CPAN modules required.

=head1 COREQUISITES

No optional CPAN modules needed.

=head1 OSNAMES

C<any>

=head1 VERSION

$Id: hex2bin.pl,v 1.3 1999/03/02 12:28:08 jaalto Exp $

=head1 AUTHOR

Copyright (C) 1996-1999 Jari Aalto. All rights reserved. This program is
free software; you can redistribute it and/or modify it under the same
terms as Perl itself or in terms of Gnu General Public licence v2 or later.

=cut

sub Help (;$)
{
    my $id  = "$LIB.Help";
    my $msg = shift;  # optional arg, why are we here...

    pod2text $PROGRAM_NAME;

    exit 1;
}

# ************************************************************** &args *******
#
#   DESCRIPTION
#
#       Read and interpret command line arguments
#
#   INPUT PARAMETERS
#
#       none
#
#   RETURN VALUES
#
#       none
#
# ****************************************************************************

sub HandleCommandLineArgs ()
{
    my $id = "$LIB.HandleCommandLineArgs";

    # .......................................... command line options ...

    use vars qw
    (
	$FILE

	$debug
	$HELP_OPTION
	$VERSION_OPTION
	$verb

	$OCTAL
	$DECIMAL
	$HEX

	$USE_EXTENSION
	$EXTENSION
	$EXTENSION_EVAL

	$RAW
	$DUMP
	$BEG_COL
	$END_COL
    );

    $USE_EXTENSION = 0;

    # .................................................... read args ...

    Getopt::Long::config( qw
    (
	ignore_case
        require_order
    ));

    GetOptions      # Getopt::Long
    (
	  "Version"	    => \$VERSION_OPTION
	, "verbose"	    => \$verb
	, "debug:i"	    => \$debug
	, "d"		    => \$debug
	, "help"	    => \$HELP_OPTION

	, "file=s"	    => \$FILE
	, "extension=s"	    => \$EXTENSION
	, "extension-eval=s" => \$EXTENSION_EVAL

	, "input-decimal"   => \$DECIMAL
	, "input-octal"	    => \$DECIMAL
	, "input-hex"	    => \$HEX

	, "raw"		    => \$RAW
	, "beg-column"	    => \$BEG_COL
	, "end-column"	    => \$END_COL

    );

    $VERSION_OPTION	and die "$VERSION $PROGNAME $CONTACT $URL\n";
    $HELP_OPTION	and Help();

    $OCTAL		and $DECIMAL=0,   $HEX=0;
    $DECIMAL		and $OCTAL=0,	    $HEX=0;
    $HEX		and $OCTAL=0,	    $DECIMAL=0;

    $USE_EXTENSION	= 1 if defined $EXTENSION;
    $USE_EXTENSION	= 1 if defined $EXTENSION_EVAL;


    if ( !(defined $FILE || $USE_EXTENSION || defined $RAW) )
    {
	die "$id: --file --File or --extension option missing.";
    }

    if ( defined $EXTENSION )
    {
	local $ARG = $EXTENSION;
	m,/,	and warn "$id: odd Extension request: $EXTENSION\n";
	m,^--,	and warn "$id: odd Extension request: $EXTENSION\n";
    }


    if ( defined $BEG_COL  and  $BEG_COL + 0 < 1)
    {
	  die "$id: Must give positive number for --beg-column";
    }

    if ( defined $END_COL  and  $END_COL + 0 < 1)
    {
	  die "$id: Must give positive number for --end-column";
    }


}


# }}}
# {{{ Main

# ............................................................ &main ...

    Initialize();
    HandleCommandLineArgs();


    my $id   = "$LIB.main";
    my	 ( $out , @f, $i , $count, $val);
    local( *OUT, *IN );

    my   $ARGC  = @ARGV;
    push @ARGV, "-" unless @ARGV;				# pipe

for my $in ( @ARGV )
{
    #	If the file is stdin, then write to "tmp.bin", otherwise
    #	derive name from input file.

    $out = "$in";
    $out = "outfile"	if $in eq "-";

    #	However, explicit name overrides all.
    #	BUT the ARGV array must only contain one filename,
    #	otherwise we must derive the name from each file.


    if ( defined $FILE  and   $ARGC == 1)
    {
	# Do nothing, file name already given
    }
    elsif ( defined $EXTENSION_EVAL )
    {
	local $ARG = $out;

	eval  $EXTENSION_EVAL;

	$EVAL_ERROR	and die   "$id: [$EXTENSION_EVAL]  $EVAL_ERROR";
	$debug		and print "$id: [$EXTENSION_EVAL]  $out --> $ARG\n";

	$FILE = $ARG;
    }
    elsif ( defined $EXTENSION )
    {
	$FILE = $out . $EXTENSION;
    }


    # ................................................ write to file ...

    if ( defined $FILE )
    {
	$FILE eq $in	    and die "$id: IN $in and OUT $out are the same." ;
	open OUT, ">$FILE"  or  die "$id: Can't open [ $FILE ] $ERRNO";
    }

    unless ( open IN, "$in"  )
    {
	warn "Can't open [$in]"; next;
    }


    # .................................................... read-file ...

    while ( <IN> )
    {

	$debug  and print  "READING:>$ARG";

	# .................................................. fixing line ...

	if ( defined $BEG_COL  and   $BEG_COL > 0  and  $END_COL > 0 )
	{
	    $ARG = substr $ARG, $BEG_COL , $END_COL;

	}elsif ( defined $BEG_COL   and  $BEG_COL > 0  )
	{
	    $ARG = substr $ARG, $BEG_COL, length $ARG ;

	}elsif (  defined $END_COL  and $END_COL > 0  )
	{
	    $ARG = substr $ARG, 0, $END_COL;
	}


	# ............................................... detecting line ...

	    #	Must be left flushed number, otherwise skip

	next if ! m"^[0-9a-f][0-9a-f][0-9a-f]+[: ]|^[0-9a-f][0-9a-f]/"i;

	    #	NNN: 0a 0b	remove these from the beginning.
	    #	NNN  0a 0b

	s/^[0-9]+:\s*//;

	    #   00000300  00 00 00 00
	    #   The memory address must have at elast 3 numbers, otherwise
	    #   its "a0" (plain data)

	s/^[0-9a-f][0-9a-f][0-9a-f]+\s*//i;

	    #	FF/FF/FF/FF ==> FF FF FF FF

	s"/" "g;


	    #   Remove everything after 3 spaces

	s/   .*//;

	    #	Remove anything after that is not HEX
	    #
	    #	NNN: FF FF FF FF   ................
	    #

	s/\s*[^0-9A-Fa-f ].*//;

	next unless /^[0-9a-f][0-9a-f]/i;

	# ................................................ splitting line ...

	@f = split ' ';

	$debug  and print  "\nLOOP:> @f\n\n";

	for $i ( @f )
	{

	    #  Either "0a" or "124" octal or "255" decimal,
	    #  not other values accepted

	    if ( $i =~ /^[0-9A-F][0-9A-F]$|^[0-9][0-9][0-9]?$/i )
	    {
		$count++;

		print "$i " if $RAW;

		if ( defined $FILE )
		{
		    $val = hex $i ;
		    $val = oct $i   if $OCTAL;
		    $val = $i        if $DECIMAL;

		    $out = pack 'C', $val;

		    print OUT $out;
		}
	    }
	}
	print "\n" if $RAW;
    }
    close IN;

    if ( defined $FILE )
    {
	close OUT;
	print "Wrote $count bytes to $FILE\n";
    }
}


# }}}

0;
__END__
