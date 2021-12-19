<%@ LANGUAGE="VBScript" %>
<%
Option Explicit
Response.Buffer = true

'***************************************
'Reciprocal Link Checker
'***************************************
'
'Created on: 2/13/2004
'        by: Sidney Forcier (sforcier@sidneyforcier.com)
'
'This is open source code and may be modified and distributed freely, however the 
'  original copyright belongs to the author and this header information may not be 
'  changed. Please do not sell this code.


'***************************************
'Main code execution
'***************************************
'
'The following code is executed every time the page is loaded. Functions and subroutines 
'  are at the end of the file. 

Dim blnSubmitted

If Request.Form("cmdSubmit") <> "" Then
	blnSubmitted = true
End If

%>
<HTML>
<BODY>
	<%
	If blnSubmitted Then
		Call ShowResults()
	Else
		Call ShowForm()
	End If
	%>
</BODY>
</HTML>
<%

'****************************************
'Functions and Subroutines
'****************************************


'This subroutine will display the form for users to enter in the search term and URLs
'  they wish to search.
Sub ShowForm()
	If Request.QueryString("err") = "1" Then
		Response.Write "<STRONG>You must enter information in both text boxes.</STRONG><BR /><BR />"
	End If
%>	
	<FORM name="Form1" action="rl_sample.asp" method="post">
		Enter text to search for:<BR />
		<TEXTAREA name="txtSearch" rows="5" cols="50"></TEXTAREA><BR />
		Enter URLs to check (one per line):<BR />
		<TEXTAREA name="txtURLs" rows="20" cols="50"></TEXTAREA><BR />
		<INPUT type="Submit" name="cmdSubmit" value="Submit" /> <INPUT type="Reset" name="cmdReset" value="Clear" />
	</FORM>
	
<%
End Sub

'This subroutine will display the results from the form the user entered information into.
Sub ShowResults()
	If Request.Form("txtSearch") = "" Or Request.Form("txtURLs") = "" Then
		Response.Redirect("rl_sample.asp")
		Exit Sub
	End If

	'Declare variables
	Dim i, strSearch, arrURLs, xmlWebPage, strWebPage

	'Initialize variables
	Set xmlWebPage = Server.CreateObject ("Microsoft.XMLHTTP")
	'Set xmlWebPage = Server.CreateObject("MSXML2.ServerXMLHTTP")
	strSearch = Request.Form("txtSearch")
	arrURLs = Split(Server.HTMLEncode(Request.Form("txtURLs")), vbCrLf)
	i = 0

	'Dislay results in a table.
	Response.Write "<STRONG>Results</STRONG><BR /><BR /><TABLE border=""1"" cellpadding=""3"">"
	
	'Iterate through the URLs provided in the txtURLs TextBox
	Do While (i <= UBound(arrURLs))
		Response.Write "<TR><TD>" & Trim(arrURLs(i))	& "</TD><TD>"
			
		'Get the web page and store the text in strWebPage
		On Error Resume Next
		xmlWebPage.Open "GET", Trim(arrURLs(i)), false
		xmlWebPage.Send
		
		If Err.number <> 0 Then
			Response.Write "Could not open the web page"
		Else
			strWebPage = xmlWebPage.ResponseText
		
			'Look for the search string in the Web Page
			If InStr(1, strWebPage, strSearch) > 0 Then
				Response.Write "<STRONG>Search text found</STRONG><BR />"
			Else
				Response.Write "Search text not found<BR />"
			End If
		End If
		
		Response.Write "</TD></TR>"
		i = i + 1
	Loop
	Response.Write "</TABLE>"
	
	'Clean up objects
	Set xmlWebPage = Nothing 

End Sub

%>
