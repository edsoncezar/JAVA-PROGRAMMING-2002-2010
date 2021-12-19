<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD>
  <TITLE>Making A Tag Variable Available To Page Scripting</TITLE>
</HEAD>
<BODY>
  <%@ taglib uri="/arrayinscriptviatldtags.tld" prefix="chap12" %>
  <chap12:defineObjects name="myArray"/>

  <H1>Array values handled in script</H1>
  <UL>
    <%
    String Html = "";
    for (int counter = 0; counter < myArray.length; counter++) {
      Html += "<LI>" + myArray [counter];
    }
    %>
    <%=Html%>

  </UL>
</BODY>
</HTML>