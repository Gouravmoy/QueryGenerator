package com.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.extra.Keys;
import com.service.FileIO;

public class DBConnectionUtil extends MasterCommon {

	public static boolean checkConnectivity(DBDetails dbDetails) {
		String testQuery = queriesProps
				.getProperty(Keys.QUERY_TEST_CONNECTION_SQL);

		MasterCommon.updateDBCredentials(dbDetails.getDbSchema(), "",
				dbDetails.getDatabase(), dbDetails.getUserName(),
				dbDetails.getPassword());
		Connection connection;
		Statement statement;

		try {
			connection = DBUtil.getSQLConnection();
			statement = connection.createStatement();
			statement.execute(testQuery);
		} catch (SQLException | ClassNotFoundException e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	public static ArrayList<DBDetails> getAllConnection() {
		return FileIO.getDBConnectionsFromText();
	}

	public static DBDetails getDBDetails(String selectedDBName,
			ArrayList<DBDetails> dbConnection) {
		for (DBDetails dbDetail : dbConnection) {
			if (dbDetail.getConnectionName().equals(selectedDBName))
				return dbDetail;
		}
		return null;
	}
}
