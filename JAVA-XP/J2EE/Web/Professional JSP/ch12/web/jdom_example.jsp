<%@ page contentType="text/html"%>
<%@ page import="java.io.File,
                 java.util.*,
                 org.jdom.*,
                 org.jdom.input.SAXBuilder,
                 org.jdom.output.*" %>
<%
String ls_xml_file  = "c:/xml/links.xml";

SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");

Document l_doc = builder.build(new File(ls_xml_file));

Element root = l_doc.getRootElement();

/* get a list of all the links in our XML document */
List l_pages = root.getChildren("link");

      Iterator l_loop =  l_pages.iterator(); 

      while ( l_loop.hasNext())
      { 
        Element l_link = (Element) l_loop.next();
    	   Element l_year = l_link.getChild("date").getChild("year");
        l_year.setText("2002");
      } 

   XMLOutputter l_format = new XMLOutputter();
   String ls_result = l_format.outputString(l_doc);

   root.setText(ls_result);

   ls_result = l_format.outputString(l_doc);
%>

<html><head><title></title></head>
	<body>
		<pre>
		<%=ls_result%>
		</pre>
	</body>
</html>

