package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.extra.DBConnector;

public class DBUtil {

	public static Connection getSQLConnection() throws SQLException,
			ClassNotFoundException {
		Connection conn = null;
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		conn = DriverManager
				.getConnection(DBConnector.getSQLConnectionString());
		return conn;
	}
}
