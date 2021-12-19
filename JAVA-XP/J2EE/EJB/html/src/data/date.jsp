<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="java.util.*" %>
<%@ page import="MyDate,MyLocales" %>
<html>
<body bgcolor="white">
<jsp:useBean id="date" class="MyDate"/>
<jsp:useBean id="locales" scope="application" class="MyLocales"/>
<% 
  Locale locale = locales.getLocale(request.getParameter("locale")); 
  if (locale != null) {
%>
<jsp:setProperty name="date" property="locale" value="<%=locale%>"/>
The date in <b><%=locale.getDisplayName()%></b> is <b><%=date.getDate()%></b>
<% } %>
</body>
</html>
