package com.service;

import javax.swing.JTextPane;

import com.controller.MasterCommon;
import com.pojo.CaseRow;
import com.pojo.CoalesceRow;
import com.pojo.POJORow;
import com.util.QueryColorUtil;

public class Tesla2Functions {

	public static void displyQuery(JTextPane textArea) {
		textArea.setText("");
		MasterCommon.completeQuery = "Select \n";
		for (int i = 0; i < MasterCommon.selectRows.size(); i++) {
			POJORow r = MasterCommon.selectRows.get(i);
			if (r.getTable().getTableName() == null) {
				MasterCommon.selectRows.remove(i);
			} else {
				if (r.getRowType() == null) {
					if (r.getElementname().length() > 0) {
						MasterCommon.completeQuery = MasterCommon.completeQuery
								+ r.getTable().getTableName() + "."
								+ r.getTable().getColumn().getColumnName()
								+ " as '" + r.getElementname() + "', \n";
					} else {
						MasterCommon.completeQuery = MasterCommon.completeQuery
								+ r.getTable().getTableName() + "."
								+ r.getTable().getColumn().getColumnName()
								+ ", \n";
					}

				} else if (r.getRowType().equals("Case")) {
					String caseQuery = "Case \n";
					for (int j = 0; j < r.getCaseRow().size(); j++) {
						String value = "";
						CaseRow r1 = r.getCaseRow().get(j);
						if (r1.getTableOne().getTableName() == null) {
							r.getCaseRow().remove(j);
						} else {
							if (r1.getConditionString().equals("ELSE")) {
								if (r1.getValueString().length() == 0) {
									value = r1.getTableTwo().getTableName()
											+ "."
											+ r1.getTableTwo().getColumn()
													.getColumnName() + " \n";
								} else {
									value = "'" + r1.getValueString() + "' \n";
								}
								caseQuery = caseQuery + " ELSE " + value;

							} else {
								if (r1.getValueString().length() == 0) {
									value = r1.getTableTwo().getTableName()
											+ "."
											+ r1.getTableTwo().getColumn()
													.getColumnName() + " \n";
								} else {
									value = "'" + r1.getValueString() + "' \n";
								}
								caseQuery = caseQuery
										+ " When "
										+ r1.getTableOne().getTableName()
										+ "."
										+ r1.getTableOne().getColumn()
												.getColumnName() + " = '"
										+ r1.getConditionString() + "' Then "
										+ value;
							}
						}
					}

					// caseQuery = caseQuery + " End Case \n as '" +
					// r.getElementname() + "' , \n";
					caseQuery = caseQuery + " End \n as '" + r.getElementname()
							+ "' , \n";// My
										// SQL
										// Syntax
					MasterCommon.completeQuery = MasterCommon.completeQuery
							+ caseQuery;
				} else if (r.getRowType().equals("Coalesce")) {
					String coalesceQuery = "COALESCE (";
					for (int k = 0; k < r.getCoalesceRow().size(); k++) {
						String tableColString = "";
						CoalesceRow r2 = r.getCoalesceRow().get(k);
						tableColString += r2.getTableOne().getTableName() + "."
								+ r2.getTableOne().getColumn().getColumnName();
						if (!r2.getStringValue().equals("")) {
							coalesceQuery += r2.getStringValue().replace("#",
									tableColString);
						} else {
							coalesceQuery += tableColString;
						}
						coalesceQuery += ",";
					}
					if (!r.getCoalesceString().equals("")) {
						coalesceQuery += "'" + r.getCoalesceString() + "'),";
					} else {
						coalesceQuery = coalesceQuery.substring(0,
								coalesceQuery.length() - 1);
						coalesceQuery += "),";
					}
					MasterCommon.completeQuery = MasterCommon.completeQuery
							+ coalesceQuery;
				}
			}
		}
		textArea.setText(QueryColorUtil.queryColorChange(
				MasterCommon.completeQuery).toUpperCase());
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}
