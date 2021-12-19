package bd;

import java.sql.*;

public class Consulta {
  private String codigo, assunto, conteudo, prioridade;

  public void setCodigo(String novoCodigo) {
    codigo = novoCodigo;
    // ler o registro

    //====================== LISTAR =========================
    try {
      Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    }
    catch (Exception E) {
      System.out.println("Driver nao carregado!");
    }

    try {
      Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/noticias");
      PreparedStatement sql = conexao.prepareStatement("select * from artigos where codigo = ? ");
      sql.setString(1, codigo);
      ResultSet resultado = sql.executeQuery();
      if (!resultado.next()) {
        codigo="não encontrado";
        assunto="não encontrado";
        conteudo="não encontrado";
        prioridade="não encontrado";
      }
      else {
        codigo=resultado.getString("codigo");
        assunto=resultado.getString("assunto");
        conteudo=resultado.getString("conteudo");
        prioridade=resultado.getString("prioridade");
      }
      resultado.close();
    }
    catch (SQLException e) {
      System.out.println(e);
    }
//=========================FIM=============================

  }

  public String getCodigo() {
    return codigo;
  }

  public String getAssunto() {
    return assunto;
  }

  public String getConteudo() {
    return conteudo;
  }

  public String getPrioridade(){
    return prioridade;
  }

}