<%@ page
  import="javax.xml.parsers.*,
          org.w3c.dom.*,
          javax.xml.transform.*,
          javax.xml.transform.stream.*,
          java.io.*"%>
<%
StreamSource xml = new StreamSource(new File("c:/xml/links.xml"));
StreamSource xsl = new StreamSource(new File("c:/xml/links_html.xsl"));

StreamResult result = new StreamResult(out);

TransformerFactory tFactory = TransformerFactory.newInstance();
Transformer transformer = tFactory.newTransformer(xsl);

transformer.transform(xml, result);
%>
