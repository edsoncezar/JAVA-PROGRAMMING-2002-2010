//**************************************
    //     
    // Name: Base64 Encode/Decode
    // Description:Encode/Decode via the bui
    //     lt in functions base64_encode & base64_d
    //     ecode
    // By: Demian Net
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.214/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    <?
    // Base64 Encode/Decode
    // Writer: Max - Demian Net
    // E-Mail: Max@Wackowoh.com
    // WebSite: http://www.DemianNet.com htt
    //     p://www.Wackowoh.com
    // Language: PHP
    ?>
    <FORM>
    	<INPUT TYPE=TEXT NAME=code VALUE="<?echo $code?>">
    	<INPUT TYPE=SUBMIT NAME=action VALUE="Encode">
    	<INPUT TYPE=SUBMIT NAME=action VALUE="Decode">
    </FORM><BR>
    <?
    	if($action == "Encode") {
    		echo "<B><FONT FACE=Arial>Output:</FONT></B><BR>";
    		echo base64_encode($code);
    	} elseif($action == "Decode") {
    		echo "<B><FONT FACE=Arial>Output:</FONT></B><BR>";
    		echo base64_decode($code);
    	}
    ?>
