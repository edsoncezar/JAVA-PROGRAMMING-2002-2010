package email;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import sun.net.smtp.SmtpClient;


public class envia extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    try {
      SmtpClient smtp = new SmtpClient("192.168.0.1");
      smtp.from("ivan@tecnoponta.com.br");
      smtp.to("usuario01@tecnoponta.com.br");

      PrintStream msg = smtp.startMessage();

      msg.println("From: ivan@tecnoponta.com.br");
      msg.println("To: usuario@tecnoponta.com.br");
      msg.println("Subject: "+request.getParameter("assunto"));
      msg.println();

      msg.println(request.getParameter("conteudo"));
      //msg.println(new Date());

      smtp.closeServer();


      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      out.println("<HTML><BODY>");

      out.println("Dados enviados<BR>");

      out.println("</BODY></HTML>");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }
  //Clean up resources
  public void destroy() {
  }
}