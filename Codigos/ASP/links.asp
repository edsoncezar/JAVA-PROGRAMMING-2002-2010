<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0059)http://www.asp101.com/samples/download.asp?file=links%2Easp -->
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META content="MSHTML 5.50.4134.100" name=GENERATOR></HEAD>
<BODY><XMP><%
'*******************************************************
'*     ASP 101 Sample Code - http://www.asp101.com     *
'*                                                     *
'*   This code is made available as a service to our   *
'*      visitors and is provided strictly for the      *
'*               purpose of illustration.              *
'*                                                     *
'* Please direct all inquiries to webmaster@asp101.com *
'*******************************************************
%>

<%
'******** BEGIN CONSTANTS ********
'Response.Write Server.MapPath("links.htm")
Const LINKS_FILE = "D:\Webs\asp101\www\samples\links.htm"
'******** END CONSTANTS ********

'******** BEGIN FUCTION DECLARATIONS ********
Function GetLinksFileAsString()
	Dim objFSO, objInputFile
	Dim strTemp ' as String

	Set objFSO = Server.CreateObject("Scripting.FileSystemObject")
	Set objInputFile = objFSO.OpenTextFile(LINKS_FILE)

	strTemp = objInputFile.ReadAll

	objInputFile.Close
	Set objInputFile = Nothing
	Set objFSO = Nothing
	
	GetLinksFileAsString = strTemp
End Function

Function GetLinkCount(strFullLinksFileText)
	Dim I ' as Integer
	I = 1
	Do While Instr(1, strFullLinksFileText, "<!-- Begin Link " & I & " -->") <> 0
		I = I + 1
	Loop
	GetLinkCount = I - 1
End Function

' Not called directly by runtime code!
' - wrapped by the next few functions to encapsulate parameters
Function GetHTMLBetweenStrings(strFullLinksFileText, strStart, strEnd)
	Dim iStart, iEnd
	
	iStart = Instr(1, strFullLinksFileText, strStart) + Len(strStart)
	iEnd = Instr(iStart, strFullLinksFileText, strEnd)
	
	GetHTMLBetweenStrings = Mid(strFullLinksFileText, iStart, iEnd - iStart)
End Function

Function GetLinkHTML(iLinkNumber, strFullLinksFileText)
	GetLinkHTML = GetHTMLBetweenStrings(strFullLinksFileText, "<!-- Begin Link " & iLinkNumber & " -->", "<!-- End Link " & iLinkNumber & " -->")
End Function

Function GetCommentHTML(iLinkNumber, strFullLinksFileText)
	GetCommentHTML = GetHTMLBetweenStrings(strFullLinksFileText, "<!-- Begin Comment " & iLinkNumber & " -->", "<!-- End Comment " & iLinkNumber & " -->")
End Function

Function GetHTMLBetweenLinkAndComment(iLinkNumber, strFullLinksFileText)
	GetHTMLBetweenLinkAndComment = GetHTMLBetweenStrings(strFullLinksFileText, "<!-- End Link " & iLinkNumber & " -->", "<!-- Begin Comment " & iLinkNumber & " -->")
End Function

Function GetHTMLBetweenLinks(iPreviousLinkNumber, strFullLinksFileText)
	GetHTMLBetweenLinks = GetHTMLBetweenStrings(strFullLinksFileText, "<!-- End Comment " & iPreviousLinkNumber & " -->", "<!-- Begin Link " & iPreviousLinkNumber + 1 & " -->")
End Function

Function GetHeaderHTML(strFullLinksFileText)
	Dim strTemp
	Dim strEnd
	Dim iStart, iEnd
	strTemp = strFullLinksFileText
	
	strEnd = "<!-- Begin Link 1 -->"
	
	iStart = 1
	iEnd = Instr(iStart, strFullLinksFileText, strEnd)
	
	strTemp = Mid(strTemp, iStart, iEnd - iStart)

	GetHeaderHTML = strTemp
End Function

Function GetFooterHTML(strFullLinksFileText, iLinkCount)
	Dim strTemp
	Dim strStart
	Dim iStart, iEnd
	strTemp = strFullLinksFileText
	
	strStart = "<!-- End Comment " & iLinkCount & " -->"
	
	iStart = Instr(1, strFullLinksFileText, strStart) + Len(strStart)
	iEnd = Len(strFullLinksFileText) + 1

	strTemp = Mid(strTemp, iStart, iEnd - iStart)

	GetFooterHTML = strTemp
