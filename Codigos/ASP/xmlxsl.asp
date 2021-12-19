<%
'*******************************************************
'*     ASP 101 Sample Code - http://www.asp101.com/    *
'*                                                     *
'*   This code is made available as a service to our   *
'*      visitors and is provided strictly for the      *
'*               purpose of illustration.              *
'*                                                     *
'*      http://www.asp101.com/samples/license.asp      *
'*                                                     *
'* Please direct all inquiries to webmaster@asp101.com *
'*******************************************************
%>

<%
Dim objXML
Dim objXSL
Dim strHTML

'Load the XML File
Set objXML = Server.CreateObject("Microsoft.XMLDOM")
objXML.async = False
objXML.load(Server.MapPath("xmlxsl.xml"))

'Load the XSL File
Set objXSL = Server.CreateObject("Microsoft.XMLDOM")
objXSL.async = False
objXSL.load(Server.MapPath("xmlxsl.xsl"))

' Transform the XML file using the XSL stylesheet
strHTML = objXML.transformNode(objXSL)

Set objXML = Nothing
Set objXSL = Nothing

' Spit out the resulting HTML... the data comes from the
' .xml file, but the formatting of the results depends
' completely upon the .xsl file.
Response.Write strHTML
%>
