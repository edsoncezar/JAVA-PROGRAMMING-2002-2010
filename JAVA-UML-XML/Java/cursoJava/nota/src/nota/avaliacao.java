package nota;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class avaliacao extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>avaliacao</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");
    out.println("<h1>Avalia��o</h1><br>");

    int nota=Integer.parseInt(request.getParameter("nota"));
    if (nota>=7) {
      out.println("Aprovado");
    }
    else {
      out.println("Reprovado");
    }

    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
  }