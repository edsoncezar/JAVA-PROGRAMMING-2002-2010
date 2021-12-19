#!/usr/bin/perl -w
use strict;
use File::stat;

my(%files, $create_date, $file, $current_date, $code);
my($hometime, $log, $worktime, $location, $url, $set_time);
my($home_image, $work_image, $code_color);

# ------------------------------------------------
# Script created by David Wakeman, 2001.
#
# I have this setup to use the /var/www/images dir
# for my cam images, and the /var/www dir to use
# for the text files in my server-side includes.
#
# Test to see if the most recent image of your 
# specified images (%files hash) is newer than 
# your specified amount of time ($set_time).  If
# it is, that is the current image displayed on
# your cam, if not, the camera is offline.
#
# If you don't have SSI enabled in your Apache
# configs, go to apache.org and read their Docs
# to learn how it works, and how to enable it.
# ------------------------------------------------


# -----------------------------------------------
#             START OF CONFIG AREA
# -----------------------------------------------

# $code is used for the top part of my cam where
# you see: $cam = ( work | home | offline );
# My index page has this for that line:
# $cam = ( <!--#include virtual="code.txt"--> );
# It basically highlights the current location
# in whatever you put for $code_color below.

$code = "/var/www/code.txt";


# Put the highlight color of your choice here.
# Default = orange.

$code_color = "#F07800";


# $url is used for the java applet running the
# cam refresh on my page.  The java applet code's
# param name="url" value="" uses SSI as above to
# include the correct URL of The Moment (tm).

$url = "/var/www/url.txt";


# $cam is used for the blurb under my cam that
# says ".. camera image is from $location ..".
# Again, this uses SSI from the index page.

$file = "/var/www/cam.txt";


# $log = logfile - duh.

$log = "/home/iago/camlog";


# Here is the location of your work and image
# files.

$home_image = "/var/www/images/home.jpg";
$work_image = "/var/www/images/work.jpg";


# Here is the amount of time you want to test 
# your most current file against.  Value is 
# in seconds.

$set_time = 600;

# -----------------------------------------------
#              END OF CONFIG AREA
# -----------------------------------------------

$current_date = time;	# epoch time
$create_date = 0;
$worktime = 0;
$hometime = 0;
$location = "";

%files =
(
	home	=>	"$home_image",
	work	=>	"$work_image",
);


$hometime = stat($files{home})->ctime;		# This checks each file for current
$worktime = stat($files{work})->ctime;		# epoch time.


# This figures out if the newest file is newer than
# our $set_time, if not - cam is off.
# --------------------------------------------------

if ($worktime > $hometime && $current_date - $worktime < $set_time)	
{
	$location = "work";		# dood, i'm at work. blah
	adjust_cam($location);
}
elsif ($hometime > $worktime && $current_date - $hometime < $set_time)
{
	$location = "home";		# dood! i'm at home! weee
	adjust_cam($location);
}
else
{
	$location = "offline";	# dood, i'm hiding...
	adjust_cam($location);
}


# This sub changes all our SSI txt files needed
# for the cam applet.
# -----------------------------------------------

sub adjust_cam
{
	open(FILE, "+>$file") || die "can't open file: ($!)\n";
	if ($location eq "home" || $location eq "work")
	{
		print FILE ".. current image is from <b>$location</b> ..";
	}
	else
	{
		print FILE ".. camera is currently offline ..";
	}
	open(URL, "+>$url") || die "can't open file: ($!)\n";
	print URL "http://www.gibfest.org/images/$location.jpg";
	open(CODE, "+>$code") || die "can't open file: ($!)\n";
	if ($location eq "home")
	{
		print CODE "work | <font color=\"$code_color\">$location</font> | offline";
	}
	elsif ($location eq "work")
	{
		print CODE "<font color=\"$code_color\">$location</font> | home | offline";
	}
	else
	{
		print CODE "work | home | <font color=\"$code_color\">$location</font>";
	}
}

close(FILE);
close(URL);
