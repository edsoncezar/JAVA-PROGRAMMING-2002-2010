
' This is a simple sub that changes what you type in a cell to upper case.
Private Sub Worksheet_Change(ByVal Target As Excel.Range)
Application.EnableEvents = False
    Target = UCase(Target)
Application.EnableEvents = True
End Sub

' This sub shows a UserForm if the user selects any cell in myRange
Private Sub Worksheet_SelectionChange(ByVal Target As Excel.Range)
On Error Resume Next
Set myRange = Intersect(Range("A1:A10"), Target)
If Not myRange Is Nothing Then
    UserForm1.Show
End If
End Sub

' You should probably use this with the sub above to ensure
' that the user is outside of myRange when the sheet is activated.
Private Sub Worksheet_Activate()
    Range("B1").Select
End Sub

' In this example, Sheets("Table") contains, in Column A, a list of
' dates (for example Mar-97) and in Column B, an amount for Mar-97.
' If you enter Mar-97 in Sheet1, it places the amount for March in
' the cell to the right. (The sub below is in the code section of
' Sheet 1.)
Private Sub Worksheet_Change(ByVal Target As Excel.Range)
On Error GoTo iQuitz
Dim cel As Range, tblRange As Range
Set tblRange = Sheets("Table").Range("A1:A48")
Application.EnableEvents = False
For Each cel In tblRange
    If UCase(cel) = UCase(Target) Then
        With Target(1, 2)
            .Value = cel(1, 2).Value
            .NumberFormat = "#,##0.00_);[Red](#,##0.00)"
        End With
        Columns(Target(1, 2).Column).AutoFit
        Exit For
    End If
Next
iQuitz:
Application.EnableEvents = True
End Sub

'If you select a cell in a column that contains values, the total
'of all the values in the column will show in the statusbar.
Private Sub Worksheet_SelectionChange(ByVal Target As Excel.Range)
Dim myVar As Double
myVar = Application.Sum(Columns(Target.Column))
If myVar <> 0 Then
    Application.StatusBar = Format(myVar, "###,###")
Else
    Application.StatusBar = False
End If
End Sub