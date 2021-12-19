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
' This function takes a filename and returns the appropriate image for
' that file type based on it's extension.  If you pass it "dir", it assumes
' that the corresponding item is a directory and shows the folder icon.
Function ShowImageForType(strName)
	Dim strTemp

	' Set our working string to the one passed in
	strTemp = strName
	
	' If it's not a directory, get the extension and set it to strTemp
	' If it is a directory, then we already have the correct value
	If strTemp <> "dir" Then
		strTemp = LCase(Right(strTemp, Len(strTemp) - InStrRev(strTemp, ".", -1, 1)))
	End If
	
	' Debugging line used to perfect that above string parser
	'Response.Write strTemp
	
	' Set the part of the image file name that's unique to the type of file
	' to it's correct value and set this to strTemp. (yet another use of it!)
	Select Case strTemp
		Case "asp"
			strTemp = "asp"
		Case "dir"
			strTemp = "dir"
		Case "htm", "html"
			strTemp = "htm"
		Case "gif", "jpg"
			strTemp = "img"
		Case "txt"
			strTemp = "txt"
		Case Else
			strTemp = "misc"
	End Select

	' All our logic is done... build the IMG Tag for display to the browser
	' Place it into... where else... strTemp!

	' My images are all GIFs and all start with "dir_" for my own sanity.
	' They end with one of the values set in the select statement above.

	strTemp = "<IMG SRC=""./images/dir_" & strTemp & ".gif"" WIDTH=16 HEIGHT=16 BORDER=0>"

	' Set return value and exit function
	ShowImageForType = strTemp
End Function
'That's it for functions on this one!
%>


<%' Now to the Runtime code:
Dim strPath   'Path of directory to show
Dim objFSO    'FileSystemObject variable
Dim objFolder 'Folder variable
Dim objItem   'Variable used to loop through the contents of the folder

' You could just as easily read this from some sort of input, but I don't
' need you guys and gals roaming around our server so I've hard coded it to
' a directory I set up to illustrate the sample.
' NOTE: As currently implemented, this needs to end with the /
strPath = "./dir/"

' Create our FSO
Set objFSO = Server.CreateObject("Scripting.FileSystemObject")

' Get a handle on our folder
Set objFolder = objFSO.GetFolder(Server.MapPath(strPath))

' Show a little description line and the title row of our table
%>
Contents of <B><%= strPath %></B><BR>

<BR>

<TABLE BORDER="5" BORDERCOLOR="green" CELLSPACING="0" CELLPADDING="2">
	<TR BGCOLOR="#006600">
		<TD><FONT COLOR="#FFFFFF"><B>File Name:</B></FONT></TD>
		<TD><FONT COLOR="#FFFFFF"><B>File Size (bytes):</B></FONT></TD>
		<TD><FONT COLOR="#FFFFFF"><B>Date Created:</B></FONT></TD>
		<TD><FONT COLOR="#FFFFFF"><B>File Type:</B></FONT></TD>
	</TR>
<%

' First I deal with any subdirectories.  I just display them and when you
' click you go to them via plain HTTP.  You might want to loop them back
' through this file once you've set it up to take a path as input.  It seems
' like the logical thing to do to me at least!
For Each objItem In objFolder.SubFolders
	' Deal with the stupid VTI's that keep giving our visitors 404's
	If InStr(1, objItem, "_vti", 1) = 0 Then
	%>
	<TR BGCOLOR="#CCFFCC">
		<TD ALIGN="left" ><%= ShowImageForType("dir") %>&nbsp;<A HREF="<%= strPath & objItem.Name %>"><%= objItem.Name %></A></TD>
		<TD ALIGN="right"><%= objItem.Size %></TD>
		<TD ALIGN="left" ><%= objItem.DateCreated %></TD>
		<TD ALIGN="left" ><%= objItem.Type %></TD>
	</TR>
	<%
	End If
Next 'objItem

' Now that I've done the SubFolders, do the files!
For Each objItem In objFolder.Files
	%>
	<TR BGCOLOR="#CCFFCC">
		<TD ALIGN="left" ><%= ShowImageForType(objItem.Name) %>&nbsp;<A HREF="<%= strPath & objItem.Name %>"><%= objItem.Name %></A></TD>
		<TD ALIGN="right"><%= objItem.Size %></TD>
		<TD ALIGN="left" ><%= objItem.DateCreated %></TD>
		<TD ALIGN="left" ><%= objItem.Type %></TD>
	</TR>
	<%
Next 'objItem

' All done!  Kill off our object variables.
Set objItem = Nothing
Set objFolder = Nothing
Set objFSO = Nothing

' Oops... I almost forgot to close up the table.  IE will forgive you,
' but Netscape will catch you on this error - AS IT SHOULD!

' Ok, so I'm a little odd being a Microsoft supporter and yet I still 
' use Netscapes's browser... I just like it better... so sue me!
'
' I probably shouldn't say that too loudly...
'                    ...you never know what the next lawsuit will be!
%>
</TABLE>
