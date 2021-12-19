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
' Declare variables for the File System Object and the File to be accessed.
Dim objFSO, objTextFile

' Create an instance of the the File System Object and assign it to objFSO.
Set objFSO = CreateObject("Scripting.FileSystemObject")

' Open the file
'Set objTextFile = objFSO.OpenTextFile("C:\InetPub\PR86027\samples\textfile.txt")
Set objTextFile = objFSO.OpenTextFile(Server.MapPath("textfile.txt"))

Do While Not objTextFile.AtEndOfStream
	Response.Write objTextFile.ReadLine & "<BR>" & vbCrLf
Loop

' Close the file.
objTextFile.Close

' Release reference to the text file.
Set objTextFile = Nothing

' Release reference to the File System Object.
Set objFSO = Nothing
%>

<BR>

<A HREF="./textfile_w.asp">Write to the text file</A><BR>

