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
' Error handling in VBScript is relatively primitive.
' There are basically just two options:
'
' On Error Goto 0 (the default)
'   This causes execution to break when an error happens and
'   show an error message.
' On Error Resume Next
'   This causes the script to continue execution starting with
'   the next line after the error.
'
' By itself this can hardly be called error handling, but VBScript
' has a built in Err object which helps some.  When "On Error
' Resume Next" is enabled, the properties of the Err object are
' automatically populated with details of the errors that have
' occurred.

' Tell our script to continue executing even if an error occurs:
On Error Resume Next

' Some code that is intentionally wrong:
Dim cnnTest
Set cnnTest = Server.CreateObject("ADODB.ThisObjectDoesntExist")
Set cnnTest = Nothing
%>

<p>
Error details:
</p>
<table border="1">
	<tr>
		<td>Err.Source</td>
		<td><%= Err.Source %></td>
	</tr>
	<tr>
		<td>Err.Number</td>
		<td><%= Err.Number %></td>
	</tr>
	<tr>
		<td>Err.Description</td>
		<td><%= Err.Description %></td>
	</tr>
</table>

<%
' You can clear the error information in the Err object by calling
' the clear method:
Err.Clear

' You can also raise an error of your own if you need to:
Err.Raise 1, "ASP 101", "This is a custom error that I raised!"
%>

<p>
Error details:
</p>
<table border="1">
	<tr>
		<td>Err.Source</td>
		<td><%= Err.Source %></td>
	</tr>
	<tr>
		<td>Err.Number</td>
		<td><%= Err.Number %></td>
	</tr>
	<tr>
		<td>Err.Description</td>
		<td><%= Err.Description %></td>
	</tr>
</table>

<%
' Turn "error-handling" back off... after the next line the
' script will break as normal if any errors happen.
On Error Goto 0


' Note that what we've just done is pretty pointless.  If we
' wanted our users to see the error details then we just wouldn't
' handle the errors at all.  But in the real world you probably
' wouldn't just print them out.  You can then log these to a file,
' send them to someone via email, or do whatever else you might
' want to do with them.  I tend to display a generic message
' something like "We're sorry, but there has been an error
' processing your request.  The webmaster has been notified and
' will look into the problem as soon as possible.  Please try your
' request later." to the end user and then send an email to
' myself containing the script name and the error details so I can
' look into the problem.

'=================================================================
' Starting with ASP 3.0 there is a another method for handling
' run-time errors: the ASPError object.  It's used to centrally
' handle errors for an entire site by setting up a custom error
' page.  You can find more information about this type of error
' handling here:
'
' http://www.asp101.com/articles/carvin/errlog/default.asp
' http://www.asp101.com/articles/stuart/asperrormsgs/default.asp
'=================================================================
%>
