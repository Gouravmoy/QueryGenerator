package com.service;

import java.util.ArrayList;

import javax.swing.table.TableColumn;

import com.celleditor.ColumnCellEditor;
import com.celleditor.DropDownCellEditor;
import com.celleditor.TableEditor;
import com.controller.MasterCommon;
import com.pojo.CaseRow;
import com.pojo.CoalesceRow;
import com.pojo.POJORow;
import com.renderer.ColumnCellRenderer;
import com.renderer.DropDownRenderer;
import com.renderer.TableCellRenderer;
import com.ui.TeslaTransforms;

public class TeslaTransFunctions extends TeslaTransforms {

	public TeslaTransFunctions(POJORow pojoRow) {
		super(pojoRow);
	}

	public static void initializeCaseTables() {

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

	public static void initializeCoalesceTables() {

		TableColumn table1Column = tableCoalesce.getColumn("TableOne");
		table1Column.setCellRenderer(new TableCellRenderer());
		table1Column.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col1Column = tableCoalesce.getColumn("ColumnOne");
		col1Column.setCellRenderer(new ColumnCellRenderer());
		col1Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));

		TableColumn joinTypeColumn = tableCoalesce.getColumn("Conditions");
		joinTypeColumn.setCellRenderer(new DropDownRenderer());
		joinTypeColumn.setCellEditor(new DropDownCellEditor(
				MasterCommon.stringCoalesceConditions));

	}

	public static String displayCaseQuery(ArrayList<CaseRow> caseRows) {
		String query = " Case \n";
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
						value = r.getTableTwo().getTableName() + "."
								+ r.getTableTwo().getColumn().getColumnName()
								+ " \n";
					} else {
						value = "'" + r.getValueString() + "' \n";
					}
					query = query + " ELSE " + value;
					break;
				} else {
					if (r.getValueString().length() == 0) {
						value = r.getTableTwo().getTableName() + "."
								+ r.getTableTwo().getColumn().getColumnName()
								+ " \n";
					} else {
						value = "'" + r.getValueString() + "' \n";
					}
					query = query + when + r.getTableOne().getTableName() + "."
							+ r.getTableOne().getColumn().getColumnName()
							+ " = '" + r.getConditionString() + "'" + then
							+ value;
				}
			}
		}
		return query = query + "End \n";

	}

	public static String displayCoalesceQuery(
			ArrayList<CoalesceRow> coalesceRows) {
		String tableColString = "";
		String query = "COALESCE (";
		for (int i = 0; i < coalesceRows.size(); i++) {
			CoalesceRow r2 = coalesceRows.get(i);
			tableColString = r2.getTableOne().getTableName() + "."
					+ r2.getTableOne().getColumn().getColumnName();
			if (!r2.getStringValue().equals("")) {
				query += r2.getStringValue().replace("#", tableColString);
			} else {
				query += tableColString;
			}
			query += ",";
		}
		return query;

	}

	public static String displayCoalesceQuery_1(String s, String query) {
		if (s == null) {
			query = query.substring(0, query.length() - 1);
			query += " )";
		} else {
			query += "'" + s + "')";
		}
		return query;

	}

	public static void editTableCreate(String rowType) {
		if (rowType.equals("Case")) {

		}
	}
}
