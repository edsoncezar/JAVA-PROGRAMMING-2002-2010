Sub CountNonBlankCells()               'Returns a count of  non-blank cells in a selection
Dim myCount As Integer                   'using the CountA ws function (all non-blanks)
myCount = Application.CountA(Selection)
MsgBox "The number of non-blank cell(s) in this selection is :  "_
     & myCount, vbInformation, "Count Cells"
End Sub