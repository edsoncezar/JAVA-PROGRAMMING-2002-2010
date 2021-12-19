<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="java.util.*" %>
<%@ page import="MyLocales" %>
<%@ page contentType="text/html; charset=ISO-8859-5" %>
<html>
<head><title>Localized Dates</title></head>
<body bgcolor="white">
<jsp:useBean id="locales" scope="application" class="MyLocales"/>
<form name="localeForm" action="index.jsp" method="post">
<b>Locale:</b>
<select name=locale>
<% 
  Iterator i = locales.getLocaleNames().iterator();
  String selectedLocale = request.getParameter("locale");
  while (i.hasNext()) {
    String locale = (String)i.next();
    if (selectedLocale != null && selectedLocale.equals(locale) ) {
%>
      <option selected><%=locale%></option>
<%
    } else {
%>
      <option><%=locale%></option>
<%
    } 
  }
%>
</select>
<input type="submit" name="Submit" value="Get Date">
</form>
<p>
<jsp:include page="date.jsp" flush="true" />
</body>
</html>
