@echo off

rem Configure a localiza��o do J2SDK...
set JAVA_HOME=C:\dev\j2sdk1.4.2_01
set PATH=%PATH%;%JAVA_HOME%\bin

rem Configure a localiza�a�o do ANT...
set ANT_HOME=C:\dev\apache-ant-1.5.4
set PATH=%PATH%;%ANT_HOME%\bin

rem Configure a localiza��o do AspectJ...
set ASPECTJ_HOME=C:\dev\aspectj1.1
set PATH=%PATH%;%ASPECTJ_HOME%\bin

rem O variavel CLASSPATH deve conter o aspectjrt.jar p/ utiliza��o
rem das ferramentas ajc e ajcbrowser. Este CLASSPATH n�o � necess�rio
rem caso se utilize apenas o ANT para a execu��o das classes
set CLASSPATH=.;%ASPECTJ_HOME%\lib\aspectjrt.jar
