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

<strong><%= RetrieveAndIncrementCount() %></strong>
<%
' I placed this in a function so I wouldn't have to worry about
' any namespace collisions.  For example... if this was inline
' code and someone named a variable strSQL in a file this file
' gets included into you'd get an error.  This way you don't and
' there's no chance of the variables overwriting one another!
Function RetrieveAndIncrementCount()
	' From adovbs.inc:
	Const adOpenKeyset = 1
	Const adLockPessimistic = 2
	Const adCmdText = &H0001

	' Local variables
	Dim strFilename
	Dim strSQL
	Dim rsCounter
	Dim iCount

	' Get filename and build SQL query
	strFilename = Request.ServerVariables("SCRIPT_NAME")
	strSQL = "SELECT page_name, hit_count FROM hit_count WHERE page_name='" & strFilename & "';"

	' Open our recordset
	Set rsCounter = Server.CreateObject("ADODB.Recordset")
	
	' Access version:
	'rsCounter.Open strSQL, _
	'	"Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" & Server.MapPath("counter_db.mdb") & ";", _
	'	adOpenKeyset, adLockPessimistic, adCmdText
	
	' SQL Server version:
	rsCounter.Open strSQL, "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
		& "Initial Catalog=samples;User Id=samples;Password=password;" _
		& "Connect Timeout=15;Network Library=dbmssocn;", _
		adOpenKeyset, adLockPessimistic, adCmdText

	' If we've got a record then we read the current value
	' If we don't then we create one, set the filename, and start at 0
	If rsCounter.EOF Then
		rsCounter.AddNew

		iCount = 0

		rsCounter.Fields("page_name").Value = strFilename
	Else
		rsCounter.MoveFirst

		iCount = rsCounter.Fields("hit_count").Value
	End If

	' Increment the count and update the DB
	rsCounter.Fields("hit_count").Value = iCount + 1
	rsCounter.Update

	' Close our connection
	rsCounter.Close
	Set rsCounter = Nothing

	' Return the count (pre-incrementation).
	RetrieveAndIncrementCount = iCount
End Function
%>
