  #!/usr/local/bin/perl -w
    my $VERSION = '1.00';
    my $Usage = "Usage: peg [<options>|-help] <perlexpr> [<files>]\n";
    my ($Dirs_specified, $Eval, $Implicit_C, $No_slurp);
    my (@Files, %Options, @Perlexpr, @Warnings) = ();
    my ($After, $Before) = (2, 2);
    my $Perlexpr = '';
    $SIG{'__WARN__'} = sub { push @Warnings, @_; };
    process_ARGV();
    check_Perlexpr();
    $g ||= $s;
    find_files() if ($d || $r);
    build_Eval();
    show_debug() if $D;
    reset 'a-z';
    eval $Eval;
    die "\npeg: run time eval error:\n", @Warnings, $@,
    "\n...when eval'ing:\n$Eval\n...with input:\n$_\n";
    sub process_ARGV
    {
    my $options = 1;
    my $context = 'C';
    if ($_ = $ENV{'PEG_OPTIONS'}) {
    unshift @ARGV, (/^-/ ? $_ : "-$_");
    }
    while (defined ($_ = shift @ARGV)) {
    if ($f) {
    open(F, "<$_") || die "peg: can't open $_: $!\n";
    while (<F>) {
    chomp;
    push @Perlexpr, $_ unless /^$/;
    }
    close F;
    $f = 0;
    }
    elsif ($options && s/^-(?=.)//) {
    /^help$/ && help();
    while (s/^(.)//) {
    my $opt = $1;
    if ($opt =~ /^[abcdfghilnoqrstvwxyABCDEFGHLNOPQSTXYZ]$/) {
    ${$opt} = $Options{$opt} = 1;
    $context = $opt if ($opt =~ /^[ABC]$/);
    }
    elsif ($opt =~ /^\d$/) {
    while (s/^(\d)//) { $opt = (10 * $opt) + $1; }
    $After = $opt if ($context ne 'B');
    $Before = $opt if ($context ne 'A');
    $Implicit_C = 1;
    }
    elsif ($opt eq '-') { $options = 0; }
    elsif ($opt eq 'V') { die "peg v$VERSION (Dec 1999)\n"; }
    else { die "peg: illegal option -- $opt\n$Usage"; }
    }
    }
    elsif (!@Perlexpr || (($o || $O) && $options)) {
    push @Perlexpr, $_;
    }
    else {
    push @Files, $_;
    }
    }
    die $Usage unless @Perlexpr;
    } # process_ARGV
    sub check_Perlexpr
    {
    my $regexp = $G || $Q || $i || $w || $x;
    foreach (@Perlexpr) {
    ($Q && !$E) || ($No_slurp ||= /[\^\$]/);
    next if ($E || !($regexp || /^\w+$/));
    $Q ? ($_ = quotemeta($_)) : (s/\//\\\//g);
    $_ = '\b' . $_ . '\b' if ($w && !$x);
    $_ = '^' . $_ . '$' if $x;
    $_ = '/' . $_ . '/';
    $_ .= 'i' if $i;
    }
    if ($O) {
    $Perlexpr .= join(",\n\t", map({"(\$Match$_ ||= (" . $Perlexpr[$_] . "))"} (0..$#Perlexpr)),
    ('(' . join(' && ', map {"\$Match$_"} (0 .. $#Perlexpr)) . ')'));
    }
    else {
    $Perlexpr = join("\n\t|| ", map {"($_)"} @Perlexpr);
    }
    $Perlexpr = 'not (' . $Perlexpr . ')' if $v;
    local ($a, $b, $c, $d, $f, $g, $h, $i, $l, $n, $o, $q, $r, $s, $t, $v, $w, $x, $y,
    $A, $B, $C, $D, $E, $F, $G, $H, $L, $N, $O, $P, $Q, $S, $T, $X, $Y, $Z);
    eval "\$_ = ''; if ($Perlexpr) {}";
    die "peg: error in Perl expression: $Perlexpr\n", @Warnings, $@ if $@;
    } # check_Perlexpr
    sub find_files
    {
    if ($d && @Files) {
    my ($start_dir, $dir, @dirs, @files);
    foreach (@Files) {
    (-d $_) ? push @dirs, $_ : push @files, $_;
    }
    if ($Dirs_specified = @dirs) {
    @Files = @files;
    require Cwd;
    $start_dir = Cwd::cwd() || die "peg: cannot determine current directory\n";
    foreach $dir (@dirs) {
    chdir($dir)
    || (($s || print STDERR "peg: can't chdir to $dir: $!\n"), next);
    find($dir);
    chdir($start_dir)
    || die "peg: can't chdir back to starting directory $start_dir: $!\n";
    }
    }
    }
    find('.') if $r;
    if (!@Files && ($r || ($d && $Dirs_specified)) && !$X) {
    print STDERR "peg: no files found\n" if !$s;
    exit(1);
    }
    } # find_files
    sub find
    {
    my $cwd = shift;
    my (@f, $f, $ff);
    opendir(DIR, '.')
    || (($g || print STDERR "peg: can't opendir $cwd: $!\n"), return);
    @f = readdir DIR;
    closedir DIR;
    foreach $f (@f) {
    next if ($f eq '.' || $f eq '..');
    $ff = "$cwd/$f";
    lstat $f;
    if (-d _) {
    chdir($f)
    || (($g || print STDERR "peg: can't chdir to $ff: $!\n"), next);
    find($ff);
    chdir('..')
    || die "peg: can't chdir back to .. from $ff: $!\n";
    }
    else {
    push @Files, $ff;
    }
    }
    } # find
    sub help
    {
    system("perldoc peg") && die "\npeg: perldoc: $?\n";
    exit;
    } # help
    sub show_debug
    {
    print "peg: Warnings =>\n", @Warnings, "\n" if @Warnings;
    print "peg: Options => ", sort(keys %Options),
    (($_ = $ENV{'PEG_OPTIONS'}) ? " (PEG_OPTIONS = $_)" : ''), "\n\n";
    print "peg: Files =>\n", (map {"\t$_\n"} @Files), "\n";
    print "peg: Perl code =>\n$Eval\n";
    exit;
    } # show_debug
    sub build_Eval
    {
    my ($context, $gap, $nonmatch_print, $output, $print, $reset, @my_vars);
    if ($O) {
    $l = 1;
    $A = $B = $C = $Implicit_C = $c = $L = $q = $Z = 0;
    }
    $No_slurp = @Files = ('-') if (!@Files && !$X);
    $No_slurp ||= $x;
    $C = 1 if ($Implicit_C && !($A || $B));
    $A = $B = 1 if $C;
    $context = $A || $B || $C;
    $c = $l = $L = $q = $S = $Z = 0 if $context;
    $h = 1 if (@Files <= 1 && !(($d && $Dirs_specified) || $r || $X));
    $reset = 1 if (((@Files > 1) || $X) && $Perlexpr =~ /[\$\@\%][a-z]/ && !$x);
    $h = 0 if $H;
    if ($c || $l || $L || $O || $q || $Z) {
    $a = 1;
    $b = $N = $S = $T = 0;
    }
    $y = 1 if (($l || $L || $q) && !$No_slurp);
    $N = 0 if $T;
    $F = 0 if ($F && $Perlexpr !~ /\bF\b/);
    $P = 0 if ($P && $Perlexpr !~ /\bP\b/);
    $a = 1 if $S;
    if ($c) {
    $L = $q = $Z = 0;
    }
    elsif ($L) {
    $q = $Z = 0;
    }
    elsif ($l) {
    $q = $Z = 0;
    $output = '"$File\n"';
    $t = 1;
    }
    elsif ($Z) {
    $q = 0;
    }
    elsif ($q) {}
    else {
    $output = '';
    $output = "\$Offset:" if $b;
    $output = "\$.:$output" if $n;
    $output = "\$File:$output" if !$h;
    $output = "\"$output\$_\"" if $output;
    }
    if (defined $output) {
    $print = 'print' . ($output ? " $output" : '') . ';';
    $print .= ' last;' if ($t && !$context);
    }
    if ($context) {
    $output ||= '$_';
    $gap = ($A ? $After : 0) + ($B ? $Before : 0);
    ($nonmatch_print = $print) =~ s/:/-/g;
    $output =~ s/:/-/g;
    $Perlexpr = "\$First_match && ($Perlexpr)" if $t;
    }
    @my_vars = (($context ? '$After' : ()),
    ($B ? '@Before' : ()),
    ($a ? () : '$Binary_file'),
    ($c ? '$Count' : ()),
    ($F ? '@F' : ()),
    ($context ? '$First_match' : ()),
    '$File',
    ($L ? '$Found' : ()),
    ($b ? '$Length' : ()),
    ($O ? (map {"\$Match$_"} (0..$#Perlexpr)): ()),
    ($context ? '$Matched' : ()),
    ($b ? '$Offset' : ()),
    ($P ? ('$P', '@P') : ()),
    ($Z ? '$Z' : ()));
    $Eval = '';
    $Eval .= "while (<STDIN>) { chomp; push \@Files, \$_; }\n" .
    "\@Files || (" . ($s ? '' : '(print STDERR "peg: no files found\n"), ')
    . "exit(1));\n" if $X;
    $Eval .= "\$| = 1;\n" if !$q;
    $Eval .= ($y ? "undef \$/;\n" : ($Y ? "\$/ = '';\n" : ''));
    $Eval .= "my \$Exit_code = 1;\n" if !$q;
    $Eval .= 'my (' . join(", ", @my_vars) . ");\n";
    $Eval .= "foreach \$File (\@Files) {\n";
    $Eval .= " open(FILE, \"<\$File\")";
    $Eval .= $s ? " || next;\n"
    : "\n|| ((print STDERR \"peg: can't open \$File: \$!\\n\"), next);\n";
    $Eval .= " \$After = $After;\n" if $A;
    $Eval .= " \@Before = ();\n" if $B;
    $Eval .= " \$Binary_file = -B FILE;\n" if !$a;
    $Eval .= " \$Count = 0;\n" if $c;
    $Eval .= " \$Found = 0;\n" if $L;
    $Eval .= " \$Offset = 0;\n" if $b;
    $Eval .= " \$First_match = 1;\n" if $context;
    $Eval .= ' ' . join(" = ", map {"\$Match$_"} (0..$#Perlexpr)) . " = 0;\n" if $O;
    $Eval .= " \@P = ();\n" if $P;
    $Eval .= " \$Z = '';\n" if $Z;
    $Eval .= " while (<FILE>) {\n";
    $Eval .= "\$P = \$_;\n" if $P;
    $Eval .= "\$Length = length;\n" if $b;
    $Eval .= "\@F = split;\n" if $F;
    $Eval .= "shift \@Before if (\@Before > $Before);\n" if $B;
    $Eval .= "study;\n" if (@Perlexpr > 5);
    $Eval .= "if ($Perlexpr) {\n" if !$S;
    $Eval .= ' ' . ($q ? 'exit(0)' : '$Exit_code = 0') . ";\n";
    $Eval .= ' $Binary_file && ((print "Binary file $File matches\n"), last);' . "\n" if !$a;
    $Eval .= " $Perlexpr;\n" if $S;
    $Eval .= " chomp; \$_ .= \"\\n\";\n" if $N;
    $Eval .= " chomp; \$_ .= ' ';\n" if $T;
    $Eval .= " ++\$Count;\n" if $c;
    $Eval .= " \$Found = 1;\n last;\n" if $L;
    $Eval .= " print \"--\\n\" if (\$Matched++ && (\$First_match || (\$After > $gap)));\n" if $context;
    $Eval .= " print \@Before;\n" if $B;
    $Eval .= " $print\n" if $print;
    $Eval .= " \$After = 0;\n" if $A;
    $Eval .= " \@Before = ();\n" if $B;
    $Eval .= " \$First_match = 0;\n" if $context;
    $Eval .= "}\n" if !$S;
    $Eval .= "elsif (++\$After <= $After) {\n $nonmatch_print\n}\n" if $A;
    $Eval .= "else {\n" if ($B || ($context && $t));
    $Eval .= " ++\$After;\n" if (!$A && $B);
    $Eval .= " push \@Before, $output;\n" if $B;
    $Eval .= " last if !\$First_match;\n" if ($context && $t);
    $Eval .= "}\n" if ($B || ($context && $t));
    $Eval .= "\$Offset += \$Length;\n" if $b;
    $Eval .= "push \@P, \$P;\n" if $P;
    $Eval .= " }\n";
    $Eval .= ' print "\n";' . "\n" if $T;
    $Eval .= ' print "' . ($h ? '' : '$File:') . '$Count\n";' . "\n" if $c;
    $Eval .= " chomp \$Z;\n print \"" . ($h ? '' : '$File:') . "\$Z\\n\";\n" if $Z;
    $Eval .= ' if (!$Found) { print "$File\n"; }' . "\n" if $L;
    $Eval .= " reset 'a-z';\n" if $reset;
    $Eval .= " close FILE;\n}\n";
    $Eval .= 'exit(' . ($q ? '1' : '$Exit_code') . ");\n";
    } # build_Eval
    __END__
    =head1 NAME
    peg - Perl expression grep
    =head1 SYNOPSIS
    peg [<options>|-help] <perlexpr> [<files>]
    =head1 DESCRIPTION
    Peg is a grep(1) clone. It uses a Perl expressions to match lines
    from a list of input files, or standard input if none specified.
    Internally, peg eval's code that resembles the following pseudo-Perl:
    foreach $File ( <files> ) {
    open(FILE, "<$File");
    while (<FILE>) {
    if ( <perlexpr> ) {
    print;
    }
    }
    }
    Thus, each input line is available as the Perl variable C<$_>, and this
    will be printed if <perlexpr> is true. In particular, to match lines
    according to a Perl regular expression pattern, it is necessary to place
    it within the pattern matching operator, which defaults to searching C<$_>.
    eg% peg '/\bneedle\b/i' haystack
    Note that <perlexpr> can be any Perl expression, and is not limited just
    to regular expressions.
    =head1 OPTIONS
    The options include equivalents to most of those of standard grep(1),
    including the GNU extensions. They can be grouped anywhere in the
    argument list (except after '--'), and can also be set via the
    environment variable "PEG_OPTIONS".
    If less than two files specified, then B<-h> is assumed.
    Selection and interpretation of <perlexpr>:
    =over 4
    =item B<-E>
    Overrides B<-G> & B<-Q>. Assume <perlexpr> is a Perl expression
    (this is the default behavior).
    =item B<-G>
    Assume <perlexpr> is a Perl regular expression pattern to be matched.
    This option is implicit if any of B<-i>, B<-w>, B<-x> are used, or if
    <perlexpr> matches /^\w+$/ (ie. is entirely alphanumeric). Thus,
    "peg foo bar" is equivalent to "peg '/foo/' bar".
    =item B<-Q>
    Overrides B<-G>. Assume <perlexpr> is a fixed literal string to be matched.
    Thus, C<"peg -Q 'fo+' bar"> is equivalent to "peg '/fo\+/' bar".
    =item B<-f> <file>
    The following argument is a file containing further <perlexpr>'s.
    (Note, this is the only option that takes an argument). Lines
    will be adjudged to match if they match any of the <perlexpr>'s.
    =item B<-o>
    Arguments following the B<-o> option up until '--' are interpreted
    as further <perlexpr>'s. Lines will be adjudged to match if they
    match any of the <perlexpr>'s. For example, C<"peg -o foo bar baz -- file">
    is equivalent to C<"peg '/foo/ or /bar/ or /baz/' file">.
    =item B<-O>
    This option is similar to B<-ol>, except each <perlexpr> must match
    independently. As with B<-o>, arguments following the B<-O> option
    up until '--' are interpreted as further <perlexpr>'s.
    =item B<-i>
    Enables B<-G>. Ignore case distinctions.
    =item B<-v>
    Negates the sense of <perlexpr>.
    =item B<-w>
    Enables B<-G>. Force <perlexpr> to match only whole words.
    =item B<-x>
    Enables B<-G>. Force <perlexpr> to match only whole lines.
    =back
    File selection:
    =over 4
    =item B<-d>
    Any directories listed in the argument list will be searched recursively
    for files to work upon.
    =item B<-r>
    Work upon all files in and beneath the current directory.
    =item B<-X>
    Interpret STDIN as a stream of filenames to process.
    It provides a builtin B<xargs(1)> facility. (See example 6).
    =back
    Basic output control:
    =over 4
    =item B<-a>
    Do not suppress binary output. The default behavior for when
    a match occurs on a binary file is to print "Binary file <filename>
    matches".
    =item B<-A> B<-B> B<-C>
    =     ; B<-NUM>
    These options specify that matching lines should be shown with lines of
    surrounding I<context>. B<-A> shows lines of trailing (I<after>) context;
    B<-B> shows lines of leading (I<before>) context; B<-C> shows both leading
    and trailing context. B<-NUM> sets the number of lines of context for the
    most recently specified context option (the default is 2) or assumes B<-C>
    if none specified. That is, B<-B1A3> specifies one line of leading context
    and three lines of trailing context.
    =item B<-b>
    Print the byte offset within the input file.
    =item B<-c>
    Print only a count of the input lines that match <perlexpr>.
    =item B<-h>
    Suppress filenames being printed when searching multiple files.
    =item B<-H>
    Print the filename for each match.
    =item B<-l>
    Print only the names of files which match <perlexpr> at least once.
    =item B<-L>
    Print only the names of files which don't match <perlexpr> anywhere.
    =item B<-n>
    Print the input line number.
    =item B<-t>
    Print only the first match in any one file.
    =back
    Peg specials:
    =over 4
    =item B<-D>
    Prints out the internal Perl code that would otherwise be eval'ed.
    =item B<-F>
    Provide an array @F which is the result of a split applied to
    the input line.
    =item B<-N>
    Ensure each printed line ends in a newline. This is only necessary
    if <perlexpr> leaves C<$_> without a trailing newline. (See example 2).
    =item B<-P>
    Provide an array @P of the input up until that point. $P[-1] is the
    previous line. This provides a mechanism to allow matches to be made
    over consecutive lines. (See example 4).
    =item B<-S>
    Always print the input line. This enables stream editing with s///.
    =item B<-T>
    Print each file's output on one single line, with each line separated
    by a single whitespace.
    =item B<-y>
    Treat each file as a single line.
    =item B<-Y>
    Treat paragraphs (text delimited by blank lines) as single lines.
    =item B<-Z>
    Print the value of $Z at EOF. (See example 5).
    =back
    Miscellaneous:
    =over 4
    =item B<-g>
    Suppress the error messages about unreadable directories outputted
    when either B<-d> or B<-r> is used.
    =item B<-q>
    Write nothing to STDOUT. Exit 0 if a match is found, else exit 1.
    =item B<-s>
    Suppress all error messages about unreadable files and directories.
    =item B<-V>
    Display peg's version number and exit.
    =item B<-->
    Explicitly end options. Allows filenames beginning with a -.
    Also used by the B<-o> and B<-O> options to determine which arguments are
    <perlexpr>'s and which are files.
    =back
    =head1 EXAMPLES
    1. Search recursively for all VHDL constant declarations:
    % peg -r '/^\s*constant\s.*:=/i'
    2. Find the instance names of CTS buffers in a verilog netlist:
    % peg -N '/^\s*CTS\w*\s+(\w+)\s*\(/ and $_ = $1' foo.v
    3. Extract the entity declaration section from a VHDL file:
    % peg 's/\s*--.*$//, /^\s*entity\b/i .. /^\s*end\b/i' bar.vhd
    4. Search for the sequence A,B,C split over 3 consecutive lines:
    % peg -PB2n '$P[-2]=~/A/ and $P[-1]=~/B/ and /C/' bam
    5. Sum up the entries in the last column of a file:
    % peg -ZF '$Z += $F[-1]' report.txt
    6. Search for "main" in C files below the current directory.
    % find . -name "*.c" | peg -Xw main
    =head1 ENVIRONMENT
    The environment variable PEG_OPTIONS can be used to set options.
    =head1 EXIT STATUS
    The following exit values are returned:
    0one or more matches were found
    1no matches were found
    >1peg did not complete normally
    =head1 SCRIPT CATEGORIES
    Search
    =head1 README
    This script is yet another Perl grep(1).
    =head1 SEE ALSO
    L<perl(1)>, L<perlre>, L<grep(1)>.
    =head1 AUTHOR
    Alex Davies <Alex.Davies@ti.com>
    =head1 COPYRIGHT
    Copyright (c) 1999 Alex Davies. All rights reserved. This program is
    free software; you can redistribute it and/or modify it under the same
    terms as Perl itself.
    =cut


