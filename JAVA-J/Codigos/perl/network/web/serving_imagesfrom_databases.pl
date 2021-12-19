#!C:/perl/bin/perl.exe -wT

use strict;
use DBI qw(:sql_types);

# This program works as follows:
# In a Web document, if an image is not found, the server calls this script
# The script checks the $ENV{'QUERY_STRING'} for the image name.
# If the image name is not in the form /([a-zA-z])(\d+)\.($types)/,
# the script exits and a broken image link will result, letting the developer
# know that there's a typo in the image format.
#
# If the image is in the proper form, the script queries the appropriate
# database to see if the image is there.  If it is, the image is served.
#
# If it is not, the appropriate noImage path is pulled from %catData 
# and this image is served instead, letting the developer know that the
# image format is correct, but an image probably needs to be added to
# the database.

my ($imageType, $imageID, $types, $extension, $table, $field, $noImage, $image,
	%mimeType,  %catData);

# Keys should mirror extensions and values should be the proper MIME type
# Currently, we only use gifs and jpegs.

%mimeType = (jpg  => 'jpeg',
			 gif  => 'gif');

# This creates an extension alternation to be used in a regex

$types = join '|', keys %mimeType;

# The catData hash fields are as follows:
#
# primary key -- This corresponds to $imageType
# database    -- Database image is in
# table       -- Table in database
# field       -- field in table where image is stored
# noImage     -- Path to image to display if no image found in database
# mimetype    -- If an explicit mime type is not listed, this represents
#                the fieldname in table that the mime type is stored in
#                This probably will not be used, but is included on the
#                off chance that this is necessary in the future.
# Thus, if in our UFMCatalog database, in table _5, we have an image with
#  an ID of 4392, a proper request for it might be:
# <img src="/images/category4392.jpg" height=215 width=131 alt="Some image">

%catData = (category     =>
						{database => 'UFMCatalog',
						 table    => '_5',
						 field    => 'image',
						 noImage  => '../images/no-image-long.jpg',
						 mimetype => 'jpeg'},
			productLarge =>
						{database => 'UFMCatalog',
						 table    => '_4',
						 field    => 'largeImage',
						 noImage  => '../images/no-image-big.jpg',
						 mimetype => 'jpeg'},
			productSmall =>
						{database => 'UFMCatalog',
						 table    => '_4',
						 field    => 'smallImage',
						 noImage  => '../images/no-image-small.jpg',
						 mimetype => 'jpeg'},
			logo         =>
						{database => 'ECinterface',
						 table    => 'logo',
						 field    => 'logo',
						 noImage  => '../images/1xshim.gif',
						 mimetype => 'format'}
			);

$ENV{'QUERY_STRING'} =~ m!([a-zA-Z]+)(\d+)\.($types)$!;

# Creates a "broken image" if the form of the image request is wrong

$imageType = defined $1 ? $1 : exit;
$imageID   = defined $2 ? $2 : exit;
$extension = defined $3 ? $3 : exit;

# Creates a "broken image" if $imageType is not in %catData

if (! exists $catData{$imageType}{field} ) {
	exit;
}

$image = getImage();
$image = getNoImage() if ! defined $image;

print "Content-type: image/$mimeType{$extension}\n\n";
print $image;

sub getImage {
	my $image;
	my $dbh = DBI->connect("dbi:ODBC:ourdb", 'ourdb', 'youwish', 
				{RaiseError => 1}) or die DBI->errstr;

	$dbh->{LongReadLen} = 200000;
	$dbh->{LongTruncOk} = 1;

	my $sql = 	"SELECT $catData{$imageType}{field} " .
				"FROM $catData{$imageType}{database}..$catData{$imageType}{table} " . 
				"WHERE id = $imageID";

	my $sth = $dbh->prepare($sql);

	$sth->execute;
	$image = $sth->fetchrow_array();
	$sth->finish();

	$dbh->disconnect;
	return $image;
}

sub getNoImage {
	my ($chunk, $image);

	open IMAGEFILE, "<$catData{$imageType}{noImage}" 
		or die "Cannot open $catData{$imageType}{noImage}: $!\n";
	binmode IMAGEFILE;
	while (read(IMAGEFILE, $chunk, 1024)) {
		$image .= $chunk; 
	}
	close IMAGEFILE;
	
	# Since we're not getting the image we expected, reset the extension to 
	# the noImage extension to guarantee the correct MIME type is sent.
	
	$catData{$imageType}{noImage} =~ /\.(\w+)$/;
	$extension = $1;
	
	return $image;
}
