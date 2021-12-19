package com.wrox.projsp.ch20;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ControllerPool extends HttpServlet {
  private static final String PARAM_POOL_SIZE = "poolSize";
  private static final String CATALOG_JSP = "/catalog_5.jsp";

// These are the Access Strings

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


// These are the Oracle Strings


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

  private static final int DEFAULT_POOL_SIZE = 10;

  private ConnectionPool widgetPool;
  private ConnectionPool foreignPool;
  private int poolSize;


   public void init(ServletConfig config) throws ServletException {
    super.init(config);

    /* retrieve pool size */
    this.poolSize = getPoolSize(config);

    // initialize connection pools
    try {
      widgetPool = new ConnectionPool(JDBC_DRIVER, WIDGET_CONN, 
                                      WIDGET_NAME, WIDGET_PW, poolSize);
      foreignPool = new ConnectionPool(JDBC_DRIVER, FOREIGN_CONN, 
                                       FOREIGN_NAME, FOREIGN_PW, poolSize);
    } catch (SQLException e) {
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
        WidgetDataPool localWidgets = new WidgetDataPool(widgetPool, 
                WidgetData.LOCAL_WIDGETS);
        localWidgets.getWidgets();
        WidgetDataPool foreignWidgets = new WidgetDataPool(foreignPool, 
                WidgetData.FOREIGN_WIDGETS);
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

  public void destroy() {

    // deinitialize pool
    widgetPool.closePool();
    foreignPool.closePool();
  } 

  private String getResourceName(HttpServletRequest req) {
    return req.getPathInfo();
  } 
  private int getPoolSize(ServletConfig config) {
    int paramPoolSize;

    String sps = config.getInitParameter(PARAM_POOL_SIZE);
    try {
      paramPoolSize = Integer.parseInt(sps);
    } catch (Exception e) {
      paramPoolSize = DEFAULT_POOL_SIZE;
    } 

    return paramPoolSize;
  } 

}
