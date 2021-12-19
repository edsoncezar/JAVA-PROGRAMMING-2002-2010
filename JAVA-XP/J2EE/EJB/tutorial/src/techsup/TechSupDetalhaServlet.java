// Import Servlet packages
import javax.servlet.*;
import javax.servlet.http.*;

// Import other Java packages
import java.io.*;
import java.util.*;
import java.sql.*;

public class TechSupDetalhaServlet extends HttpServlet {
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

  protected void doGet(HttpServletRequest req, HttpServletResponse res) 
          throws ServletException, IOException {

    int requestId = Integer.parseInt(req.getParameter("requestId"));
    RequestDetails rd = null;
    try {
      rd = techSupDB.getRequestDetails(requestId);
    } catch(RequestNotFoundException e) {
      throw new ServletException("Erro no banco de dados: ", e);
    }

    PrintWriter out = res.getWriter();
    res.setContentType("text/html");
    out.println("<HTML><HEAD>");
    out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"cursos.css\"/>");
    out.println("<TITLE>");
    out.println("Suporte Técnico: Detalhe de Pedido");
    out.println("</TITLE>");
    out.println("</HEAD>");
    out.println("<BODY>");
    out.println("<H1>Suporte Técnico: Detalhe de Pedido</H1>");
    out.println("<H2>Identificação de Pedido: " + rd.getRequestId() + "</H2>");
    out.println("<UL>");
    out.println("<LI>Nome: " + rd.getNome() + "</LI>");
    out.println("<LI>Sobrenome: " + rd.getSobrenome() + "</LI>");
    out.println("<LI>Email: " + rd.getEmail() + "</LI>");
    out.println("<LI>Fone: " + rd.getFone() + "</LI>");
    out.println("<LI>Software: " + rd.getSoftware() + "</LI>");
    out.println("<LI>SO: " + rd.getSo() + "</LI>");
    out.println("<LI>Problema: " + rd.getProblema() + "</LI>");
    out.println("</UL>");
    out.close();
  } 
}
