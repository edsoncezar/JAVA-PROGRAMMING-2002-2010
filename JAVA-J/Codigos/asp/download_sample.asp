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
Dim objASPFSO, objASPFile
Dim strFileName
Dim strInput, strOutput
Dim bProcessString

strFileName = Request.QueryString("file")
strOutput = ""

' Conditional limiting use of this file
If InStr(1, strFileName, "\", 1) Then strFileName=""
If InStr(1, strFileName, "/", 1) Then strFileName=""

If strFileName <> "" Then
	strFileName = Left(strFileName, Len(strFileName) - 4)

	Set objASPFSO = CreateObject("Scripting.FileSystemObject")
	Set objASPFile = objASPFSO.OpenTextFile(Server.MapPath(strFileName & ".asp"))
	
	' Loop Through Real File and Output Results to Browser
	Do While Not objASPFile.AtEndOfStream
		strInput = objASPFile.ReadLine
		' If we find Begin Script Tag start processing
		If InStr(1, strInput, "<!-- BEGIN " & "SCRIPT -->", 1) Then
			bProcessString = 1
			strInput = objASPFile.ReadLine
		End If
		' If we find End Script Tag stop processing
		If InStr(1, strInput, "<!-- END " & "SCRIPT -->", 1) Then bProcessString = 0
			
		If bProcessString = 1 Then
			'Response.Write strInput & vbCrLf
			strOutput = strOutput & strInput & vbCrLf
		End If
	Loop

	objASPFile.Close
	Set objASPFile = Nothing
	Set objASPFSO = Nothing

	'Response.AddHeader "Content-Disposition", "inline; filename=" & strFileName & ".asp"
	Response.AddHeader "Content-Disposition", "filename=" & strFileName & ".asp"
	'Response.AddHeader "Content-Disposition", "attachment; filename=" & strFileName & ".asp"
	
	'Response.ContentType = "application/rtf"
	'Response.ContentType = "application/save"
	Response.ContentType = "application/octet-stream"
	'Response.ContentType = "application/unknown"  ' Causes security zone issues in IE4
	'Response.ContentType = "application/asp"      ' Causes security zone issues in IE4

	Response.Write "<" & "%" & vbCrLf
	Response.Write "'*******************************************************" & vbCrLf
	Response.Write "'*     ASP 101 Sample Code - http://www.asp101.com     *" & vbCrLf
	Response.Write "'*                                                     *" & vbCrLf
	Response.Write "'*   This code is made available as a service to our   *" & vbCrLf
	Response.Write "'*      visitors and is provided strictly for the      *" & vbCrLf
	Response.Write "'*               purpose of illustration.              *" & vbCrLf
	Response.Write "'*                                                     *" & vbCrLf
	Response.Write "'* Please direct all inquiries to webmaster@asp101.com *" & vbCrLf
	Response.Write "'*******************************************************" & vbCrLf
	Response.Write "%" & ">" & vbCrLf
		
	Response.Write vbCrLf

	Response.Write strOutput
Else
	' Response.Write "Can't find that file!"
	Response.Write "Nothing to see here!"
End If
%>
