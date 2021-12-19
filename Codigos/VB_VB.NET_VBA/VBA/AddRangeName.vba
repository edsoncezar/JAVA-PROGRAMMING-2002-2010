' To add a range name for known range
Sub AddName1()
ActiveSheet.Names.Add Name:="MyRange1", RefersTo:="=$A$1:$B$10"
End Sub


' To add a range name based on a selection
Sub AddName2()
ActiveSheet.Names.Add Name:="MyRange2", RefersTo:="=" & Selection.Address()
End Sub


' To add a range name based on a selection using a variable. Note: This is a shorter version
Sub AddName3()
Dim rngSelect As String
rngSelect = Selection.Address
ActiveSheet.Names.Add Name:="MyRange3", RefersTo:="=" & rngSelect
End Sub


' To add a range name based on a selection. (The shortest version)
Sub AddName4()
Selection.Name = "MyRange4"
End Sub