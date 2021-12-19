<%@ page errorPage="error.jsp" %>
<html>
<head>
  <title>Profanity Filter Example</title>
</head>
<body>
  <h1>Profanity Filter</h1>

  <%@ taglib uri="/profanityfiltertags.tld" prefix="chap10" %>
  <chap10:profanityfilter>
    <p>
    Some text to filter: D'oh!
    </p>
    <p>
    With a D'uh and another D'uh!
    </p>
    <p>
    D'oh, a deer, a female deer.
    </p>
  </chap10:profanityfilter>
</body>
</html>
