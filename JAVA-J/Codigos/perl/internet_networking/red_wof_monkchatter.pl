#!/usr/bin/perl

### Modules
use Tk;
use IO::Socket;

### Win32 junk
BEGIN {
  my($win32)=1 if($^O eq 'MSWin32');
  
  if($win32) {
    eval 'use Win32::Shell';
    die "$@\n" if($@);
  }
}

### Configurables
$maxlines=500;
$blanknode=14532;
$delaysec=10;
# $proxy='';

### Code starts here
# HTTP request (w/o LWP's complexity)
sub httpreq {
  my($method,$url,$headers,$content)=@_;
  my($site,$dir,$port,$remote,$req,$result,$code,@top,$bot,$sock);
  my($e)="\r\n";
  
#  if(defined $proxy) {
#    print "Through proxy....";
#    $proxy=~ m#^http://([^/]+)(/.*)?$#i;
#  } else {
    $url=~ m#^http://([^/]+)(/.*)?$#i;
#  }
  $site=$1;$dir=$2;
  $dir="/" unless($dir);
  
  if($site =~ /^([^:]+):(\d+)$/)
    {	
      $remote=$1; $port=$2;
    } else {
      $remote=$site; $port=80;
    }
  
#  if(defined $proxy) {
#    $req="\U$method\E $url HTTP\\1.0$e";
#  } else {    
    $req="\U$method\E $dir HTTP\\1.0$e";
#  }

  foreach $j (@$headers)
    {
      $req .= "$j$e";
    }
  
  if($method =~ /POST/i)
    {
      my($l)=length $content;
      $req .= "Content-Length: $l$e$e";
      $req .= $content;
    } else {
      $req .= $e;
    }
  
  $sock=IO::Socket::INET->new(
			      Proto => "tcp",
			      PeerAddr => $remote,
			      PeerPort => $port)
    or return ''; #Nothing
  $sock->autoflush(1);
  
  print $sock $req;
  $code=<$sock>;
  my($i)=0;
  while(<$sock>)
    {
      tr/\r\n//d;
      last if(/^$/);
      $top[$i++]=$_;
      $main->update;
    }
  $bot='';
  while(<$sock>) { $bot .= $_; $main->update; }
  
  ($code,$bot,@top);
}

