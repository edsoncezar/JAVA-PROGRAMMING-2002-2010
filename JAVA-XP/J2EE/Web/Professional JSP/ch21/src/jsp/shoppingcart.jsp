<%@ page language="java" 
  import="java.util.Collection,
          java.util.Iterator,
          java.util.Locale,
          java.util.HashMap,
          com.wrox.pjsp2.struts.common.CartItem,
          com.wrox.pjsp2.struts.common.CD,
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
  <title><bean:message key="shoppingcart.title" /></title>
</head>
<body>
<html:base/>
<%@ include file="header.html" %>
<p></p>
<table cellspacing="2" cellpadding="2" border="0">
  <tr>
    <td width="120">&nbsp;</td>
    <td align="center">
      <h1><bean:message key="shoppingcart.title" /></h1>
    </td>
  </tr>
  <tr>
    <td width="120">&nbsp;</td>
    <td align="left">
      <bean:message key="shoppingcart.instructions" />
    </td>
  </tr>
  <tr>
    <td width="120">&nbsp;</td>
    <td valign="top">
      <p></p>
      <table cellspacing="2" cellpadding="2" border="1">
        <tr bgcolor="#B0E0E6">
          <th><bean:message key="tableheading.artist" /></th>
          <th><bean:message key="tableheading.title" /></th>
          <th><bean:message key="tableheading.unitPrice" /></th>
          <th><bean:message key="tableheading.quantity" /></th>
          <th><bean:message key="tableheading.itemTotal" /></th>
        </tr>
<%
  int num = 0;
  String SLATE = "#C0C0C0";
  String WHITE = "#FFFFFF";
  String bgColor = null;
  Locale locale = (Locale) session.getAttribute(Action.LOCALE_KEY);
  ShoppingCart cart = (ShoppingCart)session.getAttribute(Constants.SHOPPING_CART_KEY);
  if(cart == null) {
    cart = new ShoppingCart();
    session.setAttribute(Constants.SHOPPING_CART_KEY, cart);
  }
  cart.setLocale(locale);
%>
<logic:iterate id="cartItem" 
             type="com.wrox.pjsp2.struts.common.CartItem" 
             name="<%= Constants.SHOPPING_CART_KEY %>"
         property="cartItems">
<%
  num++;
  if((num % 2) == 0) {
    bgColor = SLATE;
  } else {
    bgColor = WHITE;
  }
%>
  <bean:define id="cd" name="cartItem" property="cd" type="com.wrox.pjsp2.struts.common.CD"/>
        <tr bgcolor="<%= bgColor %>">
            <td><jsp:getProperty name="cd" property="artist"/></td>
            <td><jsp:getProperty name="cd" property="titleName"/></td>
            <td><jsp:getProperty name="cd" property="price"/></td>
            <td valign="middle">
              <html:form action="/checkout.do">
                <html:hidden property="action" value="update" />
                <bean:define id="titleId" name="cd" property="titleId"/>
                <html:hidden property="<%= Constants.TITLE_ID %>" 
                                value="<%= String.valueOf(titleId) %>" />
                <table cellspacing="2" cellpadding="2" border="0">
                  <tr>
                      <td>
                        <bean:define id="quantity" name="cartItem" property="quantity"/>
                        <html:text property="quantity" 
                                      value="<%= String.valueOf(quantity) %>" 
                                       size="5" maxlength="5"/>
                      </td>
                      <td>
                        <html:submit>
                           <bean:message key="button.update"/>
                        </html:submit>
                      </td>
                  </tr>
                </table>
              </html:form>
            </td>
            <td><jsp:getProperty name="cartItem" property="total"/></td>
        </tr>

</logic:iterate>
        <tr bgcolor="#B0E0E6">
            <td colspan="4" align="right"><bean:message key="tableheading.total" /></td>
            <td><b><%= cart.getTotal() %></b></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  <td>&nbsp;</td>
  <td align="center">
    <html:form action="/order.do">
      <html:hidden property="action" value="createOrder" />
        <html:submit>
           <bean:message key="button.createOrder"/>
        </html:submit>
    </html:form>
  </td>
  </tr>
</table>
<%
  boolean omitCheckoutLink = true;
%>
<%@ include file="footer.jsp"%>
</body>
</html:html>
