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
' Tells the browser to use Excel to open the file
Response.ContentType = "application/vnd.ms-excel"

' Your data can come from ANYWHERE! Since that's not the
' point of this sample, I'm just hand writing a table
' below, but it could easily be generated from a database
' as illustrated in some of our other samples.

' Everything that follows is plain HTML... what you see
' in Excel is based on Excel's interpretation of it.
' It picks up the formulas and emulates the formatting
' pretty well...
%>

<!--
 <td><font color="red">=sum(a2:b2)</font></td>
-->

<table>
	<thead>
		<tr>
			<th bgcolor="blue"><font color="white">Name</font></th>
			<th bgcolor="blue"><font color="white">Sales</font></th>
			<th bgcolor="blue"><font color="white">Commission</font></th>
		</tr>
	</thead>

	<tbody>
		<tr>
			<td>David Andersen</td>
			<td>6420</td>
			<td>=(B2 * 0.05)</td>
		</tr>
		<tr>
			<td>Laura Callahan</td>
			<td>3675</td>
			<td>=(B3 * 0.05)</td>
		</tr>
		<tr>
			<td>Frank Edwards</td>
			<td>3840</td>
			<td>=(B4 * 0.05)</td>
		</tr>
		<tr>
			<td>Chris Graham</td>
			<td>4050</td>
			<td>=(B5 * 0.05)</td>
		</tr>
		<tr>
			<td>Owen Ingraham</td>
			<td>4750</td>
			<td>=(B6 * 0.05)</td>
		</tr>
		<tr>
			<td>Robert King</td>
			<td>5290</td>
			<td>=(B7 * 0.05)</td>
		</tr>
		<tr>
			<td>Greg Miller</td>
			<td>5640</td>
			<td>=(B8 * 0.05)</td>
		</tr>
		<tr>
			<td>Ian Ostrander</td>
			<td>3965</td>
			<td>=(B9 * 0.05)</td>
		</tr>
		<tr>
			<td>Howard Quinn</td>
			<td>5865</td>
			<td>=(B10 * 0.05)</td>
		</tr>
		<tr>
			<td>Tammy Steel</td>
			<td>3570</td>
			<td>=(B11 * 0.05)</td>
		</tr>
		<tr>
			<td>Debra Underwood</td>
			<td>3790</td>
			<td>=(B12 * 0.05)</td>
		</tr>
		<tr>
			<td>Mark White</td>
			<td>4500</td>
			<td>=(B13 * 0.05)</td>
		</tr>
		<tr>
			<td>Quincy Youngs</td>
			<td>4420</td>
			<td>=(B14 * 0.05)</td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td><strong><font color="blue">Totals:</font></strong></td>
			<td><font color="red">=SUM(B2:B14)</font></td>
			<td><font color="red">=SUM(C2:C14)</font></td>
		</tr>
	</tbody>
</table>
