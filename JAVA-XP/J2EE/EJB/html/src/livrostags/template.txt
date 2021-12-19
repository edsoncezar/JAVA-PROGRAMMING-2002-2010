<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ taglib uri="/tutorial-template" prefix="tt" %>
<%@ page errorPage="errorpage.jsp" %>
<%@ page import="java.util.*" %>
<% ResourceBundle messages = (ResourceBundle)session.getAttribute("messages"); %>
<%@ include file="screendefinitions.jsp" %>
<html>
<head>
<title>
        <tt:insert definition="bookstore" parameter="title"/>
</title>
</head>
        <tt:insert definition="bookstore" parameter="banner"/>
        <tt:insert definition="bookstore" parameter="body"/>
<center><em>Copyright &copy; 2001 Sun Microsystems, Inc. </em></center>
</body>
</html>
