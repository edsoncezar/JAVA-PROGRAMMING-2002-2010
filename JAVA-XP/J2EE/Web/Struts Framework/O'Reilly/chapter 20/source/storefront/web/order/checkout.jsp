<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:html>
<head>
<title><bean:message key="global.title"/></title>
<html:base/>
<script language=javascript src="include/scripts.js"></script>
<link rel="stylesheet" href="../stylesheets/format_win_nav_main.css" type="text/css">
</head>

<body topmargin="0" leftmargin="0" bgcolor="#FFFFFF">

<!-- Header Page Information -->
<%@ include file="../include/head.inc"%>

<!-- Nav Bar -->
<%@ include file="../include/menubar.inc"%>

<br>

 Display order summary and take credit card information here
 
 <html:form action="/processcheckout">
     <html:submit property="action">
      <bean:message key="button.checkout"/>
     </html:submit>
 </html:form>
 
 
<br><br>
<%@ include file="../include/copyright.inc"%>
</body>

</html:html>
