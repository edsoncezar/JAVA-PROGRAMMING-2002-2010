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
'************************************************************
' This function takes a string and converts to Proper Case.
' Prototyped by: Brian Shamblen on 3/18/99
' This version by: Us... naturally!
'************************************************************
Function PCase(strInput)
	Dim iPosition  ' Our current position in the string (First character = 1)
	Dim iSpace     ' The position of the next space after our iPosition
	Dim strOutput  ' Our temporary string used to build the function's output

	' Set our position variable to the start of the string.
	iPosition = 1
	
	' We loop through the string checking for spaces.
	' If there are unhandled spaces left, we handle them...
	Do While InStr(iPosition, strInput, " ", 1) <> 0
		' To begin with, we find the position of the offending space.
		iSpace = InStr(iPosition, strInput, " ", 1)
		
		' We uppercase (and append to our output) the first character after
		' the space which was handled by the previous run through the loop.
		strOutput = strOutput & UCase(Mid(strInput, iPosition, 1))
		
		' We lowercase (and append to our output) the rest of the string
		' up to and including the current space.
		strOutput = strOutput & LCase(Mid(strInput, iPosition + 1, iSpace - iPosition))

		' Note:
		' The above line is something you may wish to change to not convert
		' everything to lowercase.  Currently things like "McCarthy" end up
		' as "Mccarthy", but if you do change it, it won't fix things like
		' ALL CAPS.  I don't see an easy compromise so I simply did it the
		' way I'd expect it to work and the way the VB command
		' StrConv(string, vbProperCase) works.  Any other functionality is
		' left "as an exercise for the reader!"
		
		' Set our location to start looking for spaces to the
		' position immediately after the last space.
		iPosition = iSpace + 1
	Loop

	' Because we loop until there are no more spaces, it leaves us
	' with the last word uncapitalized so we handle that here.
	' This also takes care of capitalizing single word strings.
	' It's the same as the two lines inside the loop except the
	' second line LCase's to the end and not to the next space.
	strOutput = strOutput & UCase(Mid(strInput, iPosition, 1))
	strOutput = strOutput & LCase(Mid(strInput, iPosition + 1))

	' That's it - Set our return value and exit
	PCase = strOutput
End Function
%>


<%' Run-time Code
Dim strTemp ' The string we play with

' Get the string from the form
strTemp = CStr(Request.Form("inputstring"))

' If it's empty, use our default
If strTemp = "" Then strTemp = "The quick brown fox jumps over the lazy dog."

' Output table of original, LCase, UCase, and PCase'd strings
' then show form if they want to try it on their own string.
%>
<TABLE BORDER="1" CELLPADDING="4" CELLSPACING="2">
	<TR>
		<TD ALIGN="right"><B>Original String:</B></TD>
		<TD><%= strTemp %></TD>
	</TR>
	<TR>
		<TD ALIGN="right"><B>LCase (VBScript) String:</B></TD>
		<TD><%= LCase(strTemp) %></TD>
	</TR>
	<TR>
		<TD ALIGN="right"><B>UCase (VBScript) String:</B></TD>
		<TD><%= UCase(strTemp) %></TD>
	</TR>
	<TR>
		<TD ALIGN="right"><B>PCase (ASP 101) String:</B></TD>
		<TD><%= PCase(strTemp) %></TD>
	</TR>
</TABLE>

<FORM ACTION="<%= Request.ServerVariables("SCRIPT_NAME") %>" METHOD="post">
	Try it out on your input:<BR>
	<INPUT TYPE="text" NAME="inputstring" SIZE="50"></INPUT><BR>
	<INPUT TYPE="submit"></INPUT><BR>
</FORM>
