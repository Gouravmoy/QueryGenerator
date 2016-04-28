package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.entity.DBDetails;
import com.entity.Query;
import com.entity.Tables;
import com.pojo.InnerJoinRow;
import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;
import com.pojo.WhereRow;
import com.util.QueryIOUtil;

public class MasterCommon {

	public static String schemaName;
	public static String serverName;
	public static String userName;
	public static String password;
	public static String dbName;
	public static Properties queriesProps;
	public static Properties keywordsProps;

	public static List<POJOTable> listPojoTable = new ArrayList<>();
	public static ArrayList<POJOColumn> listPojoCols = new ArrayList<POJOColumn>();
	public static ArrayList<Tables> listTable = new ArrayList<Tables>();
	public static ArrayList<POJORow> selectRows = new ArrayList<POJORow>();
	public static ArrayList<InnerJoinRow> joinRows = new ArrayList<InnerJoinRow>();
	public static ArrayList<POJORow> caseRows = new ArrayList<POJORow>();
	public static ArrayList<WhereRow> whereRows = new ArrayList<WhereRow>();
	public static HashMap<Integer, String> tableHolder = new HashMap<Integer, String>();
	public static HashMap<String, String> innerJoinMap = new HashMap<String, String>();
	public static Query mainQuery = new Query();
	public static String completeQuery = "";
	public static String completeCaseQuery = "";
	public static String masterPath = System.getProperty("user.home")
			+ "//Desktop//Query//";
	public static QueryIOUtil queryUtil;
	public static String selectedDBName;

	public static List<DBDetails> dbConnection = new ArrayList<DBDetails>();

	public static void updateDBCredentials(String schemaName2, String url,
			String dbName2, String userName2, String password2) {
		schemaName = schemaName2;
		serverName = url;
		userName = userName2;
		password = password2;
		dbName = dbName2;
	}

	public static String[] joinTypes = { "SELECT JOIN", "INNER JOIN",
			"LEFT OUTER JOIN", "RIGHT INNER JOIN", "NO JOIN" };
	public static String[] relationalOps = { "SELECT RELATION", "EQUAL",
			"GREATER THAN", "GREATER THAN EQ TO", "LESS THAN",
			"LESS THAN EQ TO", "BETWEEN", "NOT EQUAL" };
	public static String[] andOrs = { "SELECT", "AND", "OR", "END" };
	public static String[] stringCoalesceConditions = { "", "LTRIM(#)",
			"RTRIM(#)", "LTRIM(RTRIM(#))" };
	public static String[] stringConditions = { "", "AVG(#)", "COUNT(#)",
			"SUM(#)", "LTRIM(#)", "RTRIM(#)", "LTRIM(RTRIM(#))" };

	public static void reInitilizeMasterCommon() {
		listPojoTable.clear();
		listPojoCols.clear();
		listTable.clear();
		selectRows.clear();
		joinRows.clear();
		caseRows.clear();
		whereRows.clear();
		tableHolder.clear();
		innerJoinMap.clear();
		mainQuery = new Query();
		completeQuery = "";
		completeCaseQuery = "";
		queryUtil = new QueryIOUtil();
		selectedDBName = "";
	}

}
