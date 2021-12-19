package com.wrox.projsp.ch11.utils;

import org.w3c.dom.*; 
import org.xml.sax.*; 
import org.apache.xalan.xpath.*; 
import org.apache.xalan.xpath.xml.*; 
import org.apache.xerces.parsers.*;

public class XPathUtil {

    private static XMLParserLiaison xpathSupport =  new XMLParserLiaisonDefault(); 
    private static XPathProcessor xpathParser = new XPathProcessorImpl(xpathSupport); 
    
    public static NodeList getNodes(Node context, String xpath) throws org.xml.sax.SAXException {
        PrefixResolver prefixResolver = new PrefixResolverDefault(context); 
        XPath xp = new XPath(); 
        xpathParser.initXPath(xp, xpath, prefixResolver); 
        XObject list = xp.execute(xpathSupport, context, prefixResolver); 
        return list.nodeset(); 
    }

    public static String getValue(Node context, String xpath) throws org.xml.sax.SAXException {
        NodeList nodes = getNodes(context, xpath); 
        if (nodes.getLength() > 0)
            return nodes.item(0).getNodeValue();
        return "";
    }

    /** Sets the values of of all nodes matching xpath to value. */
    public static void setValue(Node context, String xpath, String value) throws org.xml.sax.SAXException {
        NodeList nodes = getNodes(context, xpath); 
        for(int i = 0; i < nodes.getLength(); i++)
            nodes.item(i).setNodeValue(value);
    }
    
    /*
    public static String getAttribute(Node context, String value) {
       if(context.getAttributes() == null)
           return null;
       return context.getAttributes().getNamedItem(attribute).getNodeValue();
    }
    */

}

