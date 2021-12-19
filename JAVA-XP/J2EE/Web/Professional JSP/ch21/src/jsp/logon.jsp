<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html locale="true">
<head>
<title><bean:message key="logon.title"/></title>
<html:base/>
</head>
<body bgcolor="white">
<%@ include file="header.html" %>
<html:errors/>
<html:form action="/logon.do" focus="userName">
<table border="0" width="100%">
  <tr>
    <th align="right">
      <bean:message key="prompt.userName"/>
    </th>
    <td align="left">
      <html:text property="userName" size="16" maxlength="16"/>
    </td>
  </tr>
  <tr>
    <th align="right">
      <bean:message key="prompt.password"/>
    </th>
    <td align="left">
      <html:password property="password" size="16" maxlength="16"/>
    </td>
  </tr>
  <tr>
    <td align="right">
      <html:submit>
        <bean:message key="button.submit"/>
      </html:submit>
    </td>
    <td align="left">
      <html:reset>
        <bean:message key="button.reset"/>
      </html:reset>
      <html:cancel>
        <bean:message key="button.cancel"/>
      </html:cancel>
    </td>
  </tr>
</table>
</html:form>
</body>
</html:html>
