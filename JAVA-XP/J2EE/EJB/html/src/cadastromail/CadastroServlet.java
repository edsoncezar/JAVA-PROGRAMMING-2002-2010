import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class CadastroServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {

    // pega parâmetros do request
    String nome = request.getParameter("nome");
    String email = request.getParameter("email");
    
    GregorianCalendar calendário = new GregorianCalendar();
    String mensagem = calendário.get(Calendar.AM_PM) == Calendar.AM ?
                      "Bom dia" : "Boa Tarde";

    // acerta tipo MIME para a resposta
    response.setContentType("text/html");

    PrintWriter out = response.getWriter();
    out.println("<HTML>");
    out.println("<BODY>");
    out.println("<P>" + mensagem + ", " + nome + "</P>");
    out.println("<P>Obrigado por cadastrar seu email (" + email + ") conosco.</P>");
    out.println("<P>- A Galera Java</P>");
    out.println("</BODY>");
    out.println("</HTML>");
    out.close();
  }
}

