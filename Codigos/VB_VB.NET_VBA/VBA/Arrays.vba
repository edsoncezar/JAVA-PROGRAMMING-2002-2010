Sub MyTestArray()
Dim myCrit(1 To 4) As String ' Declaring array and setting bounds
Dim Response As String
Dim i As Integer
Dim myFlag As Boolean
myFlag = False

'  To fill array with values
    myCrit(1) = "A"
    myCrit(2) = "B"
    myCrit(3) = "C"
    myCrit(4) = "D"

Do Until myFlag = True
Response = InputBox("Please enter your choice: (i.e. A,B,C or D)")
'  Check if Response matches anything in array
    For i = 1 To 4  'UCase ensures that Response and myCrit are the same case
        If UCase(Response) = UCase(myCrit(i)) Then
            myFlag = True: Exit For
        End If
    Next i
Loop
End Sub