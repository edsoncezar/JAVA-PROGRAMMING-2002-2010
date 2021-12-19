#!/usr/bin/perl -Tw
#
# Home page manager -- hpage.cgi version 0.5 September 17, 2000
#
# This program is copyright 2000 by Curtis A. Poe (ovid@easystreet.com or poec@yahoo.com).
# This code may be copied, reused, or distributed under the same terms as Perl itself so
# long as this copyright notice is included.
#
# There are no warranties, expressed or implied for this code and you use it at your 
# sole risk and discretion.
# 
# This code should be considered beta quality at best.  I have tried to only use modules
# included in the standard distribution, so this should run "as is".  A flat file is used
# as the database to ensure cross-platform compatibility.
#
# To use, do the following:
# 1.  Add program to a directory that allows CGI scripts and set permissions as appropriate.
# 2.  Create an empty file with the same path and name as the $url_data variable.
# 3.  Set permissions on this file as read/write for your CGI script.
# 4.  Set the CGI script as the home page for your browser.
#
# The first time this script runs, if the steps above were followed, it will automatically
# take you to an update page where you may add or delete URLs as appropriate.  To access that
# page in the future, use http://yourserver/path/to/script/hpage.cgi?process=update
#
# Some things to do in the future:
#
# 1.  Add support for Text::CSV (people should really use this anyway)
# 2.  Add a description field to display instead of the URL (long urls make the table ugly)
# 3.  Allow people to customize the colors, fonts, etc. of the update table (low on the list)
# 4.  Have the very first page show up in a frame with a thin top frame for "update urls" or 
#     "make top frame go away"
# 5.  Cookie support for modest security.  If cookie disappears, a login screen appears.  This
#     is also low priority -- who cares if someone messes with your home page?
# 6.  Add "all day" and "every day" choices for home pages
# 7.  The user interface is pitiful and needs to be improved

use strict;

use CGI;
use URI::Escape;

my ($day, $time) = (split /\s|:/, localtime)[0,3];
my @weekday   = qw(Sun Mon Tue Wed Thu Fri Sat);
my $day_regex = join '|', @weekday;
my $separator = "::"; # be careful not to pick something with a special meaning in a regex
                      
my $query = new CGI;
my (%url, $now, $default_url);
my $url_data = 'urldata.dat'; # set this to an appropriate path and name

read_data();

# we are testing for two modes here.  If it's "update", go to the update page.
# if it's "add", we're at the update page and are adding a new URL.

if (defined $query->param("process")) {
    if ($query->param("process") eq "update") {
        print_form();
    } elsif ($query->param("process") eq "add") {
        add_data();
        print_form();
    }
    exit();
} else {
    go_homepage();
}

sub read_data {
    # sample %url hash:
    # %url = (Sun => 
    #                 { 7  => "http://www.perlmonks.com/",
    #                   17 => "http://www.slashdot.com/" },
    #         Wed =>
    #                 { 9  => "http://www.yahoo.com/",
    #                   15 => "http://www.superbad.com/",
    #                   23 => "http://www.easystreet.com/~ovid/" },
    #         Fri => 
    #                 { 16 => "http://www.slashdot.com/" },
    #        );
    # In the above example, for any day that is not listed gets the $default_url.
    # If the day is listed, it will get the default URL for any time before the 
    # first time, and after that, will get the URL for the timeframe specified
    # until the next time specified, or until the end of day.
    # For example, Wednesday, at 1:00 PM, the browser will be send to Yahoo!

    if (!(-e "$url_data")) {
        first_run(0);
    } else {
        open DATA, "< $url_data" or die "Could not open $url_data: $!\n";

        $default_url = <DATA>;
        chomp $default_url;

        # If default url is empty, close the file, initialize it, and reopen it.
        if ($default_url =~ /^\s*$/) {
            close DATA or die "Could not close $url_data: $!\n";
            first_run(1);
            open DATA, "< $url_data" or die "Could not open $url_data: $!\n";
        }

        while (<DATA>) {
            my ($day, $hour, $url) = split(/$separator/, $_, 3);
            if (defined $url) {
                chomp $url;
                $url{$day}{$hour} = $url;
            }
        }
        close DATA or die "Could not close $url_data: $!\n";
    }
}

