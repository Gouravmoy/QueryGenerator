package com.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.entity.DBDetails;

public class DBConnectionsModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Connection Names" };
	private List<DBDetails> dbDtlsList;

	public DBConnectionsModel(List<DBDetails> dbDtlsList) {
		super();
		this.dbDtlsList = dbDtlsList;
	}

	public void updateUI(DBDetails dbDetails) {
		this.dbDtlsList.add(dbDetails);
		this.fireTableDataChanged();
	}

	public void updateUI(List<DBDetails> details) {
		dbDtlsList.clear();
		dbDtlsList.addAll(details);
		this.fireTableDataChanged();
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
	@Override
	public Class getColumnClass(int column) {
		Class obj;
		obj = getValueAt(0, column).getClass();
		return obj;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DBDetails dbDetail = dbDtlsList.get(rowIndex);
		return dbDetail.getConnectionName();
	}

}
