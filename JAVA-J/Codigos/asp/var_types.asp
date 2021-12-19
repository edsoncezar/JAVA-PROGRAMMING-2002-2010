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
' Takes a variable containing some data and attempts to convert
' it to the requested type.  If it succeeds it returns the
' resulting value otherwise it returns "N/A".
Function ConvertToType(vInput, strType)
	' Set error trapping on so I can catch failures!
	On Error Resume Next

	Dim vTemp ' As Variant

	' Do the appropriate conversion
	Select Case LCase(strType)
		Case "bool", "boolean"
			vTemp = CBool(vInput)
		Case "byte"
			vTemp = CByte(vInput)
		Case "int", "integer"
			vTemp = CInt(vInput)
		Case "lng", "long"
			vTemp = CLng(vInput)
		Case "sng", "single"
			vTemp = CSng(vInput)
		Case "dbl", "double"
			vTemp = CDbl(vInput)
		Case "cur", "currency"
			vTemp = CCur(vInput)
		Case "date"
			vTemp = CDate(vInput)
		Case "str", "string"
			vTemp = CStr(vInput)
		Case Else
			' If the specified type isn't handled error out.
			Err.Raise 1
	End Select
	
	' If the specified conversion failed set our return
	' value to something we can check for.
	If Err.number <> 0 Then
		vTemp = "N/A"
		Err.Clear
	End If
	
	'Response.Write TypeName(vTemp) & "<BR>" & vbCrLf

	' Set return value
	ConvertToType = vTemp
End Function
%>

<%
Dim arrTypes  ' Array to hold our list of the different variable types we'll be trying
Dim strInput  ' The string that holds the text typed into the form
Dim vTempVar  ' Temp variant variable used to cache results from our function call
Dim I         ' Standard loop control variable and array location indicator

' Init out array of the various types
arrTypes = Array("Boolean", "Byte", "Integer", "Long", "Single", "Double", "Currency", "Date", "String")

' If available retrieve value to convert o/w set it to default of 0
If Request.QueryString("strInput").Count = 0 Then
	strInput = 0
Else
	strInput = Request.QueryString("strInput")
End If

' Show the table of results
%>
<TABLE BORDER="1" CELLSPACING="0" CELLPADDING="2">
	<TR BGCOLOR="#CCCCCC">
		<TD>Data Type</TD>
		<TD>Can Be Converted</TD>
		<TD>Value When Converted</TD>
	</TR>
<%
' Loop through our type array attempting conversion to each type
For I = LBound(arrTypes) To UBound(arrTypes)
	' Cache result in a variant variable so I don't have to keep calling the function
	vTempVar = ConvertToType(strInput, arrTypes(I))

	' Display type, conversion status, and resulting value
	%>
	<TR>
		<TD><%= arrTypes(I) %></TD>
		<TD><%= vTempVar <> "N/A" %></TD>
		<TD><%= vTempVar %></TD>
	</TR>
	<%
Next 'I

' Close the table and display the form for people to enter values to convert
%>
</TABLE>

<FORM ACTION="var_types.asp" METHOD="get">
	<INPUT TYPE="text" NAME="strInput" VALUE="<%= strInput %>">
	<INPUT TYPE="submit" value="Attempt Conversions">
</FORM>
