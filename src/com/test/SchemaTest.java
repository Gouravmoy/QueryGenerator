package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemaTest {

	public static void main(String args[]) throws ClassNotFoundException,
			SQLException {
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		String connectionURL = "jdbc:mysql://localhost/";
		Connection con = DriverManager.getConnection(connectionURL, "root",
				"Welcome123");
		ResultSet rs = con.getMetaData().getCatalogs();
		while (rs.next()) {
			System.out.println("TABLE_CAT = " + rs.getString("TABLE_CAT"));
		}
	}
}
