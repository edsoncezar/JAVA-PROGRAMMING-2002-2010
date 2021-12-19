@echo off

rem Configure a localização do J2SDK...
set JAVA_HOME=C:\dev\j2sdk1.4.2_01
set PATH=%PATH%;%JAVA_HOME%\bin

rem Configure a localizaçaão do ANT...
set ANT_HOME=C:\dev\apache-ant-1.5.4
set PATH=%PATH%;%ANT_HOME%\bin

rem Configure a localização do AspectJ...
set ASPECTJ_HOME=C:\dev\aspectj1.1
set PATH=%PATH%;%ASPECTJ_HOME%\bin

rem O variavel CLASSPATH deve conter o aspectjrt.jar p/ utilização
rem das ferramentas ajc e ajcbrowser. Este CLASSPATH não é necessário
rem caso se utilize apenas o ANT para a execução das classes
set CLASSPATH=.;%ASPECTJ_HOME%\lib\aspectjrt.jar
