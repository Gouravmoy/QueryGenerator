package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.entity.DBTypes;
import com.exceptions.DBConnectionError;
import com.exceptions.QueryExecutionException;
import com.extra.DBConnector;
import com.extra.Keys;

public class DBUtil {

	public static Connection getSQLConnection() throws DBConnectionError {
		Connection conn;
		String driver = Keys.SQL_DRIVER_NAME;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(DBConnector
					.getSQLConnectionString());
		} catch (ClassNotFoundException | SQLException e) {
			throw new DBConnectionError(
					"Error in connecting to SQLConnection - " + e.getMessage());
		}
		return conn;
	}

	public static Connection getSQLConnection(DBDetails dbDetails)
			throws SQLException, ClassNotFoundException {
		Connection conn;
		String driver = Keys.SQL_DRIVER_NAME;
		Class.forName(driver);
		conn = DriverManager.getConnection(DBConnector
				.getSQLConnectionString(dbDetails));
		return conn;
	}

	public static List<String> getSchemaName(String conUrl, String userName,
			String portNo, String dbName, String password, String dbType)
			throws DBConnectionError {
		List<String> listSchemas = new ArrayList<>();
		String connectionURL = "";
		Connection con;
		String sqlDriver = "com.mysql.jdbc.Driver";
		String db2Driver = "com.ibm.db2.jcc.DB2Driver";
		try {

			if (dbType.equals(DBTypes.SQL.toString())) {
				Class.forName(sqlDriver);
				connectionURL = "jdbc:mysql://localhost/";
			}
			if (dbType.equals(DBTypes.DB2.toString())) {
				Class.forName(db2Driver);
				connectionURL = "jdbc:db2://" + conUrl + ":" + portNo + "/"
						+ dbName;
				con = DriverManager.getConnection(connectionURL, userName,
						password);
				return getDB2Schemas(con);
			}
			con = DriverManager
					.getConnection(connectionURL, userName, password);
			ResultSet rs = con.getMetaData().getCatalogs();
			while (rs.next()) {
				listSchemas.add(rs.getString("TABLE_CAT"));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DBConnectionError(
					"DB Connection Error. Please Select all Necessary Feilds");
		}
		return listSchemas;
	}

	private static ArrayList<String> getDB2Schemas(Connection con)
			throws SQLException {
		ArrayList<String> returnSchemaList = new ArrayList<>();
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(MasterCommon.queriesProps
				.getProperty(Keys.KEY_DB2_SCHEMA_LIST));
		while (res.next()) {
			returnSchemaList.add(res.getString(1));
		}
		res.close();
		stmt.close();
		con.close();
		return returnSchemaList;
	}

	public static boolean testFinalQuery(String queryToExecute)
			throws QueryExecutionException {
		boolean returnValue = false;
		DBDetails dbDetails;
		Connection conn = null;
		ResultSet res = null;
		Statement stmt = null;
		dbDetails = DBConnectionUtil.getDBDetails(MasterCommon.selectedDBName);
		try {
			if (dbDetails.getDbType().equals(DBTypes.SQL.toString()))
				conn = DBUtil.getSQLConnection();
			if (dbDetails.getDbType().equals(DBTypes.DB2.toString()))
				conn = DBUtil.getDB2Connection(dbDetails);
			if (conn != null) {
				stmt = conn.createStatement();
				res = stmt.executeQuery(queryToExecute);
			}
			returnValue = true;
			if (res != null)
				res.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();

		} catch (ClassNotFoundException | SQLException | DBConnectionError e) {
			throw new QueryExecutionException(e.getMessage());
		}
		return returnValue;
	}

	public static Connection getDB2Connection(DBDetails dbDetails)
			throws ClassNotFoundException, SQLException {
		Connection conn;
		String driver = Keys.SQL_DRIVER_NAME;
		Class.forName(driver);
		conn = DriverManager.getConnection(
				DBConnector.getDB2ConnectionString(dbDetails),
				dbDetails.getUserName(), dbDetails.getPassword());
		return conn;
	}
}
