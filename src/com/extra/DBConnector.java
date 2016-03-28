package com.extra;

import com.controller.MasterCommon;

public class DBConnector extends MasterCommon {

	public static String getSQLConnectionString() {
		String connectionStr = "jdbc:mysql://localhost/" + schemaName + "?"
				+ "user=" + userName + "&password=" + password;
		return connectionStr;
	}
}
