package com.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JTextPane;

import com.controller.MasterCommon;
import com.pojo.InnerJoinRow;
import com.pojo.WhereRow;
import com.ui.Tesla6;

public class QueryUtil extends MasterCommon {
	final static String space = " ";
	final static String newLine = "\n";
	final static String singleQuote = "'";

	public static void updateQuery(JTextPane textArea, String status) {
		if (status.equals("DIRRECT")) {
			textArea.setText(QueryColorUtil.queryColorChange(Tesla6.queryToExecute));
		} else {
			QueryUtil.buildQuery();
			textArea.setText(QueryColorUtil.queryColorChange(MasterCommon.mainQuery.toString()));
		}
	}

	@SuppressWarnings("rawtypes")
	public static void buildQuery() {

		StringBuilder stringBuilder;
		ArrayList<String> selectStmts = new ArrayList<>();
		ArrayList<String> joinStmts = new ArrayList<>();
		ArrayList<String> whereStmts = new ArrayList<>();
		ArrayList<Integer> queryorder = new ArrayList<Integer>();

		for (int i = 1; i < MasterCommon.completeQuery.split("\\n").length - 1; i++) {
			selectStmts.add(MasterCommon.completeQuery.split("\\n")[i]);
		}

		mainQuery.setSelectStmts(selectStmts);

		Iterator it2 = innerJoinMap.entrySet().iterator();
		while (it2.hasNext()) {
			Map.Entry pair = (Map.Entry) it2.next();
			String key = pair.getKey() + "";
			queryorder.add(Integer.parseInt(key.split("\\|")[2]));
		}

		Collections.sort(queryorder);

		for (int i : queryorder) {
			Iterator it = innerJoinMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				String key = pair.getKey() + "";
				if (key.split("\\|")[2].equals(Integer.toString(i))) {
					joinStmts.add(pair.getValue() + "\n");
				}
			}
		}
		mainQuery.setJoinStmts(joinStmts);
		for (WhereRow whereRow : MasterCommon.whereRows) {

			if (!whereRow.getRelationalOp().equals("SELECT RELATION")) {
				stringBuilder = new StringBuilder();
				stringBuilder.append(whereRow.getWhereTableCol().getTableName() + "."
						+ whereRow.getWhereTableCol().getColumn().getColumnName() + space
						+ resolveCondition(whereRow.getRelationalOp()) + space + singleQuote
						+ whereRow.getConditionalValue() + singleQuote + space);
				if (whereRow.getAndOrCondition().equals("SELECT") || whereRow.getAndOrCondition().equals("END"))
					stringBuilder.append("");
				else
					stringBuilder.append(whereRow.getAndOrCondition());
				whereStmts.add(stringBuilder.toString());
			}
		}
		mainQuery.setWhereStmts(whereStmts);
	}

	private static String resolveCondition(String relationalOp) {
		if (relationalOp.equals("EQUAL"))
			return "=";
		if (relationalOp.equals("GREATER THAN"))
			return ">";
		if (relationalOp.equals("GREATER THAN EQ TO"))
			return ">=";
		if (relationalOp.equals("LESS THAN"))
			return "<";
		if (relationalOp.equals("LESS THAN EQ TO"))
			return "<=";
		if (relationalOp.equals("BETWEEN"))
			return "BETWEEN";
		if (relationalOp.equals("NOT EQUAL"))
			return "<>";
		return "";
	}

	@SuppressWarnings("rawtypes")
	public static void updateInnerJoinMap(List<InnerJoinRow> innerJoinRows) {

		boolean isReverse = false;
		boolean match = false;
		InnerJoinRow joinRow;
		if (innerJoinRows.size() == 0 /* || innerJoinRows.size() == 1 */) {
			return;
		}
		for (int i = 0; i < innerJoinRows.size(); i++) {
			isReverse = false;
			match = false;
			joinRow = innerJoinRows.get(i);

			if (joinRow.getInnerJoinType().equals("NO JOIN")) {
				String table1Name = joinRow.getJoinTable1().getTableName();
				innerJoinMap.put(table1Name + "|" + "" + "|" + i, getInnerJoinValue(joinRow, 5));
				joinRow.setStatus(true);
				System.out.println("Condition No Join");
				continue;
			}

			if (innerJoinRows.get(0).getInnerJoinType().equals("NO JOIN")) {
				return;
			}

			if (joinRow.getInnerJoinType().equals("SELECT JOIN"))
				break;
			String table1Name = joinRow.getJoinTable1().getTableName();
			String table2Name = joinRow.getJoinTable2().getTableName();

			if (innerJoinMap.containsKey(table1Name + "|" + table2Name + "|" + i)
					|| innerJoinMap.containsKey(table2Name + "|" + table1Name + "|" + i) || joinRow.isStatus()) {
				continue;
			} else if (table1Name.equals(table2Name)) {
				Iterator it = innerJoinMap.entrySet().iterator();
				if (innerJoinMap.size() == 0) {
					innerJoinMap.put(table1Name + "|" + table1Name + "|0", getInnerJoinValue(joinRow, 4));
					continue;
				}
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					String key = (String) pair.getKey();
					String mapTable = key.split("\\|")[0] + "|" + key.split("\\|")[1];
					if (mapTable.split("\\|")[0].equals(table1Name) || mapTable.split("\\|")[1].equals(table1Name)) {
						String prevVal = innerJoinMap.get(key);
						String newJoinCond = getInnerJoinValue(joinRow, 1);
						if (!prevVal.contains(newJoinCond))
							innerJoinMap.put(key, prevVal + "\n" + newJoinCond);
						match = true;
					}
				}
			} else {
				if (innerJoinMap.size() == 0) {
					innerJoinMap.put(table1Name + "|" + table2Name + "|" + i, getInnerJoinValue(joinRow, 2));
					joinRow.setStatus(true);
					System.out.println("Condition Map Size is zero - 2");
					continue;
				}
				Iterator it = innerJoinMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					String key = (String) pair.getKey();
					String mapTable = key.split("\\|")[0] + "|" + key.split("\\|")[1];
					String keyRow = key.split("\\|")[2];
					if (mapTable.equalsIgnoreCase(table2Name + "|" + table1Name)
							|| mapTable.equalsIgnoreCase(table1Name + "|" + table2Name)) {

						String prevVal = innerJoinMap.get(table1Name + "|" + table2Name + "|" + keyRow);
						if (prevVal == null) {
							prevVal = innerJoinMap.get(table2Name + "|" + table1Name + "|" + keyRow);
							isReverse = true;
						}
						String newJoinCond = getInnerJoinValue(joinRow, 1);
						if (!prevVal.contains(newJoinCond)) {
							if (isReverse == true) {
								innerJoinMap.put(table2Name + "|" + table1Name + "|" + keyRow,
										prevVal + "\n" + newJoinCond);
								joinRow.setStatus(true);
								match = true;
								System.out.println("Condition Existing in Reverse");
							} else {
								innerJoinMap.put(table1Name + "|" + table2Name + "|" + keyRow,
										prevVal + "\n" + newJoinCond);
								joinRow.setStatus(true);
								match = true;
								System.out.println("Condition Existing");
							}

						}
					}
					// Add modificaton here
				}
				if (match == false) {
					// Add modificaton here
					Iterator it2 = innerJoinMap.entrySet().iterator();
					HashMap<String, String> tempMap = new HashMap<String, String>();
					while (it2.hasNext()) {
						Map.Entry pair = (Map.Entry) it2.next();
						String key = (String) pair.getKey();
						String mapTable1 = key.split("\\|")[0];
						String mapTable2 = key.split("\\|")[1];
						// String keyRow = key.split("\\|")[2];
						if ((table1Name.equals(mapTable1) || table1Name.equals(mapTable2))) {
							tempMap.put(table1Name + "|" + table2Name + "|" + i, getInnerJoinValue(joinRow, 3));
							joinRow.setStatus(true);
							match = true;
							System.out.println("Condition One Table - Table 1 is Existing");
							break;
						}
						if ((table2Name.equals(mapTable1) || table2Name.equals(mapTable2))) {
							tempMap.put(table1Name + "|" + table2Name + "|" + i, getInnerJoinValue(joinRow, 4));
							joinRow.setStatus(true);
							match = true;
							System.out.println("Condition One Table - Table 2 is Existing");
							break;
						}
					}
					if (match == true) {
						joinRow.setStatus(true);
						innerJoinMap.putAll(tempMap);
					}
				}
				if (match == false) {
					// throw error
					System.out.println("Error Condition!");
				}
			}
		}

	}

	public static void reinitilizeJoinRows(ArrayList<InnerJoinRow> joinRows) {
		for (InnerJoinRow innerJoinRow : joinRows) {
			innerJoinRow.setStatus(false);
		}
	}

	private static String getInnerJoinValue(InnerJoinRow joinRow, int condition) {

		String value = "";
		String joinTbale1 = "";
		String joinTbale2 = "";
		String joinCol1 = "";
		String joinCol2 = "";
		String table1Name = joinRow.getJoinTable1().getTableName();
		String table2Name = joinRow.getJoinTable2().getTableName();
		String col1Name = joinRow.getJoinTable1().getColumn().getColumnName();
		String col2Name = joinRow.getJoinTable2().getColumn().getColumnName();
		String joinType = joinRow.getInnerJoinType();

		if (condition == 1) {
			return value = "AND" + space + table1Name + "." + col1Name + " = " + table2Name + "." + col2Name;
		}
		if (condition == 2) {
			return value = table1Name + space + table1Name + space + joinType + space + table2Name + space + table2Name
					+ space + "ON" + space + table1Name + "." + col1Name + " = " + table2Name + "." + col2Name;
		}
		if (condition == 3) {
			joinTbale1 = table2Name;
			joinTbale2 = table1Name;
			joinCol1 = col2Name;
			joinCol2 = col1Name;
		}
		if (condition == 4) {
			joinTbale1 = table1Name;
			joinTbale2 = table2Name;
			joinCol1 = col1Name;
			joinCol2 = col2Name;
		}
		if (condition == 5) {
			return value = table1Name + space + table1Name;
		}
		value = joinType + space + joinTbale1 + space + joinTbale1 + space + "ON" + space + joinTbale1 + "." + joinCol1
				+ " = " + joinTbale2 + "." + joinCol2;
		return value;
	}

	public static void removeLastFromInnerJoinMap() {
		innerJoinMap.remove(innerJoinMap.size() - 1);

		mainQuery.getJoinStmts().remove(mainQuery.getJoinStmts().size() - 1);
		buildQuery();
	}

	public static void removeLastFromWherenMap() {
		// mainQuery.getJoinStmts().remove(mainQuery.getJoinStmts().size() - 1);
		// buildQuery();
	}
}
