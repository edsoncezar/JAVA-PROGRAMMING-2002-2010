Professional JSP, 2nd Edition - Code Download Notes
===================================================

The source bundle contains the source code from Professional JSP, 2nd Edition
(ISBN 1-86100495-8).


Quick Start
===========

Full instructions follow but you can get started by following these steps:

  * Follow the Tomcat 4.0 setup instructions on pages 974-7.

  * Copy the .war files for the chapters you are interested in from the
    wars directory into Tomcat's webapps directory. A .war file is
    effectively just a .zip file in a particular format - you can open it
    with a zip tool such as WinZip.

  * Restart Tomcat.

  * Browse to http://localhost:8080/chNN/ where NN is the chapter number
    you are interested in.



Installing the Necessary Software
=================================

You will need:

  * Java 2 Platform, Standard Edition SDK - we used version 1.3. Download from
    http://java.sun.com/j2se/1.3/.

    Set the JAVA_HOME environment variable correctly to point to the
    installation directory (e.g. C:\jdk1.3) - see page 974.


  * Tomcat 4.0 Beta 3 or higher. Download from
    http://jakarta.apache.org/tomcat/ and follow the setup instructions
    in Appendix A.

    Ensure that the CATALINA_HOME environment variable is correctly
    set to point to your Tomcat 4.0 installation directory
    (e.g. C:\jakarta-tomcat-4.0-b3) - see page 974.


  * Ant 1.3 or higher. Download from http://jakarta.apache.org/ant/ and
    unzip into a suitable directory (e.g. into C:\, which will install
    Ant into C:\jakarta-ant-1.3).

    Ensure that the ANT_HOME environment variable is correctly
    set to point to your Ant 1.3 installation directory (e.g.
    C:\jakarta-ant-1.3). Add %ANT_HOME%\bin to your PATH.


  * For chapters 12 and 21, the Java API for XML Processing (JAXP) 1.1. 
    Download from http://java.sun.com/xml/ and unzip into a suitable
    directory (e.g. into C:\, which will install JAXP into C:\jaxp-1.1).

    Ensure that the JAXP_HOME environment variable is correctly
    set to point to your JAXP 1.1 installation directory (e.g. C:\jaxp-1.1).


  * For Chapter 24 you will need JBoss 2.2.1. Download from
    http://www.jboss.org/ and unzip into a suitable directory (e.g. into
    C:\ which will install JBoss into C:\JBoss-2.2.1).

    Ensure that the JBOSS_HOME environment variable is correctly
    set to point to your JBoss 2.2.1 installation directory
    (e.g. C:\JBoss-2.2.1).



Compiling and Building Web Applications
=======================================

  * Ready-built web applications (.war files) can be found in the wars
    directory for all except chapters 12 and 21. You can either use these
    or recompile them yourself.

  * Ant is Java-based tool for building applications. It uses an XML format
    configuration to specify the steps that must be performed (compiling
    Java sources, copying files, creating a .zip file, etc.).

  * Each web application has a separate subdirectory here, generally one
    per chapter. Within each directory is a file named build.xml which
    contains instructions for Ant to build the web application.

  * To build the web application for a particular chapter, change into
    the relevant directory and execute

      ant

    from a command prompt. Provided the relevant environment variables have
    been sent and ant.bat is in your PATH, a .war file will appear in the
    wars subdirectory.

  * A .war file is effectively just a .zip file in a particular format - 
    you can open it with a zip tool such as WinZip. To deploy it in Tomcat
    4.0, copy it into the %CATALINA_HOME%/webapps directory and restart
    Tomcat. Alternatively, the .war file may be unzipped into a suitable
    subdirectory of %CATALINA_HOME%/webapps instead of just dropping in the
    .war file directly.

  * There is also a build.xml file in this directory that can be used to
    build all of the applications by calling each individual build.xml
    file in turn; to do this, just execute

      ant

    from this directory. You will need to have all the necessary software
    for all chapters available (see above) for this to work.

  * Running the command

      ant clean

    will clean everything up - delete all .class etc. files and delete the
    .war file.

  * Certain chapters have additional setup requirements - see below for
    details.



Chapter 6 Additional Setup Notes
================================

To use the extended example (pages 219-224):

  * Edit URL.properties so that the last two lines read:

      NEW_RECORD=/jsp/newRecordBind.jsp
      SUMMARY=/jsp/summaryBind.jsp

    in place of:

      NEW_RECORD=/jsp/newRecord.jsp
      SUMMARY=/jsp/summary.jsp



Chapter 12 Additional Setup Notes
=================================

No .war file provided

  * We can't redistribute the Java API for XML Parsing (JAXP) and so there
    is no .war file included for this chapter - unfortunately you'll have
    to build it yourself. You will need to download JAXP 1.1 from
    http://java.sun.com/xml/ and set the JAXP_HOME environment variable
    to point to the directory where you unpacked the JAXP distribution.


Correct location of XML files

  * Copy the contents of the xml folder into C:\xml




Chapter 13 Additional Setup Notes
=================================

If using MS Access database:

  * Set up a System DSN named presidential_election for the database
    OnlineVotingApp.mdb, which is in the ch13/data directory. Instructions
    are on pages 478-9.


If using mySQL database:

  * Create and populate database by following the instructions on
    pages 479-81. The scripts createtables.sql and populatetables.sql
    are in the ch13/data directory.

  * Edit the .jsp files as indicated within the files themselves
    to comment out the Access-specific code and uncomment the mySQL-
    specific code.




