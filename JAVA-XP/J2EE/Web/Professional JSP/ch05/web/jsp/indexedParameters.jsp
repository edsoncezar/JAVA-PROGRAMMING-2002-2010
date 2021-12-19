<%@ page import="java.util.Enumeration" %>

<html>

<body>

<h2>Indexed Parameter Test</h2>

<ul>
  <li>Request Parameters:
                                       
  <ul>
<%
  Enumeration params = request.getParameterNames();
  while (params.hasMoreElements()) {
    String paramName = (String) params.nextElement();
    String paramValue = request.getParameter(paramName);
%>
    <li><%= paramName%>=<%=paramValue%>
<%
  }
%>
  </ul>


  <li>Indexed Parameter Values:
  <ul>
<%
    String [] indexedParams = request.getParameterValues("indexed");
    if (indexedParams != null) {
      for (int i = 0; i < indexedParams.length; i++) {
        out.println("<li>" + indexedParams[i]);
      }
    }
%>
  </ul>
</ul>

<form action="indexedParameters.jsp" method="POST">
  <input type="hidden" name="indexed" value="1">
  <input type="hidden" name="indexed" value="2">
  <input type="hidden" name="indexed" value="3">
  <input type="submit">
</form>
</body>
</html>
