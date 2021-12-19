<html>
<center>
<h3>Your Charge Record has been saved</h3>

<%@ page import="com.wrox.projsp.ch06.time.beans.Charge" %>
<%@ page import="com.wrox.projsp.ch06.time.TimeHashtable" %>

<jsp:useBean id="newCharge" class="com.wrox.projsp.ch06.time.beans.Charge" >
  <jsp:setProperty name="newCharge" property="*" />
</jsp:useBean>

<%  
  TimeHashtable h = (TimeHashtable) session.getAttribute("charges");
  if (h == null) {
    ServletContext context = config.getServletContext();
    h = new TimeHashtable(context.getRealPath("charges.bin"));  // first charge
    session.setAttribute ("charges", h);
  }
  h.put (newCharge.getProject(), newCharge);  // use project as the key
%>

Record Details: <p>
Name = <%= newCharge.getName() %>, 
Project = <%= newCharge.getProject() %>, 
Hours = <%= newCharge.getHours() %>, 
Date = <%= newCharge.getDate() %>

</center>
</html>


