Project Template for JBoss 3.0:
===============================

In order to use this template you have to download
and install:
- JBoss 3.0 (it does not work with JBoss 2.4)
- XDoclet 1.1.2 (from www.sf.net/projects/xdoclet)
- Ant 1.4.1 (will not work with ealier versions)

Now you have to copy the ".ant.properties.example"
to ".ant.properties" and adjust the settings to
your needs.

Start JBoss 3.0 in another console.

Then create an environment variable pointing to
the Ant home directory (ANT_HOME) or create a
shell script calling "$ANT_HOME/bin/ant" or
"%ANT_HOME%\bin\ant" and start the Ant script with:
   $ANT_HOME/bin/ant
or the script.
At the end you will see that the project is deployed
on the JBoss console.

To start the client please ensure the proper settings
in the "jndi.properties" file.

Have fun - Andreas Schaefer
