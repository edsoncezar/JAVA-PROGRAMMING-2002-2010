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
' Selected constants from adovbs.inc
Const adCmdStoredProc    = 4
Const adInteger          = 3
Const adParamInput       = 1

' For more info see:
' http://www.asp101.com/articles/john/adovbs/default.asp


' Declare our vars
Dim cnnStoredQuery ' Connection object
Dim cmdStoredQuery ' Command object
Dim rstStoredQuery ' Recordset object
Dim paramId        ' Parameter object

' Establish connection to database (DB)
Set cnnStoredQuery = Server.CreateObject("ADODB.Connection")
cnnStoredQuery.Open "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" _
	& Server.MapPath("storedqueries.mdb") _
	& ";User ID=admin;"

' Create Command object we'll use to execute the query
Set cmdStoredQuery = Server.CreateObject("ADODB.Command")

' Set our Command to use our existing connection
cmdStoredQuery.ActiveConnection = cnnStoredQuery

' Message so people know what the sample's doing:
Response.Write "<p>Running the stored procedure: " _
	& "<strong>GetNameInfoById</strong>.</p>" & vbCrLf

' Set the SP's name and tell the Command object
' that the name we give is supposed to be a SP
cmdStoredQuery.CommandText = "GetNameInfoById"
cmdStoredQuery.CommandType = adCmdStoredProc

' Message so people know what the sample's doing:
Response.Write "<p>Passing it one parameter:<br />" & vbCrLf _
	& "<strong>Id</strong> = 1<br />" & vbCrLf _
	& "</p>" & vbCrLf

' Create and add the SP's required parameter to our
' Command object.
Set paramId = cmdStoredQuery.CreateParameter("Id", adInteger, adParamInput)
paramId.Value = 1
cmdStoredQuery.Parameters.Append paramId

' Run the SP by executing the command and grab
' the retruned recordset.
Set rstStoredQuery = cmdStoredQuery.Execute

' Message so people know what the sample's doing:
Response.Write "<p>It returned a recordset which I used " _
	& "to print out this name: <strong>"

' Spit out our data which I pull out of the recordset.
Response.Write Trim(rstStoredQuery.Fields("first_name"))
Response.Write " "
Response.Write Trim(rstStoredQuery.Fields("last_name"))

' Message so people know what the sample's doing:
Response.Write "</strong>.</p>" & vbCrLf

' Kill our objects
Set paramId = Nothing
Set rstStoredQuery = Nothing
Set cmdStoredQuery = Nothing

' Close and kill our connection
cnnStoredQuery.Close
Set cnnStoredQuery = Nothing
%>
