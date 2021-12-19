package com.wrox.projsp.ch15.travelbase;

import java.sql.*;
import java.io.*;

public class sqlBean
{
  private String myDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
  private String myURL = "jdbc:odbc:travel";

  protected Connection myConn;
  
  public sqlBean() {}

  public void makeConnection() throws  Exception
  {
   Class.forName( myDriver);
   myConn = DriverManager.getConnection(myURL);
  }

  public void takeDown() throws Exception
  {
   myConn.close();
  }
  
}
