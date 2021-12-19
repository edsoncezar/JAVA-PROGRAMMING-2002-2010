<%@ page language="java" 
  import="com.wrox.pjsp2.struts.common.Constants,
          com.wrox.pjsp2.struts.common.User
"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<app:checkLogon/>
<html:html locale="true">
<head>
  <title><bean:message key="thankyou.title" /></title>
</head>
<body>
<html:base/>
<%
  User user = (User)session.getAttribute(Constants.USER_KEY);
%>
<%@ include file="header.html" %>
<p></p>
<table cellspacing="2" cellpadding="2" border="0">
  <tr>
    <td width="120">&nbsp;</td>
    <td align="center">
      <h3><bean:message key="thankyou.title" /></h3>
    </td>
  </tr>
  <tr>
    <td width="120">&nbsp;</td>
    <td align="center">
      <table cellspacing="2" cellpadding="2" border="0">
        <tr>
          <td>
            <bean:message key="thankyou.message" 
                         arg0="<%= user.getDisplayName() %>" />
          </td>
        </tr>
        <tr>
        <td><bean:message key="thankyou.billingMsg"/></td>
        </tr>
        <tr>
          <td>
            <bean:message key="thankyou.orderNumber"/>
            <b>
              <bean:write name="orderForm" property="orderNumber" scope="session"/>
            </b>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</html:form>
<%
  boolean omitCheckoutLink = true;
%>
<%@ include file="footer.jsp"%>
</body>
<%
  session.removeAttribute(Constants.SHOPPING_CART_KEY);
  System.out.println("removing shopping cart from the session.");
  session.removeAttribute("orderForm");
  System.out.println("removing orderForm from the session.");
%>
</html:html>