End Function

Sub WriteLinksFileToDisk(strFullLinksFileText)
	Dim objFSO, objLinksFile
	Dim strTemp ' as String

	Set objFSO = Server.CreateObject("Scripting.FileSystemObject")
	Set objLinksFile = objFSO.CreateTextFile(LINKS_FILE, True)

	objLinksFile.Write strFullLinksFileText

	objLinksFile.Close
	Set objLinksFile = Nothing
	Set objFSO = Nothing
End Sub
'******** END FUCTION DECLARATIONS ********


'******** BEGIN RUNTIME CODE ********
Dim strInput ' as String - will contain the whole links html page!
Dim strOutput ' as String - will contain modified version!
Dim strTemp ' as String - used for string building etc.
Dim iLinkCount ' as Integer - number of links we're dealing with
Dim iLink ' as Integer - stores what link # we're performing the operation on
Dim I ' as Integer - standard loop controller

' Get the current links page and place it into strInput
strInput = GetLinksFileAsString()

' Get the current number of links on the links page
iLinkCount = GetLinkCount(strInput)

iLink = Request.QueryString("link")
If IsNumeric(iLink) Then
	iLink = CInt(iLink)
Else
	iLink = 0
End If


' Debugging Lines!
'Response.Write "="
'Response.Write strInput
'Response.Write iLinkCount
'Response.Write GetHeaderHTML(strInput)
'Response.Write GetLinkHTML(1, strInput)
'Response.Write GetCommentHTML(1, strInput)
'Response.Write GetFooterHTML(strInput, iLinkCount)
'Response.Write "="

