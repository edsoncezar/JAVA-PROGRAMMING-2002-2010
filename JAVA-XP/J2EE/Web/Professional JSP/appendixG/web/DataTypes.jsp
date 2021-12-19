<html>
<body>

<%
  // the common variables  
  byte largestByte = Byte.MAX_VALUE;
  short largestShort = Short.MAX_VALUE;
  int largestInteger = Integer.MAX_VALUE;
  long largestLong = Long.MAX_VALUE;

  // real numbers for handling interesting things like money
  float largestFloat = Float.MAX_VALUE;
  double largestDouble = Double.MAX_VALUE;

  // other primitive types 
  char aChar = 'S';
  boolean aBoolean = true;


  // write some output - the equivalent of response.write("output")

  out.print("The largest byte value is " + largestByte+ "<br>");
  out.print("The largest short value is " + largestShort+ "<br>");
  out.print("The largest integer value is " + largestInteger+ "<br>");
  out.print("The largest long value is " + largestLong+ "<br>");

  out.print("The largest float value is " + largestFloat+ "<br>");
  out.print("The largest double value is " + largestDouble+ "<br>");

  if (Character.isUpperCase(aChar)) {
    out.print("The character " + aChar + " is upper case."+ "<br>");
  } else {
    out.print("The character " + aChar + " is lower case." + "<br>");
  }
  
  out.print("The value of aBoolean is " + aBoolean + "<br>");
  
%>      

</body>
</html>
