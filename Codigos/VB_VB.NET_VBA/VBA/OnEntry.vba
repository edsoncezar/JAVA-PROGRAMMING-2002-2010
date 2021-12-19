Sub Auto_Open()
ActiveSheet.OnEntry = "Action"
End Sub


Sub Action()
If IsNumeric(ActiveCell) Then
    ActiveCell.Font.Bold = ActiveCell.Value >= 500
End If
End Sub


Sub Auto_Close()
ActiveSheet.OnEntry = ""
End Sub

