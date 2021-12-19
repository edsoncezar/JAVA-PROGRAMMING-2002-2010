<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:html>
<head>
<title><bean:message key="global.title"/></title>
<html:base/>
<script language=javascript src="include/scripts.js"></script>
<link rel="stylesheet" href="stylesheets/format_win_nav_main.css" type="text/css">
</head>

<body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">

<!-- Header Page Information -->
<%@ include file="include/head.inc"%>


<!-- Nav Bar -->
<%@ include file="include/menubar.inc"%>
		
<%@ include file="include/mainoffer.inc"%>	
<!-- Featured Items Header Row -->
<table width="645" cellpadding="0" cellspacing="0" border="0">
 <tr>   
  <td width="21"><html:img height="1" alt="" page="/images/spacer.gif" width="1" border="0"/></td>
  <td width="534"><html:img page="/images/week_picks.gif" altKey="label.featuredproducts"/></td>
  <td width="1" bgcolor="#9E9EFF"><html:img height="1" alt="" page="/images/spacer.gif" width="1" border="0"/></td>
  <td width="1"  bgcolor="#9E9EFF"><html:img height="1" alt="" page="/images/spacer.gif" width="1" border="0"/></td>
  <td width="90" bgcolor="#9E9EFF" align="right"><html:img height="1" alt="" page="/images/spacer.gif" width="90" border="0"/></td>
 </tr>
 <tr>
  <td><html:img height="1" alt="" page="/images/spacer.gif" width="21" border="0"/></td>
  <td colspan="4" bgcolor="#9E9EFF"><html:img height="1" alt="" page="/images/spacer.gif" width="1" border="0"/></td>
 </tr>
</table>

<html:img height="10" alt="" page="/images/spacer.gif" width="1" border="0"/><br>

<!-- THIS WEEK'S FEATURES -->
<%@ include file="include/featureditems.inc"%>

<%@ include file="include/copyright.inc"%>
</body>
</html:html>