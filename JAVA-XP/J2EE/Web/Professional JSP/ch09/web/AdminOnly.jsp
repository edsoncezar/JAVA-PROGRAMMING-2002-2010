<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD>
  <TITLE>Admin Only Example</TITLE>
</HEAD>
<BODY>
  <%@ taglib uri="/adminonlytags.tld" prefix="chap09" %>
  <P>
  Some text that everyone can see.
  </P>

  <P>
  <%String username = "Bart";%>
  Using username 'Bart'.
  </P>

  <chap09:adminonly username="<%=username%>">
    <BLOCKQUOTE>
      <I>Some text that only administrators can see.</I>
    </BLOCKQUOTE>
  </chap09:adminonly>

  <P>
  <%username = "Lisa";%>
  Using username 'Lisa'.
  </P>

  <chap09:adminonly username="<%=username%>">
    <BLOCKQUOTE>
      <I>Some text that only administrators can see.</I>
    </BLOCKQUOTE>
  </chap09:adminonly>

  <P>
  <%username = "Maggie";%>
  Using username 'Maggie'.
  </P>

  <chap09:adminonly username="<%=username%>">
    <BLOCKQUOTE>
      <I>Some text that only administrators can see.</I>
    </BLOCKQUOTE>
  </chap09:adminonly>
</BODY>
</HTML>
