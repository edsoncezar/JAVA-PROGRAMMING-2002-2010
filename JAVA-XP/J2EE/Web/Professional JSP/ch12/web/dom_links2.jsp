<%@ page
 import=" org.w3c.dom.*,
 javax.xml.parsers.*" %>
<%
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder();
Document doc = db.parse("c:/xml/links.xml");

session.setAttribute("doc", doc);
%>
<jsp:forward page="dom_links_checker.jsp" />
