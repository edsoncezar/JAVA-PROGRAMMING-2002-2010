
#!/usr/local/bin/perl -w
#
# Usage:
#   net-bg.pl some words          # loop over images that match 
#   net-bg.pl 1                   # random images from random words
#   net-bg.pl                     # save as 'net-bg.pl 1'
#
# If you give it words in @ARGV, it will continually show images
# that match those words, and exit when it runs out.
#
# If you give it a number (n) in @ARGV, it will loop forever, each time
# picking n random words to find a random image.  (each image from a
# different set of random words)
# NOTE: if your screen size is large and n>1 it could take a LOOOOONG
# time to find an image
#
# See the "configuration options" section below for variables you can
# adjust to customize the behavior.
#
# PS: If you set $imgfile to /tmp/bar you might find this usefull...
#    alias save-bg cp /tmp/bar "~/saved-bgs/`date +"%Y-%m-%d-%H-%M-%S"`"
# (or bind it to a button) 
#
# Inspiration based on something alexjb said to me one day
# (but didn't really mean).  Some of this code is borrowed from...
# "webcollage, Copyright (c) 1999-2001 by Jamie Zawinski <jwz@jwz.org>"
#
# NOTE: Using this code *MAY* violate the Goole Terms of Service,
#       http://www.google.com/terms_of_service.html
#       Use at your own risk.
#
########################################################################
#
# :TODO: use GetOpt
#
use strict;
use LWP;
use URI;

###########################################################################
# configuration options

# location of the dictionary
my $dict = "/usr/share/dict/words";
# ideal number of URLs for a good random sample in rand word mode
# (the higher it is, the longer it takes to cycle)
my $rand_sample_size = 70; 
# ideal number of URLs to cycle through in specific word mode
# (the higher it is, the longer it takes to start)
my $specific_sample_size = 400; 
# optimal image size, if 0, defaults to screen size
my ($img_width, $img_height) = (0, 0);
# fudge factor, what size percentage diff can images have
# (set to 0 if you wan't only images that are exactly $img_width x $img_height)
my $fudge = 0.22;  # 0.22 means '800x600 ok for 1024x768'
# what type of images?   &PORN_OK or &FAMILY_ONLY ?
my $img_filter = &PORN_OK; # &FAMILY_ONLY;
# min seconds that have to elapse between images
# (if we find a "next" image before this many seconds has passed, we wait.)
my $min_delay = 30;
# timeout value for the LWP User Agent
my $ua_timeout = $min_delay - 1;
# place to store the current bg img.
my $imgfile = "$ENV{HOME}/curr-rand-bg"; # ($ENV{TMPDIR} ? $ENV{TMPDIR} : "/tmp/") ."curr-rand-bg.$$";
# place to store the img "on deck"
my $nextimgfile = "$ENV{HOME}/next-rand-bg"; #($ENV{TMPDIR} ? $ENV{TMPDIR} : "/tmp/") ."next-rand-bg.$$";
# where you want debug info to go (if anywhere)
my $DEBUG_HANDLE; # = *STDOUT; # = *STDERR;
# command for diplaying images in the root window (file name goes after)
my $root_cmd = "xv -root -quit -viewonly +noresetroot -rmode 5"
    .          "   -rfg black -rbg black -maxpect ";


############################################################################
# globals
my $ua = new LWP::UserAgent(timeout=>$ua_timeout);
$ua->agent("Lynx 2.5"); # fuck you too



##########################################################################
# main code

# pick the image size unless we allready have some
if (!$img_width || !$img_height) {
    $_ = "xdpyinfo";
    &which($_) || die "$_ not found on \$PATH -- you have to pick a size";
    $_ = `$_`;
    ($img_width, $img_height) = m/dimensions: *(\d+)x(\d+) /;
    if (!defined($img_height)) {
	die "xdpyinfo failed -- you have to pick a size";
  }
}

# do we have words or a number?
my $num = 0;
if (defined $ARGV[0]) {
    if ($ARGV[0] =~ /\d+/) {
	$num = $ARGV[0];
    }
} else {
    $num = 1;
}


