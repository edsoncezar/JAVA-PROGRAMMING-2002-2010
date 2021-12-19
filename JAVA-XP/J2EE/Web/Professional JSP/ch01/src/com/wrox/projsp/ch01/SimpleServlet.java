package com.wrox.projsp.ch01;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SimpleServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    PrintWriter pw = resp.getWriter();
    pw.print("<html><body>Hello world!</body></html>");
    pw.close();
  }
}

