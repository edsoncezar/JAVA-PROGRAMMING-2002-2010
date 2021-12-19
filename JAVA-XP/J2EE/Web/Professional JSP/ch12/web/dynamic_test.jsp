<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/jsp_1_2">
  <html>
    <head><title> Dynamic Test Page </title> 
      <ExampleCount views="0">
        <jsp:scriptlet> String ls_Display = ""; </jsp:scriptlet>
      </ExampleCount>
    </head>
    <body>	
      <ExampleLinks>
        <table border="1">
          <tr><th> Great Sites </th></tr>
          <tr>					
            <td align="center"> 
              <a href="http://www.wrox.com">wrox</a> 
            </td>
          </tr>
        </table>
      </ExampleLinks>
      <p><jsp:expression>ls_Display</jsp:expression></p>
      <p><a href="dynamic_links.jsp">Update This Page</a></p>
    </body>
  </html>
</jsp:root>