if (0 < $num) {
    # random word option
    #
    # loop forever, constantly pick new words, and show only one image per word
    my $last_image_time = 0;

    while (1) {
	# if we get in this loop, it should never exit
	my $words = join ' ', &random_words($num);
	
	my %urlmap = &get_n_google_images_by_size($words,
						  $img_filter,
						  $img_width, $img_height,
						  $fudge,
						  $rand_sample_size);
	my @urls = keys %urlmap;
	&debug(scalar(@urls) . " URLS for: $words \n");
	redo if 0 == scalar(@urls); # don't sleep if we didn't find anything

	for (my $retry = 10; 0 < $retry and 0 < scalar(@urls); $retry--) {
	    # keep trying random images from this set
	    # untill we get one we like, or we get sick of trying
	    my $url = splice @urls, rand(scalar(@urls)), 1;
	    &debug("trying: $url\n");
	    last if &save_page($url, $nextimgfile);
	}
	# wait untill the minumal time has passed before showing
	# (or don't wait if enough time has past)
	sleep_diff($last_image_time, $min_delay);
	if (&show_next_img) {
	    $last_image_time = time;
	}
    }
} else {
    # specific word option
    #
    # do one search, get all the words, and then loop over them in order
    my $last_image_time = 0;

    my $words = join ' ', @ARGV;
    my %urlmap = &get_n_google_images_by_size($words,
					      $img_filter,
					      $img_width, $img_height,
					      $fudge,
					      $specific_sample_size);
    my @urls = keys %urlmap;
    &debug(scalar(@urls) . " URLS for: $words \n");
    while (@urls) {

	my $url = splice @urls, rand(scalar(@urls)), 1;
	&debug("trying: $url\n");
	if (&save_page($url, $nextimgfile)) {
	    # wait untill the minumal time has passed before showing
	    # (or don't wait if enough time has past)
	    sleep_diff($last_image_time, $min_delay);
	    if (&show_next_img) {
		$last_image_time = time;
		&debug("displayed next image\n");
	    }
	} # if save_page
    } # while @urls
    &debug("done with all URLs for $words\n");
    exit;
}
    

#############################################################
# functions
#############################################################

sub get_n_google_images_by_size {
    # @_ = $search_term, $mature, $width, $height, $fudge, $n
    #
    # give some search terms, and some size prefrences, will get
    # successive google image results pages untill it finds at least
    # $n images that meet the criteria (or as many as it can find).
    #
    # returns a hash of { $url => [$w, $h] }
    my ($q, $mature, $w, $h, $f, $n) = @_;
    my $second = 20; # the number to start with for page 2
    
    # results isn't a hash, but this makes it easy to pool things
    my @results = (); # just remeber to divide by 2 for usefull sizing
    
    my $page = 0;
    while (scalar @results / 2 < $n) {
	# get the page
	my $doc = &get_google_page($q, $mature, $page);

	# get out now if there's a problem
	last unless defined $doc;

	push @results, &cut_by_size($w, $h, $f,
				    &parse_google_img_links($doc));	
	
	last unless &parse_google_has_next($doc);

	$page += 20; # 20 itmes per page
    }
    
    return @results;
    
}

sub cut_by_size {
    # @_ = $width, $height, $fudge, %url_to_size
    #
    # returns a subset of %url_to_size that meet the specified size critera.
    #
    # $width is the optimal width, $height is the optimal height,
    # and $fudge is a +/-percentage that the images are allowed to deviate
    # in both width & height.  if $fudge is 0 then the images MUST be
    # exactly $width & $height.
    my ($width, $height, $fudge, %urls) = @_;
    my %result;

    my $min_w = $width - ($width * $fudge);
    my $max_w = $width + ($width * $fudge);
    my $min_h = $height - ($height * $fudge);
    my $max_h = $height + ($height * $fudge);

    
    foreach my $url (keys %urls) {
	next unless $urls{$url}[0] <= $max_w;
	next unless $urls{$url}[0] >= $min_w;
	next unless $urls{$url}[1] <= $max_h;
	next unless $urls{$url}[1] >= $min_h;
	$result{$url} = $urls{$url};
    }
    return %result;
}

sub get_page {
    # @_ = $uri (URI object or string)
    #
    # will fetch the URI using the global $ua
    # fakes out hte refer so mean sites won't serve an error img.
    #
    # returns the contents of the url, or undef if a failure
    my $uri = shift;

    $uri = new URI($uri) unless ref $uri;
    
    # get us a good refer url
    my $baseuri = $uri->clone();
    $baseuri->path("/");
    $baseuri->query(undef);

    my $req = HTTP::Request->new(GET => $uri);
    $req->referer($baseuri->as_string());
    my $res = $ua->request($req);
    if (! $res->is_success) {
	&debug("FAILED: " . $res->code() . " " . $uri->as_string() . "\n");
	return undef;
    }
    &debug("WTF? success, but undef\n") unless defined $res->content();
    # parse the output
    return $res->content();
}

sub save_page {
    # @_ = $uri (URI object or string), $file (string)
    #
    # saves the contents of $uri into $file
    # returns true if everythign is kosher, or false if there was a problem
    my ($uri, $file) = @_;
    
    my $content = get_page $uri;

    return 0 unless defined $content;
    open FILE, ">$file" or &debug("can't open $file\n") and return 0;
    print FILE $content;
    close FILE;
    return 1;
}


