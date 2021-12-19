@echo off

if "%1"=="skipant" goto SKIP_ANT

@set PACKAGE_NAME=loading
@set TARGET_NAME=build-%PACKAGE_NAME%

@rem ===========================================
@rem = You will need to modify these according =
@rem = to where your JDK, JMX RI and Ant are   =
@rem = installed.                              =
@rem ===========================================
@set JAVA_HOME=c:\jdk1.3.1
@set JMX_HOME=c:\jmx_1.1_ri_bin
@set ANT_HOME=c:\ant1.4

@set JMX_LIB_HOME=%JMX_HOME%\lib
@set CP=%CLASSPATH%;%JMX_LIB_HOME%\jmxri.jar;%JMX_LIB_HOME%\jmxtools.jar;.

@echo Starting Build ...
call %ANT_HOME%\bin\ant %TARGET_NAME%
if "%1"=="compileonly" goto DONE
:SKIP_ANT

@rem This 'if' does not work on Windows ME
@rem Uncomment if you're running on Windows 2000/NT
@rem if NOT "%ERRORLEVEL%"=="0" goto DONE

@rem %JAVA_HOME%\bin\java -classpath %CP% -DLEVEL_DEBUG -DINFO_ALL sample.%PACKAGE_NAME%.Controller 150 100
%JAVA_HOME%\bin\java -classpath %CP% -DLEVEL_DEBUG -DINFO_ALL sample.%PACKAGE_NAME%.Controller 150 100

:DONE
