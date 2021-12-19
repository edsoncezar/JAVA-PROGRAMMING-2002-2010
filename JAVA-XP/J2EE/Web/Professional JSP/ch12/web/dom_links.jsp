<%@ page
 import=" org.w3c.dom.*,
 javax.xml.parsers.*" %>
<%
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder();
Document doc = db.newDocument();

 session.setAttribute("doc", doc);

 Element newLink = doc.createElement("root");
 doc.appendChild(newLink);
 System.out.println("Got to end of creating the document");
 System.out.println(session.getAttribute("doc"));
%>
<jsp:forward page="dom_links_checker.jsp" />

