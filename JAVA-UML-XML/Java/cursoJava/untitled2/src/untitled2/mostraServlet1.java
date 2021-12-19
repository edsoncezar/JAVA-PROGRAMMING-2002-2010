package untitled2;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class mostraServlet1 extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>mostraServlet1</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");
    out.println("pARABEMS");
    out.println(request.getParameter("idade"));


    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}