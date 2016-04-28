package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.controller.MasterCommon;
import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;

public class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "No.", "Conditions", "TableName",
			"ColumnName", "ElementName" };
	private List<POJORow> listRow = new ArrayList<>();

	public TableModel(List<POJORow> listRow) {
		this.listRow = listRow;
	}

	public void updateUI() {
		POJOColumn column = new POJOColumn(null);
		POJOTable pojoTable = new POJOTable(null, column);
		POJORow row2 = null;
		row2 = new POJORow(pojoTable, "");
		row2.setConditionString("");
		this.listRow.add(row2);
		this.fireTableDataChanged();
	}

	public POJORow updateUI1() {
		POJOColumn column = new POJOColumn("Transform");
		POJOTable pojoTable = new POJOTable("Transform", column);
		POJORow row2 = null;
		row2 = new POJORow(pojoTable, "");
		row2.setConditionString("");
		this.listRow.add(row2);
		this.fireTableDataChanged();
		return row2;
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
		row = listRow.get(rowIndex);
		switch (columnIndex) {
		case 1:
			row.setConditionString((String) value);
			break;
		case 2:
			p = (POJOTable) value;
			tableName = p.getTableName();
			row.setTable(new POJOTable(tableName, new POJOColumn("")));
			break;
		case 3:
			row.getTable().setColumn((POJOColumn) value);
			break;
		case 4:
			row.setElementname((String) value);
			break;
		}
		if (rowIndex < MasterCommon.selectRows.size()) {
			MasterCommon.selectRows.set(rowIndex, row);
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
			returnValue = row.getConditionString();
			break;
		case 2:
			returnValue = row.getTable();
			break;
		case 3:
			returnValue = row.getTable().getColumn();
			break;
		case 4:
			returnValue = row.getElementname();
			break;
		}
		return returnValue;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (listRow.get(rowIndex).getRowType() != null && columnIndex < 4) {
			return false;
		}
		return true;
	}
}
