Sub AddSheetWithNameCheckIfExists()
Dim ws As Worksheet
Dim newSheetName As String
newSheetName = Sheets(1).Range("A1")   '   Substitute your range here
    For Each ws In Worksheets
        If ws.Name = newSheetName Or newSheetName = "" Or IsNumeric(newSheetName) Then
            MsgBox "Sheet already exists or name is invalid", vbInformation
            Exit Sub
        End If
    Next
Sheets.Add Type:="Worksheet"
    With ActiveSheet
        .Move after:=Worksheets(Worksheets.Count)
        .Name = newSheetName
    End With
End Sub


Sub Add_Sheet()
Dim wSht As Worksheet
Dim shtName As String
shtName = Format(Now, "mmmm_yyyy")
For Each wSht In Worksheets
    If wSht.Name = shtName Then
        MsgBox "Sheet already exists...Make necessary " & _
            "corrections and try again."
        Exit Sub
    End If
Next wSht
    Sheets.Add.Name = shtName
    Sheets(shtName).Move After:=Sheets(Sheets.Count)
    Sheets("Sheet1").Range("A1:A5").Copy _
        Sheets(shtName).Range("A1")
End Sub


Sub Copy_Sheet()
Dim wSht As Worksheet
Dim shtName As String
shtName = "NewSheet"
For Each wSht In Worksheets
    If wSht.Name = shtName Then
        MsgBox "Sheet already exists...Make necessary " & _
            "corrections and try again."
        Exit Sub
    End If
Next wSht
Sheets(1).Copy before:=Sheets(1)
Sheets(1).Name = shtName
Sheets(shtName).Move After:=Sheets(Sheets.Count)

End Sub