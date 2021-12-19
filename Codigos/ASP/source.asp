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

<B>ASP Source Code:</B>
<HR>
<%
Dim objFSO, objInFile 'object variables for file access
Dim strIn, strTemp    'string variables for reading and color processing
Dim I                 'standard loop control variable
Dim strASPFileName    'string containing filename of ASP file to view
Dim ProcessString     'flag determining whether or not to output each line

' We don't start showing code till we find the start script comment
ProcessString = 0

' Get file name from query string
strASPFileName = Request.QueryString("file")

' Conditional limiting use of this file to current directory
If InStr(1, strASPFileName, "\", 1) Then strASPFileName = ""
If InStr(1, strASPFileName, "/", 1) Then strASPFileName = ""

' Set the default so it shows itself if nothing or an invalid
' path is passed in.  Delete the following line to just display
' a message.
If strASPFileName = "" Then strASPFileName = "source.asp"

If strASPFileName <> "" Then
	Set objFSO = CreateObject("Scripting.FileSystemObject")
	Set objInFile = objFSO.OpenTextFile("D:\Webs\asp101\www\samples\" & strASPFileName)

	Response.Write "<PRE>" & vbCRLF
	' Loop Through Real File and Output Results to Browser
	Do While Not objInFile.AtEndOfStream
		strIn = Server.HTMLEncode(objInFile.ReadLine)
		' Check for start script comment
		If InStr(1, strIn, "&lt;!-- BEGIN SCRIPT --&gt;", 1) Then
			ProcessString = 1
			strIn = Server.HTMLEncode(objInFile.ReadLine)
		End If
		' Check for end script comment
		If InStr(1, strIn, "&lt;!-- END SCRIPT --&gt;", 1) Then ProcessString = 0
		' If we're on a line to be processed then do so
		If ProcessString = 1 Then
			strTemp = ""
			' Loop through line
			For I = 1 to Len(strIn)
				' First look for script openers to start red
				If InStr(I, strIn, "&lt;%", 1) = I Then
		 			strTemp = strTemp & "<FONT COLOR=#FF0000>" & Mid(strIn, I, 1)
				Else
					' If no script openers look for closers to end red
					If InStr(I, strIn, "%&gt;", 1) = I Then
						strTemp = strTemp & "%&gt;</FONT>"
						I = I + 4
					Else
						' If neither just copy to strTemp as is
						strTemp = strTemp & Mid(strIn, I, 1)
					End If
				End If
			Next
			' Output out processed line
			Response.Write strTemp & vbCRLF
		End If
	Loop
	Response.Write "</PRE>" & vbCRLF
	
	' Close file and free variables
	objInFile.Close
	Set objInFile = Nothing
	Set objFSO = Nothing
Else
	' If they entered no filename or one with a / or \ ... deny access
	Response.Write "Sorry, but you do not have access to view files outside the current directory."
End If
%>
