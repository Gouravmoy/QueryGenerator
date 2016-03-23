package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;

public class TableModel extends AbstractTableModel {

	int i = 0;
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "No.", "TableName", "ColumnName",
			"ElementName" };
	private List<POJORow> listRow = new ArrayList<>();

	public TableModel(List<POJORow> listRow) {
		this.listRow = listRow;
	}

	public void updateUI() {

		POJOColumn column = new POJOColumn(null);
		POJOTable pojoTable = new POJOTable(null, column);

		for (POJORow listRowddddd : listRow) {
			System.out.println("For " + (++i) + " Row in UI");
			System.out.println(listRowddddd.getTable().getTableName());
			System.out.println(listRowddddd.getTable().hashCode());
			System.out.println(listRowddddd.getTable().getColumn()
					.getColumnName());
			System.out.println(listRowddddd.getTable().getColumn().hashCode());
		}
		System.out.println("------------------------------------------------");

		// System.out.println("Column -> " + column.hashCode());

		POJORow row2 = null;
		row2 = new POJORow(pojoTable, "");
		this.listRow.add(row2);
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		Class obj = null;
		obj = getValueAt(0, column).getClass();
		return obj;
	}

	@Override
	public int getRowCount() {
		return listRow.size();
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		POJORow row = null;
		String tableName = "";
		POJOTable p = null;
		try {
			p = (POJOTable) value;
			tableName = p.getTableName();
		} catch (Exception e) {

		}

		row = listRow.get(rowIndex);
		switch (columnIndex) {
		case 1:
			row.setTable(new POJOTable(tableName, new POJOColumn("")));
			break;
		case 2:
			row.getTable().setColumn((POJOColumn) value);
			break;
		case 3:
			row.setElementname((String) value);
			break;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object returnValue = null;
		POJORow row = null;
		row = listRow.get(rowIndex);

		switch (columnIndex) {
		case 0:
			returnValue = rowIndex + 1;
			break;
		case 1:
			returnValue = row.getTable();
			break;
		case 2:
			returnValue = row.getTable().getColumn();
			break;
		case 3:
			returnValue = row.getElementname();
			break;
		}

		return returnValue;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
}
