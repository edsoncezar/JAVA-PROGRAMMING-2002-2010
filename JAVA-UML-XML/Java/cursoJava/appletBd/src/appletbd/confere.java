package appletbd;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class confere extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();

    //====================== LISTAR =========================
    try {
      Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    }
    catch (Exception E) {
      out.println("Driver nao carregado!");
    }

    try {
      Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/controle");
      PreparedStatement sql = conexao.prepareStatement("select * from usuarios where nome=? and senha=?");
      sql.setString(1,request.getParameter("nome"));
      sql.setString(2,request.getParameter("senha"));
      ResultSet  resultado = sql.executeQuery();

      if (!resultado.next()){
        out.println("ERRO!");
      }
      else {
        out.println("OK");
      }
      resultado.close();
    } catch (SQLException e) {
      out.println(e);
    }
    //=========================FIM=============================


  }
  //Clean up resources
  public void destroy() {
  }
}