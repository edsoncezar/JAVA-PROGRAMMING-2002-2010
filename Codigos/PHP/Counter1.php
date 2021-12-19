    //**************************************
    //     
    // Name: Counter.php
    // Description:a little counter-script f
    //     or PHP. Needs the gd-library and Colors.
    //     php by Claus Radloff
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?
    ////////////////////////////////////////
    /////////////////////////////////
    //
    // counter.php - implements a simple Cou
    //     nter
    // 
    // Author: Claus Radloff
    // 
    // Description:
    // The counter in this file can count mu
    //     ltiple counters. Therefore
    // every counter gets it´s own identifie
    //     r.
    // The Fore- and Backgroundcolor and the
    //     number of digits are
    // defined, but can be overwritten. Fore
    //     - and Backgroundcolor may
    // be transparent as well.
    // The Parameters are passed in the URL.
    //     
    // Some Examples:
    // simple counter:
    //<img src="counter.php?Identifier=Te
    //     st">
    //
    // Counter with red background
    //<img src="counter.php?Identifier=Te
    //     st&BGColor=255+0+0">
    // or:
    //<img src="counter.php?Identifier=Te
    //     st&BGColor=red">
    //
    // Counter with red background, green fo
    //     reground and 4 digits
    //<img src="counter.php?Identifier=Te
    //     st&BGColor=255+0+0&FGColor=0+255+0&Lengt
    //     h=4">
    // or:
    //<img src="counter.php?Identifier=Te
    //     st&BGColor=red&FGColor=green&Length=4"&g
    //     t;
    //
    // Counter with transparent background a
    //     nd red foreground
    //<img src="counter.php?Identifier=Te
    //     st&BGColor=transparent&FGColor=red">
    // 
    Header("Content-type: image/gif");
    require("colors.php");
    // Load the gd-library
    // under Windooze use this one
    // dl("php3_gd.dll");
    // under UNIX use this one
    dl("php3_gd.so");
    // some default-values
    $Font = 5;
    $BGColor = GetColor("black");
    $BGTrans = False;
    $FGColor = GetColor("white");
    $FGTrans = False;
    $Length= 7;
    // get environment
    $query_string = getenv("QUERY_STRING");
    // parse environment
    // split query-string
    $env_array = split("&", $query_string);
    // split in key=value and convert %XX
    while (list($key, $val) = each($env_array))
    {
    // split
    list($name, $wert) = split("=", $val);
    // replace %XX by character
    $name = urldecode($name);
    $wert = urldecode($wert);
    // write to $cgivars
    $CGIVars[$name] = $wert;
    }
    // eventually replace the default-values
    //     by the given parameters
    if ($CGIVars["BGColor"])
    {
    if (ereg("([0-9]*) ([0-9]*) ([0-9]*)", $CGIVars["BGColor"], $tmp))
    {
    $BGColor["red"]= $tmp[1];
    $BGColor["green"] = $tmp[2];
    $BGColor["blue"] = $tmp[3];
    }
    else if (eregi("transparent", $CGIVars["BGColor"]))
    {
    $BGTrans = True;
    }
    else
    {
    $BGColor = GetColor($CGIVars["BGColor"]);
    }
    }
    if ($CGIVars["FGColor"])
    {
    if (ereg("([0-9]*) ([0-9]*) ([0-9]*)", $CGIVars["FGColor"], $tmp))
    {
    $FGColor["red"]= $tmp[1];
    $FGColor["green"] = $tmp[2];
    $FGColor["blue"] = $tmp[3];
    }
    else if (eregi("transparent", $CGIVars["FGColor"]))
    {
    $FGTrans = True;
    }
    else
    {
    $FGColor = GetColor($CGIVars["FGColor"]);
    }
    }
    if ($CGIVars["Length"])
    {
    $Length = $CGIVars["Length"];
    }
    // calculate size of image
    $SizeX = $Length * 13;
    $SizeY = 16;
    // read counter-file
    if (file_exists("counter.txt"))
    {
    $fp = fopen("counter.txt", "rt");
    while ($Line = fgets($fp, 999))
    {
    // split lines into identifier/counter
    if (ereg("([^ ]*) *([0-9]*)", $Line, $tmp))
    {
    $CArr["$tmp[1]"] = $tmp[2];
    }
    }
    // close file
    fclose($fp);
    // get counter
    $Counter = $CArr[$CGIVars["Identifier"]];
    $Counter += 1;
    $CArr[$CGIVars["Identifier"]] = $Counter;
    }
    else
    {
    // the new counter is initialized with 1
    //     
    $CArr[$CGIVars["Identifier"]] = 1;
    $Counter = 1;
    }
    // write counter file
    $fp = fopen("counter.txt", "wt");
    // output array elements
    reset($CArr);
    while (list($Key, $Value) = each($CArr))
    {
    $tmp = sprintf("%s %d\n", $Key, $Value);
    fwrite($fp, $tmp);
    }
    // close file
    fclose($fp);
    // Counter mit führenden Nullen auffülle
    //     n
    // fill counter with leading 0´s
    $Counter = sprintf("%0".$Length."d", $Counter);
    // create image
    $img = ImageCreate($SizeX + 4, $SizeY + 4);
    // use interlace
    ImageInterlace($img, 1);
    // transparent color for separators
    $trans = ImageColorAllocate($img, 1, 1, 1);
    ImageColorTransparent($img, $trans);
    // fill background
    if ($BGTrans)
    {
    ImageFill($img, 1, 1, $trans);
    }
    else
    {
    $col = ImageColorAllocate($img, $BGColor["red"], $BGColor["green"],
    $BGColor["blue"]);
    ImageFill($img, 1, 1, $col);
    }
    // output digits
    if ($FGTrans)
    {
    $col = $trans;
    }
    else
    {
    $col = ImageColorAllocate($img, $FGColor["red"], $FGColor["green"],
    $FGColor["blue"]);
    }
    $PosX = 0;
    for ($i = 1; $i <= strlen($Counter); $i++)
    {
    ImageString($img, $Font, $PosX + 3, 2 + $i % 2, 
    		substr($Counter, $i - 1, 1), $col);
    	
    	if ($i != 1)
    	{
    	// draw separator
    	ImageLine($img, $PosX, 0, $PosX, $SizeY + 4, $trans);
    	}
    	
    	$PosX += 13;
    }
    // output image
    ImageGif($img);
    ImageDestroy($img);
    ?>

