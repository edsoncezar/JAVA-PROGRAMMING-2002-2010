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