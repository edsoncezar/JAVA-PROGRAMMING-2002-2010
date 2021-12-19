#!/usr/bin/perl -w

# Copyright (C) 2000 Steve Haslam
# This is free software, distributable under the same terms as Perl
# itself - see the Perl source distribution for details.

# Generate HTML pages and fingernails of photos from an XML file:
#  e.g.: ./make_photos (uses photolist.xml, flibble.jpg, flibblebed.jpg, flibble-and-mewler.jpg)
#   --> index.html, cats.html, flibble-s.jpg, flibblebed-s.jpg, flibble-and-mewler-s.jpg

require 5;
use strict;
use XML::Parser;
use XML::DOM;
use IO::File;
use POSIX qw(strftime);
use Sys::Hostname;
use Image::Size;
use Image::Magick;
use Getopt::Std;
use vars qw($listdoc %index $photo_dir $html_dir $opt_f $opt_d $opt_h);

sub read_photolist {
  my $filename = shift;
  my $parser = XML::DOM::Parser->new;
  $parser->parsefile($filename);
}

sub read_description {
  my $photo_node = shift; # XML::DOM::Element (photo)
  my $path = shift;
  my $indexp = shift;
  my $description = '';
  my $plaindescription = '';
  
  my @indexrefs;
  
  foreach my $descnode ($photo_node->getChildNodes) {
    if ($descnode->getNodeType == TEXT_NODE) {
      # Add some more text
      $description .= $descnode->getData;
      $plaindescription .= $descnode->getData;
    }
    elsif ($descnode->getNodeType == ELEMENT_NODE) {
      if ($descnode->getTagName eq 'index') {
	# Index reference
	# Get the text content
	my $indexcontent = '';
	foreach my $idxnode ($descnode->getChildNodes) {
	  next unless ($idxnode->getNodeType == TEXT_NODE);
	  $indexcontent .= $idxnode->getData;
	}
	my $indexid = $descnode->getAttribute("id") || $indexcontent;
	push(@indexrefs, $indexid); # Can't push to $indexp until we have the entire description
	# Add the index link to the description
	$description .= "<a href=\"__xref.html#$indexid\">$indexcontent</a>";
	$plaindescription .= $indexcontent;
      }
      else {
	die "Unknown element <".$descnode->getTagName."> in <photo>\n";
      }
    }
  }

  foreach (@indexrefs) {
    push(@{$indexp->{$_}}, { href => page_filename(@$path) . '#' . $photo_node->getAttribute('name'), description => $plaindescription });
  }
  
  return $description;
}

sub page_filename {
  @_ ? join("-", map { lc($_) } @_).".html" : "index.html";
}

sub get_photo_fingernail {
  my $photo_filename = shift;
  my $obj = {};
  my $fnfile;

  if (! -f "$photo_dir/$photo_filename") {
    print "*** Photo \"$photo_filename\" does not exist\n";
    return { src => '', width => 0, height => 0 };
  }
  
  print "Finding fingernail for \"$photo_filename\"...";

  if ($photo_filename =~ /(.*)\.([^.]+)/) {
    my($stem, $ext) = ($1, $2);
    foreach ("${stem}-s.$ext", "${stem}-s.png") {
      if (-f "$photo_dir/$_") {
	$fnfile = $_;
	last;
      }
    }
  }

  if ($fnfile) {
    print "$fnfile\n";
    my($width, $height) = imgsize("$photo_dir/$fnfile");
    { src => $fnfile, width => $width, height => $height };
  }
  else {
    print "(not found)\n";
    $fnfile = $photo_filename;
    $fnfile =~ s|\.([^.+]+)$|-s.png| or die "Don't know how to make fingernail file name for $photo_filename\n";
    my $image = Image::Magick->new;
    $image->Read("$photo_dir/$photo_filename");
    my($origwidth, $origheight) = $image->Get('width', 'height');
    print "Original size: $origwidth x $origheight\n";
    my($fwidth, $fheight);
    if ($origwidth > $origheight) {
      $fwidth = int(64 * $origwidth / $origheight);
      $fheight = 64;
    }
    else {
      $fwidth = 64;
      $fheight = int(64 * $origheight / $origwidth);
    }
    print "Fingernail size: $fwidth x $fheight\n";
    $image->Scale(geom => "${fwidth}x${fheight}");
    $image->Write("$photo_dir/$fnfile");
    { src => $fnfile, width => $fwidth, height => $fheight };
  }
}
  
