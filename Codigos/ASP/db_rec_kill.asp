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

<!-- #include file="adovbs.inc" -->
<%
' The above line is for ADODB Constants
' For more info see "What is Adovbs.inc and Why Do I Need It?"
' @ http://www.asp101.com/articles/john/adovbs/default.asp

Dim DB_CONNECTIONSTRING    ' DB connection string

' Basic Access OLE DB connection
DB_CONNECTIONSTRING = "Provider=Microsoft.Jet.OLEDB.4.0;" _
    & "Data Source=" & Server.MapPath("db_scratch.mdb") & ";"

' We're actually using SQL server so I'm going to overwrite the above
' connection string with the one for our SQL server.  You can comment
' out the following lines to use the sample Access DB.
' You can get a copy of the Access version from
' http://www.asp101.com/samples/db_scratch.mdb
DB_CONNECTIONSTRING = "Provider=SQLOLEDB;Data Source=10.2.1.214;" _
    & "Initial Catalog=samples;User Id=samples;Password=password;" _
    & "Connect Timeout=15;Network Library=dbmssocn;"

Dim cnnConnection
Dim rstShowTable
Dim objField
Dim iLooper
Dim iRecordCount
Dim strRecordsToDelete
Dim strSQL
Dim lngRecordsAffected

Const strTableName           = "scratch"
Const strPrimaryKeyFieldName = "id"

' Open our connection:
Set cnnConnection = Server.CreateObject("ADODB.Connection")
cnnConnection.Open DB_CONNECTIONSTRING

' First we see if we've got any records to delete.
strRecordsToDelete = Request.Form(strPrimaryKeyFieldName)

' Quick test to make sure all entries are numeric.
Dim arrTest
arrTest = Split(strRecordsToDelete, ", ")
For iLooper = LBound(arrTest) To UBound(arrTest)
	If Not IsNumeric(arrTest(iLooper)) Then strRecordsToDelete = ""
Next

If strRecordsToDelete <> "" Then
    strSQL = "DELETE FROM " & strTableName & " WHERE " & strPrimaryKeyFieldName _
        & " IN (" & strRecordsToDelete & ")"
    'Response.Write strSQL

    cnnConnection.Execute strSQL, lngRecordsAffected, adExecuteNoRecords
    Response.Write "<p>" & lngRecordsAffected & " Record(s) Deleted! (" _
        & Request.Form(strPrimaryKeyFieldName) & ")</p>"
End If

' Next we show the records currently in the table. I'm showing all
' this table's records, but if your table is large you'll want to
' either show a page at a time or run a query to narrow them down
' to a reasonable number.

'<!-- Show Form and Table -->
%>
<form action="<%= Request.ServerVariables("URL") %>" method="post">
<%

strSQL = "SELECT * FROM " & strTableName & " ORDER BY " & strPrimaryKeyFieldName & ";"

Set rstShowTable = Server.CreateObject("ADODB.Recordset")
rstShowTable.Open strSQL, cnnConnection, adOpenStatic, adLockPessimistic, adCmdText

' Write out title row
Response.Write "<table border=""1"" cellspacing=""2"" cellpadding=""2"">" & vbCrLf
Response.Write vbTab & "<tr>" & vbCrLf
For Each objField in rstShowTable.Fields
    Response.Write vbTab & vbTab & "<th>" & objField.Name & "</th>" & vbCrLf
Next
' Extra column for delete checkboxes
Response.Write vbTab & vbTab & "<th>Delete?</th>" & vbCrLf
Response.Write vbTab & "</tr>" & vbCrLf

If Not rstShowTable.EOF Then
    rstShowTable.MoveFirst
    ' Show data
    Do While Not rstShowTable.EOF
        Response.Write vbTab & "<tr>" & vbCrLf

        For iLooper = 0 To rstShowTable.Fields.Count - 1
            Response.Write vbTab & vbTab & "<td>" & rstShowTable.Fields(iLooper).Value _
                & "</td>" & vbCrLf
        Next

        ' Add checkbox making value the primary key field
        Response.Write vbTab & vbTab & "<td align=""center"">"
        Response.Write "<input type=""checkbox"" name=""" & strPrimaryKeyFieldName & """ "
        Response.Write "value=""" & rstShowTable.Fields(strPrimaryKeyFieldName).Value & """ />"
        Response.Write "</td>" & vbCrLf
        
        Response.Write vbTab & "</tr>" & vbCrLf
    
        rstShowTable.MoveNext
    Loop
End If

' Add row for submit button
Response.Write vbTab & "<tr>" & vbCrLf
For Each objField in rstShowTable.Fields
    Response.Write vbTab & vbTab & "<td>&nbsp;</th>" & vbCrLf
Next
Response.Write vbTab & vbTab & "<td><input type=""submit"" value=""Delete!""></td>" & vbCrLf
Response.Write vbTab & "</tr>" & vbCrLf

Response.Write "</table>" & vbCrLf

' Get a recordcount so we know if we need to add more records later.
iRecordCount = rstShowTable.RecordCount
'Response.Write iRecordCount

rstShowTable.Close
Set rstShowTable = Nothing

' Leave connection open for clean up which follows, O/W would close it here.
%>
</form>
<%
'<!-- /Show Form and Table -->


'========================================================================================
' Some behind the scenes maintenence to keep over 5 records in the table.
' You should remove this section if you plan on using this code with any other table.
If iRecordCount < 5 Then
    Set rstShowTable = Server.CreateObject("ADODB.Recordset")
    rstShowTable.Open strSQL, cnnConnection, adOpenDynamic, adLockPessimistic, adCmdText

    For iLooper = iRecordCount to 8
        rstShowTable.AddNew
        rstShowTable.Fields("text_field") = CStr(WeekdayName(WeekDay(Date())))
        rstShowTable.Fields("integer_field")   = CInt(Day(Now()))
        rstShowTable.Fields("date_time_field") = Now()
    Next
    rstShowTable.Update

    rstShowTable.Close
    Set rstShowTable = Nothing
End If
'========================================================================================

cnnConnection.Close
Set cnnConnection = Nothing
%>
