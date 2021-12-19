package com.jspinsider.jspkit.jaxp;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import java.lang.Object;

public class JAXPTransformerResources extends java.lang.Object implements java.io.Serializable
{

private Object XMLSource;
private Object XSLSource;
private Object transformResult;

public JAXPTransformerResources(){}

public Object getXMLSource(){ return XMLSource; }
public Object getXSLSource(){ return XSLSource; }
public Object getTransformResult(){ return transformResult; }

public void setXMLStreamSource(StreamSource newXMLStreamSource)
   { XMLSource = newXMLStreamSource; }
public void setXMLDOMSource(DOMSource newXMLDOMSource)
   { XMLSource = newXMLDOMSource; }
public void setXMLSAXSource(SAXSource newXMLSAXSource)
   { XMLSource = newXMLSAXSource; }
public void setXSLStreamSource(StreamSource newXSLStreamSource)
   { XSLSource = newXSLStreamSource; }
public void setXSLDOMSource(DOMSource newXSLDOMSource)
   { XSLSource = newXSLDOMSource; }
public void setXSLSAXSource(SAXSource newXSLSAXSource)
   { XSLSource = newXSLSAXSource; }
public void setStreamResult(StreamResult newStreamResult)
   { transformResult = newStreamResult; }
public void setDOMResult(DOMResult newDOMResult)
   { transformResult = newDOMResult; }
public void setSAXResult(SAXResult newSAXResult)
   { transformResult = newSAXResult; }
}
