<%@ page errorPage="error.jsp" %>
<html>
<head>
  <title>Cooperating Tags</title>
</head>
<body>
  <h1>Cooperating tags</h1>

  <%@ taglib uri="/cooperatingtags.tld" prefix="chap10" %>
  <chap10:parent>
    <chap10:child>
      Array value 
    </chap10:child>
  </chap10:parent>
</body>
</html>
