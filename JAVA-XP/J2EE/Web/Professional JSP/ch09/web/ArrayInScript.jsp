<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD>
  <TITLE>Making A Tag Variable Available To Page Scripting</TITLE>
</HEAD>
<BODY>
  <%@ taglib uri="/arrayinscripttags.tld" prefix="chap09" %>
  <chap09:defineObjects name="myArray"/>

  <H1>Array values handled in script</H1>
  <UL>
    <%
    String html = "";
    for (int counter = 0; counter < myArray.length; counter++) {
      html += "<LI>" + myArray[counter];
    }
    %>
    <%=html%>

  </UL>
</BODY>
</HTML>
