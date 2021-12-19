@echo off
REM * Creates two customers given their primary key, first name and lastname
REM * Uses command-line parameters:   
REM * client_61 <primarykey> <firstname> <lastname> <primarykey> <firstname> <lastname>
REM
ant.bat run.client_61 -Dparam.pk1=%1 -Dparam.fn1=%2 -Dparam.ln1=%3 -Dparam.pk2=%4 -Dparam.fn2=%5 -Dparam.ln2=%6
