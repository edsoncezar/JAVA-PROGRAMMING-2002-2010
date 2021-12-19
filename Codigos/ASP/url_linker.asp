<%
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
'***** BEGIN FUNCTIONS *****
' This function takes a string as input and links any http's it
' finds so that they are then clickable in a browser.  If only
' looks for http:// so www.asp101.com alone wouldn't link, but
' http://www.asp101.com would.
Function LinkURLs(strInput)
	Dim iCurrentLocation  ' Our current position in the input string
	Dim iLinkStart        ' Beginning position of the current link
	Dim iLinkEnd          ' Ending position of the current link
	Dim strLinkText       ' Text we're converting to a link
	Dim strOutput         ' Return string with links in it

	' Start at the first character in the string
	iCurrentLocation = 1

	' Look for http:// in the text from the current position to
	' the end of the string.  If we find it then we start the
	' linking process otherwise we're done because there are no
	' more http://'s in the string.
	Do While InStr(iCurrentLocation, strInput, "http://", 1) <> 0
		' Set the position of the beginning of the link
		iLinkStart = InStr(iCurrentLocation, strInput, "http://", 1)
		
		' Set the position of the end of the link.  I use the
		' first space as the determining factor.
		iLinkEnd = InStr(iLinkStart, strInput, " ", 1)
		
		' If we didn't find a space then we link to the
		' end of the string
		If iLinkEnd = 0 Then iLinkEnd = Len(strInput) + 1

		' Take care of any punctuation we picked up
		Select Case Mid(strInput, iLinkEnd - 1, 1)
			Case ".", "!", "?"
				iLinkEnd = iLinkEnd - 1
		End Select
		
		' This adds to the output string all the non linked stuff
		' up to the link we're curently processing.
		strOutput = strOutput & Mid(strInput, iCurrentLocation, iLinkStart - iCurrentLocation)

		' Get the text we're linking and store it in a variable
		strLinkText = Mid(strInput, iLinkStart, iLinkEnd - iLinkStart)
		
		' Build our link and append it to the output string
		strOutput = strOutput & "<A HREF=""" & strLinkText & """>" & strLinkText & "</A>"

		' Some good old debugging
		'Response.Write iLinkStart & "," & iLinkEnd & "<BR>" & vbCrLf
		
		' Reset our current location to the end of that link
		iCurrentLocation = iLinkEnd
	Loop
	
	' Tack on the end of the string.  I need to do this so we
	' don't miss any trailing non-linked text
	strOutput = strOutput & Mid(strInput, iCurrentLocation)
	
	' Set the return value
	LinkURLs = strOutput
End Function 'LinkURLs
'***** END FUNCTIONS *****


Dim strUnlinked ' The variable to hold out text to be linked up.

' Get the input string from wherever...
' It probably makes the most sense when this is read in from a
' DB or text file.  For illustration I'm setting it to this as
' a little plug for our partners!
strUnlinked = "http://www.asp101.com is the best ASP site! <br />" & vbCrLf
strUnlinked = strUnlinked & "You can get good XML content from http://www.xml101.com. <br />" & vbCrLf
strUnlinked = strUnlinked & "Microsoft http://www.microsoft.com/ always has lots of good info too. <br />" & vbCrLf


' Show title for modified string
Response.Write "<B>Original Text:</B><BR>" & vbCrLf

' Show the original string for comparison:
Response.Write strUnlinked


' Spacing!
Response.Write vbCrLf & "<BR>" & vbCrLf & vbCrLf


' Show title for modified string
Response.Write "<B>Text After Linking:</B><BR>" & vbCrLf

' Call our function and write out the results:
Response.Write LinkURLs(strUnlinked)
' That really is all there is to it folks!
%>
