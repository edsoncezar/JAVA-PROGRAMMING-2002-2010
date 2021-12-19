' You should create a reference to the Outlook Object Library in the VBEditor

Sub Send_Msg()
Dim objOL As New Outlook.Application
Dim objMail As MailItem

Set objOL = New Outlook.Application
Set objMail = objOL.CreateItem(olMailItem)

With objMail
    .To = "name@domain.com"
    .Subject = "Automated Mail Response"
    .Body = "This is an automated message from Excel. " & _
        "The cost of the item that you inquired about is: " & _
        Format(Range("A1").Value, "$ #,###.#0") & "."
    .Display
End With

Set objMail = Nothing
Set objOL = Nothing
End Sub