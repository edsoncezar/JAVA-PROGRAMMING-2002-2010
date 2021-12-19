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
Const adCurrency         = 6
Const adParamInput       = 1
Const adParamOutput      = 2
Const adExecuteNoRecords = 128

' For more info see:
' http://www.asp101.com/articles/john/adovbs/default.asp


' Declare our vars
Dim cnnStoredProc ' Connection object
Dim cmdStoredProc ' Command object
Dim rstStoredProc ' Recordset object (for part 2)
Dim paramId       ' Parameter object

' Establish connection to database (DB)
Set cnnStoredProc = Server.CreateObject("ADODB.Connection")
cnnStoredProc.Open "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
    & "Initial Catalog=samples;User Id=samples;Password=password;" _
    & "Connect Timeout=15;Network Library=dbmssocn;"

' ********************************************************
' *** Stored Proc #1 - Parameter In & Return Value Out ***
' ********************************************************

' Create Command object we'll use to execute the SP
Set cmdStoredProc = Server.CreateObject("ADODB.Command")

' Set our Command to use our existing connection
cmdStoredProc.ActiveConnection = cnnStoredProc

' Message so people know what the sample's doing:
Response.Write "<p>Running the stored procedure: " _
	& "<strong>GetSalesById</strong>.</p>" & vbCrLf

' Set the SP's name and tell the Command object
' that the name we give is supposed to be a SP
cmdStoredProc.CommandText = "GetSalesById"
cmdStoredProc.CommandType = adCmdStoredProc

' Message so people know what the sample's doing:
Response.Write "<p>Passing it two parameters:<br />" & vbCrLf _
	& "<strong>@Id</strong> = 1<br />" & vbCrLf _
	& "<strong>@Sales</strong> = (an output parameter)<br />" & vbCrLf _
	& "</p>" & vbCrLf

' Create and add the SP's required parameters to our
' Command object.  The first is an input param which
' I'm passing in with the value 1.  The second is an
' output param which will return our value. This
' particular SP passes back a return value and doesn't
' return any records.
Set paramId = cmdStoredProc.CreateParameter("@Id", adInteger, adParamInput)
paramId.Value = 1
cmdStoredProc.Parameters.Append paramId

' If you find it simpler, it is possible to combine
' multiple commands into one long line like this:
cmdStoredProc.Parameters.Append cmdStoredProc.CreateParameter("@Sales", adCurrency, adParamOutput)

' Run the SP by executing the command.  I added the
' adExecuteNoRecords option so that ADO knows not to
' bother returning the empty recordset.
cmdStoredProc.Execute , , adExecuteNoRecords

' Message so people know what the sample's doing:
Response.Write "<p>It returned the value: <strong>"

' Spit out our return value which we pull out of the
' parameters collection.
Response.Write cmdStoredProc.Parameters("@Sales").Value

' Message so people know what the sample's doing:
Response.Write "</strong>.</p>" & vbCrLf

' Kill our objects
Set paramId = Nothing
Set cmdStoredProc = Nothing


' A little spacing for the display
Response.Write vbCrLf & "<p>&nbsp;</p>" & vbCrLf & vbCrLf


' *****************************************************
' *** Stored Proc #2 - Parameter In & Recordset Out ***
' *****************************************************

' Create Command object we'll use to execute the SP
Set cmdStoredProc = Server.CreateObject("ADODB.Command")

' Set our Command to use our existing connection
cmdStoredProc.ActiveConnection = cnnStoredProc

' Message so people know what the sample's doing:
Response.Write "<p>Running the stored procedure: " _
	& "<strong>GetNameInfoById</strong>.</p>" & vbCrLf

' Set the SP's name and tell the Command object
' that the name we give is supposed to be a SP
cmdStoredProc.CommandText = "GetNameInfoById"
cmdStoredProc.CommandType = adCmdStoredProc

' Message so people know what the sample's doing:
Response.Write "<p>Passing it one parameter:<br />" & vbCrLf _
	& "<strong>@Id</strong> = 1<br />" & vbCrLf _
	& "</p>" & vbCrLf

' Create and add the SP's required parameter to our
' Command object.  Only the one this time... still
' passing in the same value = 1.  This time the SP
' passes back a full recordset.
Set paramId = cmdStoredProc.CreateParameter("@Id", adInteger, adParamInput)
paramId.Value = 1
cmdStoredProc.Parameters.Append paramId

' Run the SP by executing the command and grab
' the returned recordset.
Set rstStoredProc = cmdStoredProc.Execute

' Message so people know what the sample's doing:
Response.Write "<p>It returned a recordset which I used " _
	& "to print out this name: <strong>"

' Spit out our data which I pull out of the recordset.
Response.Write Trim(rstStoredProc.Fields("first_name"))
Response.Write " "
Response.Write Trim(rstStoredProc.Fields("last_name"))

' Message so people know what the sample's doing:
Response.Write "</strong>.</p>" & vbCrLf

' Kill our objects
Set paramId = Nothing
Set rstStoredProc = Nothing
Set cmdStoredProc = Nothing

' Close and kill our connection
cnnStoredProc.Close
Set cnnStoredProc = Nothing
%>
