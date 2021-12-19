<%@page import="java.sql.*"%>
<html>
<head>
<title>
listar
</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></head>
<body bgcolor="#66FFCC">
<h1 align="center"> Listar </h1>

<%
    //====================== LISTAR =========================
    try {
          Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    }
    catch (Exception E) {
       out.println("Driver nao carregado!");
    }

    try {
        Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/noticias");
        PreparedStatement sql = conexao.prepareStatement("select * from artigos order by assunto ");
        ResultSet  resultado = sql.executeQuery();
%>

       <table border=1 cellpadding="1" cellspacing="0" bordercolor="#000000">
       <tr>
          <td>codigo</td>
          <td>assunto</td>
          <td>conteudo</td>
          <td>prioridade</td>
        </tr>
<%      while (resultado.next()){ %>
          <tr>
            <td><%=resultado.getString("codigo")%></td>
            <td><%=resultado.getString("assunto")%></td>
            <td><%=resultado.getString("conteudo")%></td>
            <td><%=resultado.getString("prioridade")%></td>
         </tr>
<%      }  %>
        </table>
<%
        resultado.close();
    } catch (SQLException e) {
        out.println(e);
    }
    //=========================FIM=============================

%>
</body>
</html>

