<%@page import="java.sql.*"%>
<html>
<head>
<title>
listar
</title>
</head>
<body bgcolor="#808000">
<h1>
Listar JSP
</h1>
<%

    try {
      Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    }
    catch (Exception E) {
      out.println("Driver nao carregado!");
    }

    try {
      Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/noticias");
      PreparedStatement sql = conexao.prepareStatement("select * from artigos order by prioridade");
      ResultSet  resultado = sql.executeQuery();
      out.println("<table border=1>");
      out.println("<tr>");
      out.println("<td>Codigo</td>");
      out.println("<td>Assunto</td>");
      out.println("<td>Conteudo</td>");
      out.println("<td>Prioridade</td>");
      out.println("</tr>");
      while (resultado.next()){
        out.println("<tr>");
        out.println("<td>"+resultado.getString("codigo")+"</td>");
        out.println("<td>"+ resultado.getString("assunto")+"</td>");
        out.println("<td>"+ resultado.getString("conteudo")+"</td>");
        out.println("<td>"+ resultado.getString("prioridade")+"</td>");
        out.println("</tr>");
      }
      out.println("</table>");


      resultado.close();
    } catch (SQLException e) {
      out.println(e);
    }

%>
</body>
</html>
