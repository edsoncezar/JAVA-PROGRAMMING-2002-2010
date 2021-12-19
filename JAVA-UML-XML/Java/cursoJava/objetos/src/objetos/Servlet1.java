package objetos;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import muitoutil.*;

public class Servlet1 extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>Servlet1</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");

     Calendario c = new Calendario(2,1,2004);
     Calendario c2 = new Calendario();

     out.println("<br>data do c: "+c.getData());
     out.println("<br>data do c2: "+c2.getData());

     out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}