 sub replace{
    	@ops = @_;
    	$xpos = index($ops[0],$ops[1]);
    	$lenRepVar = length($ops[1]);
    	$retString=$ops[0];
    	while ($xpos != -1)
    		{$retString = substr($retString,0,$xpos).$ops[2].substr($retString,$xpos + $lenRepVar);$xpos = index($retString,$ops[1]);}
    	return $retString;
    }

