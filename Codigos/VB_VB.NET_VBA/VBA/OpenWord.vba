' You should create a reference to the Word Object Library in the VBEditor

Sub Open_MSWord()
On Error GoTo errorHandler
Dim wdApp As Word.Application
Dim myDoc As Word.Document
Dim mywdRange As Word.Range
Set wdApp = New Word.Application

With wdApp
    .Visible = True
    .WindowState = wdWindowStateMaximize
End With

Set myDoc = wdApp.Documents.Add

Set mywdRange = myDoc.Words(1)

With mywdRange
    .Text = Range("F6") & " This text is being used to test subroutine." & _
        "  More meaningful text to follow."
    .Font.Name = "Comic Sans MS"
    .Font.Size = 12
    .Font.ColorIndex = wdGreen
    .Bold = True
End With

errorHandler:

Set wdApp = Nothing
Set myDoc = Nothing
Set mywdRange = Nothing
End Sub