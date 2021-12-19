<html>
<head>
<title>
Integrated Database Bean Test
</title>
</head>

<body>

<jsp:useBean id="dbBeanID" scope="request" 
             class="com.wrox.projsp.appG.dbbeans.dbBeanClass" />

<% 
  String myID = request.getParameter("dbIDNumber");
%>

<h3>
Getting the Country for Country ID Number <%=myID%>:<p>

<%
  out.print(dbBeanID.getCountry(Integer.parseInt(myID)));
%>
</h3>

</body>
</html>
