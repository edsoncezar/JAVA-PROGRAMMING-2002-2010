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
Dim objFSO
Dim objFile
Dim dateModified

' Creates the object and assigns it to our variable.
Set objFSO = Server.CreateObject("Scripting.FileSystemObject")

' Get access to the file
Set objFile = objFSO.GetFile(Server.MapPath("modified.asp"))

' Get last modified property
dateModified = objFile.DateLastModified

' You can format it as you like using the FormatDateTime command
%>
This file was modified on the date and time below:<BR>
&nbsp;&nbsp;You can write out the date as is...<BR>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= dateModified %>;<BR>
&nbsp;&nbsp;or format it as you like!<BR>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= FormatDateTime(dateModified, 1) %>; at <%= FormatDateTime(dateModified, 3) %><BR>
<%
' Kill our objects
Set objFile = Nothing
Set objFSO = Nothing
%>
