package enquanto;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

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

    int x=0;
    while(x<10){
      out.println("<br>"+x);
      x++;
    }

    int nota=8;
    switch (nota) {
      case 1:
      case 2:
      case 3:
      case 4:
           out.println("riumzinho");
        break;
      case 5:
      case 6:
      case 7:
      case 8:
           out.println("bom");
        break;
      case 9:
      case 10:
           out.println("otimo");
      default:
           out.println("nota invalida");
        break;
    }

    int dia=4;
    out.println((dia<10?"0":"")+dia);

    int z=0;
    int y=5;
    try{
      int a=y/z;
    } catch (ArithmeticException e){
      out.println("<h1>Nao digite zero</h1>");
      out.println(e.getMessage());
    } catch (Exception e) {
      out.println(e.getMessage());
    }



    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}