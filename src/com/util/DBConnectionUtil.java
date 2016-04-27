package com.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.entity.DBTypes;
import com.exceptions.DBConnectionError;
import com.extra.Keys;
import com.service.FileIO;

public class DBConnectionUtil extends MasterCommon {

	static final Logger logger = Logger.getLogger(DBUtil.class);

	public static boolean checkConnectivity(DBDetails dbDetails) {
		String testQuery = "";
		MasterCommon.updateDBCredentials(dbDetails.getDbSchema(), "",
				dbDetails.getDatabase(), dbDetails.getUserName(),
				dbDetails.getPassword());
		Connection connection = null;
		Statement statement;
		try {
			if (dbDetails.getDbType().equals(DBTypes.DB2.toString())) {
				testQuery = queriesProps
						.getProperty(Keys.QUERY_TEST_CONNECTION_DB2);
				connection = DBUtil.getDB2Connection(dbDetails);
			} else if (dbDetails.getDbType().equals(DBTypes.SQL.toString())) {
				testQuery = queriesProps
						.getProperty(Keys.QUERY_TEST_CONNECTION_SQL);
				connection = DBUtil.getSQLConnection(dbDetails);
			}
			if (connection != null) {
				statement = connection.createStatement();
				statement.execute(testQuery);
				statement.close();
				connection.close();
			}

		} catch (SQLException | ClassNotFoundException | DBConnectionError e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	public static List<DBDetails> getAllConnection() {
		return FileIO.getDBConnectionsFromText();
	}

	public static DBDetails getDBDetails(String selectedDBName) {
		for (DBDetails dbDetail : MasterCommon.dbConnection) {
			if (dbDetail.getConnectionName().equals(selectedDBName))
				return dbDetail;
		}
		return null;
	}

	public static void deleteDBConnection(String selectedDBName) {
		FileIO.deleteDBConnection(selectedDBName);
	}
}
