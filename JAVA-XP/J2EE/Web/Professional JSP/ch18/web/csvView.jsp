<%--
	CSV table view of data.
--%>

<%@page session="true" errorPage="systemError.jsp" %>

<%@ taglib uri="/views" prefix="views" %>

<%-- The request controller will instantiate this bean --%>
<jsp:useBean id="browseSession" scope="session" type="ui.BrowseSession" />

<%-- Make this the default view page until the user chooses another page --%>
<% browseSession.setTableViewJSP(request.getServletPath()); %>

<% javax.swing.table.TableModel tableModel = browseSession.getTableModel(); %>

<html>
<head>
<title>CSV View -- Table: [<%=browseSession.getTableName()%>]</title> 
</head>

<body>
<% boolean multiSelect = false; %>
<%@ include file="_chooseTable.jspf" %>

<%@ include file="_chooseMode.jspf" %>  

Cut and paste the data below into any application
</p>
<code>
    <views:jspTable model="<%=tableModel %>" >
            <views:headingOpen></views:headingOpen>
            <views:headingClose><br/>
            </views:headingClose>
            <%-- We use the ternary operator to ensure that we don't place a comma after
                the last entry on each line --%>
            <views:headingCell><%=heading%><%=(column.intValue() < tableModel.getColumnCount() - 1) ? "," : ""%></views:headingCell>
            <views:rows>
                    <views:rowOpen></views:rowOpen>
                    <views:rowClose><br/></views:rowClose>
                    <views:cell><%=value%><%=(column.intValue() < tableModel.getColumnCount() - 1) ? "," : ""%></views:cell>
            </views:rows>
    </views:jspTable>
</code>

</body>
</html>