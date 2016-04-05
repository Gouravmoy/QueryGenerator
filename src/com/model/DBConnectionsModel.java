package com.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.entity.DBDetails;

public class DBConnectionsModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Connection Names" };
	private ArrayList<DBDetails> dbDtlsList;

	public DBConnectionsModel(ArrayList<DBDetails> dbDtlsList) {
		super();
		this.dbDtlsList = dbDtlsList;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return dbDtlsList.size();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		Class obj = null;
		obj = getValueAt(0, column).getClass();
		return obj;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DBDetails dbDetail = dbDtlsList.get(rowIndex);
		return dbDetail.getConnectionName();
	}

}
