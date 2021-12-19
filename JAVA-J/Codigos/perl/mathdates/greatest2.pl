sub RecursiveGCD
    {
    	$A = shift;
    	$B = shift;
    	if( $A == 0 )
    	{
    		return $B;
    	}
    	else
    	{
    		RecursiveGCD( $A % $B, $B );	
    	}
    }

