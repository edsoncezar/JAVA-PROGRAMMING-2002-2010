<%
'*******************************************************
'*     ASP 101 Sample Code - http://www.asp101.com     *
'*                                                     *
'*   This code is made available as a service to our   *
'*      visitors and is provided strictly for the      *
'*               purpose of illustration.              *
'*                                                     *
'* Please direct all inquiries to webmaster@asp101.com *
'*******************************************************
%>

<%
' Just in case you're not up on my acronyms:
' DB = database, RS=recordset, CONN=connection
' o/w = otherwise

' Include the VBScript ADO constants file
%>
<!-- #INCLUDE FILE="adovbs.inc" -->
<%
' *** Begin DB Setup ***
Dim strConnString
' Sample access OLEDB CONN String.
strConnString = "Provider=Microsoft.Jet.OLEDB.4.0; Data Source=" & _
	Server.MapPath("db_scratch.mdb") & ";"
		
' Override with our site-wide CONN string.
strConnString = Application("SQLConnString")

' Access likes #, but SQL Server expects ' so you'll need to change
' this based on the DB you're using.
Const DATE_DELIMITER = "'"
' *** End DB Setup ***

Dim cnnFormToDB       ' CONN object
Dim strSQL            ' String in which to build our SQL command
Dim lngRecsAffected   ' # of records affected... just informational

' Vars for the fields read in from the form.  All fields are read
' in as strings so I need to covert them to the appropriate data
' types to be sure they're appropriate for the DB fields.  These
' variables give me some working space to do this easily.
Dim strTextField      ' text field
Dim intIntegerField   ' integer field
Dim datDateTimeField  ' date field

Dim strErrorMsg       ' Holds error message if we catch any problems.


' See if we have any info to process.
' If we don't (ie. the first time through) we just show
' the form.  If we do we proceed with the insert.
If Request.Form("action") <> "Save Form Data" Then
	' Show the form
	%>
	<FORM ACTION="<%= Request.ServerVariables("SCRIPT_NAME") %>" METHOD="post">
	<INPUT TYPE="hidden" NAME="action" VALUE="Save Form Data">
	<TABLE BORDER="0">
	<TR>
		<TD ALIGN="right"><B>Text Field:</B></TD>
		<TD ALIGN="left"><INPUT TYPE="text" NAME="text_field" MAXLENGTH="10"></TD>
	</TR>
	<TR>
		<TD ALIGN="right"><B>Integer Field:</B></TD>
		<TD ALIGN="left"><INPUT TYPE="text" NAME="integer_field"></TD>
	</TR>
	<TR>
		<TD ALIGN="right"><B>Date/Time Field:</B></TD>
		<TD ALIGN="left"><INPUT TYPE="text" NAME="date_time_field"></TD>
	</TR>
	<TR>
		<TD>&nbsp;</TD>
		<TD>
			<INPUT TYPE="reset" VALUE="Clear">
			<INPUT TYPE="submit" VALUE="Save">
		</TD>
	</TR>
	</TABLE>
	</FORM>
	<%
Else
	' Do our DB insert!

	' Retrieve the 3 strings to be entered into the DB
	strTextField     = Request.Form("text_field")
	intIntegerField  = Request.Form("integer_field")
	datDateTimeField = Request.Form("date_time_field")

	' Start error handling... I'm too lazy to check all the criteria
	' on my own so I use VBScript to do it for me.  I simply do a
	' conversion the the expected type and if it fails I catch the
	' error, abort the insert, and display a message.
	On Error Resume Next
	strErrorMsg = ""
	
	' String (text) field:
	' Nothing should really go wrong here.  It's already a string so
	' I don't bother with a CStr.  I do replace ' with '' for the
	' validity our SQL statement and also check to make sure it's
	' not an empty string.  If it is an empty string ("") then I
	' throw a fake error since I've already got this type of error
	' handling in place... hey I already admitted I was lazy!
	strTextField = Trim(strTextField)
	If Len(strTextField) = 0 Or Len(strTextField) > 10 Then Err.Raise 1
	strTextField = Replace(strTextField, "'", "''")
	If Err.number <> 0 Then
		strErrorMsg = strErrorMsg & "Your entry for string_field is " & _
			"inappropriate!<BR>" & vbCrLf
		Err.Clear
	End If

	' Integer field:
	' Lots of possible problems here.  First off it could be text and
	' not a number at all!  Even if it is a number, there are a lot
	' of restrictions on integers.  It needs to be a whole number and
	' it's absolute value can't be bigger than around 32,000.  Using
	' CInt I don't have to worry about it though.
	intIntegerField = CInt(intIntegerField)
	If Err.number <> 0 Then
		strErrorMsg = strErrorMsg & "Your entry for integer_field could " & _
			"not be converted to an integer!  Remember that integers " & _
			"need to be from -32,768 to 32,767.<BR>" & vbCrLf
		Err.Clear
	End If		
	
	' Date field:
	' Well it needs to be a valid date.  You can enter it in any format
	' the computer can understand.  Doing the CDate will not only make
	' sure it's a date, but will also make them all nice and uniform!
	datDateTimeField = CDate(datDateTimeField)
	If Err.number <> 0 Then
		strErrorMsg = strErrorMsg & "Your entry for date_time_field could " & _
			"not be converted to an date variable!<BR>" & vbCrLf
		Err.Clear
	End If

	' I don't know if this is really documented or a hack,
	' but it turns error trapping back off!
	On Error Goto 0

	' If we have an error in our error string then we show
	' the error message o/w we proceed with the insert.
	If strErrorMsg <> "" Then
		' Show the error message that got us here!
		Response.Write strErrorMsg
	Else
		' Open connection to the DB
		Set cnnFormToDB = Server.CreateObject("ADODB.Connection")
		cnnFormToDB.Open strConnString

		' Build our SQL String
		strSQL = ""
		strSQL = strSQL & "INSERT INTO scratch "
		strSQL = strSQL & "(text_field, integer_field, date_time_field) " & vbCrLf
		strSQL = strSQL & "VALUES ("
		strSQL = strSQL & "'" & strTextField & "'"
		strSQL = strSQL & ", "
		strSQL = strSQL & intIntegerField
		strSQL = strSQL & ", "
		strSQL = strSQL & DATE_DELIMITER & datDateTimeField & DATE_DELIMITER
		strSQL = strSQL & ");"

		' Execute the SQL command.  I pass it a variable lngRecsAffected
		' in which to return the number of records affected.  I also tell
		' it that this is a text command and it won't be returing any
		' records... this helps it execute the script faster!
		' And before you ask... I don't know, but YES IT IS OR!!!
		cnnFormToDB.Execute strSQL, lngRecsAffected, adCmdText Or adExecuteNoRecords

		' Dispose of the CONN object
		cnnFormToDB.Close
		Set cnnFormToDB = Nothing
		
		' Display a verification message and we're done!
		%>
		<H2>Thanks for submitting your information to us!</H2>
		
		<P>
		<B>The resulting SQL statement was:</B>
		<PRE><%= strSQL %></PRE>
		</P>
		
		<P>
		<B>Number of records affected:</B> <%= lngRecsAffected %>
		</P>
		<%
	End If
End If
%>
