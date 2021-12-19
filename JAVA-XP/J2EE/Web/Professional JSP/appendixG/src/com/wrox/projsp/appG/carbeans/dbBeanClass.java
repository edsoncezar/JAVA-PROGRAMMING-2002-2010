package com.wrox.projsp.appG.dbbeans;

// this line is really important, it defines the
// directory that the bean exists in

import java.util.*;
import java.awt.*;
import java.sql.*;

public class dbBeanClass {

  // a var for holding values
  String country = "";

  // get back the country back
  public String getCountry(int countryNumber) {

    String url = "jdbc:odbc:countrydb";
    String user = "";
    String password = "";

    try {

      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      System.out.println("got past the driver name");
      Connection con = DriverManager.getConnection(url, user, password);
      System.out.println("got past the connection");
      Statement stmt = con.createStatement();
      ResultSet rs = 
        stmt.executeQuery("SELECT name FROM countries WHERE ID =" 
                          + countryNumber);

      while (rs.next()) {
        String result = rs.getString("name");
        country = result;
      } 
      rs.close();

      if (country == "") {
        country = "No record Found for " + countryNumber;
      } 


    }   // end the try
 
    catch (Exception e) {
      country = "There was an error " + e;
    }   // end the catch
 
    return (this.country);

  } 

}
