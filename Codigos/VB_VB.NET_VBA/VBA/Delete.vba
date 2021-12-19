Sub DeleteRangeNames()
Dim rName As Name
	For Each rName In ActiveWorkbook.Names
		rName.Delete
	Next rName
End Sub