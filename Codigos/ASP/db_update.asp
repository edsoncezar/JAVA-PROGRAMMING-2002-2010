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

<%' Defining some constants to make my life easier! (Same as Sample 1 & 2)
' Begin Constant Definition
	
	' DB Configuration constants
	' Fake const so we can use the MapPath to make it relative.
	' After this, strictly used as if it were a Const.
	Dim DB_CONNECTIONSTRING

	' ODBC
	'DB_CONNECTIONSTRING = "DRIVER={Microsoft Access Driver (*.mdb)};" _
	'	& "DBQ=" & Server.Mappath("./db_scratch.mdb") & ";"

	' OLE DB
	DB_CONNECTIONSTRING = "Provider=Microsoft.Jet.OLEDB.4.0;" _
		& "Data Source=" & Server.Mappath("db_scratch.mdb") & ";"

	' We don't use these, but we could if we neeeded to.
	'Const DB_USERNAME = "username"
	'Const DB_PASSWORD = "password"

	'Now we override the above settings to use our SQL server.
	'Delete the following line to use the sample Access DB.
	DB_CONNECTIONSTRING = "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
		& "Initial Catalog=samples;User Id=samples;Password=password;" _
		& "Connect Timeout=15;Network Library=dbmssocn;"

	' ADODB Constants
	' You can find these in the adovbs.inc file
	' Do a search for it and it should turn up somewhere on the server
	' If you can't find it you can download our copy from here:
	'     http://www.asp101.com/samples/download/adovbs.inc
	' It may not be the most recent copy so use it at your own risk.
' End Constant Definition
%>
	<!-- #INCLUDE FILE="adovbs.inc" -->
<%
Dim I               ' Standard looping var
Dim iRecordToUpdate ' Id of deleted record
Dim strSQL          ' String variable for building our query

'We're going to keep this as simple as we can.
'  1. Create a Recordset object
'  2. Connect the Recordset to the table
'  3. Find the record to update
'  4. Update the record
'  5. Update the table
'  6. Close the Recordset

'Step 1:
	Dim objRecordset
	Set objRecordset = Server.CreateObject("ADODB.Recordset")

'Step 2:
	' Get the Id of the record to update
	iRecordToUpdate = Request.QueryString("id")
	iRecordToUpdate = Replace(iRecordToUpdate, "'", "''")
	
	' If the record ID passed in isn't a number, we set it to
	' one so we don't cause SQL query errors.  I use 0 since I
	' know there's no record in the DB with an id of 0
	If IsNumeric(iRecordToUpdate) Then
		iRecordToUpdate = CLng(iRecordToUpdate)
	Else
		iRecordToUpdate = 0
	End If
	
	' I'm prebuilding our SQL query so it's easier to print
	' out in case we need to debug later.  I'm using a query
	' that will return just the record we want to update.
	strSQL = "SELECT * FROM scratch WHERE id=" & iRecordToUpdate & ";"

	' The syntax for the open command is
	' recordset.Open Source, ActiveConnection, CursorType, LockType, Options 
	objRecordset.Open strSQL, DB_CONNECTIONSTRING, adOpenKeyset, adLockPessimistic, adCmdText

'Step 3:		
	' The recordset should only have the one record so:
	If Not objRecordset.EOF Then
		objRecordset.MoveFirst

'Step 4:
	'Only update if we've got a record, o/w we never run this

		' String / Text Data Type
		objRecordset.Fields("text_field") = CStr(WeekdayName(WeekDay(Date())))

		' Integer Data Type
		objRecordset.Fields("integer_field") = CInt(Day(Now()))

		' Date / Time Data Type
		objRecordset.Fields("date_time_field") = Now()

'Step 5:
	'Only update if we've got a record, o/w we never run this
		objRecordset.Update
	
		Response.Write "<p>Record id " & iRecordToUpdate & " updated!</p>" & vbCrLf
	End If

'Step 6:
	' Finally we close the recordset and release the memory used by the
	' object variable by setting it to Nothing (a VBScript keyword)
	objRecordset.Close
	Set objRecordset = Nothing


'********************************
' This is the end of the sample!
'********************************

'Show Table
	' Feel free to skip this area. (Ignore the man behind the curtain!)
	' I'm just showing the Table so you have something to look at when
	' you view the sample.
	Dim objCleanUpRS

	strSQL = "SELECT * FROM scratch ORDER BY id;"

	Set objCleanUpRS = Server.CreateObject("ADODB.Recordset")
	objCleanUpRS.Open strSQL, DB_CONNECTIONSTRING, adOpenStatic, adLockReadOnly, adCmdText

	Response.Write "<table border=""1"" cellspacing=""2"" cellpadding=""2"">" & vbCrLf
	Response.Write vbTab & "<tr>" & vbCrLf
	Response.Write vbTab & vbTab & "<th>id</th>" & vbCrLf
	Response.Write vbTab & vbTab & "<th>text_field</th>" & vbCrLf
	Response.Write vbTab & vbTab & "<th>integer_field</th>" & vbCrLf
	Response.Write vbTab & vbTab & "<th>date_time_field</th>" & vbCrLf
	Response.Write vbTab & "</tr>" & vbCrLf

	If Not objCleanUpRS.EOF Then
		objCleanUpRS.MoveFirst
		'Show data
		Do While Not objCleanUpRS.EOF
			Response.Write vbTab & "<tr>" & vbCrLf
			For I = 0 To objCleanUpRS.Fields.Count - 1
				Response.Write vbTab & vbTab & "<td><a href=""db_update.asp?id=" & objCleanUpRS.Fields("id").Value & """>" & objCleanUpRS.Fields(I) & "</a></td>" & vbCrLf
			Next
			Response.Write vbTab & "</tr>" & vbCrLf
			objCleanUpRS.MoveNext
		Loop
	End If
	Response.Write "</table>" & vbCrLf

	objCleanUpRS.Close
	Set objCleanUpRS = Nothing

%>
