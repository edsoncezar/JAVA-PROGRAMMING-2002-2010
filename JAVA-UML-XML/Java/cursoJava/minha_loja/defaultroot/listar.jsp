<%@page import="java.sql.*"%>
<html>
<head>
<title>
listar
</title>
</head>
<body bgcolor="#ffffff">
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
      Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/loja");
      PreparedStatement sql = conexao.prepareStatement("select * from produtos order by codigo");
      ResultSet  resultado = sql.executeQuery();
      out.println("<table border=1>");
      out.println("<tr>");
      out.println("<td>Codigo</td>");
      out.println("<td>Descricao</td>");
      out.println("<td>Prateleira</td>");
      out.println("<td>Preco</td>");
      out.println("<td>Fabricante</td>");
      out.println("</tr>");
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


      resultado.close();
    } catch (SQLException e) {
      out.println(e);
    }

%>
</body>
</html>