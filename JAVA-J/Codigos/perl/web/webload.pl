#!/usr/bin/perl -w

# Load - current server load

print "Content-type: text/html\n\n";
require 'timelocal.pl';
$Now=time;
@Now=localtime(time);
$Now=&timelocal(@Now);

if ($ENV{PATH_INFO} eq "") {
        $Log = "/usr/local/apache/access_log"; 
} else {
        $Log=$ENV{PATH_INFO};
}

$Last=500;
$Steps=10;

open (h,"$Log") || die "Could not open access log ($!)\n";

print <<EOM;
<META HTTP-EQUIV="Refresh" CONTENT=15> 
<TITLE> $ENV{SERVER_NAME} HTTPD Load </TITLE>

<H1> Current HTTPD Load of 
<A HREF=/>$ENV{SERVER_NAME}</A><HR></H1>
EOM
    # print "Logfile: $Log\n";

    $from=0;
    LINE: while (<h>) {
        $line=$_;
        ($host, $rfc931, $authuser, $timestamp, $request, $status, $bytes) =
        /^(\S+) (\S+) (\S+) \[(.+)\] \"(.+)\" (\S+) (\S+)\s/;

        if (!($host && $rfc931 && $authuser && $timestamp 
                                            && $request && $status)) {
            # print(STDERR "$.:$line"); 
            $bad++;
            next LINE;
        }

        $TotalReq++;
        $TotalBytes+=$bytes;

        $_ = $timestamp;
        ($d,$m,$y,$hh,$mm,$ss) = m|(\d+)/(\w\w\w)/(\d+):(\d+):(\d+):(\d+).*|;
        $m=index("JanFebMarAprMayJunJulAugSepOctNovDec",$m)/3;
        $iTime= &timelocal($ss,$mm,$hh,$d,$m,$y-1900);
        if ($from == 0) {
                $from=$iTime;
                $diff=$Now-$iTime;
                $Resolution=int($diff/$Steps);
        }
        $diff=$Now-$iTime;
        $iDiff=int((1.0*$diff/$Resolution)+.5); 
        $req[$iDiff]++;
        $bytes[$iDiff]+=$bytes;
    }
    close h;


    # print $bad . "-". $TotalReq;
    print "<TABLE BORDER=0>\n";
    print "<TR><TH>seconds</TH><TH>reqests</TH><TH>Kbytes</TH><TH>requests chart</TH></TR>\n";
    for ($i=0; $i<=$#req; $i++) {
        $r = $i*$Resolution;
        $q = $req[$i];
        $w = $q*200.*$Steps/$TotalReq;
        $b = int ($bytes[$i]/1024);
        print "<TR ALIGN=RIGHT><TD>$r</TD><TD>$q</TD><TD>$b</TD><TD ALIGN=LEFT><HR WIDTH=$w SIZE=14 ALIGN=LEFT></TD></TR>\n";
A
    }
    print "</TABLE><PRE>\n";

    $fullDiff=($Now-$from);
    $fullDiffMin=$fullDiff/60;
    $fullRPH=$TotalReq/$fullDiff*3600.;
    $fullCPS=$TotalBytes/$fullDiff*11./1024.;
    $lastRPH=$req[0]/$Resolution*3600;
    $lastCPS=$bytes[0]/$Resolution*11./1024.;
    $lastDiffMin=$Resolution/60.;
    print "<B>";
    printf "Load in the last %4.1f minutes: %5.0f requests/day, %5.2f kbps.\n",
        $fullDiffMin,$fullRPH*24,$fullCPS;
    printf "Load in the last %4.1f minutes: %5.0f requests/day, %5.2f kbps.\n",
        $lastDiffMin, $lastRPH*24, $lastCPS;
    print `uptime`;
    #print "\nTail of server log:\n</B>";
    #print `tail $Log`;
    print <<EOM;
</B></PRE>
<HR>
EOM
