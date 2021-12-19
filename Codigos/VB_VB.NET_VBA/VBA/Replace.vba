'// This sub will replace information in all sheets of the workbook \\
'//...... Replace "old stuff" and "new stuff" with your info ......\\

Sub ChgInfo()
Dim Sht As Worksheet
For Each Sht In Worksheets
    Sht.Cells.Replace What:="old stuff", _ 
        Replacement:="new stuff", LookAt:=xlPart, MatchCase:=False
Next
End Sub
