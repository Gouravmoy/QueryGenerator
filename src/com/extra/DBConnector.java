package com.extra;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.util.DBConnectionUtil;

public class DBConnector extends MasterCommon {

	public static String getSQLConnectionString() {
		DBDetails dbDetails = DBConnectionUtil
				.getDBDetails(MasterCommon.selectedDBName);
		String connectionStr = "jdbc:mysql://localhost/"
				+ dbDetails.getDbSchema() + "?" + "user="
				+ dbDetails.getUserName() + "&password="
				+ dbDetails.getPassword();
		return connectionStr;
	}

	public static String getSQLConnectionString(DBDetails dbDetails) {

		String connectionStr = "jdbc:mysql://localhost/"
				+ dbDetails.getDbSchema() + "?" + "user="
				+ dbDetails.getUserName() + "&password="
				+ dbDetails.getPassword();
		return connectionStr;
	}

	public static String getDB2ConnectionString(DBDetails dbDetails) {
		String connStr = "jdbc:db2://" + dbDetails.getHostName() + ":"
				+ dbDetails.getPort() + "/" + dbDetails.getDatabase();
		return connStr;
	}
}
