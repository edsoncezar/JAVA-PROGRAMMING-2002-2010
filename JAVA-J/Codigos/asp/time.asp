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

<% If Request.QueryString("time") = "" Then %>
You haven't clicked on the link below yet.<BR>
<% Else %>
You spent <%= DateDiff("s", Request.QueryString("time"), Now()) %>
seconds looking at the previous page.<BR>
<% End If %>
<BR>
<A HREF="time.asp?time=<%= Server.URLEncode(Now())%>">How long have I spent on this page?</A><BR>
<BR>
This script passes the time in a QueryString parameter. You could just as
easily store it in a session variable, log it to a database, or write it to
a text file.  It all depends upon what you intend to use the information
for.
