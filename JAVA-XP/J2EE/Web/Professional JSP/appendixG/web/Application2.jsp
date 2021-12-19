<%! Integer IntItem= new Integer(33); %>
<%! String AppValueReturned; %>
<%! Integer AppToInteger; %>

<%
//set the application value
getServletContext().setAttribute("appIntexample", ""+ IntItem);

// retrieve the application value
AppValueReturned = (String) getServletContext().getAttribute("appIntexample");

// convert back to an Int
try {
AppToInteger = Integer.valueOf(AppValueReturned);
}
catch(NumberFormatException e){};

%>

The value of AppToInteger = <%= ""+ AppToInteger%><p>

Consequently, the value of Application1.jsp's app variable is
<%= (String) getServletContext().getAttribute("appstrexample")%>
