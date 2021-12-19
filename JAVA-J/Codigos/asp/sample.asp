<%@ Language=VBScript %>
<%Option Explicit%>

<%
'This function will display one of several colored strings depending on how many 
'  days have passed since "datLastUpdated".
function ShowFadingUpdate(datLastUpdated)
	dim intDaysSince
	
	intDaysSince = DateDiff("d", datLastUpdated, Now())
	
'MODIFY: You can modify the numbers here to change the effective date 
'			ranges for each image. You can also add or remove if clauses
'			to increase or reduce the number of date ranges.
	if intDaysSince < 7 then
'MODIFY: Change the value of ShowFadingUpdate to be what you would like to display up to the 
'			seventh day.
		ShowFadingUpdate = "<span style=""font-family: tahoma; font-size: 8pt; font-weight: bold; color: #990000;"">Updated</span>"
	elseif intDaysSince < 14 then
'MODIFY: Change the value of ShowFadingUpdate to be what you would like to display up to the 
'			fourteenth day.
		ShowFadingUpdate = "<span style=""font-family: tahoma; font-size: 8pt; font-weight: bold; color: #999933;"">Updated</span>"	
	elseif intDaysSince < 21 then
'MODIFY: Change the value of ShowFadingUpdate to be what you would like to display up to the 
'			twenty-first day.
		ShowFadingUpdate = "<span style=""font-family: tahoma; font-size: 8pt; font-weight: bold; color: #999999;"">Updated</span>"
	else
		ShowFadingUpdate = ""
	end if

end function

'MODIFY: In the HTML code, when you update something, remember to put the date in 
'			the function call.
%>
<html>
<body>
<%=ShowFadingUpdate("3/14/2004")%> Sample Text<br />
<%=ShowFadingUpdate("3/3/2004")%> Sample Text<br />
<%=ShowFadingUpdate("2/29/2004")%> Sample Text<br />
<br />
<%=ShowFadingUpdate(Now() - 4)%> Sample Text<br />
<%=ShowFadingUpdate(Now() - 9)%> Sample Text<br />
<%=ShowFadingUpdate(Now() - 17)%> Sample Text<br />

</body>
</html>