sub get_google_page {
    # @_ = $search_term, $mature, $startnum
    #
    # searches google images for $search_term, starting at result number
    # $startnum and returns a hash of { $url => [$w, $h] }
    #
    # if $mature is true, mature content is "ok"
    #
    my ($q, $mature, $start) = @_;
    $mature = ($mature) ? 'off' : 'on';
    
    # query google
    my $gurl = new URI('http://images.google.com/images');
    $gurl->query_form('q' => $q,
		      'start' => $start,
		      'imgsafe' => $mature,
		      'imgsz' => 'xxlarge',
		      );
    return get_page($gurl);
}

sub parse_google_has_next {
    # @_ = $doc
    #
    # pass it the body of a google results page
    # returns true if the page has a next link or not.
    my $doc = shift;

    return ($doc =~ m|nav_next\.gif|);
}

sub parse_google_img_links {
    # @_ = $doc
    #
    # pass it the body of a google results page and it pulls out hte links
    # returns a hash of { $url => [$w, $h] }

    my $doc = shift;
    my %results;

    while ($doc =~ m|(/imgres\?[^>]*)\"?|g) {
	my $uri = new URI($1);
	my %params = $uri->query_form();
	
	unless ($params{'imgurl'} =~ m|^[a-z]{1,5}://|) {
	    $params{'imgurl'} = "http://" . $params{'imgurl'};
	}
	$results{$params{'imgurl'}} = [$params{'w'}, $params{'h'}];
    }
    
    return %results;
}

sub show_next_img {
    # no input
    #
    # mv $nextimgfile to $imgfile, and display it.
    # (isn't stupid about a lack of next, or a failed move)
    # return true if it's all good, 0 if there are any problems
    &debug("no $nextimgfile\n") and return 0 unless -f $nextimgfile;
    &debug("$nextimgfile is not binary\n") and return 0 unless -B $nextimgfile;
    &debug("can't rename\n") and return 0 unless rename($nextimgfile, $imgfile);
	  
    &show_file($imgfile);
    return 1
}

sub show_file {
    # @_ = $file
    #
    # uses xv to display the file in the root background
    my $file = shift;
    system("$root_cmd $file");
    &debug("displayed $file in root window\n");
}

sub random_words {
    # @_ = $num
    #
    # returns an array of $num random words
    return map { random_word($_) } (1..$_[0]);
}

sub random_word {
    # returns a random word from the dictionary,
    # or undef if there was a problem
    #
    # will die if $dict can't be found.
    #
    # from webcollage

    die "no dictionary: $dict" unless -f $dict;
    
    my $word = 0;
    if (open (IN, "<$dict")) {
        my $size = (stat(IN))[7];
        my $pos = rand $size;
        if (seek (IN, $pos, 0)) {
            $word = <IN>;   # toss partial line
            $word = <IN>;   # keep next line
        }
        if (!$word) {
          seek( IN, 0, 0 );
          $word = <IN>;
        }
        close (IN);
    }

    return undef if (!$word);

    $word =~ s/^[ \t\n\r]+//;
    $word =~ s/[ \t\n\r]+$//;
    $word =~ s/ys$/y/;
    $word =~ s/ally$//;
    $word =~ s/ly$//;
    $word =~ s/ies$/y/;
    $word =~ s/ally$/al/;
    $word =~ s/izes$/ize/;
    $word =~ tr/A-Z/a-z/;

    # if it's got a space in it, quote it
    $word = qw("$word") if ($word =~ /\s/);
    
    return $word;
}


sub which {
    # from webcollage
    my ($prog) = @_;
    foreach (split (/:/, $ENV{PATH})) {
	if (-x "$_/$prog") {
	    return $prog;
	}
    }
    return undef;
}

sub sleep_diff {
    # @_ $old_time, $delay
    #
    # given a previous time, will make sure that at least $min_sec have
    # passed before it returns (by sleeping)
    my ($old_time, $delay) = @_;
    
    my $time_diff = (time - $old_time);
    my $time_delay = ($delay - $time_diff);
    $time_delay = (0 > $time_delay) ? 0 : $time_delay;
    &debug("waiting $time_delay seconds\n");
    sleep ($time_delay) if $time_delay;
    return 1;
}

sub debug {
    # prints it's args to $DEBUG_HANDLE only if $DEBUG_HANDLE is defined
    # allways returns true;
    print $DEBUG_HANDLE @_ if defined $DEBUG_HANDLE;
    return 1;
}

sub PORN_OK { 1 }
sub FAMILY_ONLY { 0 }

