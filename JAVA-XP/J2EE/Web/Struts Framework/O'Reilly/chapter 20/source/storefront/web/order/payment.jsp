<html>
<head></head>

<body lang=EN-US link=blue vlink=red style='tab-interval:.5in'>

<div>

<table border=0 cellspacing=0 cellpadding=0 width=480>
 <tr>
  <td width=20 valign=top style='width:15.0pt;padding:0in 0in 0in 0in'>
  <p></p>
  </td>
  <td width=460 valign=top style='width:345.0pt;padding:0in 0in 0in 0in'>
  <p><script language="JavaScript">
<!--
function formCheck(){
	var SelectedShipID = 8503472;
	var check;
	check = false;
	
	if (SelectedShipID != 0 ){
		check = document.MyForm["useShipAdd"].value
	}
	message = "";
	if (document.MyForm["addCardType"].value == ""){message += "  -- Credit Card Type\n";}
	if (document.MyForm["addCardNumber"].value == ""){message += "  -- Credit Card Number\n";}
	if ((document.MyForm["addCardType"].value != "Best Buy") && ((document.MyForm["addCardExpMonth"].value == "") || (document.MyForm["addCardExpYear"].value == ""))){message += "  -- Expiration Date\n";}
	if ((document.MyForm["addFirstName"].value == "") && (check == false)){message += "  -- First Name\n";}
	if ((document.MyForm["addLastName"].value == "") && (check == false)){message += "  -- Last Name\n";}
	if ((document.MyForm["addAddress1"].value == "") && (check == false)){message += "  -- Address\n";}
	if ((document.MyForm["addCity"].value == "") && (check == false)){message += "  -- City\n";}
	if ((document.MyForm["addState"].value == "") && (check == false)){message += "  -- State\n";}
	if ((document.MyForm["addPostalCode"].value == "") && (check == false)){message += "  -- ZIP/Postal Code\n";}
	if ((document.MyForm["addCountry"].value == "") && (check == false)){message += "  -- Country\n";}
	if (((document.MyForm["addDayPhone1"].value == "") || (document.MyForm["addDayPhone2"].value == "") || (document.MyForm["addDayPhone3"].value == "")) && (check == false)){message += "  -- Daytime Phone\n";}
	if (((document.MyForm["addEvePhone1"].value == "") || (document.MyForm["addEvePhone2"].value == "") || (document.MyForm["addEvePhone3"].value == "")) && (check == false)){message += "  -- Evening Phone\n";}
	
	if (message != ""){
		message = "In order to continue with the checkout process,\nyou must enter the following information:\n\n" + message + "\nPlease click OK, enter the required information,\nand re-submit your request.";
		alert(message);
		return false;
	}
}

function checkCard(){
	var field = document.MyForm["addCardNumber"]
	if (field.value == "") {
		return true;
	}else{
		alert("Please enter a valid Card Number.\n(Do not use hyphens or spaces.)");
		field.focus();
		field.select();
		return false;
	}
}

function checkBBCard(){
	var string1 = ""
	for (i=0; i<6; i++){
		myString = new String(document.MyForm["addCardNumber"].value);
		mychar = myString.charAt(i);
		string1 += mychar;
	}
	if ((string1 == "534819") || (string1 == "601917")){
			window.open('/Checkout/popups/pickNewCard.asp?type=addCardType','newCard','toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,width=580,height=480,top=100,left=200');
		document.MyForm["addCardType"].value="";
		document.MyForm["addCardNumber"].value="";
	}
}

function checkPhone(field,digits){
	for (var i=0; i<document.MyForm[field].value.length;i++){
	temp=document.MyForm[field].value.substring(i,i+1);
		if (".".indexOf(temp) != -1){   //checks to make sure there is no decimal point, i.e., it is a whole number
			alert("Please enter a " + digits + "-digit number.");
			document.MyForm[field].focus();
			document.MyForm[field].select();
			return false;
		}
	}
	if ((document.MyForm[field].value == "") || ((document.MyForm[field].value.length == digits) && (document.MyForm[field].value > 0))){
		return true;
	}else{
		alert("Please enter a " + digits + "-digit number.");
		document.MyForm[field].focus();
		document.MyForm[field].select();
		return false;
	}
}

