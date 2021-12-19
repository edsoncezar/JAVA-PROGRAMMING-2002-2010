<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:html>
<head>
<title><bean:message key="global.title"/></title>
<script language=javascript src="include/scripts.js"></script>
<link rel="stylesheet" href="../stylesheets/format_win_nav_main.css" type="text/css">

<html:javascript formName="checkoutForm" dynamicJavascript="true" staticJavascript="true" src="staticJavascript.jsp"/>

</head>

<body>


<!-- Header Page Information -->
<%@ include file="../include/head.inc"%>

<!-- Nav Bar -->
<%@ include file="../include/menubar.inc"%>
  <ul>
    <html:messages id="message">
      <li><bean:write name="message"/></li>
    </html:messages>
  </ul>
  
<html:img alt="Shipping Address" page="/order/shipping_files/image004.gif" border="0"/>  

<table border=0 cellspacing=0 cellpadding=0 width=460 bgcolor="#cccccc">
 <tr>
  <td width=10>
   <p>
   <img width=5 height=18 src="./shipping_files/image005.gif" border=0>
   </p>
  </td>
  <td width="100%" style='width:100.0%;padding:0in 0in 0in 0in'>
  <p><b><span style='font-size:7.0pt;font-family:Verdana'>Enter Shipping Address</span></b></p>
  </td>
 </tr>
</table>


<img width=1 height=6 src="./shipping_files/image006.gif" border=0><br>
 <span style='font-size:7.0pt;font-family:Verdana'>Please complete the following shipping information for this order.<b>
<br><br>
 <span>All shipping information is required.</span></b>
<br>
<img width=1 height=10 src="./shipping_files/image007.gif" border=0>


<html:form action="getPaymentInfo" onsubmit="return validateCheckoutForm(this);">
<table border=0 cellpadding=0>
 <tr>
  <td width=10 valign=top style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
    <p>&nbsp;</p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><span class=feature-text1><b><span style='font-size:7.0pt;font-family:Verdana'>First Name</span></b></span></p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><html:text property="firstName"/></p>
  </td>
 </tr>
 <tr>
  <td width=10 style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
  <p class=MsoNormal>&nbsp;<span style='mso-bidi-font-size:12.0pt;color:black'><o:p></o:p></span></p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><b><span style='font-size:7.0pt; font-family:Verdana'>Last Name</b></span>
  </p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><html:text property="lastName"/></p>
  </td>
 </tr>
 <tr>
  <td width=10 style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
  <p>&nbsp;</p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><b><span style='font-size:7.0pt;font-family:Verdana'>Street Address</span></b></p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><html:text property="address"/></p>
  </td>
 </tr> 
 <tr>
  <td width=10 style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
  <p>&nbsp;</p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><b><span style='font-size:7.0pt; font-family:Verdana'>City</b></span></p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><html:text property="city"/></p>
  </td>
 </tr>
 <tr>
  <td width=10 style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
  <p>&nbsp;</p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><b><span style='font-size:7.0pt;font-family:Verdana'>State</b></p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><SELECT NAME="state">
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
</SELECT></p>
  </td>
 </tr>
 <tr>
  <td width=10 style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
  <p>&nbsp;</p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><b><span style='font-size:7.0pt;font-family:Verdana'>ZIP/Postal Code</b></span><span style='mso-bidi-font-size:
  12.0pt;color:black'><o:p></o:p></span></p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><html:text size="10" property="postalCode"/></p>
  </td>
 </tr>
 <tr>
  <td width=10 style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
  <p>&nbsp;</p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><b><span style='font-size:7.0pt;font-family:Verdana'>Country</b></span></p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
   <p>
   <SELECT NAME="country">
     <OPTION SELECTED VALUE="US">USA
   </SELECT>
   </p>
  </td>
 </tr>
 <tr>
  <td width=10 style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
  <p>&nbsp;</p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><b><span style='font-size:7.0pt;font-family:Verdana'>Phone</b></span></p>
  </td>
  <td style='padding:.75pt .75pt .75pt .75pt'>
  <p><html:text size="10" property="phone"/></p>
  </td>
 </tr>
 <tr>
  <td width=10 style='width:7.5pt;padding:.75pt .75pt .75pt .75pt'>
  <p>&nbsp;</p>
  </td>
 </tr> 
</table>

</div>
<table>
 <tr><td width="10"></td><td></td>
 <td>
 
 <html:link page="/action/getPaymentInfo">
   <input type="image" alt="Continue Shopping" src="/images/continue.gif" border="0" onclick="return validateCheckoutForm(this);"/>
 </html:link>
 
 <html:submit value="Submit" onclick="return validateCheckoutForm(this);"/>

  
  </td>
 </tr> 
</table>
</html:form>


</body>
</html:html>
