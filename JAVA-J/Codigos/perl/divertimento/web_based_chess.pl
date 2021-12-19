rnbqkbnr
pppppppp
10101010
01010101
10101010
01010101
PPPPPPPP
RNBQKBNR
1

##</code><code>##


#!/usr/bin/perl -w
use CGI;
$q = new CGI;

$fx = $q->param('fx');
$fy = $q->param('fy');
$tx = $q->param('tx');
$ty = $q->param('ty');
$mesg = $q->param('mesg');

$fx--;   #Because the average non-geek doesn't like thinking in
$tx--;   #terms of 0-7.
$fy--;
$ty--;

print "Content-type: text/html\n\n";
$dir[0] = '/home/httpd/html/chess/board1';
$dir[1] = '/home/httpd/html/chess/board1.intransit';
$dir[2] = '/home/httpd/html/chess/log.html';
$dir[3] = '/home/httpd/html/chess/newboard1.html';
$dir[4] = '/home/httpd/html/chess/board1.html';

open (BRD, "$dir[0]") or die "Can't open that one sir!$!\n";
open (nBRD, ">$dir[1]") or die "Can't write to that one sir$!!\n";
open (LOG, ">>$dir[2]") or die "Can't write the log!$!\n";
open (NB, ">$dir[3]") or die "Can't write the new board!$!\n";

for (0..8)          #Read in the ACSII version of the board
  {                 #and assemble the structure.
    $in = <BRD>;
    chop $in;
    push @board, split //, $in;
  }

$piece = $board[$fy*8+$fx];   #Sanity Czechs.
if (($piece =~ /[bknpqr]/)&&($board[64]==1)) {&not_your_turn;}
if (($piece =~ /[BKNPRQ]/)&&($board[64]==0)) {&not_your_turn;}
if ($piece =~ /[01]/) {&no_piece_there;}
if (($fx>7)||($fy>7)||($tx>7)||($ty>7)) {&out_of_bounds;}
if (($fx<0)||($fy<0)||($tx<0)||($ty<0)) {&out_of_bounds;}
    
$board[$ty*8+$tx] = $piece;
if (($fy%2)xor($fx%2))
 {$board[$fy*8+$fx] = 0;}
else  {$board[$fy*8+$fx] = 1;}
if ($board[64]==1){print LOG "<font color=#ffffff>White ";}   
elsif ($board[64]==0){print LOG "<font color=#ff0000>Black ";}

print NB  <<"endhere";
<html><body bgcolor=#000000 text=ff0000 link=ffff00 vlink=ffff00>
<table><tr><td>
<table border=0 cellpadding=0 cellspacing=0>
<tr><td align=absmiddle>1</td>
endhere

$linecount=1;
for(0..63)     #Build the html of the new board.
 {
  $_ = $board[$_];
  print nBRD $_;
  if ($_ eq '0'){print NB "<td><img src=b.JPG></td>\n"}
  elsif ($_ eq '1'){print NB "<td><img src=w.JPG></td>\n"}
  elsif ($_ eq 'p'){print NB "<td><img src=bp.JPG></td>\n"}
  elsif ($_ eq 'P'){print NB "<td><img src=wp.JPG></td>\n"}
  elsif ($_ eq 'n'){print NB "<td><img src=bk.JPG></td>\n"}
  elsif ($_ eq 'N'){print NB "<td><img src=wk.JPG></td>\n"}
  elsif ($_ eq 'r'){print NB "<td><img src=br.JPG></td>\n"}
  elsif ($_ eq 'R'){print NB "<td><img src=wr.JPG></td>\n"}
  elsif ($_ eq 'b'){print NB "<td><img src=bb.JPG></td>\n"}
  elsif ($_ eq 'B'){print NB "<td><img src=wb.JPG></td>\n"}
  elsif ($_ eq 'q'){print NB "<td><img src=bq.JPG></td>\n"}
  elsif ($_ eq 'Q'){print NB "<td><img src=wq.JPG></td>\n"}
  elsif ($_ eq 'k'){print NB "<td><img src=bg.JPG></td>\n"}
  elsif ($_ eq 'K'){print NB "<td><img src=wg.JPG></td>\n"}
  $count++;
  if ($count==8)
   {
    $count=0;
    $linecount++;
    if($linecount<9){print nBRD "\n";
     print NB "</tr><tr><td align=absmiddle>$linecount</td>";}
   }
 }
