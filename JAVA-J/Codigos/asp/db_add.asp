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

<%' Defining some constants to make my life easier!
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
<!-- #include file="adovbs.inc" -->
<%
Dim I            ' Standard looping var
Dim strSQL       ' String variable for building our query
Dim iRecordAdded ' Id of added record

'We're going to keep this as simple as we can.
'  1. Create a Recordset object
'  2. Connect the Recordset to the table
'  3. Add a new record to the Recordset
'  4. Set the values of the Recordset
'  5. Update the table
'  6. Close the Recordset

'Step 1:
	Dim objRecordset
	Set objRecordset = Server.CreateObject("ADODB.Recordset")

	' The following syntax is also acceptable if you move it outside of the
	' script delimiters.  I prefer to Dim and then set it like any other
	' variable, but it really doesn't make too big a difference.

	'<OBJECT RUNAT=server PROGID=ADODB.Recordset ID=objRecordset></OBJECT>

'Step 2:
	' The syntax for the open command is
	' recordset.Open Source, ActiveConnection, CursorType, LockType, Options 
	'
	' Source
	'   In this case it's our SQL statement.  It could also be a
	'   Table Name, a command object, or a stored procedure.
	' ActiveConnection
	'   We use a string which contains connection information.  It could also
	'   be a connection object which is faster if you need to open multiple
	'   recordsets. 
	' CursorType
	'   Doesn't matter too much in this case since I'm not going to be doing
	'   much with the records.  I'm opening it as a static so I can get a
	'   recordcount.
	' LockType
	'   Specifies how the provider should lock the data.  We'll use pessimistic
	'   so that it'll lock as soon as we start editing and basically ensure a
	'   successful update.
	' Options
	'   Tells what type of source we're using if it's not a command object
	'
	' Most of the above are optional to some degree.  It's usually better
	' to set them so you know what their settings are.  It'll avoid the
	' defaults coming back to haunt you when you try and do something they
	' don't allow.

	' I'm prebuilding our SQL query so it's easier to print
	' out in case we need to debug later.  I'm using a query
	' that will return no results since I'm really just trying
	' to add a new one and am not interested in the current
	' data in the table.
	strSQL = "SELECT * FROM scratch WHERE 0=1;"

	' This is the way I normally would open this RS:
	objRecordset.Open strSQL, DB_CONNECTIONSTRING, adOpenKeyset, adLockPessimistic, adCmdText
	
	' You could also do it step by step if you want:
	'objRecordset.Source           = "table_name"
	'objRecordset.ActiveConnection = DB_CONNECTIONSTRING
	'objRecordset.CursorType       = adOpenKeyset
	'objRecordset.LockType         = adLockPessimistic
	'objRecordset.Open

'Step 3:		
	' To add a new record to the current recordset we naturally call the
	' AddNew Method.
	objRecordset.AddNew

	' If you're not sure if your RS supports Adding a New record you can check
	' via the following command.  This will return True if it does, False
	' otherwise:
	' objRecordset.Supports(adAddNew)

	' Another Note: It takes arrays as input and gets confusing so I usually
	' don't do it, but you can actually specify the values on the AddNew line
	' (combining steps 3 and 4) like this:
	' objRecordset.AddNew Array("text_field", "integer_field", "date_time_field"), Array("Some Text", CInt(Day(Date())), Now())

'Step 4:
	' Here we set the values of each field.  You'll notice we don't set the
	' id field.  Since it's the primary key, I've set it as an autonumber in
	' the DB so it'll take care of creating the value for us.
	
	' I'm just pulling any values I want for insertion here. You'd probably use
	' something from a form or other user input.  Just make sure you're putting
	' the right types of data into the fields.
	
	' String / Text Data Type
	objRecordset.Fields("text_field") = CStr(WeekdayName(WeekDay(Date())))
	
	' Integer Data Type
	objRecordset.Fields("integer_field") = CInt(Day(Now()))
	
	' Date / Time Data Type
	objRecordset.Fields("date_time_field") = Now()

'Step 5:
	' Couldn't be too much easier:
	objRecordset.Update

'Show the user something:
	' Get the DB assigned ID of the record we just added.
	iRecordAdded = objRecordset.Fields("id").Value
	
	' Tell people which record we added.
	Response.Write "<p>Record id " & iRecordAdded & " added!</p>" & vbCrLf

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
				Response.Write vbTab & vbTab & "<td>" & objCleanUpRS.Fields(I) & "</td>" & vbCrLf
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

' Normally I'd cut you off right here and do the rest behind the scenes;
' however, since this has to do with the DB you were just writing to,
' I'll give you a treat and let you see some of our administative /
' housekeeping code!

' Now we clean up!
' Basically, to keep things manageable, I'm checking the DB
' to keep it under a dozen or so records.
	If iRecordCount >= 12 Then
		Set objCleanUpRS = Server.CreateObject("ADODB.Recordset")
		objCleanUpRS.Open strSQL, DB_CONNECTIONSTRING, adOpenDynamic, adLockPessimistic, adCmdText

		For I = 1 to 10
			objCleanUpRS.MoveFirst
			objCleanUpRS.Delete
		Next

		objCleanUpRS.Close
		Set objCleanUpRS = Nothing
	End If
%>