### XML minipharser
# Splits a message up along XML tags.  Returns the message in an array.
sub splitxml {

  my($d,$fl,@j)=@_;
  my(@tags,$i,$last);
  
  while($d)
    {
      "" =~ /(.?)/;
      if($d =~ s/^(<[^>]+>)//s) {
	$tags[$i++]=$1;
      } elsif( $fl && $d =~ s/^(\[[^\]]+\])//s ) {
	$tags[$i++]=$1;
      }	else {
       	$d=~s/^([^<]+)//s unless($fl);
	$d=~s/^([^<\[]+)//s if($fl);
	if(length $1) {
	  $tags[$i++]=$1;
	} else {
	  $d.=">" if($d=~/^\</);
	  $d.="]" if($d=~/^\[/);
	}
	
      }
    }
  (@tags);
}

# Takes a tag and splits it up into options.
# Returns a tag name and an hash.
sub splittag {
  my($tag)=@_;
  my($i,$j,$k,%opts,@t,$ti);
  
  $tag=~ s/^<//; $tag=~ s/>$//;
  
  $tag=~ s/^(\S+)\s+//; $ti=$1;
  
  while($tag) {
    $tag =~ s/^([^=]+)(=[\"\']([^\"\']+)[\"\']\s?)?//;
    $opts{$1}=($2 ? $3 : 1);
  }
  ($ti, %opts);
}

# Pharses a line and translantes all those &amp; thingies...
sub xlateamp {
  my($l,@j)=@_;
  
  $l=~ s/&lt;/</g;
  $l=~ s/&gt;/>/g;
  $l=~ s/&amp;/&/g;
  $l=~ s/&#(\d+);/chr($1)/eg;
  
  return($l);
}

###
# URL launcher
sub do_url {
  my($tag)=@_;
  my($i,$h);
  
  if($tag =~ /^url(\d+)/) {
    $i=$1;
    $h=$url[$i];
    
    $h=~ s#^id:\/\/(\d+)$#http:\/\/perlmonks.org\/index.pl?node_id=$1#;
    if($h !~ /^http:\/\// ) {
      $h =~ s/([^\w ])/sprintf("%%%02X",ord($1))/eg;
      $h =~ s/ /+/g;
      $h="http://perlmonks.org/index.pl?node=$h";
    }
    
    if($win32) {
      Win32::Shell::Execute("open", $h, undef,undef, "SW_SHOWNORMAL");
    } else {
      system("netscape -remote 'openURL($h)'");
    }
  }
}

# statnode
sub statnode {
  my($node)=@_;
  my($i,$j,$k,$l,$fl);
  my($cd,$doc,@head)=&httpreq("GET",
			      "http://perlmonks.org/index.pl?node=node+query+xml+generator&nodes=$node",
			      ["Cookie: $cookie"], "");
  
  my(@lines)=splitxml($doc);
  $fl=0;
  for($i=0;$i<@lines;$i++) {
    $l=$lines[$i];
    $l=~ tr/\n\r//d;
    $l=~ s/^\s+//;
    $fl++ if($l=~/^<node/i);
    $fl=0 if($l=~/^<\/node/i);
    $k=$l if($fl);
  }
  
  $k="id://$node" unless($k);
  return($k);
}

# LinePharzer
sub do_add {
  my($msg)=@_;
  
  if($raw)
    {
      $main_index_list->insert("end","\n$msg");
    } else {
      my($i,$j,$k,$l,%tags,$op);
      my(@lines)=splitxml($msg,1);
      
      $main_index_list->insert("end","\n");
      $op='';
      for($i=0;$i<scalar @lines;$i++)
	{
	  $l=$lines[$i];
	  
	  $tags{'code'}++, next if($l=~/^<\s*code/i && !$tags{'code'});
	  $tags{'code'}=$op='', next if($l=~/^<\/code/i);
	  
	  unless($tags{'code'})
	    {
	      if($l=~/^\[([^\]]+)\]/) {
		$j=$1; $k=$1;
		
		if($j=~/^id:\/\/(\d+)$/) {
		  $k=statnode($1);
		}
		
		$j=$1, $k=$2 if($j=~/^([^\|]+)\|(.+)$/);
		
		$url[$uc]=$j; $l=$k; $op="url$uc"; $uc++;
		
		$main_index_list->insert("end",$l,$op);
		$main_index_list->tagConfigure($op,-foreground=>"blue",
					       -data=>$j,
					       -underline=>1);
		# merlyn code...
		$main_index_list->tagBind($op, "<1>", do { my $thing = $op; sub { do_url($thing) } } );
		$op='';
		next;
	      } 
	      if($l=~/^<a/i) {
		my($t,%opt)=splittag($l);		    
		$url[$uc]=$opt{'href'}; $tags{'a'}=$op="url$uc"; $uc++;
		$main_index_list->insert("end",'',$op);
		$main_index_list->tagConfigure($op,-foreground=>"blue",
					       -data=>$j,
					       -underline=>1);
		# merlyn code...
		$main_index_list->tagBind($op, "<1>", do { my $thing = $op; sub { do_url($thing) } } );
		next;
	      }
	      if($l=~/^<\/a/i) {
		$tags{'a'}=$op='';
		next;
	      }
	      $l=xlateamp($l);
	    }
	  
	  $main_index_list->insert("end",$l,$op) if($op);
	  $main_index_list->insert("end",$l) unless($op);
	}
    }
}

# Do an update of the tags.
sub do_update {
  return if($uplock);
  $uplock=1;
  $main_bot_stat->configure(-text=>"Updating...");
  my($c,$d,@h)=&httpreq("GET",
			"http://perlmonks.org/index.pl?node=chatterbox+xml+ticker",
			"","");
  my($i,$l,$t,$au,$bt,$m,$fl,$uid,$ti,%opts);

  if($c =~ /200 OK/ )
    {
      my(@lines)=splitxml($d);
      $fl=0;
      for($i=0;$i<@lines;$i++)
	{
	  $l=$lines[$i];
	  $l=~ tr/\n\r//d;
	  $l=~ s/^\s+//;
	  if($l=~ /^<message/)
	    {
	      $au=$t=$uid='';
	      ($ti,%opts)=splittag($l);
	      $au=$opts{'author'};
	      $t=$opts{'time'};
	      $uid=$opts{'user_id'};
	      $fl=1; $t=~ s/^....//;
	      $t=~ /^....(..)(..)/;$t+=0;
	      $m="$1:$2: [id://$uid|$au]";
	      next;
	    } 
	    
	  if( $l =~ /^<\/message>/ )
	    {
	      $t+=.01 while($msg{$t} && $msg{$t} ne $m);
	      $msg{$t}=$m;
	      $bt=$t; $fl=0;
	      next;
	    }
	  
	  if( $fl==1 )
	    {
	      $l=xlateamp($l);
	      if( $l=~ /^\/me(.+)$/) { $m.=$1; } else { $m.=": $l"; }
	      $fl++;
	    } elsif ($fl>1) {
	      $m .= xlateamp($l);
	    }
	}
      
      if($prv) {
	if($justin) {
	  $justin=0;
	  $j=(int $lasttime)+.01;
	  foreach $z (sort keys %pmsg)
	    {
	      $msg{$j}=$pmsg{$z};
	      $bt=$j; $j+=.01;
	    }
	}
	foreach $j (sort keys %pmsg) {
	  $i=$j; $i+=.01 while($msg{$i} && $msg{$i} ne $pmsg{$j});
	  $msg{$i}=$pmsg{$j};
	  $bt=$i if($bt < $i);
	}
	
	$prv=0; %pmsg=();
      }
      
      $bt=$bt+0;
      if($bt > $lasttime)
	{
	  my($f)=0;
	  my(@ti)=sort keys %msg;
	  $lasttime=$ti[0]-1 unless($lasttime);
	  foreach $i (@ti)
	    {
	      
	      $f++ if($i > $lasttime);
	      do_add($msg{$i}) if($f);
	    }
	  $lasttime=$ti[-1]+0;
	  $main_index_list->see("end") if($f && $jump);
	}
    }
  $main_bot_stat->configure(-text=>" ");
  $uplock=0;
}

# We need a cookie for this routine.  It's the XP and Private stuff.
sub do_userprv {
  return 1 unless($in);
  
  # Get the privates...
  my($c,$ml,@h)=&httpreq("GET","http://perlmonks.org/index.pl?node=private+message+xml+ticker",
			 ["Cookie: $cookie"],"");
  my(@lines)=splitxml($ml);
  my($i,$fl,$l,$au,$t,$m,@mid,$mi,$z,$ft,$uid);
  
  for($i=0;$i<@lines;$i++)
    {
      $l=$lines[$i];
      $l=~ tr/\n\r//d;
      $l=~ s/^\s+//;
      if($l=~ /^<message/)
	{
	  $au=$t=$uid='';
	  ($ti,%opts)=splittag($l);
	  $au=$opts{'author'};
	  $t=$opts{'time'};
	  $uid=$opts{'user_id'};
	  $z=$opts{'message_id'};
	  $mid[$mi++]="deletemsg_$z=yup";
	  $fl=1; $t=~ s/^....//;
	  $t=~ /^....(..)(..)/;$t+=0;
	  $m="$1:$2: [id://$uid|$au]";
	  next;
	} 
      if($l=~ /^<message message_id=(.+) author=(.+) time=(.+)>/)
	{
	  $z=$1;
	  $au=$2; $t=$3;
	  $au=~ tr/\"\'//d; $t=~ tr/\"\'//d; 
	  $z=~ tr/\"\'//d; $t=~ s/^....//;
	  $mid[$mi++]="deletemsg_$z=yup";
	  $fl=1; 	
	  $t=~ /^....(..)(..)/; $t+=0;
	  $m="$1:$2: $au";
	  next;
	} 
	
      if( $l =~ /^<\/message>/ )
	{
	  $pmsg{$t}=$m; $prv++;
	  $bt=$t; $fl=0; $ft=$t unless($ft);
	  next;
	}
      
      if( $fl==1 )
	{
	  $l=~ s/&lt;/</g;
	  $l=~ s/&gt;/>/g;
	  $l=~ s/&amp;/&/g;
	  if( $l=~ /^\/me(.+)$/) { 
	    $m.=" (privately) ";
	  } else { 
	    $m.="> "; 
	  }
	  $m .= $l;
	  $fl++;
	} elsif ($fl>1) {
	  $l=~ s/&lt;/</g;
	  $l=~ s/&gt;/>/g;
	  $l=~ s/&amp;/&/g;
	  $m .= $l;
	}
    }

    # Now clear 'em...
  my($req)=join "&",@mid;
  $req.="&message=&message_send=talk&op=message";
  ($c,$ml,@h)=&httpreq("POST","http://perlmonks.org/index.pl",
		       ["Cookie: $cookie"], $req)
    if($oktoclear && $mi);
  
  # Get XP.
  
  ($c,$ml,@h)=&httpreq("GET","http://perlmonks.org/index.pl?node=XP+xml+ticker",["Cookie: $cookie"], "");
  @lines=splitxml($ml);
  for($i=0;$i<@lines;$i++)
    {
      $l=$lines[$i];
      $l=~ tr/\n\r//d;
      $l=~ s/^\s+//;
      if($l =~ /^<XP level=(.+) xp=(.+) xp2nextlevel=(.+) votesleft=(.+)>/)
	{
	  my($lv,$xp)=($1, $2);
	  $lv=~ tr/\'\"//d; $xp=~ tr/\'\"//d;
	  $main_bot_xp->configure(-text=>"Lev: $lv XP: $xp");
	}
    }
  
}

# Do Login/out
sub do_loginout
{
  my($i,$a,$c,@b);
  unless($in)
    {
      $user=$main_btns_user->get;
      $pass=$main_btns_pass->get;
      
      # Log us in...
      ($a,$c,@b)=&httpreq("POST","http://perlmonks.org/index.pl","",
			  "op=login&user=$user&passwd=$pass&expires=%2b10y&login=Login&node_id=$blanknode");
      if($a =~ /200 OK/)
	{
	  foreach $i (@b)
	    {
	      $cookie=$1 if($i =~/Set\-Cookie: (userpass=[^;]+);/ );
	    }
	  
	  if($cookie)
	    {
	      $main_bot_stat->configure(-text=>"Logged in.");
	      $in++; $justin++;
	      $main_btns_open->configure(-text=>"Logout");
	      $main_btns_user->configure(-state=>"disabled");
	      $main_btns_pass->configure(-state=>"disabled");
	      $main_art_text->configure(-state=>"normal");
	      do_userprv;
	    }
	}
    } else {
      ($a,$c,@b)= &httpreq("GET",
			   "http://perlmonks.org/index.pl?op=logout&node_id=$blanknode",
			   ["Cookie: $cookie"], "");
      if($a =~ /200 OK/) {
	$cookie=''; $in=0;
	$main_bot_stat->configure(-text=>"Logged out.");
	$main_btns_open->configure(-text=>"Login");
	$main_btns_user->configure(-state=>"normal");
	$main_btns_pass->configure(-state=>"normal");
	$main_art_text->configure(-state=>"disabled");
      }
    }
}

# Do some smackdowns.
sub do_chatter {
  my($a,$b,@c,$z);
  my($line)=$main_art_text->get;
  $main_art_text->delete(0,end);
  
  $line =~ s/([^\w ])/sprintf("%%%02X",ord($1))/eg;
  $line =~ s/ /+/g;
  
  ($a,$c,@b)=&httpreq("POST","http://perlmonks.org/index.pl",
		      ["Cookie: $cookie"], 
		      "op=message&message=$line&node_id=$blanknode");

  do_update;
}

# Menu handler
sub do_menu {
  $menued=0 if($main_opts->state ne "normal");
  
  unless($menued) {
    $main_opts->post($main_btns_opts->rootx,
			 $main_btns_opts->rooty+$main_btns_opts->height);
  } else {
    $main_opts->unpost;
  }
  $menued=1-$menued;
}

sub do_regen {
  my($i);
  $main_index_list->delete("1.0", "end");
  
  my(@ti)=sort keys %msg;
  foreach $i (@ti)
    {
      do_add($msg{$i});
    }
  $lasttime=$ti[-1]+0;
  $main_index_list->see("end");
}

$user=$pass=$line=$cookie=$lasttime='';
$in=$prv=$oktoclear=$justin=$menued=$uc=$uplock=0;
$jump=$raw=1;
@url=();

$main=MainWindow->new(-height=>320, -width=>240);

$main->geometry("240x320");

$main_btns = $main->Frame;
$main_btns_user = $main_btns->Entry(-width=> 10);
$main_btns_user->insert(0,"(username)");
$main_btns_pass = $main_btns->Entry(-show=> "*", -width=> 10);
$main_btns_open = $main_btns->Button(-text=> "Login",-padx=>1,-pady=>1, 
				     -command=>sub{do_loginout});
$main_btns_opts=$main_btns->Button(-text=>">>",-padx=>1,-pady=>1,
				   -command=>sub{do_menu });
$main_btns_opts->pack(-side=>"right");
$main_btns_open->pack(-side=>"right");
$main_btns_user->pack(-anchor=>"w", -side=>"left", -fill=>"x", -expand=>1);
$main_btns_pass->pack(-anchor=>"w", -side=>"left", -fill=>"x", -expand=>1);
$main_btns->pack(-anchor=>"w",-side=>"top",-fill=>"x",-expand=>1);

$main_opts=$main->Menu(-tearoff=>1,-title=>"Options");
$main_opts->add("command", -label=>"Regenerate", -command=>sub{do_regen});
$main_opts->add("separator");
$main_opts->add("checkbutton", -variable=>\$jump, -label=>"Jump on scroll");
$main_opts->add("checkbutton", -variable=>\$oktoclear,
		-label=>"Autoclear private messages");
$main_opts->add("checkbutton", -variable=>\$raw,
		-label=>"Don't do fancy formatting");

#$main_opts=$main->Frame;
#$main_opts_jump=$main_opts->Checkbutton(-variable=>\$jump, -text=>"Jumpscroll");
#$main_opts_jump->pack(-anchor=>"w", -side=>"left");
#$main_opts_clear=$main_opts->Checkbutton(-variable=>\$oktoclear, -text=>"AutoClear PrvMsg");
#$main_opts_clear->pack(-anchor=>"w",-side=>"left");
#$main_opts->pack(-anchor=>"e",-side=>"top",-fill=>"x",-expand=>1);

$main_bot=$main->Frame;
$main_bot_stat=$main_bot->Label(-text=>"RedWolf MonkChatter");
$main_bot_stat->pack(-anchor=>"w",-side=>"left");
$main_bot_xp=$main_bot->Label(-text=>"Lev: 0 XP: 0");
$main_bot_xp->pack(-anchor=>"e",-side=>"right");
$main_bot->pack(-anchor=>"s",-side=>"bottom",-fill=>"x",-expand=>1);

$main_index = $main->Frame;
$main_index_list = $main_index->Text(-wrap=>"word");
$main_index_list->configure(-font=>"fixed") unless($win32);
$main_index_scroll = $main_index->Scrollbar(-width=> 10,
					    -command=> ["yview", $main_index_list]);
$main_index_list->configure(-yscrollcommand=>['set', $main_index_scroll]);
$main_index_scroll->pack(-side=>"right",-fill=>"y");
$main_index_list->pack(-fill=>"both", -expand=>1);

$main_art = $main->Frame;
$main_art_text = $main_art->Entry(-state=>"disable");
$main_art_text->bind("<Return>",sub{do_chatter;});
$main_art_text->pack(-fill=>'x', -expand=>1);
$main_art->pack(-fill=>"x", -expand=>1, -anchor=>"w", -side=>"bottom");

$main_index->pack(-fill=>"both", -anchor=>"w",-side=>"bottom");

do_update;
$main->repeat($delaysec*1000,sub{do_update});
$main->repeat(60*1000,sub{do_userprv});

MainLoop;