if (!$board[64]){$board[64]=1;}
elsif ($board[64]){$board[64]=0;}
{print nBRD "\n$board[64]\n";}
    
print NB <<"end_of_text";
</tr>
<tr>
<td>&nbsp;</td>
<td><center>1</center></td>
<td><center>2</center></td>
<td><center>3</center></td>
<td><center>4</center></td>
<td><center>5</center></td>   
<td><center>6</center></td>
<td><center>7</center></td>
<td><center>8</center></td>
</tr>
</table>

</td>
<td valign=top>
<font size=+2>PERLMONKS CHESS</font><br>
<font size=-2>
"I once held the opinion that magnificent pieces allowed a person to play
a magnificent game.  I quickly learned that my opinion was wrong."
--Wombat</font>

<table border=2>
<tr><td><img align=absmiddle src=b.JPG>Black square</td>
<td><img align=absmiddle src=w.JPG>White square</td></tr>
<tr><td><img align=absmiddle src=bp.JPG>Pawn</td>
<td><img align=absmiddle src=br.JPG>Rook</td></tr>
<tr><td><img align=absmiddle src=bk.JPG>Knight</td>
<td><img align=absmiddle src=bb.JPG>Bishop</td></tr>
<tr><td><img align=absmiddle src=bq.JPG>Queen</td>
<td><img align=absmiddle src=bg.JPG>King</td></tr>
</table>
<a href=log.html>See the log of moves and dialogue.</a>

<form method=POST action=http://www.your.server.here/cgi-bin/chess.cgi>
<table>
<tr><td>From Y:</td><td>
<input type=text name="fy" size=1 maxlength=1></td>
<td>From X:</td><td>
<input type=text name="fx" size=1 maxlength=1></td></tr><br>
<tr><td>To Y:</td><td>
<input type=text name="ty" size=1 maxlength=1></td>
<td>To X:</td><td>
<input type=text name="tx" size=1 maxlength=1></td></tr><br>
<tr><td colspan=4>
Do you have anything to say to your opponent?<br>
<textarea name=mesg cols=40 rows=5></textarea></td></tr>
<tr><td colspan=4><input type=submit value="Move it or lose it!">
<input type=reset></td></tr>
</table>
</form>
</td>
</tr>
</table>
</html>
end_of_text

if ($piece =~ '[pP]'){print LOG "Pawn --";}
if ($piece =~ '[bB]'){print LOG "Bishop --";}
if ($piece =~ '[kK]'){print LOG "King --";}
if ($piece =~ '[nN]'){print LOG "Knight --";}
if ($piece =~ '[rR]'){print LOG "Rook --";}
if ($piece =~ '[qQ]'){print LOG "Queen --";}
$fy++;$fx++;$ty++;$tx++;
print LOG "$fy x $fx to $ty x $tx<br>\n";
print LOG "$mesg</font><br><br><br>\n";

$cmd = "mv $dir[3] $dir[4]";
system ($cmd);
$cmd = "mv $dir[1] $dir[0]";
system ($cmd);

print <<"HTML";
<html><head>
<SCRIPT LANGUAGE="JAVASCRIPT">
document.location = 'http://www.your.server.here/chess/board1.html';
</script></head>
<body bgcolor=#000000 text=ffffff>
Okay! You moved, have a nice day. <br>
<a href=http://www.your.server.here/chess/board1.html>Click here to return
to the board</a>
HTML

sub no_piece_there
 {
  print "There isn't any piece there.  Do not smoke crack while playing chess.\$
  exit (0);
 }

sub not_your_turn
 {
  print "Not your turn, go away. :-P\n";
  exit (0);
 }
  
sub out_of_bounds
 {
  print "Invalid move, try again.";
  exit (0);
 }
