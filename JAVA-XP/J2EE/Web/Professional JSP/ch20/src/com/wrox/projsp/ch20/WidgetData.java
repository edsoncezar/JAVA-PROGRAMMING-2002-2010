package com.wrox.projsp.ch20;

import java.sql.*;

public class WidgetData {
  private Connection con;
  private Statement stmt;
  private ResultSet rs = null;
  private String query;

// The following are for Access databses
public static final String LOCAL_WIDGETS = 
    "select WIDGET_ID, NAME, WIDGET_DESC, (DATE_AVAILABLE) as STR_DATE_AVAILABLE from WIDGETS";
  public static final String FOREIGN_WIDGETS = 
    "select F_W_ID, F_WIDGET, F_WIDGET_DESC, (DATE_AVAIL) as STR_DATE_AVAIL from FOREIGN_WIDGETS";


// Here are the Oracle query strings

/*  public static final String LOCAL_WIDGETS = 
    "select WIDGET_ID, NAME, WIDGET_DESC, TO_CHAR(DATE_AVAILABLE, " +
    "'MM/DD/YY') STR_DATE_AVAILABLE from BEN_WIDGETS";
  public static final String FOREIGN_WIDGETS = 
    "select F_W_ID, F_WIDGET, F_WIDGET_DESC, TO_CHAR(DATE_AVAIL, " +
    "'MM/DD/YY') STR_DATE_AVAIL from FOREIGN_WIDGETS";
*/

  private static final String ERROR_NO_RESULTSET = 
    "ResultSet not initialized.";

  public WidgetData(Connection con, String query) {
    this.con = con;
    this.query = query;
  }

  public void getWidgets() throws SQLException {
    stmt = con.createStatement();
    rs = stmt.executeQuery(query);
  } 

  public boolean next() throws SQLException {
    if (rs == null) {
      throw new SQLException(this.ERROR_NO_RESULTSET);
    } 

    return rs.next();
  } 

  public String getWidgetID() throws SQLException {
    return rs.getString(1);
  } 

  public String getWidgetName() throws SQLException {
    return rs.getString(2);
  } 

  public String getWidgetDescription() throws SQLException {
    return rs.getString(3);
  } 

  public String getWidgetDate() throws SQLException {
    return rs.getString(4);
  } 

  public void closeWidget() {
    try {
      stmt.close();
      con.close();
    } catch (SQLException e) {}
  } 
}
