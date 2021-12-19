<%@taglib uri="/WEB-INF/fop.tld" prefix="fop" %><%
%><%@page contentType="application/pdf"%><%
  String xmlFile = request.getParameter("xml"); 
%><fop:xml2pdf xml="<%= xmlFile %>" xsl="/xml/pdfHistory.xsl"/>