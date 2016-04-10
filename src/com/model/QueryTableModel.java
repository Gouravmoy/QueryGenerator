package com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.util.DBUtil;

public class QueryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	Vector cache;
	int colCount;
	String[] headers;

	Connection conn;
	Statement stmt;

	@SuppressWarnings("rawtypes")
	public QueryTableModel() {
		cache = new Vector();
	}

	public String getColumnName(int i) {
		return headers[i];
	}

	@Override
	public int getColumnCount() {
		return colCount;
	}

	@Override
	public int getRowCount() {
		return cache.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return ((String[]) cache.elementAt(row))[col];
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void executeQueryAndUI(String queryToExecute) throws ClassNotFoundException, SQLException {
		cache = new Vector();
		conn = DBUtil.getSQLConnection();
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(queryToExecute);
		ResultSetMetaData meta = rs.getMetaData();
		colCount = meta.getColumnCount();
		headers = new String[colCount];
		for (int h = 1; h <= colCount; h++) {
			headers[h - 1] = meta.getColumnName(h);
		}
		while (rs.next()) {
			String[] record = new String[colCount];
			for (int i = 0; i < colCount; i++) {
				record[i] = rs.getString(i + 1);
			}
			cache.addElement(record);
		}
		System.out.println("All Records Fetched");
		conn.close();
		fireTableChanged(null);
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

}
