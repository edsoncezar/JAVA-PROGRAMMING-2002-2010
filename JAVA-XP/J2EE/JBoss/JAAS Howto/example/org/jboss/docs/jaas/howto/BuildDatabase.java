package org.jboss.docs.jaas.howto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Build the database tables for the DatabaseServerLoginModule. That module
is based on two logical tables:

 Principals(PrincipalID text, Password text)
 Roles(PrincipalID text, Role text, RoleGroup text)

@author Scott.Stark@jboss.org
@version $Revision: 1.1 $ 
*/
public class BuildDatabase
{

    /** Setup the DatabaseServerLoginModule tables using the Hypersonic
        database. This database needs to be configured under java:/DefaultDS
        which is the default config in the JBoss dist.
    */
    public static void main(String[] args)
    {
        try
        {
            // Load the Hypersonic JDBC driver
            Class.forName("org.hsqldb.jdbcDriver");
            String jdbcURL = "jdbc:hsqldb:hsql://localhost:1701";
            Connection conn = DriverManager.getConnection(jdbcURL, "sa", "");
            Statement statement = conn.createStatement();
            createPrincipalsTable(statement);
            createRolesTable(statement);
            statement.close();
            conn.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(2);
        }
        System.exit(0);
    }

    static void createPrincipalsTable(Statement statement) throws SQLException
    {
        try
        {
            statement.execute("DROP TABLE Principals");
        }
        catch(SQLException e)
        {
            // Ok, assume table does not exist
        }
        boolean result = statement.execute("CREATE TABLE Principals ("
            + "PrincipalID VARCHAR(64) PRIMARY KEY,"
            + "Password VARCHAR(64) )"
        );
        System.out.println("Created Principals table, result="+result);
        result = statement.execute("INSERT INTO Principals VALUES ('java', 'echoman')");
        System.out.println("INSERT INTO Principals VALUES ('java', 'echoman'), result="+result);
        result = statement.execute("INSERT INTO Principals VALUES ('duke', 'javaman')");
        System.out.println("INSERT INTO Principals VALUES ('duke', 'javaman'), result="+result);
    }

    static void createRolesTable(Statement statement) throws SQLException
    {
        try
        {
            statement.execute("DROP TABLE Roles");
        }
        catch(SQLException e)
        {
            // Ok, assume table does not exist
        }
        boolean result = statement.execute("CREATE TABLE Roles ("
            + "PrincipalID	VARCHAR(64),"
            + "Role	VARCHAR(64),"
            + "RoleGroup VARCHAR(64) )"
        );
        System.out.println("Created Roles table, result="+result);
        result = statement.execute("INSERT INTO Roles VALUES ('java', 'Echo', 'Roles')");
        System.out.println("INSERT INTO Roles VALUES ('java', 'Echo', 'Roles'), result="+result);
        result = statement.execute("INSERT INTO Roles VALUES ('java', 'caller_java', 'CallerPrincipal')");
        System.out.println("INSERT INTO Roles VALUES ('java', 'caller_java', 'CallerPrincipal'), result="+result);
        result = statement.execute("INSERT INTO Roles VALUES ('duke', 'Java', 'Roles')");
        System.out.println("INSERT INTO Roles VALUES ('duke', 'Java', 'Roles'), result="+result);
        result = statement.execute("INSERT INTO Roles VALUES ('duke', 'Coder', 'Roles')");
        System.out.println("INSERT INTO Roles VALUES ('duke', 'Coder', 'Roles'), result="+result);
        result = statement.execute("INSERT INTO Roles VALUES ('duke', 'caller_duke', 'CallerPrincipal')");
        System.out.println("INSERT INTO Roles VALUES ('duke', 'caller_duke', 'CallerPrincipal'), result="+result);
    }
}
