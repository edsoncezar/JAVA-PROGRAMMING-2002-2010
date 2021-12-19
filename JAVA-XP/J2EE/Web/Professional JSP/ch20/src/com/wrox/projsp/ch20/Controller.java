package com.wrox.projsp.ch20;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Controller extends HttpServlet {
  private static final String PARAM_POOL_SIZE = "poolSize";
  private static final String CATALOG_JSP = "/catalog_4.jsp";

// Here are the Access Strings
  private static final String JDBC_DRIVER = 
    "sun.jdbc.odbc.JdbcOdbcDriver";

  private static final String WIDGET_CONN = 
    "jdbc:odbc:WIDGET";
  private static final String WIDGET_NAME = "admin";
  private static final String WIDGET_PW = "";

  private static final String FOREIGN_CONN = 
    "jdbc:odbc:FWIDGET";
  private static final String FOREIGN_NAME = "admin";
  private static final String FOREIGN_PW = "";


// And here are the Oracle Strings

/*  private static final String JDBC_DRIVER = 
    "oracle.jdbc.driver.OracleDriver";

  private static final String WIDGET_CONN = 
    "jdbc:oracle:thin:@db.widget.com:1521:WIDGET";
  private static final String WIDGET_NAME = "wteam";
  private static final String WIDGET_PW = "wteam";

  private static final String FOREIGN_CONN = 
    "jdbc:oracle:thin:@foreign.widget.com:1521:FWIDGET";
  private static final String FOREIGN_NAME = "fwteam";
  private static final String FOREIGN_PW = "fwteam";

*/
  private Connection con;
  private Connection con2;

  public void init() throws ServletException {
    try {
      Class.forName(JDBC_DRIVER);
    } catch (Exception e) {
      throw new ServletException(e.getMessage());
    } 
  } 

  public void doGet(HttpServletRequest req, 
                    HttpServletResponse resp) throws ServletException {

    // determine which resource is requested
    String resourceName = getResourceName(req);

    // if resource requires bean, add it to request
    if (resourceName.equalsIgnoreCase(CATALOG_JSP)) {
      try {
        con = DriverManager.getConnection(WIDGET_CONN, WIDGET_NAME, 
                                          WIDGET_PW);
        WidgetData localWidgets = new WidgetData(con, 
                                                 WidgetData.LOCAL_WIDGETS);
        localWidgets.getWidgets();

        con2 = DriverManager.getConnection(FOREIGN_CONN, FOREIGN_NAME, 
                                           FOREIGN_PW);
        WidgetData foreignWidgets = 
          new WidgetData(con2, WidgetData.FOREIGN_WIDGETS);
        foreignWidgets.getWidgets();

        req.setAttribute("localWidgets", localWidgets);
        req.setAttribute("foreignWidgets", foreignWidgets);
      } catch (SQLException e) {
        throw new ServletException(e.getMessage());
      } 
    } 

    // forward request to resource
    try {
      req.getRequestDispatcher(resourceName).forward(req, resp);
    } catch (java.io.IOException e) {}
  } 

  public void doPost(HttpServletRequest req, 
                     HttpServletResponse resp) throws ServletException {
    doGet(req, resp);
  } 

  private String getResourceName(HttpServletRequest req) {
    return req.getPathInfo();
  } 

}
