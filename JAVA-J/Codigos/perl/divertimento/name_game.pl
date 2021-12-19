#!/usr/bin/perl

print "Content-type: text/html\n\n";
&showform();

if($ENV{'REQUEST_METHOD'} ne "GET"){
  my($myform, $game, $left);
  $myform=&translate_form();
  $$myform{'name'}=~s/(\w)(\w+)/\u$1\L$2/;# capitalize first letter, lc the rest
  my @letters=split(//, $$myform{'name'});
  while($letters[0]!~/^[aeiouy]/i){
   last if scalar(@letters)==0;
   shift @letters;
  }
  $game="!name! !name! Bo-B!left!\nBanana Fanna Fo-F!left!\nMe My Mo-M!left!\n!name!!\n";
  $$myform{'left'}=join("", @letters);
  $game=~s/!([^!]+)!/$$myform{$1}/sg;
  $game=~s/\n/<br>\n/g;

print <<END;
<HR>
<font face="Comic Sans MS, Verdana, Arial" size=4 color="#FF4444">
$game
</font>
END

}
 print <<END;
</body>
</html>
END


sub showform{
 print <<END;
<html>
<title>The Name Game</title>
<body>
<CENTER>
<form method="POST" action="namegame.cgi">
<input type="text" name="name" size=25>
<input type="submit" value="Name-Game it!">
</form>
</CENTER>
END

}

sub translate_form{
  my($myform, $pair, %myform);
  if ($ENV{'REQUEST_METHOD'} eq "GET"){ 
     $myform = $ENV{'QUERY_STRING'};
  }else{
     read(STDIN, $myform, $ENV{'CONTENT_LENGTH'});
  }  
  foreach $pair (split (/&/, $myform)) {
     $pair=~s/\+/ /g;
     my($name, $value) = split(/=/,$pair);  
     foreach($name, $value){
       s/%(..)/pack ("C", hex ($1))/eg; 
       s/^\s*//sg; # remove leading spaces
       s/\s*$//sg; # remove trailing spaces
     }
     $myform{$name} .= "\0" if (defined($myform{$name})); 
     $myform{$name} .= $value;
  } 
  return \%myform;
}
