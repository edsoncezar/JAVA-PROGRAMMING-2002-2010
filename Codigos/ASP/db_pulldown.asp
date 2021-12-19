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
Dim objDC, objRS

' Create and establish data connection
Set objDC = Server.CreateObject("ADODB.Connection")
objDC.ConnectionTimeout = 15
objDC.CommandTimeout = 30

'Use this line to use Access
'objDC.Open "DBQ=" & Server.MapPath("database.mdb") & ";Driver={Microsoft Access Driver (*.mdb)};DriverId=25;MaxBufferSize=8192;Threads=20;", "username", "password"

'Our SQL Server code - use above line to use sample on your server
objDC.Open "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
	& "Initial Catalog=samples;Connect Timeout=15;" _
	& "Network Library=dbmssocn;", "samples", "password"

' Create recordset and retrieve values using the open connection
Set objRS = Server.CreateObject("ADODB.Recordset")
' Opening record set with a forward-only cursor (the 0) and in read-only mode (the 1)

' If a request for a specific id comes in, then do it o/w just show pulldown
If Len(Request.QueryString("id")) <> 0 Then
	' request record for requested id
	objRS.Open "SELECT * FROM sample WHERE id = " & Replace(Request.QueryString("id"), "'", "''"), objDC, 0, 1
	' Show selected record
	If Not objRS.EOF Then
		objRS.MoveFirst
		%>
		<table border="2">
			<tr>
				<td><strong>ID Number</strong></td>
				<td><strong>First Name</strong></td>
				<td><strong>Last Name</strong></td>
				<td><strong>Month's Sales</strong></td>
			</tr>
			<tr>
				<td align="center"><%= objRS.Fields("id") %></td>
				<td align="left"><%= objRS.Fields("first_name") %></td>
				<td align="left"><%= objRS.Fields("last_name") %></td>
				<td align="right"><%= objRS.Fields("sales") %></td>
			</tr>
		</table>
		<%
	End If
	objRS.Close
End If

objRS.Open "sample", objDC, 0, 1
' Loop through recordset and display results
If Not objRS.EOF Then
	objRS.MoveFirst
	' the form below calls this file only this time with an id in the QueryString
	%>
	<form action="db_pulldown.asp" method="get">
	<select name="id">
		<option></option>
	<%
	' Continue until we get to the end of the recordset.
	Do While Not objRS.EOF
		' For each record we create a option tag and set it's value to the employee id
		' The text we set to the employees first name combined with a space and then their last name
		%>
		<option value="<%= objRS.Fields("id") %>"><%= objRS.Fields("first_name") & " " & objRS.Fields("last_name") %></option>
		<%
	' Get next record
	objRS.MoveNext
	Loop
	%>
	</select>
	<input type="submit" value="Submit" />
	</form>
	<%
End If

' Close Data Access Objects and free DB variables
objRS.Close
Set objRS =  Nothing
objDC.Close
Set objDC = Nothing
%>
