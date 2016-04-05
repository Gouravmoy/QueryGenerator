package com.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.controller.MasterCommon;
import com.pojo.CaseRow;
import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;
import com.ui.TeslaCase;

public class CaseTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "When", "TableOne", "ColumnOne",
			"Condition", "Then", "TableTwo", "ColumnTwo", "Value" };
	private ArrayList<CaseRow> listRow = new ArrayList<CaseRow>();

	public void updateUI(POJORow pojoRow) {
		POJOColumn column1 = new POJOColumn(null);
		POJOColumn column2 = new POJOColumn(null);
		POJOTable pojoTable1 = new POJOTable(null, column1);
		POJOTable pojoTable2 = new POJOTable(null, column2);
		pojoRow.setCaseRow(listRow);
		CaseRow rowCase = new CaseRow(pojoTable1, pojoTable2, "", "");
		this.listRow.add(rowCase);
		this.fireTableDataChanged();

	}

	public void removeAll() {
		listRow.removeAll(listRow);
	}

	public CaseTableModel(ArrayList<CaseRow> listRow) {
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
		CaseRow row = null;
		String tableName = "";
		POJOTable p = null;
		POJOTable q = null;
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
		case 3:
			row.setConditionString(new String((String) value));
			break;
		case 5:
			q = (POJOTable) value;
			tableName = q.getTableName();
			row.setTableTwo(new POJOTable(tableName, new POJOColumn("")));
			break;
		case 6:
			row.getTableTwo().setColumn((POJOColumn) value);
			break;
		}
		if (rowIndex < MasterCommon.caseRows.size()) {
			TeslaCase.caseRows.set(rowIndex,row);
		}

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Object returnValue = null;
		CaseRow row = null;
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
			break;
		case 3:
			returnValue = row.getConditionString();
			break;
		case 4:
			returnValue = "Then";
			break;
		case 5:
			returnValue = row.getTableTwo();
			break;
		case 6:
			returnValue = row.getTableTwo().getColumn();
			break;
		case 7:
			returnValue = row.getValueString();
		}

		return returnValue;

	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

}
