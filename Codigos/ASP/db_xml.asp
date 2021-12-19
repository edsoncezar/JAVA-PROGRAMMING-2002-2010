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

<!-- #include file="adovbs.inc" -->
<%
' Constants file included above.

' Delete existing file
Dim objFSO
Set objFSO = Server.CreateObject("Scripting.FileSystemObject")
If objFSO.FileExists(Server.MapPath("db_xml.xml")) Then
	objFSO.DeleteFile Server.MapPath("db_xml.xml")
End IF
Set objFSO = Nothing

' Declare our variables... always good practice.
Dim cnnXML  ' ADO connection
Dim rstXML  ' ADO recordset

' Create an ADO Connection to connect to the scratch database.
' We're using OLE DB but you could just as easily use ODBC or a DSN.
Set cnnXML = Server.CreateObject("ADODB.Connection")

' This line is for the Access sample database:
'cnnXML.Open "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" _
'	& Server.MapPath("db_scratch.mdb") & ";"

' We're actually using a connection to our SQL Server:
cnnXML.Open "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
	& "Initial Catalog=samples;User Id=samples;Password=password;" _
	& "Connect Timeout=15;Network Library=dbmssocn;"

' Execute a query using the connection object.  It automatically
' creates and returns a recordset which we store in our variable.
Set rstXML = Server.CreateObject("ADODB.Recordset")
Set rstXML = cnnXML.Execute("SELECT * FROM scratch ORDER BY id;")

Response.Write "<p>Saving data as XML...</p>" & vbCrLf

' Save the file to XML format.
rstXML.Save Server.MapPath("db_xml.xml"), adPersistXML

' Close our recordset and connection and dispose of the objects
rstXML.Close
Set rstXML = Nothing
cnnXML.Close
Set cnnXML = Nothing

Response.Write "<p>XML file written...</p>" & vbCrLf
Response.Write "<p>Click <a href=""db_xml.xml"">here</a> to view the file.</p>" & vbCrLf
%>
