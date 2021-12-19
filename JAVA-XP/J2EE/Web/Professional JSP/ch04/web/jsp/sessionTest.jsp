<%@ page session="true" %>

<html>
<body>
<h2>This is static template data</h2>

<% if (session.isNew()) {
  out.println("<h3>New Session</h3>");
   }
%>

</body>
</html>
