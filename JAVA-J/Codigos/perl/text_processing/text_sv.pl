package Text::xSV;
$VERSION = 0.03;
use strict;
use Carp;

sub bind_fields {
  my $self = shift;
  my %field_pos;
  foreach my $i (0..$#_) {
    $field_pos{$_[$i]} = $i;
  }
  $self->{field_pos} = \%field_pos;
}

sub bind_header {
  my $self = shift;
  $self->bind_fields($self->get_row());
  delete $self->{row};
}

sub extract {
  my $self = shift;
  my $row = $self->{row} or confess("No row found (did you call get_row())?");
  my $lookup = $self->{field_pos}
    or confess("Can't find field info (did you bind_fields or bind_header?)");
  my @data;
  foreach my $field (@_) {
    if (exists $lookup->{$field}) {
      push @data, $row->[$lookup->{$field}];
    }
    else {
      my @allowed = sort keys %$lookup;
      confess(
        "Invalid field $field for file '$self->{filename}'.\n" .
        "Valid fields are: (@allowed)\n"
      );
    }
  }
  return wantarray ? @data : \@data;
}

# Private block for shared variables in a small "parse engine".
# The concept here is to use pos to step through a string.
# This is the real engine, all else is syntactic sugar.
{
  my ($self, $fh, $line);

  sub get_row {
    $self = shift;
    delete $self->{row};
    $fh = $self->{fh};
    defined($line = <$fh>) or return;
    chomp($line);
    my @row = _get_row();
    $self->{row} = \@row;
    return wantarray ? @row : [@row];
  }

  sub _get_row {
    my @row;
    my $q_sep = quotemeta($self->{sep});
    my $match_sep = qr/\G$q_sep/;
    my $start_field = qr/\G(")|([^"$q_sep]*)/;

    # This loop is the heart of the engine
    while ($line =~ /$start_field/g) {
      if ($1) {
        push @row, _get_quoted();
      }
      else {
        push @row, $2;
      }
      my $pos = pos($line);
      if ($line !~ /$match_sep/g) {
        if ($pos == length($line)) {
          return @row;
        }
        else {
          my $expected = "Expected '$self->{sep}'";
          confess("$expected at $self->{filename}, line $., char $pos");
        }
      }
    }
    confess("I have no idea how parsing $self->{filename} left me here!");
  }

  sub _get_quoted {
    my $piece = "";
    while ($line =~ /\G((?:[^"]|"")*)/g) {
      $piece .= $1;
      if ($line =~ /\G"/g) {
        $piece =~ s/""/"/g;
        return $piece;  # EXIT HERE
      }
      else {
        # Must be at end of line
        $piece .= $/;
        defined($line = <$fh>) or
          confess("File $self->{filename} ended inside a quoted field");
        chomp($line);
      }
    }
    confess("I have no idea how parsing $self->{filename} left me here!");
  }
}

sub new {
  my $self = bless ({}, shift);
  my @fields = qw(filename fh filter sep);
  # args, required, optional, defaults
  @$self{@fields} = proc_args( +{ @_ },  [], [@fields],  { sep => ',' });
  if (defined($self->{filename}) and not defined($self->{fh})) {
    $self->open_file($self->{filename});
  }
  $self->set_sep($self->{sep});
  return $self;
}

sub open_file {
  my $self = shift;
  my $file = $self->{filename} = shift;
  my $fh = do {local *FH}; # Old trick, not needed in 5.6
  open ($fh, "< $file") or confess("Cannot read '$file': $!");
  $self->{fh} = $fh;
}


# See node 43323 - this could be in a module but I didn't want to
# create external dependencies.
sub proc_args {
  my $args = shift;
  my $req = shift;
  my $opt = shift || [];
  my $default = shift || {};
  my @res;
  foreach my $arg (@$req) {
    if (exists $args->{$arg}) {
      push @res, $args->{$arg};
      delete $args->{$arg};
    }
    else {
      confess("Missing required argument $arg");
    }
  }
  foreach my $arg (@$opt) {
    if (exists $args->{$arg}) {
      push @res, $args->{$arg};
      delete $args->{$arg};
    }
    else {
      push @res, $default->{$arg};
    }
  }
  if (%$args) {
    my $bad = join ", ", sort keys %$args;
    confess("Unrecognized arguments: $bad\n");
  }
  else {
    return @res;
  }
}

sub set_fh {
  $_[0]->{fh} = $_[1];
}

sub set_filename {
  $_[0]->{filename} = $_[1];
}

sub set_sep {
  my $self = shift;
  my $sep = shift;
  if (1 == length($sep)) {
    $self->{sep} = $sep;
  }
  else {
    confess("The separator '$sep' is not of length 1");
  }
}

1;

__END__
=head1 NAME

Text::xSV

=head1 EXAMPLE

  use Text::xSV;
  my $csv = new Text::xSV;
  $csv->open_file("foo.csv");
  $csv->bind_header();
  while ($csv->get_row()) {
    my ($name, $age) = $csv->extract(qw(name age));
    print "$name is $age years old\n";
  }

=head1 ABSTRACT

This module is for reading character separated data.  The most common
example is comma-separated.  However that is far from the only
possibility, the same basic format is exported by Microsoft products
using tabs, colons, or other characters.

The format is a series of rows separated by returns.  Within each row
you have a series of fields separated by your character separator.
Fields may either be unquoted, in which case they do not contain a
double-quote, separator, or return, or they are quoted, in which case
they may contain everything, and will pair double-quotes.

People usually naively solve this with split.  A next step up is to
read a line and parse it.  Unfortunately this choice of interface
(which is made by Text::CSV on CPAN) makes it impossible to handle
returns embedded in a field.  Therefore you may need access to the
whole file.

This module solves the problem by creating a CSV object with access to
the filehandle, if in parsing it notices that a new line is needed, it
can read at will.

=head1 DESCRIPTION

First you set up and initialize an object, then you read the CSV file
through it.  The creation can also do multiple initializations as
well.  Here are the available methods

=over 4

=item C<new>

This is the constructor.  It takes a hash of optional arguments.
They are the I<filename> of the CSV file you are reading, the
I<fh> through which you read, and the one character I<sep> that
you are using.  If the filename is passed and the fh is not, then
it will open a filehandle on that file and sets the fh accordingly.
The separator defaults to a comma.

=item C<set_filename>

=item C<set_fh>

=item C<set_sep>

Set methods corresponding to the optional arguments to C<new>.

=item C<open_file>

Takes the name of a file, opens it, then sets the filename and fh.

=item C<bind_fields>

Takes an array of fieldnames, memorizes the field positions for later
use.  C<bind_headers> is preferred.

=item C<bind_headers>

Reads a row from the file as a header line and memorizes the positions
of the fields for later use.  File formats that carry field information
tend to be far more robust than ones which do not, so this is the
preferred function.

=item C<get_row>

Reads a row from the file.  Returns an array or reference to an array
depending on context.  Will also store the row in the row property for
later access.

=item C<extract>

Extracts a list of fields out of the last row read.

=back

=head1 BUGS

When I say single character separator, I mean it.

Performance could be better.  That is largely because the API was
chosen for simplicity of a "proof of concept", rather than for
performance.  One idea to speed it up you would be to provide an
API where you bind the requested fields once and then fetch many
times rather than binding the request for every row.

Also note that should you ever play around with the special variables
$`, $&, or $', you will find that it can get much, much slower.  The
cause of this problem is that Perl avoids calculating those on a match
unless it has seen one of those.  This does many, many matches.
