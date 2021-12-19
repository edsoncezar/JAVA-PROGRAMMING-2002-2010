sub LoopingGCD 
    {
    	$A = shift;
    	$B = shift;
    	$R = 1;
    	
    	while ( $R != 0 )
    	{
    		$R = $A % $B;
    		
    		if ( $R == 0 )
    		{
    			return $B;
    		}
    		
    		$A = $B;
    		$B = $R;
    	}
    }

