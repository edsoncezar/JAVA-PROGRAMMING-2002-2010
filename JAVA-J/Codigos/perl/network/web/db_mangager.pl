#!/usr/local/bin/perl
### Database Manager v1.0 ###
### Author: Ben Kittrell  ###
### Created: 03/26/2001   ###

use strict;
use CGI;
use DBI;

my $q = CGI::new();
my $scriptname = $ENV{SCRIPT_NAME};
$scriptname =~ s/.*\/([^\/]*)$/$1/;
my $s = $q->param('_s');
my $database = $q->cookie('Database');
my $host = $q->cookie('Host');
my $driver = $q->cookie('Driver');
my $username = $q->cookie('Username');
my $password = $q->cookie('Password');

if ($s eq "connect") {
	if ($q->param('action') eq "Connect") { 
		$database = $q->param('Database');
		$host = $q->param('Host');
		$driver = $q->param('Driver');
		$username = $q->param('Username');
		$password = $q->param('Password');
	}
	my $dbh = DBI->connect("DBI:$driver:$database:$host", $username, $password) or ErrorOut($DBI::errstr);
	$dbh->disconnect;
	my $cookie1 = $q->cookie(-name=>'Database', -value=>$database, -expires=>'+3M');
	my $cookie2 = $q->cookie(-name=>'Host', -value=>$host, -expires=>'+3M');
	my $cookie3 = $q->cookie(-name=>'Driver', -value=>$driver, -expires=>'+3M');
	my $cookie4 = $q->cookie(-name=>'Username', -value=>$username, -expires=>'+3M');
	my $cookie5 = $q->cookie(-name=>'Password', -value=>$password, -expires=>'+3M');
	print $q->header(-cookie=>[$cookie1,$cookie2,$cookie3,$cookie4,$cookie5]);
	print &header("Connected");
	print "<ul><li><a href=\"$scriptname?_s=tables\">Show Tables</a></li>\n
	<li><a href=\"$scriptname?_s=createform\">Create Tables</a></li>\n
	<li><a href=\"$scriptname?_s=commandline\">Command Line</a></li>\n
	<li><a href=\"$scriptname?_s=logout\">Log Out</a></li></ul>";
	print &footer;
} elsif ($s eq "tables") {
	my $htmlout;
	my $table = $q->param('table');
	my $dbh = DBI->connect("DBI:$driver:$database:$host", $username, $password) or ErrorOut($DBI::errstr);
	if ($q->param('action') eq "drop") {
		$dbh->do("DROP TABLE $table");
	}
	my $sth = $dbh->prepare("SHOW TABLES");
	$sth->execute();
	while (my $results = $sth->fetchrow_hashref) {
		my $key = join '', keys %$results;
		my %results = %$results;
		if (!$htmlout) {
			$htmlout .= "<tr><th colspan=3>$key</th></tr>";
		}
		$htmlout .= "<tr><td><a href=\"$scriptname?_s=showtable&table=$results{$key}\">$results{$key}</a></td>
		<td><a href=\"$scriptname?_s=edit&table=$results{$key}\">edit</a></td>
		<td><a href=\"$scriptname?_s=tables&table=$results{$key}&action=drop\">drop</a></td></tr>";
	}
	$sth->finish;
	print $q->header();
	print &header("Show Tables");
	print "<table border=1>$htmlout</table>";
	print "<p><a href=\"$scriptname?_s=connect\">Home</a></p>";
	print &footer;;
	$dbh->disconnect;
} elsif ($s eq "showtable") {
	my $dbh = DBI->connect("DBI:$driver:$database:$host", $username, $password) or die "Connect Error: $DBI::errstr";
	my $table = $q->param('table');
	my $sth = $dbh->prepare("SHOW FIELDS FROM $table");
	$sth->execute();
	my $htmlout = "<tr><th>Field</th><th>Type</th><th>Default</th><th>Null</th><th>Key</th><th>Extra</th></tr>\n";
	while (my $results = $sth->fetchrow_hashref) {
		my %results = %$results;
		$htmlout .= "<tr><td>$results{'Field'}&nbsp;</td><td>$results{'Type'}&nbsp;</td>
		<td>$results{'Default'}&nbsp;</td><td>$results{'Null'}&nbsp;</td>
		<td>$results{'Key'}&nbsp;</td><td>$results{'Extra'}&nbsp;</td></tr>\n";
	}
	$sth->finish;
	print $q->header();
	print &header("$table");
	print "<table border=1>$htmlout</table>";
	print "<a href=\"$scriptname?_s=showvalues&table=$table\">See the Values</a>";
	print "<p><a href=\"$scriptname?_s=connect\">Home</a></p>";
	print &footer;
	$dbh->disconnect
} elsif ($s eq "showvalues") {
	my $dbh = DBI->connect("DBI:$driver:$database:$host", $username, $password) or die "Connect Error: $DBI::errstr";
	my $htmlout;
	my $table = $q->param('table');
	my $sth = $dbh->prepare("SELECT * FROM $table");
	$sth->execute();
	while (my $results = $sth->fetchrow_hashref) {
		my %results = %$results;
		if (!$htmlout) {
			$htmlout = "<tr>";
			foreach my $key (keys %results) {
				$htmlout .= "<th>$key</th>";
			}
			$htmlout .= "</tr>\n";
		}
		$htmlout .= "<tr>";
		foreach my $key (keys %results) {
			$htmlout .= "<td>$results{$key}&nbsp;</td>";
		}
		$htmlout .= "</tr>\n";
	}
	$sth->finish;
	print $q->header();
	print &header("Values");
	print "<table border=1>$htmlout</table>";
	print "<p><a href=\"$scriptname?_s=connect\">Home</a></p>";
	print &footer;
	$dbh->disconnect
} elsif ($s eq "logout") {
	my $cookie1 = $q->cookie(-name=>'Database', -value=>'', -expires=>'now');
	my $cookie2 = $q->cookie(-name=>'Host', -value=>'', -expires=>'now');
	my $cookie3 = $q->cookie(-name=>'Driver', -value=>'', -expires=>'now');
	my $cookie4 = $q->cookie(-name=>'Username', -value=>'', -expires=>'now');
	my $cookie5 = $q->cookie(-name=>'Password', -value=>'', -expires=>'now');
	print $q->header(-cookie=>[$cookie1,$cookie2,$cookie3,$cookie4,$cookie5]);
	print &header("Log out");
	print "Logged out";
	print &footer;
} elsif ($s eq "edit") {
	my $dbh = DBI->connect("DBI:$driver:$database:$host", $username, $password) or die "Connect Error: $DBI::errstr";
	my $table = $q->param('table');
	my $field = $q->param('field');
	if ($q->param('action') eq "drop") {
		$dbh->do("ALTER TABLE $table DROP $field");
	}
	my $adderr;
	if  ($q->param('action') eq "add") {
		my $sqlstr = "ALTER TABLE $table ADD " . $q->param('name') . " " . $q->param('type');
		$sqlstr .= " DEFAULT '" . $q->param('default') . "'" if $q->param('default');
		$sqlstr .= " not null" unless ($q->param('notnull') eq "yes");
		$dbh->do($sqlstr);
		$adderr = $dbh->errstr;
	}
	if  ($q->param('action') eq "modify") {
		my $sqlstr = "ALTER TABLE $table CHANGE " . $q->param('field') . " "  . $q->param('name') . " " . $q->param('type');
		$sqlstr .= " DEFAULT '" . $q->param('default') . "'" if $q->param('default');
		$sqlstr .= " not null" unless ($q->param('notnull') eq "yes");
		$dbh->do($sqlstr);
		$adderr = $dbh->errstr;
	}
	if  ($q->param('action') eq "Privilegize") {
		my $privs = join ", ", $q->param('privileges');
		my $sqlStr = $q->param('grchoice');
		$sqlStr .= " ALL PRIVILEGES" if $q->param('all');
		$sqlStr .= " $privs" if $privs && !$q->param('all');
		$sqlStr .= ", " if ($q->param('grant') && ($privs || $q->param('all')) && ($q->param('grchoice') eq "REVOKE"));
		$sqlStr .= " GRANT OPTION" if ($q->param('grant') && $q->param('grchoice') eq "REVOKE");
		$sqlStr .= " ON $table ";
		$sqlStr .= "TO " . $q->param('gusername') if ($q->param('grchoice') eq "GRANT");
		$sqlStr .= "FROM " . $q->param('gusername') if ($q->param('grchoice') eq "REVOKE");
		$sqlStr .= " IDENTIFIED BY '" . $q->param('gpassword') . "'" if ($q->param('gpassword') && $q->param('grchoice') eq "GRANT");
		$sqlStr .= " WITH GRANT OPTION" if ($q->param('grant') && $q->param('grchoice') eq "GRANT");
		$dbh->do($sqlStr);
		$adderr = $dbh->errstr;
	}
	my $sth = $dbh->prepare("SHOW FIELDS FROM $table");
	$sth->execute();
	my $htmlout = "<tr><th>Field</th><th>Type/Size</th><th>Default</th><th>Allow Null</th><th>Key</th><th>Extra</th><th>&nbsp;</th><th>&nbsp;</th></tr>\n";
	while (my $results = $sth->fetchrow_hashref) {
		my %results = %$results;
		my $notnull = "checked" if ($results{'Null'} eq "YES");
		$htmlout .= "<form action=\"$scriptname\" method=\"POST\"><tr><td><input type=text name=name value=\"$results{'Field'}\" size=15>&nbsp;</td>
		<td><input type=text name=type value=\"$results{'Type'}\" size=15>&nbsp;</td>
		<td><input type=text name=default value=\"$results{'Default'}\" size=10>&nbsp;</td>
		<td><input type=checkbox name=notnull value=yes $notnull>&nbsp;</td>
		<td>$results{'Key'}&nbsp;</td><td>$results{'Extra'}&nbsp;</td>
		<td><a href=\"$scriptname?_s=edit&table=$table&field=$results{'Field'}&action=drop\">drop</a></td>
		<td><input type=submit name=action value=modify><input type=hidden name=table value=$table>
		<input type=hidden name=field value=\"$results{'Field'}\"><input type=hidden name=\"_s\" value=edit></td></tr></form>\n";
	}
	$htmlout .= "<form action=\"$scriptname\" method=\"POST\"><tr><td><input type=text name=name size=15></td><td><input type=text name=type size=15></td>
	<td><input type=text name=default size=10></td><td><input type=checkbox name=notnull value=yes></td>
	<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	<td><input type=submit name=action value=add><input type=hidden name=table value=$table><input type=hidden name=\"_s\" value=edit></td></tr></form>";
	$htmlout = "<table border=1>$htmlout</table>";
	$htmlout .= "<br><b>Grant/Revoke Privileges:</b><br><form action=\"$scriptname\" method=\"POST\"><table border=1>
	<tr><td colspan=3 align=center><select name=\"grchoice\"><option value=\"GRANT\">GRANT</option><option value=\"REVOKE\">REVOKE</option></select></td></tr>\n
	<tr><td colspan=2>ALL</td><td><input type=\"checkbox\" name=\"all\" value=\"Yes\"></td></tr><tr>\n
	<tr><td colspan=2>SELECT</td><td><input type=\"checkbox\" name=\"privileges\" value=\"SELECT\"></td></tr>\n
	<tr><td colspan=2>INSERT</td><td><input type=\"checkbox\" name=\"privileges\" value=\"INSERT\"></td></tr>\n
	<tr><td colspan=2>UPDATE</td><td><input type=\"checkbox\" name=\"privileges\" value=\"UPDATE\"></td></tr>\n
	<tr><td colspan=2>DELETE</td><td><input type=\"checkbox\" name=\"privileges\" value=\"DELETE\"></td></tr>\n
	<tr><td colspan=2>CREATE</td><td><input type=\"checkbox\" name=\"privileges\" value=\"CREATE\"></td></tr>\n
	<tr><td colspan=2>DROP</td><td><input type=\"checkbox\" name=\"privileges\" value=\"DROP\"></td></tr>\n
	<tr><td colspan=2>ALTER</td><td><input type=\"checkbox\" name=\"privileges\" value=\"ALTER\"></td></tr>\n
	<tr><td colspan=2>INDEX</td><td><input type=\"checkbox\" name=\"privileges\" value=\"INDEX\"></td></tr>\n
	<tr><td colspan=2>GRANT</td><td><input type=\"checkbox\" name=\"grant\" value=\"Yes\"></td></tr>\n
	<tr><td>User\@Host:</td><td colspan=2><input type=\"text\" name=\"gusername\" size=10></td></tr>\n
	<tr><td>Password:</td><td colspan=2><input type=\"password\" name=\"gpassword\" size=10></td></tr>\n
	<tr><td colspan=3 align=center><input type=\"submit\" name=\"action\" value=\"Privilegize\"></td></tr>\n
	</table><input type=hidden name=\"_s\" value=edit><input type=hidden name=\"table\" value=\"$table\"></form>";
	$sth->finish;
	print $q->header();
	print &header("$table");
	print $adderr;
	print "$htmlout";
	print "<a href=\"$scriptname?_s=showvalues&table=$table\">See the Values</a>";
	print "<p><a href=\"$scriptname?_s=connect\">Home</a></p>";
	print &footer;
	$dbh->disconnect
} elsif ($s eq "createform") {
	my $htmlout;
	if ($q->param('_action') eq "Finished") {
		my $tablename = $q->param('tablename');
		my $sqlStr;
		my $primary;
		my @params = $q->param();
		foreach my $param (@params) {
			if ($q->param($param) && $param =~ s/^fieldname(\d*)$/$1/) {
				$sqlStr .= ", " if $sqlStr;
				$sqlStr .= $q->param("fieldname$param") . " " . $q->param("fieldtype$param");
				$sqlStr .= " DEFAULT '" . $q->param("fielddefault$param") . "'" if $q->param("fielddefault$param");
				$sqlStr .= " NOT NULL" unless ($q->param("allownull$param") eq "Yes");
				$sqlStr .= " " . $q->param("extra$param") if $q->param("extra$param");
				$primary .= ", " if $primary && $q->param("primary$param");
				$primary .= $q->param("fieldname$param") if $q->param("primary$param");
			}
		}
		$primary = ", PRIMARY KEY($primary)" if $primary;
		my $queryStr = "CREATE TABLE $tablename ($sqlStr$primary)";
		my $dbh = DBI->connect("DBI:$driver:$database:$host", $username, $password) or die "Connect Error: $DBI::errstr";
		$dbh->do($queryStr);
		my $createerr = $dbh->errstr;
		$dbh->disconnect;
		if ($createerr) {
			$htmlout = "$createerr\n$queryStr";
		} else {
			$htmlout = "Table Created";
		}
	} else {
		my $start = $q->param('_start') || 1;
		$htmlout = "<form action=\"$scriptname\" method=\"POST\">\n";
		my $end = $start + 5;
		my @params = $q->param();
		if ($q->param('_action') eq "Add More Fields") {
			foreach my $param (@params) {
				if ($param !~ /^_/ && $q->param($param)) { 
					$htmlout .= "<input type=\"hidden\" name=\"$param\" value=\"" . $q->param($param) . "\">\n";
				}
			}
		} else {
			$htmlout .= "Table Name:<input type=\"text\" name=\"tablename\">";
		}
		$htmlout .= "<table border=1><tr><th>Field</th><th>Type/Size</th><th>Default</th><th>Allow Null</th><th>Key</th><th>Extra</th></tr>\n";
		for (my $i=$start; $i<$end; $i++) {
			$htmlout .= "<tr><td><input type=\"text\" name=\"fieldname$i\"></td>
			<td><input type=\"text\" name=\"fieldtype$i\"></td>
			<td><input type=\"text\" name=\"fielddefault$i\"></td>
			<td><input type=\"checkbox\" name=\"allownull$i\" value=\"Yes\"></td>
			<td><input type=\"checkbox\" name=\"primary$i\" value=\"Yes\"></td>
			<td><input type=\"text\" name=\"extra$i\"></td></tr>\n";
		}
		$htmlout .= "</table><input type=\"hidden\" name=\"_start\" value=\"$end\">
		<input type=\"hidden\" name=\"_s\" value=\"createform\">
		<input type=\"submit\" name=\"_action\" value=\"Add More Fields\">
		<input type=\"submit\" name=\"_action\" value=\"Finished\"></form>\n";
	}
	print $q->header();
	print &header("Create Table");
	print $htmlout;
	print "<p><a href=\"dbman.mpl?_s=connect\">Home</a></p>";
	print &footer;
} elsif ($s eq "commandline") {
	my $htmlout;
	my $err;
	if ($q->param('action') eq "Submit") {
		my $dbh = DBI->connect("DBI:$driver:$database:$host", $username, $password) or die "Connect Error: $DBI::errstr";
		if ($q->param('query') =~ /^SELECT/i) {
			my $sth = $dbh->prepare($q->param('query'));
			$sth->execute();
			$err = $sth->errstr;
			while (my $results = $sth->fetchrow_hashref) {
				my %results = %$results;
				if (!$htmlout) {
					$htmlout = "<tr>";
					foreach my $key (keys %results) {
						$htmlout .= "<th>$key</th>";
					}
					$htmlout .= "</tr>\n";
				}
				$htmlout .= "<tr>";
				foreach my $key (keys %results) {
					$htmlout .= "<td>$results{$key}&nbsp;</td>";
				}
				$htmlout .= "</tr>\n";
			}
			$sth->finish;
			$htmlout = "<table border=1>$htmlout</table>";
		} else {
			my $sth = $dbh->prepare($q->param('query'));
			$sth->execute();
			$err = $sth->errstr;
			$htmlout = $sth->rows . " rows affected";
			$sth->finish;
		}
		$dbh->disconnect;
	}
	print $q->header();
	print &header("Command Line");
	if ($err) {
		print "$err<br>";
	} elsif ($htmlout) {
		print "$htmlout<br>";
	}
	print <<HTMLOUT;
	<form action="$scriptname" method="POST">
	<input type="hidden" name="_s" value="commandline">
	<input type="text" name="query" size="40">
	<input type="submit" name="action" value="Submit">
	</form>
HTMLOUT
	print "<p><a href=\"dbman.mpl?_s=connect\">Home</a></p>";
	print &footer;
} else {
	print $q->header();
	print &header("Login");
	print <<HTMLOUT;
	<form action="$scriptname" method="POST">
	<input type="hidden" name="_s" value="connect">
	<table>
	  <tr><td align="right">Database:</td>
	  <td><input type="text" name="Database" value="$database"><br></td></tr>
	  <tr><td align="right">Host:</td>
	  <td><input type="text" name="Host" value="$host"><br></td></tr>
	  <tr><td align="right">Driver:</td>
	  <td><input type="text" name="Driver" value="$driver"><br></td></tr>
	  <tr><td align="right">Username:</td>	    
	  <td><input type="text" name="Username" value="$username"><br></td></tr>
	  <tr><td align="right">Password:</td>
	  <td><input type="password" name="Password" value="$password"><br></td></tr>
	  <tr><td colspan="2" align="center"><input type="submit" name="action" value="Connect"> 
	  <input type="reset" value="Reset"></td></tr>
	</table>
	</form>
HTMLOUT
	print &footer;
}	

sub header {
	my $output = <<HTMLOUT;
	<html><head><title>$_[0]</title></head>
	<body>
	<h1>DB Manager</h1>
HTMLOUT
	return $output;
}

sub footer {
	my $output = <<HTMLOUT;
	<p align="right"><a href="http://benk.hypermart.net/kaos">thabenksta</a></p>
	</body></html>
HTMLOUT
	return $output;
}

sub ErrorOut {
	print $q->header();
	print "<h1>Error:</h1><p>$_[0]</p>";
	exit;
}
