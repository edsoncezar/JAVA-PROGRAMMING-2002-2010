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
' Constants ripped from adovbs.inc:
Const adOpenStatic = 3
Const adLockReadOnly = 1
Const adCmdText = &H0001

' Our own constants:
Const PAGE_SIZE = 5  ' The size of our pages.

' Declare our variables... always good practice!
Dim strURL     ' The URL of this page so the form will work
               ' no matter what this file is named.

Dim cnnSearch  ' ADO connection
Dim rstSearch  ' ADO recordset
Dim strDBPath  ' path to our Access database (*.mdb) file

Dim strSQL     ' The SQL Query we build on the fly
Dim strSearch  ' The text being looked for

Dim iPageCurrent ' The page we're currently on
Dim iPageCount   ' Number of pages of records
Dim iRecordCount ' Count of the records returned
Dim I            ' Standard looping variable

' Retreive the URL of this page from Server Variables
strURL = Request.ServerVariables("URL")

' Retreive the term being searched for.  I'm doing it on
' the QS since that allows people to bookmark results.
' You could just as easily have used the form collection.
strSearch = Request.QueryString("search")
strSearch = Replace(strSearch, "'", "''")

' Retrieve page to show or default to the first
If Request.QueryString("page") = "" Then
	iPageCurrent = 1
Else
	iPageCurrent = CInt(Request.QueryString("page"))
End If

' Since I'm doing this all in one page I need to see if anyone
' has searched for something.  If they have we hit the DB.
' O/W I just show the search form and quit.
%>
<p>Search our sample db by first or last name.  (% returns all)</p>
<form action="<%= strURL %>" method="get">
<input name="search" value="<%= strSearch %>" />
<input type="submit" />
</form>
<p>
[Since we've got a very small sample DB, try a single letter
search like 'a' or 'e' for an example that actually pages!]
</p>
<%
If strSearch <> "" Then
	' MapPath of virtual database file path to a physical path.
	' If you want you could hard code a physical path here.
	strDBPath = Server.MapPath("database.mdb")


	' Create an ADO Connection to connect to the sample database.
	' We're using OLE DB but you could just as easily use ODBC or a DSN.
	Set cnnSearch = Server.CreateObject("ADODB.Connection")

	' This line is for the Access sample database:
	'cnnSearch.Open "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" & strDBPath & ";"

	' We're actually using SQL Server so we use this line instead:
	cnnSearch.Open "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
		& "Initial Catalog=samples;User Id=samples;Password=password;" _
		& "Connect Timeout=15;Network Library=dbmssocn;"

	' Build our query based on the input.
	strSQL = "SELECT last_name, first_name, sales " _
		& "FROM sample " _
		& "WHERE last_name LIKE '%" & Replace(strSearch, "'", "''") & "%' " _
		& "OR first_name LIKE '%" & Replace(strSearch, "'", "''") & "%' " _
		& "ORDER BY last_name;"

	' Execute our query using the connection object.  It automatically
	' creates and returns a recordset which we store in our variable.
	Set rstSearch = Server.CreateObject("ADODB.Recordset")
	rstSearch.PageSize  = PAGE_SIZE
	rstSearch.CacheSize = PAGE_SIZE

	' Open our recordset
	rstSearch.Open strSQL, cnnSearch, adOpenStatic, adLockReadOnly, adCmdText

	' Get a count of the number of records and pages
	' for use in building the header and footer text.
	iRecordCount = rstSearch.RecordCount
	iPageCount   = rstSearch.PageCount

	If iRecordCount = 0 Then
		' Display no records error.
		%>
		<p>
		No records found.  Please try again.
		</p>
		<%
	Else
		' Move to the page we need to show.	
		rstSearch.AbsolutePage = iPageCurrent

		' Show a quick status line letting people know where they are:
		%>
		<hr />
		<p>
		<%= iRecordCount %> Records Found.
		Displaying page <%= iPageCurrent %>
		of <%= iPageCount %>:
		</p>
		<%
		' Display a table of the data in the recordset.  We loop through the
		' recordset displaying the fields from the table and using MoveNext
		' to increment to the next record.  We stop when we reach EOF.
		' For fun I'm combining some fields and showwing you can do more then
		' just spit out the data in the form it is in in the table.
		%>
		<table border="1">
		<tr>
		<th>Name</th>
		<th>Sales</th>
		</tr>
		<%
		Do While Not rstSearch.EOF And rstSearch.AbsolutePage = iPageCurrent
			%>
			<tr>
			<td><%= rstSearch.Fields("first_name").Value %> <%= rstSearch.Fields("last_name").Value %></td>
			<td><%= rstSearch.Fields("sales").Value %></td>
			</tr>
			<%

			rstSearch.MoveNext
		Loop
		%>
		</table>
		<p>
		<%
		' Now we need to show our navigation links:
		
		' Show "previous" and "next" page links which pass the page to
		' view our search parameter.  You could also use form buttons
		' but I find this looks better.
		If iPageCurrent > 1 Then
			%>
			<a href="<%= strURL %>?search=<%= Server.URLEncode(strSearch) %>&page=<%= iPageCurrent - 1 %>">[&lt;&lt; Prev]</a>
			<%
		End If

		' You can also show page numbers:
		For I = 1 To iPageCount
			If I = iPageCurrent Then
				%>
				<%= I %>
				<%
			Else
				%>
				<a href="<%= strURL %>?search=<%= Server.URLEncode(strSearch) %>&page=<%= I %>"><%= I %></a>
				<%
			End If
		Next 'I

		If iPageCurrent < iPageCount Then
			%>
			<a href="<%= strURL %>?search=<%= Server.URLEncode(strSearch) %>&page=<%= iPageCurrent + 1 %>">[Next &gt;&gt;]</a>
			<%
		End If
		%>
		</p>
		<%
	End If

	' Close our recordset and connection and dispose of the objects
	rstSearch.Close
	Set rstSearch = Nothing
	cnnSearch.Close
	Set cnnSearch = Nothing
End If
%>
