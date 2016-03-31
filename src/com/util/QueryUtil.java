package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.controller.MasterCommon;
import com.pojo.InnerJoinRow;

public class QueryUtil extends MasterCommon {

	@SuppressWarnings("rawtypes")
	public static void buildQuery() {

		StringBuilder stringBuilder = new StringBuilder();
		ArrayList<String> joinStmts = new ArrayList<>();
		stringBuilder.append(completeQuery + "\n");

		for (int i = 0; i < innerJoinMap.size(); i++) {
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
	}

	@SuppressWarnings("rawtypes")
	public static void updateInnerJoinMap(List<InnerJoinRow> innerJoinRows) {

		boolean isReverse = false;
		boolean match = false;
		InnerJoinRow joinRow;
		if (innerJoinRows.size() == 0 || innerJoinRows.size() == 1) {
			return;
		}
		for (int i = 0; i < innerJoinRows.size(); i++) {
			isReverse = false;
			match = false;
			joinRow = innerJoinRows.get(i);
			if (joinRow.getInnerJoinType().equals("SELECT JOIN"))
				break;
			String table1Name = joinRow.getJoinTable1().getTableName();
			String table2Name = joinRow.getJoinTable2().getTableName();

			if (innerJoinMap.containsKey(table1Name + "|" + table2Name + "|"
					+ i)
					|| innerJoinMap.containsKey(table2Name + "|" + table1Name
							+ "|" + i)) {
				continue;
			} else {
				if (innerJoinMap.size() == 0) {
					innerJoinMap.put(table1Name + "|" + table2Name + "|" + i,
							getInnerJoinValue(joinRow, 2));
					System.out.println("Condition Map Size is zero - 2");
					continue;
				}
				Iterator it = innerJoinMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					String key = (String) pair.getKey();
					String mapTable = key.split("\\|")[0] + "|"
							+ key.split("\\|")[1];
					String keyRow = key.split("\\|")[2];
					if (mapTable
							.equalsIgnoreCase(table2Name + "|" + table1Name)
							|| mapTable.equalsIgnoreCase(table1Name + "|"
									+ table2Name)) {

						String prevVal = innerJoinMap.get(table1Name + "|"
								+ table2Name + "|" + keyRow);
						if (prevVal == null) {
							prevVal = innerJoinMap.get(table2Name + "|"
									+ table1Name + "|" + keyRow);
							isReverse = true;
						}
						String newJoinCond = getInnerJoinValue(joinRow, 1);
						if (!prevVal.contains(newJoinCond)) {
							if (isReverse == true) {
								innerJoinMap.put(table2Name + "|" + table1Name
										+ "|" + keyRow, prevVal + "\n"
										+ newJoinCond);
								match = true;
								System.out
										.println("Condition Existing in Reverse");
							} else {
								innerJoinMap.put(table1Name + "|" + table2Name
										+ "|" + keyRow, prevVal + "\n"
										+ newJoinCond);
								match = true;
								System.out.println("Condition Existing");
							}

						}
					}
				}
				if (match == false) {
					Iterator it2 = innerJoinMap.entrySet().iterator();
					HashMap<String, String> tempMap = new HashMap<String, String>();
					while (it2.hasNext()) {
						Map.Entry pair = (Map.Entry) it2.next();
						String key = (String) pair.getKey();
						String mapTable1 = key.split("\\|")[0];
						String mapTable2 = key.split("\\|")[1];
						// String keyRow = key.split("\\|")[2];
						if ((table1Name.equals(mapTable1) || table1Name
								.equals(mapTable2))) {
							tempMap.put(
									table1Name + "|" + table2Name + "|" + i,
									getInnerJoinValue(joinRow, 3));
							/*
							 * innerJoinMap.put(table1Name + "|" + table2Name +
							 * "|" + i, getInnerJoinValue(joinRow, 3));
							 */
							match = true;
							System.out
									.println("Condition One Table - Table 1 is Existing");
						}
						if ((table2Name.equals(mapTable1) || table2Name
								.equals(mapTable2))) {
							tempMap.put(
									table1Name + "|" + table2Name + "|" + i,
									getInnerJoinValue(joinRow, 3));
							/*
							 * innerJoinMap.put(table1Name + "|" + table2Name +
							 * "|" + i, getInnerJoinValue(joinRow, 4));
							 */
							match = true;
							System.out
									.println("Condition One Table - Table 2 is Existing");
						}
					}
					if (match == true) {
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

	private static String getInnerJoinValue(InnerJoinRow joinRow, int condition) {
		final String space = " ";
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
			return value = "AND" + space + table1Name + "." + col1Name + " = "
					+ table2Name + "." + col2Name;
		}
		if (condition == 2) {
			return value = table1Name + space + table1Name + space + joinType
					+ space + table2Name + space + table2Name + space + "ON"
					+ space + table1Name + "." + col1Name + " = " + table2Name
					+ "." + col2Name;
		}
		if (condition == 3) {
			joinTbale1 = table2Name;
			joinTbale2 = table1Name;
			joinCol1 = col2Name;
			joinCol1 = col2Name;
		}
		if (condition == 4) {
			joinTbale1 = table1Name;
			joinTbale2 = table2Name;
			joinCol1 = col1Name;
			joinCol2 = col2Name;
		}
		value = joinType + space + joinTbale1 + space + joinTbale1 + space
				+ "ON" + space + joinTbale1 + "." + joinCol1 + " = "
				+ joinTbale2 + "." + joinCol2;
		return value;
	}

	public static void removeLastFromInnerJoinMap() {
		innerJoinMap.remove(innerJoinMap.size() - 1);

		mainQuery.getJoinStmts().remove(mainQuery.getJoinStmts().size() - 1);
		buildQuery();
	}
}
