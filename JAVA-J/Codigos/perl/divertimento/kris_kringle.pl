chomp,@_=split(/::/),($e{$_[0]},$l{$_[0]})=($_[1],0)while(<>);;
$m=qq#/usr/lib/sendmail#;$s=q#kris.kringle@north.pole#;;foreach
(keys%l){$l{$_}++;@L=grep{($l{$_}==0)}keys%l;;$x=int rand(@L);;
$l{$L[$x]}++;$l{$_}--;open(M,"|".$m." ".$e{$_});print M "From:"
," $s\n";print M "To: ", $e{$_},"\n";$t=qq/Your Kris Kringle /.
q/Recipient/;print M "Subject: $t!\n";print M "\n$t is: $L[$x].
\n\nK.K.";print M "\n\nPlease do NOT reply! 'I' am a program:",
"\n\n";open(T,$0);printf M while(<T>);close(T);close(M);}##JnK!

