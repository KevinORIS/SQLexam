package by.itstep.oris.workspace.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAO implements CRUD {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/hospital";
	public static final String LOGIN = "root";
	public static final String PASSWORD = "mysql123";
	
	public Connection connection;
	
	public void connect() {
		try {
			Class.forName(DRIVER);
			
			connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}
	}
	
	protected void release() {
		if (connection != null) {
			try {
				connection.close();
			} catch(SQLException e) {
				System.out.print(e);
			}
		}
	}
}
