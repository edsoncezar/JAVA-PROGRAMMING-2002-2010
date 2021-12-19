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
Dim bAutoNavigate ' Our flag which indicates autonavigation status

' Set our flag to False unless it's passed on the querystring as True
bAutoNavigate = CBool(Request.QueryString("auto"))
If bAutoNavigate <> True Then bAutoNavigate = False

' If needed include the client side script for the autonavigation
If bAutoNavigate = True Then
	%>
	<SCRIPT ID=clientEventHandlersJS LANGUAGE=javascript>
	<!--
	function filename_onchange() {
		//window.location = document.formNav.filename[document.formNav.filename.selectedIndex].value;
		document.formNav.submit();
		return true;
	}
	//-->
	</SCRIPT>
	<%
End If

' Show our form
' Pretty plain, but you could pull the links from a DB to make things
' more interesting.  See our db_pulldown sample if you're interested.
' The only thing to notice is that I remove the event handler if we're
' not autonavigating.
%>
<FORM NAME="formNav" ACTION="pulldown_nav_go.asp" METHOD="post">
	<SELECT NAME="filename" SIZE="1"<% If bAutoNavigate Then Response.Write " LANGUAGE=javascript onchange=""return filename_onchange()""" %>>
		<OPTION VALUE="pulldown_nav.asp">Where do you want to go?</OPTION>
		<OPTION VALUE="index.asp">Sample Index</OPTION>
		<OPTION VALUE="viewasp.asp?file=pulldown_nav.asp">View ASP</OPTION>
		<OPTION VALUE="viewhtml.asp?file=pulldown_nav.asp">View HTML</OPTION>
		<OPTION VALUE="download.asp?file=pulldown_nav.asp">Download Source Code</OPTION>
	</SELECT>
	<%
	' If we're autonavigating then I hide the submit button
	' since it's not needed.  That is assuming the script works!  ;)
	If Not bAutoNavigate Then
		%>
		<INPUT TYPE="submit" VALUE="Go">
		<%
	End If
	%>
</FORM>

<%' Our little on / off switch.  It passes the opposite of the current setting! %>
<A HREF="pulldown_nav.asp?auto=<%= Not bAutoNavigate %>">Toggle Auto Navigation</A>
