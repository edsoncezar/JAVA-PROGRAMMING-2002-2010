<%@ page
 import="org.w3c.dom.*,
 javax.xml.parsers.*,
 java.net.*"%>
<html>

<table>
  <tr>
    <td colspan="2">
      <form action="dom_links_checker.jsp" method="post">
      Add a url: <INPUT name="add" size="25">
    </td>
  </tr>
  <tr>
    <td align="center"><INPUT type="submit" value=" Send "></form></td>
    <td align="center">
     <form action="dom_links_checker.jsp" method="post">
      <INPUT name="clear" type="hidden" value="true">
      <INPUT type="submit" value=" Clear List">
     </form>
    </td>
  </tr>
</table>
<%

 org.w3c.dom.Document doc = (org.w3c.dom.Document)session.getAttribute("doc");
 if (request.getParameter("add") != null)
  {
  Element newLink = doc.createElement("url");
  org.w3c.dom.Text linkText =
   (org.w3c.dom.Text)doc.createTextNode(request.getParameter("add"));
  newLink.appendChild(linkText);
  doc.getDocumentElement().appendChild(newLink);
  }

 if (request.getParameter("clear") != null)
  {
  int count = doc.getElementsByTagName("url").getLength();
  for(int i = 0; i< count; i++)
   doc.getDocumentElement().removeChild(doc.getElementsByTagName("url").item(0));
  }

for(int i = 0; i < doc.getElementsByTagName("url").getLength(); i++)
 {
  URL url = new
   URL(doc.getElementsByTagName("url").item(i).getFirstChild().getNodeValue());
  HttpURLConnection link = (HttpURLConnection)url.openConnection();
%>
<font color="blue">
 <%= doc.getElementsByTagName("url").item(i).getFirstChild().getNodeValue() %>
</font> 
<font color="red"><%= link.getResponseCode() %></font><br />
<% } %>
</html>