function BBCard(){
	if (document.MyForm["addCardType"].value == "Best Buy"){
		document.MyForm["addCardExpMonth"].value = "";
		document.MyForm["addCardExpYear"].value = "";
	}
}

function populate(){
	
	if (document.MyForm["useShipAdd"].checked)
		{
		document.MyForm.addFirstName.value = "a";
		document.MyForm.addLastName.value = "a";
		document.MyForm.addAddress1.value = "a";
		document.MyForm.addAddress2.value = "a";
		document.MyForm.addCity.value = "snellville";
		
		for (i = 0; i < document.MyForm.addState.length; i++)
		{
			if (document.MyForm.addState.options[i].value == "GA")
			{
				document.MyForm.addState.options.selectedIndex = i;
			}
		}
		
		document.MyForm.addPostalCode.value = "30039";
		document.MyForm.addDayPhone1.value = "317";
		document.MyForm.addDayPhone2.value = "637";
		document.MyForm.addDayPhone3.value = "1111";
		document.MyForm.addEvePhone1.value = "317";
		document.MyForm.addEvePhone2.value = "637";
		document.MyForm.addEvePhone3.value = "1111";
		
		}
   else {
		document.MyForm.addFirstName.value = "";
		document.MyForm.addLastName.value = "";
		document.MyForm.addAddress1.value = "";
		document.MyForm.addAddress2.value = "";
		document.MyForm.addCity.value = "";
		document.MyForm.addState.options.selectedIndex = 0;
		document.MyForm.addPostalCode.value = "";
		document.MyForm.addDayPhone1.value = "";
		document.MyForm.addDayPhone2.value = "";
		document.MyForm.addDayPhone3.value = "";
		document.MyForm.addEvePhone1.value = "";
		document.MyForm.addEvePhone2.value = "";
		document.MyForm.addEvePhone3.value = "";
		
		}
} 

// Deactivates <enter> 
IE4 = (document.all);
NS4 = (document.layers);

if (NS4) document.captureEvents(Event.KEYPRESS);
document.onkeypress = doKey;

