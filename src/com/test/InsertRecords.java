package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertRecords {
	public static void main(String args[]) {

		Connection conn = null;
		Statement stmt;
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection("jdbc:mysql://localhost/world?rewriteBatchedStatements=true",
					"root", "Welcome123");
			stmt = conn.createStatement();

			long start = System.currentTimeMillis();
			for (int i = 0; i < 10000000; i++) {
				String query = "insert into large_table (val) VALUES (" + i
						+ " )";
				stmt.addBatch(query);

				// execute and commit batch of 1000 queries
				if (i % 1000 == 0) {
					System.out.println("Batch Complete - " + i);
					stmt.executeBatch();
				}
			}
			// commit remaining queries in the batch
			stmt.executeBatch();
			System.out.println("Time Taken="
					+ (System.currentTimeMillis() - start));
			stmt.close();
			conn.close();
		} catch (Exception err) {
			err.printStackTrace();
		}

	}
}
