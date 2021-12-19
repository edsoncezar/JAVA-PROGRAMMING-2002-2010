<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD>
  <TITLE>Creating A Variable In One Tag And Showing It In Another</TITLE>
</HEAD>
<BODY>
  <%@ taglib uri="/attarraytags.tld" prefix="chap09" %>
  <chap09:defineObjects name="myArray"/>
  <chap09:showarray name="myArray"/>
</BODY>
</HTML>
