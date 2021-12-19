<%@ page errorPage="error.jsp" %>
<html>
<head>
  <title>Validating Tags</title>
</head>
<body>
  <h1>Validating tags (no attribute supplied)</h1>

  <%@ taglib uri="/validationfailure1tags.tld" prefix="chap10" %>
  <chap10:validation/>
</body>
</html>
