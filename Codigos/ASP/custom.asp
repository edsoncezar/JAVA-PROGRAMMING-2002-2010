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
' Some constants for our text labels since I need
' to know their exact values when comparing them.
Const strPreviewLabel = "Preview Settings"
Const strSaveLabel    = "Save Settings To A Cookie"

' Variables to hold the style attributes
Dim strFontFamily
Dim strFontSize
Dim strFontStyle
Dim strFontWeight
Dim strFontColor

' See if we've got values coming in from the form.
' If so use them otherwise get the values from the
' cookie if they exist or just use some defaults.
If Request.QueryString.Count = 0 Then
	' Get settings from cookie if available O/W use defaults.
	If Request.Cookies("FontConfig").HasKeys Then
		' Get Settings From Cookie
		strFontFamily = Request.Cookies("FontConfig")("family")
		strFontSize   = Request.Cookies("FontConfig")("size")
		strFontStyle  = Request.Cookies("FontConfig")("style")
		strFontWeight = Request.Cookies("FontConfig")("weight")
		strFontColor  = Request.Cookies("FontConfig")("color")
	Else
		' Here's where I get to set it to the format I like!
		strFontFamily = "Arial"
		strFontSize   = "16pt"
		strFontStyle  = "Italic"
		strFontWeight = "Bold"
		strFontColor  = "#0000FF"
	End If
Else
	strFontFamily = Request.QueryString("family")
	strFontSize   = Request.QueryString("size")
	strFontStyle  = Request.QueryString("style")
	strFontWeight = Request.QueryString("weight")
	strFontColor  = Request.QueryString("color")

	' Check to see if they wanted to save these new values.
	' Make sure the form element has the name "submit"
	If Request.QueryString("submit") = strSaveLabel Then
		' Save Settings To Cookie
		Response.Cookies("FontConfig")("family") = strFontFamily
		Response.Cookies("FontConfig")("size")   = strFontSize
		Response.Cookies("FontConfig")("style")  = strFontStyle
		Response.Cookies("FontConfig")("weight") = strFontWeight
		Response.Cookies("FontConfig")("color")  = strFontColor
		
		' Set the cookie to apply to our sample directory only
		' and to expire in 5 days.  These should both be changed
		' to appropriate values for your implementation.
		Response.Cookies("FontConfig").Path = "/samples"
		Response.Cookies("FontConfig").Expires = DateAdd("d", 5, Date())
	End If
End If

' The display stuff... the only part you see and
' it's the simplest part of the code!  Just set the
' parameters of the style attribute of the font element.
%>

<p>
<font style="
font-family: <%= strFontFamily %>;
font-size:   <%= strFontSize %>;
font-style:  <%= strFontStyle %>;
font-weight: <%= strFontWeight %>;
color:       <%= strFontColor %>;
">
This is the text that will vary in appearence based
on the settings you choose.
</font>
</p>

<%
' Now that that's done... please realize I just used this as an
' example.  You could really let people change anything from
' page titles to background colors.
' You name it... you can probably do it.


' The following form can get pretty confusing but all it's really
' doing is maintaining state from request to request.
%>
<form action="<%= Request.ServerVariables("URL") %>" method="get">

<select name="family">
  <option <% If strFontFamily = "Arial"   Then Response.Write "selected" %>>Arial</option>
  <option <% If strFontFamily = "Courier" Then Response.Write "selected" %>>Courier</option>
  <option <% If strFontFamily = "Symbol"  Then Response.Write "selected" %>>Symbol</option>
  <option <% If strFontFamily = "Times"   Then Response.Write "selected" %>>Times</option>
</select>

<select name="size">
  <option <% If strFontSize = "8pt"  Then Response.Write "selected" %>>8pt</option>
  <option <% If strFontSize = "10pt" Then Response.Write "selected" %>>10pt</option>
  <option <% If strFontSize = "12pt" Then Response.Write "selected" %>>12pt</option>
  <option <% If strFontSize = "14pt" Then Response.Write "selected" %>>14pt</option>
  <option <% If strFontSize = "16pt" Then Response.Write "selected" %>>16pt</option>
</select>

<select name="style">
  <option <% If strFontStyle = "Normal" Then Response.Write "selected" %>>Normal</option>
  <option <% If strFontStyle = "Italic" Then Response.Write "selected" %>>Italic</option>
</select>

<select name="weight">
  <option <% If strFontWeight = "Normal" Then Response.Write "selected" %>>Normal</option>
  <option <% If strFontWeight = "Bold"   Then Response.Write "selected" %>>Bold</option>
</select>

<select name="color">
  <option value="#00FFFF" <% If strFontColor = "#00FFFF" Then Response.Write "selected" %>>Aqua</option>
  <option value="#000000" <% If strFontColor = "#000000" Then Response.Write "selected" %>>Black</option>
  <option value="#0000FF" <% If strFontColor = "#0000FF" Then Response.Write "selected" %>>Blue</option>
  <option value="#FF00FF" <% If strFontColor = "#FF00FF" Then Response.Write "selected" %>>Fushsia</option>
  <option value="#00FF00" <% If strFontColor = "#00FF00" Then Response.Write "selected" %>>Lime</option>
  <option value="#FF0000" <% If strFontColor = "#FF0000" Then Response.Write "selected" %>>Red</option>
  <option value="#FFFFFF" <% If strFontColor = "#FFFFFF" Then Response.Write "selected" %>>White</option>
  <option value="#FFFF00" <% If strFontColor = "#FFFF00" Then Response.Write "selected" %>>Yellow</option>
</select>

<br />
<br />

<input type="submit" name="submit" value="<%= strPreviewLabel %>" />

<input type="submit" name="submit" value="<%= strSaveLabel %>" />

</form>

<!--
Just a link back to this page without anything on the QueryString
so that the values from the cookie or the default values kick in.
-->
<a href="<%= Request.ServerVariables("URL") %>">
View using the settings saved in your cookie.
</a>
