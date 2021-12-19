<%@taglib uri="/WEB-INF/fop.tld" prefix="fop" %><%
%><%@page contentType="application/pdf"%><%
  String foFile = request.getParameter("fo");
%><fop:fo2pdf fo="<%= foFile %>"/>