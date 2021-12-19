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
Dim I ' declare our loop index variable
%>

<%
' Start the loop telling it to loop from 1 to 5 incrementing
' I by one (which is the default) on each pass.
'
' ie. this would have done the same thing:
' For I = 1 To 5

For I = 1 To 5 Step 1
	' Output our HTML and text using the value of I as
	' the FONT TAG's SIZE attribute.
	%>
	<FONT SIZE="<%= I %>">Hello World</FONT><BR>
	<%
Next ' Bounce back to the top of the loop
%>
