<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0059)http://www.asp101.com/samples/download.asp?file=polls%2Easp -->
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META content="MSHTML 5.50.4134.100" name=GENERATOR></HEAD>
<BODY><XMP><%
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
' ADO VBScript constants file
' You should have a copy of this already, you just need to find it
' and adjust the include command appropriately!
' If you can't you can get a copy of this one from:
' http://www.asp101.com/samples/download/adovbs.inc
%>
<!-- #INCLUDE FILE="./download/adovbs.inc" -->

<%
' You'll notice I'm using an alternate way of creating a DB object
' as compared to my usual Dim / Create method.  I'm not overly fond
' of this manner, but there are some advantages to it so I thought I'd
' introduce those of you who haven't seen it before to this method.
%>

<OBJECT RUNAT="server" PROGID="ADODB.Connection" id="cnnPoll"> </OBJECT>
<OBJECT RUNAT="server" PROGID="ADODB.Recordset" id="rsPoll"> </OBJECT>

<%
Dim SCRIPT_NAME   ' Script name so we can make it location independent
SCRIPT_NAME = Request.ServerVariables("SCRIPT_NAME")

Dim strAction     ' Variable to determine what we need to do
Dim iPoll         ' Poll ID number to perform current action on

Dim iVote         ' In the case that a vote is being cast this is the vote

Dim iTotalVotes   ' Total votes in DB - used to figure out %s on results page

Dim strSQL        ' Our SQL string for getting to the DB
Dim I             ' A standard looping variable



' Get action choice and format it for easy comparison
strAction = LCase(CStr(Request.QueryString("action")))

' Set to default of showing the question unless results is passed in
' indicating I should show the results.  This way I don't have to
' worry about it since strAction is always question or results.
If strAction <> "results" Then strAction = "question"


' Get poll id
iPoll = Request.QueryString("pid")

' Validate and refine iPoll
If IsNumeric(iPoll) Then 
	If iPoll > 0 Then
		iPoll = CInt(iPoll)
	Else
		'strAction = "Error: Invalid Poll Id!"
		
		' I commented out the above and let this slide so you can
		' just request poll.asp and get back something.
		iPoll = 1
	End If
Else
	' If it's not numeric I should just error out which I do
	' but since this script will handle multiple polls I've
	' set it up to take "all" as a parameter when displaying
	' results so you can get to see the whole set of results
	' if you want to.
	If LCase(iPoll) = "all" And strAction = "results" Then
		iPoll = "all"
	Else
		strAction = "Error: Invalid Poll Id!"
	End If
End If


' Open our DB connection since every choice hits the DB for something

' This ODBC connection works fine... used until Nov 8, 99
'cnnPoll.Open "DBQ=" & SERVER.MapPath("polls.mdb") & ";DRIVER={Microsoft Access Driver (*.mdb)};DriverId=25;MaxBufferSize=8192;Threads=20;"

' Testing OLE DB connection started using it Nov 8, 99
cnnPoll.Open "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" & SERVER.MapPath("polls.mdb") & ";"

' Set the default for our SQL string.
' This is used all the time unless the whole "all" thing comes into play
strSQL = "SELECT * FROM polls WHERE id=" & iPoll & ";"

