// Import Servlet packages
import javax.servlet.*;
import javax.servlet.http.*;

// Import other Java packages
import java.io.*;
import java.sql.*;

public class TechSupServlet extends HttpServlet {
  private TechSupDB techSupDB;

  public void init() throws ServletException {
    techSupDB =
            (TechSupDB)getServletContext().getAttribute("techSupDB");
    if(techSupDB == null) {
      throw new UnavailableException("Nao pode obter o banco de dados.");
    }
  }

  public void destroy() {
    techSupDB.remove();
    techSupDB = null;
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse res) 
          throws ServletException, IOException {
    String nome = req.getParameter("nome");
    String sobrenome = req.getParameter("sobrenome");
    String email = req.getParameter("email");
    String fone = req.getParameter("fone");
    String software = req.getParameter("software");
    String so = req.getParameter("so");
    String problema = req.getParameter("problema");
    // deveria verificar os parametros para ter certeza que n�o s�o nulos
    int requestId = 0;
    try {
      requestId = techSupDB.addRequest(nome, sobrenome, email, fone,
                             software, so, problema);
    } catch (RequestNotInsertedException e) {
      throw new ServletException("Erro no banco de dados: ", e);
    } 

    // Prepara resposta
    PrintWriter out = res.getWriter();

    res.setContentType("text/html");

    out.println("<HTML><HEAD><TITLE>");
    out.println("Suporte T�cnico: Confirma��o de Pedido");
    out.println("</TITLE></HEAD>");
    out.println("<BODY>");
    out.println("<H1>Suporte T�cnico: Confirma��o de Pedido</H1>");
    out.println("<P>Obrigado por seu pedido. Recebemos seu pedido e ele recebeu o n�mero de identifica��o seguinte.</P>");
    out.println("<P>Identifica��o do Pedido: " + requestId + "</P>");
    out.println("<P>Favor anotar este n�mero para refer�ncia futura.</P>");
    out.println("<P>Atenderemos seu pedido nos pr�ximos 24 horas.</P>");
    out.println("<P>O administrador<br>Equipe de Suporte T�cnico. </P>");
    out.println("</BODY></HTML>");

    out.close();
  } 
}
