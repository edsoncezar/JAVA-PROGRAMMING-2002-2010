Sub FindDates()
On Error GoTo errorHandler
Dim startDate As String
Dim stopDate As String
Dim startRow As Integer
Dim stopRow As Integer
    startDate = InputBox("Enter the Start Date:  (mm/dd/yy)")
        If startDate = "" Then End
    stopDate = InputBox("Enter the Stop Date:  (mm/dd/yy)")
        If stopDate = "" Then End
    startDate = Format(startDate, "mm/??/yy")
    stopDate = Format(stopDate, "mm/??/yy")
    startRow = Worksheets("Table").Columns("A").Find(startDate, _
        lookin:=xlValues, lookat:=xlWhole).Row
    stopRow = Worksheets("Table").Columns("A").Find(stopDate, _
        lookin:=xlValues, lookat:=xlWhole).Row
    Worksheets("Table").Range("A" & startRow & ":A" & stopRow).Copy _
        destination:=Worksheets("Report").Range("A1")
End
errorHandler:
MsgBox "There has been an error:  " & Error() & Chr(13) _
    & "Ending Sub.......Please try again", 48
End Sub