' Figure out what we're supposed to do with the file now that we have it!
' Options implemented are add, edit, delete, and the default case "view"
' Sort is a choice but has been (in the spirit of my old Calculus text) "left as an exercise for the reader!"
' If anyone is actually interested, leave me a comment and we MIGHT do it for you!
Select Case Request.QueryString("action")
	Case "add" ' Add a new link - This could be done in less code using the Replace function
		' If we've already entered new link, write links to links file o/w show add form
		If Request.Form("write") = "yes" Then
			' Start the new string with the everthing up to the first link
			strOutput = GetHeaderHTML(strInput)
		
			' Loop through existing links placing them into the new string
			For I = 1 to iLinkCount
				strOutput = strOutput & "<!-- Begin Link " & I & " -->" & GetLinkHTML(I, strInput) & "<!-- End Link " & I & " -->"
				strOutput = strOutput & GetHTMLBetweenLinkAndComment(I, strInput)
				strOutput = strOutput & "<!-- Begin Comment " & I & " -->" & GetCommentHTML(I, strInput) & "<!-- End Comment " & I & " -->"
				If I <> iLinkCount Then
					strOutput = strOutput & GetHTMLBetweenLinks(I, strInput)
				Else
					strOutput = strOutput & GetHTMLBetweenLinks(I - 1, strInput)
				End If
			Next 'I
		
			' Add the new link to the end of the new string
			strOutput = strOutput & "<!-- Begin Link " & iLinkCount + 1 & " -->" & Request.Form("link") & "<!-- End Link " & iLinkCount + 1 & " -->"
			strOutput = strOutput & GetHTMLBetweenLinkAndComment(iLinkCount, strInput)
			strOutput = strOutput & "<!-- Begin Comment " & iLinkCount + 1 & " -->" & Request.Form("comment") & "<!-- End Comment " & iLinkCount + 1 & " -->"
		
			' Tack on the rest of the html from the page
			strOutput = strOutput & GetFooterHTML(strInput, iLinkCount)
		
			' Save the new links file to the disk
			WriteLinksFileToDisk(strOutput)				

			' Show options for navigation
			Response.Write Request.Form("link") & " <B>has been added!</B><BR>" & vbCrLf
			Response.Write "<BR>" & vbCrLf
		Else
			%>
			<FORM ACTION="./links.asp?action=add" METHOD="post">
				<INPUT TYPE="hidden" NAME="write" VALUE="yes"></INPUT>
				<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0>
					<TR><TD ALIGN="right"><B>Link:</B> </TD><TD><INPUT TYPE="text" SIZE=80 NAME="link" VALUE="<%= Server.HTMLEncode("<A HREF=""http://www.asp101.com""><IMG SRC=""./images/asp101-100x30.gif"" BORDER=""0""></A>") %>"></INPUT></TD></TR>
					<TR><TD ALIGN="right"><B>Comment:</B> </TD><TD><INPUT TYPE="text" SIZE=80 NAME="comment" VALUE="The best Active Server Pages site on the net!"></INPUT></TD></TR>
				</TABLE>	
				<INPUT TYPE="reset" VALUE="Reset Form">
				<INPUT TYPE="submit" VALUE="Add Link">
			</FORM>
			<%
		End If

		' Show options for navigation
		Response.Write "<A HREF=""./links.asp"">Back to Link List</A><BR>" & vbCrLf
	Case "edit" ' Edit an existing link
		' If we've already made changes then write links to links file o/w show change form
		If Request.Form("write") = "yes" Then
			' Start the new string with the everthing up to the first link
			strOutput = GetHeaderHTML(strInput)
		
			' Loop through existing links placing them into the new string except for link to be changed
			For I = 1 to iLinkCount
				If I = iLink Then
					strOutput = strOutput & "<!-- Begin Link " & I & " -->" & Request.Form("link") & "<!-- End Link " & I & " -->"
					strOutput = strOutput & GetHTMLBetweenLinkAndComment(I, strInput)
					strOutput = strOutput & "<!-- Begin Comment " & I & " -->" & Request.Form("comment") & "<!-- End Comment " & I & " -->"
				Else
					strOutput = strOutput & "<!-- Begin Link " & I & " -->" & GetLinkHTML(I, strInput) & "<!-- End Link " & I & " -->"
					strOutput = strOutput & GetHTMLBetweenLinkAndComment(I, strInput)
					strOutput = strOutput & "<!-- Begin Comment " & I & " -->" & GetCommentHTML(I, strInput) & "<!-- End Comment " & I & " -->"
				End If
				If I <> iLinkCount Then
					strOutput = strOutput & GetHTMLBetweenLinks(I, strInput)
				End If
			Next 'I
		
			' Tack on the rest of the html from the page
			strOutput = strOutput & GetFooterHTML(strInput, iLinkCount)
		
			' Save the new links file to the disk
			WriteLinksFileToDisk(strOutput)				

			Response.Write Request.Form("link") & " <B>has been updated!</B><BR>" & vbCrLf
			Response.Write "<BR>" & vbCrLf
		Else
			%>
			<FORM ACTION="./links.asp?action=edit&link=<%= iLink %>" METHOD="post">
				<INPUT TYPE="hidden" NAME="write" VALUE="yes"></INPUT>
				<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0>
					<TR><TD ALIGN="right"><B>Link:</B> </TD><TD><INPUT TYPE="text" SIZE=80 NAME="link" VALUE="<%= Server.HTMLEncode(GetLinkHTML(iLink, strInput)) %>"></INPUT></TD></TR>
					<TR><TD ALIGN="right"><B>Comment:</B> </TD><TD><INPUT TYPE="text" SIZE=80 NAME="comment" VALUE="<%= Server.HTMLEncode(GetCommentHTML(iLink, strInput)) %>"></INPUT></TD></TR>
				</TABLE>	
				<INPUT TYPE="reset" VALUE="Reset Form">
				<INPUT TYPE="submit" VALUE="Update Link">
			</FORM>
			<%
		End If
		
		' Show options for navigation
		Response.Write "<A HREF=""./links.asp"">Back to Link List</A><BR>" & vbCrLf
	Case "delete" ' Delete an existing link
		If iLinkCount <= 2 Then
			%>
			Currently you are not allowed to remove the last two links!<BR>
			<BR>
			Since this script was designed to work with any HTML formatting, if the number
			of links falls below two, the script would have no way of figuring out what
			type of separator goes between links; therefore, we can't let you delete the
			last two.  If you want to remove one of them, please add another link first!<BR>
			<BR>
			Okay, so you caught us in a bug!  Just think of it as an added feature!
			(The script maintains a minimum number of links so your site never looks lame!)<BR>
			<BR>
			Here's our 3 part rationalization:<BR>
			<OL>
			<LI>If you have two or fewer links, do you really need this script!
			<LI>For those of you trying to delete all the links and re-enter them to avoid doing the sort routine, shame on you, it really isn't that hard!
			<LI>It's a reasonable price to play for the added flexability of allowing you guys to format your own link pages and still being able to use the script to update them!
			</OL>
			<BR>
			<%
		Else
			' Start the new string with the everthing up to the first link
			strOutput = GetHeaderHTML(strInput)
		
			' Loop through existing links placing them into the new string except for link to be deleted
			' Note we also need to decrement the ids of the links after the one removed!
			For I = 1 to iLink - 1
				strOutput = strOutput & "<!-- Begin Link " & I & " -->" & GetLinkHTML(I, strInput) & "<!-- End Link " & I & " -->"
				strOutput = strOutput & GetHTMLBetweenLinkAndComment(I, strInput)
				strOutput = strOutput & "<!-- Begin Comment " & I & " -->" & GetCommentHTML(I, strInput) & "<!-- End Comment " & I & " -->"
				If I <> iLinkCount - 1 Then
					strOutput = strOutput & GetHTMLBetweenLinks(I, strInput)
				End If
			Next 'I
			' Notice we never hit iLink!
			For I = iLink + 1 to iLinkCount
				strOutput = strOutput & "<!-- Begin Link " & I - 1 & " -->" & GetLinkHTML(I, strInput) & "<!-- End Link " & I - 1 & " -->"
				strOutput = strOutput & GetHTMLBetweenLinkAndComment(I, strInput)
				strOutput = strOutput & "<!-- Begin Comment " & I - 1 & " -->" & GetCommentHTML(I, strInput) & "<!-- End Comment " & I - 1 & " -->"
				If I <> iLinkCount Then
					strOutput = strOutput & GetHTMLBetweenLinks(I, strInput)
				End If
			Next 'I
		
			' Tack on the rest of the html from the page
			strOutput = strOutput & GetFooterHTML(strInput, iLinkCount)
		
			' Save the new links file to the disk
			WriteLinksFileToDisk(strOutput)				

			' Show options for navigation
			Response.Write GetLinkHTML(iLink, strInput) & " <B>has been deleted!</B><BR>" & vbCrLf
			Response.Write "<BR>" & vbCrLf
		End If
		
		Response.Write "<A HREF=""./links.asp"">Back to Link List</A><BR>" & vbCrLf
	Case "sort" ' Sort existing links (Yeah Right!?!)
		' We'll leave this for those experts out ther who feel like tackling it!
		' Hint: add a column to the view table where the user can enter
		' integers representing the order they want to put them in.  You could
		' also just force an alphabetic sort if you exclusively use text links,
		' but like I said... this one is for ya'll to solve!  (We're just too
		' tired of looking at this code at this point!)
	Case Else ' Default - "view"
		%>
		<TABLE BORDER=0>
		<TR>
		<TD><TABLE BORDER=1>
			<TR>
			<TD><B>Current Links</B></TD>
			<TD><B>Edit</B></TD>
			<TD><B>Delete</B></TD>
			</TR>
		<% For I = 1 to iLinkCount %>
			<TR>
			<TD><%= GetLinkHTML(I, strInput) %></TD>
			<TD><A HREF="./links.asp?action=edit&link=<%= I %>">Edit This Link</A></TD>
			<TD><A HREF="./links.asp?action=delete&link=<%= I %>">Delete This Link</A></TD>
			</TR>
		<% Next 'I %>
			</TABLE></TD>
		</TR>
		<TR>
		<TD ALIGN="right"><A HREF="./links.asp?action=add">Add a New Link</A></TD>
		</TR>
		</TABLE>
		<%
End Select
' For those of you who made it this far trying to    ____/_
' read the code, we salute you!  Just save it and    \--/-/    
' give it a try already!  It's relatively safe,       \o /
' just make sure you have a backup copy of your        \/
' links page before you try it!  It has been know      ||
' to eat them when they're not commented to its        ||
' liking!  Besides that it won't hurt anything!       ====
'
' Okay, so you try drawing a martini in ascii-art.  "Damn-it Jim! We're Scripters, Not Artists!"
%>
<A HREF="./links.htm">View the generated links page!</A>
</XMP></BODY></HTML>
