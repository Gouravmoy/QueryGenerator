package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.entity.Tables;
import com.pojo.InnerJoinRow;
import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;
import com.util.QueryIOUtil;

public class MasterCommon {

	public static String schemaName;
	public static String serverName;
	public static String userName;
	public static String password;
	public static String dbName;
	public static Properties queriesProps;

	public static List<POJOTable> listPojoTable = new ArrayList<>();
	public static ArrayList<POJOColumn> listPojoCols = new ArrayList<POJOColumn>();
	public static ArrayList<Tables> listTable = new ArrayList<Tables>();
	public static ArrayList<POJORow> selectRows = new ArrayList<POJORow>();
	public static ArrayList<InnerJoinRow> joinRows = new ArrayList<InnerJoinRow>();
	public static HashMap<Integer, String> tableHolder = new HashMap<Integer, String>();
	public static HashMap<Integer, String> innerJoinMap = new HashMap<Integer, String>();
	public static HashMap<Integer, String> selectQueryHolder = new HashMap<Integer, String>();
	public static String completeQuery;
	public static QueryIOUtil queryUtil;

	public static void updateDBCredentials(String schemaName2, String url,
			String dbName2, String userName2, String password2) {
		schemaName = schemaName2;
		serverName = url;
		userName = userName2;
		password = password2;
		dbName = dbName2;
	}

	public static String[] joinTypes = { "SELECT JOIN", "INNER JOIN",
			"LEFT OUTER JOIN", "RIGHT INNER JOIN" };
}
