package com.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.entity.TablesSelect;

public class TableNameModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Table Names", "Select" };
	private ArrayList<TablesSelect> tablenames;

	public TableNameModel(ArrayList<TablesSelect> tablesSelects) {
		super();
		this.tablenames = tablesSelects;
	}

	public void updateUI() {
		this.fireTableDataChanged();
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

	public void setValueAt(Object value, int row, int column) {
		tablenames.get(row).setSelected((boolean) value);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object returnValue = null;
		String tableName;
		tableName = tablenames.get(rowIndex).getTableName();

		switch (columnIndex) {
		case 0:
			returnValue = tableName;
			break;
		case 1:
			returnValue = tablenames.get(rowIndex).isSelected();
			break;
		}
		return returnValue;

	}

	public boolean isCellEditable(int row, int column) {
		return (column != 0);
	}

}
