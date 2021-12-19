# $Id: StringBuffer.pm,v 1.1 2000/04/03 22:53:04 kayos Exp $
package StringBuffer;


sub TIEHANDLE {
    my ($class, $c) = @_;
    return bless(\$c,$class);
}

sub PRINT {
    my ($self) = shift;
    ${$self} .= join('', @_);
}

sub PRINTF {
    my ($self) = shift;
    my ($fmt) = shift;
    ${$self} .= sprintf($fmt, @_);
}       

#   sometimes Perl wants it...
sub DESTROY { };

1;

