# Cleans up all .class files
# Author: Steve Perry

TARGET_NAME=clean

export JAVA_HOME="c:/jdk1.3.1"

# Set Ant version. You may need to change this 
# according to your particular configuration...
# 
ANT_VERSION=1.4

# Set the ANT_HOME environment variable...
#
ANT_HOME=c:/ant${ANT_VERSION}

echo Starting Build ...

${ANT_HOME}/bin/ant ${TARGET_NAME}

