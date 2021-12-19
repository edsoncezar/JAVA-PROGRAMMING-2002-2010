<%@ page import="java.util.Enumeration" %>

<html>
<head><title>Time Entry System - Admin Page </title></head>

<body>

<h2>Adminstration Page</h2>

<h3>Webapp and Servlet Engine Info</h3>

<ul>
  <li>Server Info: <%= application.getServerInfo() %>
  <li>Major Version: <%= application.getMajorVersion() %>
  <li>Minor Version: <%= application.getMinorVersion() %>
  <li>Webapp Init Parameters:
                                       
  <ul>
<%
  Enumeration webappParams = application.getInitParameterNames();
  while (webappParams.hasMoreElements()) {
    String paramName = (String) webappParams.nextElement();
    String paramValue = application.getInitParameter(paramName);
%>
    <li><%= paramName%>=<%=paramValue%>
<%
  }
%>
  </ul>
</ul>
                                                                           
<h3>Servlet Info</h3>

<ul>
  <li>Servlet Name: <%= config.getServletName() %>
  <li>Servlet Init Parameters:

  <ul>
<%
Enumeration servletParams = config.getInitParameterNames();
while (servletParams.hasMoreElements()) {
  String paramName = (String) servletParams.nextElement();
  String paramValue = config.getInitParameter(paramName);
%>
  <li><%= paramName%>=<%=paramValue%>
<%
  }
%>

  </ul>
</ul>
