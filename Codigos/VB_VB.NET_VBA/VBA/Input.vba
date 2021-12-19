'To print header, control the font and to pull second line of header (the date) from worksheet
Sub Printr()
    ActiveSheet.PageSetup.CenterHeader = "&""Arial,Bold Italic""&14My Report" & Chr(13) _
        & Sheets(1).Range("A1")
    ActiveWindow.SelectedSheets.PrintOut Copies:=1
End Sub


Sub PrintRpt1()   'To control orientation
    Sheets(1).PageSetup.Orientation = xlLandscape
    Range("Report").PrintOut Copies:=1
End Sub


Sub PrintRpt2()   'To print several ranges on the same sheet - 1 copy
    Range("HVIII_3A2").PrintOut
    Range("BVIII_3").PrintOut
    Range("BVIII_4A").PrintOut
    Range("HVIII_4A2").PrintOut
    Range("BVIII_5A").PrintOut
    Range("BVIII_5B2").PrintOut
    Range("HVIII_5A2").PrintOut
    Range("HVIII_5B2").PrintOut
End Sub


'To print a defined area, center horizontally, with 2 rows as titles,
'in portrait orientation and fitted to page wide and tall - 1 copy
Sub PrintRpt3()                          
    With Worksheets("Sheet1").PageSetup  
        .CenterHorizontally = True
        .PrintArea = "$A$3:$F$15"
        .PrintTitleRows = ("$A$1:$A$2")
        .Orientation = xlPortrait
        .FitToPagesWide = 1
        .FitToPagesTall = 1
    End With
    Worksheets("Sheet1").PrintOut
End Sub

