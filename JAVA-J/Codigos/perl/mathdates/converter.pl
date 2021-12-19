   #!perl.exe
    my ($sbase,$tbase,$sample)=@ARGV;
    $sbase	= get_in( 'Enter the sample base:'	) unless($sbase );
    $tbase	= get_in( 'Enter base to convert to:'	) unless($tbase );
    if($sample) {
    	print convert_base($sbase,$sample,$tbase),"\n";
    } else {
    	while(<STDIN>) {
    		chomp;
    		print convert_base($sbase,$_,$tbase),"\n";
    	}
    }
    sub convert_base {
    	my ($sbase,$sample,$tbase)=@_;
    	my @num=split(//,$sample);
    	my ($num,$i,@res,@symb,%base);
    	foreach(0..9,A..Z,a..z) {
    		push @symb,$_;
    	}
    	foreach(0..@symb-1) {
    		$base{$_}=$symb[$_];
    	}
    	@num=reverse(@num);
    	foreach(0..@num-1) {
    		#print "Digit $_ (" . $num[$_] . ") is ";
    		$t=$base{$num[$_]} * expn($sbase,$_);
    		$t=$base{$num[$_]} if($_==0);
    		#print $t . "\n";
    		$num+=$t;
    	}
    	#print "Base is $num\n";
    	while(expn($tbase,$i)<=$num) {
    		$i++;
    	}
    	$i--;
    	foreach(0..$i) {
    		$_=$i-$_;
    		$res[$_]='0';
    		while($num>=expn($tbase,$_)) {
    			$num-=expn($tbase,$_);
    			$res[$_]++;
    		}
    	}
    	foreach(@res) {
    		$_=$symb[$_];
    	}
    	@res=reverse(@res);
    	return join('',@res);
    }
    sub expn {
    	my ($num,$expn)=@_;
    	my $n=1;
    	foreach(1..$expn) {
    		$n*=$num;
    	}
    	return $n;
    }
    sub get_in {
    	print shift() . ' ';
    	my $data=<STDIN>;
    	chomp $data;
    	return $data;
    }

