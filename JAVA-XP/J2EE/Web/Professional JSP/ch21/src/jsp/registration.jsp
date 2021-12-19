<%@ page language="java" import="com.wrox.pjsp2.struts.common.Constants" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<logic:equal name="registrationForm" property="action"
              scope="request" value="Edit">
  <app:checkLogon/>
</logic:equal>
<%@ include file="header.html" %>
<html:html locale="true">
<head>
<logic:equal name="registrationForm" property="action"
            scope="request" value="Create">
  <title><bean:message key="registration.title.create"/></title>
</logic:equal>
<logic:equal name="registrationForm" property="action"
            scope="request" value="Edit">
  <title><bean:message key="registration.title.edit"/></title>
</logic:equal>
</head>
<html:base/>
<body>
<html:errors/>
<!-- save is needed for both Create and Edit -->
<html:form action="/saveRegistration.do">
  <table align="center" cellspacing="0" cellpadding="0" border="0" width="480">
  <html:hidden property="action"/>
<!-- START: Create a new User -->
<logic:equal name="registrationForm" property="action"
              scope="request" value="Create">
  <tr>
    <td><bean:message key="registration.header1.create"/></td>
  </tr>
  <tr>
    <td>
      <p><bean:message key="registration.create"/></p><p></p>
    </td>
  </tr>
  <tr><td><bean:message key="registration.note"/></td></tr>
  <tr>
  <table align="center" cellspacing="3" cellpadding="0" border="0" width="480">
  <tr>
    <td width="120"><bean:message key="prompt.user.title"/></td>
    <td colspan="2">
    <html:select property="user.title" size="1">
        <html:options collection="<%= Constants.TITLE_ARRAY_KEY %>"
                                      property="value"
                                  labelProperty="label"/>
    </html:select>
    </td>
  </tr>
  <tr>
    <td width="120"><bean:message key="prompt.fullName"/></td>
    <td colspan="2">
    <html:text property="user.firstName" size="15" maxlength="25"/>
    <html:text property="user.lastName" size="17" maxlength="25"/>
  </td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.user.email"/></td>
     <td colspan="2">
       <html:text property="user.email" size="32" maxlength="32"/>
     </td>
  </tr>
  <tr>
      <td width="120"><bean:message key="prompt.userName"/></td>
      <td colspan="2">
        <html:text property="user.userName" size="34" maxlength="30"/>
      </td>
  </tr>
  <tr>
      <td width="120"><bean:message key="prompt.password"/></td>
      <td colspan="2">
        <html:password property="user.password" size="34" maxlength="30"/>
      </td>
  </tr>
  <tr>
      <td width="120"><bean:message key="prompt.confirmPassword"/></td>
      <td colspan="2">
        <html:password property="user.confirmPassword" size="34" maxlength="30"/>
      </td>
  </tr>
  <tr>
      <td width="120"><bean:message key="prompt.passwordHint"/></td>
      <td colspan="2">
        <html:text property="user.passwordHint" size="34" maxlength="30"/>
      </td>
  </tr>
</logic:equal>
<!-- END: Create a new User -->
<!-- START: Edit an existing User -->
<logic:equal name="registrationForm" property="action"
              scope="request" value="Edit">
<%
  String selectedValue = (String)request.getAttribute(Constants.SELECTED_OPTION_KEY);
  log("selectedValue = " + selectedValue);
%>
  <tr>
    <td><bean:message key="registration.header1.edit"/></td>
  </tr>
  <tr>
    <td>
      <p><bean:message key="registration.edit"/></p><p></p>
    </td>
  </tr>
  <tr><td><bean:message key="registration.note"/></td></tr>
  <tr>
  <table align="center" cellspacing="3" cellpadding="0" border="0" width="480">
  <tr>
    <td width="120"><bean:message key="prompt.user.title"/></td>
    <td colspan="2">
    <html:select property="user.title" size="1" value="<%= selectedValue %>">
        <html:options collection="<%= Constants.TITLE_ARRAY_KEY %>"
                                      property="value"
                                  labelProperty="label"/>
    </html:select>
    </td>
  </tr>
  <tr>
    <td width="120"><bean:message key="prompt.fullName"/></td>
    <td colspan="2">
    <html:text property="user.firstName" size="15" maxlength="25" />
    <html:text property="user.lastName" size="17" maxlength="25" />
  </td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.user.email"/></td>
     <td colspan="2"><html:text property="user.email" size="32" maxlength="32"/></td>
  </tr>
</logic:equal>
<!-- END: Edit an existing User -->
<!-- Common to both Create and Edit -->
  <tr>
    <td colspan="3">&nbsp;</td>
  </tr>
  <tr>
    <th colspan="3"><bean:message key="registration.user.address"/></th>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.address"/></td>
     <td colspan="2">
       <html:text property="user.userAddress.address1" size="34" maxlength="50"/>
     </td>
  </tr>
  <tr>
     <td width="120">&nbsp;</td>
     <td colspan="2">
       <html:text property="user.userAddress.address2" size="34" maxlength="50"/>
     </td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.city"/></td>
     <td colspan="2">
       <html:text property="user.userAddress.city" size="15" maxlength="50"/>
     </td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.state"/></td>
     <td colspan="2">
       <html:text property="user.userAddress.state" size="4" maxlength="5"/>
     </td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.zip"/></td>
     <td colspan="2">
       <html:text property="user.userAddress.zip" size="10" maxlength="15"/>
     </td>
  </tr>
  <tr>
    <td colspan="3">&nbsp;</td>
  </tr>
  <tr>
    <th colspan="3"><bean:message key="registration.billing.address"/></td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.address"/></td>
     <td colspan="2">
       <html:text property="user.billingAddress.address1" size="34" maxlength="50"/>
     </td>
  </tr>
  <tr>
     <td width="120">&nbsp;</td>
     <td colspan="2">
       <html:text property="user.billingAddress.address2" size="34" maxlength="50"/>
     </td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.city"/></td>
     <td colspan="2">
       <html:text property="user.billingAddress.city" size="15" maxlength="50"/>
     </td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.state"/></td>
     <td colspan="2">
       <html:text property="user.billingAddress.state" size="4" maxlength="5"/>
     </td>
  </tr>
  <tr>
     <td width="120"><bean:message key="prompt.zip"/></td>
     <td colspan="2">
       <html:text property="user.billingAddress.zip" size="10" maxlength="15"/>
     </td>
  </tr>
  <tr>
    <td colspan="3">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2">
      <table cellspacing="2" cellpadding="2" border="0">
        <tr>
          <td>
            <html:submit>
               <bean:message key="button.submit"/>
            </html:submit>
          </td>
          <td>
            <html:cancel>
               <bean:message key="button.cancel"/>
            </html:cancel>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </table>
  </tr>
  </table>
</html:form>
</body>
</html:html>
</html>
