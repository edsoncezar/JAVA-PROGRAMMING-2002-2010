<%@ page import="com.wrox.projsp.ch06.time.beans.Charge" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Hashtable" %>
<html>
<center>
<h3>Summary of your Charge Records</h3>

<%
  Hashtable h = (Hashtable) session.getAttribute("charges");
  if (h != null) {
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
<%
    }
  }
%>
    </ul>
</center>
</html>


