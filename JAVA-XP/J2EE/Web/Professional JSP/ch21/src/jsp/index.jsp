<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html locale="true">
<head>
<title><bean:message key="index.title"/></title>
<html:base/>
</head>
<body bgcolor="white">
<%@ include file="header.html" %>
<logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
  <font color="red">
    ERROR:  Application resources not loaded -- check servlet container
    logs for error messages.
  </font>
</logic:notPresent>
<bean:message key="index.heading"/>
<ul>
  <li>
    <html:link page="/editRegistration.do?action=Create">
      <bean:message key="index.registration"/>
    </html:link>
  </li>
  <li>
    <html:link page="/logon.jsp">
      <bean:message key="index.logon"/>
    </html:link>
  </li>
</ul>
</body>
</html:html>
