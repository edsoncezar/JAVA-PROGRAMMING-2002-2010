<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:html>
<head>
<title><bean:message key="global.title"/></title>
<html:base/>
<script language=javascript src="../include/scripts.js"></script>
<link rel="stylesheet" href="../stylesheets/format_win_nav_main.css" type="text/css">
</head>

<body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">

<!-- Header Page Information -->
<%@ include file="../include/head.inc"%>

<!-- Nav Bar -->
<%@ include file="../include/menubar.inc"%>
<br>
<br>

<table cellSpacing=0 cellPadding=0 width=480 border=0>
  <tbody>
   <tr>
     <td width=480>&nbsp;
     <td vAlign=top width="100%">
      <table cellSpacing=0 cellPadding=0 width=480 border=0>
       <tbody>
         <tr>
         <td vAlign=top width=20><html:img height="1" alt="" page="/images/spacer.gif" width="20" border="0"/></td>
         <td vAlign=top width=460>

	<!-- PRODUCT INFO -->
	<table cellSpacing=0 cellPadding=0 width=510 border=0>
	<tbody>
	<tr>
	<td vAlign=top width=105>
	<img alt="Product" src="<bean:write name="itemDetailForm" property="view.largeImageURL"/>" border="0">
	<BR>

        <html:img height="10" alt="" page="/images/spacer.gif" width="105" border="0"/><BR>
        <font face="Verdana, Arial, Helvetica" size=2><SPAN class=feature-text><font face="Verdana, Arial, Helvetica" size=2></font></SPAN></font></td>
        <td width=10><html:img height="1" alt="" page="/images/spacer.gif" width="10" border="0"/></td>
	<td vAlign=top width=245><font face="Verdana, Arial, Helvetica" color=#cc9900 size=3><B><SPAN class=body-headline-black>

	<!-- PRODUCT DESCRIPTION -->
	<bean:write name="itemDetailForm" property="view.name"/><BR>

	</SPAN></B></font><font face="Verdana, Arial, Helvetica" size=2><SPAN class=feature-text>
	 <!-- PRODUCT MODEL -->Model:<bean:write name="itemDetailForm" property="view.modelNumber"/><BR><BR><!-- /PRODUCT MODEL --></SPAN></font>
	 <font face="Verdana, Arial, Helvetica" size=2><SPAN class=body-text>

	<!-- PRODUCT DESCRIPTION -->
	<bean:write name="itemDetailForm" property="view.description"/><BR><BR></SPAN></font><BR>
	
	<table cellSpacing=0 cellPadding=0 width=245 border=0>
	<tbody>
	<tr>
	<td vAlign=top width=130><font face="Verdana, Arial, Helvetica" size=2><SPAN class=feature-text>
	<font face="Verdana, Arial, Helvetica" size=2><SPAN class=feature-text-red><B>In Stock</B></SPAN></font>
	<font face="Verdana, Arial, Helvetica" size=2><SPAN class=feature-text>- Usually ships in 2-3 business days. </SPAN></font> </SPAN></font></td>
	<td width=10><html:img height="1" alt="" page="/images/spacer.gif" width="10" border="0"/></td>
	<td vAlign=top width=105><!-- PRICE BLOCK -->
	<table cellSpacing=0 cellPadding=0 width=105 border=0>
	<tbody>

	<tr>
        <td width=5 bgColor=#ffcc00><html:img height="1" alt="" page="/images/spacer.gif" width="5" border="0"/></td>
	<td vAlign=center noWrap align=right width=55 bgColor=#ffcc00><font face="Verdana, Arial, Helvetica" size=2><B>
	<SPAN class=price-text>$<bean:write name="itemDetailForm" property="view.unitPrice"/><BR></SPAN></B></font></td>
        <td width=2 bgColor=#ffcc00><html:img height="1" alt="" page="/images/spacer.gif" width="2" border="0"/></td>
 	<td width=43>

	<html:link
	  page="/action/cart?method=addItem"
	  paramId="id"
	  paramName="itemDetailForm"
	  paramProperty="view.id"
	  paramId="id"
	  paramScope="request">
	  <html:img height="18" alt="Buy" page="/catalog/images/buynow.gif" width="43" border="0"/>
	</html:link>
	<BR></td></tr>
	<tr>
        <td align=left colSpan=4><html:img height="3" alt="" page="/images/spacer.gif" width="1" border="0"/><BR>
	<html:img height="12" alt="Free Shipping" page="/catalog/images/freeshipping.gif" width="105" border="0"/><BR></td></tr></tbody></table></td></tr></tbody></table>
	</td>
	<td width=15><html:img height="1" alt="" page="/images/spacer.gif" width="10" border="0"/></td>
	<td vAlign=top width=135>
	</td></tr></tbody></table><html:img height="5" alt="" page="/images/spacer.gif" width="1" border="0"/><BR>

	<font face="Verdana, Arial, Helvetica" color=#cc9900 size=3><SPAN class=body-headline-gold><A name=features>PRODUCT FEATURES</A>
	</SPAN></font><BR>
	<table cellSpacing=0 cellPadding=0 width=460 border=0>
	<tbody>
	<tr>
	<td vAlign=top width=12>
	<html:img height="10" alt="" page="/catalog/images/square.gif" width="12" border="0"/></td>
	<td width=3><html:img height="1" alt="" page="/images/spacer.gif" width="3" border="0"/></td>
	<td vAlign=top><font face="Verdana, Arial, Helvetica" size=2><SPAN class=feature-text><bean:write name="itemDetailForm" property="view.productFeature"/></SPAN></font><BR></td></tr>
	</tbody></table>

</tr></tbody></table>

<%@ include file="../include/copyright.inc"%>
</body>

</html:html>

