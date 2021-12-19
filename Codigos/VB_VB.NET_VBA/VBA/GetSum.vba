Sub GetSum()                    ' using the shortcut approach
[A1].Value = Application.Sum([E1:E15])
End Sub

Sub EnterChoice()
Dim DBoxPick As Integer
Dim InputRng As Range
Dim cel As Range
DBoxPick = DialogSheets(1).ListBoxes(1).Value
Set InputRng = Columns(1).Rows

For Each cel In InputRng
    If cel.Value = "" Then
        cel.Value = Application.Index([InputData!StateList], DBoxPick, 1)
        End
    End If
Next

End Sub