sub process_photolist {
  my $photolist = shift; # XML::DOM::Element (section or photolist)
  my $path = shift;
  my $indexp = shift;
  my $title = $photolist->getAttribute("title");
  my $outfile = "$html_dir/".page_filename(@$path);
  my $outstream = IO::File->new(">$outfile") or die "Unable to write to $outfile: $!\n";

  $outstream->print(<<EOF);
<html lang="en-gb">
<head>
<title> Photos: $title </title>
</head>
<body>
<h1> $title </h1>
EOF

  foreach my $node ($photolist->getChildNodes) {
    next unless ($node->getNodeType == ELEMENT_NODE);
    if ($node->getTagName eq 'section') {
      my $section_id = $node->getAttribute("name") or die "No \"name\" attribute on <section>\n";
      my $section_title = $node->getAttribute("title") or die "No \"title\" attribute on <section name=\"$section_id\">\n";
      my @newpath = (@$path, $section_id);
      my $section_page = page_filename(@newpath);
      $outstream->print(<<EOF);
<h2> $section_title </h2>
  <a href="$section_page">More photos...</a>
EOF
      process_photolist($node, \@newpath, $indexp);
    }
    elsif ($node->getTagName eq 'photo') {
      my $photo_name = $node->getAttribute("name");
      my $photo_filename = $node->getAttribute("filename");
      my $description = read_description($node, $path, $indexp);
      my $photo_fingernail = get_photo_fingernail($photo_filename);
      $outstream->print(<<EOF);
<a name="$photo_name">
<table>
 <tr> <td> <a href="$photo_filename"><img src="$photo_fingernail->{src}" width="$photo_fingernail->{width}" height="$photo_fingernail->{height}"></a> </td>
      <td> $description </td> </table>

EOF
    }
    else {
      die "Unknown element <".$node->getTagName."> in <".$photolist->getTagName.">\n";
    }
  }

  if (@$path) {
    # upwards navigation
    $outstream->print(<<EOF);
<hr> <menu>
EOF
    for (my $i=$#$path-1; $i>=0; $i--) {
      my @subpath = @$path[0..$i];
      my $page = page_filename(@subpath);
      my $path = $subpath[$#subpath];
      $outstream->print(<<EOF);
<li> <a href="$page">$path</a> </li>
EOF
    }
    $outstream->print(<<EOF);
<li> <a href="./">Top photo page</a> </li>
</menu>
EOF
  }
  else {
    # link to cross-references
    if (%$indexp) {
      $outstream->print(<<EOF);
<hr> <p> Handy page of <a href="__xref.html"> cross-references </a>
EOF
    }
  }
  
  my $date = strftime("%d %b %Y, %H:%M:%S %Z", localtime(time));
  my $hostname = hostname;
  my $user = getlogin || (getpwnam($<))[0];
  
  $outstream->print(<<EOF);
<hr> <p> Generated $date on $hostname by $user
</body>
</html>
EOF

  $outstream->close;
}

sub writexrefs {
  my $indexp = shift;
  my $outfile = "$html_dir/__xref.html";
  my $outstream = IO::File->new(">$outfile") or die "Unable to write to $outfile: $!\n";

  $outstream->print(<<EOF);
<html lang="en-gb">
<head>
<title> Photos: Index </title>
</head>
<body>
<h1> Index </h1>

<table>

EOF

  foreach my $who (sort keys %$indexp) {
    my $photos = $indexp->{$who};
    $outstream->print(<<EOF);
<tr> <td> $who <td>
EOF
    foreach my $photo (@$photos) {
      $outstream->print("<a href=\"$photo->{href}\">$photo->{description}</a> <br>");
    }
  }

  $outstream->print(<<EOF);
</table>
<hr>
<menu>
<li> <a href="./">Top photo page</a> </li>
</menu>
</body>
</html>
EOF

  $outstream->close;
}

getopts('d:f:h:') && @ARGV == 0 or die <<EOF;
Syntax: $0 [-f photolist.xml] [-d photo_dir] [-h html_dir]
See the POD documentation for more information
EOF

$photo_dir = $opt_d || ".";
$html_dir = $opt_h || $photo_dir;

$listdoc = read_photolist($opt_f || "photolist.xml");

process_photolist($listdoc->getDocumentElement, [], \%index);
writexrefs(\%index);

=head1 NAME

make_photos - Generate HTML and fingernails for putting photos on the web

=head1 SYNOPSIS

  make_photos [-d B<photos_dir> [-h B<html_dir>]] [-f B<photolist>]

=head1 DESCRIPTION

Reads an XML file (B<photolist>, or C<photolist.xml> by default) and
uses it to build HTML pages for navigating through a tree of
photographs.

As well as generating HTML, it will also automagically generate
scaled-down PNGs to use as fingernails. It can also produce a secondary
index- e.g. for people who appear in various places in the tree.

The XML used looks like this:

   <photolist title="My Photos">
    <section name="Cats" title="Photos of cats">
     <photo name="Photo1" filename="flibble.jpg">
      This is the first photo, a picture of my cat <index>Flibble<index>
     </photo>
     <photo name="Photo2" filename="flibblebed.jpg">
      This is a photo of <index id="Flibble">Flibble's</index> bed
     </photo>
     <photo name="Photo3" filename="flibble-and-mewler.jpg">
      This is <index>Flibble</index> and our neighbour's cat <index>Mewler</index>
     </photo>
    </section>
   </photolist>

Sections may be nested. The "name" elements are used to build the HTML
filenames and anchors.

The B<index> tag may or may not take an B<id> attribute- if none is
specified, then the text content is used for indexing. (I suspect this
is evil, but...)

The photos referenced in the XML file are expected to be found in
B<photos_dir> (defaults to the current working directory). The
fingernail file is expected to have the same name but with "-s"
interposed between the basename and the extension, viz.:

   Photo file      Fingernail file
   flibble.jpg     flibble-s.jpg
   flibblebed.jpg  flibblebed-s.jpg

The HTML is written on to B<html_dir>, which defaults to the same
directory as where the photos are found...

=head1 BUGS

There is no way to customise the HTML output without modifying the
script, and the HTML currently generated is unpleasant.

There is no way to customise the scaling decision used for the
fingernail file (scales smallest dimension to 64 pixels).

HTML escaping is not performed.

I don't really have a cat called Flibble. Honest.

=head1 AUTHOR

Steve Haslam <araqnid@debian.org>

=cut
