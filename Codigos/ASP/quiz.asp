<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0058)http://www.asp101.com/samples/download.asp?file=quiz%2Easp -->
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
' Takes a integer parameter and converts it to the appropriate letter
Function GetLetterFromAnswerNumber(iInput)
Dim strTemp
	Select Case iInput
		Case 0
			strTemp = "A"
		Case 1
			strTemp = "B"
		Case 2
			strTemp = "C"
		Case 3
			strTemp = "D"
		Case 4
			strTemp = "E"
		Case 5
			strTemp = "F"
	End Select
GetLetterFromAnswerNumber = strTemp
End Function

' To simplify and streamline the code I split this line into many parts,
' wrapped it into a function and commented it so you'd have a better
' chance of figuring out what I'm doing since I usually can't!
Function GetAnswerFromAnswerString(iQuestionNumber, strAnswers)
Dim strTemp
Dim iOffset
	' I use InStrRev since I want to retrieve the last entered value
	' in case they went back and changed their mind. 
	
	' Find the location of the question number we want to use
	iOffset = InStrRev(strAnswers, "|" & iQuestionNumber & "|", -1, 1)
	
	' Get our answer by using the offset we just found and then moving
	' right the length of the question indicator to arrive at the
	' appropriate letter
	strTemp = Mid(strAnswers, iOffset + Len("|" & iQuestionNumber & "|"), 1)

	' There's no way it should be anything else, but to be sure we
	' convert it to a string and make sure it's uppercase
GetAnswerFromAnswerString = UCase(CStr(strTemp))
End Function
%>

<%
' This code works with either a DB or hard coded in values.  I developed
' it using the DB connection and our sample DB, but switched to the hard
' coded to increase the speed for our site.  Using the DB allows for better
' data encapsulation and easier updates.  It all comes down to your specific
' needs so I gave you both options.

' Set this to True to use the Db.  False to use hard coded values
Const USE_DB_FOR_INFO = False

' These 2 things only apply if you use the DB
' If you're not then they're values are irrelevant
Dim DB_CONN_STRING
DB_CONN_STRING = "DBQ=" & Server.MapPath("quiz.mdb") & ";"
DB_CONN_STRING = DB_CONN_STRING & "Driver={Microsoft Access Driver (*.mdb)};"
DB_CONN_STRING = DB_CONN_STRING & "DriverId=25;FIL=MS Access;"
' Lets you run multiple quizzes from one DB by separating by QUIZ_ID!
Const QUIZ_ID = 1


' Now to all our other variables!
Dim cnnQuiz, rsQuiz     'DB objects if we use the DB for the info

Dim I                   'our standard (improperly named) looping variable
Dim iNumberOfQuestions  'the number of questions in the test
Dim iQuestionNumber     'the question we're currently on
Dim strQuestionText     'text of the question to be asked
Dim aAnswers            'array of choices for the question to be asked
                        'if we hard code, then I also use it for the
                        'correct answers when I go to grade the quiz
Dim strAnswers          'stores the question numbers and response choices
                        'seperated by |'s
Dim iScore              'so we know how well the user did
Dim bAbort              'added after I had finished to account for closed sessions
Dim strResults          'another late addition for the each question breakdown!

bAbort = False          'set it to false since we only want to abort in certain cases



' If this is the first call to the quiz then init everything
' o/w retrieve values we need.  We check by looking for the
' Question ID from the querystring.
If Request.QueryString("qid") = "" Then
	' Retrieve and Set the Quiz Info
	If USE_DB_FOR_INFO Then
		' Code to use DB!
		' Create DB connection and connect to the DB
		Set cnnQuiz = Server.CreateObject("ADODB.Connection")
		cnnQuiz.Open DB_CONN_STRING
		
		' Create RS and query DB for quiz info
		Set rsQuiz = Server.CreateObject("ADODB.Recordset")
		rsQuiz.Open "SELECT * FROM quizzes WHERE quiz_id=" & QUIZ_ID & ";", cnnQuiz
		
		' Set our session vars
		Session("QuizName") = CStr(rsQuiz.Fields("quiz_name").Value)
		Session("NumberOfQuestions") = CInt(rsQuiz.Fields("number_of_questions").Value)
		Session("PercentageToPass") = CInt(rsQuiz.Fields("percentage_to_pass").Value)
		
		' Close and dispose of our DB objects
		rsQuiz.Close
		Set rsQuiz = Nothing
		cnnQuiz.Close
		Set cnnQuiz = Nothing
	Else
		' If we're not going to the DB, hard code in values here!
		' BEGIN HARD CODE
		Session("QuizName") = "ASP 101 Quiz"
		Session("NumberOfQuestions") = 10
		Session("PercentageToPass") = 70
		' END HARD CODE
	End If

	' Set our question indicator to 1 and init our answer string
	iQuestionNumber = 1
	Session("AnswerString") = "|"
