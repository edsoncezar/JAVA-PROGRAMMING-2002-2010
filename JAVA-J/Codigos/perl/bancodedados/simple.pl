#!/usr/bin/perl
    $file = shift;
    my $restart;
    print "SimpleDB Editor\n";
    print "Written by Verite Donato\n\n";
    if ($file eq "") {
    print "Filename: ";
    $file = <STDIN>;
    }
    while ( 1 ) {
    my $found = 0;
    my $newval;
    open (FileIn, $file) or print "Invalid filename\n" and die;
    print "Search string: ";
    chop ($search = <STDIN>);
    while ($dummy = <FileIn>) {
    chop ($dummy);
    ($field, $value) = split (getDelim($dummy), $dummy);
    if ($field eq $search) {
    	$found = 1;
    print "Search string found:\n";
    print "Field: $field\n";
    print "Value: $value\n";
    	print "New value: ";
    chop ($input = <STDIN>);
    	if ( $input ) {
    $dummy = change ( $dummy, $field, $input );
    		$newval = $input;
    	}
    	print "New field: ";
    chop ($input = <STDIN>);
    	if ( $input ) {
    changeField ($dummy, $input, $newval);
    	}
    
    print "Changes complete. Restart?\n";
    	$restart = <STDIN>;
    close (FileIn);
    	break;
    }
    }
    if ( $found eq 0 ) {
    print "Error: Search string not found in file!\n\n";
    	print "Restart? ";
    	$restart = <STDIN>;
    }
    unless ( $restart =~ /y/ or $restart =~ /Y/ ) {
    	print "Okay. Exiting . . .\n\n";
    	exit (1);
    }
    }
    sub changeField {
    $dummy = shift;
    $newfld = shift;
    $value = shift;
    my $buf;
    $delimStr = getDelim($dummy);
    open (temp, $file);
    while ($temp = <temp>) {
    chop ($temp);
    if ($temp eq $dummy) {
    $buf = $buf . "$newfld$delimStr$value\n";
    }
    else {
    $buf = $buf . "$temp\n";
    }
    }
    close (temp);
    open (temp, ">$file");
    print temp $buf;
    close (temp);
    }
    sub change {
    $dummy = shift;
    $field = shift;
    $newval = shift;
    my $retval;
    $delimStr = getDelim($dummy);
    open (temp, $file);
    while ($temp = <temp>) {
    chop ($temp);
    if ($temp eq $dummy) {
    $buf = $buf . "$field$delimStr$newval\n";
    	$retval = "$field$delimStr$newval";
    }
    else {
    $buf = $buf . "$temp\n";
    }
    }
    close (temp);
    open (temp, ">$file");
    print temp $buf;
    close (temp);
    return $retval;
    }
    sub getDelim {
    $line = shift;
    @chars = split ( '', $line );
    my $delim = "";
    my $flag = 0;
    for ( $i = 0; $i < @chars; $i++ ) {
    	if ( $chars[$i] eq ' ' ) {
    	$flag = 1;
    	}
    	if ( $chars[$i] ne ' ' ) {
    	$flag = 0;
    	}
    	if ( $flag ) {
    	$delim .= $chars[$i];
    	}
    }
    return $delim;
    }