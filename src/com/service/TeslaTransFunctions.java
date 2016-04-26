package com.service;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.celleditor.ColumnCellEditor;
import com.celleditor.TableEditor;
import com.controller.MasterCommon;
import com.pojo.CaseRow;
import com.pojo.POJORow;
import com.renderer.ColumnCellRenderer;
import com.renderer.TableCellRenderer;
import com.ui.TeslaTransforms;
import com.util.QueryColorUtil;

public class TeslaTransFunctions extends TeslaTransforms {

	public TeslaTransFunctions(POJORow pojoRow) {
		super(pojoRow);
	}

	public static void initializeCaseTables(JTable tableCase) {

		TableColumn table1Column = tableCase.getColumn("TableOne");
		table1Column.setCellRenderer(new TableCellRenderer());
		table1Column.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col1Column = tableCase.getColumn("ColumnOne");
		col1Column.setCellRenderer(new ColumnCellRenderer());
		col1Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));

		TableColumn joinTypeColumn = tableCase.getColumn("TableTwo");
		joinTypeColumn.setCellRenderer(new TableCellRenderer());
		joinTypeColumn
				.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col2Column = tableCase.getColumn("ColumnTwo");
		col2Column.setCellRenderer(new ColumnCellRenderer());
		col2Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));

	}

	public static void initializeCoalesceTables(JTable tableCase) {

		TableColumn table1Column = tableCase.getColumn("TableOne");
		table1Column.setCellRenderer(new TableCellRenderer());
		table1Column.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col1Column = tableCase.getColumn("ColumnOne");
		col1Column.setCellRenderer(new ColumnCellRenderer());
		col1Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));

	}

	public static void displayQuery() {
		textPane.setText("Case \n");
		query = "Case \n";
		String when = " When ";
		String then = " Then ";
		for (int i = 0; i < caseRows.size(); i++) {
			String value = "";
			CaseRow r = caseRows.get(i);
			if (r.getTableOne().getTableName() == null) {
				caseRows.remove(i);
			} else {
				if (r.getConditionString().equals("ELSE")) {
					if (r.getValueString().length() == 0) {
						value = r.getTableTwo().getTableName() + "." + r.getTableTwo().getColumn().getColumnName()
								+ " \n";
					} else {
						value = "'" + r.getValueString() + "' \n";
					}
					query = query + " ELSE " + value;
					break;
				} else {
					if (r.getValueString().length() == 0) {
						value = r.getTableTwo().getTableName() + "." + r.getTableTwo().getColumn().getColumnName()
								+ " \n";
					} else {
						value = "'" + r.getValueString() + "' \n";
					}
					query = query + when + r.getTableOne().getTableName() + "."
							+ r.getTableOne().getColumn().getColumnName() + " = '" + r.getConditionString() + "'" + then
							+ value;
				}
			}
		}
		query = query + "End Case";
		textPane.setText(QueryColorUtil.queryColorChange(query));
	
		
	}

}
