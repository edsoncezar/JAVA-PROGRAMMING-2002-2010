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
   if (messages == null) {
      Locale locale=null;
      String language = request.getParameter("language");

      if (language != null) { 
         if (language.equals("English")) { 
          locale=new Locale("en", ""); 
         } else { 
          locale=new Locale("es", ""); 
         }
      } else 
         locale=new Locale("en", "");

      messages = ResourceBundle.getBundle("BookStoreMessages", locale); 
      session.setAttribute("messages", messages);
   }
%>

<p><b><%=messages.getString("What")%></b></p

<jsp:useBean id="bookDB" class="database.BookDB" scope="page" >
  <jsp:setProperty name="bookDB" property="database" value="<%=bookDBEJB%>" />
</jsp:useBean>

<jsp:setProperty name="bookDB" property="bookId" value="203" />

<bean:define id="book" name="bookDB" property="bookDetails" type="database.BookDetails"/>
 
<blockquote><p><em><a href="<%=request.getContextPath()%>/bookdetails?bookId=203">
<jsp:getProperty name="book" property="title"/></a></em>, 
<%=messages.getString("Talk")%></blockquote>
<p><b><a href="<%=request.getContextPath()%>/catalog"><%=messages.getString("Start")%></a></b>


