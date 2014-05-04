package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	static Connection conn;

	public static Connection getConnection() {
		try {
			String url = "jdbc:mysql://199.98.20.120/";
			String dbName = "users";
			String uname = "userAccess";
			String pwd = "uapass";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			try {
				conn = DriverManager.getConnection(url + dbName, uname, pwd);
			} catch (SQLException ex) {
				System.out.println("HERE");
				ex.printStackTrace();
				System.out.println("HERE2");
			}
		} catch (Exception e){
			System.out.println(e);
		}
		return conn;
	}

}
