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
Dim arrColors   ' Array of usable colors

Dim iMinColor   ' LBound of the array
Dim iMaxColor   ' UBound of the array

Dim iR, iG, iB  ' Index vars for looping of each color

Dim strColor    ' Temp var for building color string in loop

' Assign acceptable color components in hex.
' This is the 216 color web-safe palette.
arrColors = Array("00", "33", "66", "99", "CC", "FF")

' Note I use the same array for all the colors since
' it's really just a mechanism to hold the hex values.

' I do this to save the processing time that would o/w
' result from doing this computation on each pass of the loop.
iMinColor = LBound(arrColors)
iMaxColor = UBound(arrColors)

' Table the colors for neat display
Response.Write "<table cellspacing=""0"" cellpadding=""0"" " _
    & "border=""0"">" & vbCrLf

' Loop through reds
For iR = iMinColor To iMaxColor
    ' Put in a row break so we can see the whole thing on one page
    Response.Write "<tr>" & vbCrLf
    ' Loop through greens
    For iG = iMinColor To iMaxColor
        ' Loop through blues
        For iB = iMinColor To iMaxColor
            ' calculate the color and show it
            strColor = "#" & arrColors(iR) & arrColors(iG) & arrColors(iB)
            Response.Write "<td bgcolor=""" & strColor & """>" _
                & "<a href=""color_chooser.asp?color=" & Server.URLEncode(strColor) _
                & """><img src=""images/spacer.gif"" width=""15"" height=""15"" " _
                & "alt=""" & strColor & """ border=""0""></a></td>" & vbCrLf
        Next 'iB
    Next 'iG
    Response.Write "</tr>" & vbCrLf
Next 'iR
Response.Write "</table>" & vbCrLf
%>

<p>
<strong>
<font size="+2" color="<%= Request.QueryString("color") %>">
This text will appear in the color you click above.
</font>
</strong>
</p>
