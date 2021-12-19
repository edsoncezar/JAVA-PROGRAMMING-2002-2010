<%@ page import="com.wrox.projsp.ch06.time.beans.Charge" %>
<%@ page import="com.wrox.projsp.ch06.time.TimeHashtable" %>
<%@ page import="java.util.Enumeration" %>
<html>
<center>
<h3>Summary of your Charge Records</h3>

<%
  TimeHashtable h = (TimeHashtable) session.getAttribute("charges");
  if (h == null) {
    ServletContext context = config.getServletContext();
    h = new TimeHashtable(context.getRealPath("charges.bin"));  // first charge
    session.setAttribute ("charges", h);
  }
%>
    <ul>

<%
    Enumeration charges = h.keys();
    while (charges.hasMoreElements()) {
      String proj = (String) charges.nextElement();
      Charge ch = (Charge) h.get(proj);
%>
      <li>
      name = <%= ch.getName() %>
      , project = <%= proj %>
      , hours = <%= ch.getHours() %>
      , date = <%= ch.getDate() %>
    </ul>
<% 
    }
%>
</center>
</html>