' Do whatever action is appropriate "question" or "results"
' Otherwise error out on the else option.
Select Case strAction
	Case "question"
		' Open our RS to show the choices
		rsPoll.Open strSQL, cnnPoll, adOpenStatic, adLockReadOnly, adCmdText

		If Not rsPoll.EOF Then
			' Show the voting form
			' You'll need to format this to your liking
			%>
			<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0">
				<TR>
					<TD BGCOLOR="#006600" ALIGN="left"><FONT COLOR="#FFFFFF"><B>Quick Question:</B></FONT></TD>
					<TD BGCOLOR="#006600">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
					<TD BGCOLOR="#006600" ALIGN="right"><A HREF="<%= SCRIPT_NAME %>?action=results&pid=<%= iPoll %>"><FONT COLOR="#FFFFFF" SIZE="-1">View Results</FONT></A></TD>
				</TR>
				<TR>
					<TD COLSPAN="3" BGCOLOR="#009933" ALIGN="center"><FONT COLOR="#FFFFFF"><%= rsPoll.Fields("question").Value %></FONT></TD>
				</TR>
				<TR>
					<TD COLSPAN="3" BGCOLOR="#009933" ALIGN="center">
						<%
						' Loop 1 to 5 since there are only 5 possible choices set up in the DB
						For I = 1 to 5
							If Not IsNull(rsPoll.Fields("choice" & I).Value) Then
								' Some spacing if needed
								If I <> 1 Then Response.Write "&nbsp;&nbsp;"
								' Show choices hyperlinked to the vote portion of the script.
								%>
								<A HREF="<%= SCRIPT_NAME %>?action=results&pid=<%= iPoll %>&vote=<%= I %>"><FONT COLOR="#FFFFFF"><%= rsPoll.Fields("choice" & I).Value %></A></FONT>
								<%
							End If
						Next 'I
						%>
					</TD>
				</TR>
			</TABLE>
			<%
		Else
			Response.Write "Error: Invalid Poll Id!"
		End If
		
		rsPoll.Close

	Case "results"

		' If we're processing a vote then we need to know what it is so:
		' Get The Vote!
		iVote = Request.QueryString("vote")

		' Validate and refine iVote.  Setting to 0 if invalid!
		If IsNumeric(iVote) Then
			iVote = CInt(iVote)
							
			If Not(1 <= iVote And iVote <= 5) Then
				iVote = 0
			End If
		Else
			iVote = 0
		End If

		' If iVote = 0 or iPoll = "all" then I'm just showing the results
		' Otherwise we need to log them first
		If iVote <> 0 And iPoll <> "all" Then
			' Log results
		
			' Open our RS to record the choice
			' Notice that it's not static or read only.
			rsPoll.Open strSQL, cnnPoll, adOpenKeyset, adLockPessimistic, adCmdText

			If Not rsPoll.EOF Then
				' Check to be sure they haven't already voted in this session
				' This prevents the refresh button from resubmitting the info
				' You could make this a lot more sophisticated and useful if
				' you had some reason to.  I just don't really care and do it
				' mainly for the refresh button issue
				If Session("AlreadyVoted") <> 1 Then
					rsPoll.Fields("votes" & iVote).Value = rsPoll.Fields("votes" & iVote).Value + 1
					rsPoll.Update

					' Set Flag to prevent revoting
					Session("AlreadyVoted") = 1
				End If

			Else
				Response.Write "Error: Invalid Poll Id!"
			End If

			rsPoll.Close
		Else
			If iPoll = "all" Then
				' Override our standard SQL string to show all otherwise it'll work fine.
				strSQL = "SELECT * FROM polls ORDER BY id;"
			End If
		End If

		' I've already processed any entry we needed to and set up for all condition.
		' Time to show the results!

		' Open recordset to show results.
		rsPoll.Open strSQL, cnnPoll, adOpenKeyset, adLockPessimistic, adCmdText

		If Not rsPoll.EOF Then
			' For each poll show results.
			' Normally just one, but I built it so it'd work for "all" too.
			Do While Not rsPoll.EOF
				' Tally the total votes and store it
				iTotalVotes = rsPoll.Fields("votes1").Value + _
								rsPoll.Fields("votes2").Value + _
								rsPoll.Fields("votes3").Value + _
								rsPoll.Fields("votes4").Value + _
								rsPoll.Fields("votes5").Value

				' Show Results - Format to your liking!
				%>
				<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0">
					<TR>
						<TD BGCOLOR="#006600"><FONT COLOR="#FFFFFF"><B>Poll #<%= rsPoll.Fields("id").Value %> Results</B> (based on <%= iTotalVotes %> votes)</FONT></TD>
					</TR>
					<TR>
						<TD BGCOLOR="#009933" ALIGN="center"><FONT COLOR="#FFFFFF"><%= rsPoll.Fields("question").Value %></FONT></TD>
					</TR>
					<TR>
						<TD BGCOLOR="#009933" ALIGN="center">
							<%
							' Loop over choices
							For I = 1 to 5
								If Not IsNull(rsPoll.Fields("choice" & I).Value) Then
									' The math was giving me trouble when I divided 0 by 1 so I avoided the situation
									If rsPoll.Fields("votes" & I).Value = 0 Then
										%>
										<FONT COLOR="#FFFFFF"><%= rsPoll.Fields("choice" & I).Value %>:</FONT> <FONT COLOR="#000000"><B><%= FormatPercent(0, 1) %></B></FONT>&nbsp;&nbsp;
										<%
									Else
										%>
										<FONT COLOR="#FFFFFF"><%= rsPoll.Fields("choice" & I).Value %>:</FONT> <FONT COLOR="#000000"><B><%= FormatPercent((rsPoll.Fields("votes" & I).Value / iTotalVotes), 1) %></B></FONT>&nbsp;&nbsp;
										<%
									End If
								End If
							Next 'I
							%>
						</TD>
					</TR>
				</TABLE>
				
				<BR>
				<%
				rsPoll.MoveNext
			Loop
		Else
			Response.Write "Error: Invalid Poll Id!"
		End If

		rsPoll.Close

	Case Else ' "error"
		' OK so this is pretty lame error handling, but it
		' catches most stupid things and warns the user.
		Response.Write strAction
End Select

cnnPoll.Close

' I can't set the DB objects to nothing because I never created them.
' This syntax is just weird.
%>
</XMP></BODY></HTML>
