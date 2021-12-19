//**************************************
    //     
    // Name: CodeLib
    // Description:This is a group of functi
    //     ons I wrote so I didn't have to write th
    //     ings over and over in an HTML file..
    // By: Dennis Wrenn
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.243/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    <?
    //#############################COUNTER F
    //     UNCTIONS#############################\\
    //create a directory called hits in your
    //     
    //main directory
    function gethits($logpath) {
    	$hits = 0;
    	$logpath = "./hits/".$logpath.".hits";
    	If (file_exists($logpath)) {
    		$filenum = fopen($logpath,"r");
    		if ($filenum > 0) {
    			$filesize = filesize($logpath);
    			$hits = fread($filenum,$filesize+1);
    			fclose($filenum);
    		}
    	} else {
    		$filenum = fopen($logpath,"w");
    		fwrite($filenum,$hits);
    		fclose($filenum);
    	}
    	return $hits;
    }
    function addhit($logpath) {
    	$hits = 0;
    	$logpath = "./hits/".$logpath.".hits";
    	If (file_exists($logpath)) {
    		$filenum = fopen($logpath,"r");
    		if ($filenum > 0) {
    			//Get the current file hit count
    			$filesize = filesize($logpath);
    			$hits = fread($filenum,$filesize);
    			fclose($filenum);
    			//Increment the hit counter
    			$hits++;
    			$filenum = fopen($logpath,"w");
    			fwrite($filenum,$hits);
    			fclose($filenum);
    		}
    	}else{
    		$hits++;
    		$filenum = fopen($logpath,"w");
    		fwrite($filenum,$hits);
    		fclose($filenum);
    	}
    	return;
    }
    //#############################IP FUNCTI
    //     ONS#############################\\
    //logs IP for forums and stuff like that
    //     
    function logip($path) {
    	global $REMOTE_ADDR;
    	writetolog($path, "$REMOTE_ADDR*\n");
    }
    function parseips($ips) {
    	return str_replace("*", "<BR>", $ips);
    }
    //#############################FILE FUNC
    //     TIONS#############################\\
    //for logip functions, and others I will
    //     soon write
    function writetolog($logfile, $texttowrite) {
    	$filenum = fopen($logfile,"a");
    	fwrite($filenum,$texttowrite);
    	fclose($filenum);
    }
    function openfile($path) {
    if(file_exists($path) == 0) {
    return "";
    }
    else
    {
    $thefilesize = filesize($path);
    $filenum = fopen($path,"r");
    $filecontent = fread($filenum, $thefilesize+1);
    fclose($filenum);
    return $filecontent;
    }
    }
    //#############################HEADER AN
    //     D FOOTER#############################\\
    //use like this:
    //showheader("title","path");
    function showheader($title, $path) {
    	$link = ; //link color
    	$alink = ; //active link color
    	$vlink = ; //visited link color
    	$bgcolor = ; // BG color
    	$text = ; // textcolor
    	addhit($path);
    	echo "<HTML>\n";
    	echo "<HEAD>\n";
    	echo "<TITLE>$title</TITLE>\n";
    	echo "<HEAD>\n";
    	echo "<BODY BGCOLOR=$bgcolor TEXT=$text LINK=$link ALINK=$alink VLINK=$vlink>\n";
    	echo "<FONT FACE=\"Arial\" SIZE=2>\n";
    	showsidebar($title);
    		
    	echo "<BR>\n";
    }
    function showfooter($path) {
    	echo "<P>\n";
    	echo "<BR>\n";
    	echo "<CENTER>";
    	echo "<FONT COLOR=\"#0000FF\" SIZE=2>\n";
    	echo "There have been <B>";
    	echo gethits($path);
    	echo "</B> visitors to this page since 12/19/2000 <BR>\n";
    	echo "Copyright © 2000 Your Name ";
    	echo "All rights reserved <BR> \n";
    	echo "</FONT>\n";
    	echo "</TD>\n</TR>\n</TABLE>\n";
    	echo "</CENTER>\n";
    	echo "</FONT>\n";
    	echo "</BODY>\n</HTML>";
    }
    function showsidebar($title) {
    //look at screenshot, and change what yo
    //     u need.
    	echo "<TABLE BORDER=3 BORDERCOLOR=\"#336699\" WIDTH=100% HEIGHT=100% CELLPADDING=\"20\" CELLSPACING=0>\n";
    	echo "<TR>\n<TD WIDTH=15% BGCOLOR=\"#336699\" VALIGN=\"CENTER\" ALIGN=\"CENTER\"><IMG SRC=\"images/dw.gif\"></TD>";
    	echo "<TD WIDTH=85% BGCOLOR=\"#336699\" VALIGN=\"CENTER\" ALIGN=\"CENTER\">";
    	echo "<FONT COLOR=\"#FFFFFF\">\n";
    	showtitle($title);
    	echo "</FONT>\n";
    	echo "<TR>\n<TD WIDTH=15% BGCOLOR=\"#336699\" VALIGN=\"TOP\" ALIGN=\"CENTER\">";
    //create a file called sidebar.txt
    //and put some links in there. It 
    //must be in HTML format.EG:
    //<A HREF="link1.htm">Link1</A&
    //     gt;<BR>
    //<A HREF="link2.htm">link2</A&
    //     gt;<BR>
    //etc
    //be sure to leave a blank line at the t
    //     op of the
    //file.
    	$file = openfile("sidebar.txt");
    	echo "$file";
    	echo "</TD>\n<TD WIDTH=85% VALIGN=\"TOP\" ALIGN=\"LEFT\">";
    }
    function showtitle($title) {
    	echo "\n<H1><CENTER>$title</CENTER></H1>\n";
    }
    function showsubtitle($stitle,$size=2) {
    	echo "<FONT COLOR=\"#0000FF\">\n<H$size><CENTER>$stitle</CENTER></H$size>\n</FONT>";
    }
    ?>
