 =**************************************
    = Name: Thrown Text
    = Description:Can you tell what's writte
    =     n there? Try running the code.
    = By: IdoT
    =
    = Inputs:An argument would change the de
    =     fault thrown string...
    =
    = Assumes:Must have Tk installed.
    =
    = Side Effects:Time waste looking at it;
    =     )
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=539&lngWId=6    =for details.    =**************************************
    
    use Tk;
    my $mw=new MainWindow;
    my $canvas=$mw->Canvas(-width=>400,-height=>600,-bg=>'white')->pack;
    $g=0.4;
    sub create{
    	my $self=shift;
    	my $t=0;
    	my $alpha=atan2($self->{m}-$self->{y0}-$g*$self->{t}**2/2,$self->{n}-$self->{x0});
    	my $v0=($self->{n}-$self->{x0})/($self->{t}*cos($alpha));
    	my $sub;
    	my $i=$canvas->create('text',$self->{x0},$self->{y0},-text=>$self->{letter});
    	$sub=sub{
    		$canvas->coords($i,$self->{x0}+cos($alpha)*$v0*$t,$self->{y0}+sin($alpha)*$v0*$t+$g*$t**2/2);
    		$t++;
    		$mw->after(30,$sub) unless $t>$self->{t};
    	};
    	$mw->after(rand(2000),$sub);
    }
    $canvas->repeat(6000,my $subr=sub{$canvas->delete('all');
    $x=100;
    $n=50;
    $string=$ARGV[0]||'www.planet-source-code.com';
    my @pos;
    for(split //,$string){
    	create({letter=>$_,x0=>$n+do{my $a;do{$a=int rand(length $string)} while $pos[$a];$pos[$a]=1;$a*10},y0=>590,m=>300,n=>$x+($string=~s/\Q$_/^/,$-[0])[1]*8,t=>55+int rand(40)});
    }});
    $subr->();
    MainLoop;

