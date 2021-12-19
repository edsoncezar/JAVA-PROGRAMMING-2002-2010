<%@ taglib uri="http://jakarta.apache.org/taglibs/jdbc" prefix="jdbc" %>
<html>
<head><title>The Candidates</title></head>
<body>
<h1>The Candidates</h1>

<%-- Open a connection --%>
<jdbc:connection id="conn">
  <jdbc:url>jdbc:odbc:presidential_election</jdbc:url>
  <jdbc:driver>sun.jdbc.odbc.JdbcOdbcDriver</jdbc:driver>
</jdbc:connection>

<%--
<jdbc:connection id="conn">
  <jdbc:url>jdbc:mysql:///presidential_election</jdbc:url>
  <jdbc:driver>org.gjt.mm.mysql.Driver</jdbc:driver>
  <jdbc:userId>admin</jdbc:userId>
  <jdbc:password>admin</jdbc:password>
</jdbc:connection>
--%>

<%-- Create a query --%>
<table border="1" cellpadding="2" cellspacing="0">
  <tr>
    <th>Name</th>
    <th>Party</th>
  <tr>

  <jdbc:statement id="candidates" conn="conn"> 
    <jdbc:query>
      SELECT FIRSTNAME, LASTNAME, POLITICALPARTY FROM CANDIDATE
    </jdbc:query>

    <%-- Iterate through the candidates --%>
    <jdbc:resultSet id="results">
      <tr>
        <td><jdbc:getColumn position="1"/> <jdbc:getColumn position="2"/></td>
        <td><jdbc:getColumn position="3"/>
            <jdbc:wasNull><i>Not specified</i></jdbc:wasNull></td>
      </tr>
    </jdbc:resultSet>
  </jdbc:statement>
</table>

<%-- Close connection --%>
<jdbc:closeConnection conn="conn"/>

</body>
</html>

