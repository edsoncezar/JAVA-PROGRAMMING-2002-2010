<%@page contentType="text/html"%>
<%@ page import="java.io.*,
                 java.util.*,
                 org.jdom.*,
                 org.jdom.input.SAXBuilder,
                 org.jdom.output.*" %>

<%
 
/* determine where all our files are located */
String ls_path = request.getServletPath();
       ls_path = ls_path.substring(0,ls_path.indexOf("dynamic_links.jsp")) ;

String ls_Jsp_Template  = application.getRealPath(ls_path + "dynamic_test.jsp");
String ls_XML_Links     = "c:/xml/links.xml";
SAXBuilder builder      = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
Document  l_jspdoc      = builder.build(new File(ls_Jsp_Template));

Element l_page   = l_jspdoc.getRootElement().getChild("html");
Element l_count  = l_page.getChild("head").getChild("ExampleCount");
String ls_number    = l_count.getAttributeValue("views");
int    li_modcount  = Integer.parseInt(ls_number) + 1;
       ls_number    = Integer.toString(li_modcount);
   
l_count.getAttribute("views").setValue(ls_number);
Namespace jsp =  Namespace.getNamespace("jsp", "http://java.sun.com/jsp_1_2"); 
Element l_change = l_page.getChild("head").getChild("ExampleCount").getChild("scriptlet",jsp);

String ls_message = "This page has been modified : " + ls_number + " times.";
l_change.setText("String ls_Display =\"" + ls_message + "\";"); 

Document l_doc = builder.build(new File(ls_XML_Links));

Element root 	      = l_doc.getRootElement();
Element example_links = l_page.getChild("body").getChild("ExampleLinks");
 
List l_links = root.getChildren("link");

example_links.setText(null);
Element l_table = new Element("Table");
Element l_tr    = new Element("tr");
Element l_th    = new Element("th");
Element l_td    = new Element("td");
Element l_anchor= new Element("a");

l_th.setText(" Great Sites ");
l_tr.addContent(l_th);
l_table.addContent(l_tr);
l_table.addAttribute("border","1");

      Iterator l_loop =  l_links.iterator(); 
      while ( l_loop.hasNext())
      { Element l_link   = (Element) l_loop.next();
        l_tr     = new Element("tr");
        l_td     = new Element("td");
        l_anchor = new Element("a");
        l_anchor.addContent(l_link.getChild("author").getText());
        l_anchor.addAttribute("href",l_link.getChild("url").getText());
        l_td.addContent(l_anchor);
        l_tr.addContent(l_td);
        l_table.addContent(l_tr);
      } 

example_links.addContent(l_table);
    try 
    { 
      FileOutputStream l_write_file = new FileOutputStream(ls_Jsp_Template); 
      XMLOutputter l_format = new XMLOutputter();
      l_format.output(l_jspdoc, l_write_file); 
    } 
    catch (IOException e) 
    { 
       out.print(e.toString());
    } 
%>
<html><head><title>Dynamic JSP</title></head>
	<body>
	   We have finished processing your JSP Page <br>
	   <p><a href="dynamic_test.jsp">View the Changes</a></p>
	</body>
</html>
