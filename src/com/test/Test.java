package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.exceptions.DBConnectionError;
import com.extra.Keys;
import com.util.DBUtil;

public class Test {

	public static void main(String args[]) {
		Properties props = new Properties();
		Connection conn = null;
		PreparedStatement preparedStatement;
		ResultSet res;
		try {
			props.load(new FileInputStream(new File(
					"./props/queries.properties")));
			String sql = props.getProperty(Keys.KEY_SQL_META_QUERY);
			conn = DBUtil.getSQLConnection();
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, "employee_details");
			res = preparedStatement.executeQuery();
			while (res.next()) {
				System.out.println(res.getString(1) + "->" + res.getString(2));
			}
			preparedStatement.close();
			conn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DBConnectionError e) {
			// TODO Auto-generated catch block
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

	}
}
