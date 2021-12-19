<%-- 
    JSP fragment to offer a choice of mode, depending on the user's
    location in the system.
--%>

<% if (request.getServletPath().indexOf("tableView") == -1) { %>
    <a href="tableView.jsp">View mode</a> | 
<% } %>
<% if (request.getServletPath().indexOf("csvView") == -1) { %>
    <a href="csvView.jsp">Downloadable format mode (CSV)</a> |
<% } %>
<% if (request.getServletPath().indexOf("editTable") == -1 && browseSession.isTableEditable()) { %>
    <a href="editTable.jsp">Edit mode</a>
<% } %>
<br/>
<a href="controller.jsp?action=NewConnection">Disconnect and connect to another database</a> |
<a href="controller.jsp?action=Logout">Logout out and disconnect</a>
<p/>
