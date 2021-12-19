#!/usr/bin/perl
#
#	tidyhome.perl
#
#	Perl script to parse a ~/.tidy file and move files from
#	user home directory to thier tidied/<category> dir. 
#
#	(c) 2000 Billy Allan.
#	Released under the terms of the GNU General Public License.
#	See http://www.gnu.org for details.
#
#	Version 0.1 Fri Nov 24 19:43:39 GMT 2000
#	Version 0.2 Sun Jan 14 15:16:47 GMT 2001
#	Version 0.3 Mon Mar 19 18:48:52 GMT 2001
#
#	Usage:
#	tidyhome.perl [username] [username]...
#
#	If no username is supplied, the current UID is assumed (and warned)
#
#	Format of ~/.tidy file :
#	<basedir>
#	<dir-type> <extension> [<extension> <extension>...]
#	<dir-type> <extension> ...
#	...
#
#	For example :
#
#	tidied
#	pictures jpg xcf gif png tiff
#	docs/web html htm
#	docs/text txt doc
#	movies avi mpg fli mpeg mov
#
#	The files will be moved into a folder called :
#		~/<basedir>/<dir-type>
#	****which must exist already!****
#

$basedir = 'tidied';		# default
$config = '.tidy';

#######################

if ($#ARGV < 0)
{
	warn("No username given - defaulting to current username\n");
	@details = getpwuid($<);	# get the current user-account
	@userlist = $details[0];	# get the username
}
else
{
	@userlist = @ARGV;
}

#######################

foreach $user (@userlist)
{
	if ($homedir = (getpwnam("$user"))[7])
	{
		if (! tidy($homedir))
		{
			warn("Could not tidy $user homedir...\n");
		}
	}
}

1;

#######################

sub tidy					# tidy($homdir)
{
	my $home = $_[0];
	if (open(CONFIG,"<$home/$config"))
	{
		$basedir = <CONFIG>;
		chomp($basedir);
		while (<CONFIG>)
		{
			chomp();
			my ($key,@types) = split(/ /);
			if ($key =~ m/[^0-9a-zA-Z_\-\/]/)		# only allow "safe" directory names
			{
				print "Invalid directory in .tidy file...\n";
				next();
			}
			foreach my $type (@types)
			{
				chomp($type);
				my @list = glob("$home/*.$type");
				foreach my $file (@list)
				{
					my $result = rename($file,"$home/$basedir/$key");
				}
				$type = uc($type);
				@list = glob("$home/*.$type");
				foreach $file (@list)
				{
					$result = rename($file,"$home/$basedir/$key");
				}

			}
		}
		return 1;
	}
	else
	{
		return 0;
	}
}

