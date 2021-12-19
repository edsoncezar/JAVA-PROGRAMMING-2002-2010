<%@ page contentType="text/html"%>
<%@ page import="java.io.File, org.jdom.*, org.jdom.input.SAXBuilder" %>
<%
SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");

Document l_doc = builder.build(new File("c:/xml/message.xml"));
%>
<html>
<body>
	<%= l_doc.getRootElement().getChild("message").getText() %>
</body>
</html>
