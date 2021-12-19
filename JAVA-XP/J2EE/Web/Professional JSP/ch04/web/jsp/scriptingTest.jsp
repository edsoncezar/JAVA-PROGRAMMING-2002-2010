<%@ page import="java.util.Calendar" %>

<html>
<body>
<h2>This is a scripting test</h2>

<%! Calendar c = Calendar.getInstance(); %>

<%-- JSP comment --%>
<!-- HTML comment -->

<% 

if ((c.DAY_OF_WEEK == Calendar.SATURDAY) || 
    (c.DAY_OF_WEEK == Calendar.SUNDAY)) 
{
  out.println("Its the weekend, I don't know what time it is");
} else {
  out.println("today is");
%>
  <%= c.getTime() %>

<%
  }
%>

</body>
</html>
