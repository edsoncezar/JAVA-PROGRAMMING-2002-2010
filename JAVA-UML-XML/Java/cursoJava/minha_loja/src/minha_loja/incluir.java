package minha_loja;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class incluir extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>incluir</title></head>");
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
     PreparedStatement sql = conexao.prepareStatement("insert into produtos (descricao,prateleira,preco,fabricante) values(?,?,?,?)");
     sql.setString(1,request.getParameter("descricao"));
     sql.setString(2,request.getParameter("prateleira"));
     sql.setString(3,request.getParameter("preco"));
     sql.setString(4,request.getParameter("fabricante"));
     sql.executeUpdate();
     out.println("<h2>Artigo incluido</h2>");

   } catch (SQLException e) {
     out.println("Nao foi possivel incluir");
     out.println("Erro: "+ e.getMessage());
   }
   //=========================FIM=======================


    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}