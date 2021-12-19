<%@ page errorPage="error.jsp" %>
<html>
<head>
  <title>Validating Tags</title>
</head>
<body>
  <h1>Validating tags (invalid attribute supplied)</h1>

  <%@ taglib uri="/validationfailure2tags.tld" prefix="chap10" %>
  <chap10:validation firstName="Barney"/>
</body>
</html>
