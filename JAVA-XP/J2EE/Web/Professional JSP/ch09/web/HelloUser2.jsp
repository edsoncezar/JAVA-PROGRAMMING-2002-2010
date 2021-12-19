<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD>
  <TITLE>Hello User 2 Example</TITLE>
</HEAD>
<BODY>
  <%@ taglib uri="/hellouser2tags.tld" prefix="chap09" %>
  <%
    String user = "Marge";
  %>
  <chap09:hellouser2 username="<%=user%>"/>
</BODY>
