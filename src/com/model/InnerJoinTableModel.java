package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.controller.MasterCommon;
import com.pojo.InnerJoinRow;
import com.pojo.POJOColumn;
import com.pojo.POJOTable;
import com.util.QueryUtil;

public class InnerJoinTableModel extends AbstractTableModel {

	static final Logger LOGGER = Logger.getLogger(InnerJoinTableModel.class);
	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "No.", "TableName1", "ColumnName1",
			"Join Type", "TableName2", "ColumnName2" };
	private List<InnerJoinRow> innerJoinRow = new ArrayList<>();

	public InnerJoinTableModel(List<InnerJoinRow> innerJoinRow) {
		this.innerJoinRow = innerJoinRow;
	}

	public void updateUI() {

		POJOColumn column1 = new POJOColumn(null);
		POJOColumn column2 = new POJOColumn(null);
		POJOTable pojoTable1 = new POJOTable(null, column1);
		POJOTable pojoTable2 = new POJOTable(null, column2);

		InnerJoinRow row;
		row = new InnerJoinRow(pojoTable1, pojoTable2,
				MasterCommon.joinTypes[0]);
		this.innerJoinRow.add(row);
		this.fireTableDataChanged();

	}

	public void removeFromUI() {
		InnerJoinRow row;

		if (!innerJoinRow.isEmpty()) {
			row = innerJoinRow.get(innerJoinRow.size() - 1);
			if (!("SELECT JOIN").equals(row.getInnerJoinType())) {
				QueryUtil.removeLastFromInnerJoinMap();
			}
			innerJoinRow.remove(innerJoinRow.size() - 1);
		}

		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return innerJoinRow.size();
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
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		InnerJoinRow row;
		String tableName = "";
		POJOTable p = null;
		try {
			p = (POJOTable) value;
			tableName = p.getTableName();
		} catch (Exception e) {
			LOGGER.error(e);
		}

		row = innerJoinRow.get(rowIndex);
		switch (columnIndex) {
		case 1:
			row.setJoinTable1(new POJOTable(tableName, new POJOColumn("")));
			break;
		case 2:
			row.getJoinTable1().setColumn((POJOColumn) value);
			break;
		case 3:
			row.setInnerJoinType(new String((String) value));
			break;
		case 4:
			row.setJoinTable2(new POJOTable(tableName, new POJOColumn("")));
			break;
		case 5:
			row.getJoinTable2().setColumn((POJOColumn) value);
			break;
		default:
			LOGGER.error("Error in Table Structure");
		}
		if (rowIndex < MasterCommon.joinRows.size()) {
			MasterCommon.joinRows.set(rowIndex, row);
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object returnValue = null;
		InnerJoinRow row;
		row = innerJoinRow.get(rowIndex);

		switch (columnIndex) {
		case 0:
			returnValue = rowIndex + 1;
			break;
		case 1:
			returnValue = row.getJoinTable1();
			break;
		case 2:
			returnValue = row.getJoinTable1().getColumn();
			break;
		case 3:
			returnValue = row.getInnerJoinType();
			break;
		case 4:
			returnValue = row.getJoinTable2();
			break;
		case 5:
			returnValue = row.getJoinTable2().getColumn();
			break;
		default:
			LOGGER.error("Error in Table Structure");
		}

		return returnValue;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

}