function doKey(e) {
	whichASC = (NS4) ? e.which : event.keyCode;
	if (whichASC == 13 || whichASC == 3) {
		// determine how to submit form
		if (document.MyForm["digitalcoupons"].value == "") {
		// no input for coupons, so continue
			document.MyForm.refresh.value = "1"
			document.MyForm.actor.value = "ADD";
			document.MyForm.submit();	
		} else {
		// process the input for coupons
			document.MyForm.refresh.value = "1"
			document.MyForm.actor.value = "NEWDISCOUNT";
			document.MyForm.submit();
		}
		return false;
	} else {
	return true;
	}
}
//-->
  </script><script  src="/javascripts/checkZip.js">
  </script><o:p></o:p></p>
  <form method=Get enctype="application/x-www-form-urlencoded">
  <p class=MsoNormal><span style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="primaryCard" VALUE="Y"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="addAddr" VALUE="8503210"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="actor" VALUE="CONTINUE"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="refresh" VALUE="0"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddCountry" VALUE="US"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddState" VALUE="GA"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddCity" VALUE="snellville"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddPostalCode" VALUE="30039"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddHomePhone" VALUE="3176371111"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddWorkPhone" VALUE="3176371111"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddAddress1" VALUE="a"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddAddress2" VALUE="a"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddFirstName" VALUE="a"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="o_strAddLastName" VALUE="a"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="Email" VALUE="foofoobar@yahoo.com"></span><span
  style='display:none;mso-hide:all'><INPUT TYPE="hidden" NAME="old_Email" VALUE="foofoobar@yahoo.com"></span><!-- MAIN BODY CONTENT--><!--[if gte vml 1]><v:shapetype
   id="_x0000_t75" coordsize="21600,21600" o:spt="75" o:preferrelative="t"
   path="m@4@5l@4@11@9@11@9@5xe" filled="f" stroked="f">
   <v:stroke joinstyle="miter"/>
   <v:formulas>
    <v:f eqn="if lineDrawn pixelLineWidth 0"/>
    <v:f eqn="sum @0 1 0"/>
    <v:f eqn="sum 0 0 @1"/>
    <v:f eqn="prod @2 1 2"/>
    <v:f eqn="prod @3 21600 pixelWidth"/>
    <v:f eqn="prod @3 21600 pixelHeight"/>
    <v:f eqn="sum @0 0 1"/>
    <v:f eqn="prod @6 1 2"/>
    <v:f eqn="prod @7 21600 pixelWidth"/>
    <v:f eqn="sum @8 21600 0"/>
    <v:f eqn="prod @7 21600 pixelHeight"/>
    <v:f eqn="sum @10 21600 0"/>
   </v:formulas>
   <v:path o:extrusionok="f" gradientshapeok="t" o:connecttype="rect"/>
   <o:lock v:ext="edit" aspectratio="t"/>
  </v:shapetype><v:shape id="_x0000_i1027" type="#_x0000_t75" alt="" style='width:.75pt;
   height:.75pt'>
   <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
  </v:shape><![endif]--><![if !vml]><img width=1 height=1
  src="./payment_files/image001.gif" border=0 v:shapes="_x0000_i1027"><![endif]><br>
  <!--[if gte vml 1]><v:shape id="_x0000_i1028" type="#_x0000_t75" alt="Payment Options"
   style='width:345pt;height:20.25pt'>
   <v:imagedata src="./payment_files/image002.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/checkout/images/tl_paymentOpt.gif"/>
  </v:shape><![endif]--><![if !vml]><img width=460 height=27
  src="./payment_files/image002.gif" alt="Payment Options" border=0 v:shapes="_x0000_i1028"><![endif]><br>
  <!--[if gte vml 1]><v:shape id="_x0000_i1029" type="#_x0000_t75" alt=""
   style='width:.75pt;height:3.75pt'>
   <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
  </v:shape><![endif]--><![if !vml]><img width=1 height=5
  src="./payment_files/image003.gif" border=0 v:shapes="_x0000_i1029"><![endif]><br>
<!-- TOPIC HEADER GRAPHIC--><!--[if gte vml 1]><v:shape id="_x0000_i1030"
   type="#_x0000_t75" alt="Payment Options" style='width:345pt;height:26.25pt'>
   <v:imagedata src="./payment_files/image004.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/checkout/images/headPaymentOpt.gif"/>
  </v:shape><![endif]--><![if !vml]><img width=460 height=35
  src="./payment_files/image004.gif" alt="Payment Options" border=0 v:shapes="_x0000_i1030"><![endif]><br
  style='mso-special-character:line-break'>
  <![if !supportLineBreakNewLine]><br style='mso-special-character:line-break'>
  <![endif]><o:p></o:p></p>
<!-- /TOPIC HEADER GRAPHIC-->
  <table border=0 cellspacing=0 cellpadding=0 width=460 bgcolor="#cccccc"
   style='width:345.0pt;mso-cellspacing:0in;background:#CCCCCC;mso-padding-alt:
   0in 0in 0in 0in'>
   <tr>
    <td width=10 style='width:.1in;padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1031" type="#_x0000_t75"
     alt="" style='width:3.75pt;height:13.5pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img width=5 height=18
    src="./payment_files/image005.gif" border=0 v:shapes="_x0000_i1031"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td width="100%" style='width:100.0%;padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>Enter Payment Method(s)</span></b></span><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
  </table>
  <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1032" type="#_x0000_t75"
   alt="" style='width:.75pt;height:4.5pt'>
   <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
  </v:shape><![endif]--><![if !vml]><img width=1 height=6
  src="./payment_files/image006.gif" border=0 v:shapes="_x0000_i1032"><![endif]><br>
