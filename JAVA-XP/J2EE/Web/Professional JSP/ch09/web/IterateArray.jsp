<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD>
  <TITLE>Iterator Example Using Page Attributes And An Iterating Tag</TITLE>
</HEAD>
<BODY>
  <H1>Array values iterated in a tag</H1>

  <%@ taglib uri="/iteratearraytags.tld" prefix="chap09" %>
  <chap09:createarray name="myArray"/>

  <chap09:iteratearray name="myArray">
    Array value
  </chap09:iteratearray>
</BODY>
</HTML>
