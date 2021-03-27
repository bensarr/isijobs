package repository.jdbc;

import java.sql.*;

public interface DataSource {

	public Connection getConnection()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException;
	public void initPS(String sql);
    public int executeMaj();
    public ResultSet executeSelect();
    public PreparedStatement getPstm();
    public void CloseConnection();
}
