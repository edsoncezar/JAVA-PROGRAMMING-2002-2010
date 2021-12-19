package com.wrox.projsp.ch20;

import java.sql.*;

public class ConnectionPool {
  private static final String NO_CONNECTIONS = 
    "No connections available in pool.";

  private Connection pool[];
  private long timeout = 10000;

  public ConnectionPool(String driver, String connection, String username, 
                        String pw, int connections) throws SQLException {
    pool = new Connection[connections];

    initializePool(driver, connection, username, pw);
  }

  private void initializePool(String driver, String connection, 
                              String username, 
                              String pw) throws SQLException {
    try {
      Class.forName(driver);

      for (int i = 0; i < pool.length; i++) {
        pool[i] = DriverManager.getConnection(connection, username, pw);
      } 

    } catch (ClassNotFoundException ce) {
      throw new SQLException(ce.getMessage());
    } 
  } 

  public synchronized Connection getConnection() throws SQLException {
    Connection con = null;
    long timeStart;

    timeStart = System.currentTimeMillis();
    while ((System.currentTimeMillis() - timeStart) < timeout) {
      for (int i = 0; i < pool.length; i++) {
        if (pool[i] != null) {
          con = pool[i];
          pool[i] = null;
          break;
        } 
      } 
      if (con != null) {
        break;
      } 
    } 

    if (con == null) {
      throw new SQLException(NO_CONNECTIONS);
    } 

    return con;
  } 

  public synchronized void returnConnection(Connection con) {
    for (int i = 0; i < pool.length; i++) {
      if (pool[i] == null) {
        pool[i] = con;
        break;
      } 
    } 
  } 

  public void closePool() {
    for (int i = 0; i < pool.length; i++) {
      try {
        pool[i].close();
      } catch (SQLException e) {}
    } 
  } 
}
