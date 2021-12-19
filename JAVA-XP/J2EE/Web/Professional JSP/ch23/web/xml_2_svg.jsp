<%@taglib uri="/WEB-INF/xsl.tld" prefix="xsl" %><%
%><%@page contentType="image/svg+xml"%><%
  String xmlFile = request.getParameter("xml"); 
%><xsl:usexsl xml="<%= xmlFile %>" xsl="/xml/svgHistory.xsl"/>
