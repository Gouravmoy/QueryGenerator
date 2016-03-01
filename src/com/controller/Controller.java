package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.entity.Tables;
import com.extra.Keys;
import com.util.DBUtil;
import com.util.PropsLoader;

public class Controller {

	public static ArrayList<String> getTables(String schemaName, String url,
			String dbName, String userName, String password) {
		MasterCommon.updateDBCredentials(schemaName, url, dbName, userName,
				password);
		PropsLoader.loadProps();
		ArrayList<String> tableNames = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement preparedStatement;
		ResultSet res;
		try {
			String sql = MasterCommon.queriesProps
					.getProperty(Keys.KEY_SQL_TABLE_LIST);
			System.out.println("Connecting to Database");
			conn = DBUtil.getSQLConnection();
			System.out.println("Connected to Database");
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, schemaName);
			res = preparedStatement.executeQuery();
			while (res.next()) {
				tableNames.add(res.getString(1));
			}
			preparedStatement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return tableNames;
	}

	public static ArrayList<Tables> getTablesMetaInfo(
			ArrayList<String> selectedTableNames) {
		ArrayList<Tables> tables = new ArrayList<Tables>();
		return tables;
	}

}
