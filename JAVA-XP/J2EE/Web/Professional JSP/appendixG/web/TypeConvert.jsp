<html>
<body>
<%

  // first we'll assign a value to a previously declared int
  int i = 50;
  // and then to a previously declared but Empty String
  String str = "";

  // then we can convert between the different types

  out.print("integer to String:<br>");
  str = Integer.toString(i);
  out.print(str + "<br>");

  out.print("String to integer:<br>");
  i = Integer.valueOf(str).intValue();
  out.print(i + "<br>");

  out.print("Another Approach for String to integer:<br>");
  i = Integer.parseInt(str);
  out.print(i + "<br>");

%>
</body>
</html>
