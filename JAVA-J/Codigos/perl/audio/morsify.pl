#!perl -w
#!/usr/bin/perl -w

use strict;

# winzode users need Win32::Sound;

# Please test it on other platforms blah blah
# i didn't use Audio::DSP;
# just because it's possible to write raw data directly to /dev/dsp

# It took me about a whole day(24h) to finish this, mainly because of the pod
# and because i kept adding on to the thing, so i ended up changing it quite
# a bit.  What really had me at one point is determening what to do with the
# input(translate to or from morse) mainly because i am a regex loser, but
# luckily Petruchio came to the rescue with ($string =~ /[^ .-]/)
# and i never messed with any -switch stuff so that required some thinking/modi.
# added -morse switch because of epoptai 12/31/2000
# also pondered adding -case : adds |b4C ~b4c but it would only have value for
# but decided not to, for now, since morse code doesn't distinguish between case
# also updated the documentation some

# Got the clean bill of health from podchecker (no warnings & i got pod ;)

# This version has all the 'development' variables, and i don't feel like
# changing it anytime soon, maybe sometime in the future, but not likely

# i also pondered where to post this, cool uses for perl or craft, and decided
# to put it in the code catacombs under audio related programs
# whether or not it will be useful to anyone else, radio amateurs maybe

# *explore* and enjoy ;)

my $pitch = .21;
my $volume = '100';
my $duration = .1; #of a $second
my $soundoff; # you really shouldn't modify any vars on this line and below
my $saveas;
my $this;
my $vol1;
my $arger = 'if(!defined$ARGV[0]){print "you need to enter a message";exit;}';
my $mango;
my $doozle;
my $dazzle;
my $drazle;

