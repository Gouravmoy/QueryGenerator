package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.entity.Column;
import com.entity.DBDetails;
import com.entity.Tables;
import com.extra.Keys;
import com.util.DBConnectionUtil;
import com.util.DBUtil;
import com.util.PropsLoader;

public class Controller {

	public static ArrayList<String> getTables(DBDetails dbDetails) {
		MasterCommon.updateDBCredentials(dbDetails.getDbSchema(),
				dbDetails.getHostName(), dbDetails.getDatabase(),
				dbDetails.getUserName(), dbDetails.getPassword());
		//PropsLoader.loadProps();
		PropsLoader loader = new PropsLoader();
		loader.loadProps();
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
			preparedStatement.setString(1, dbDetails.getDbSchema());
			res = preparedStatement.executeQuery();
			while (res.next()) {
				tableNames.add(res.getString(1).toUpperCase());
			}
			preparedStatement.close();
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
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
		ArrayList<Column> columns = new ArrayList<Column>();
		Column column = new Column();
		Tables table = new Tables();
		Connection conn = null;
		PreparedStatement preparedStatement;
		ResultSet res;

		DBDetails dbDetails = DBConnectionUtil.getDBDetails(
				MasterCommon.selectedDBName);

		try {
			String sql = MasterCommon.queriesProps
					.getProperty(Keys.KEY_SQL_META_QUERY);
			System.out.println("Connecting to Database");
			conn = DBUtil.getSQLConnection();
			System.out.println("Connected to Database");
			preparedStatement = conn.prepareStatement(sql);
			for (String tableName : selectedTableNames) {
				columns = new ArrayList<>();
				preparedStatement.setString(1, tableName);
				preparedStatement.setString(2, dbDetails.getDbSchema());
				res = preparedStatement.executeQuery();
				table = new Tables();
				table.setTableName(tableName);
				while (res.next()) {
					column = new Column();
					column.setColName(res.getString(1).toUpperCase());
					column.setDataType(res.getString(2).toUpperCase());
					columns.add(column);
				}
				table.setColumnList(columns);
				tables.add(table);
			}
			preparedStatement.close();
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
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
		return tables;
	}

}
