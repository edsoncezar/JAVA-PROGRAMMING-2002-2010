Sub SelectDown()
    Range(ActiveCell, ActiveCell.End(xlDown)).Select
End Sub


Sub Select_from_ActiveCell_to_Last_Cell_in_Column()
Dim topCel As Range
Dim bottomCel As Range
On Error GoTo errorHandler
Set topCel = ActiveCell
Set bottomCel = Cells((65536), topCel.Column).End(xlUp)
    If bottomCel.Row >= topCel.Row Then
        Range(topCel, bottomCel).Select
    End If
Exit Sub
errorHandler:
MsgBox "Error no. " & Err & " - " & Error
End Sub


Sub SelectUp()
    Range(ActiveCell, ActiveCell.End(xlUp)).Select
End Sub


Sub SelectToRight()
    Range(ActiveCell, ActiveCell.End(xlToRight)).Select
End Sub


Sub SelectToLeft()
    Range(ActiveCell, ActiveCell.End(xlToLeft)).Select
End Sub


Sub SelectCurrentRegion()
    ActiveCell.CurrentRegion.Select
End Sub


Sub SelectActiveArea()
    Range(Range("A1"), ActiveCell.SpecialCells(xlLastCell)).Select
End Sub


Sub SelectActiveColumn()
    If IsEmpty(ActiveCell) Then Exit Sub
    On Error Resume Next
    If IsEmpty(ActiveCell.Offset(-1, 0)) Then Set TopCell = ActiveCell Else Set TopCell = ActiveCell.End(xlUp)
    If IsEmpty(ActiveCell.Offset(1, 0)) Then Set BottomCell = ActiveCell Else Set BottomCell = ActiveCell.End(xlDown)
    Range(TopCell, BottomCell).Select
End Sub


Sub SelectActiveRow()
    If IsEmpty(ActiveCell) Then Exit Sub
    On Error Resume Next
    If IsEmpty(ActiveCell.Offset(0, -1)) Then Set LeftCell = ActiveCell Else Set LeftCell = ActiveCell.End(xlToLeft)
    If IsEmpty(ActiveCell.Offset(0, 1)) Then Set RightCell = ActiveCell Else Set RightCell = ActiveCell.End(xlToRight)
    Range(LeftCell, RightCell).Select
End Sub


Sub SelectEntireColumn()
    Selection.EntireColumn.Select
End Sub


Sub SelectEntireRow()
    Selection.EntireRow.Select
End Sub


Sub SelectEntireSheet()
    Cells.Select
End Sub


Sub ActivateNextBlankDown()
    ActiveCell.Offset(1, 0).Select
    Do While Not IsEmpty(ActiveCell)
        ActiveCell.Offset(1, 0).Select
    Loop
End Sub


Sub ActivateNextBlankToRight()
    ActiveCell.Offset(0, 1).Select
    Do While Not IsEmpty(ActiveCell)
        ActiveCell.Offset(0, 1).Select
    Loop
End Sub


Sub SelectFirstToLastInRow()
    Set LeftCell = Cells(ActiveCell.Row, 1)
    Set RightCell = Cells(ActiveCell.Row, 256)

    If IsEmpty(LeftCell) Then Set LeftCell = LeftCell.End(xlToRight)
    If IsEmpty(RightCell) Then Set RightCell = RightCell.End(xlToLeft)
    If LeftCell.Column = 256 And RightCell.Column = 1 Then ActiveCell.Select Else Range(LeftCell, RightCell).Select
End Sub


Sub SelectFirstToLastInColumn()
    Set TopCell = Cells(1, ActiveCell.Column)
    Set BottomCell = Cells(16384, ActiveCell.Column)

    If IsEmpty(TopCell) Then Set TopCell = TopCell.End(xlDown)
    If IsEmpty(BottomCell) Then Set BottomCell = BottomCell.End(xlUp)
    If TopCell.Row = 16384 And BottomCell.Row = 1 Then ActiveCell.Select Else Range(TopCell, BottomCell).Select
End Sub


Sub SelCurRegCopy()
    Selection.CurrentRegion.Select
    Selection.Copy
    Range("A17").Select ' Substitute your range here
    ActiveSheet.Paste
    Application.CutCopyMode = False
End Sub