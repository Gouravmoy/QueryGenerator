package com.extra;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.util.DBConnectionUtil;

public class DBConnector extends MasterCommon {

	public static String getSQLConnectionString() {
		DBDetails dbDetails = DBConnectionUtil.getDBDetails(
				MasterCommon.selectedDBName, MasterCommon.dbConnection);
		String connectionStr = "jdbc:mysql://localhost/"
				+ dbDetails.getDbSchema() + "?" + "user="
				+ dbDetails.getUserName() + "&password="
				+ dbDetails.getPassword();
		return connectionStr;
	}
}
