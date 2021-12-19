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
' Declare our variables... always good practice!
Dim cnnSimple  ' ADO connection
Dim rstSimple  ' ADO recordset
Dim strDBPath  ' path to our Access database (*.mdb) file


' MapPath of virtual database file path to a physical path.
' If you want you could hard code a physical path here.
strDBPath = Server.MapPath("db_scratch.mdb")


' Create an ADO Connection to connect to the scratch database.
' We're using OLE DB but you could just as easily use ODBC or a DSN.
Set cnnSimple = Server.CreateObject("ADODB.Connection")

' This line is for the Access sample database:
'cnnSimple.Open "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" & strDBPath & ";"

' We're actually using SQL Server so we use this line instead:
cnnSimple.Open "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
	& "Initial Catalog=samples;User Id=samples;Password=password;" _
	& "Connect Timeout=15;Network Library=dbmssocn;"


' Execute a query using the connection object.  It automatically
' creates and returns a recordset which we store in our variable.
Set rstSimple = cnnSimple.Execute("SELECT * FROM scratch")


' Display a table of the data in the recordset.  We loop through the
' recordset displaying the fields from the table and using MoveNext
' to increment to the next record.  We stop when we reach EOF.
%>
<table border="1">
<%
Do While Not rstSimple.EOF
	%>
	<tr>
		<td><%= rstSimple.Fields("id").Value %></td>
		<td><%= rstSimple.Fields("text_field").Value %></td>
		<td><%= rstSimple.Fields("integer_field").Value %></td>
		<td><%= rstSimple.Fields("date_time_field").Value %></td>
	</tr>
	<%
	rstSimple.MoveNext
Loop
%>
</table>
<%
' Close our recordset and connection and dispose of the objects
rstSimple.Close
Set rstSimple = Nothing
cnnSimple.Close
Set cnnSimple = Nothing

' That's all folks!
%>
