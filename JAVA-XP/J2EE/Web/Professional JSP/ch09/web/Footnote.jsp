<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD>
  <TITLE>Footnote Example</TITLE>
</HEAD>
<BODY>
  <%@ taglib uri="/footnotetags.tld" prefix="chap09" %>
  <P>
  Some text. Some text. Some text. Some text. Some text. Some text.
  Some text. Some text. Some text. Some text. Some text. Some text.
  Some text. Some text. Some text. Some text. Some text. Some text.
  <chap09:footnote>Footnote goes here</chap09:footnote>
  Some text. Some text. Some text. Some text. Some text. Some text.
  </P>

  <P>
  Some text. Some text. Some text. Some text. Some text. Some text.
  Some text. Some text. Some text. Some text. Some text. Some text.
  Some text. Some text. Some text. Some text. Some text. Some text.
  </P>
</BODY>
</HTML>
