#!/usr/bin/perl -- -*-fundamental-*-

## Resume logger
## Greg Sabino Mullane, greg@turnstep.com, 0x14964AC8
## Note: your log format and mail program may vary:
## Try local 'sendmail' or use Mail::Mailer

use strict;

my($mail, $mailto, $mailfrom, $subject);
my($accesslog, $resumelog, $mydomain, $mypage);
my(@fresh, %seenit, %seenIP, %visits);
my($x);

$mail      = "/usr/bin/mail"; ## Paranoia is a Good Thing
$mailto    = "resume\@example.com";
$mailfrom  = qq["Abe Lincoln" <alincoln\@example.com>];
$subject   = "More resume nibbles";

$accesslog = "/foo/bar/logs/access_log";
$resumelog = "/home/alincoln/jobs/resume.log";
$mydomain  = "turnstep"; ## Use if server has >1 domain...
$mypage    = "/resume.html";

## Load our old data:
open(OLD, "+< $resumelog") || die "Could not open $resumelog: $!\n";
while(<OLD>) {
  $seenit{$_}++; ($x) = split(/ /,$_); $seenIP{$x}++;
}

## Anything new in the logs?
open(ACCESSLOG, "$accesslog") || die "Could not open $accesslog: $!\n";
while(<ACCESSLOG>) {
  ## Crude match: refine if other pages contain $mypage
  next unless m/^$mydomain (.*?) (.*$mypage.*\n)/;
  $seenit{"$1 $2"} || push(@fresh, "$1 $2") && $visits{$1}++;
}
close(ACCESSLOG);

## Bail if nothing new:
@fresh || exit; ## perl closes OLD for us. Isn't that nice?

## Add new entries to the end of OLD:
print OLD @fresh; close(OLD);

## Mail it to us:
open(MAILME, "| $mail -t $mailto") || die " Could not start $mail: $!\n";
print MAILME "From: $mailfrom\n";
print MAILME "Subject: $subject.\n\n";
for (@fresh) {
  ($x) = split(/ /, $_);
  $seenIP{$x} ||=0;
  print MAILME "\tThis time: $visits{$x} Prior: $seenIP{$x}\n$_\n\n";
}
close(MAILME);
exit;

