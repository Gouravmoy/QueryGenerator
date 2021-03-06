package com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.entity.DBTypes;
import com.exceptions.DBConnectionError;
import com.exceptions.TestQueryExecutionError;
import com.util.DBConnectionUtil;
import com.util.DBUtil;

public class QueryTableModel extends AbstractTableModel {

	static final Logger logger = Logger.getLogger(QueryTableModel.class);
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	Vector cache;
	int colCount;
	String[] headers;

	Connection conn;
	Statement stmt;
	DBDetails dbDetails;

	@SuppressWarnings("rawtypes")
	public QueryTableModel() {
		cache = new Vector();
	}

	@Override
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
	public int executeQueryAndUI(String queryToExecute)
			throws TestQueryExecutionError {
		int recordCount = 0;
		ResultSet rs;
		cache = new Vector();
		dbDetails = DBConnectionUtil.getDBDetails(MasterCommon.selectedDBName);
		try {
			if (dbDetails.getDbType().equals(DBTypes.SQL.toString()))
				conn = DBUtil.getSQLConnection();
			if (dbDetails.getDbType().equals(DBTypes.DB2.toString()))
				conn = DBUtil.getDB2Connection(dbDetails);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(queryToExecute);
			ResultSetMetaData meta = rs.getMetaData();
			colCount = meta.getColumnCount();
			headers = new String[colCount];
			for (int h = 1; h <= colCount; h++) {
				headers[h - 1] = meta.getColumnName(h);
			}
			while (rs.next()) {
				recordCount++;
				String[] record = new String[colCount];
				for (int i = 0; i < colCount; i++) {
					record[i] = rs.getString(i + 1);
				}
				cache.addElement(record);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException | DBConnectionError err) {
			logger.error(err);
			throw new TestQueryExecutionError("Error in Query Execution - "
					+ err.getMessage());
		}
		fireTableChanged(null);
		return recordCount;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

}
