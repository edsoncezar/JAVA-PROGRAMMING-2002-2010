<%@ page import="java.util.Date" %>
<%
  Date now= new Date();
  out.print(now.getHours() + ":" + now.getMinutes());
%>
