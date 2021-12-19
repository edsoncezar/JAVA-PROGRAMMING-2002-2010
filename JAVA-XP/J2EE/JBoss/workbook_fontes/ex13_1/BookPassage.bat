@echo off
REM * Books passage for a Customer on a particular Cruise in a specified Cabin
REM * Uses command-line parameters:   
REM * BookPassage.bat <customerID> <cruiseID> <cabinID> <price>
REM
ant.bat run.client_122b -Dparam.cust=%1 -Dparam.cruise=%2 -Dparam.cabin=%3 -Dparam.price=%4
