<%@ page import="java.util.*" %>
<jsp:useBean id="directory" scope="application" 
             class="java.util.Properties"/>

<html>
  <head>
    <title>Wrox Directory Example For Application Listeners</title>
  </head>

  <body bgcolor="#FFFFCC">
    <h3 align="center">Thank you for using the Wrox directory ! </h3>
    <table width="100%" border="1" cellspacing="0" cellpadding="0" 
           bgcolor="#CCCCFF" bordercolor="#000000">
      <tr>
        <td>
          <form action="directory.jsp" >
            <input type="submit"value="List Directory">
          </form>
        </td>
        <td>
          <form action="directory.jsp">
            <input type="text" name="person_name">
            <input type="submit" value="Lookup">
          </form>
        </td>
        <td>
          <form action="directory.jsp" >
            <b>Name</b> 
            <input type="text" name="new_name">
            <br>
            <b>Number</b> 
            <input type="text" name="new_tel">
            <input type="submit" value="Insert In Directory">
          </form>
        </td>
      </tr>
    </table>
    <hr size="1">


<%
  // The request object contains the HttpRequest parameters
  // This JSP checks for a parameter called 'name' ie the name to lookup 
  // Or checks for the parameter 'insert' which contains the entry to insert

  String name = request.getParameter("person_name");
  String newname= request.getParameter("new_name");
  
  if ((name != null) && (!name.equals(""))) {
    // Try to return the phone number for the specified
    // 'person_name' parameter
    String phone= directory.getProperty(name.trim());
    if (phone == null) 
      out.println("<h2>" +name + " is not listed in the directory </h2>");
    else 
      out.println("<h2>" +name +  " can  be reached at " + phone +"</h2>");
    return;
  }


  if ((newname != null) && (!newname.equals(""))) {
    directory.setProperty(newname,request.getParameter("new_tel").trim());
    out.println(" <h2>Your entry has been inserted in the directory </h2>");
    return;
  }


  // if the above returns have not been processed
  // then just list the directory
%>
    <hr>
    <table width="100%" border="1" cellspacing="0" 
           cellpadding="0"bgcolor="#CCCCFF" bordercolor="#000000">
      <tr> 
        <td><h3>Name</h3></td>
        <td><h3>Phone number</h3></td>
      </tr>
    
      <% Enumeration enum = directory.propertyNames();
         while(enum.hasMoreElements()) {
           String propname= (String)enum.nextElement();
           out.println("<tr> <td><b>" + propname+ "</b></td><td>" +
                       directory.getProperty(propname) + " </td></tr>");
         }
      %>

    </table>
    <hr>
  </body>
</html>
