package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.controller.MasterCommon;
import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;
import com.ui.Tesla2;

public class TableModel extends AbstractTableModel {

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
		row = listRow.get(rowIndex);
		switch (columnIndex) {
		case 1:
			p = (POJOTable) value;
			tableName = p.getTableName();
			row.setTable(new POJOTable(tableName, new POJOColumn("")));
			String valueQuery = row.getTable().getTableName() + "."
					+ row.getTable().getColumn().getColumnName() + " as '"
					+ row.getElementname() + "' ,";
			Tesla2.displyQuery(rowIndex, valueQuery);
			break;
		case 2:
			row.getTable().setColumn((POJOColumn) value);
			String valueQuery1 = row.getTable().getTableName() + "."
					+ row.getTable().getColumn().getColumnName() + " as '"
					+ row.getElementname() + "' ,";
			Tesla2.displyQuery(rowIndex, valueQuery1);

			break;
		case 3:
			row.setElementname((String) value);
			String valueQuery2 = row.getTable().getTableName() + "."
					+ row.getTable().getColumn().getColumnName() + " as '"
					+ row.getElementname() + "' ,";
			Tesla2.displyQuery(rowIndex, valueQuery2);
			MasterCommon.selectRows.add(row);
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
