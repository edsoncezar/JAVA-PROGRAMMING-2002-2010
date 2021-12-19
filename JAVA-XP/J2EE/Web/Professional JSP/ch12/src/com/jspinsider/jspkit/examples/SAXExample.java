package com.jspinsider.jspkit.examples;

import org.xml.sax.helpers.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.servlet.jsp.*;
import java.io.*;

public class SAXExample extends DefaultHandler{
  private Writer w;
  String currentElement;
  int urlCount = 0;

  public SAXExample(java.io.Writer new_w){
   w = new_w;
  }

  public void startDocument() throws SAXException{
    try{
      w.write(new String("<b>Document Started</b>\n"));
    }
    catch(Exception e){throw new SAXException(e.toString());}
  }

  public void endDocument() throws SAXException{
    try{
      w.write(new String("<br><b>Document Finished:</b> Total URLs = " + urlCount));
    }
    catch(Exception e){throw new SAXException(e.toString());}
  }

  public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String qName, Attributes attributes) throws SAXException{
    currentElement = localName;
    if(0 == localName.compareTo("url"))
    {
      urlCount++;
      try{
        w.write(new String("<br><font color=\"blue\">URL Element.</font> Open in new window? <font color=\"red\">" + attributes.getValue(0) + "</font><br>&nbsp;&nbsp;&nbsp;"));
      }
      catch(Exception e){throw new SAXException(e.toString());}
    }
  }

  public void characters(char[] ch, int start, int length) throws SAXException{
    try{
      if (0 == currentElement.compareTo("url")){
        int count = 0;
        while(count < length)
        {
          w.write(ch[start + count]);
          count++;
        }
        w.write("\n");
      }
    }
    catch(Exception e){throw new SAXException(e.toString());}
  }
}

