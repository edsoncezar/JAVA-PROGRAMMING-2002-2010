' Tests the value in each cell of a column and if it is greater
' than a given number, places it in another column.  This is just
' an example so the source range, target range and test value may
' be adjusted to fit different requirements.

Sub Test_Values()
Dim topCel As Range, bottomCel As Range, _
    sourceRange As Range, targetRange As Range
Dim x As Integer, i As Integer, numofRows As Integer
Set topCel = Range("A2")
Set bottomCel = Range("A65536").End(xlUp)
If topCel.Row > bottomCel.Row Then End     ' test if source range is empty
Set sourceRange = Range(topCel, bottomCel)
Set targetRange = Range("D2")
numofRows = sourceRange.Rows.Count
x = 1
For i = 1 To numofRows
    If Application.IsNumber(sourceRange(i)) Then
        If sourceRange(i) > 1300000 Then
            targetRange(x) = sourceRange(i)
            x = x + 1
        End If
    End If
Next
End Sub