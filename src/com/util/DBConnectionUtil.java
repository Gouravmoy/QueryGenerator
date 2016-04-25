package com.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.entity.DBTypes;
import com.exceptions.DBConnectionError;
import com.extra.Keys;
import com.service.FileIO;

public class DBConnectionUtil extends MasterCommon {

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
			statement = connection.createStatement();
			statement.execute(testQuery);
		} catch (SQLException | ClassNotFoundException | DBConnectionError e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static ArrayList<DBDetails> getAllConnection() {
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
