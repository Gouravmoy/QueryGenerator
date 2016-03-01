package com.controller;

import java.util.Properties;

public class MasterCommon {

	public static String schemaName;
	public static String serverName;
	public static String userName;
	public static String password;
	public static String dbName;
	
	public static Properties queriesProps;

	public static void updateDBCredentials(String schemaName2, String url,
			String dbName2, String userName2, String password2) {
		schemaName = schemaName2;
		serverName = url;
		userName = userName2;
		password = password2;
		dbName = dbName2;
	}
}