sub add_data {
    # This sub takes all data from the %url hash and writes it to $url_data
    # The first line of $url_data is the $default_url

    my ($daykey, $hourkey);

    get_form_data();

    open DATA, "> $url_data" or die "Can't open $url_data for writing: $!\n";
    my $safe_default = uri_escape($default_url); 
    print DATA $safe_default . "\n";

    foreach $daykey (keys %url) {
        foreach $hourkey (keys %{$url{$daykey}}) {
            if (defined $url{$daykey}{$hourkey}) {
                my $unsafe_url = $url{$daykey}{$hourkey};
                my $safe_url   = uri_escape($unsafe_url) ;
                print DATA $daykey . $separator . $hourkey . $separator. $safe_url . "\n";
            }
        }
    }
    print DATA "\n";
    close DATA or die "Could not close $url_data: $!\n";

    go_homepage() if defined $query->param("done");
}

# The following sub sends the browser to the appropriate URL, or the default url
# if no URL is found in the hash for the day/hour listed.

sub go_homepage {
    # The following line greps for values <= $now, sorts them, and takes the last value.
    # Thanks to [Russ] for this one

    $now = (sort grep {$_ <= $time} keys %{$url{$day}})[-1];

    if (defined $now && exists $url{$day}{$now}) {
	    print $query->redirect($url{$day}{$now});
    } else {
	    print $query->redirect($default_url);
    }
    exit();
}

# If the data file isn't found, the script assumes this to be the first run,
# writes the default url to the data file and then sets process to update
# to put the user at the update screen.  (Default URL is determined by the datafile
# if it exists.  Therefore, perlmonks is only the default URL on a first run.

sub first_run {
    my $file_exists = shift;

    if (! $file_exists) {
        print $query->header;
        print $query->start_html;
        print "<H3>Please create a file with the path and name of '$url_data'.</H3><P>";
        print 'Be aware that your server may require that you set appropriate<BR>';
        print 'read/write permissions for this file prior to your being able<BR>';
        print 'to use it for this program.';
        print $query->end_html;
        exit();
    } else {
        $default_url = "http://www.perlmonks.org/";
        open  DATA, ">$url_data"       or die "Could not open $url_data for writing: $!\n";
        print DATA $default_url . "\n" or die "Could not print to $url_data: $!\n";
        close DATA                     or die "Could not close $url_data: $!\n";
        $query->param("process", "update");
    }
}

# This reads the data submitted by the form and writes it to %url. 

sub get_form_data {
    $query->delete("process"); 

    my @keys = $query->param;
    $default_url = $query->param("default_url") if defined $query->param("default_url");
    foreach my $key (@keys) {
        my $value = $query->param($key);

        # This checkbox is for deleting the URL
        if ($key =~ /^CHK,($day_regex),(2[0-3]|1?[0-9])$/o) {
            my ($day, $hour) = ($1, $2);

            delete $url{$day}{$hour} if exists $url{$day}{$hour};
        }

        # The following is for adding a URL.
        if ($key eq "new_url" && $query->param("new_url") !~ /^\s*$/) {
            $query->param("day")  =~ /^($day_regex$)/o    or die "Bad data in day\n";
            my $day               = $1;
            $query->param("time") =~ /^(2[0-3]|1?[0-9])$/ or die "Bad data in time\n";
            my $hour              = $1;
            my $url               = $query->param($key);

            $url =~ s/\s*$//; # get rid of spaces at the end of new URL
            $url{$day}{$hour} = $url;
        }
    }
}

# This creates the update Web page in the browser

