<h3>Your Charge Record has been saved</h3>

<%@ page import="com.wrox.projsp.ch05.time.beans.Charge" %>
<%@ page import="java.util.Hashtable" %>

<jsp:useBean id="newCharge" class="com.wrox.projsp.ch05.time.beans.Charge" >
  <jsp:setProperty name="newCharge" property="*" />
</jsp:useBean>

<%  
  Hashtable h = (Hashtable) session.getAttribute("charges");
  if (h == null) {
    h = new Hashtable();  // first charge
    session.setAttribute ("charges",  h);
  }
  String project = newCharge.getProject();
  h.put (newCharge.getProject(), newCharge);  // use project as the key
%>

Record Details: <p>
Name = <jsp:getProperty name="newCharge" property="name"/>,
Project = <jsp:getProperty name="newCharge" property="project"/>,
Hours = <jsp:getProperty name="newCharge" property="hours"/>,
Date = <jsp:getProperty name="newCharge" property="date"/> 
