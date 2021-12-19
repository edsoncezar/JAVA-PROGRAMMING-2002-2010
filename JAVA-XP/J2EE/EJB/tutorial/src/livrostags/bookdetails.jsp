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

<logic:present parameter="bookId">
    <bean:parameter id="bookId" name="bookId" />
    <jsp:setProperty name="bookDB" property="bookId"/>
    <bean:define id="book" name="bookDB" property="bookDetails" type="database.BookDetails"/>
  
    <h2><jsp:getProperty name="book" property="title"/></h2>
    &nbsp;<%=messages.getString("By")%> <em><jsp:getProperty name="book" property="firstName"/> <jsp:getProperty name="book" property="surname"/></em>&nbsp;&nbsp;
    (<jsp:getProperty name="book" property="year"/>)<br> &nbsp; <br>
    <h4><%=messages.getString("Critics")%></h4>
    <blockquote><jsp:getProperty name="book" property="description"/>
    </blockquote>
    <h4>Our price: 
    <jsp:setProperty name="currency" property="amount" value="<%=book.getPrice()%>"/>
    <jsp:getProperty name="currency" property="format"/>
  </h4>
    
    <p><strong><a href="<%=request.getContextPath()%>/catalog?Add=<%=bookId%>"><%=messages.getString("CartAdd")%></a>&nbsp; &nbsp; &nbsp;
</logic:present>
<a href="<%=request.getContextPath()%>/catalog"><%=messages.getString("ContinueShopping")%></a></p></strong>

