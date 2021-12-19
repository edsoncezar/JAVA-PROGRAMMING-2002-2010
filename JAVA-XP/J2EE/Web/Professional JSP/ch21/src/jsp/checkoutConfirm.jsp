<%@ page language="java" 
  import="java.util.Collection,
          java.util.Iterator,
          java.util.Locale,
          java.util.Map,
          java.util.Map.Entry,
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
  <title><bean:message key="checkout.title" /></title>
</head>
<body>
<html:base/>
<%@ include file="header.html" %>
<p></p>
<table cellspacing="2" cellpadding="2" border="0">
  <tr>
    <td width="120">&nbsp;</td>
    <td align="left">
      <bean:message key="checkout.instructions" />
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
  CD cd = null;
  CartItem item = null;
  Iterator it = cart.values().iterator();
  String actionString = null;
  while(it.hasNext()) {
    num++;
    item = (CartItem)it.next();
    cd = item.getCD();
    if((num % 2) == 0) {
      bgColor = SLATE;
    } else {
      bgColor = WHITE;
    }
%>
        <tr bgcolor="<%= bgColor %>">
            <td><%= cd.getArtist() %></td>
            <td><%= cd.getTitleName() %></td>
            <td><%= cd.getPrice() %></td>
            <td valign="middle">
              <html:form action="/checkout.do">
                <html:hidden property="action" value="update" />
                <html:hidden property="<%= Constants.TITLE_ID %>" 
                                value="<%= String.valueOf(cd.getTitleId()) %>" />
                <table cellspacing="2" cellpadding="2" border="0">
                  <tr>
                      <td>
                        <html:text property="quantity" 
                                      value="<%= String.valueOf(item.getQuantity()) %>" 
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
            <td><%= item.getTotal(locale) %></td>
        </tr>
<%
  } //end while
%>
        <tr bgcolor="#B0E0E6">
            <td colspan="4" align="right"><bean:message key="tableheading.total" /></td>
            <td><b><%= cart.getTotal(locale) %></b></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  <td>&nbsp;</td>
  <td>
    <html:form action="/checkout.do">
      <html:hidden property="action" value="next" />
      <table cellspacing="2" cellpadding="2" border="0" width="508">
        <tr>
          <td width="281">&nbsp;</td>
          <td width="124" align="right"><bean:message key="checkout.next" /></td>
          <td width="83"> 
            <html:submit>
               <bean:message key="button.next"/>
            </html:submit>
          </td>
        </tr>
      </table>
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
