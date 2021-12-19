#!/usr/local/bin/perl -w

use strict;

my %open_comment = (
        '.c'    => '/*',
        '.h'    => '/*',
        '.pas'  => '{',
                        );

my %close_comment = (
        '.c'    => '*/',
        '.h'    => '*/',
        '.pas'  => '}',
                        );

my %line_comment = (
        '.com'  => '!',
        '.opt'  => '!',
        '.mms'  => '#',
        '.mar'  => ';',
        '.pl'   => '#',
        '.pm'   => '#',
        '.sh'   => '#',
                        );

my $lincnt = 0;
my $meatcnt = 0;
my $tokencnt = 0;
my $filcnt = 0;
my $g_lincnt = 0;
my $g_meatcnt = 0;
my $g_tokencnt = 0;

foreach my $filnam (@ARGV) {
    next if !open INFILE,$filnam;

    my $incomment = 0;
    my $inpod = 0;

# Parse out filename extension. Needs to be portable
# Note: VMS can have ;version on the end of the file name

    my $ext = $1 if $filnam =~ /(\.[^.;]+)(;|$)/;
    next if !$ext;

# Get comment delimiters for extension

    my $lc = $line_comment{$ext};
    my $oc = $open_comment{$ext};
    my $cc = $close_comment{$ext};

    while (defined ($_ = <INFILE>)) {

# Line comment

        if ($lc) {
            s/\Q$lc\E.*//; }

# Begin and end comment e.g. /* ... */

        if ($oc) {
            $incomment = 0 if $incomment && s/.*?\Q$cc//;
            s/.*// if $incomment;
            s/\Q$oc\E.*?\Q$cc//g;
            $incomment++ if s/\Q$oc\E.*//; }

# POD section

        $inpod++ if /^=/;
        $inpod = 0 if /^=cut/;
        s/.*// if $inpod;
        s/^=cut//;

# Warning, does not handle token count for multi line 
# strings, \', regexs or <<FOO
# but token count is only a rough guide

        s/(["'`]).*?\1/ string /g;
        s/^\s+//;
        my @tokens = /\w+\s*|.\s*/g;

        $lincnt++; $g_lincnt++;
        $meatcnt++,$g_meatcnt++ if $_;
        $g_tokencnt += @tokens;
        $tokencnt += @tokens; }

    close INFILE;

    printf("%s %d lines, %d non-commentary, %d tokens = %2.2f per line\n",
    $filnam,$lincnt,$meatcnt,$tokencnt,
    $meatcnt ? ($tokencnt/$meatcnt) : 0);

    $lincnt = 0;
    $meatcnt = 0;
    $tokencnt = 0;
    $filcnt++;
}

printf("\nGrand total %d files, %d lines, %d non-commentary, %d tokens = %2.2f per line\n",
    $filcnt,$g_lincnt,$g_meatcnt,$g_tokencnt,
    $g_meatcnt ? ($g_tokencnt/$g_meatcnt) : 0);