Else
	'Check to be sure we've still got a session!
	If Session("AnswerString") = "" Then
		Response.Write "I'm sorry, but you've taken too long.  You can start over by "
		Response.Write "clicking <A HREF=""" & Request.ServerVariables("URL") & """>here</A>."
		' I'd normally just do a response.end, but I can't because I'm inside of our
		' site template.  I need the script to complete so I've declared and set a Flag
		'Response.End
		bAbort = True
	End If
	
	' Get the number of the question we're processing
	iQuestionNumber = CInt(Request.QueryString("qid"))
		
	' Log selected answer to last question
	Session("AnswerString") = Session("AnswerString") & iQuestionNumber & "|" & _
		GetLetterFromAnswerNumber(CInt(Request.QueryString("sa"))) & "|"
	
	' Increment question identifier
	iQuestionNumber = iQuestionNumber + 1
End If

' If session has expired then skip all the code.
' Equivalently, only run all the code if it hasn't!
If Not bAbort Then

	' Set this to a local variable to avoid accessing the collection each time
	' This isn't required, but makes it easier for me to access and
	' supposedly speeds it up... I'm not sure how much, but it can't hurt!
	iNumberOfQuestions = Session("NumberOfQuestions")

	' Check to see it the quiz is over.  If so then show results, o/w
	' ask the next question
	If iQuestionNumber > iNumberOfQuestions Then
		' Process results and show end quiz status report
		
		' Done for the same reason as for iNumberOfQuestions a few lines above
		strAnswers = Session("AnswerString")
		
		' Useful for debugging
		'Response.Write strAnswers & "<BR>" & vbCrLf & "<BR>" & vbCrLf

		' Bug hunting once again... I didn't even come across any real bugs on this trip!
		' Could you imagine the ammo I'd take if it was real hunting I was doing!
		'For I = 1 to iNumberOfQuestions
		'	Response.Write GetAnswerFromAnswerString(I, strAnswers) & "<BR>" & vbCrLf
		'Next 'I

		' Retrieve Correct Answers and compare to the entered ones
		If USE_DB_FOR_INFO Then
			' Code to use DB!
			' Create DB connection and connect to the DB
			Set cnnQuiz = Server.CreateObject("ADODB.Connection")
			cnnQuiz.Open DB_CONN_STRING
				
			' Create RS and query DB for quiz info
			Set rsQuiz = Server.CreateObject("ADODB.Recordset")
			' Specify 3, 1 (Static, Read Only)
			rsQuiz.Open "SELECT * FROM questions WHERE quiz_id=" & QUIZ_ID & _
				" ORDER BY question_number;", cnnQuiz, 3, 1
				
			iScore = 0
			I = 1
			Do While Not rsQuiz.EOF
				If UCase(CStr(rsQuiz.Fields("correct_answer").Value)) = _
					GetAnswerFromAnswerString(I, strAnswers) Then
					
					iScore = iScore + 1
					' This and the Else could be used to output a
					' correctness status for each question
					' Also useful for bug hunting!
					'Response.Write "Right" & "<BR>" & vbCrLf
				Else
					'Response.Write "Wrong" & "<BR>" & vbCrLf
					strResults = strResults & I & ", "
				End If
				I = I + 1
				rsQuiz.MoveNext
			Loop

			' Close and dispose of our DB objects
			rsQuiz.Close
			Set rsQuiz = Nothing
			cnnQuiz.Close
			Set cnnQuiz = Nothing
		Else
			' If we're not going to the DB, hard code in answer values here!
			' BEGIN HARD CODE
			aAnswers = Array("A", "A", "A", "E", "D", "A", "E", "E", "A", "A")
			' END HARD CODE
			
			iScore = 0
			For I = 1 to iNumberOfQuestions
				If UCase(CStr(aAnswers(I - 1))) = _
					GetAnswerFromAnswerString(I, strAnswers) Then
					
					iScore = iScore + 1
					' This and the Else could be used to output a
					' correctness status for each question
					' Also useful for bug hunting!
					'Response.Write "Right" & "<BR>" & vbCrLf
				Else
					'Response.Write "Wrong" & "<BR>" & vbCrLf
					strResults = strResults & I & ", "
				End If
			Next 'I
		End If

		' Convert score to a percentage rounded to the whole number
		iScore = Round((iScore / iNumberOfQuestions) * 100)
		%>
		<FONT SIZE="+2"><B><%= Session("QuizName") %></B></FONT><BR>
		<BR>
		<%
		If iScore >= Session("PercentageToPass") Then 
			Response.Write "Congratulations!  You've passed the quiz with a score of "
			Response.Write iScore & "%.<BR>" & vbCrLf
		Else
			Response.Write "Sorry!  You needed to achieve a score of "
			Response.Write Session("PercentageToPass") & "% or higher to pass.  "
			Response.Write "Unfortunately, your score was only " & iScore & "%.  "
			Response.Write "You can take the test again by clicking <A HREF="""
			Response.Write Request.ServerVariables("URL") & """>here</A>.<BR>" & vbCrLf
		End If
		Response.Write "<BR>" & vbCrLf
		If Len(strResults) <> 0 Then
			Response.Write "You missed the following questions: " & Left(strResults, Len(strResults) - 2)
			Response.Write "<BR>" & vbCrLf
		End If
		'Response.Write iScore & "%"

		' This is also where you could log the results if you wanted to.
		' In it's simplest form, you would simply log strAnswers to a file,
		' but you could format little "report cards" or log the result to a
		' separate data source.
	Else
		' Retrieve and Set the Question Info
		If USE_DB_FOR_INFO Then
			' Code to use DB!
			' Create DB connection and connect to the DB
			Set cnnQuiz = Server.CreateObject("ADODB.Connection")
			cnnQuiz.Open DB_CONN_STRING
				
			' Create RS and query DB for quiz info
			Set rsQuiz = Server.CreateObject("ADODB.Recordset")
			rsQuiz.Open "SELECT * FROM questions WHERE quiz_id=" _
				& QUIZ_ID & " AND question_number=" & iQuestionNumber & ";", cnnQuiz
				
			' Set our question info
			strQuestionText = CStr(rsQuiz.Fields("question_text").Value)
			
			' Get an array of answers
			aAnswers = Array( _
			CStr(rsQuiz.Fields("answer_a").Value & ""), _
			CStr(rsQuiz.Fields("answer_b").Value & ""), _
			CStr(rsQuiz.Fields("answer_c").Value & ""), _
			CStr(rsQuiz.Fields("answer_d").Value & ""), _
			CStr(rsQuiz.Fields("answer_e").Value & ""), _
			CStr(rsQuiz.Fields("answer_f").Value & ""))
				
			' This is probably bad coding style, but too bad... it works!
			For I = LBound(aAnswers) To UBound(aAnswers)
				If aAnswers(I) = "" Then
					ReDim Preserve aAnswers(I - 1)
					Exit For
				End If
			Next ' I

			' Close and dispose of our DB objects
			rsQuiz.Close
			Set rsQuiz = Nothing
			cnnQuiz.Close
			Set cnnQuiz = Nothing
		Else
			' If we're not going to the DB, hard code in values here!
			' BEGIN HARD CODE
			Select Case iQuestionNumber
				Case 1
					strQuestionText = "What does ASP stand for?"
					aAnswers = Array( _
						"Active Server Pages", _
						"Additional Sensory Perception", _
						"Accidental Script Problem", _
						"Altruistically Solving Problems", _
						"Additional Sleeping Preferred", _
						"Any Solution Possible")
				Case 2
					strQuestionText = "What command is &lt;%= %&gt; equivalent to?"
					aAnswers = Array( _
						"Response.Write", _
						"Request.Write", _
						"Referer.Write", _
						"Redirect.Write", _
						"Reasonably.Write", _
						"Damn It I'm Right!")
				Case 3
					strQuestionText = "What does &quot;Option Explicit&quot; do?"
					aAnswers = Array( _
						"Requires explicit variable declaration", _
						"Makes the computer give you additional errors", _
						"Converts a PG rated programming language into one rated NC-17")
				Case 4
					strQuestionText = "Which of the following is not a valid "
					strQuestionText = strQuestionText & "VBScript looping statement?"
					aAnswers = Array( _
						"Do...Loop", _
						"While...Wend", _
						"For...Next", _
						"For Each...Next", _
						"Just do this 10 times you stupid computer!")
				Case 5
					strQuestionText = "What language can you not use to write ASP?"
					aAnswers = Array( _
						"VBScript", _
						"JavaScript (JScript)", _
						"PerlScript", _
						"SuperScript")
				Case 6
					strQuestionText = "Where does ASP code execute?"
					aAnswers = Array( _
						"On the web server", _
						"In the client's browser", _
						"On any machine it wants to", _
						"Reportedly somewhere in Washington State")
				Case 7
					strQuestionText = "Which set of acronyms is not associated with ASP?"
					aAnswers = Array( _
						"CDO, CDONTS", _
						"ADO, RDS, DAO, ODBC", _
						"IIS, PWS, MMC", _
						"ADSI, XML", _
						"B&Ouml;C, OU812, GNR, BTO")
				Case 8
					strQuestionText = "Which of the following is not something you can get "
					strQuestionText = strQuestionText & "from the Request collection?"
					aAnswers = Array( _
						"Cookies", _
						"Form", _
						"QueryString", _
						"ServerVariables", _
						"Beer", _
						"ClientCertificate")
				Case 9
					strQuestionText = "What will this script output when run?<BR><BR>&lt;%<BR>"
					strQuestionText = strQuestionText & "Dim aTempArray<BR>Dim I<BR>"
					strQuestionText = strQuestionText & "aTempArray = Array(1, 2, 3)<BR>"
					strQuestionText = strQuestionText & "For I = LBound(aTempArray) To "
					strQuestionText = strQuestionText & "Ubound(aTempArray)<BR>"
					strQuestionText = strQuestionText & "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					strQuestionText = strQuestionText & "Response.Write I & &quot&nbsp;&quot;<BR>"
					strQuestionText = strQuestionText & "Next 'I<BR>%&gt;<BR>"
					aAnswers = Array( _
						"0 1 2", _
						"1 2 3", _
						"0<BR>1<BR>2<BR>", _
						"1<BR>2<BR>3<BR>")
				Case 10
					strQuestionText = "What is the URL of the best ASP web site?"
					aAnswers = Array("http://www.asp101.com (yeah... like we'd put any other choices here!)")
			End Select
			' END HARD CODE
		End If

		' Now that we've got the variables set...
		' show the appropriate question and choices
		%>

		<FONT SIZE="+2"><B><%= Session("QuizName") %></B></FONT><BR>
		
		<BR>
		
		Progress Indicator:
		<%
		Const BAR_LENGTH = 160
		If iQuestionNumber = 1 Then
			' Since a 0 width is ignored by the browsers we need to remove the image altogether!
			Response.Write "<IMG SRC=""./images/spacer_red.gif"" HEIGHT=""10"" WIDTH="""
			Response.Write BAR_LENGTH
			Response.Write """><BR>"
		Else
			Response.Write "<IMG SRC=""./images/spacer_blue.gif"" HEIGHT=""10"" WIDTH="""
			Response.Write (BAR_LENGTH / iNumberOfQuestions) * (iQuestionNumber - 1) 
			Response.Write """>"
			Response.Write "<IMG SRC=""./images/spacer_red.gif"" HEIGHT=""10"" WIDTH="""
			Response.Write (BAR_LENGTH / iNumberOfQuestions) * (iNumberOfQuestions - (iQuestionNumber - 1))
			Response.Write """><BR>"
		End If
		%>
		Question <%= iQuestionNumber %> of <%= iNumberOfQuestions %><BR>
		
		<BR>

		<STRONG><%= iQuestionNumber %>.</STRONG>&nbsp;&nbsp;<%= strQuestionText %><BR>

		<BR>

		<STRONG>Choices:</STRONG>

		<OL TYPE="A">
		<%
		For I = LBound(aAnswers) to UBound(aAnswers)
			Response.Write "<LI><A HREF=""" & Request.ServerVariables("URL")
			Response.Write "?qid=" & iQuestionNumber & "&sa=" & I & """>"
			Response.Write aAnswers(I) & "</A></LI>" & vbCrLf
		Next 'I
		%>
		</OL>
		<%
	End If
End If 'bAbort
%>
</XMP></BODY></HTML>
