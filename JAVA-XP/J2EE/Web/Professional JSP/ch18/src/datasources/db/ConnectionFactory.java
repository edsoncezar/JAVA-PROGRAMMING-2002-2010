
package datasources.db;

import java.sql.*;

/**
 *
 * @author  Rod Johnson
 * @since July 31, 2000
 */
public interface ConnectionFactory {

	Connection getConnection() throws SQLException;

}