<!-- ADD CREDIT CARD INFO --><!--[if gte vml 1]><v:shape id="_x0000_i1033"
   type="#_x0000_t75" alt="" style='width:.75pt;height:4.5pt'>
   <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
  </v:shape><![endif]--><![if !vml]><img width=1 height=6
  src="./payment_files/image007.gif" border=0 v:shapes="_x0000_i1033"><![endif]><br>
  <span class=feature-text1><span style='font-size:7.0pt;font-family:Verdana'>Please
  enter your credit card information and any digital coupons you may have. </span></span><span
  style='font-size:7.0pt;font-family:Verdana'><br>
  <br>
  <span class=feature-text1><b>All information is required.</b></span><br>
  </span><!--[if gte vml 1]><v:shape id="_x0000_i1034" type="#_x0000_t75"
   alt="" style='width:.75pt;height:.75pt'>
   <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
  </v:shape><![endif]--><![if !vml]><img width=1 height=1
  src="./payment_files/image001.gif" border=0 v:shapes="_x0000_i1034"><![endif]><span
  style='color:black'><o:p></o:p></span></p>
  <table border=0 cellspacing=0 cellpadding=0 width=460 style='width:345.0pt;
   mso-cellspacing:0in;mso-padding-alt:0in 0in 0in 0in'>
   <tr>
    <td valign=top style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><span style='font-size:7.0pt;
    font-family:Verdana'>&nbsp;</span></span><span style='font-size:7.0pt;
    font-family:Verdana'><br>
    <span class=feature-text1><SELECT NAME="addCardType">
<OPTION SELECTED VALUE="">Select a Card
<OPTION VALUE="AE">American Express
<OPTION VALUE="BB">Best Buy
<OPTION VALUE="DS">Discover
<OPTION VALUE="MC">MasterCard
<OPTION VALUE="VS">VISA
</SELECT></span></span><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td valign=top style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>Credit Card Number</span></b></span><span
    style='font-size:7.0pt;font-family:Verdana'><br>
    </span><INPUT TYPE="TEXT" MAXLENGTH="19" SIZE="19" NAME="addCardNumber"
    onblur="javascript:checkBBCard()"><span style='mso-bidi-font-size:12.0pt;
    color:black'><o:p></o:p></span></p>
    </td>
    <td width=10 rowspan=2 style='width:.1in;background:#CCCCCC;padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1035" type="#_x0000_t75"
     alt="" style='width:.75pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img width=1 height=1
    src="./payment_files/image001.gif" border=0 v:shapes="_x0000_i1035"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td width=10 rowspan=2 style='width:.1in;padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1036" type="#_x0000_t75"
     alt="" style='width:.75pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img width=1 height=1
    src="./payment_files/image001.gif" border=0 v:shapes="_x0000_i1036"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td width=100 rowspan=2 valign=top style='width:75.0pt;padding:0in 0in 0in 0in'>
    <p class=MsoNormal align=center style='text-align:center'><span
    style='font-size:7.0pt;font-family:Verdana'><!--[if gte vml 1]><v:shape
     id="_x0000_i1037" type="#_x0000_t75" alt="" style='width:28.5pt;height:18pt;
     mso-wrap-distance-top:2.25pt;mso-wrap-distance-bottom:2.25pt'>
     <v:imagedata src="./payment_files/image008.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/BBcard.gif"/>
    </v:shape><![endif]--><![if !vml]><img width=38 height=24
    src="./payment_files/image008.gif" vspace=3 border=0 v:shapes="_x0000_i1037"><![endif]><o:p></o:p></span></p>
    <p class=MsoNormal><span class=feature-text1><span style='font-size:7.0pt;
    font-family:Verdana'>Pay <a
    href="javascript:popUpMenu('/HomePage/creditCard.asp','Credit_Card_Application')">no
    interest</a> until January 2003 when you use your Best Buy credit card.</span></span><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
   <tr>
    <td colspan=2 style='padding:0in 0in 0in 0in'>
    <table border=0 cellpadding=0 style='mso-cellspacing:1.5pt'>
     <tr>
      <td style='padding:.75pt .75pt .75pt .75pt'>
      <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
      7.0pt;font-family:Verdana'>Expiration Date</span></b></span><span
      style='font-size:7.0pt;font-family:Verdana'><br>
      <span class=feature-text1><SELECT NAME="addCardExpMonth">
