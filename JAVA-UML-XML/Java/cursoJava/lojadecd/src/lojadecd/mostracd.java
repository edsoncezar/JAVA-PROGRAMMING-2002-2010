package lojadecd;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class mostracd extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>mostracd</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");
    out.println("<h1>O CD escolhido foi:");
    out.println(request.getParameter("cod"));

    for (int i=0; i<100; i++ ) {
       out.println("<br>Ivan: "+i);
    }

    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}