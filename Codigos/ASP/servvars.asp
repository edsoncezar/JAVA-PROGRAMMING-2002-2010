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
Dim Item
%>

<!-- Show greeting using selected server variables -->
Hello visitor from <%= Request.ServerVariables("REMOTE_ADDR") %>! Your
browser identifies itself as <%= Request.ServerVariables("HTTP_USER_AGENT") %>.

<BR>
<BR>

<!-- Show all server variables -->
<TABLE BORDER=2>
<TR>
	<TD><B>Server Variable</B></TD>
	<TD><B>Value</B></TD>
</TR>
<% For Each Item In Request.ServerVariables %> 
<TR>
	<TD><FONT SIZE="-1"><%= Item %></FONT></TD>
	<TD><FONT SIZE="-1"><%= Request.ServerVariables(Item) %>&nbsp;</FONT></TD>
</TR>
<% Next %>
</TABLE>

<% ' That's It!  Another cool time when a few lines of code go a long way! %>
