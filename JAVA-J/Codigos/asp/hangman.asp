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

<%' Begin functions declarations
'**********
' GetNewWord()
'     Generates a random number from 1 to 83 which it uses to
'     select a line from a textfile with 83 lines of words in it.
'     It then retrieves the word, converts in to uppercase, and
'     returns it as its return value.
'**********
Function GetNewWord()
Dim objFSO, objFile
Dim I
Dim iRandom
Dim strTemp
	' Get a random number between 1 and 83 inclusive
	Randomize()
	iRandom = Int(83 * Rnd + 1)
	'iRandom = 42 ' A word with a space = 3, with a - = 42 
	
	' Open file and get the line equal to the number generated
	Set objFSO = Server.CreateObject("Scripting.FileSystemObject")
	Set objFile = objFSO.OpenTextFile(Server.MapPath("hangman.txt"))
	
	' Loop till line before the one we want
	For I = 1 to iRandom - 1
		objFile.SkipLine
	Next
	strTemp = UCase(objFile.ReadLine)

	objFile.Close
	Set objFile = Nothing
	Set objFSO = Nothing

	GetNewWord = strTemp
End Function ' GetNewWord()

'**********
' CalculateGuess(strWord)
'     Takes the secret word as input.  It checks to see if the
'     word contains any spaces or dashes to include them in the
'     guess since you can't guess space or dash.  Returns a
'     properly adjusted guess based upon the secret word.
'**********
Function CalculateGuess(strWord)
Dim strGuess
Dim I
	strGuess = ""
	If InStr(1, strWord, Chr(32), 1) Or InStr(1, strWord, Chr(45), 1) Then
		For I = 1 to Len(strWord)
			Select Case Asc(Mid(strWord, I, 1))
				Case 32
					strGuess = strGuess & " "
				Case 45
					strGuess = strGuess & "-"
				Case Else
					strGuess = strGuess & "_"
			End Select
		Next
	Else
		strGuess = String(Len(strWord), "_")
	End If
	CalculateGuess = strGuess
End Function ' CalculateGuess()

'**********
' CalculateGuess(strWord, strGuess, strLetter)
'     Takes the secret word, the current state of the guess, and
'     the guessed letter as input.  It updates the guess to
'     include all occurances of the guessed letter.  It returns
'     the guess containing the new letter.  Only needs to be
'     called when the letter is in the secret word.
'**********
Function UpdateGuess(strWord, strGuess, strLetter)
Dim strTemp
	strTemp = ""
	For I = 1 to Len(strWord)
		If Mid(strWord, I, 1) = strLetter Then
			strTemp = strTemp & strLetter
		Else
			strTemp = strTemp & Mid(strGuess, I, 1)
		End If
	Next	
	UpdateGuess = strTemp
End Function ' UpdateGuess()
' End functions declarations
%>

<%' Begin run-time code
Dim iDeath
Dim strWord, strGuess
Dim aLetters
Dim strLetter

Dim strTemp
Dim I

'Get all values from Session and QueryString for processing speed
iDeath = Session("Death")
strWord = Session("Word")
strGuess = Session("Guess")
aLetters = Session("LettersArray")
strLetter = Request.QueryString("letter")

' Get a new word and init variables if no guess or dead
' otherwise check letter submitted against array and secret word
If strLetter = "" or iDeath > 6 Then
	strWord = GetNewWord()
	strGuess = CalculateGuess(strWord)
	iDeath = 0
	aLetters = Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
Else
	' Set Letter in array to checked
	If 65 <= Asc(strLetter) AND Asc(strLetter) <= 90 Then
		aLetters(Asc(strLetter) - 65) = 1
	End If

	' If letter guessed is in the secret word then update guess
	' otherwise increase death amount by 1
	If InStr(1, strWord, strLetter, 1) Then
		strGuess = UpdateGuess(strWord, strGuess, strLetter)
	Else
		iDeath = iDeath + 1
	End If
End If

' Display Gallows Image based on death amount
Response.Write "<IMG SRC=""images/hang_" & iDeath + 1 & ".gif"" BORDER=0 WIDTH=100 HEIGHT=100><BR><BR>" & vbCrLf

' Debugging Outputs
'Response.Write "Word:" & strWord & vbCrLf & "<BR><BR>"
'Response.Write "Guess:" & strGuess & vbCrLf & "<BR><BR>"

'Output Guess using black letter images
For I = 1 to Len(strGuess)
	If LCase(Mid(strGuess, I, 1)) = " " Then
		Response.Write "<IMG SRC=""./images/lb_" & "~" & ".gif"" BORDER=0 WIDTH=20 HEIGHT=20>&nbsp;"
	Else
		Response.Write "<IMG SRC=""./images/lb_" & LCase(Mid(strGuess, I, 1)) & ".gif"" BORDER=0 WIDTH=20 HEIGHT=20>&nbsp;"
	End If		
Next

Response.Write "<BR><BR>" & vbCrLf

' Check to see if Dead. If so output sorry and display word.
' If not then check to see if the word is right. If so then
' output congrats.  In both of these cases, set to dead so
' nothing weird happens if the come back or try and continue
' guessing manually.
' If neither of the above then output the alphabet letters
' so the player can continue guessing, but only letters they
' haven't yet clicked should be clickable, others are red.
If iDeath >= 6 Then
	Response.Write "Sorry, you have been hung!  The word was: <B>" & LCase(strWord) & "</B><BR>"
	Response.Write "Play Again? <A HREF=""./hangman.asp"">Yes</A> <A HREF=""./index.asp"">No</A><BR>"
	iDeath = 7
Else
	If strGuess = strWord Then
		Response.Write "Congratulations you have correctly guessed the word: <B>" & LCase(strWord) & "</B><BR>"
		Response.Write "Play Again? <A HREF=""./hangman.asp"">Yes</A> <A HREF=""./index.asp"">No</A><BR>"
		iDeath = 7
	Else
		' Loop through alphabet Chr(65) = A and Chr(90)=Z
		For I = 65 to 77
			If aLetters(I - 65) = 0 Then
				Response.Write "<A HREF=""./hangman.asp?letter=" & Chr(I) & """><IMG SRC=""./images/lb_" & LCase(Chr(I)) & ".gif"" BORDER=0 WIDTH=20 HEIGHT=20></A>"
			Else
				Response.Write "<IMG SRC=""./images/lr_" & LCase(Chr(I)) & ".gif"" BORDER=0 WIDTH=20 HEIGHT=20>"
			End If
		Next
		Response.Write "<BR>" & vbCrLf
		For I = 78 to 90
			If aLetters(I - 65) = 0 Then
				Response.Write "<A HREF=""./hangman.asp?letter=" & Chr(I) & """><IMG SRC=""./images/lb_" & LCase(Chr(I)) & ".gif"" BORDER=0 WIDTH=20 HEIGHT=20></A>"
			Else
				Response.Write "<IMG SRC=""./images/lr_" & LCase(Chr(I)) & ".gif"" BORDER=0 WIDTH=20 HEIGHT=20>"
			End If
		Next
		Response.Write "<BR>" & vbCrLf
	End If
End If

' Put all values back into Session for storage
Session("Death") = iDeath
Session("Word") = strWord
Session("Guess") = strGuess
Session("LettersArray") = aLetters
%>
