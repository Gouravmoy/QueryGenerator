package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.controller.MasterCommon;
import com.entity.Column;
import com.entity.DBDetails;
import com.entity.DBTypes;
import com.entity.Tables;
import com.exceptions.DBConnectionError;
import com.exceptions.QueryExecutionException;
import com.extra.DBConnector;
import com.extra.Keys;

public class DBUtil {

	static final Logger logger = Logger.getLogger(DBUtil.class);

	public static Connection getSQLConnection() throws DBConnectionError {
		Connection conn;
		String driver = Keys.SQL_DRIVER_NAME;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(DBConnector
					.getSQLConnectionString());
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Error in connecting to SQLConnection - "
					+ e.getMessage());
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
	
	public static List<String> getTables(DBDetails dbDetails) {
		MasterCommon.updateDBCredentials(dbDetails.getDbSchema(),
				dbDetails.getHostName(), dbDetails.getDatabase(),
				dbDetails.getUserName(), dbDetails.getPassword());
		PropsLoader loader = new PropsLoader();
		loader.loadProps();
		ArrayList<String> tableNames = new ArrayList<>();
		Connection conn = null;
		PreparedStatement preparedStatement;
		ResultSet res;
		String key = "";
		try {
			logger.info("Connecting to Database");
			if (dbDetails.getDbType().equals(DBTypes.SQL.toString())) {
				key = Keys.KEY_SQL_TABLE_LIST;
				conn = DBUtil.getSQLConnection();
			} else if (dbDetails.getDbType().equals(DBTypes.DB2.toString())) {
				key = Keys.KEY_DB2_TABLE_LIST;
				conn = DBUtil.getDB2Connection(dbDetails);
			}
			logger.info("Connected to Database");
			String sql = MasterCommon.queriesProps.getProperty(key);

			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, dbDetails.getDbSchema());
			res = preparedStatement.executeQuery();
			while (res.next()) {
				tableNames.add(res.getString(1).toUpperCase());
			}
			preparedStatement.close();
			conn.close();
		} catch (SQLException | DBConnectionError e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return tableNames;
	}
	
	public static List<Tables> getTablesMetaInfo(List<String> selectedTableNames) {
		ArrayList<Tables> tables = new ArrayList<Tables>();
		ArrayList<Column> columns = new ArrayList<Column>();
		Column column = new Column();
		Tables table = new Tables();
		Connection conn = null;
		PreparedStatement preparedStatement;
		ResultSet res;
		String key = "";

		DBDetails dbDetails = DBConnectionUtil
				.getDBDetails(MasterCommon.selectedDBName);

		try {
			logger.info("Connecting to Database");
			if (dbDetails.getDbType().equals(DBTypes.SQL.toString())) {
				key = Keys.KEY_SQL_META_QUERY;
				conn = DBUtil.getSQLConnection();
			} else if (dbDetails.getDbType().equals(DBTypes.DB2.toString())) {
				key = Keys.KEY_DB2_META_QUERY;
				conn = DBUtil.getDB2Connection(dbDetails);
			}
			logger.info("Connected to Database");
			String sql = MasterCommon.queriesProps.getProperty(key);
			preparedStatement = conn.prepareStatement(sql);
			for (String tableName : selectedTableNames) {
				columns = new ArrayList<>();
				preparedStatement.setString(1, tableName);
				preparedStatement.setString(2, dbDetails.getDbSchema());
				res = preparedStatement.executeQuery();
				table = new Tables();
				table.setTableName(tableName);
				while (res.next()) {
					column = new Column();
					column.setColName(res.getString(1).toUpperCase());
					column.setDataType(res.getString(2).toUpperCase());
					columns.add(column);
				}
				table.setColumnList(columns);
				tables.add(table);
			}
			preparedStatement.close();
			conn.close();
		} catch (SQLException | DBConnectionError e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return tables;
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
			logger.error("DB Connection Error. Please Select all Necessary Feilds "
					+ e.getMessage());
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

		} catch (SQLException | DBConnectionError e) {
			logger.error(e.getMessage());
			throw new QueryExecutionException(e.getMessage());
		}
		return returnValue;
	}

	public static Connection getDB2Connection(DBDetails dbDetails)
			throws DBConnectionError {
		Connection conn = null;
		String driver = Keys.SQL_DRIVER_NAME;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(
					DBConnector.getDB2ConnectionString(dbDetails),
					dbDetails.getUserName(), dbDetails.getPassword());
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("DB2 Connection Error" + e.getMessage());
			throw new DBConnectionError("DB2 Connection Error" + e.getMessage());
		}
		return conn;
	}
}
