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
  <title><bean:message key="order.title" /></title>
</head>
<body>
<html:base/>
<html:errors/>
<%@ include file="header.html" %>
<p></p>
<table cellspacing="2" cellpadding="2" border="0">
  <tr>
    <td width="120">&nbsp;</td>
    <td align="center">
      <h3><bean:message key="order.title" /></h3>
    </td>
  </tr>
<html:form action="/order.do">
  <html:hidden property="action" value="updateAddress" />
  <tr>
    <td width="120">&nbsp;</td>
    <td align="center">
      <table align="center" cellspacing="2" cellpadding="2" border="1">
      <tr bgcolor="#B0E0E6">
          <th><h4><bean:message key="order.billToHeader"/></h4></th>
          <th><h4><bean:message key="order.shipToHeader"/></h4></th>
      </tr>
      <tr>
        <td>
          <html:text name="orderForm" property="shippingInformation.firstName"
                           size="15" maxlength="25"/>
          <html:text name="orderForm" property="shippingInformation.lastName" 
                           size="17" maxlength="25"/>
        </td>
        <td>
          <html:text name="orderForm" property="billingInformation.firstName"
                           size="15" maxlength="25"/>
          <html:text name="orderForm" property="billingInformation.lastName" 
                           size="17" maxlength="25"/>
        </td>
      </tr>
      <tr>
          <td>
            <html:text name="orderForm" 
                     property="shippingInformation.address.address1" 
                         size="34" maxlength="50"/>
          </td>
          <td>
            <html:text name="orderForm" 
                     property="billingInformation.address.address1" 
                         size="34" maxlength="50"/>
          </td>
      </tr>
      <tr>
          <td>
            <html:text name="orderForm" 
                     property="shippingInformation.address.address2" 
                         size="34" maxlength="50"/>
          </td>
          <td>
            <html:text name="orderForm" 
                     property="billingInformation.address.address2" 
                         size="34" maxlength="50"/>
          </td>
      </tr>
      <tr>
          <td>
            <html:text name="orderForm" 
                     property="shippingInformation.address.city" 
                         size="15" maxlength="50"/>
          </td>
          <td>
            <html:text name="orderForm" 
                     property="billingInformation.address.city" 
                         size="15" maxlength="50"/>
          </td>
      </tr>
      <tr>
          <td>
            <html:text name="orderForm" 
                     property="shippingInformation.address.state" 
                         size="4" maxlength="5"/>
          </td>
          <td>
            <html:text name="orderForm" 
                     property="billingInformation.address.state" 
                         size="4" maxlength="5"/>
          </td>
      </tr>
      <tr>
          <td>
            <html:text name="orderForm" 
                     property="shippingInformation.address.zip" 
                         size="10" maxlength="15"/>
          </td>
          <td>
            <html:text name="orderForm" 
                     property="billingInformation.address.zip" 
                         size="10" maxlength="15"/>
          </td>
      </tr>
      <tr>
          <td colspan="2" align="center">
                <html:submit>
                   <bean:message key="button.updateAddress"/>
                </html:submit>
          </td>
      </tr>
      </table>
    </td>
  </tr>
</html:form>
<html:form action="/order.do">
  <tr>
    <td width="120">&nbsp;</td>
    <td align="center">
      <table align="center" cellspacing="2" cellpadding="2" border="1">
        <tr bgcolor="#B0E0E6">
            <th><h4><bean:message key="order.paymentInformation"/></h4></th>
        </tr>
        <tr>
          <td>
            <table align="center" cellspacing="2" cellpadding="2" border="0">
              <tr>
                <td align="center"><bean:message key="order.creditCardType"/></td>
                <td align="center"><bean:message key="order.creditCardNumber"/></td>
                <td colspan="2" align="center"><bean:message key="order.expirationDate"/></td>
              </tr>
              <tr>
                  <td>
                    <!-- note: label and value the same -->
                    <html:select property="creditCardType" size="1">
                      <html:options collection="<%= Constants.CCTYPES_ARRAY_KEY %>" 
                                      property="value"
                                  labelProperty="label"/>
                    </html:select>
                  </td>
                  <td><html:text property="creditCardNumber" size="17" maxlength="16"/></td>
                  <td>
                    <html:select property="expireMonth" size="1">
                      <html:options collection="<%= Constants.MONTHS_ARRAY_KEY %>"
                                      property="value"
                                  labelProperty="label"/>
                    </html:select>
                  </td>
                  <td>
                    <html:select property="expireYear" size="1">
                      <html:options collection="<%= Constants.YEARS_ARRAY_KEY %>"
                                      property="value"
                                  labelProperty="label"/>
                    </html:select>
                  </td>
              </tr>
            </table> <!-- end CC table-->
          </td>
        </tr>
      </table> <!-- end Payment Info table -->
    </td>
  </tr>
  <tr>
    <td width="120">&nbsp;</td>
    <td valign="top" align="center">
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
<logic:iterate id="cartItem" type="com.wrox.pjsp2.struts.common.CartItem" 
             name="<%= Constants.SHOPPING_CART_KEY %>" property="cartItems">
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
              <bean:define id="quantity" name="cartItem" property="quantity"/>
              <%= String.valueOf(quantity) %>
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
  <html:hidden property="action" value="placeOrder" />
        <html:submit>
           <bean:message key="button.placeOrder"/>
        </html:submit>
  </td>
  </tr>
</table>
</html:form>
<%
  boolean omitCheckoutLink = true;
%>
<%@ include file="footer.jsp"%>
</body>
</html:html>
