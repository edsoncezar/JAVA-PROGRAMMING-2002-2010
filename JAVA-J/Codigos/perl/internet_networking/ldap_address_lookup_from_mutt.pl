#!/usr/bin/perl

use strict;
use Net::LDAP;

use constant HOST => 'ldap.mysite.com';
use constant BASE => 'cn=People, dc=MySite, dc=com';

{
  print "Searching database... ";
  my $name = shift || die "Usage: $0 filter\n";
  my $ldap = Net::LDAP->new( HOST, onerror => 'die' ) 
                || die "Cannot connect: $@";
  $ldap->bind() or die "Cannot bind: $@";
  my $msg = $ldap->search( base => BASE, 
                           filter => "(|(sn=*$name*)(givenName=*$name*))" );
  my @entries = ();
  foreach my $entry ( $msg->entries() ) {
    push @entries, 
        { email      => $entry->get_value( 'mail' ),
          first_name => $entry->get_value( 'givenName' ),
          last_name  => $entry->get_value( 'sn' ) };
  }
  $ldap->unbind();
  print scalar @entries, " entries found.\n";
  foreach my $entry ( @entries ) {
    print "$entry->{email}\t$entry->{first_name} $entry->{last_name}\n";
  }
}

