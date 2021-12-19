@echo off

@set PACKAGE_NAME=monitor
@set TARGET_NAME=build-%PACKAGE_NAME%

@rem ===========================================
@rem = You will need to modify these according =
@rem = to where your JDK, JMX RI and Ant are   =
@rem = installed.                              =
@rem ===========================================
@set JAVA_HOME=c:\jdk1.3.1
@set JMX_HOME=c:\jmx1.0.1
@set ANT_HOME=c:\ant1.4

@set JMX_LIB_HOME=%JMX_HOME%\jmx\lib
@set CP=%CLASSPATH%;%JMX_LIB_HOME%\jmxri.jar;%JMX_LIB_HOME%\jmxtools.jar;.

@echo Starting Build ...
call %ANT_HOME%\bin\ant %TARGET_NAME%

@rem This 'if' does not work on Windows ME
@rem Uncomment if you're running on Windows 2000/NT
@rem if NOT "%ERRORLEVEL%"=="0" goto DONE

%JAVA_HOME%\bin\java -classpath %CP% sample.%PACKAGE_NAME%.Controller 150 100

:DONE
