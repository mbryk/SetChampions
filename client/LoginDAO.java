package client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginDAO {
	static Connection currentCon = null;
	static ResultSet rs = null;

	public static boolean login(String username, String password) {
		Statement stmt = null;
		String searchQuery = "select * from users where uname='" + username
				+ "' AND password='" + password + "'";

		try {
			// connecting to the DB
			currentCon = ConnectionManager.getConnection();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);
			boolean userExists = rs.next();

			if (!userExists) {
				System.out
						.println("Username/Password entered is Incorrect or User doesnot Exists.");
				return false;
			} else {
				System.out.println("Welcome " + username);
				return true;
			}

		} catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! "
					+ ex);
		}
		return false;
	}
	
	public static boolean register(String username, String password) {
		Statement stmt = null;
		String searchQuery = "select * from users where uname='" + username
				+ "' AND password='" + password + "'";
		try {
			if (currentCon == null)
				currentCon = ConnectionManager.getConnection();
			
			stmt = currentCon.createStatement();
			ResultSet res = stmt.executeQuery(searchQuery);
			boolean userExists = res.next();

			if (!userExists) {
				String registerQuery = "insert into allUsers (username, password, score) values"
										+ "('" + username + "', '" + password + "', '" + "0" + "');";
				int retVal = stmt.executeUpdate(registerQuery);
				if (retVal != 0) {
					System.out.println("Successfully registered " + username);
					return true;
				}
				else {
					System.out.println("Couldn't register " + username);
					return false;
				}
			} else {
				System.out.println("Username " + username +" already exists.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
}