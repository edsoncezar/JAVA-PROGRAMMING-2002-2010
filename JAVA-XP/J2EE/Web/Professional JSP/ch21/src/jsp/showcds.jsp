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
  <title><bean:message key="showcds.title" /></title>
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
    <td valign="top">
      <p></p>
      <table cellspacing="2" cellpadding="2" border="1">
      <tr bgcolor="#B0E0E6">
        <th>Artist</th>
        <th>Title</th>
        <th>Price</th>
        <th>Add to Cart</th>
      </tr>
<% 
  int num = 0;
  String SLATE = "#C0C0C0";
  String WHITE = "#FFFFFF";
  String bgColor = null;
%>
<logic:iterate id="cd" type="com.wrox.pjsp2.struts.common.CD" 
                       name="<%= Constants.CD_ARRAY_KEY %>">
<% 
  num++;
  if((num % 2) == 0) {
    bgColor = SLATE;
  } else {
    bgColor = WHITE;
  }
%>
      <tr bgcolor="<%= bgColor %>">
        <td><bean:write name="cd" property="artist" filter="true"/></td>
        <td><bean:write name="cd" property="titleName" filter="true"/></td>
        <td><bean:write name="cd" property="price" filter="true"/></td>
        <td>
          <html:link page="/addToCart.do" name="cd" property="mapping">
            <bean:message key="cds.add"/>
          </html:link>
        </td>
      </tr>
</logic:iterate>
      </table>
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
