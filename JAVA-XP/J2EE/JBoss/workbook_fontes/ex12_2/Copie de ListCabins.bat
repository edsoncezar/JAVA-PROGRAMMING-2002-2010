@echo off
REM * Lists available cabins for a specific Cruise having a desired number of beds
REM * Uses command-line parameters:   
REM * ListCabins <cruiseID> <bedCount>
REM
ant.bat client_127 -Dparam.cruise=%1 -Dparam.bedcount=%2
