<%--
	Editable table view. This will contain a form
        for each row of data.
--%>

<%@page session="true" errorPage="systemError.jsp" %>

<%@ taglib uri="/views" prefix="views" %>

<%-- The request controller will instantiate this bean --%>
<jsp:useBean id="browseSession" scope="session" type="ui.BrowseSession" />

<%-- Make this the default view page until the user chooses another page --%>
<% browseSession.setTableViewJSP(request.getServletPath()); %>

<html>
<head>
<title>Table: [<%=browseSession.getTableName()%>]</title>
</head>

<body>
<% boolean multiSelect = false; %>
<%@ include file="_chooseTable.jspf" %>

<%@ include file="_chooseMode.jspf" %>

<% javax.swing.table.TableModel tableModel = browseSession.getTableModel(); %>   

<% String color="white"; %>
<table border="1">
<views:jspTable model="<%=tableModel %>" >
	<views:headingOpen><tr bgcolor="black"></views:headingOpen>
	<views:headingClose>
		<%-- We can use this to add an extra column --%>
		<td><font size=4 color="white">Update values</font></td></tr>
	</views:headingClose>
	<views:headingCell><td><font size=4 color="white"><%=heading%></font></td></views:headingCell>
	<views:rows>
		<views:rowOpen>
                        <%-- Each row of the table is a form --%>
                        <form method="post" action="controller.jsp">
                        <%-- As always, we need a hidden field containing the required RequestHandler's name --%>
                        <input type="hidden" name="action" value="UpdateTable"/>
                        <input type="hidden" name="row" value="<%=row.intValue()%>" />    
                        <%-- Use the ternary operator to implement color banding --%>
                        <% color = (row.intValue() % 2 == 0) ? "gray" : "white"; %>
			<tr bgColor="<%=color%>">
		</views:rowOpen>
		<views:rowClose>
			<%--
				We need to provide the extra column we promised in the heading.
				We need to provide a variable containing the current alert's
				id for the included form
			--%>
			<td>                           
	                    <input type="submit" value="Update" />
                        </td>
                        </form>
			</tr>
		</views:rowClose>
		<views:cell><td>
                           <% if (!tableModel.isCellEditable(row.intValue(), column.intValue())) { %>
                                <%-- The primary key is not editable: don't use a form field --%>
                                <b><%=value%></b>  
                           <% } else { %>
                                <input type="text" name="<%=column.intValue()%>" value="<%=value%>" />                     
                           <% } %>
                </td></views:cell>
	</views:rows>
</views:jspTable>
</table>

</body>
</html>