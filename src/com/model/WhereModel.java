package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.controller.MasterCommon;
import com.pojo.POJOColumn;
import com.pojo.POJOTable;
import com.pojo.WhereRow;

public class WhereModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "No.", "TableName", "ColumnName",
			"Condition", "Expression", "And/Or" };

	private List<WhereRow> whereRows = new ArrayList<WhereRow>();

	public WhereModel(List<WhereRow> whereRows) {
		super();
		this.whereRows = whereRows;
	}

	public void updateUI() {
		POJOColumn column = new POJOColumn(null);
		POJOTable table = new POJOTable(null, column);
		WhereRow row = null;
		row = new WhereRow(table, MasterCommon.relationalOps[0], "",
				MasterCommon.andOrs[0]);
		this.whereRows.add(row);
		this.fireTableDataChanged();
	}

	public void removeFromUI() {
		WhereRow row;
		if (whereRows.size() > 0) {
			row = whereRows.get(whereRows.size() - 1);
			if (!row.getRelationalOp().equals("SELECT RELATION")) {
				// QueryUtil.removeLastFromInnerJoinMap();
			}
			whereRows.remove(whereRows.size() - 1);
		}
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return whereRows.size();
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
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		WhereRow row = null;
		String tableName = "";
		POJOTable p = null;
		try {
			p = (POJOTable) value;
			tableName = p.getTableName();
		} catch (Exception e) {

		}
		row = whereRows.get(rowIndex);
		switch (columnIndex) {
		case 1:
			row.setWhereTableCol(new POJOTable(tableName, new POJOColumn("")));
			break;
		case 2:
			row.getWhereTableCol().setColumn((POJOColumn) value);
			break;
		case 3:
			row.setRelationalOp(new String((String) value));
			break;
		case 4:
			row.setConditionalValue(new String((String) value));
			break;
		case 5:
			row.setAndOrCondition(new String((String) value));
			break;
		}
		if (rowIndex < MasterCommon.whereRows.size()) {
			MasterCommon.whereRows.set(rowIndex, row);
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object returnValue = null;
		WhereRow row = null;
		row = whereRows.get(rowIndex);

		switch (columnIndex) {
		case 0:
			returnValue = rowIndex + 1;
			break;
		case 1:
			returnValue = row.getWhereTableCol();
			break;
		case 2:
			returnValue = row.getWhereTableCol().getColumn();
			break;
		case 3:
			returnValue = row.getRelationalOp();
			break;
		case 4:
			returnValue = row.getConditionalValue();
			break;
		case 5:
			returnValue = row.getAndOrCondition();
			break;
		}
		return returnValue;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

}
