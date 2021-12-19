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
' Declare our variables... always good practice!
Dim strURL     ' The URL of this page so the form will work
               ' no matter what this file is named.

Dim cnnSearch  ' ADO connection
Dim rstSearch  ' ADO recordset
Dim strDBPath  ' path to our Access database (*.mdb) file

Dim strSQL     ' The SQL Query we build on the fly
Dim strSearch  ' The text being looked for

' Retreive the URL of this page from Server Variables
strURL = Request.ServerVariables("URL")

' Retreive the term being searched for.  I'm doing it on
' the QS since that allows people to bookmark results.
' You could just as easily have used the form collection.
strSearch = Request.QueryString("search")
'strSearch = Replace(strSearch, "'", "''")

' Since I'm doing this all in one page I need to see if anyone
' has searched for something.  If they have we hit the DB.
' O/W I just show the search form and quit.

%>
<p>Search our sample db by first or last name.  (% returns all)</p>
<form action="<%= strURL %>" method="get">
<input name="search" value="<%= strSearch %>" />
<input type="submit" />
</form>
<p>[Try 'am' or 'er' for an example]</p>
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
	Set rstSearch = cnnSearch.Execute(strSQL)

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
	Do While Not rstSearch.EOF
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
	<%
	' Close our recordset and connection and dispose of the objects
	rstSearch.Close
	Set rstSearch = Nothing
	cnnSearch.Close
	Set cnnSearch = Nothing
End If

' That's all folks!  See it's really not all that hard.
%>
