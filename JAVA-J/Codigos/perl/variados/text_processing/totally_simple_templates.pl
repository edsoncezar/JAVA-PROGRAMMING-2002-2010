#!/usr/bin/perl

# coming to your CPAN mirror soon
# http://www.pobox.com/~japhy/modules/
use DynScalar 'newTemplate';
use strict;
use vars '$data';  # has to be package var

my $template = newTemplate {
  my $out = << "END";

Hello, <b>$data->{USER}</b>.  You've been here
$data->{COUNT} times.

<br><br>

<ul>
END

  for (@{ $data->{NODES} }) {
    $out .= qq{<li> <a href="$_->{href}">$_->{name}</a>\n};
  }

  $out .= "</ul>\n";

  $out;
};

$data = getInfo(magically_determine_user());
print HTML_page($template);

