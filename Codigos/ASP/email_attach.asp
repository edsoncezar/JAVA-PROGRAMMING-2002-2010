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
' Note that I'm using the Win2000 CDO syntax!  I'm
' assuming most people are off of NT4 by now.

' For simplicity and because I'm not letting you enter
' them, I'm not validating email addresses. If you let
' people enter them you should at least do some
' rudimentary level of validation to ensure entries
' are valid.

Dim objMessage

Set objMessage = Server.CreateObject("CDO.Message")

With objMessage
	' Set message attributes
	.To       = "Full Name <user name@some domain.com>"
	.From     = "Full Name <user name@some domain.com>"
	.Subject  = "Email Attachment Sample"
	.TextBody = "This email should have an attachment attached!"

	' Attachment using known static physical path
	'.AddAttachment "c:\somepath\somefile.txt"

	' Attachment using mappath to find the physical path
	.AddAttachment Server.MapPath("images/asp101-100x30.gif")

	' Attachment added directly from a URL
	.AddAttachment "http://www.asp101.com/samples/images/asp101-100x30.gif"
	
	' Send message - uncomment the following line only
	' AFTER you've entered appropriate To and From
	' addresses above.  Then the script will actually
	' send the messages.
	'.Send
End With

Set objMessage = Nothing
%>

Messages are <strong>not</strong> being sent... check the code for comments.

