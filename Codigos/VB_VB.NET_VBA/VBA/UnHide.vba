'-----You might want to step through this using the "Watch" feature-----

Sub Accumulate()
Dim n As Integer
Dim t As Integer
    For n = 1 To 10
        t = t + n
    Next n
    MsgBox "        The total is " & t
End Sub


'-----This sub checks values in a range 10 rows by 5 columns
'moving left to right, top to bottom-----

Sub CheckValues1()
Dim rwIndex As Integer
Dim colIndex As Integer
    For rwIndex = 1 To 10
            For colIndex = 1 To 5
                If Cells(rwIndex, colIndex).Value <> 0 Then _
                    Cells(rwIndex, colIndex).Value = 0
            Next colIndex
    Next rwIndex
End Sub


'-----Same as above using the "With" statement instead of "If"-----

Sub CheckValues2()
Dim rwIndex As Integer
Dim colIndex As Integer
    For rwIndex = 1 To 10
         For colIndex = 1 To 5
             With Cells(rwIndex, colIndex)
                 If Not (.Value = 0) Then Cells(rwIndex, colIndex).Value = 0
             End With
         Next colIndex
    Next rwIndex
End Sub


'-----Same as CheckValues1 except moving top to bottom, left to right-----

Sub CheckValues3()
Dim colIndex As Integer
Dim rwIndex As Integer
    For colIndex = 1 To 5
            For rwIndex = 1 To 10
                If Cells(rwIndex, colIndex).Value <> 0 Then _
                    Cells(rwIndex, colIndex).Value = 0
            Next rwIndex
    Next colIndex
End Sub


'-----Enters a value in 10 cells in a column and then sums the values------

Sub EnterInfo()
Dim i As Integer
Dim cel As Range
Set cel = ActiveCell
    For i = 1 To 10
        cel(i).Value = 100
    Next i
cel(i).Value = "=SUM(R[-10]C:R[-1]C)"
End Sub


' Loop through all worksheets in workbook and reset values
' in a specific range on each sheet.

Sub Reset_Values_All_WSheets()
Dim wSht As Worksheet
Dim myRng As Range
Dim allwShts As Sheets
Dim cel As Range
Set allwShts = Worksheets

For Each wSht In allwShts
Set myRng = wSht.Range("A1:A5, B6:B10, C1:C5, D4:D10")
    For Each cel In myRng
        If Not cel.HasFormula And cel.Value <> 0 Then
            cel.Value = 0
        End If
    Next cel
Next wSht

End Sub