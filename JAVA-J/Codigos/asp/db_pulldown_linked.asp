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
' Questions about adovbs.inc?
' See "What is Adovbs.inc and Why Do I Need It?"
'   http://www.asp101.com/articles/john/adovbs/default.asp


Dim strSQL
Dim cnnSalesDB
Dim rstSalesmen, rstSales
Dim iRequestedID

' Some basic checking and cleaning of the input
iRequestedID = Trim(Request.QueryString("id"))
iRequestedID = Replace(iRequestedID, "'", "''")

If IsNumeric(iRequestedID) Then
	iRequestedID = CInt(iRequestedID)
Else
	iRequestedID = 0
End If

' The form links back to this same file passing back the id
%>
<p>
Pick a salesman to see the details of one of their sales:
</p>
<form action="db_pulldown_linked.asp" method="get">
<%

' Create ADO data connection object
Set cnnSalesDB = Server.CreateObject("ADODB.Connection")

' Open data connection - Use this line to use Access
'cnnSalesDB.Open "DBQ=" & Server.MapPath("db_pulldown_linked.mdb") & ";" _
'	& "Driver={Microsoft Access Driver (*.mdb)};", "admin", ""

' Open data connection - Our SQL Server code
cnnSalesDB.Open "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
	& "Initial Catalog=samples;Connect Timeout=15;" _
	& "Network Library=dbmssocn;", "samples", "password"

' Build our query for select box 1
strSQL = "SELECT * FROM sample ORDER BY last_name;"

' Create and open recordset object using existing connection
Set rstSalesmen = Server.CreateObject("ADODB.Recordset")
rstSalesmen.Open strSQL, cnnSalesDB, adOpenForwardOnly, adLockOptimistic, adCmdText

' Build our drop down box of salesmen names
If Not rstSalesmen.EOF Then
	rstSalesmen.MoveFirst
	%>
	<select name="id">
		<option></option>
		<% ' Loop through names
		Do While Not rstSalesmen.EOF
			Response.Write "<option value="""
			Response.Write rstSalesmen.Fields("id")
			Response.Write """"
			If rstSalesmen.Fields("id") = CInt(iRequestedID) Then
				Response.Write "selected=""true"""
			End If
			Response.Write ">"
			Response.Write Trim(rstSalesmen.Fields("first_name"))
			Response.Write " "
			Response.Write Trim(rstSalesmen.Fields("last_name"))
			Response.Write "</option>" & vbCrLf

			' Move to next record
			rstSalesmen.MoveNext
		Loop
		%>
	</select>
	<%
End If

' Close ADO objects we're finished with and free DB variables
rstSalesmen.Close
Set rstSalesmen =  Nothing

' If a request for a specific id comes in, then build second select box
If iRequestedID <> 0 Then
	strSQL = "SELECT * FROM sales WHERE SalesmanId = " _
		& iRequestedID & " ORDER BY timestamp"

	Set rstSales = Server.CreateObject("ADODB.Recordset")
	rstSales.Open strSQL, cnnSalesDB, adOpenForwardOnly, adLockOptimistic, adCmdText
	
	' Build our drop down box of the salesmen's sales
	If Not rstSales.EOF Then
		rstSales.MoveFirst
		%>
		<select name="sale">
			<option></option>
			<% ' Loop through names
			Do While Not rstSales.EOF
				Response.Write "<option>$"
				Response.Write Trim(rstSales.Fields("amount"))
				Response.Write " on "
				Response.Write Trim(rstSales.Fields("timestamp"))
				Response.Write "</option>" & vbCrLf

				' Move to next record
				rstSales.MoveNext
			Loop
			%>
		</select>
		<%
	End If

	' Close ADO objects we're finished with and free DB variables
	rstSales.Close
	Set rstSales =  Nothing
End If

' Close ADO objects we're finished with and free DB variables
cnnSalesDB.Close
Set cnnSalesDB = Nothing
%>
<input type="submit" value="Submit" />
</form>
