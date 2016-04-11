package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.exceptions.QueryExecutionException;
import com.extra.DBConnector;

public class DBUtil {

	public static Connection getSQLConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		conn = DriverManager.getConnection(DBConnector.getSQLConnectionString());
		return conn;
	}

	public static Connection getSQLConnection(DBDetails dbDetails) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		conn = DriverManager.getConnection(DBConnector.getSQLConnectionString(dbDetails));
		return conn;
	}

	public static boolean testFinalQuery(String queryToExecute) throws QueryExecutionException {
		boolean returnValue = false;
		try {
			Connection conn = DBUtil.getSQLConnection(DBConnectionUtil.getDBDetails(MasterCommon.selectedDBName));
			Statement stmt = conn.createStatement();
			stmt.executeQuery(queryToExecute);
			returnValue = true;
		} catch (ClassNotFoundException | SQLException e) {
			throw new QueryExecutionException(e.getMessage());
		}
		return returnValue;
	}
}
