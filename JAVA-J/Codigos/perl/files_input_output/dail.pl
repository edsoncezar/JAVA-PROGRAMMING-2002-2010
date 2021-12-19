 #!/usr/bin/perl
    use File::Find;
    use vars qw($yesterday @files);
    my $basedir = $ARGV[0];
    my $name = $ARGV[1];
    usage() unless $name;
    my $now = time;
    $yesterday = $now - 86400;
    my @date=localtime($now);
    $date[4]++; $date[5]+=1900;
    my $date_stamp = '_' . $date[3] . '_' . $date[4] . '_' . $date[5];
    $name .= $date_stamp;
    find (\&process, $basedir);
    my $filelist = join ' ', @files;
    `tar -zcf $name.tar.gz $filelist`;
    sub process	{
    	my $mtime = (stat($File::Find::name))[9];
    	return unless $mtime > $yesterday && ! -d $File::Find::name;
    	# print "$File::Find::name\n";
    	push @files, $File::Find::name;
    } # End sub process
    sub usage	{
    	print qq~
    dailydiff DIRECTORY NAME_OF_RESULT_FILE
    Generates a .tar.gz file at the specified location with all files
    that have been modified, in the specified directory, in the last
    24 hours.	
    ~;
    	exit(0);
    } # End sub usage
    =head1 NAME
    dailydiff - Creates a tar file of all the files in a particular directory
    that have been modified in the last 24 hours.
    =head1 USAGE
    dailydiff DIRECTORY RESULT_FILE
    The specified directory is searched, and the found files are added
    to a gzipped tar file called RESULT_FILE_xx_xx_xxxx.tar.gz, where
    xx_xx_xxxx is today's date.
    You can run this by cron every night to get a snapshot of what you
    changed that day. Combined with nightly backups, this can give you
    a very accurate idea of what you modified.
    Use this as a fill-in until you get a real source control product
    in place!
    =head1 AUTHOR
    Rich Bowen <rich@cre8tivegroup.com>
    =head1 README
    dailydiff - Creates a tar file of all the files in a particular directory
    that have been modified in the last 24 hours.
    =head1 PREREQUISITES
    	File::Find
    	tar and gzip
    =pod SCRIPT CATEGORIES
    UNIX/System_administration

