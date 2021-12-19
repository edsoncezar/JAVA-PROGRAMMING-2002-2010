package redirect;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class redirect extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>redirect</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");

    int idade=Integer.parseInt(request.getParameter("idade"));
    if (idade<18) {
       response.sendRedirect("http://www.monica.com.br");
       }
    else {
       response.sendRedirect("http://www.cnn.com");
       }


    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}