package minha_loja;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class consultar extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>consultar</title></head>");
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
  PreparedStatement sql = conexao.prepareStatement("select * from produtos where codigo= ? ");
  sql.setString(1,request.getParameter("codigo"));

  ResultSet  resultado = sql.executeQuery();

  if (!resultado.next()){
      out.println("<h1>Palavra nao encontrada</h1>");
    }
  else {
  out.println("<table border=1 >");
  out.println("<tr>");
  out.println("<td>Codigo</td>");
  out.println("<td>Descricao</td>");
  out.println("<td>Prateleira</td>");
  out.println("<td>Preco</td>");
   out.println("<td>Fabricante</td>");
  out.println("</tr>");

  resultado.beforeFirst();
  while (resultado.next()){
    out.println("<tr>");
    out.println("<td>"+resultado.getString("codigo")+"</td>");
    out.println("<td>"+ resultado.getString("descricao")+"</td>");
    out.println("<td>"+ resultado.getString("prateleira")+"</td>");
    out.println("<td>"+ resultado.getString("preco")+"</td>");
    out.println("<td>"+ resultado.getString("fabricante")+"</td>");
    out.println("</tr>");
  }
  out.println("</table>");
  }

  resultado.close();
} catch (SQLException e) {
  out.println(e);
}
//=========================FIM=============================


    out.println("</body></html>");
  }
  //Clean up resources
  public void destroy() {
  }
}