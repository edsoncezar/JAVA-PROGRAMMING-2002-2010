use Net::FTP; 

my $ftp;
my $filename;
my @files = ('file1','file2');
my %servers = (
		'box1' => 'password',
		'box2' => 'password',
		);

chdir "path/where/you/want/the/files";

foreach (keys %servers) {
# line 16
	$ftp = Net::FTP -> new($_);
	print $ftp -> message(), "\n";
	$ftp -> login('Randal', $servers{$_});
	print $ftp -> message(), "\n";
	$ftp -> binary();
	print $ftp -> message(), "\n";
	$ftp -> cwd('/your/path');
	print $ftp -> message(), "\n";
	
	foreach $filename (@files) {
		$ftp -> get( "$filename", "$_$filename");
		print $ftp -> message(), "\n";
	}
print "Completed $_\n\n";
$ftp -> quit();
}

exit;

