// Import Servlet packages
import javax.servlet.*;
import javax.servlet.http.*;

// Import other Java packages
import java.io.*;
import java.util.*;
import java.sql.*;

public class TechSupListaServlet extends HttpServlet {
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

    Iterator it = null;
    try {
      Collection requests = techSupDB.getTechSupRequests();
      it = requests.iterator();
    } catch(RequestsNotFoundException e ) {
      throw new ServletException("Erro no banco de dados: ", e);
    }
    PrintWriter out = res.getWriter();
    res.setContentType("text/html");
    out.println("<HTML><HEAD>");
    out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"cursos.css\"/>");
    out.println("<TITLE>");
    out.println("Suporte Técnico: Lista de Pedidos");
    out.println("</TITLE>");
    out.println("</HEAD>");
    out.println("<BODY>");
    out.println("<H1>Suporte Técnico: Lista de Pedidos</H1>");
    out.println("<TABLE CLASS=\"clsIndex\">");
    out.println("<TR>");
    out.println("<TD CLASS=\"clsBigNav\">");
    out.println("Identificação de pedido");
    out.println("</TD>");
    out.println("<TD CLASS=\"clsBigNav\">");
    out.println("Sobrenome");
    out.println("</TD>");
    out.println("<TD CLASS=\"clsBigNav\">");
    out.println("Software");
    out.println("</TD>");
    out.println("</TR>");
    while(it.hasNext()) {
      RequestDetails rd = (RequestDetails)it.next();
      out.println("<TR>");
      out.println("<TD CLASS=\"clsTitle\">");
      out.println("<a href=\"/techsup/detalha?requestId=" + rd.getRequestId() + "\">");
      out.println(rd.getRequestId());
      out.println("</A>");
      out.println("</TD>");
      out.println("<TD CLASS=\"clsTitle\">");
      out.println(rd.getSobrenome());
      out.println("</TD>");
      out.println("<TD CLASS=\"clsTitle\">");
      out.println(rd.getSoftware());
      out.println("</TD>");
      out.println("</TR>");
    }
    out.close();
  } 
}
