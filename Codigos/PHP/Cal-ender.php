//**************************************
    //     
    // Name: Cal-ender
    // Description:A calendar that doesn't n
    //     eed any editing. If you want the better 
    //     version (which is free) go to http://ddk
    //     resource.cjb.com.
    // By: Driss Kaitouni
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.353/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    <?php
    $DBHost="your.host.name";
    $DBUser="db_username";
    $DBPass="db_password";
    function DBInfo() {
    global $DBHost,$DBUser,$DBPass;
    }
    $Approval_Email="email@your.domain";
    $Web_Host="http://your.domain.com";
    function Approver() {
    global $Approval_Email,$Web_Host;
    }
    function commonHeader($title) {
    ?>
    <HTML>
    <HEAD>
    <TITLE>Rosenet Community Calendar - <? echo $title;?></TITLE>
    </HEAD>
    <BODY BGCOLOR="#ffffff">
    <!-- start body -->
    <?
    }
    function commonFooter() {
    ?>
    <!-- End Body -->
    <CENTER>
    <br><hr width='70%'><br><font face='Arial'>
    | <a href="index.phtml">View Calendar</a> |
    <a href="newData.phtml">Add An Event</a> |
    <br>
    </font>
    </CENTER>
    </BODY>
    </HTML>
    <?
    }
    function adminFooter() {
    ?>
    <!-- End Body -->
    <CENTER>
    <br><hr width='70%'><br><font face='Arial'>
    | <a href="/calendar/index.phtml">View Calendar</a> |
    <a href="./admin.phtml">Admin Page</a> |
    <br>
    </font>
    </CENTER>
    </BODY>
    </HTML>
    <?
    }
    function eventFooter($Old,$back) {
    echo "<br>";
    if ($Old==1) {
    echo "<center><font face=\"Arial\"><a href=\"$back\">Return To The Calendar</a></center>";
    } else {
    echo "<CENTER><FORM><INPUT TYPE=\"button\" VALUE=\"Close This Window\" onClick=\"window.close()\"></FORM><br>";
    }
    echo "<font face=\"Arial\"><a href=\"newData.phtml\">Add An Event</a></center>";
    echo "</font></body></html>";
    }
    function eventAdminFooter() {
    echo "<br>";
    echo "<CENTER><FORM><INPUT TYPE=\"button\" VALUE=\"Close This Window\" onClick=\"window.close()\"></FORM><br>";
    echo "<font face=\"Arial\"><a href=\"addCalendar.phtml\">Add An Event</a></center>";
    echo "</font></body></html>";
    }
    /*
    This script was created by Christopher Ostmo of Rosenet, Inc. 1998.
    Significant contributions were offered by Joe McNulty of Condor
    Consulting, Inc and Brad Marsh.
    If you have questions or feature requests, e-mail me at:
    costmo@rosenet.net
    Feel free to use and modify these scripts, but please keep this notice
    attached.
    http://www.rosenet.net/
    http://modems.rosenet.net/
    The latest version of these scripts and other PHP/MySQL utilities can be
    found at:
    http://modems.rosenet.net/mysql/
    Copyright, 1998-1999 Rosenet Inc.
    */
    ?>

