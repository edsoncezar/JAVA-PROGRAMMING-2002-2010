#!/usr/bin/perl -w
use strict;
use XML::Writer;

# Small utility to parse Opera bookmark files.
# Originally written in Python by Lars Marius Garshol
# perl version - Briac Pilpré

my $version = <>;
die "Doesn't seem to be a Opera Hotlist\n" if $version !~ /^Opera/;

chomp( $version );

my $xml = new XML::Writer(DATA_MODE=>1, DATA_INDENT=>1);
   $xml->xmlDecl();
   $xml->doctype(
       'xbel',
       '+//IDN python.org//DTD XML Bookmark Exchange Language 1.0//EN//XML',
       'xbel.dtd');
   $xml->startTag('xbel');
   $xml->dataElement('desc', $version);

while (<>){
        next unless /\S/;
        chomp;
        if ( $_ eq '#FOLDER' || $_ eq '#URL' ){
                my $node = $_;

                my $line;
                {
                        local $/ = "";  # Paragraph mode
                        $line    = <>;
                }

                if ($node eq '#FOLDER'){
                        my $name    = $1 if $line =~ /NAME=(.*)/;
                        my $created = $1 if $line =~ /CREATED=(.*)/;
                        $xml->startTag('folder');
                        $xml->dataElement('title', $name);
                }

                elsif ($node eq '#URL') {
                        my $name    = $1 if $line =~ /NAME=(.*)/;
                        my $created = $1 if $line =~ /CREATED=(.*)/;
                        my $order   = $1 if $line =~ /ORDER=(.*)/;
                        my $visited = $1 if $line =~ /VISITED=(.*)/;
                        my $url     = $1 if $line =~ /URL=(.*)/;

                        $xml->startTag('bookmark', href => $url, 
                            added => $created, visited => $visited);
                        $xml->dataElement('title', $name);
                        $xml->endTag('bookmark');
                }

        }
        elsif ( $_ eq '-'){
                $xml->endTag('folder');
        }
}

$xml->endTag('xbel');

##</code><code>##

__END__
#!/usr/bin/perl -w
use strict;
use XML::Parser;
die "usage: $0 opera.xbel\n" if !$ARGV[0];
my $parser = new XML::Parser(Handlers => {
	Start=>\&start, End=>\&end, Char=>\&char,
});
my%equiv=(title=>'NAME', desc=>'DESCRIPTION');
my $flag = '';
$parser->parsefile($ARGV[0]);
sub char {
	my ($p, $txt) = @_; return if $txt =~ /^\s*$/;
      chomp($txt);
	print $txt . "\n" if exists($equiv{$flag});
}
sub start {
	my ($p, $e, %a) = @_; $flag = $e;
	if    ($e eq 'folder'){  print "\n#FOLDER\n" }
	elsif ($e eq 'bookmark'){print "\n#URL\n\tURL=" . $a{'href'} if $a{'href'}}
	else  {print "\n\t" . $equiv{$e} . "=" if exists $equiv{$e}	}
}
sub end {undef $flag}

##</code><code>##

__END__
#!/usr/bin/perl -w
#
# adr2xbel : Convert Opera Bookmarks file to xbel
#            (XML Bookmark Exchange Language)
# OeufMayo - 23 apr 2001

use strict;
use XML::Writer;
use IO;
die "usage: $0 opera.adr\n" if !$ARGV[0];
my $output = new IO::File("> $ARGV[0].xbel.xml") or die $!;
my $writer = new XML::Writer(OUTPUT => $output, 
                             DATA_MODE => 1,
				     DATA_INDENT => 4) or die $!;
$writer->xmlDecl('iso-8859-1', 'no');
$writer->doctype(
           'xbel',
           undef,
           'http://www.python.org/topics/xml/dtds/xbel-1.0.dtd');
$writer->startTag('xbel');
$writer->dataElement('title', 'Opera Hotlist version 2.0');

$/ = "";
while (<>){
	chomp;
	my @chunk = split(/\n\t/, $_);
	
	while ($chunk[0] =~ s/^(-\n)//){	# Folder end
		$writer->endTag('folder') if $chunk[0];
	}

	if    ($chunk[0] =~ /^#FOLDER/){	# Folder beginning
		shift @chunk;
		my %attrs = &getAttlist(@chunk);
		$writer->startTag("folder");
		for ('title', 'desc'){
			$writer->dataElement($_, $attrs{$_});
		}
	}
	
	elsif ($chunk[0] =~ /^#URL/){		# URL Tag
		shift @chunk;
		my %attrs = &getAttlist(@chunk);
		$writer->startTag("bookmark", 
                              'href' => $attrs{href} );
			for ('title', 'desc'){
				$writer->dataElement($_, $attrs{$_});
			}
		$writer->endTag("bookmark");
	}
}

$writer->endTag('xbel');
$writer->end;

#########################

sub getAttlist {
	my %attrs;
	my %tr = (
            URL=>'href',
            DESCRIPTION=>'desc',
            NAME=>'title',
      );
	foreach $_ (@_){
		m/^([^=]+)=(.*)$/;
		my ($key, $value) = ($1, $2);
		next unless (
                   $key eq 'URL'         || 
                   $key eq 'DESCRIPTION' || 
                   $key eq 'NAME'
            );
		$key = $tr{$key};
		$attrs{$key} = $value;
	}
	return %attrs;
}

