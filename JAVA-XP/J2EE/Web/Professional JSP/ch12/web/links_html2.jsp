<jsp:root
  xmlns:jsp="http://java.sun.com/jsp_1_2"
  xmlns:JAXP="http://www.jspinsider.com/jspkit/JAXP">
<JAXP:transformer>
  <JAXP:xmlFile><jsp:cdata>c:/xml/links.xml</jsp:cdata></JAXP:xmlFile>
  <JAXP:xslFile><jsp:cdata>c:/xml/links_html.xsl</jsp:cdata></JAXP:xslFile>
</JAXP:transformer>
</jsp:root>
