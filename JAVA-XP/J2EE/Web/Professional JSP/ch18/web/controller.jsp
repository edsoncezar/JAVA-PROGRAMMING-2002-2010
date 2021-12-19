<%--
    JSP controller for the data explorer application.
    This page produces no output, but redirects each request
    to the appropriate JSP view. 
    The logic required to make this choice
    is supplied by the RequestController session bean.
--%>

<%@page session="true" errorPage="systemError.jsp" %>

<%-- The controller object will be instantiated by a session's first
    call to this page --%>
<jsp:useBean id="controller" scope="session" class="ui.RequestController" />

<%-- Each request to this page will be forwarded to the appropriate view,
    as determined by the controller and its helper classes. These classes
    will set the session state appropriately before returning the URL of
    a view, to which this page will forward the response. --%>
<jsp:forward page="<%=controller.getNextPage(pageContext, request)%>"/>