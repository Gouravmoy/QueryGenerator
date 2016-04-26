package com.service;

import javax.swing.JTextPane;

import com.controller.MasterCommon;
import com.pojo.CaseRow;
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
				if (r.getCaseRow().size() == 0) {
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

				} else {
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
				}
			}
		}
		textArea.setText(QueryColorUtil.queryColorChange(
				MasterCommon.completeQuery).toUpperCase());
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}
