package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.extra.DBConnector;

public class DBUtil {

	public static Connection getSQLConnection() {
		Connection conn = null;
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(DBConnector
					.getSQLConnectionString());
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
