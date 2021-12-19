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
Dim intNumberSelected  ' Count of items selected
Dim strSelectedTeams   ' String returned from QS (or Form)
Dim arrSelectedTeams   ' Variable to hold team array
Dim I                  ' Looping variable
	
' Retrieve the count of items selected
intNumberSelected = Request.Form("teams").Count
	
If intNumberSelected = 0 Then
	%>
	Pick your favorite teams (hold down CTRL to select more than one):<BR>
	
	<FORM ACTION="multiple_selection.asp" METHOD="post">

		<!--
		The MULTIPLE parameter allows users to select multiple items
		from the pulldown box.  The size attribute specifies how many
		items tall to make the selection box.
		-->
		<SELECT NAME="teams" MULTIPLE SIZE="10">
			<OPTION>Anaheim Angels</OPTION>
			<OPTION>Atlanta Braves</OPTION>
			<OPTION>Arizona Diamondbacks</OPTION>
			<OPTION>Baltimore Orioles</OPTION>
			<OPTION>Boston Red Sox</OPTION>
			<OPTION>Chicago Cubs</OPTION>
			<OPTION>Chicago White Sox</OPTION>
			<OPTION>Cincinnati Reds</OPTION>
			<OPTION>Cleveland Indians</OPTION>
			<OPTION>Colorado Rockies</OPTION>
			<OPTION>Detroit Tigers</OPTION>
			<OPTION>Florida Marlins</OPTION>
			<OPTION>Houston Astros</OPTION>
			<OPTION>Kansas City Royals</OPTION>
			<OPTION>Los Angeles Dodgers</OPTION>
			<OPTION>Milwaukee Brewers</OPTION>
			<OPTION>Minnesota Twins</OPTION>
			<OPTION>Montreal Expos</OPTION>
			<OPTION>New York Mets</OPTION>
			<OPTION>New York Yankees</OPTION>
			<OPTION>Oakland Athletics</OPTION>
			<OPTION>Philadelphia Phillies</OPTION>
			<OPTION>Pittsburgh Pirates</OPTION>
			<OPTION>San Diego Padres</OPTION>
			<OPTION>San Francisco Giants</OPTION>
			<OPTION>Seattle Mariners</OPTION>
			<OPTION>St. Louis Cardinals</OPTION>
			<OPTION>Tampa Bay Devil Rays</OPTION>
			<OPTION>Texas Rangers</OPTION>
			<OPTION>Toronto Blue Jays</OPTION>
		</SELECT>

		<BR>

		<INPUT type="submit" value="Send Team Selection">

	</FORM>
	<%
Else
	' Retrieve the comma delimited list of teams that is returned
	' from the Form collection.  This could also be gotten from
	' the QueryString collection, but I used the post method
	' instead of get in my form.
	strSelectedTeams = Request.Form("teams")
	
	' Split our text variable into an array so we have easy
	' programmatic access to the individual elements.  Rememeber
	' the array will start at 0 not 1 so a 10 item array will
	' run from 0 to 9 and not 1 to 10!
	
	' Split takes a string and then searches for a delimiter
	' (in this case the comma followed by a space) in that string.
	' It returns an array of strings which consists of all the
	' text except the delimiters cut up into nice little pieces
	' at the delimiters.  The last two parameters specify the
	' maximum number of delimiters to find (-1 = all) and the last
	' one is what type of comparison to perform
	' (0 = binary comparison, 1 = text comparison)
	arrSelectedTeams = Split(strSelectedTeams, ", ", -1, 1)

	
	' UPDATE NOTE:
	' One of our ever-vigilant visitors pointed out to me that this
	' will cause problems if any of your choices contain a comma.
	' While he's right, I'm leaving the code as is, because I feel
	' exposing users to the split command and some array work is a
	' good thing, but if you need to use commas try something like
	' this:
	'
	'ReDim arrSelectedTeams(intNumberSelected - 1)
	'
	'For I = 1 To intNumberSelected
	'	arrSelectedTeams(I - 1) = Request.Form("teams")(I)
	'Next 'I
	'
	' We now join our regularly scheduled program already in progress...
	
		
	' Show users the count of and string containing their choices
	%>
	<P>You selected <B><%= intNumberSelected %></B> team(s).</P>

	<P>Request.Form("teams") returned:</P>
	<P><FONT SIZE="-1"><B><%= strSelectedTeams %></B></FONT></P>

	<P>You can easily convert this to an array using the split command.  The contents of that array are shown in the table below:</P>
	
	<TABLE BORDER="1">
		<TR>
			<TH>Array Element <FONT COLOR="#FF0000">*</FONT></TH>
			<TH>Value</TH>
		</TR>
		<%
		' Some debugging lines if you start having problems
		'Response.Write LBound(arrSelectedTeams)
		'Response.Write UBound(arrSelectedTeams)

		' Loop through the array showing one table row for each selection
		For I = LBound(arrSelectedTeams) To UBound(arrSelectedTeams)
			%>
			<TR>
				<TD><%= I %></TD>
				<TD><%= arrSelectedTeams(I) %></TD>
			</TR>
			<%
		Next 'I
		%>
	</TABLE>
	
	<P><FONT COLOR="#FF0000">*</FONT> Remember that VBScript arrays start counting from 0.  So a 10 item array will run from 0 to 9!</P>
	<%

	' Some code showing fully qualified requests.  Might be fun to
	' play with or possible useful for debugging.
	'Dim Item
	'For Each Item in Request.Form
	'	Response.Write Request.Form.Key(Item) & ": "
	'	Response.Write Request.Form.Item(Item) & " "
	'	Response.Write Request.Form.Item(Item).Count & "<BR>"
	'Next
End If
%>
