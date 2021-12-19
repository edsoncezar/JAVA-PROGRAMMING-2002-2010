import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class TechSupDB {
  Connection con;
  private String dbName = "java:comp/env/jdbc/TechSupDB";

  public TechSupDB() throws Exception {
    try  {               
      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource)ic.lookup(dbName);
      con =  ds.getConnection();
    } catch (Exception ex) {
      throw new Exception("Nao pode abrir conexao para o banco de dados: " +
                          ex.getMessage());
    }	 	
  }
    
  public void remove() {
    try {
      con.close();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
    
  public Collection getTechSupRequests() throws RequestsNotFoundException {
    ArrayList techSupRequests = new ArrayList();
    try {
      String selectStatement = "select * from SUPP_REQUESTS";
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);
      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
        techSupRequests.add(new RequestDetails(
                                         rs.getInt(1),
                                         rs.getString(2),
                                         rs.getString(3),
                                         rs.getString(4), 
                                         rs.getString(5), 
                                         rs.getString(6), 
                                         rs.getString(7), 
                                         rs.getString(8)));
      }
      rs.close();
      prepStmt.close();
    } catch(SQLException ex) {
      throw new RequestsNotFoundException(ex.getMessage());
    }
    Collections.sort(techSupRequests);
    return techSupRequests;
  }

  public RequestDetails getRequestDetails(int requestId)
                        throws RequestNotFoundException {
    try {
      String selectStatement = "select * from SUPP_REQUESTS where REQUEST_ID = ?";
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);
      prepStmt.setInt(1, requestId);
      ResultSet rs = prepStmt.executeQuery();
      if (rs.next()) {
        RequestDetails requestDetail = new RequestDetails(
                                         rs.getInt(1),
                                         rs.getString(2),
                                         rs.getString(3),
                                         rs.getString(4), 
                                         rs.getString(5), 
                                         rs.getString(6), 
                                         rs.getString(7), 
                                         rs.getString(8));
        rs.close();
        prepStmt.close();
        return requestDetail;
      } else {					
        rs.close();
        prepStmt.close();
        throw new RequestNotFoundException("Nao pode achar request: " + 
                                           requestId);
      }
    } catch (SQLException ex) {
      throw new RequestNotFoundException("Nao pode achar request: " + 
                                         requestId + " " + ex.getMessage());
    }
  }

  public int addRequest(String nome,
                        String sobrenome, String email,
                        String fone, String software,
                        String so, String problema) 
                        throws RequestNotInsertedException {

    int requestId = 0;
    try {
      requestId = getPróximoNúmero();
    } catch (SQLException ex) {
      throw new RequestNotInsertedException("Nao pode obter requestId: " + 
                                            ex.getMessage());
    }
    try {
      String insertStatement = "insert into SUPP_REQUESTS VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement prepStmt = con.prepareStatement(insertStatement);
      prepStmt.setInt(1, requestId);
      prepStmt.setString(2, nome);
      prepStmt.setString(3, sobrenome);
      prepStmt.setString(4, email);
      prepStmt.setString(5, fone);
      prepStmt.setString(6, software);
      prepStmt.setString(7, so);
      prepStmt.setString(8, problema);
      con.setAutoCommit(true);
      prepStmt.executeUpdate();
      prepStmt.close();
    } catch (SQLException ex) {
      throw new RequestNotInsertedException("Nao pode inserir request: " + 
                                         requestId + " " + ex.getMessage());
    }
    return requestId;
  }

  private int getPróximoNúmero() throws SQLException {
    final String updateStatementStr = "UPDATE SEQ_NO SET PROX_NUM = PROX_NUM + 1";
    final String selectStatementStr = "SELECT PROX_NUM FROM SEQ_NO";
    PreparedStatement updateStatement = con.prepareStatement(updateStatementStr);
    PreparedStatement selectStatement = con.prepareStatement(selectStatementStr);
    con.setAutoCommit(false);
    // Incrementa sequenciador
    updateStatement.executeUpdate();
    updateStatement.close();
    // Pega número de sequência
    ResultSet rs = selectStatement.executeQuery();
    rs.next();
    int next = rs.getInt(1);

    selectStatement.close();
    con.commit();
    // volta ao valor original do auto commit
    con.setAutoCommit(true);
    return next;
  } 
}
