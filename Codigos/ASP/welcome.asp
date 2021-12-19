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
Dim dHour
dHour = Hour(Now)

If dHour < 12 Then
	Response.Write "Good morning!"
ElseIf dHour < 17 Then
	Response.Write "Good afternoon!"
Else
	Response.Write "Good evening!"
End If
%>
We hope you are enjoying our sample code.<BR>
<BR>
If you are curious it is currently <%= Time() %> on <%= Date() %>.<BR>
