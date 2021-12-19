'Copies only the weekdates from a range of dates.

Sub EnterDates()
Columns(3).Clear
Dim startDate As String, stopDate As String, startCel As Integer, stopCel As Integer, dateRange As Range
On Error Resume Next

Do
    startDate = InputBox("Please enter Start Date:  Format(mm/dd/yy)", "START DATE")
    If startDate = "" Then End
Loop Until startDate = Format(startDate, "mm/dd/yy") _
    Or startDate = Format(startDate, "m/d/yy")

Do
    stopDate = InputBox("Please enter Stop Date:  Format(mm/dd/yy)", "STOP DATE")
    If stopDate = "" Then End
Loop Until stopDate = Format(stopDate, "mm/dd/yy") _
    Or stopDate = Format(stopDate, "m/d/yy")

startDate = Format(startDate, "mm/dd/yy")
stopDate = Format(stopDate, "mm/dd/yy")

startCel = Sheets(1).Columns(1).Find(startDate, LookIn:=xlValues, lookat:=xlWhole).Row
stopCel = Sheets(1).Columns(1).Find(stopDate, LookIn:=xlValues, lookat:=xlWhole).Row

On Error GoTo errorHandler

Set dateRange = Range(Cells(startCel, 1), Cells(stopCel, 1))

Call CopyWeekDates(dateRange)  ' Passes the argument dateRange to the CopyWeekDates sub.

Exit Sub
errorHandler:
    If startCel = 0 Then MsgBox "Start Date is not in table.", 64
    If stopCel = 0 Then MsgBox "Stop Date is not in table.", 64
End Sub

 

Sub CopyWeekDates(myRange)
Dim myDay As Variant, cnt As Integer
cnt = 1
For Each myDay In myRange
    If WeekDay(myDay, vbMonday) < 6 Then
        With Range("C1")(cnt)
            .NumberFormat = "mm/dd/yy"
            .Value = myDay
        End With
    cnt = cnt + 1
    End If
Next
End Sub