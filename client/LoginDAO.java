package client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class LoginDAO {
	static Connection currentCon = null;
	static ResultSet rs = null;

	public static boolean login(String username, String password) {
		Statement stmt = null;
		String searchQuery = "select * from allUsers where uname='" + username
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
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				System.out.println(ts.getTime());
				String stamp = "update allUsers set lastSignIn=" + ts.getTime() + " where uname = '" + username 
								+ "' and password = '" + password + "';"; 
				stmt.executeUpdate(stamp);
				return true;
			}

		} catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! "
					+ ex);
		}
		return false;
	}
	
	public static boolean register(String username, String password, String email) {
		Statement stmt = null;
		String searchQuery = "select * from allUsers where uname='" + username
				+ "'";
		try {
			if (currentCon == null)
				currentCon = ConnectionManager.getConnection();
			
			stmt = currentCon.createStatement();
			ResultSet res = stmt.executeQuery(searchQuery);
			boolean userExists = res.next();

			if (!userExists) {
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				String registerQuery = "insert into allUsers (uname, password, lastSignIn, email) values"
										+ "('" + username + "', '" + password + "', '" + ts.getTime() + "', '" + email + "');";
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