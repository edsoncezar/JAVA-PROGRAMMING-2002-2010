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

<logic:present parameter="Add">
    <bean:parameter id="bookId" name="Add" />
    <jsp:setProperty name="bookDB" property="bookId" value="<%=bookId%>" />
    <bean:define id="book" name="bookDB" property="bookDetails" type="database.BookDetails"/>
    <% cart.add(bookId, book); %>

    <p><h3><font color="red" size="+2"> 
    <%=messages.getString("CartAdded1")%> <em><jsp:getProperty name="book" property="title"/></em> <%=messages.getString("CartAdded2")%></font></h3>
</logic:present>

<logic:greaterThan name="cart" property="numberOfItems" value="0" >
    <p><strong><a href="<%=request.getContextPath()%>/showcart"><%=messages.getString("CartCheck")%></a>&nbsp;&nbsp;&nbsp;
    <a href="<%=request.getContextPath()%>/cashier"><%=messages.getString("Buy")%></a></p></strong>
</logic:greaterThan>

<br>&nbsp;
<br>&nbsp;
<h3><%=messages.getString("Choose")%></h3>

<center>
<table>
<logic:iterate name="bookDB" property="books" id="book" type="database.BookDetails">
    <bean:define id="bookId" name="book" property="bookId" type="java.lang.String"/>
    <tr> 
    <td bgcolor="#ffffaa"> 
    <a href="<%=request.getContextPath()%>/bookdetails?bookId=<%=bookId%>"><strong><jsp:getProperty name="book" property="title"/>&nbsp;</strong></a></td> 

    <td bgcolor="#ffffaa" rowspan=2> 
    <jsp:setProperty name="currency" property="amount" value="<%=book.getPrice()%>"/>
    <jsp:getProperty name="currency" property="format"/>
    &nbsp;</td> 

    <td bgcolor="#ffffaa" rowspan=2> 
    <a href="<%=request.getContextPath()%>/catalog?Add=<%=bookId%>">&nbsp;<%=messages.getString("CartAdd")%>&nbsp;</a></td></tr> 

    <tr> 
    <td bgcolor="#ffffff"> 
    &nbsp;&nbsp;<%=messages.getString("By")%> <em><jsp:getProperty name="book" property="firstName"/>&nbsp;<jsp:getProperty name="book" property="surname"/></em></td></tr>

</logic:iterate>

</table>
</center>

