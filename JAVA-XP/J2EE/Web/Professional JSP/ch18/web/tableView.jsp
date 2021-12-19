<%--
	Default table view of data. Read only.
        Uses some simple color banding.
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

<%-- We only want a drop down, not an expanded list --%>
<% boolean multiSelect = false; %>
<%@ include file="_chooseTable.jspf" %>

<% javax.swing.table.TableModel tableModel = browseSession.getTableModel(); %>

<%@ include file="_chooseMode.jspf" %>

<% String color="white"; %>  
<table border="1">
<views:jspTable model="<%=tableModel %>" >
	<views:headingOpen><tr bgcolor="black"></views:headingOpen>
	<views:headingCell><td><font size=4 color="white"><%=heading%></font></td></views:headingCell>
	<views:rows>
		<views:rowOpen>
                        <%-- Use a scriptlet to achieve color banding for readability --%>
                        <% color = (row.intValue() % 2 == 0) ? "white" : "cyan"; %>
			<tr bgColor="<%=color%>">
		</views:rowOpen>
		<views:rowClose></tr></views:rowClose>
		<views:cell><td><%=value%></td></views:cell> 
	</views:rows>
</views:jspTable>
</table>

</body>
</html>