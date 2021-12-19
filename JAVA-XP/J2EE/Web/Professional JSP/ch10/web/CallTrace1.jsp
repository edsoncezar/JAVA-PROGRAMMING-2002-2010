<%@ page errorPage="error.jsp" %>
<html>
<head>
  <title>Call Trace</title>
</head>
<body>
  <%@ taglib uri="/calltrace1tags.tld" prefix="chap10" %>
  <h1>Test 1 - a self-closing tag.</h1>
    <chap10:calltrace name="name1"/>
</body>
</html>
