<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ taglib uri="/struts-bean" prefix="bean" %>
<%@ taglib uri="/struts-logic" prefix="logic" %>
<%@ page import="java.util.*" %>
<%
   ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
%>

<jsp:useBean id="cart" class="cart.ShoppingCart" scope="session"/>
<jsp:useBean id="currency" class="util.Currency" scope="session">
  <jsp:setProperty name="currency" property="locale" value="<%=request.getLocale()%>"/>
  <jsp:setProperty name="currency" property="amount" value="<%=cart.getTotal()%>"/>
</jsp:useBean>

<p><%=messages.getString("Amount")%>
<strong><jsp:getProperty name="currency" property="format"/></strong>
</strong>
<p><%=messages.getString("Purchase")%>
<form action="<%=request.getContextPath()%>/receipt" method="post">
<table>
<tr>
<td><strong><%=messages.getString("Name")%></strong></td>
<td><input type="text" name="cardname" value="Gwen Canigetit" size="19"></td>
</tr>
<tr>
<td><strong><%=messages.getString("CCNumber")%></strong></td>
<td><input type="text" name="cardnum" value="xxxx xxxx xxxx xxxx" size="19"></td>
</tr>
<tr>
<td></td>
<td><input type="submit" value="<%=messages.getString("Submit")%>"></td>
</tr>
</table>
</form>

