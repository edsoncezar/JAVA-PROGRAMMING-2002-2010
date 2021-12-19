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

<%' Defining some constants to make my life easier! (Same as Sample 1)
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
Dim iRecordToDelete ' Id of deleted record
Dim strSQL          ' String variable for building our query

'We're going to keep this as simple as we can.
'  1. Create a Recordset object
'  2. Connect the Recordset to the table
'  3. Find the record to delete
'  4. Delete It!
'  5. Update the table
'  6. Close the Recordset

'Step 1:
	Dim objRecordset
	Set objRecordset = Server.CreateObject("ADODB.Recordset")

'Step 2:
	' Get the Id of the record to delete 
	iRecordToDelete = Request.QueryString("id")
	iRecordToDelete = Replace(iRecordToDelete, "'", "''")
	
	' If the record ID passed in isn't a number, we set it to
	' one so we don't cause SQL query errors.  I use 0 since I
	' know there's no record in the DB with an id of 0
	If IsNumeric(iRecordToDelete) Then
		iRecordToDelete = CLng(iRecordToDelete)
	Else
		iRecordToDelete = 0
	End If
	
	' I'm prebuilding our SQL query so it's easier to print
	' out in case we need to debug later.  I'm using a query
	' that will return just the record we want to delete.
	strSQL = "SELECT * FROM scratch WHERE id=" & iRecordToDelete & ";"

	' The syntax for the open command is
	' recordset.Open Source, ActiveConnection, CursorType, LockType, Options 
	objRecordset.Open strSQL, DB_CONNECTIONSTRING, adOpenKeyset, adLockPessimistic, adCmdText

'Step 3:		
	' Not really much to do here!
	' We're looking to delete the only record from the current recordset.
	' The first one is the one to delete.

	' Note: If the data was a little more important (or of any value at all
	' to us!), we'd probably check some other criteria or at least check to see
	' if it's the oldest record in the recordset.  Since it's not and we really
	' don't care, here goes!

'Step 4:
	'Make sure we actually have a record to delete.
	If Not objRecordset.EOF Then
		objRecordset.MoveFirst
		objRecordset.Delete adAffectCurrent

		' You can also delete groups of records which satisfy the filter
		' property setting if you're doing this in batch mode.  For this
		' situation we're just killing the one record so we don't bother.
	End If

'Step 5:
	' We don't need to do the update unless we batch it like mentioned above!
	'objRecordset.UpdateBatch
	
	' Show a message saying we deleted a record
	If iRecordToDelete <> 0 Then
		Response.Write "Record id " & iRecordToDelete & " deleted!"
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
	Dim iRecordCount

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
				Response.Write vbTab & vbTab & "<td><a href=""db_del.asp?id=" & objCleanUpRS.Fields("id").Value & """>" & objCleanUpRS.Fields(I) & "</a></td>" & vbCrLf
			Next
			Response.Write vbTab & "</tr>" & vbCrLf
			objCleanUpRS.MoveNext
		Loop
	End If
	Response.Write "</table>" & vbCrLf

	' Get recordcount so we know if we need to clean up.
	iRecordCount = objCleanUpRS.RecordCount

	objCleanUpRS.Close
	Set objCleanUpRS = Nothing


' Now this is REALLY behind the curtain!

' Normally I'd cut you off right here and do the rest behind the scenes; however,
' since this has to do with the DB you were just writing to, I'll give you a
' treat and let you see some of our administative / housekeeping code!

' Now we clean up!
' Basically, to keep things manageable, I'm checking the DB to keep it over
' 2 records.
If iRecordCount <= 2 Then
	Set objCleanUpRS = Server.CreateObject("ADODB.Recordset")

	objCleanUpRS.Open strSQL, DB_CONNECTIONSTRING, adOpenStatic, adLockPessimistic, adCmdText

	For I = 1 to 2
		objCleanUpRS.AddNew
		objCleanUpRS.Fields("text_field") = CStr(WeekdayName(WeekDay(Date())))
		objCleanUpRS.Fields("integer_field")   = CInt(Day(Now()))
		objCleanUpRS.Fields("date_time_field") = Now()
	Next
	objCleanUpRS.Update

	objCleanUpRS.Close
	Set objCleanUpRS = Nothing
End If
%>
