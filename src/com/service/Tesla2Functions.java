package com.service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;

import org.apache.log4j.Logger;

import com.controller.MasterCommon;
import com.entity.AutoJoinModel;
import com.exceptions.NoJoinPossible;
import com.pojo.CaseRow;
import com.pojo.CoalesceRow;
import com.pojo.InnerJoinRow;
import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;
import com.test.AutoSuggestInnerJoin;
import com.test.InnerJoinUtil;
import com.util.QueryColorUtil;

public class Tesla2Functions {

	static final Logger logger = Logger.getLogger(Tesla2Functions.class);

	public static void addAutoSuggesstJoins(JTextPane textArea) throws NoJoinPossible {
		String table1;
		String table2 = null;
		POJORow lastButOneRow = null;
		POJORow lastRow;
		List<String> joinStmts;
		StringBuilder builder;
		InnerJoinUtil innerJoinUtil;
		innerJoinUtil = new InnerJoinUtil(AutoSuggestInnerJoin.getTableMeta());
		joinStmts = new ArrayList<>();
		builder = new StringBuilder();
		if (!MasterCommon.selectRows.isEmpty()) {
			lastRow = MasterCommon.selectRows.get(MasterCommon.selectRows.size() - 1);
			table1 = lastRow.getTable().getTableName();
			builder.append("FROM \n");
			if (MasterCommon.selectRows.size() > 1) {
				lastButOneRow = MasterCommon.selectRows.get(MasterCommon.selectRows.size() - 2);
				table2 = lastButOneRow.getTable().getTableName();
			}
			if (lastButOneRow == null) {
			} else {
				try {
					if (!(MasterCommon.innerJoinedTables.contains(table1)
							&& MasterCommon.innerJoinedTables.contains(table2)))
						innerJoinUtil.fetchInnerJoinQuery(table1, table2);
					if (!MasterCommon.innerJoinedTables.contains(table1))
						MasterCommon.innerJoinedTables.add(table1);
					if (!MasterCommon.innerJoinedTables.contains(table2))
						MasterCommon.innerJoinedTables.add(table2);
					for (int i = 0; i < MasterCommon.autoJoinModels.size(); i++) {
						AutoJoinModel autoJoinModel = MasterCommon.autoJoinModels.get(i);
						MasterCommon.joinRows.add(new InnerJoinRow(
								new POJOTable(autoJoinModel.getTable1().toUpperCase(),
										new POJOColumn(autoJoinModel.getColumn1().toUpperCase())),
								new POJOTable(autoJoinModel.getTable2().toUpperCase(),
										new POJOColumn(autoJoinModel.getColumn2().toUpperCase())),
								"INNER JOIN"));
					}
				} catch (NoJoinPossible e) {
					logger.error(e);
					throw new NoJoinPossible(e.msg);
				}
			}
			for (int i = 0; i < MasterCommon.autoJoinModels.size(); i++) {
				builder.append(MasterCommon.autoJoinModels.get(i).getJoinStmt().toUpperCase());
			}
			MasterCommon.completeQuery += builder.toString();
			joinStmts.add(builder.toString());
			textArea.setText(QueryColorUtil.queryColorChange(MasterCommon.completeQuery).toUpperCase());
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}

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
						MasterCommon.completeQuery = MasterCommon.completeQuery + r.getTable().getTableName() + "."
								+ r.getTable().getColumn().getColumnName() + " as '" + r.getElementname() + "', \n";
					} else {
						MasterCommon.completeQuery = MasterCommon.completeQuery + r.getTable().getTableName() + "."
								+ r.getTable().getColumn().getColumnName() + ", \n";
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
									value = r1.getTableTwo().getTableName() + "."
											+ r1.getTableTwo().getColumn().getColumnName() + " \n";
								} else {
									value = "'" + r1.getValueString() + "' \n";
								}
								caseQuery = caseQuery + " ELSE " + value;

							} else {
								if (r1.getValueString().length() == 0) {
									value = r1.getTableTwo().getTableName() + "."
											+ r1.getTableTwo().getColumn().getColumnName() + " \n";
								} else {
									value = "'" + r1.getValueString() + "' \n";
								}
								caseQuery = caseQuery + " When " + r1.getTableOne().getTableName() + "."
										+ r1.getTableOne().getColumn().getColumnName() + " = '"
										+ r1.getConditionString() + "' Then " + value;
							}
						}
					}

					// caseQuery = caseQuery + " End Case \n as '" +
					// r.getElementname() + "' , \n";
					caseQuery = caseQuery + " End \n as '" + r.getElementname() + "' , \n";// My
																							// SQL
																							// Syntax
					MasterCommon.completeQuery = MasterCommon.completeQuery + caseQuery;
				} else if (r.getRowType().equals("Coalesce")) {
					String coalesceQuery = "COALESCE (";
					for (int k = 0; k < r.getCoalesceRow().size(); k++) {
						String tableColString = "";
						CoalesceRow r2 = r.getCoalesceRow().get(k);
						tableColString += r2.getTableOne().getTableName() + "."
								+ r2.getTableOne().getColumn().getColumnName();
						if (!r2.getStringValue().equals("")) {
							coalesceQuery += r2.getStringValue().replace("#", tableColString);
						} else {
							coalesceQuery += tableColString;
						}
						coalesceQuery += ",";
					}
					if (!r.getCoalesceString().equals("")) {
						coalesceQuery += "'" + r.getCoalesceString() + "'),";
					} else {
						coalesceQuery = coalesceQuery.substring(0, coalesceQuery.length() - 1);
						coalesceQuery += "),";
					}
					MasterCommon.completeQuery = MasterCommon.completeQuery + coalesceQuery;
				}
			}
		}
		textArea.setText(QueryColorUtil.queryColorChange(MasterCommon.completeQuery).toUpperCase());
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}
