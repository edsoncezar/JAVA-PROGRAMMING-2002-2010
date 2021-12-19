<%@ page
  import="org.xml.sax.helpers.*,
  javax.xml.parsers.*,
  com.jspinsider.jspkit.examples.*,
  org.xml.sax.*" %>
<html>
<%
 SAXParserFactory spf = SAXParserFactory.newInstance();
 SAXParser sp = spf.newSAXParser();
 SAXExample se = new SAXExample(out);

 sp.parse(new java.io.File("c:/xml/links.xml"), se);
%>
</html>