Chapter 14 Additional Setup Notes
=================================

Setting up context:

  * To enable separate logging for the ch14 web application add the following
    entry to server.xml, as described on page 522:

      <Context path="/ch14" docBase="ch14" debug="0" reloadable="true">
        <Logger className="org.apache.catalina.logger.FileLogger"
         prefix="localhost_ch14_log." suffix="txt" timestamp="true"/>
      </Context>

    NOTE: Per-context logging configuration is broken in Tomcat 4.0 Beta 3
    but should work correctly in subsequent beta releases.


Filter configuration:

  * Edit web.xml as described in the chapter to enable and disable filters
    and experiment with filter ordering. By default the "Simple Filter"
    (p. 522-5) is enabled and all others are disabled; adjust the commenting
    out of web.xml entries to experiment with other filters. Note that the
    format of web.xml requires that all <filter> entries precede
    <filter-mapping> entries, and that the ordering of <filter-mapping>
    elements is significant.




Chapter 15 Additional Setup Notes
=================================

Setting up context:

  * To enable separate logging for the ch15 web application add the following
    entry to server.xml, as described on page 537:

      <Context path="/ch15" docBase="ch15" debug="0" reloadable="true">
        <Logger className="org.apache.catalina.logger.FileLogger"
         prefix="localhost_ch15_log." suffix="txt" timestamp="true"/>
      </Context>

    NOTE: Per-context logging configuration is broken in Tomcat 4.0 Beta 3
    but should work correctly in subsequent beta releases.


Filter configuration:

  * Edit web.xml as described in the chapter to enable and disable filters
    and experiment with filter ordering. By default the "Visual Auditing"
    filter (p. 538-46) is enabled and all others are disabled; adjust the
    commenting out of web.xml entries to experiment with other filters.
    Note that the format of web.xml requires that all <filter> entries
    precede <filter-mapping> entries, and that the ordering of
    <filter-mapping> elements is significant.


Database setup (XSLT Filter):

  * Set up a System DSN named travel for the database traveldeals.mdb,
    which is in the ch15/data directory. Instructions are on page 572.



Chapter 16 Additional Setup Notes
=================================

Adding a user:

  * As described on page 615, add an entry to tomcat-users.xml (found in
    CATALINA_HOME/conf):

      <user name="justin" password="justin" roles="user"/>


Configuring Tomcat to use HTTPS:

  * Follow the instructions on pages 608-9.



Chapter 20 Additional Setup Notes
=================================

The code in this chapter was designed to use an Oracle database, however
we are including MS Access databases for you to use in the absence of
Oracle:

  * Set up a System DSN named widget for the database widget.mdb, and
    one named fwidget for fwidget.mdb. Both files are in the ch20/data
    directory.

If you have Oracle then edit the source files appropriately to comment
out the Access-specific code and substitute the commented-out Oracle
code.



Chapter 21 Additional Setup Notes
=================================

No .war file provided

  * We can't redistribute the Java API for XML Parsing (JAXP) and so there
    is no .war file included for this chapter - unfortunately you'll have
    to build it yourself. You will need to download JAXP 1.1 from
    http://java.sun.com/xml/ and set the JAXP_HOME environment variable
    to point to the directory where you unpacked the JAXP distribution.



Chapter 24 Additional Setup Notes
=================================

Prerequisites

  * These instructions that follow assume you are running on Microsoft Windows
    and have installed :

    - JDK 1.3
    - jBoss 2.2.1 (or 2.1)
    - Tomcat 4

    This example has been tested with jBoss 2.2.1 (and 2.1) and various
    versions of Tomcat 4 (including Beta 3) running in separate JVMs.


Compiling the Code

  * If you have an SMTP server available to test the JavaMail example
    (p. 966-9), edit src/com/wrox/store/ejb/mail/MailBean.java and replace
    smtp.yourdomain.com with its DNS name.

  * Ensure that the CATALINA_HOME and JBOSS_HOME environment variables are
    set.

  * Run ant in this directory. This will build ch24.jar and ch24.war, in the
    ../wars directory.


Deploying the EJBs to jBoss

  * Start jBoss.

  * Copy ch24.jar file into the %JBOSS_HOME%/deploy directory.

  * To test that the deployment was successful (and to upload some sample
    data), run the upload.bat and test.bat files.


Deploying the web application to Tomcat

  * Copy ch24.war into the %CATALINA_HOME%/webapps/ directory.

  * (Optional) Open up the %CATALINA_HOME%/conf/server.xml file and add
    the following underneath the "tomcat examples context":

      <Context path="/wrox" docBase="wrox" debug="0"/>


Running the example

  * Start up both jBoss and Tomcat.

  * Point a web browser to http://localhost:8080/wrox/



Appendix G Additional Setup Notes
=================================

Tomcat-IIS integration:

  * This section requires Tomcat 3.2.x rather than Tomcat 4.0. Download
    from http://jakarta.apache.org/tomcat/ and follow the instructions on
    pages 1099-1103.


MS Access database:

  * Set up a System DSN named countrydb for the database countrydb.mdb,
    which is in the appendixG/data directory. Instructions are on pages 478-9.


Jacob Java-COM bridge:

  * Please follow the setup instructions on page 1141. jacob.dll can be
    found in the appendixG/jacob directory.


Java in .NET:

  * Please follow the setup instructions on pages 1144-5.
