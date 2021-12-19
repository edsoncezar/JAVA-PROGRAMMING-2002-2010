<%@ page language="java" 
  import="java.util.Locale,
          com.wrox.pjsp2.struts.common.Constants,
          com.wrox.pjsp2.struts.common.ShoppingCart,
          org.apache.struts.action.Action
          "
%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<app:checkLogon/>
<html:html locale="true">
<head>
  <title><bean:message key="showcategories.title" /></title>
</head>
<body>
<%@ include file="headerWithCart.jsp" %>
<html:errors/>
<table cellspacing="2" cellpadding="2" border="0">
  <tr>
    <td width="120">
      <table align="left" cellspacing="0" cellpadding="0" border="0" width="145">
        <tr><td><b><bean:message key="showcategories.heading"/></b></td></tr>
        <tr><td>&nbsp;</td></tr>
<logic:iterate id="category" type="com.wrox.pjsp2.struts.common.Category" 
                             name="<%= Constants.CATEGORIES_ARRAY_KEY %>">
        <tr>
          <td>
            <html:link page="/showCDs.do" name="category" property="mapping">
              <bean:write name="category" property="categoryName" filter="true"/>
            </html:link>
          </td>
        </tr>
</logic:iterate>
      </table>
    </td>
    <td>
      &nbsp;
    </td>
  </tr>
</table>
<p></p>
<%
  boolean omitCheckoutLink = false;
%>
<%@ include file="footer.jsp"%>
</body>
</html:html>
