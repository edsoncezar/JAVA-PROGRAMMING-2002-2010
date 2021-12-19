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

<%' BEGIN FUNCTION AREA

'* Min ******************************************************
' Finds and returns the lowest value in an array of numbers.
' Ignores non-numeric and Null data contained in the array.
' Returns Null if no numeric items are found in the array.
'************************************************************
Function Min(aNumberArray)
	Dim I               ' Standard loop counter
	Dim dblLowestSoFar  ' Numeric variable for current lowest item
	
	' Init it to Null so I know it's empty
	dblLowestSoFar = Null

	' Loop through the array
	For I = LBound(aNumberArray) to UBound(aNumberArray)
		' Testing line left in for debugging if needed
		'Response.Write aNumberArray(I) & "<BR>"
		
		' Check to be sure the item is numeric so we don't bomb out by trying to
		' compare a number to a string.
		If IsNumeric(aNumberArray(I)) Then
			' Convert it to a Double for comparison and compare it to previous lowest #.
			' If it's lower than the current lowest or the value of dblLowestSoFar is
			' still NULL then set dblLowestSoFar to it's new value.
			If CDbl(aNumberArray(I)) < dblLowestSoFar Or IsNull(dblLowestSoFar) Then
				dblLowestSoFar = CDbl(aNumberArray(I))
			End If
		End If
	Next 'I

	' Set our return value and "Get the Hell Outta Dodge."
	' http://www.asp101.com/samples/download/get_the_hell_outta_dodge.wav
	' FYI: That's Kristen Cloke as Shane Vansen from "Space: Above and Beyond"
	
	Min = dblLowestSoFar
End Function


'* Max ******************************************************
'Finds and returns the highest value in an array of numbers.
'Ignores non-numeric and Null data contained in the array.
'Returns Null if no numeric items are found in the array.
'************************************************************
Function Max(aNumberArray)
	Dim I
	Dim dblHighestSoFar

	' Y'all really don't want comments on this one too, do you?  It's exactly the
	' same as above except for the > instead of <.  I also changed the variable name
	' from dblLowestSoFar to dblHighestSoFar so it made more sense.
	
	' Notice about the "Y'all"...
	'             we've just moved to Georgia and I'm practicing my accent!  ;)

	dblHighestSoFar = Null

	For I = LBound(aNumberArray) to UBound(aNumberArray)
		' Testing line left in for debugging if needed
		'Response.Write aNumberArray(I) & "<BR>"
		If IsNumeric(aNumberArray(I)) Then
			If CDbl(aNumberArray(I)) > dblHighestSoFar Or IsNull(dblHighestSoFar) Then
				dblHighestSoFar = CDbl(aNumberArray(I))
			End If
		End If
	Next 'I
	
	Max = dblHighestSoFar
End Function

' END FUNCTION AREA
%>

<%' BEGIN RUNTIME CODE
Dim strValues   ' String variable for storing and manipulating the input
Dim aValues     ' Array variable for when we split the input into an array
'Dim I          ' Looping var that I only used for debugging

' Get our input. If there is none then we use 1 to 10 scrambled
If Request.QueryString("values") = "" Then
	strValues = "2, 9, 6, 4, 10, 3, 7, 1, 5, 8"
Else
	strValues = Request.QueryString("values")
End If

' Split our input string at the ,'s.
' Parameters explanation:  -1 means all, 1 means plain text comparison
aValues = Split(strValues, ",", -1, 1)

' Debugging stuff so I could see the split up values
'For I = LBound(aValues) to UBound(aValues)
'	Response.Write "=" & aValues(I) & "=<BR>" & vbCrLf
'Next 'I

' Call Min and Max passing them our array of values and display the results
Response.Write "<FONT SIZE=""+1""><B>Min = </B>" & Min(aValues) & "</FONT><BR><BR>" & vbCrLf
Response.Write "<FONT SIZE=""+1""><B>Max = </B>" & Max(aValues) & "</FONT><BR><BR>" & vbCrLf

' Please Note: If you're trying to call the functions directly on a list of numbers
' you'll need to convert the list to an array first using syntax something like this:
'
'Reponse.Write Max(Array(1,2,3,4,5,6,7,8,9,10))


' Finally, we display the form for people to try this with their own entries.
' I've tried to make this bulletproof.  If you find a way to break it please let me
' know so I can fix it!
%>
So you can have some fun, I'm letting you input your own set of numbers for the
functions to chew on.  You'll need to use commas to delimit your list so it knows
where to split it up.  If you don't enter any valid numbers, the functions will
return Null which won't show up as anything in the browser.

<FORM ACTION="min-max.asp" METHOD="get">
	<INPUT TYPE="text" NAME="values" VALUE="<%= strValues %>" SIZE="30"></INPUT>
	<INPUT TYPE="submit">
</FORM>
