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
' Declare the variables to store the original text and the
' text after we word wrap it.
Dim strTextPreWrap
Dim strTextPostWrap
Dim intWrapPosition

' Here's the text we're going to be using for illustration.
strTextPreWrap = "The quick brown fox jumps over the lazy dog.  " _
     & "The quick brown fox jumps over the lazy dog again.  " _
     & "The quick brown fox jumps over the lazy dog one more time."

' Pass the text to our Wrapping function and save the result
' to the new variable.  I'm using the number 40 as the default.
' I'm not going to build a form and all that stuff so that the
' code stays simple, but I will let you enter a number on the
' querystring if you want to play around with different values.
intWrapPosition = CInt(Request.QueryString("wrap"))
If intWrapPosition = 0 Then intWrapPosition = 40

' Call our function
strTextPostWrap = WordWrap(strTextPreWrap, intWrapPosition)

' Notice I've used <p><code></code></p> for display.  This gives
' you the mono-spaced type, but doesn't word wrap at vbCrLf
' characters.  I've used it instead of a <pre></pre> because I
' didn't want the original line to scroll off to the right in
' the browser.  As such I had to compensate and convert the
' vbCrLf's to <br />'s so you could see the result in the browser.
' The call to AddBrToCrLf isn't needed when outputting text
' to a non-HTML environment like an email or text file and it
' doesn't do anything on the non-wrapped text, but I used it on
' both so no one accused me of doing anything funny in it.
%>

<h3>The original string:</h3>
<p><code>
<%= AddBrToCrLf(strTextPreWrap) %>
</code></p>

<h3>The string word wrapped at <%= intWrapPosition %> characters:</h3>
<p><code>
<%= AddBrToCrLf(strTextPostWrap) %>
</code></p>

<%
' Here's the function that does the work.  It takes a string
' to word wrap and a maximum line length and returns the
' string wrapped appropriately.
Function WordWrap(strTextToBeWrapped, intMaxLineLength)
     Dim strWrappedText           ' Result storage

     Dim intLengthOfInput         ' Length of original

     Dim intCurrentPosition       ' Where we're at now
     Dim intCurrentLineStart      ' Where the current line starts
     Dim intPositionOfLastSpace   ' Last space we saw

     ' Get this once so we don't have to keep checking
     intLengthOfInput = Len(strTextToBeWrapped)

     ' Start both of these at the beginning
     intCurrentPosition = 1
     intCurrentLineStart = 1

     ' Loop through until we get to the end
     Do While intCurrentPosition < intLengthOfInput
          ' If the current position is a space, make a note of
          ' it's location for later use.
          If Mid(strTextToBeWrapped, intCurrentPosition, 1) = " " Then
               intPositionOfLastSpace = intCurrentPosition
          End If

          ' If we're at what should be the end of a line, we go back
          ' to the last space we saw and cut the line there.
          If intCurrentPosition = intCurrentLineStart + intMaxLineLength Then
               ' Some debugging lines if something's not lining up.
               'Response.Write intCurrentLineStart & "<br />"
               'Response.Write intPositionOfLastSpace & "<br />"
               'Response.Write Trim(Mid(strTextToBeWrapped, intcurrentLineStart, _
               '     intPositionOfLastSpace - intCurrentLineStart + 1)) & "<br />"

               ' Append this latest line to our result
               strWrappedText = strWrappedText _
                    & Trim(Mid(strTextToBeWrapped, intcurrentLineStart, _
                    intPositionOfLastSpace - intCurrentLineStart + 1)) _
                    & vbCrLf

               ' Reset the next line's starting point to the point we
               ' used for the last one's end + 1.
               intCurrentLineStart = intPositionOfLastSpace + 1

               ' Remove any leading spaces that might mess up our
               ' character count.  If you want to just pull of one,
               ' switch this to a simple If conditional instead of
               ' looping.
               Do While Mid(strTextToBeWrapped, intCurrentLineStart, 1) = " "
                    intCurrentLineStart = intCurrentLineStart + 1
               Loop
          End If

          ' Increment our location indicator.
          intCurrentPosition = intCurrentPosition + 1
     Loop

     ' Since the loop ends before we add the remaining text,
     ' add remaining text as the last line.
     strWrappedText = strWrappedText & Trim(Mid(strTextToBeWrapped, _
          intcurrentLineStart)) & vbCrLf

     ' Return our result to the calling line.
     WordWrap = strWrappedText
End Function

Function AddBrToCrLf(strInput)
     AddBrToCrLf = Replace(strInput, vbCrLf, "<br />" & vbCrLf)
End Function
%>
