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
Dim sSomeText
sSomeText = "This is some text"
' OK, here's the scenario:
' We're happily scripting along and all of a sudden we need to
' output the value of the variable sSomeText inside a bold tag.
' This naturally means we need to output some plain HTML for the
' beginning and end <B> tags.

' Output title - this really isn't part of the sample, but we need to label things!
Response.Write "Method 1 - Break Script:<BR>" & vbCrLf

' *** Method 1 - Break Script ***
'  We stop our scripting, drop out to the HTML that will be sent
'  directly to the browser and then just type our HTML tags as we
'  normally would in a plain HTML page.  This is actually done
'  twice in this instance.  Once for the <B> and once for the </B>.
%><B><%
Response.Write sSomeText
%></B><%

' Note: in our samples we would normally format it more like this:
'
' % >
' <B>< % Response.Write sSomeText % ></B>
' < %
'
' the two are functionally equivalent, the only difference is the
' placement of the carriage returns.  The first is shown to more
' clearly illustrate where we stop and resume the script each time.
'
' The main benefit of this method is it often allows you to more
' easily distinguish between script and HTML than the upcoming
' method 2.  We use it most often when we have a large amount of
' HTML into which we need to place a value or two.  For example, if
' you've got a table which has a lot of formatting it's easier to
' code your table as normal and simply start script, display the
' value, and end script in the table cell where the value goes.
' The < %=  % > format of the Response.Write command is very handy
' in this type of scenario!
' *** End Method 1 ***


' Output some line feeds so the next sample appears on a new line:
'  Speaking of which, you'll notice we use both the HTML tag <BR>
'  and a vbCrLF which is a Visual Basic constant for the 
'  Carriage Return - Line Feed character.  Here's why!
'  Since HTML ignores spacing and carriage returns, we put in the
'  <BR> so our next output doesn't appear on the same line as the
'  previous one.  Since we're inside script, the standard carriage
'  returns aren't shown in the resulting HTML (which would then
'  ignore them); however, since our site is designed for our
'  visitors to read our code, to keep our HTML code neat for our
'  readers, we add the vbCrLf.  The result of the HTML when viewed
'  in a browser is the same either way.
' Note: the first <BR> ends the previous line.
'       the second <BR> skips a line.
Response.Write "<BR>" & vbCrLf & "<BR>" & vbCrLf


' Output title - Once again, not really part of the sample.  (Ignore the man behind the curtain!)
Response.Write "Method 2 - Response.Write:<BR>" & vbCrLf

' *** Method 2 - Response.Write ***
'  In this method we never drop out of our script.  We accomplish
'  this by simply outputting the HTML as Strings along with the
'  value of our variable.
Response.Write "<B>"
Response.Write sSomeText
Response.Write "</B><BR>"

' Note: we would normally have it all on one line like this:
'
' Response.Write "<B>" & sSomeText & "</B><BR>"
'
' Once again, the two are functionally equivalent, the only
' difference is the placement of the carriage returns.  The first is
' shown to more clearly illustrate the process.

' The main benefit of this method is it lets you output a little
' HTML without breaking stride.  We use it most often when we have a
' situation like this example.  We've got some a variable that we
' just want to throw a quick tag around.  It is also reportedly
' quicker, in terms of server processing time, since the ASP
' processor doesn't need to stop parsing and then restart when we
' start scripting again.  (We've yet to be able to actually notice
' a perceivable difference, but that's what we hear!)
'
' There is also one caveat when using this method:
'   BE CAREFUL USING THE " CHARACTER
' In VBScript the " is the string delimiter, hence a command like
' the following will cause problems:
'     Response.Write "<A HREF="./output.asp">Output</A>"
' This WILL NOT result in <A HREF="./output.asp">Output</A> being
' sent to the browser.  What it will give you is an error because
' it thinks the string ended at the " after the = sign and it doesn't
' recognize the rest of the text as a valid VBScript command.
' The syntax to achieve the desired result is:
'     Response.Write "<A HREF=""./output.asp"">Output</A>"
' Notice the "" instead of " wherever we want a " in the string!
' *** End Method 2 ***
%>
