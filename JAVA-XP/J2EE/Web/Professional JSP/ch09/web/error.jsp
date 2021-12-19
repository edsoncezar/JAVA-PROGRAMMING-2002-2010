<%@ page import="java.io.*" isErrorPage="true" %>
<HTML>
<HEAD>
  <TITLE>Error!</TITLE>
</HEAD>
<BODY>

<H1>Error: <%=exception%></H1>
  <%
  StringWriter errorWriter = new StringWriter ();
  PrintWriter errorStream = new PrintWriter (errorWriter);
  exception.printStackTrace (errorStream);
  %>
  <P>Stack trace:
  <BLOCKQUOTE>
    <PRE><%=errorWriter%></PRE>
  </BLOCKQUOTE>
</BODY>
</HTML>
