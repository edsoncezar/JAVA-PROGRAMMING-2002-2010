package noticias;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class apresenta extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>apresenta</title></head>");
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
      PreparedStatement sql = conexao.prepareStatement("select * from artigos where codigo= ? ");
      sql.setString(1,request.getParameter("codigo"));
      ResultSet  resultado = sql.executeQuery();

      if (!resultado.next()){
        out.println("<h1>Artigo nao encontrada</h1>");
      }
      else {
        out.println("<form method='post' action='./alterar'>");
        out.println("<br>Codigo <input readonly name='codigo' value='"+resultado.getString("codigo")+"'>");
        out.println("<br>Assunto <input name='assunto' value='"+resultado.getString("assunto")+"'>");
        out.println("<br>Conteudo <input name='conteudo' value='"+resultado.getString("conteudo")+"'>");
        out.println("<br>Prioridade <input name='prioridade' value='"+resultado.getString("prioridade")+"'>");
        out.println("<br><br><input type='submit' value='Salvar' >");
        out.println("<input type='reset' value='Limpar'>");
        out.println("</form>");
      }
      resultado.close();
    } catch (SQLException e) {
      out.println(e);
    }
    //=========================FIM=============================


    out.println("</body></html>");
  }

}