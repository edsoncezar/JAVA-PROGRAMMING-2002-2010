package com.jspinsider.jspkit.jaxp;

import java.lang.Object;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.TransformerException;

public class JAXPTransformer extends java.lang.Object implements java.io.Serializable
{

 JAXPTransformerResources source;

 public JAXPTransformer(){}

 public JAXPTransformer(JAXPTransformerResources source) throws  TransformerException
   {
   transform(source);
   }

public void transform (JAXPTransformerResources source) throws TransformerException
   {

   Object XMLSource = source.getXMLSource();
   Object XSLSource = source.getXSLSource();
   Object transformResult = source.getTransformResult();

   TransformerFactory tFactory = TransformerFactory.newInstance();
   Transformer transformer = tFactory.newTransformer();

if(XSLSource.getClass().getName().compareTo("javax.xml.transform.stream.StreamSource") == 0);
      transformer = tFactory.newTransformer((StreamSource)XSLSource);
   if(XSLSource.getClass().getName().compareTo("javax.xml.transform.dom.DOMSource") == 0)
      transformer = tFactory.newTransformer((DOMSource)XSLSource);
   if(XSLSource.getClass().getName().compareTo("javax.xml.transform.sax.SAXSource") == 0)
      transformer = tFactory.newTransformer((SAXSource)XSLSource);

if(XMLSource.getClass().getName().compareTo("javax.xml.transform.stream.StreamSource") == 0)
      {
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.stream.StreamResult") == 0)
         transformer.transform((StreamSource)XMLSource, (StreamResult)transformResult);
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.dom.DOMResult") == 0)
         transformer.transform((StreamSource)XMLSource, (DOMResult)transformResult);
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.sax.SAXResult") == 0)
         transformer.transform((StreamSource)XMLSource, (SAXResult)transformResult);
      }
   if(XMLSource.getClass().getName().compareTo("javax.xml.transform.dom.DOMSource") == 0)
      {
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.stream.StreamResult") == 0)
         transformer.transform((DOMSource)XMLSource, (StreamResult)transformResult);
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.dom.DOMResult") == 0)
         transformer.transform((DOMSource)XMLSource, (DOMResult)transformResult);
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.sax.SAXResult") == 0)
         transformer.transform((DOMSource)XMLSource, (SAXResult)transformResult);
      }
   if(XMLSource.getClass().getName().compareTo("javax.xml.transform.sax.SAXSource") == 0)
      {
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.stream.StreamResult") == 0)
         transformer.transform((SAXSource)XMLSource, (StreamResult)transformResult);
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.dom.DOMResult") == 0)
         transformer.transform((SAXSource)XMLSource, (DOMResult)transformResult);
      if(transformResult.getClass().getName().compareTo("javax.xml.transform.sax.SAXResult") == 0)
         transformer.transform((SAXSource)XMLSource, (SAXResult)transformResult);
      }
   }

public void setTransformerResources(JAXPTransformerResources new_resources)
   {
   source = new_resources;
   }

public JAXPTransformerResources getTransformerResources()
   {
   return source;
   }
}
