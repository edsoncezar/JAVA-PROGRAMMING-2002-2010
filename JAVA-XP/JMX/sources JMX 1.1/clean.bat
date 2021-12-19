@echo off
@rem
@rem Cleans up all .class files
@rem
@rem Author: Steve Perry

@set TARGET_NAME=clean

@set JAVA_HOME=c:\jdk1.3.1

@rem
@rem Set Ant version. You may need to change this 
@rem according to your particular configuration...
@rem
@set ANT_VERSION=1.4

@rem
@rem Set the ANT_HOME environment variable...
@rem
@set ANT_HOME=c:\ant%ANT_VERSION%

@echo Starting Build ...

call %ANT_HOME%\bin\ant %TARGET_NAME%

:DONE
