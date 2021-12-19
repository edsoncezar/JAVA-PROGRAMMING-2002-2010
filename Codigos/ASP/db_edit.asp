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
' NOTE: Since I'm writing for illustration, I'm doing almost
' no error checking so there are a number of places where this
' script could easily throw errors if you give it invalid input.
' Before you use this anywhere outside of a testing environment
' be sure you add the appropriate error handling and securty.

' Declare some "pseudo-constants".  These are really variables
' because when declaring a Const you can't use any functions
' to do it.  I use the old Const naming structure so I know that
' after this they don't get changed anywhere.

' Our connection string... you can replace this with whatever
' connection string you want to use.
Dim CONN_STRING
CONN_STRING = "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
	& "Initial Catalog=samples;User Id=samples;Password=password;" _
	& "Connect Timeout=15;Network Library=dbmssocn;"

' The name of this file so all the links and forms will still
' work if you rename it.
Dim SCRIPT_NAME
SCRIPT_NAME = Request.ServerVariables("SCRIPT_NAME")

' I use this link a lot so I threw it into a "pseudo-const"
' so I don't have to keep typing it.
Dim BACK_TO_LIST_TEXT
BACK_TO_LIST_TEXT = "<p>Click <a href=""" & SCRIPT_NAME & """>" _
    & "here</a> to go back to record list.</p>"


' Declare our standard variables.
Dim cnnDBEdit, rstDBEdit  ' ADO objects

Dim strSQL      ' To hold various SQL Strings
Dim iRecordId   ' Used to keep track of the record in play

' Choose what to do by looking at the action parameter
Select Case LCase(Trim(Request.QueryString("action")))
	Case "add"
		' Select an empty RS
		strSQL = "SELECT * FROM scratch WHERE id=0;"

		Set rstDBEdit = Server.CreateObject("ADODB.Recordset")
		rstDBEdit.Open strSQL, CONN_STRING, adOpenKeyset, adLockOptimistic, adCmdText

		' Add our record and set it's values.  You could bounce
		' into an edit mode here to let people enter the initial
		' values, but for simplicity I just add the record with
		' some default values.
		rstDBEdit.AddNew

		rstDBEdit.Fields("text_field").Value      = CStr(WeekdayName(WeekDay(Date())))
		rstDBEdit.Fields("integer_field").Value   = CInt(Day(Now()))
		rstDBEdit.Fields("date_time_field").Value = Now()

		rstDBEdit.Update

		' Get the id of the record just added. This might cause
		' problems with some DB providers, but it works with
		' the SQL Server our sample runs off.
		iRecordId = rstDBEdit.Fields("id").Value

		rstDBEdit.Close
		Set rstDBEdit = Nothing

		Response.Write("<p>Record Id #" & iRecordId & " added!</p>")
		Response.Write(BACK_TO_LIST_TEXT)

		' Here's the more efficient way, but since I want to get
		' back the new record's id I'm not using it.
		'
		'strSQL = "INSERT INTO scratch " _
		'    & "(text_field, integer_field, date_time_field) " _
		'    & "VALUES (" _
		'    & "'" & CStr(WeekdayName(WeekDay(Date()))) & "', " _
		'    & CInt(Day(Now())) & ", " _
		'    & "'" & Now() & "'" _
		'    & ")"
		'
		''Response.Write strSQL
		'
		'Set cnnDBEdit = Server.CreateObject("ADODB.Connection")
		'cnnDBEdit.Open CONN_STRING
		'
		'cnnDBEdit.Execute strSQL, adAffectAll, adCmdText Or adExecuteNoRecords
		'
		'cnnDBEdit.Close
		'Set cnnDBEdit = Nothing
	Case "delete"
		' Get the id to delete
		iRecordId = Request.QueryString("id")
		If IsNumeric(iRecordId) Then
			iRecordId = CLng(iRecordId)
		Else
			iRecordId = 0
		End If

		strSQL = "DELETE FROM scratch WHERE id=" & iRecordId & ";"

		Set cnnDBEdit = Server.CreateObject("ADODB.Connection")
		cnnDBEdit.Open CONN_STRING

		cnnDBEdit.Execute strSQL, adAffectAll, adExecuteNoRecords

		cnnDBEdit.Close
		Set cnnDBEdit = Nothing

		' We assume all is fine.  Notice that we really don't
		' check that anything was done... we just assume
		' so since no error was thrown.  For example... if
		' you enter an id that's not really in the DB, the
		' script runs fine, but nothing gets deleted.
		Response.Write("Record Id #" & iRecordId & " deleted!")
		Response.Write(BACK_TO_LIST_TEXT)
	Case "edit"
		' First of a 2 part process... build a form with the
		' values from the db.
		iRecordId = Request.QueryString("id")
		If IsNumeric(iRecordId) Then
			iRecordId = CLng(iRecordId)
		Else
			iRecordId = 0
		End If

		strSQL = "SELECT * FROM scratch WHERE id=" & iRecordId & ";"

		Set rstDBEdit = Server.CreateObject("ADODB.Recordset")
		rstDBEdit.Open strSQL, CONN_STRING, adOpenKeyset, adLockOptimistic, adCmdText

		If Not rstDBEdit.EOF Then
			%>
			<p>
			Note: Watch your input... the text field is small and no error
			handling is done to check for valid integers or dates.  If
			an error gets thrown when you submit simply hit back and fix
			the offending entry before resubmitting.
			</p>

			<form action="<%= SCRIPT_NAME %>?action=editsave" method="post">
				<input type="hidden" name="id" value="<%= rstDBEdit.Fields("id").Value %>" />

				<input type="text" name="text_field" value="<%= Server.HTMLEncode(rstDBEdit.Fields("text_field").Value) %>" /><br />
				<input type="text" name="integer_field" value="<%= Server.HTMLEncode(rstDBEdit.Fields("integer_field").Value) %>" /><br />
				<input type="text" name="date_time_field" value="<%= Server.HTMLEncode(rstDBEdit.Fields("date_time_field").Value) %>" /><br />

				<input type="submit" name="Update Database">
			</form>
			<%
		Else
			Response.Write "Record not found!"
		End If

		rstDBEdit.Close
		Set rstDBEdit = Nothing

		Response.Write(BACK_TO_LIST_TEXT)
	Case "editsave"
		' Part 2 of 2: Here's where we save the values that the
		' user entered back to the DB.  Again... no error
		' handling or input checking so ' characters and invalid
		' values will throw error messages.
		iRecordId = Request.Form("id")
		iRecordId = Replace(iRecordId, "'", "''")

		' Date delimiter on this should be changed to # for Access
		strSQL = "UPDATE scratch SET " _
			& "text_field = '" & CStr(Replace(Request.Form("text_field"), "'", "''")) & "', " _
			& "integer_field = " & CInt(Replace(Request.Form("integer_field"), "'", "''")) & ", " _
			& "date_time_field = '" & CDate(Replace(Request.Form("date_time_field"), "'", "''")) & "' " _
			& "WHERE (id = " & iRecordId & ")"

		' If something does throw an error, checking this is
		' actually a valid command often helps debug.
		'Response.Write strSQL

		Set cnnDBEdit = Server.CreateObject("ADODB.Connection")
		cnnDBEdit.Open CONN_STRING

		cnnDBEdit.Execute strSQL, adAffectAll, adCmdText Or adExecuteNoRecords

		cnnDBEdit.Close
		Set cnnDBEdit = Nothing

		Response.Write("<p>Record Id #" & iRecordId & " updated!</p>")
		Response.Write(BACK_TO_LIST_TEXT)
	Case Else ' view
		' Our default action... just lists the records in the DB
		strSQL = "SELECT * FROM scratch ORDER BY id;"

		Set rstDBEdit = Server.CreateObject("ADODB.Recordset")
		rstDBEdit.Open strSQL, CONN_STRING, adOpenForwardOnly, adLockReadOnly, adCmdText
		%>
		<table border="1" cellspacing="2" cellpadding="2">
		<thead>
		<tr>
			<th>id</th>
			<th>text_field</th>
			<th>integer_field</th>
			<th>date_time_field</th>
			<th>Delete</th>
			<th>Edit</th>
		</tr>
		</thead>
		<tbody>
		<%
		Do While Not rstDBEdit.EOF
			%>
			<tr>
				<td><%= rstDBEdit.Fields("id").Value %></td>
				<td><%= rstDBEdit.Fields("text_field").Value %></td>
				<td><%= rstDBEdit.Fields("integer_field").Value %></td>
				<td><%= rstDBEdit.Fields("date_time_field").Value %></td>
				<td><a href="<%= SCRIPT_NAME %>?action=delete&id=<%= rstDBEdit.Fields("id").Value %>">Delete</a></td>
				<td><a href="<%= SCRIPT_NAME %>?action=edit&id=<%= rstDBEdit.Fields("id").Value %>">Edit</a></td>
			</tr>
			<%
			rstDBEdit.MoveNext
		Loop
		%>
		</tbody>
		<tfoot>
		<tr>
			<td colspan="6" align="right"><a href="<%= SCRIPT_NAME %>?action=add">Add a new record</a></td>
		</tr>
		</tfoot>
		</table>
		<%
		rstDBEdit.Close
		Set rstDBEdit = Nothing
End Select
%>

<%
'********************************
' This is the end of the sample!
'********************************

' Feel free to skip this area. (Ignore the man behind the curtain!)
' To keep things manageable and working for other visitors, I'm
' checking to make sure you don't delete all the records and adding
' some to keep at least 2 records in the DB.

Dim cnnCleanUp, rstCleanUp, iRecordCount

Set cnnCleanUp = Server.CreateObject("ADODB.Connection")
cnnCleanUp.Open CONN_STRING

strSQL = "SELECT COUNT(*) FROM scratch;"

Set rstCleanUp = cnnCleanUp.Execute(strSQL, , adCmdText)
iRecordCount = rstCleanUp.Fields(0).Value
rstCleanUp.Close
Set rstCleanUp = Nothing

'Response.Write iRecordCount
If iRecordCount <= 2 Then
	strSQL = "INSERT INTO scratch " _
	    & "(text_field, integer_field, date_time_field) " _
	    & "VALUES (" _
	    & "'" & CStr(WeekdayName(WeekDay(Date()))) & "', " _
	    & CInt(Day(Now())) & ", '" & Now() & "')"

	' Add 2
	cnnCleanUp.Execute strSQL, adAffectAll, adCmdText Or adExecuteNoRecords
	cnnCleanUp.Execute strSQL, adAffectAll, adCmdText Or adExecuteNoRecords
End If

cnnCleanUp.Close
Set cnnCleanUp = Nothing
%>
