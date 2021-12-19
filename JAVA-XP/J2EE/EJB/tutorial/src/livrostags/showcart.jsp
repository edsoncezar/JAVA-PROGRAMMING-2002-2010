<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ taglib uri="/struts-bean" prefix="bean" %>
<%@ taglib uri="/struts-logic" prefix="logic" %>
<%@ include file="initdestroy.jsp" %>
<%@ page import="java.util.*" %>
<%
   ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
%>

<jsp:useBean id="bookDB" class="database.BookDB" scope="page" >
  <jsp:setProperty name="bookDB" property="database" value="<%=bookDBEJB%>" />
</jsp:useBean>
<jsp:useBean id="currency" class="util.Currency" scope="session">
  <jsp:setProperty name="currency" property="locale" value="<%=request.getLocale()%>"/>
</jsp:useBean>

<jsp:useBean id="cart" class="cart.ShoppingCart" scope="session"/>

<logic:present parameter="Remove">
    <bean:parameter id="bookId" name="Remove" />
    <% cart.remove(bookId); %>
    <jsp:setProperty name="bookDB" property="bookId" value="<%=bookId%>" />
    <bean:define id="book" name="bookDB" property="bookDetails" type="database.BookDetails"/>
    <font color="red" size="+2"><%=messages.getString("CartRemoved")%><em><jsp:getProperty name="book" property="title"/></em> 
    <br>&nbsp;<br> 
    </font>
</logic:present>

<logic:present parameter="Clear">
    <% cart.clear(); %>
    <font color="red" size="+2"><strong> 
    <%=messages.getString("CartCleared")%> 
    </strong><br>&nbsp;<br></font>
</logic:present>

<bean:define id="num" name="cart" property="numberOfItems" />

<logic:greaterThan name="num" value="0" >

    <font size="+2"><%=messages.getString("CartContents")%>
    <%=num%>
    <logic:equal name="num" value="1">
      <%=messages.getString("CartItem")%>.
    </logic:equal>
    <logic:greaterThan name="num" value="1">
      <%=messages.getString("CartItems")%>.
    </logic:greaterThan>
    </font><br>&nbsp;

    <table> 
    <tr> 
    <th align=left><%=messages.getString("ItemQuantity")%></TH> 
    <th align=left><%=messages.getString("ItemTitle")%></TH> 
    <th align=left><%=messages.getString("ItemPrice")%></TH> 
    </tr>

    <logic:iterate name="cart" property="items" id="item" type="cart.ShoppingCartItem">
        <bean:define id="book" name="item" property="item" type="database.BookDetails"/>
        <bean:define id="bookId" name="book" property="bookId" type="java.lang.String"/>
        <tr> 
        <td align="right" bgcolor="#ffffff"> 
        <jsp:getProperty name="item" property="quantity" />
        </td> 

        <td bgcolor="#ffffaa"> 
        <strong><a href="<%=request.getContextPath()%>/bookdetails?bookId=<%=bookId%>"><jsp:getProperty name="book" property="title"/></a></strong> 
        </td> 

        <td bgcolor="#ffffaa" align="right"> 
        <jsp:setProperty name="currency" property="amount" value="<%=book.getPrice()%>"/>
        <jsp:getProperty name="currency" property="format"/>&nbsp;</td>  
        </td> 

        <td bgcolor="#ffffaa"> 
        <strong><a href="<%=request.getContextPath()%>/showcart?Remove=<%=bookId%>"><%=messages.getString("RemoveItem")%></a></strong> 
        </td></tr>

    </logic:iterate>

    <tr><td colspan="5" bgcolor="#ffffff"> 
    <br></td></tr> 

    <tr> 
    <td colspan="2" align="right" "bgcolor="#ffffff"> 
    <%=messages.getString("Subtotal")%></td> 
    <td bgcolor="#ffffaa" align="right"> 
    <jsp:setProperty name="currency" property="amount" value="<%=cart.getTotal()%>"/>
    <jsp:getProperty name="currency" property="format"/>&nbsp; </td> 
    </td><td><br></td></tr></table> 

    <p>&nbsp;<p>
    <strong><a href="<%=request.getContextPath()%>/catalog"><%=messages.getString("ContinueShopping")%></a>&nbsp;&nbsp;&nbsp;  
    <a href="<%=request.getContextPath()%>/cashier"><%=messages.getString("Checkout")%></a>&nbsp;&nbsp;&nbsp; 
    <a href="<%=request.getContextPath()%>/showcart?Clear=clear"><%=messages.getString("ClearCart")%></a></strong>
</logic:greaterThan>

<logic:lessEqual name="num" value="0" >
    <font size="+2"><%=messages.getString("CartEmpty")%></font> 
    <br>&nbsp;<br> 
    <strong><a href="<%=request.getContextPath()%>/catalog"><%=messages.getString("Catalog")%></a></strong>
</logic:lessEqual>

</body>
</html>

