
use strict;
use warnings;
use CGI qw/:all *table/;
use Cache::FileCache;

my $cache = new Cache::FileCache({namespace => 'coffee'});

my $cgi = new CGI;

print $cgi->header;
print $cgi->start_html({bgcolor=>'white', title=>'Someone get the coffee'});

print h1({align=>'center'}, "Someone get the coffee");

if ($cgi->param("action"))
{
	my $action = $cgi->param("action");

	if ($action eq "add")
	{
		my $name = $cgi->param("name");
		my $email = $cgi->param("email");
		
		unless ($name and $email)
		{
			print "Get lost.\n";
			print hr();
			exit;
		}
		else
		{
			$cache->set($name, $email, "1 day");
		}

		my @keys = $cache->get_keys();
		if (@keys > 2)
		{
			foreach my $key (@keys)
			{
				my $email = $cache->get($key);
				open(FOO, "|mail $email");
				print FOO "Subject: someone get the coffee\n\n";
				foreach my $keyagain (@keys)
				{
					print FOO "$keyagain - " . $cache->get($keyagain) . "\n";
				}
				print FOO $cgi->url() . "\n\n.\n";
				close(FOO);
			}
			
		}
	}
	elsif ($action eq "deleteall")
	{
		$cache->clear();
	}
	else
	{
		print "Get lost.\n";
		print hr();
		exit;
	}
}

my @keys = $cache->get_keys();

unless (@keys > 0)
{
	print p({align=>'center'}, "No orders right now.");
}
else
{
	print start_table({cellpadding=>3,cellspacing=>3, align=>'center'});
	print Tr({bgcolor=>'blue'},
			 td( span({style=>'color: white'}, "Name")),
			 td( span({style=>'color: white'}, "E-mail"))
			 );
	foreach my $key (@keys)
	{
		my $email = $cache->get($key);
		print Tr(
		   td($key),
		   td($email)
		   );
	}
	print end_table(), p();
}
  
print start_form({url=>$cgi->self_url, method=>'get'}),
	start_table(),
	Tr(
	   td( "Name" ),
	   td( textfield({name=>'name'}) ),
	   ),
	Tr(
	   td( "Email" ),
	   td( textfield({name=>'email'}) )
	   ),
	end_table();

print start_table(),
	Tr(
	   td(submit({name=>'action', value=>'add'}, "add")),
	   td(submit({name=>'action', value=>'deleteall'}, "deleteall")),
	   td(button({name=>'refreshpage',
			  value=>'refreshpage',
			  onClick=>"document.location.href=\"" . $cgi->url() . "\"; return;"}))
	   ),
	end_table();

print end_form();
