Sub ListFormulas()
    Dim counter As Integer
    Dim i As Variant
    Dim sourcerange As Range
    Dim destrange As Range
    Set sourcerange = Selection.SpecialCells(xlFormulas)
    Set destrange = Range("M1") '             Substitute your range here
    destrange.CurrentRegion.ClearContents
    destrange.Value = "Address"
    destrange.Offset(0, 1).Value = "Formula"
        If Selection.Count > 1 Then
            For Each i In sourcerange
                counter = counter + 1
                destrange.Offset(counter, 0).Value = i.Address
                destrange.Offset(counter, 1).Value = "'" & i.Formula
            Next
        ElseIf Selection.Count = 1 And Left(Selection.Formula, 1) = "=" Then
                destrange.Offset(1, 0).Value = Selection.Address
                destrange.Offset(1, 1).Value = "'" & Selection.Formula
        Else
                MsgBox "This cell does not contain a formula"
        End If
    destrange.CurrentRegion.EntireColumn.AutoFit
End Sub


Sub AddressFormulasMsgBox()  'Displays the address and formula in message box
    For Each Item In Selection
        If Mid(Item.Formula, 1, 1) = "=" Then
            MsgBox "The formula in " & Item.Address(rowAbsolute:=False, _
                columnAbsolute:=False) & " is:  " & Item.Formula, vbInformation
        End If
    Next
End Sub