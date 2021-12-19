package noticias;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class excluir extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>excluir</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");

    //====================== LISTAR =========================
    try {
      Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    }
    catch (Exception E) {
      out.println("Driver nao carregado!");
    }

    try {
      Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/noticias");
      PreparedStatement sql = conexao.prepareStatement("delete from artigos where codigo = ? ");
      sql.setString(1,request.getParameter("codigo"));
      int quant = sql.executeUpdate();
      if (quant==0) {
        out.println("<h1>Codigo nao encontrado</h1>");
      }
      else {
        out.println("<h1>Artigo excluido</h1>");
      }

    } catch (SQLException e) {
      out.println(e);
    }
    //=========================FIM=======================


    out.println("</body></html>");
  }

}