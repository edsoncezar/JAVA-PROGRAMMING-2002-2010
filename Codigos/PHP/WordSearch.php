    //**************************************
    //     
    // Name: WordSearch
    // Description:The user provides a list 
    //     of words to be placed into a word search
    //     puzzle suitable for printing. by Park Wi
    //     ker
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <? 
    // for an example of the submit form, lo
    //     ok at http://www.wiker.net/games/wordsea
    //     rch.phps
    reset ($HTTP_POST_VARS); 
    // -------------------------------------
    //     ----------------------------------------
    //     ------------- 
    function setvars() { 
    // make all the vars globally available 
    //     
    global $studentname,$classname,$assignment,$period,$board,$wordarray,$dirtn,$title,$gridsize,$wordlist,$difficulty,$listorder,$HTTP_POST_VARS; 
    // set all variables from post to discre
    //     te vars 
    $title=ucfirst(strtolower(trim($HTTP_POST_VARS[title]))); 
    $gridsize=$HTTP_POST_VARS[gridsize]; 
    $wordlist=trim($HTTP_POST_VARS[wordlist]); 
    $difficulty=$HTTP_POST_VARS[difficulty]; 
    $listorder=$HTTP_POST_VARS[listorder]; 
    $studentname=$HTTP_POST_VARS[studentname]; 
    $classname=$HTTP_POST_VARS[classname]; 
    $assignment=$HTTP_POST_VARS[assignment]; 
    $period=$HTTP_POST_VARS[period]; 
    // if the losser enters a blank list, pu
    //     t in the word "losser" 
    if ($wordlist== "") 
    $wordlist= "losser"; 
    } 
    // -------------------------------------
    //     ----------------------------------------
    //     ------------- 
    // separate word list into separate vars
    //     for each word 
    function seplist() { 
    global $studentname,$classname,$assignment,$period,$board,$wordarray,$dirtn,$title,$gridsize,$wordlist,$difficulty,$listorder,$HTTP_POST_VARS; 
    // make wordlist an array by linefeed 
    $wordlist=explode( "\n",$wordlist); 
    reset($wordlist); 
    // take wordlist and put separate words 
    //     into a multi-dim array 
    // of course make them upper case and tr
    //     im all whitespace from front and back 
    while ( list( $key, $val ) = each( $wordlist) ) { 
    $val=trim ($val); 
    $val=strtoupper($val); 
    // after assigning everything to upper c
    //     ase, replace the array val with the new 
    //     val 
    $wordlist[$key]=$val; 
    // get the string length so that we can 
    //     parse through the word and make a 
    // multidim array each letter being an a
    //     rray position 
    $strlength=strlen($val); 
    for ($i=0;$i!=($strlength);$i++){ 
    $letter=substr($val,$i,1); 
    $wordarray[$key][$i]=$letter; 
    } 
    } 
    } 
    // -------------------------------------
    //     ----------------------------------------
    //     ------------- 
    function boardset() { 
    global $studentname,$classname,$assignment,$period,$board,$wordarray,$dirtn,$title,$gridsize,$wordlist,$difficulty,$listorder,$HTTP_POST_VARS; 
    // establish direction array relative di
    //     rections 
    $dirtn[0]=array(x=>0,y=>1);// up | 
    $dirtn[1]=array(x=>1,y=>0);// right - 
    $dirtn[2]=array(x=>0,y=>-1); // down | 
    $dirtn[3]=array(x=>-1,y=>0); // left - 
    $dirtn[4]=array(x=>1,y=>1);// up and right / 
    $dirtn[5]=array(x=>1,y=>-1); // down and right \ 
    $dirtn[6]=array(x=>-1,y=>-1); //down and left / 
    $dirtn[7]=array(x=>-1,y=>1); //up and left \ 
    //establish board with blank spaces 
    for ($i=0;$i!=$gridsize;$i++){ 
    for ($j=0;$j!=$gridsize;$j++){ 
    $board[$i][$j]= " "; 
    } 
    } 
    } 
    // -------------------------------------
    //     ----------------------------------------
    //     ------------- 
    function placewords(){ 
    global $studentname,$classname,$assignment,$period,$board,$wordarray,$dirtn,$title,$gridsize,$wordlist,$difficulty,$listorder,$HTTP_POST_VARS; 
    // count the number of letters in the fi
    //     rst word 
    $wordnumber=count($wordarray); 
    // main for loop one time through for ea
    //     ch word 
    for($i=0;$i!=$wordnumber;$i++) { 
    // count the letters in the current word
    //     
    $letternumber=(count($wordarray[$i])); 
    // toggle wordfit for verification of wo
    //     rd fitting on the grid without overwrite
    //     ing any other letters 
    $wordfit=0; 
    while ($wordfit <= 0){ 
    // toggle wordfit again in case the whil
    //     e loop runs a few times 
    $wordfit=0; 
    // toggle length ok to ensure proper che
    //     cking of word length within constraints 
    //     of grid 
    $lengthok=0; 
    // test for fit of word 
    while ($lengthok!=1){ 
    // seed random with the clock 
    srand((double)microtime()*1000000); 
    // get the random direction based on dif
    //     ficulty selected 
    switch ($difficulty){ 
    case hard: 
    $direction=rand()%8; 
    break; 
    case easy: 
    $direction=rand()%4; 
    break; 
    default: 
    $direction=rand()%8; 
    } 
    // pick a random position within the sco
    //     pe of the grid 
    srand((double)microtime()*1000000); 
    $position=array(x=>(rand()%$gridsize),y=>(rand()%$gridsize)); 
    // calculate where the word will end 
    $endposition[x]=$position[x]+($letternumber*$dirtn[$direction][x]); 
    $endposition[y]=$position[y]+($letternumber*$dirtn[$direction][y]); 
    // make our endposition calculation have
    //     to be between 0 and 1 
    $result[x]=(abs($endposition[x]/$gridsize)); 
    $result[y]=(abs($endposition[y]/$gridsize)); 
    // if the end position is inside the gri
    //     d set the length ok 
    if($result[x]>1) { 
    $lengthok=0; 
    } 
    elseif($result[y]>1) { 
    $lengthok=0; 
    } 
    else{ 
    $lengthok=1; 
    } 
    } 
    // set a temp var to verify every positi
    //     on a letter will occupy 
    $tposition=$position; 
    // now check for what is in the position
    //     s selected 
    reset($wordarray[$i]); 
    while ( list ($key,$val)=each($wordarray[$i])){ 
    // what is currently in the space 
    switch ($board[$tposition[x]][$tposition[y]]){ 
    // if it is a space, move in the directi
    //     on one space and toggle wordfit +1 
    case " ": 
    $tposition[x]=$tposition[x]+$dirtn[$direction][x]; 
    $tposition[y]=$tposition[y]+$dirtn[$direction][y]; 
    $wordfit=$wordfit+1; 
    break; 
    // if it is the same as the value we wan
    //     t to place there, move and toggle wordfi
    //     t +1 
    case $val: 
    $tposition[x]=$tposition[x]+$dirtn[$direction][x]; 
    $tposition[y]=$tposition[y]+$dirtn[$direction][y]; 
    $wordfit=$wordfit+1; 
    break; 
    // if the letter is not compatible, togg
    //     le wordfit -50 so that we do not place t
    //     he word 
    // we will have to go back and find a ne
    //     w place 
    default: 
    $wordfit=(-50); 
    } 
    } 
    } 
    reset ($wordarray[$i]); 
    // we have successfully gotten out of th
    //     e first while loop and the word can be p
    //     laced 
    while (list ($key,$val)=each($wordarray[$i])){ 
    $board[$position[x]][$position[y]]=$val; 
    $position[x]=$position[x]+$dirtn[$direction][x]; 
    $position[y]=$position[y]+$dirtn[$direction][y]; 
    } 
    } // end of main for loop 
    } 
    //--------------------------------------
    //     ----------------------------------------
    //     ------ 
    function printgrid(){ 
    global $studentname,$classname,$assignment,$period,$board,$wordarray,$dirtn,$title,$gridsize,$wordlist,$difficulty,$listorder,$HTTP_POST_VARS; 
    
    //fill blanks with random characters 
    reset($board); 
    // while in each row x 
    while (list($key,$val)=each($board)){ 
    // go to each column y 
    while (list($key2,$val2)=each($board[$key])){ 
    // if the character there is blank go ah
    //     ead and fill it in in the array 
    switch ($board[$key][$key2]){ 
    case " ": 
    srand((double)microtime()*1000000); 
    $board[$key][$key2]=chr(rand()%26+65); 
    break; 
    default: 
    break; 
    } 
    } 
    } 
    // now print those values to the page in
    //     side of a table 
    for ($i=0;$i!=$gridsize;$i++){ 
    echo "<tr>\n"; 
    for ($j=0;$j!=$gridsize;$j++){ 
    echo "<td width=\"20\" height=\"15\" align=\"CENTER\" valign=\"MIDDLE\">" . $board[$i][$j] . "</td>\n"; 
    } 
    echo "</tr>\n"; 
    } 
    } 
    // -------------------------------------
    //     ---------------------------- 
    function printlist(){ 
    global $studentname,$classname,$assignment,$period,$board,$wordarray,$dirtn,$title,$gridsize,$wordlist,$difficulty,$listorder,$HTTP_POST_VARS; 
    
    sort ($wordlist); 
    reset ($wordlist); 
    while (list ($key,$val)=current( $wordlist) ) { 
    echo "<tr>\n"; 
    for ($i=0;$i<3;$i++){ 
    $val=current($wordlist); 
    echo "<td width=\"150\" align=\"LEFT\" valign=\"MIDDLE\">" . $val . "</td>\n"; 
    $val=next($wordlist); 
    } 
    echo "</TR>\n"; 
    } 
    } 
    // -------------------------------------
    //     ---------------------------- 
    function printinfo(){ 
    global $studentname,$classname,$assignment,$period,$board,$wordarray,$dirtn,$title,$gridsize,$wordlist,$difficulty,$listorder,$HTTP_POST_VARS; 
    echo "<tr>\n<td align=\"LEFT\" valign=\"MIDDLE\">Class: " . $classname . "<br>\n"; 
    echo "Student: " . $studentname . "</td>\n"; 
    echo "<td align=\"LEFT\" valign=\"MIDDLE\">Assignment: " . $assignment . "<br>\n"; 
    echo "Period: " . $period . "</td></tr>\n"; 
    echo "<tr>\n<td><i><font size=\"-1\"><b>Provided by Wiker Business Systems</b></font></i><td>\n<i><font size=\"-1\"><b>http://www.wiker.net</b></font></i></td></tr>\n"; 
    } 
    ?> 
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
    <html> 
    <head> 
    <title>Puzzle Result</title> 
    </head> 
    <body bgcolor="White" topmargin=0> 
    <? 
    setvars(); 
    seplist(); 
    boardset(); 
    placewords(); 
    echo "<div align=\"center\"><h2>$title</h2></div>"; 
    ?> 
    <table border="0" cellspacing="0" cellpadding="0" align="CENTER" valign="TOP" nowrap> 
    <? 
    printgrid(); 
    ?> 
    </table> 
    <br><br> 
    <table width="80%" border="0" cellspacing="0" cellpadding="0" align="CENTER" valign="MIDDLE" nowrap> 
    <? 
    printlist(); 
    ?> 
    </table> 
    <br> 
    <table width="80%" border="1" cellspacing="0" cellpadding="0" align="CENTER" valign="MIDDLE" nowrap> 
    <? 
    printinfo(); 
    ?> 
    </table> 
    </body> 
    </html> 

