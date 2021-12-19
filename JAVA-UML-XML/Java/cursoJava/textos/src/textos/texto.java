package textos;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class texto extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>texto</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");

    String s="Tecnoponta";
    out.println("<br>maiuscula: "+s.toUpperCase());//tudo em mai�scula
    out.println("<br>minuscula: "+s.toLowerCase());//tudo em min�scula
    out.println("<br>sub:"+s.substring(5,10));//pega um peda�o de uma string
    out.println("<br>troca: "+s.replaceAll("o","<b><font color=red>o</font></b>"));

    String n="43,20";
    out.println("<br>"+n.replaceAll(",","."));

    String local=request.getHeader("accept-language");
    out.println("<br>"+local);
    out.println("<br>"+local.substring(0,2));

    if (local!=null && local.substring(0,2).equalsIgnoreCase("pt")){
       out.println("portugues");
    }
    else{
       out.println("ingles");
    }



    out.println("</body></html>");
  }
 }