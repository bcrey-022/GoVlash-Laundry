package databaseconnector;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String URL = String.format("jdbc:mysql://localhost:3306/govlashdb");
	private static Connection con = null;
	private static Connect instance = null;
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	public static Connect getInstance() {
		if(instance == null) {
			synchronized (Connect.class) {
				if(instance == null) {
					instance = new Connect();
				}
			}			
		}
		return instance;
	}
}
