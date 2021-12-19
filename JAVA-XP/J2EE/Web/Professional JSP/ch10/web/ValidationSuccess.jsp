<%@ page errorPage="error.jsp" %>
<html>
<head>
  <title>Validating Tags</title>
</head>
<body>
  <h1>Validating tags (valid attribute supplied)</h1>

  <%@ taglib uri="/validationfailure3tags.tld" prefix="chap10" %>
  <chap10:validation firstName="Maggie"/>
</body>
</html>
