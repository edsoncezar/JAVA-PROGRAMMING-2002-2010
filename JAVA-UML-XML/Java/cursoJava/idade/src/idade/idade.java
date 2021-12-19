package idade;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class idade extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>idade</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");

    int idade=Integer.parseInt(request.getParameter("idade"));
    if (idade<18){
       out.println("<b><font color=green>Vai tomar Coca-Cola</font><br><br>");
       out.println("<img src='imagens/coca.jpg'>");
    }
    else{
       out.println("<b><font color=red>Vamos pro bar</font><br><br>");
       out.println("<img src='imagens/skol.jpg'>");
    }

    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}