sub print_form {
    my ($this_day, $i, $url_counter, @time_display);

    for (0..23) {
        if ($_ == 0) {
            $time_display[$_] = 'Midnight';
        } elsif ($_ == 12) {
            $time_display[$_] = 'Noon';
        } elsif ($_ > 12) {
            my $hour          = $_ - 12;
            $time_display[$_] = "$hour:00 PM";
        } else {
            $time_display[$_] = "$_:00 AM";
        }
    }
    print $query->header(-expires=>'now');
    print $query->start_html(-title=>'hpage.cgi Update Form', 
                             -author=>'ovid@easystreet.com', 
                             -BGCOLOR=>'#6600CC');

    print << "[END]";
<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#000000" WIDTH="600">
  <TR>
    <TD>
      <FORM ACTION="hpage.cgi" METHOD="POST">
	  <INPUT type=hidden NAME="process" VALUE="add">
      <TABLE CELLPADDING="4" CELLSPACING="1" BORDER="0" WIDTH="100%">
        <TR BGCOLOR="#000000">
          <TD COLSPAN="4">
            <FONT COLOR="#FFFFFF"><STRONG>Defaults</STRONG></FONT>
          </TD>
        </TR>           
        <TR BGCOLOR="#99CCCC">
          <TD COLSPAN="4">
            <INPUT TYPE="TEXT" MAXLENGTH="100" SIZE="40" NAME="default_url" VALUE="${default_url}">&nbsp;&nbsp;Default URL
          </TD>
        </TR>
        <TR BGCOLOR="#000000">
          <TD COLSPAN="4">
            <FONT COLOR="#FFFFFF"><STRONG>Home Page Data</STRONG></FONT>
          </TD>
        </TR>
        <TR BGCOLOR="#99CCCC">
          <TH>Delete</TH><TH>Day</TH><TH>Time</TH><TH>URL</TH>
        </TR>
        <TR BGCOLOR="#99CCCC">
[END]

    foreach $this_day (@weekday) {
        if (defined $url{$this_day}) {
    		foreach (sort {$a <=> $b} keys %{$url{$this_day}}) {
	    	    print "<TR BGCOLOR=\"#99CCCC\">\n";
		        print "<TD><INPUT TYPE=CHECKBOX NAME=\"CHK,${this_day},${_}\"></TD>\n";
    			print "<TD>$this_day</TD>\n";
	    		print "<TD>$time_display[$_]</TD>\n";
		    	print "<TD><A HREF=\"$url{$this_day}{$_}\" TARGET=\"_blank\">$url{$this_day}{$_}</A></TD>\n";
			    print "</TR>\n";
	    		$url_counter++;
		    }
    	}
    }
	unless ($url_counter) {
	    print "<TR BGCOLOR=\"#99CCCC\"><TD COLSPAN=\"4\"><B>No homepages have been specified.</B></TD></TR>\n";
    }
	print << "[END]";
        <TR BGCOLOR="#000000">
          <TD COLSPAN="4">
            <FONT COLOR="#FFFFFF"><STRONG>Add a Home Page</STRONG></FONT>
          </TD>
        </TR>
      </TABLE>
      <TABLE CELLPADDING="4" CELLSPACING="1" BORDER="0" WIDTH="100%">
        <TR BGCOLOR="#99CCCC">
          <TH>Day</TH><TH>Time</TH><TH>URL</TH>
        </TR>
        <TR BGCOLOR="#99CCCC">
          <TD>
            <SELECT NAME="day">
[END]
    foreach (@weekday) {
	    print "<OPTION>${_}\n";
	}
	print << "[END]";
            </SELECT>
          </TD>
          <TD>
            <SELECT NAME="time">
[END]
    for (0..23) {
        print "<OPTION VALUE=$_>$time_display[$_]</OPTION>\n";
    }
	print << "[END]";
            </SELECT>
          </TD>
          <TD>
            <INPUT TYPE="TEXT" MAXLENGTH="100" SIZE="35" NAME="new_url">
          </TD>
        </TR>
        <TR BGCOLOR="#000000">
          <TD COLSPAN="3">
            <FONT COLOR="#FFFFFF"><STRONG>Are you done yet?</STRONG></FONT>
          </TD>
        </TR>
        <TR BGCOLOR="#99CCCC">
          <TD COLSPAN="3">
            <INPUT TYPE="CHECKBOX" NAME="done"> Click here when finished. (This will send you to your homepage)
          </TD>
	</TR>
        <TR>
          <TD COLSPAN="3">
            <FONT COLOR="#FFFFFF"><STRONG>Buttons (duh!)</STRONG></FONT>
          </TD>
        </TR>
        <TR BGCOLOR="#99CCCC">
          <TD COLSPAN="2">
            <INPUT TYPE="submit" VALUE="Stumbit">
          </TD>
          <TD ALIGN=RIGHT>
            <INPUT TYPE="reset" VALUE="Clear Form">
          </TD>
        </TR>
      </TABLE>
    </TD>
  </TR>
</TABLE>
</BODY>
</HTML>
[END]
}
