<%@ page errorPage="error.jsp" %>
<html>
<head>
  <title>Call Trace</title>
</head>
<body>
  <%@ taglib uri="/calltrace2tags.tld" prefix="chap10" %>
  <h1>Test 2 - with enclosed body text</h2>
  <chap10:calltrace name="name2">
    <blockquote>Body text here.</blockquote>
  </chap10:calltrace>
</body>
</html>