<OPTION VALUE="01">01
<OPTION VALUE="02">02
<OPTION VALUE="03">03
<OPTION VALUE="04">04
<OPTION VALUE="05">05
<OPTION SELECTED VALUE="06">06
<OPTION VALUE="07">07
<OPTION VALUE="08">08
<OPTION VALUE="09">09
<OPTION VALUE="10">10
<OPTION VALUE="11">11
<OPTION VALUE="12">12
</SELECT><SELECT NAME="addCardExpYear">
<OPTION SELECTED VALUE="2002">2002
<OPTION VALUE="2003">2003
<OPTION VALUE="2004">2004
<OPTION VALUE="2005">2005
<OPTION VALUE="2006">2006
<OPTION VALUE="2007">2007
<OPTION VALUE="2008">2008
<OPTION VALUE="2009">2009
<OPTION VALUE="2010">2010
<OPTION VALUE="2011">2011
<OPTION VALUE="2012">2012
</SELECT></span></span><span
      style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
      </td>
      <td valign=top style='padding:.75pt .75pt .75pt .75pt'>
      <p class=MsoNormal><span style='font-size:7.0pt;font-family:Verdana'><br>
      <span class=feature-text1>No expiration date is required</span><br>
      <span class=feature-text1>if you are using a Best Buy credit card.</span></span><span
      style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
      </td>
     </tr>
    </table>
    <p class=MsoNormal><span style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
  </table>
  <p class=MsoNormal><span style='font-size:7.0pt;font-family:Verdana'><br>
  <span class=feature-text1><b>Billing Address</b></span><br>
  <span class=feature-text1>Your Billing address must be entered as it appears
  on your statement. Please check your credit card statement to avoid delays in
  processing your order.</span><br>
  <!--[if gte vml 1]><v:shape id="_x0000_i1038" type="#_x0000_t75" alt=""
   style='width:.75pt;height:3.75pt'>
   <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
  </v:shape><![endif]--><![if !vml]><img border=0 width=1 height=5
  src="./payment_files/image009.gif" v:shapes="_x0000_i1038"><![endif]><br>
  <span class=feature-text1><INPUT TYPE="checkbox" NAME="useShipAdd" VALUE="1"
  onclick="javascript:populate()">Use my shipping address.</span><br>
  <!--[if gte vml 1]><v:shape id="_x0000_i1039" type="#_x0000_t75" alt=""
   style='width:.75pt;height:3.75pt'>
   <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
  </v:shape><![endif]--><![if !vml]><img border=0 width=1 height=5
  src="./payment_files/image010.gif" v:shapes="_x0000_i1039"><![endif]></span><span
  style='color:black'><o:p></o:p></span></p>
  <table border=0 cellspacing=0 cellpadding=0 style='mso-cellspacing:0in;
   mso-padding-alt:0in 0in 0in 0in'>
   <tr>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>First Name</span></b></span><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>Last Name</span></b></span><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
   <tr>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><INPUT TYPE="TEXT" MAXLENGTH="30" SIZE="30" NAME="addFirstName"><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><INPUT TYPE="TEXT" MAXLENGTH="30" SIZE="30" NAME="addLastName"><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
<!-- <input type="text" name="addLastName" size="30" maxlength="30" value=""> --></td>
   </tr>
   <tr>
    <td colspan=2 style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>Address</span></b></span><span style='mso-bidi-font-size:
    12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
   <tr>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><INPUT TYPE="TEXT" MAXLENGTH="30" SIZE="30" NAME="addAddress1"><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><INPUT TYPE="TEXT" MAXLENGTH="30" SIZE="30" NAME="addAddress2"><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
  </table>
  <p class=MsoNormal><span style='display:none;mso-hide:all'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>
  <table border=0 cellspacing=0 cellpadding=0 style='mso-cellspacing:0in;
   mso-padding-alt:0in 0in 0in 0in'>
   <tr>
    <td nowrap valign=bottom style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>City</span></b></span><span class=feature-text1><span
    style='font-size:7.0pt;font-family:Verdana'> </span></span><br>
