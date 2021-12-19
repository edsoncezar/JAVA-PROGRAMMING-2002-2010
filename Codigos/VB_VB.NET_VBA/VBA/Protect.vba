' This sub looks at every cell on the worksheet and
' if the cell DOES NOT have a formula, a date or text
' and the cell IS numeric, it unlocks the cell and
' makes the font blue.  For everything else, it locks
' the cell and makes the font black.  It then protects
' the worksheet.
' This has the effect of allowing someone to edit the
' numbers but they cannot change the text, dates or
' formulas.

Sub Set_Protection()
On Error GoTo errorHandler
Dim myDoc As Worksheet
Dim cel As Range
Set myDoc = ActiveSheet
myDoc.UnProtect
For Each cel In myDoc.UsedRange
    If Not cel.HasFormula And _
    Not TypeName(cel.Value) = "Date" And _
    Application.IsNumber(cel) Then
        cel.Locked = False
        cel.Font.ColorIndex = 5
    Else
        cel.Locked = True
        cel.Font.ColorIndex = xlColorIndexAutomatic
    End If
Next
myDoc.Protect
Exit Sub
errorHandler:
MsgBox Error
End Sub