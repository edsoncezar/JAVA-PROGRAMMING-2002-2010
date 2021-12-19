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
' BEGIN CONSTANT DEFINITION

	' The following command includes the ADODB VBScript constants file.
	' If you can't find your copy you can download a copy from:
	' http://www.asp101.com/samples/download/adovbs.inc
	' It may not be the most recent copy so use it at your own risk.

	%>
	<!-- #INCLUDE FILE="./download/adovbs.inc" -->
	<%

	' DB Configuration variables
	' After this, strictly used as if it were a Const.
	Dim DB_CONNSTRING
	DB_CONNSTRING = "DRIVER={Microsoft Access Driver (*.mdb)};DBQ=" & Server.Mappath("./db_scratch.mdb") & ";"

	' Now we override the above setting so the sample uses our SQL server.
	' Comment out the following line to use the sample Access DB.
	DB_CONNSTRING = "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
		& "Initial Catalog=samples;User Id=samples;Password=password;" _
		& "Connect Timeout=15;Network Library=dbmssocn;"

' END CONSTANT DEFINITION
%>

<%
Dim rsCount ' The recordset object

' Create an instance of an ADO Recordset
Set rsCount = Server.CreateObject("ADODB.Recordset")

' Open RS
' I'm actually doing this directly (without a connection object) to keep
' the code short and to the point.  I'm opening the RS with a static
' cursor, read only, and telling it that "scratch" is a table name and
' not a SQL command.  If I don't specify how to open the rs, I'd get the
' default cursor type which doesn't support .RecordCount!
rsCount.Open "scratch", DB_CONNSTRING, adOpenStatic, adLockReadOnly, adCmdTable

' Show RecordCount
' I dress it up and pop it into the middle of a sentence, but you can
' do whatever you want with it.
Response.Write "<p>This table currently has <strong><font color=""#FF0000"">"
Response.Write rsCount.RecordCount ' This is the line that does it!
Response.Write "</font></strong> records in it!</p>" & vbCrLf


'======================================================================
' BEGIN TABLE DISPLAY
' Now I'm going to display the table if they requested it just so you
' have something to look at!  This really doesn't pertain to the topic
' of this sample so I'm going to keep the code short but feel free to
' look it over and if you do please notice the pretty HTML it outputs!
' Ugly HTML output is a pet peeve of mine!  ;)
If LCase(Request.QueryString("showtable")) = "true" Then
	Dim Field   ' Field Looper for display
	Dim bColor  ' Use for showing alternating colors
	bColor = False
	
	' Spacers and intro
	Response.Write "<p>They are:</p>" & vbCrLf

	' Start the table
	Response.Write "<table border=""1"">" & vbCrLf

	' Write Titles
	Response.Write vbTab & "<tr>" & vbCrLf
	For Each Field in rsCount.Fields
		Response.Write vbTab & vbTab & "<td bgcolor=""#CCCCCC""><strong>" & Field.Name & "</strong></td>" & vbCrLf
	Next 'Field
	Response.Write vbTab & "</tr>" & vbCrLf

	' Loop through records outputting data
	Do While Not rsCount.EOF
		Response.Write vbTab & "<tr>" & vbCrLf
		For Each Field in rsCount.Fields
			Response.Write vbTab & vbTab & "<td bgcolor="""
			
			' Decide what color to output
			If bColor Then
				Response.Write "#CCCCFF"  ' Light blueish
			Else
				Response.Write "#FFFFFF"  ' White
			End If
			
			Response.Write """>" & Field.Value & "</td>" & vbCrLf
		Next 'Field
		Response.Write vbTab & "</tr>" & vbCrLf

		' Toggle our colors
		bColor = Not bColor
		rsCount.MoveNext
	Loop

	' End the table
	Response.Write "</table>" & vbCrLf
	Response.Write "<p><a href=""db_count.asp"">Hide the table</a></p>" & vbCrLf
Else
	Response.Write "<p><a href=""db_count.asp?showtable=true"">Show the table</a></p>" & vbCrLf
End If
' END TABLE DISPLAY - Now back to our regularly scheduled code!
'======================================================================

' Close and dispose of recordset object
rsCount.Close
Set rsCount = Nothing
%>
