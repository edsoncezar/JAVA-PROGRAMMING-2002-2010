## Usage:
#
# EvalExecs($html_to_parse) <== Evaluates <!--#exec -->'s
#
# EvalIncludes($html_to_parse) <== Evaluates <!--#include -->'s
#

use Cwd;

sub EvalExecs {

my($cwd,$path,$html,$action,$mode,$file,$exec);

$cwd = getcwd();
if($^O =~ m/win/) {
	if(substr($cwd,-1,1) eq "\\") {$cwd = substr($cwd,0,(length($cwd)-1))}
} else {
	$cwd =~ s/\\/\//g;
	if(substr($cwd,-1,1) eq "/") {$cwd = substr($cwd,0,(length($cwd)-1))}
}
$path = $ENV{'DOCUMENT_ROOT'};
if($^O =~ m/win/) {
	if(substr($path,-1,1) eq "\\") {$path = substr($path,0,(length($path)-1))}
} else {
	$path =~ s/\\/\//g;
	if(substr($path,-1,1) eq "/") {$path = substr($path,0,(length($path)-1))}
}

$html = $_[0];

while($html =~ m/<!--#exec(.+)-->/) {
	$html =~ s/<__exec(.+)__>/<_INVALID_exec$1_INVALID_>/;
	$html =~ s/<!--#exec(.+)-->/<__exec$1__>/;
	$action = $1;

	if($action =~ m/cmd=/) {

		$mode = 1;
		if($action =~ m/cmd="(.+)"/) {
			$file = $1;
		}
		elsif($action =~ m/cmd='(.+)'/) {
			$file = $1;
		}

	} elsif($action =~ m/cgi=/) {

		$mode = 2;
		if($action =~ m/cgi="(.+)"/) {
			$file = $1;
		}
		elsif($action =~ m/cgi='(.+)'/) {
			$file = $1;
		}

	}

	if(!$file) {next}

	if($mode == 1) {
		$exec = `$file`;
	} else {
		$exec = `perl $file`;
	}

	$html =~ s/<__exec(.+)__>/$exec/g;
	return $html;
}
$html =~ s/<_INVALID_exec(.+)_INVALID_>/<!--#exec$1-->/g;
}

sub EvalIncludes {

my($cwd,$path,$html,$action,$mode,$file,$final,$include,@temp,@dir,@include);

$cwd = getcwd();
if($^O =~ m/win/) {
	if(substr($cwd,-1,1) eq "\\") {$cwd = substr($cwd,0,(length($cwd)-1))}
} else {
	$cwd =~ s/\\/\//g;
	if(substr($cwd,-1,1) eq "/") {$cwd = substr($cwd,0,(length($cwd)-1))}
}
$path = $ENV{'DOCUMENT_ROOT'};
if($^O =~ m/win/) {
	if(substr($path,-1,1) eq "\\") {$path = substr($path,0,(length($path)-1))}
} else {
	$path =~ s/\\/\//g;
	if(substr($path,-1,1) eq "/") {$path = substr($path,0,(length($path)-1))}
}

$html = $_[0];

while($html =~ m/<!--#include(.+)-->/) {
	$html =~ s/<__include(.+)__>/<_INVALID_include$1_INVALID_>/;
	$html =~ s/<!--#include(.+)-->/<__include$1__>/;
	$action = $1;

	if($action =~ m/file=/) {

		$mode = 1;
		if($action =~ m/file="(.+)"/) {
			$file = $1;
		}
		elsif($action =~ m/file='(.+)'/) {
			$file = $1;
		}

	} elsif($action =~ m/virtual=/) {

		$mode = 2;
		if($action =~ m/virtual="(.+)"/) {
			$file = $1;
		}
		elsif($action =~ m/virtual='(.+)'/) {
			$file = $1;
		}

	}

	if(!$file) {next}

	if($mode == 1) {
		if(-e "$cwd/$file") {$final = "$cwd/$file"}
		else {
			opendir(MAIN,"$cwd");
			@temp = readdir(MAIN);
			closedir(MAIN);

			foreach $listed (@temp) {
				if((-d "$cwd/$listed" && ($listed ne "." && $listed ne "..")) && (-e "$cwd/$listed/$file")) {$final = "$cwd/$listed/$file"; goto LAST}
				elsif(-d "$cwd/$listed" && ($listed ne "." && $listed ne "..")) {push(@dirs,$listed)}
			}

			foreach $dir (@dirs) {
				opendir(DIR,"$cwd/$dir");
				@temp = readdir(DIR);
				closedir(DIR);

				foreach $listed (@temp) {
					if(-d "$cwd/$dir/$listed" && -e "$cwd/$dir/$listed/$file") {$final = "$cwd/$dir/$listed/$file"; goto LAST}
					elsif(-d "$cwd/$dir/$listed") {push(@dirs,"$dir/$listed")}
				}
			}
		}
		LAST: if(!$final) {next}
	} else {
		$final = "$path/$file";
	}

	if(@include) {
		foreach $i (0..$#include) {
			shift(@include);
		}
	}

	open(INCLUDE,"$final");
	@include = <INCLUDE>;
	close(INCLUDE);

	$last = $include[-1];
	$include = '';

	foreach $i (@include) {
		chomp($i);
		if($i eq $last) {$include .= "$i";} else {$include .= "$i\n";}
	}

	$html =~ s/<__include(.+)__>/$include/g;
	return $html;
}
$html =~ s/<_INVALID_include(.+)_INVALID_>/<!--#include$1-->/g;
}
