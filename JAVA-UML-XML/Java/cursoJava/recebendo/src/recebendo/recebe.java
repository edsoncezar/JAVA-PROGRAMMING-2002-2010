package recebendo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class recebe extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>recebe</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");

    out.println("<h1>Ola Sr(a) " + request.getParameter("nome"));
    out.println("<br> Parab?ns pelos seus " + request.getParameter("idade") + " anos de vida.<br>");

    String pessoa=request.getParameter("nome");
    int idade=Integer.parseInt(request.getParameter("idade"));
    if (idade>=18) {
       out.println("Maior de idade");
    }
    else {
       out.println("Menor de idade");
    }
    out.println("<br><img src='imagens/brasil.jpg'>");
    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}