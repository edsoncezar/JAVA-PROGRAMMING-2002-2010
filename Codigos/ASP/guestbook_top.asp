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
' This is used to control whether or not we empty the guestbook at
' the first hit after midnight.  We do it just to keep the list
' short.  You'll probably want to set this to False
Const bDeleteEntries = True

' Allows us to easily clear the guestbook if we notice someone is
' getting rude!  All you need to do is pass it ?force=anything
' Possibly something else you might not want. To disable it comment
' out the Request.QueryString line and uncomment the "" one.
Dim bForce
bForce = Request.QueryString("force")
'bForce = ""

' Now that we're done implementing features you probably won't want,
' let's get to the actual script...

Dim objFSO    ' FileSystemObject Variable
Dim objFile   ' File Object Variable
Dim strFile   ' String variable to store the path / file we write to
Dim strBuffer ' Temporary area to swap order of the guestbook entries

strFile = Server.MapPath("guestbook.txt")

' If the script doesn't have anything posted to it we display the form
' otherwise we process the input and append it to the guestbook file.
If Request.Form("name") = "" Or Request.Form("comment") = "" Then
	' Display the entry form.
	%>
	<h3>Sign Our Guestbook:</h3>
	<form action="guestbook_top.asp" method="post">
	<table>
		<tr>
			<th align="right">Name:</td>
			<td><input type="text" name="name" size="15"></input></td>
		</tr>
		<tr>
			<th align="right">Comment:</td>
			<td><input type="text" name="comment" size="35"></input></td>
		</tr>
	</table>
	<input type="submit" value="Sign Guestbook!"></input>
	</form>

	<br />

	<h3>Today's Comments:</h3>
	<%
	' We can no longer just use an include line since we need to
	' process the file on the way in.

	' Create an instance of the FileSystemObject
	Set objFSO = Server.CreateObject("Scripting.FileSystemObject")
	' Open the TextFile (FileName, ForReading, AllowCreation)
	Set objFile = objFSO.OpenTextFile(strFile, 1, False)

	' Loop through putting eack line ahead of the previous ones
	Do While Not objFile.AtEndOfStream
		strBuffer = objFile.ReadLine & vbCrLf & strBuffer
	Loop

	Set objFile = Nothing
	Set objFSO = Nothing

	' Spit out the result
	Response.Write strBuffer
Else
	' Log the entry to the guestbook file
	
	' Create an instance of the FileSystemObject
	Set objFSO = Server.CreateObject("Scripting.FileSystemObject")
	' Open the TextFile (FileName, ForAppending, AllowCreation)
	Set objFile = objFSO.OpenTextFile(strFile, 8, True)

	' Log the results
	' I simply bold the name and do a <br />.
	' You can make it look however you'd like.
	' Once again I remind readers that we by no means claim to
	' be UI experts.  Although one person did ask us if we had a
	' graphic designer!  I laughed so hard that I almost hurt myself!
	objFile.Write "<strong>"
	objFile.Write Server.HTMLEncode(Request.Form("name"))
	objFile.Write ":</strong> "
	objFile.Write Server.HTMLEncode(Request.Form("comment"))
	objFile.Write "<br />"
	objFile.WriteLine ""
	
	' Close the file and dispose of our objects
	objFile.Close
	Set objFile = Nothing
	Set objFSO = Nothing

	' Tell people we've written their info
	%>
	<h3>Your comments have been written to the file!</h3>
	<a href="guestbook_top.asp">Back to the guestbook</a>
	<%
End If

' We do this to delete the file every day to keep it managable.
' If you were doing this for real you probably wouldn't want to
' do this so we have defined a const named bDeleteEntries at the
' top of the script that you can set to False to prevent this
' section from running.  You could also delete this whole
' If Then....End If block if you'd like.  Just be sure to leave
' the script delimiter at the bottom!
If bDeleteEntries Then
	Set objFSO = Server.CreateObject("Scripting.FileSystemObject")
	Set objFile = objFSO.GetFile(strFile)
	If DateDiff("d", objFile.DateLastModified, Date()) <> 0 Or bForce <> "" Then
		Set objFile = Nothing		
		' Can't use delete because we need the file to exist for
		' the include the next time the script is run!
		'objFile.Delete
		
		' Create a file overwriting old one.
		Set objFile = objFSO.CreateTextFile(strFile, True)

		' The include barks if the file's empty!
		objFile.Write "<strong>John:</strong> "
		objFile.WriteLine "I hope you like our guestbook!<br />"
		objFile.Close
	End If
	Set objFile = Nothing
	Set objFSO = Nothing
End If
%>
