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
Dim DateToCompareTo
' DateToCompareTo = #1/1/2004#
DateToCompareTo = CDate("January 1, " & (Year(Now()) + 1))

If Month(Now()) = 1 And Day(Now()) = 1 Then
	%>
	<p>
	<strong>Happy New Year!!!</strong>
	</p>
	<%
Else
	%>
	<p>
	<strong>Countdown to <%= Year(DateToCompareTo) %>!</strong><br />
	There are <%= DateDiff("m", Now(), DateToCompareTo) %> months left till the year <%= Year(DateToCompareTo) %>!<br />
	There are <%= DateDiff("d", Now(), DateToCompareTo) %> days left till the year <%= Year(DateToCompareTo) %>!<br />
	There are <%= DateDiff("h", Now(), DateToCompareTo) %> hours left till the year <%= Year(DateToCompareTo) %>!<br />
	There are <%= DateDiff("n", Now(), DateToCompareTo) %> minutes left till the year <%= Year(DateToCompareTo) %>!<br />
	There are <%= DateDiff("s", Now(), DateToCompareTo) %> seconds left till the year <%= Year(DateToCompareTo) %>!
	</p>
	<%
End If
%>
<p>
If you are curious it is currently <%= Time() %> on <%= Date() %>.
</p>
