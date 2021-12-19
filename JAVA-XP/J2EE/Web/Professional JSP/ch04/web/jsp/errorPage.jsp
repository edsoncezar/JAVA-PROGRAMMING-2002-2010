<%@ page isErrorPage="true" %>

<h2>Error Page</h2>

The exception <%= exception.toString() %> was raised.

<p>The exception reported this message:
<p><%= exception.getMessage() %>