<INPUT TYPE="TEXT" MAXLENGTH="28" SIZE="15" NAME="addCity"><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1040" type="#_x0000_t75"
     alt="" style='width:3.75pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=5 height=1
    src="./payment_files/image011.gif" v:shapes="_x0000_i1040"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td valign=bottom style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>State</span></b></span><span
    class=feature-text1><span style='font-size:7.0pt;font-family:Verdana'> </span></span><br>
<SELECT NAME="addState">
<OPTION SELECTED VALUE="">Select a State
<OPTION VALUE="AK">ALASKA
<OPTION VALUE="AL">ALABAMA
<OPTION VALUE="AR">ARKANSAS
<OPTION VALUE="AZ">ARIZONA
<OPTION VALUE="CA">CALIFORNIA
<OPTION VALUE="CO">COLORADO
<OPTION VALUE="CT">CONNECTICUT
<OPTION VALUE="DC">DIST. COLUMBIA
<OPTION VALUE="DE">DELAWARE
<OPTION VALUE="FL">FLORIDA
<OPTION VALUE="GA">GEORGIA
<OPTION VALUE="GU">GUAM
<OPTION VALUE="HI">HAWAII
<OPTION VALUE="IA">IOWA
<OPTION VALUE="ID">IDAHO
<OPTION VALUE="IL">ILLINOIS
<OPTION VALUE="IN">INDIANA
<OPTION VALUE="KS">KANSAS
<OPTION VALUE="KY">KENTUCKY
<OPTION VALUE="LA">LOUISIANA
<OPTION VALUE="MA">MASSACHUSETTS
<OPTION VALUE="MD">MARYLAND
<OPTION VALUE="ME">MAINE
<OPTION VALUE="MI">MICHIGAN
<OPTION VALUE="MN">MINNESOTA
<OPTION VALUE="MO">MISSOURI
<OPTION VALUE="MS">MISSISSIPPI
<OPTION VALUE="MT">MONTANA
<OPTION VALUE="NC">NORTH CAROLINA
<OPTION VALUE="ND">NORTH DAKOTA
<OPTION VALUE="NE">NEBRASKA
<OPTION VALUE="NH">NEW HAMPSHIRE
<OPTION VALUE="NJ">NEW JERSEY
<OPTION VALUE="NM">NEW MEXICO
<OPTION VALUE="NV">NEVADA
<OPTION VALUE="NY">NEW YORK
<OPTION VALUE="OH">OHIO
<OPTION VALUE="OK">OKLAHOMA
<OPTION VALUE="OR">OREGON
<OPTION VALUE="PA">PENNSYLVANIA
<OPTION VALUE="PR">PUERTO RICO
<OPTION VALUE="RI">RHODE ISLAND
<OPTION VALUE="SC">SOUTH CAROLINA
<OPTION VALUE="SD">SOUTH DAKOTA
<OPTION VALUE="TN">TENNESSEE
<OPTION VALUE="TX">TEXAS
<OPTION VALUE="UT">UTAH
<OPTION VALUE="VA">VIRGINIA
<OPTION VALUE="VI">US VIRGIN ISLANDS
<OPTION VALUE="VT">VERMONT
<OPTION VALUE="WA">WASHINGTON
<OPTION VALUE="WI">WISCONSIN
<OPTION VALUE="WV">WEST VIRGINIA
<OPTION VALUE="WY">WYOMING
</SELECT><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1041" type="#_x0000_t75"
     alt="" style='width:.75pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=1 height=1
    src="./payment_files/image001.gif" v:shapes="_x0000_i1041"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td nowrap valign=bottom style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>Zip/Postal Code</span></b></span><span
    class=feature-text1><span style='font-size:7.0pt;font-family:Verdana'> </span></span><br>
