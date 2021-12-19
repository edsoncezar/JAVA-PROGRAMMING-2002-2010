package com.wrox.projsp.ch15.travelbase;

import java.sql.*;
import java.io.*;

public class queryBean extends sqlBean
{
  String myTripQuery = "select * from hotdeals where tripno > ";

  ResultSet myResultSet = null;
  public queryBean() {super();}
 
  public boolean getDeals(String tripNo) throws Exception
  {
    String myQuery = myTripQuery + tripNo;
    Statement stmt = myConn.createStatement();
    myResultSet = stmt.executeQuery(myQuery);

    return (myResultSet != null);
  }

  public boolean getNextTrip() throws Exception
  {
    return myResultSet.next();
  }

  public String getColumn( String inCol) throws Exception
  {
    return myResultSet.getString(inCol);
  }

}
