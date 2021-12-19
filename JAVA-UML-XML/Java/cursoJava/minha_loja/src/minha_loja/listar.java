package minha_loja;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class listar extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>listar</title></head>");
    out.println("<body bgcolor=\"#ffffff\">");

    //====================== LISTAR =========================
    try {
      Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    }
    catch (Exception E) {
      out.println("Driver nao carregado!");
    }

    try {
      Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/loja");
      PreparedStatement sql = conexao.prepareStatement("select * from produtos order by codigo");
      ResultSet  resultado = sql.executeQuery();
      out.println("<table border=1  >");
      out.println("<tr>");
      out.println("<td>Codigo</td>");
      out.println("<td>Assunto</td>");
      out.println("<td>Conteudo</td>");
      out.println("<td>Prioridade</td>");
      out.println("</tr>");
      while (resultado.next()){
        out.println("<tr>");
        out.println("<td>" + resultado.getString("codigo") + "</td>");
        out.println("<td>" + resultado.getString("descricao") + "</td>");
        out.println("<td>" + resultado.getString("prateleira") + "</td>");
        out.println("<td>" + resultado.getString("preco") + "</td>");


        out.println("</tr>");
      }
      out.println("</table>");


      resultado.close();
    } catch (SQLException e) {
      out.println(e);
    }
//=========================FIM=============================
    out.println("</body></html>");
 }

}

