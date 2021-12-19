' This sub will move the sign from the right-hand side thus changing a text string into a value.

Sub MoveMinus() 
On Error Resume Next 
Dim cel As Range 
Dim myVar As Range 
Set myVar = Selection

For Each cel In myVar 
    If Right((Trim(cel)), 1) = "-" Then 
        cel.Value = cel.Value * 1 
    End If 
Next
 
   With myVar 
    .NumberFormat = "#,##0.00_);[Red](#,##0.00)" 
    .Columns.AutoFit 
End With 

End Sub 