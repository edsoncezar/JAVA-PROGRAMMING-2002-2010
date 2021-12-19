package com.wrox.projsp.ch11.jsp;
import java.io.*;
import java.util.*;
import org.w3c.dom.*; 
import org.xml.sax.*; 
import org.apache.xalan.xpath.*; 
import org.apache.xalan.xpath.xml.*; 
import org.apache.xerces.parsers.*;
import org.apache.xml.serialize.*;
import com.wrox.projsp.ch11.utils.*;

public class DiscussionBean extends PageBean
{

    public static int maxId = 99;

    public DiscussionBean() { 
        set("minVotes", "1");
        set("openMessage", "1");
        set("root", "/xml");
        if(discussion != null)
            return;
        setDocument("discussion.xml");
        try {
            messageTemplate = XPathUtil.getNodes(discussion, "//message[@id=0]").item(0);
        } catch (Exception e) {        
            e.printStackTrace();
        }
    }
    
    private static Node messageTemplate;
    
    private static Node discussion;
    public void setDiscussion(Node discussion) { this.discussion = discussion; }
    public Node getDiscussion() { return discussion; }
    
    public String getDocument() { return get("document"); }
    public void setDocument(String document) {
        try {
            set("document", document);
            String path = get("root") + "/" + get("document");
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(this.getClass().getResourceAsStream(path)));
            setDiscussion(parser.getDocument().getDocumentElement());
        } catch (Exception e) {        
            e.printStackTrace();
        }
    }

    public static synchronized void doVote(String id) throws org.xml.sax.SAXException {
      String votes = XPathUtil.getValue(
          discussion, 
          "//message[@id='" + id + "']/@votes");
      XPathUtil.setValue(
          discussion, 
          "//message[@id='" + id + "']/@votes", 
          Integer.parseInt(votes) + 1 + "");
    }

    public void afterPopulate() {
        System.out.println("DiscussionBean.afterPopulate()");
        super.afterPopulate();
    }
    
    public void doRequestReplyForm(String id) {
         set("forward", "/reply.jsp");
    }

    public void doSubmitReplyForm(String id)  
              throws org.xml.sax.SAXException {
      NodeList nodes = XPathUtil.getNodes(discussion, "//message[@id='" + id + "']");
      if(nodes.getLength()==0) {
        set("forward", "/error.jsp");
        return;
      }
      Node message = nodes.item(0);
      Node reply = messageTemplate.cloneNode(true);
      int level = 1 + Integer.parseInt(XPathUtil.getValue(
                          discussion, "//message[@id='" + id + "']/@level"));
      XPathUtil.setValue(reply, "subject/text()", get("subject"));
      XPathUtil.setValue(reply, "body/text()", get("body"));
      XPathUtil.setValue(reply, "@author", get("author"));
      XPathUtil.setValue(reply, "@email", get("email"));
      XPathUtil.setValue(reply, "@id", "" + ++maxId);
      XPathUtil.setValue(reply, "@level", "" + level);
      synchronized(discussion) {
        message.appendChild(reply);
      }
      set("forward", "/discuss.jsp");
    }
    
    public void doSave(String value) {
        System.out.println("saving file");
        try {
            OutputFormat outputFormat =  new OutputFormat("XML", null, true);
            outputFormat.setIndent(4);
            outputFormat.setOmitXMLDeclaration(true);
            SerializerFactory factory = SerializerFactory.getSerializerFactory(Method.XML);
            DOMSerializer serializer = (DOMSerializer) factory.makeSerializer(
            new FileOutputStream("test.xml"), outputFormat);
                serializer.serialize((Element) discussion);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
