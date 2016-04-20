package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestHugeVol {

	public static void main(String args[]) throws SQLException,
			ClassNotFoundException {

		long startt = System.currentTimeMillis();

		Connection conn = null;
		int count = 0;
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		conn = DriverManager.getConnection("jdbc:mysql://localhost/world",
				"root", "Welcome123");

		// make sure autocommit is off
		conn.setAutoCommit(false);
		Statement st = conn.createStatement();

		// Turn use of the cursor on.
		st.setFetchSize(50);
		ResultSet rs = st.executeQuery("SELECT * FROM large_table");
		while (rs.next()) {
			// System.out.print("a row was returned " + count++ + "\n");
		}
		rs.close();

		long endt = System.currentTimeMillis();
		System.out.println("Data Fetched in with cursor on : " + (endt - startt) + " msec.");
		startt = System.currentTimeMillis();
		count = 0;
		// Turn the cursor off.
		st.setFetchSize(0);
		rs = st.executeQuery("SELECT * FROM large_table");
		while (rs.next()) {
			// System.out.print("many rows were returned" + count++ + "\n");
		}
		rs.close();
		endt = System.currentTimeMillis();
		System.out.println("Data Fetched in with : " + (endt - startt) + " msec.");
		// Close the statement.
		st.close();
	}

}
