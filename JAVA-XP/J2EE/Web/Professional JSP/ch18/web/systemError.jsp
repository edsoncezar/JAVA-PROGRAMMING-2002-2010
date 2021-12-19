
<%@page isErrorPage="true" %>

<html>
<head>
<title>Data Browser: Error</title>
</head>

<body>
<% if (exception instanceof ui.InvalidInputException) { %>
        <%-- Provide a link to allow the user to retry --%>
        Sorry. Your input was invalid.<br/>
        <i><%=exception.getMessage()%></i><br/>
	Please
	<a href="<%=((ui.InvalidInputException) exception).getRetryURL()%>">try again</a>.
	
<% } else { %>
	Sorry. Your request could not be processed.<br/>
        <i><%=exception.getMessage()%></i><br/>
<% } %>

<%-- Include the actual exception in a hidden comment --%>
<!--
	<% exception.printStackTrace(new java.io.PrintWriter(out)); %>
-->


</body>
</html>