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
' Selected constants from adovbs.inc
Const adOpenStatic = 3
Const adLockPessimistic = 2

Dim cnnExcel
Dim rstExcel
Dim I
Dim iCols

' This is all standard ADO except for the connection string.
' You can also use a DSN instead, but so it'll run out of the
' box on your machine I'm using the string instead.
Set cnnExcel = Server.CreateObject("ADODB.Connection")
cnnExcel.Open "DBQ=" & Server.MapPath("xl_data.xls") & ";" & _
	"DRIVER={Microsoft Excel Driver (*.xls)};"

' Same as any other data source.
' FYI: TestData is my named range in the Excel file
Set rstExcel = Server.CreateObject("ADODB.Recordset")
rstExcel.Open "SELECT * FROM TestData;", cnnExcel, _
	adOpenStatic, adLockPessimistic

' Get a count of the fields and subtract one since we start
' counting from 0.
iCols = rstExcel.Fields.Count
%>
<table border="1">
	<thead>
		<%
		' Show the names that are contained in the first row
		' of the named range.  Make sure you include them in
		' your range when you create it.
		For I = 0 To iCols - 1
			Response.Write "<th>"
			Response.Write rstExcel.Fields.Item(I).Name
			Response.Write "</th>" & vbCrLf
		Next 'I
		%>
	</thead>
	<%
	rstExcel.MoveFirst

	' Loop through the data rows showing data in an HTML table.
	Do While Not rstExcel.EOF
		Response.Write "<tr>" & vbCrLf
		For I = 0 To iCols - 1
			Response.Write "<td>"
			Response.Write rstExcel.Fields.Item(I).Value
			Response.Write "</td>" & vbCrLf
		Next 'I
		Response.Write "</tr>" & vbCrLf

		rstExcel.MoveNext
	Loop
	%>
</table>

<%
rstExcel.Close
Set rstExcel = Nothing

cnnExcel.Close
Set cnnExcel = Nothing
%>
