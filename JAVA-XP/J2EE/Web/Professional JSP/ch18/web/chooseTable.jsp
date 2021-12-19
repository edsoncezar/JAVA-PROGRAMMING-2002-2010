<%--
	JSP to allow the user to choose a table from
        the database. The user will be connected to a database
        and the session bean available before this view is invoked.
--%>

<%@page session="true" errorPage="systemError.jsp" %>

<%@ taglib uri="/views" prefix="views" %>

<%-- 
    The request controller will instantiate this bean 
    This page will throw an exception here if the bean is not
    already in the PageContext
--%> 
<jsp:useBean id="browseSession" scope="session" type="ui.BrowseSession" />

<html>
<head>
<title>Choose a table</title>
</head>

<body>
<h2>Tables found</h2>

<%-- Parameter to _chooseTable.jsp: this value will ensure that
    the drop down is expanded on this page --%>
<% boolean multiSelect = true; %>

<%-- Include the actual form. We want to share this code
    with other pages --%>
<%@ include file="_chooseTable.jspf" %>   

</body>
</html>