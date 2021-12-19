<%! String Item= new String("MyString"); %>
<%! String appStr = new String(); %>

<%

//set the application value
getServletContext().setAttribute("appstrexample", Item);

// retrieve the application value
appStr = (String) getServletContext().getAttribute("appstrexample");
%>

The value of appStr = <%=appStr%>
