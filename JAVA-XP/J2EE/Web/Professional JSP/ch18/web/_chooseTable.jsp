<%-- 
    JSP fragment to allow the user to choose which table to view, and to 
    allow SQL query input if we are connected to a relational database.
    There must be a valid connection before a JSP can include this fragment.
    Including JSPs must define a boolean variable named multiSelect. If this
    is true, the list of database tables will be expanded; otherwise it will
    appear as a drop down.
--%>

You are connected to <i><%=browseSession.getDataSource()%></i>
<br/>
<b>Choose table</b><br/>
<form method="post" action="controller.jsp">
	<select name="tableIndex"
            <% if (multiSelect) { %>
                size="15"
            <% } %>
            >
            <%-- Use the NameValueModel exposed by the DataSource object
                to display the list of tables --%>
            <views:jspNameValue model="<%=browseSession.getDataSource()%>">
                    <option value="<%=value%>"
                    <% if (index.intValue() == browseSession.getTableIndex()) { %>
                        selected="true"
                    <% } %>
                   >
                    <%=name%>
                    </option>
            </views:jspNameValue>
	</select>
        <%-- Hidden parameter setting the actual action --%>
	<input type="hidden" name="action" value="ChooseTable"/>
          <% if (multiSelect) { %>
            <br />
            <p />
        <% } %>
	<input type="submit" value="View table" />
</form>

<%-- Only show the SQL query input form if we're connected to a relational database --%>
<% if (browseSession.getDataSourceSupportsSQL()) { %>
    To execute a SQL query, enter it below, or choose from the list
    of previously run queries on the right
    <form method="post" action="controller.jsp">
        <input type="hidden" name="action" value="RunQuery"/>
        <input type="text" name="query" size="40" />
        <%-- The select box allows the user to choose from previously run queries --%>
        <select name="oldquery">
                <views:jspNameValue model="<%=browseSession.getQueryModel()%>">
                        <option value="<%=value%>">
                        <%=name%>
                        </option>
                </views:jspNameValue>
            </select>
        <input type="submit" value="Execute query" />
    </form>
<% } %>
