package com.service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;

import org.apache.log4j.Logger;

import com.controller.MasterCommon;
import com.entity.AutoJoinModel;
import com.exceptions.NoJoinPossible;
import com.pojo.InnerJoinRow;
import com.pojo.POJOColumn;
import com.pojo.POJORow;
import com.pojo.POJOTable;
import com.test.AutoSuggestInnerJoin;
import com.test.InnerJoinUtil;
import com.ui.Tesla2;
import com.util.QueryColorUtil;

public class Tesla2Functions extends Tesla2 {

	static final Logger LOGGER2 = Logger.getLogger(Tesla2Functions.class);

	public static void addAutoSuggesstJoins(JTextPane textArea)
			throws NoJoinPossible {
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
			lastRow = MasterCommon.selectRows.get(MasterCommon.selectRows
					.size() - 1);
			table1 = lastRow.getTable().getTableName();
			builder.append("FROM \n");
			if (MasterCommon.selectRows.size() > 1) {
				lastButOneRow = MasterCommon.selectRows
						.get(MasterCommon.selectRows.size() - 2);
				table2 = lastButOneRow.getTable().getTableName();
			}
			for (String table : MasterCommon.innerJoinedTables) {
				if (innerJoinUtil.checkIfDirrectRelExist(table1, table) != 3) {
					table2 = table;
					break;
				}
			}
			if (lastButOneRow == null) {
			} else {
				try {
					if (!(MasterCommon.innerJoinedTables.contains(table1) && MasterCommon.innerJoinedTables
							.contains(table2))) {
						innerJoinUtil.fetchInnerJoinQuery(table1, table2);
						if (!MasterCommon.innerJoinedTables.contains(table1))
							MasterCommon.innerJoinedTables.add(table1);
						if (!MasterCommon.innerJoinedTables.contains(table2))
							MasterCommon.innerJoinedTables.add(table2);
						for (int i = 0; i < MasterCommon.autoJoinModels.size(); i++) {
							AutoJoinModel autoJoinModel = MasterCommon.autoJoinModels
									.get(i);
							if (!autoJoinModel.isAddedToJoinRow()) {
								MasterCommon.joinRows.add(new InnerJoinRow(
										new POJOTable(autoJoinModel.getTable1()
												.toUpperCase(), new POJOColumn(
												autoJoinModel.getColumn1()
														.toUpperCase())),
										new POJOTable(autoJoinModel.getTable2()
												.toUpperCase(), new POJOColumn(
												autoJoinModel.getColumn2()
														.toUpperCase())),
										"INNER JOIN"));
								autoJoinModel.setAddedToJoinRow(true);
							}
						}
					}
				} catch (NoJoinPossible e) {
					LOGGER2.error(e);
					throw new NoJoinPossible(e.msg);
				}
			}
			for (int i = 0; i < MasterCommon.autoJoinModels.size(); i++) {
				builder.append(MasterCommon.autoJoinModels.get(i).getJoinStmt()
						.toUpperCase());
			}
			MasterCommon.completeQuery += builder.toString();
			joinStmts.add(builder.toString());
			textArea.setText(QueryColorUtil.queryColorChange(
					MasterCommon.completeQuery).toUpperCase());
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}

	public static void refreshAutoJoinUI(JTextPane textArea) {
		StringBuilder builder = new StringBuilder();
		builder.append("FROM \n");
		for (int i = 0; i < MasterCommon.autoJoinModels.size(); i++) {
			builder.append(MasterCommon.autoJoinModels.get(i).getJoinStmt()
					.toUpperCase());
		}
		MasterCommon.completeQuery += builder.toString();
		textArea.setText(QueryColorUtil.queryColorChange(
				MasterCommon.completeQuery).toUpperCase());
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	public Tesla2Functions(ArrayList<String> tables) {
		super(tables);
	}

	public static void displyQuery() {
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
				}

				else if (r.getRowType().equals("Case")) {
					String query = TeslaTransFunctions.displayCaseQuery(r
							.getCaseRow());
					MasterCommon.completeQuery = MasterCommon.completeQuery
							+ query + " as '" + r.getElementname() + "' , \n ";
					textArea.setText(query);

				} else if (r.getRowType().equals("Coalesce")) {
					String query = "";
					query = TeslaTransFunctions.displayCoalesceQuery(r
							.getCoalesceRow());
					query = TeslaTransFunctions.displayCoalesceQuery_1(
							r.getCoalesceString(), query);
					MasterCommon.completeQuery = MasterCommon.completeQuery
							+ query + " as '" + r.getElementname() + "' , \n";
					textArea.setText(query);
				}
			}
		}
		textArea.setText(QueryColorUtil.queryColorChange(
				MasterCommon.completeQuery).toUpperCase());
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}
