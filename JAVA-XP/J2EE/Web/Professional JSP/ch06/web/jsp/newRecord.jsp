<html>
<center>
<h3>Your Charge Record has been saved</h3>

<%@ page import="com.wrox.projsp.ch06.time.beans.Charge" %>
<%@ page import="java.util.Hashtable" %>

<jsp:useBean id="newCharge" class="com.wrox.projsp.ch06.time.beans.Charge" >
  <jsp:setProperty name="newCharge" property="*" />
</jsp:useBean>

<%  
  Hashtable h = (Hashtable) session.getAttribute("charges");
  if (h == null) {
    h = new Hashtable();  // first charge
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