my %SW=('-?'     =>q( eval("$SW{'-help'}") ),

        '-dur'   =>'if(defined $ARGV[0]){$duration=shift;if($duration =~ /[-+'.
                   ']?\d+/){if(!($duration>0)){print "you need to enter a numb'.
                   'er > 0";exit;}}else{print "not a number\n";exit;}}else{pri'.
                   'nt "missing number\n";exit;}',

        '-fukc'  =>'while(<DATA>){if(/__END__/){select(undef,undef,undef,0'.
                   '.5);exit;}print $_;}',

        '-help'  => q(print "\n".'-dur real > 0 : specifies the duration of a ).
                    q(dot (see NOTES for more detail)'."\n \n".'-pitch 0 > rea).
                    q(l < 2 : specifies the pitch of the tone used'."\n \n".'-).
                    q(help : displays a brief description of all the switches').
                    q(."\n \n".'-man : displays the pod for morsify '."\n \n".).
                    q('-save $filename(.wav) : saves the sound as $filename.wa).
                    q(v'."\n \n".'-sound : turns sound off [ie. the message is).
                    q( only translated]'."\n \n".'-text : forces morsify to tr).
                    q(anslate .-. into, as opposed from, morsecode'."\n \n".'-).
                    q(vol real > 0 : set the volume to num (default 100%) of t).
                    q(he wav mixer setting'."\n";exit;),

        '-man'   =>'system("perldoc $0");exit;',

        '-morse'  =>'$dazzle=1;undef $drazle;',

        '-pitch' =>'if(defined $ARGV[0]){$pitch =shift;if (!($pitch =~ /[-'.
                   '+]?\d+/)){print"$pitch in -pitch != numb";exit;}if(!(($pit'.
                   'ch > 0) && ($pitch < 2))){print "the pitch must be betwee'.
                   'n 0 and 2";exit;}}else{print "you need to enter a 0 < pit'.
                   'ch < 2";exit;}',
      
        '-save'  =>'if(defined $ARGV[0]){$saveas=shift;if(lc(substr($saveas'.
                    ',-4)) ne q(.wav)){$saveas.=".wav";}if(!($saveas=~/(\w)/))'.
                    '{print "warning, filename seems funky, may not save \n'.
                    '";}}else{print "missing filename and\n";}',

        '-sound' =>'$soundoff = 1;',

        '-text'  =>'$dazzle=1;$drazle=1;',

        '-thank' =>'for(0..8){print " "x($_*$_)."carrots n p-funk!\n";}exit;',

        '-vol'   =>'if(defined $ARGV[0]){$volume =shift;if($volume =~ /(\D)/)'.
                   '{print"$volume in -vol != number";exit;}if($'.
                   'volume < 1){print "the volume must be > 1";exit;}}else'.
                   '{print "you need to enter a volume";exit;}$vol1=666;',
        );

if(defined @ARGV)
{
    while(exists($SW{$ARGV[0]}))
    {
        $mango = shift @ARGV;
        eval("$SW{$mango}");
        eval("$arger");
    }
   
    $this = (defined @ARGV) ? join(' ',@ARGV): '~`><#@$%^&*\"[]';

    if($this =~ /[^ .-]/)#P: decide whether it's morse or not
    {  $doozle = 1;  }
        
    if(defined $dazzle)
    {$doozle = $drazle;}# if -text was passed defines $doozle

    if(defined $doozle)
    {
        $this = &morse(1,$this);
        print $this;
        &morsesound($this);
    }
    else
    {
        &morsesound($this);
        print &morse(0,$this);
    }
}
else
{
    print"syntaxt: $0 -[?,dur,help,man,morse,pitch,save,sound,text] morseORtxt";
    exit;
}

sub morse
{
    my %chars=(
    'A' => '.-',        'B' => '-...',      'C' => '-.-.',      'D' => '-..',
    'E' => '.',         'F' => '..-.',      'G' => '--.',       'H' => '....',
    'I' => '..',        'J' => '.---',      'K' => '-.-',       'L' => '.-..',
    'M' => '--',        'N' => '-.',        'O' => '---',       'P' => '.--.',
    'Q' => '--.-',      'R' => '.-.',       'S' => '...',       'T' => '-',
    'U' => '..-',       'V' => '...-',      'W' => '.--',       'X' => '-..-',
    'Y' => '-.--',      'Z' => '--..',      '1' => '.----',     '2' => '..---',
    '3' => '...--',     '4' => '....-',     '5' => '.....',     '6' => '-....',
    '7' => '--...',     '8' => '---..',     '9' => '----.',     '0' => '-----',
    ',' => '--..--',    '.' => '.-.-.-',    '?' => '..--..',    '!' => '.----.',
    ';' => '-.-.-',     ':' => '---...',    '/' => '-..-.',     '-' => '-....-',
    "'" => '.----.',    '(' => '-.--.-',    ')' => '-.--.-',    '_' => '..--.-',
    'Á' => '.--.-', #A w/a accent
    'Å' => '.--.-', #A w/a circle
    'Ä' => '.-.-',  #A w/2 dots
    'É' => '..-..', #E w/a accent
    'Ñ' => '--.--', #N w/a tilde
    'Ö' => '---.',  #O w/2 dots (umlaut)
    'Ü' => '..--',  #U w/2 dots (umlaut)
    );# endof %chars

    my $abc = "";
    if($_[0]==1)
    {
        my $dis = $_[1];
            $dis =~ tr/[a-z]/[A-Z]/;
        my @dis = split('',$dis);

        for(0..$#dis)
        {
            if(defined $chars{$dis[$_]})
            {  $dis = $chars{$dis[$_]};  }
            else
            {  $dis = $dis[$_]; }

            $abc .= $dis . " ";
        }
    }
    else
    {
        my %inverse_chars = reverse(%chars);
        my $dis = $_[1];
        my @words = split('  ',"$dis");

        for(0..$#words)
        {
            my @letters = split(' ',$words[$_]);

            for my $op(0..$#letters)
            {
                if(defined $inverse_chars{$letters[$op]})
                {  $dis = $inverse_chars{$letters[$op]};  }
                else
                {  $dis = $letters[$op]; }
    
                $abc .= $dis;
            }
            $abc .= " ";
            
        }
    }

    return $abc;
}

sub morsesound
{
    my $increment = 440/44100;# 440 / num of samples that add up to 1 sec
    my $dit = "";
    my $counter = 0;
    my $v;
    my $silence = "";
    my $daw = "";
    
    if((!defined $soundoff)or(defined $saveas))
    {# if da person doesn't want to hear it, or save it, don't generate it
        for (1..($duration*44100))
        {
            # Calculate the pitch 
            # (range 0..255 for 8 bits)
            $v = sin($counter/$pitch*3.14) * 128 + 128;
            # "pack" it twice for left and right
            $dit .= pack("cc", $v, $v);
            $counter += $increment;
        }
    
        $counter=0;
    
        for (1..(44100*$duration*3))
        {# not really silence, but pitch so low it practically is
            # Calculate the pitch 
            # (range 0..255 for 8 bits)
            $v = sin($counter/$pitch*3.14) * 128 + 128;
            # "pack" it twice for left and right
            $daw .= pack("cc", $v, $v);
            $counter += $increment;
        }
    
        $counter=0;
    
        for (1..(44100*$duration))
        {# not really silence, but pitch so low it practically is
            # Calculate the pitch 
            # (range 0..255 for 8 bits)
            $v = sin($counter/999999*3.14) * 128 + 128;
            # "pack" it twice for left and right
            $silence .= pack("cc", $v, $v);
            $counter += $increment;
        }
    }
    my $stringmorse = shift; # get the string passed
    my $soundmorse = ""; # to be played (the wav)
    my @msg = split('',"$stringmorse");

    for(my $ix=0;$ix<(@msg);$ix++)
    {
        if($msg[$ix] eq '.')
        {
            $soundmorse .= "$dit$silence";
        }
        elsif($msg[$ix] eq '-')
        {
            $soundmorse .= "$daw$silence";
        }
        elsif($msg[$ix] eq ' ')
        {
            $soundmorse .= "$silence"x3;

            if(($ix >= 1) && ($msg[$ix-1] eq ' '))
            {#if there are 2 spaces in a row, it indicates a word, and words
                $soundmorse .= "$silence"x4;   # are separated by 7 spaces
            }
        }
    }

    if($^O =~ /MSWin32/i)
    {
        eval("use Win32::Sound;"); # put in eval thanx to ichimunki

        if(defined $vol1)
        {  Win32::Sound::Volume($volume.'%') or die "Can't get volume: $!";  }

        my $WAV = new Win32::Sound::WaveOut(44100, 8, 2);# rate|format|channel
    
        if(!defined $soundoff)
        {
            $WAV->Load("$soundmorse");       # get it
            $WAV->Write();           # hear it
            1 until $WAV->Status();  # wait for completion
        }
        if(defined $saveas)
        {
            $WAV->Save("$saveas","$soundmorse"); # write to disk
        }
        $WAV->Unload();          # drop it
    }
    else
    {
        if(!defined $soundoff)
        {# printing directly to /dev/dsp should work
            $| = q/flush it out right away/;
            open(NIXSOUND,'>/dev/dsp') or die "can't open /dev/dsp 4 w: $!";
            print NIXSOUND $soundmorse;
            close(NIXSOUND);
        }
        
        if(defined $saveas)
        {
            open(WAVOUT,">$saveas") or die "can't open $saveas 4 writing: $!";
            print WAVOUT $soundmorse;
            print WAVOUT "\n";# added cause of, or thanx to ichimunki
            close(WAVOUT);
        } # write to disk
    }
}
close(STDOUT);

__DATA__

i like java
i like jikes.org
you may shoot me now, if you wish, you ignorant perlophile
"-.-. .-. .- --.. -.--   .-.. .. -.- .   .-   ..-. --- -..- .----."
".--- ..- ... -
  .- -. --- - .... . .-.
  .--. . .-. .-..
  .-- .... .- -.-. -.- . .-."
__END__
=pod

=head1 NAME

morsify - Translate standard input into morse/text code and sound it out

=head1 SYNOPSIS

=over 4

=item SWITCHES

=back

=over 6

=item -?

=begin text

 see -help

=end text

=item -dur real E<gt> 0

=begin text

 specifies the duration of a dot (see NOTES for more detail)

=end text

=item -help

=begin text

 displays a brief description of all the switches

=end text

=item -man

=begin text

 displays this 'man' page (POD!)

=end text

=item -morse

=begin text

 forces morsify to translate as much morse into text as possible

=end text

=item -pitch 0 E<gt> real E<lt> 2

=begin text

 specifies the pitch of the tone used

=end text

=item -save $filename(.wav)

=begin text

 saves the 'sound msg' as $filename(.wav) if it doesn't end in .wav

=end text

=item -sound

=begin text

 turns sound off (ie. the message is only translated)

=end text

=item -text

=begin text

 forces morsify to translate as much text into morse as possible

=end text

=item -vol real E<gt> 0

=begin text

 set the volume to num (default 100%) of the wav mixer setting
 at some point(on my system 650+) it toppels over and starts from zero

=end text

=back

=begin text

    *> perl morsify.pl morsify n sound
    *> -- --- .-. ... .. ..-. -.--   -.   ... --- ..- -. -..

=end text

=over 4

=item oE<72>

=back

=begin text

    *> perl morsify.pl "morsify n sound"
    *> -- --- .-. ... .. ..-. -.--   -.   ... --- ..- -. -..

=end text

=over 4

=item OR

=back

=begin text

    *> perl morsify -- --- .-. ... .. ..-. -.--   -.   ... --- ..- -. -..
    *> MORSIFYNSOUND

=end text

=over 4

=item or

=back

=begin text

    *> perl morsify "-- --- .-. ... .. ..-. -.--   -.   ... --- ..- -. -.."
    *> MORSIFY N SOUND

=end text


=head1 DESCRIPTION

I<morsify> takes standard input and translates it into morse code.
Unkown characters are ignored.

=head1 ENVIRONMENT

I<morsify> is not affected by any environment variables.

=head1 COMPATIBILITY

Should run on all Win9x/NT/2000 with Win32::Sound; as well as all *nix which
have /dev/dsp

Only these characters: a..z|0..9|.|?|!|'| |/|:|;|-|Á|Ä|É|Ñ|Ö|Ü  :are translated
but these and others:  | ~ ` E<gt> E<lt> # @ $% ^ & * \ " [ ] :are just ignored.

=head1 NOTES

      *> perl morsify -this
    results in
      *> -....- - .... .. ...
    and not an invalid switch warning
    
If the duration of a dot is taken to be one unit then that of a dash is three
units. The space between the components of one character is one unit, between
characters is three units and between words seven units. To indicate that a
mistake has been made and for  the receiver  to delete the last word send
........ (eight dots).

characters translated into morse are separeated by a single space, while words
are separated by two (ie: "e  ee" results in ".  . .")

=head1 BUGS

I<morsify> has no known bugs.

=head1 AUTHOR

This Perl version of I<morsify> was written by DH
I<crazyinsomniac@yahoo.com> who might wish to reveal his
real sometime in the future.

=head1 COPYRIGHT and LICENSE

This program is copyright (c) DH I<crazyinsomniac@yahoo.com> 2001.

This program is free and open source.  You may use, modify, and distribute,
but not sell, this program (and any modified variants) in any way you wish,
provided you do not restrict others from doing the same, and you give B<I<ample>>
credit to me %:-)

=cut

