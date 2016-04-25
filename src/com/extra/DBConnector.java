package com.extra;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.util.DBConnectionUtil;

public class DBConnector extends MasterCommon {

	public static String getSQLConnectionString() {
		DBDetails dbDetails = DBConnectionUtil
				.getDBDetails(MasterCommon.selectedDBName);
		return "jdbc:mysql://localhost/" + dbDetails.getDbSchema() + "?"
				+ "user=" + dbDetails.getUserName() + "&password="
				+ dbDetails.getPassword();
	}

	public static String getSQLConnectionString(DBDetails dbDetails) {

		return "jdbc:mysql://localhost/" + dbDetails.getDbSchema() + "?"
				+ "user=" + dbDetails.getUserName() + "&password="
				+ dbDetails.getPassword();
	}

	public static String getDB2ConnectionString(DBDetails dbDetails) {
		return "jdbc:db2://" + dbDetails.getHostName() + ":"
				+ dbDetails.getPort() + "/" + dbDetails.getDatabase();
	}
}
