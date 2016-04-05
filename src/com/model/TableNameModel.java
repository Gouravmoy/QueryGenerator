package com.model;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

public class TableNameModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Table Names" };
	private ArrayList<String> tablenames;

	public TableNameModel(ArrayList<String> tablenames) {
		super();
		this.tablenames = tablenames;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return tablenames.size();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		Class obj = null;
		obj = getValueAt(0, column).getClass();
		return obj;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String tableName;
		tableName = tablenames.get(rowIndex);
		return tableName.toUpperCase();
		/*
		 * JCheckBox checkBox = new JCheckBox(tablenames.get(rowIndex)); return
		 * checkBox;
		 */
	}

}
