<%@ page import="com.wrox.projsp.ch16.CounterListenerExample2" %>

<%
   session.setAttribute("pagehit","xxx");   // value doesn’t matter
%>


<html>
  <head>
    <title>Example Session Counter For Application Listeners</title>
  </head>

  <body bgcolor="#FFFFCC">
    <h3 align="center">Counter Example:</h3>

    <hr size="1">

    <%= CounterListenerExample2.getCounterInfo() %>
  </body>
</html>
