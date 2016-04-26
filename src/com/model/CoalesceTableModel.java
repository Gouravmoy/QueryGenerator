package com.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.pojo.CoalesceRow;
import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;

public class CoalesceTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "Conditions", "TableOne", "ColumnOne" };
	private ArrayList<CoalesceRow> listRow = new ArrayList<CoalesceRow>();

	public void updateUI(POJORow pojoRow) {
		POJOColumn column1 = new POJOColumn(null);
		POJOTable pojoTable1 = new POJOTable(null, column1);
		CoalesceRow rowCoalesce = new CoalesceRow(pojoTable1, column1, "");
		this.listRow.add(rowCoalesce);
		this.fireTableDataChanged();

	}

	public void removeAll() {
		listRow.removeAll(listRow);
	}

	public CoalesceTableModel(ArrayList<CoalesceRow> listRow) {
		this.listRow = listRow;
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
		return listRow.size();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		Class obj = null;
		obj = getValueAt(0, column).getClass();
		return obj;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		CoalesceRow row = null;
		String tableName = "";
		POJOTable p = null;
		row = listRow.get(rowIndex);
		switch (columnIndex) {
		case 1:
			p = (POJOTable) value;
			tableName = p.getTableName();
			row.setTableOne(new POJOTable(tableName, new POJOColumn("")));
			break;
		case 2:
			row.getTableOne().setColumn((POJOColumn) value);
			break;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object returnValue = null;
		CoalesceRow row = null;
		row = listRow.get(rowIndex);

		switch (columnIndex) {
		case 0:
			returnValue = rowIndex + 1;
			break;
		case 1:
			returnValue = row.getTableOne();
			break;
		case 2:
			returnValue = row.getTableOne().getColumn();
		}

		return returnValue;

	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

}
