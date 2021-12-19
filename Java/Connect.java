// Connect to a database.  This program can be used to check that you have the
// database driver in place, and that you are using the right parameters.
// For example, download oracle.jar and run with
//
// Unix:     java -classpath .:oracle.jar Connect      (that's a colon)
// Windows:  java -classpath .;oracle.jar Connect      (that's a semicolon)

import java.sql.*;

class Connect
{
   String driver   = "oracle.jdbc.driver.OracleDriver";
   String database = "jdbc:oracle:thin:@lunaleka.cs.bris.ac.uk:1521:cs2000";
   String username = "ab1234";
   String password = "ab1234";

   public static void main (String[] args) throws Exception
   {
      Connect program = new Connect ();
      program.connect ();
   }

   void connect () throws Exception
   {
      Class.forName (driver);
      Connection con;
      con = DriverManager.getConnection (database, username, password);
      con.close ();
      System.out.println ("The connection apparently succeeded");
   }
}
