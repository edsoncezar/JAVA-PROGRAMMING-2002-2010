package noticias;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class alterar extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>alterar</title></head>");
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
      PreparedStatement sql = conexao.prepareStatement("update artigos set assunto=?,conteudo=?,prioridade=? where codigo=?");
      sql.setString(1,request.getParameter("assunto"));
      sql.setString(2,request.getParameter("conteudo"));
      sql.setString(3,request.getParameter("prioridade"));
      sql.setString(4,request.getParameter("codigo"));
      sql.executeUpdate();
      out.println("<h2>Dados Alterados</h2>");

    } catch (SQLException e) {
      out.println("Nao foi possivel alterar");
      out.println("Erro: "+ e.getMessage());
    }
   //=========================FIM=======================



    out.println("</body></html>");
  }

}