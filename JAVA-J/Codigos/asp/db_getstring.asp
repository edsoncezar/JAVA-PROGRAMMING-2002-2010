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
' Selected constants from adovbs.inc:
Const adClipString = 2

' Declare our variables... always good practice!
Dim cnnGetString   ' ADO connection
Dim rstGetString   ' ADO recordset
Dim strDBPath      ' Path to our Access DB (*.mdb) file
Dim strDBData      ' String that we dump all the data into
Dim strDBDataTable ' String that we dump all the data into
                   ' only this time we build a table

' MapPath to our mdb file's physical path.
strDBPath = Server.MapPath("db_scratch.mdb")

' Create a Connection using OLE DB
Set cnnGetString = Server.CreateObject("ADODB.Connection")

' This line is for the Access sample database:
'cnnGetString.Open "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" & strDBPath & ";"

' We're actually using SQL Server so we use this line instead.
' Comment this line out and uncomment the Access one above to
' play with the script on your own server.
cnnGetString.Open "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
	& "Initial Catalog=samples;User Id=samples;Password=password;" _
	& "Connect Timeout=15;Network Library=dbmssocn;"

' Execute a simple query using the connection object.
' Store the resulting recordset in our variable.
Set rstGetString = cnnGetString.Execute("SELECT * FROM scratch")

' Now this is where it gets interesting... Normally we'd do
' a loop of some sort until we ran into the last record in
' in the recordset.  This time we're going to get all the data
' in one fell swoop and dump it into a string so we can
' disconnect from the DB as quickly as possible.
strDBData = rstGetString.GetString()

' Since I'm doing this twice for illustration... I reposition
' at the beginning of the RS before the second call.
rstGetString.MoveFirst

' This time I ask for everything back in HTML table format:
strDBDataTable = rstGetString.GetString(adClipString, -1, _
	"</td><td>", "</td></tr>" & vbCrLf & "<tr><td>", "&nbsp;")

' Because of my insatiable desire for neat HTML, I actually
' truncate the string next.  You see, GetString only has
' a parameter for what goes between rows and not a seperate
' one for what to place after the last row.  Because of the
' way HTML tables are built, this leaves us with an extra
' <tr><td> after the last record.  GetString places the
' whole delimiter at the end since it doesn't have anything
' else to place there and in many situations this works fine.
' With HTML it's a little bit weird.  Most developers simply
' close the row and move on, but I couldn't bring myself to
' leave the extra row... especially since it would have a
' different number of cells then all the others.
' What can I say... these things tend to bother me.  ;)
strDBDataTable = Left(strDBDataTable, Len(strDBDataTable) - Len("<tr><td>"))

' Some notes about .GetString:
' The Method actually takes up to 5 optional arguments:
' 1. StringFormat    - The format in which to return the
'                      recordset text. adClipString is the only
'                      valid value.
' 2. NumRows         - The number of rows to return.  Defaults
'                      to  -1 indicating all rows.
' 3. ColumnDelimiter - The text to place in between the columns.
'                      Defaults to a tab character
' 4. RowDelimiter    - The text to place in between the rows
'                      Defaults to a carriage return
' 5. NullExpr        - Expression to use if a NULL value is
'                      returned.  Defaults to an empty string.

' Close our recordset and connection and dispose of the objects.
' Notice that I'm able to do this before we even worry about
' displaying any of the data!
rstGetString.Close
Set rstGetString = Nothing
cnnGetString.Close
Set cnnGetString = Nothing

' Display the table of the data.  I really don't need to do
' any formatting since the GetString call did most everything
' for us in terms of building the table text.
Response.Write "<table border=""1"">" & vbCrLf
Response.Write "<tr><td>"
Response.Write strDBDataTable
Response.Write "</table>" & vbCrLf

' FYI: Here's the output format you get if you cann GetString
' without any parameters:
Response.Write vbCrLf & "<p>Here's the unformatted version:</p>" & vbCrLf
Response.Write "<pre>" & vbCrLf
Response.Write strDBData
Response.Write "</pre>" & vbCrLf

' That's all folks!
%>
