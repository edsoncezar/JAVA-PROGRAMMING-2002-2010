<%@ page errorPage="error.jsp" %>
<HTML>
<HEAD>
  <TITLE>Exception Handling Within Tags</TITLE>
</HEAD>
<BODY>
  <H1>Exception handling within tags</H1>

  <%@ taglib uri="/exceptionhandlingtags.tld" prefix="chap10" %>
  <chap10:exceptionhandler>
    Enclosed body text
  </chap10:exceptionhandler>
</BODY>
</HTML>
