<%@ page import="com.wrox.projsp.ch03.time.controller.beans.Charge" %>
<%@ page import="java.util.Hashtable" %>

<h3>Your Charge Record has been saved</h3>

<%

  String name = request.getParameter("name");
  String project = request.getParameter("project");
  String hours = request.getParameter("hours");
  String date = request.getParameter("date");
  Charge c = new Charge();
  c.setName(name);
  c.setProject(project);
  c.setHours(hours);
  c.setDate(date);
  
  Hashtable h = (Hashtable) session.getAttribute("charges");
  if (h == null) {
    h = new Hashtable();  // first charge
    session.setAttribute ("charges", h);
  }
  h.put (project, c);  // use project as the key
%>

Record Details: <p>
Name = <%= name %>, Project = <%= project %>, Hours = <%= hours %>, Date = <%=date %>