<INPUT TYPE="TEXT" MAXLENGTH="5" SIZE="5" NAME="addPostalCode"
    onblur="javascript:checkZip('addPostalCode')"
    onkeyup="javascript:invalidZip('addPostalCode')"><span style='mso-bidi-font-size:
    12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
  </table>
  <p class=MsoNormal><span style='display:none;mso-hide:all'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>
  <table border=0 cellspacing=0 cellpadding=0 style='mso-cellspacing:0in;
   mso-padding-alt:0in 0in 0in 0in'>
   <tr>
    <td nowrap style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>Country</span></b></span><span style='mso-bidi-font-size:
    12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1042" type="#_x0000_t75"
     alt="" style='width:7.5pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=10 height=1
    src="./payment_files/image012.gif" v:shapes="_x0000_i1042"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>Daytime Phone</span></b></span> <span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1043" type="#_x0000_t75"
     alt="" style='width:.75pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=1 height=1
    src="./payment_files/image001.gif" v:shapes="_x0000_i1043"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td nowrap style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><span class=feature-text1><b><span style='font-size:
    7.0pt;font-family:Verdana'>Evening Phone</span></b></span><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
   <tr>
    <td nowrap style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><SELECT NAME="addCountry">
<OPTION SELECTED VALUE="US">USA
</SELECT><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1044" type="#_x0000_t75"
     alt="" style='width:.75pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=1 height=1
    src="./payment_files/image001.gif" v:shapes="_x0000_i1044"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><INPUT TYPE="TEXT" MAXLENGTH="3" SIZE="3" NAME="addDayPhone1"
    onblur="javascript:checkPhone('addDayPhone1',3)"><INPUT TYPE="TEXT" MAXLENGTH="3" SIZE="3" NAME="addDayPhone2"
    onblur="javascript:checkPhone('addDayPhone2',3)"><INPUT TYPE="TEXT" MAXLENGTH="4" SIZE="4" NAME="addDayPhone3"
    onblur="javascript:checkPhone('addDayPhone3',4)"><span style='mso-bidi-font-size:
    12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1045" type="#_x0000_t75"
     alt="" style='width:7.5pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=10 height=1
    src="./payment_files/image013.gif" v:shapes="_x0000_i1045"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td nowrap style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><INPUT TYPE="TEXT" MAXLENGTH="3" SIZE="3" NAME="addEvePhone1"
    onblur="javascript:checkPhone('addEvePhone1',3)"><INPUT TYPE="TEXT" MAXLENGTH="3" SIZE="3" NAME="addEvePhone2"
    onblur="javascript:checkPhone('addEvePhone2',3)"><INPUT TYPE="TEXT" MAXLENGTH="4" SIZE="4" NAME="addEvePhone3"
    onblur="javascript:checkPhone('addEvePhone3',4)"><span style='mso-bidi-font-size:
    12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
  </table>
<!-- SPACER ROWS -->
  <p class=MsoNormal><span style='display:none;mso-hide:all'><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></span></p>
  <table border=0 cellspacing=0 cellpadding=0 width="100%" style='width:100.0%;
   mso-cellspacing:0in;mso-padding-alt:0in 0in 0in 0in'>
   <tr>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1046" type="#_x0000_t75"
     alt="" style='width:.75pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=1 height=1
    src="./payment_files/image001.gif" v:shapes="_x0000_i1046"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
   <tr>
    <td style='background:#CCCCCC;padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1047" type="#_x0000_t75"
     alt="" style='width:49.5pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=66 height=1
    src="./payment_files/image014.gif" v:shapes="_x0000_i1047"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
   <tr>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1048" type="#_x0000_t75"
     alt="" style='width:.75pt;height:.75pt'>
     <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=1 height=1
    src="./payment_files/image001.gif" v:shapes="_x0000_i1048"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
<!-- /SPACER ROWS -->
  </table>
<!-- /CREDIT CARD INFO --><!-- /Shipping info --><!-- DIGITAL COUPONS -->
  <p class=MsoNormal><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></p>
  <p class=MsoNormal align=right style='text-align:right'><INPUT TYPE="image" SRC="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/btn_continue.gif" NAME="ADD"
  height=18 alt=Continue border=0><o:p></o:p></p>
  </form>
