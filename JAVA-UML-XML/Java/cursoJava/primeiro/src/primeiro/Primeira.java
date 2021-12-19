package primeiro;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class Primeira extends HttpServlet {

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>Primeira</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");
    out.println("P�gina do <b>Ivan</b><br>");
    Date agora = new Date();
    out.println(agora.getHours() + ":" + agora.getMinutes() + ":" + agora.getSeconds());
    out.println("</body></html>");
  }
}