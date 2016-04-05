package com.util;

import java.util.ArrayList;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.extra.Keys;

public class DBConnectionUtil extends MasterCommon{

	public static boolean checkConnectivity(DBDetails dbDetails) {
		String testQuery = queriesProps.getProperty(Keys.QUERY_TEST_CONNECTION_SQL);
		return false;
	}

	public static ArrayList<DBDetails> getAllConnection() {
		return null;
	}

	public static ArrayList<String> getAllConnectionNames() {
		return null;
	}

}
