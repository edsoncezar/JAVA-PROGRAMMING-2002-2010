#! /usr/bin/perl -w
package fastread;
use strict;
use Compress::Zlib;


###############################################################################
#                     Private Methods and Attributes                          #
###############################################################################
{
    # Remainder holds the extra data past last full line found in data
    # segment.
    my $remainder;
    # Buffer is main data read in.
    my $buffer;
    # Number of bytes read for read request to file.
    my $bytesread;
    # Filehandle of the file being read by this object.
    my $filehandle;
    # Name of file being read.
    my $filename;
    # Debug flag, determines if debug messages are displayed.
    my $debug;
    
    sub _set_remainder
    {
	# Set the internal remainder buffer.
	my $self=shift @_;
	$remainder=shift @_;
	warn "Setting remainder to $remainder.\n" if $debug;
    }

    sub _get_remainder
    {
	# Return current value of block remainder.
	warn "Request for remainder received.  Val: $remainder\n" if $debug;
	$remainder;
    }

    sub _get_buffer_ref
    {
	# Return a reference to the buffer.
	\$buffer;
    }
    
    sub _query_debug
    {
	# Returns the debug flag setting.
	$debug;
    }

    sub _set_debug
    {
	# Debug set to non zero indicates debugging enabled.
	my $self=shift @_;
	$debug=shift @_;
	warn "Debug set to $debug\n" if $debug;
    }

    sub _set_file_name
    {
	# Sets the name of the file to be read.
	my $self=shift @_;
	$filename=shift @_;
	warn "Setting name to $filename\n" if $debug;
	
    }
    
    sub _openfile
    {
	die "Trying to open stream with no name!\n" if !$filename;
	warn "Opening $filename\n" if $debug;
	$filehandle=gzopen($filename,"rb") or die "Can't open $filename for reading.\n$!\n";
    }

    sub _genblock
    { 
	my $self=shift @_;
	my $pos;
	my $newremainder;
	die "No filehandle yet opened!\n" if !$filehandle;
	warn "Generating block of data. Size $self->{_buffersize}\n" if $debug;
	$bytesread=$filehandle->gzread($buffer,$self->{_buffersize});
	$pos=rindex($buffer,"\n",$bytesread);
	warn "Read $bytesread bytes of data.\n" if $debug;
	warn "Line terminator detected at pos $pos\n" if $debug;
	$newremainder=substr($buffer, $pos+1);
	$buffer=$remainder.substr($buffer,0,$pos+1);
	$remainder=$newremainder;
	warn "Remainder now set to $remainder.\n" if $debug;
	\$buffer;
    }

}


###############################################################################
#                         Public Methods                                      #
###############################################################################

sub new
{
    my ($class,%arg)=@_;
    $class->_set_file_name($arg{filename}) if $arg{filename};
    $class->_set_remainder("");
    bless{
	_buffersize => $arg{buffer} || 4096,
    }, $class;
    
}

sub open
{
    my ($self,%args)=@_;
    warn "Request to open file\n" if $self->_query_debug();
    my $filename=$args{filename};
    my $buffsize=$args{buffer};
    if ($buffsize)
	{
	    $self->{_buffersize}=$buffsize;
	}

    $self->set_file_name($filename) if $filename;
    $self->_openfile($filename);
}

sub read_block
{
    my $self=shift @_;
    my $blockref;
    $blockref=$self->_genblock();
    wantarray()?split("\n",${$blockref}):${$blockref};
}

sub set_buffer_size
{
    my $self=shift @_;
    my $size=shift @_;
    warn "Attempt to set null value to buffer size, ignoring.\n" if !$size;
    $self->{_buffersize}=$size?$size:$self->{_buffersize};
    warn "Buffersize now set to $self->{_buffersize}\n" if $self->_query_debug;
}

sub query_buffer_size
{
    my $self=shift @_;
    $self->{_buffersize};
}
sub set_file_name
{
    my $self=shift @_;
    my $name=shift @_;
    warn "Attempting to set filename to $name.\n" if $self->_query_debug;
    $self->_set_file_name($name);
}

sub set_debug
{
    my ($self,$debug)=@_;
    $self->_set_debug($debug);
}

sub get_block_ref
{
    my $self=shift@_;
    $self->_get_buffer_ref;
}

sub get_new_block_ref
{
    my $self=shift@_;
    $self->_genblock;
}
1;