<!-- /TOP BODY CONTENT-->
  <p class=MsoNormal><span class=feature-text1><span style='font-size:7.0pt;
  font-family:Verdana'>Checkout questions? Call 111-111-1111</span></span><span
  style='color:black'><o:p></o:p></span></p>
  <table border=0 cellspacing=0 cellpadding=0 width=460 bgcolor=black
   style='width:345.0pt;mso-cellspacing:0in;background:black;mso-padding-alt:
   0in 0in 0in 0in'>
   <tr>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><!--[if gte vml 1]><v:group id="_x0000_s1026" style='position:absolute;
     margin-left:0;margin-top:0;width:179.25pt;height:10.5pt;z-index:1;
     mso-position-horizontal-relative:char;mso-position-vertical-relative:line'
     coordsize="3585,210">
     <v:rect id="_x0000_s1027"
      href="http://www.bestbuy.com/infoCenter/Policies/privacy.asp?b=0"
      style='position:absolute;left:2445;width:1140;height:210;
      mso-position-horizontal:absolute;mso-position-horizontal-relative:char;
      mso-position-vertical:absolute;mso-position-vertical-relative:line'
      o:button="t" filled="f" stroked="f">
      <v:fill o:detectmouseclick="t"/>
     </v:rect><v:rect id="_x0000_s1028"
      href="http://www.bestbuy.com/infoCenter/Policies/TOSlegal.asp?b=0"
      style='position:absolute;width:2430;height:210;mso-position-horizontal:absolute;
      mso-position-horizontal-relative:char;mso-position-vertical:absolute;
      mso-position-vertical-relative:line' o:button="t" filled="f" stroked="f">
      <v:fill o:detectmouseclick="t"/>
     </v:rect><w:anchorlock/>
    </v:group><![endif]--><![if !vml]><span style='mso-ignore:vglayout;
    position:absolute;z-index:0;margin-left:0px;margin-top:0px;width:239px;
    height:14px'><map name=MicrosoftOfficeMap0><area shape=Rect
     coords="0, 0, 162, 14"
     href="http://www.bestbuy.com/infoCenter/Policies/TOSlegal.asp?b=0"><area
     shape=Rect coords="163, 0, 239, 14"
     href="http://www.bestbuy.com/infoCenter/Policies/privacy.asp?b=0"></map><img
    border=0 width=239 height=14 src="./payment_files/image015.gif"
    usemap="#MicrosoftOfficeMap0" v:shapes="_x0000_s1026 _x0000_s1027 _x0000_s1028"></span><![endif]><!--[if gte vml 1]><v:shape
     id="_x0000_i1061" type="#_x0000_t75" alt="" style='width:180pt;height:11.25pt'>
     <v:imagedata src="./payment_files/image016.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/navBottom.gif"/>
    </v:shape><![endif]--><![if !vml]><img border=0 width=240 height=15
    src="./payment_files/image016.gif" v:shapes="_x0000_i1061"><![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
    <td style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal>&nbsp;<span style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
  </table>
  <p class=MsoNormal><!--[if gte vml 1]><v:shape id="_x0000_i1062" type="#_x0000_t75"
   alt="" style='width:.75pt;height:4.5pt'>
   <v:imagedata src="./payment_files/image001.gif" o:href="https://a248.e.akamai.net/f/248/5500/3h/origin.images.bestbuy.com/images/spacer.gif"/>
  </v:shape><![endif]--><![if !vml]><img border=0 width=1 height=6
  src="./payment_files/image017.gif" v:shapes="_x0000_i1062"><![endif]><span
  style='color:black'><o:p></o:p></span></p>
  <table border=0 cellspacing=0 cellpadding=0 align=right style='mso-cellspacing:
   0in;mso-table-anchor-vertical:paragraph;mso-table-anchor-horizontal:column;
   mso-table-left:right;mso-table-top:middle;mso-padding-alt:0in 0in 0in 0in'>
   <tr>
    <td valign=bottom style='padding:0in 0in 0in 0in'>
    <p class=MsoNormal><![if !supportEmptyParas]>&nbsp;<![endif]><span
    style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
    </td>
   </tr>
  </table>
  <p class=MsoNormal style='margin-bottom:12.0pt'><span style='mso-bidi-font-size:
  12.0pt;color:black'><o:p></o:p></span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><![if !supportEmptyParas]>&nbsp;<![endif]><o:p></o:p></p>

</div>

</body>

</html>
