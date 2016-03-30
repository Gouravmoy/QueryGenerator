package com.util;

import java.util.ArrayList;
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

		Iterator it = innerJoinMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			joinStmts.add(pair.getValue() + "\n");
		}
		mainQuery.setJoinStmts(joinStmts);
	}

	@SuppressWarnings("rawtypes")
	public static void updateInnerJoinMap(List<InnerJoinRow> innerJoinRows) {

		boolean isReverse = false;
		InnerJoinRow joinRow;
		if (innerJoinRows.size() == 0 || innerJoinRows.size() == 1) {
			return;
		}
		for (int i = 0; i < innerJoinRows.size(); i++) {
			isReverse = false;
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
					System.out.println("Condition 2");
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
							} else {
								innerJoinMap.put(table1Name + "|" + table2Name
										+ "|" + keyRow, prevVal + "\n"
										+ newJoinCond);
							}
						}
						System.out.println("Condition 1");
					} else {
						innerJoinMap.put(table1Name + "|" + table2Name + "|"
								+ i, getInnerJoinValue(joinRow, 3));
						System.out.println("Condition 3");
					}
				}

				/*
				 * if (innerJoinMap.containsKey(table1Name + "|" + table2Name)
				 * || innerJoinMap.containsKey(table2Name + "|" + table1Name)) {
				 * String prevVal = innerJoinMap.get(table1Name + "|" +
				 * table2Name); innerJoinMap.put(table1Name + "|" + table2Name,
				 * prevVal + "\n" + getInnerJoinValue(joinRow, 1)); } else { if
				 * (innerJoinMap.size() == 0) { innerJoinMap.put(table1Name +
				 * "|" + table2Name, getInnerJoinValue(joinRow, 2)); } else {
				 * innerJoinMap.put(table1Name + "|" + table2Name,
				 * getInnerJoinValue(joinRow, 3)); } }
				 */
			}
		}

	}

	private static String getInnerJoinValue(InnerJoinRow joinRow, int condition) {
		final String space = " ";
		String value = "";
		String table1Name = joinRow.getJoinTable1().getTableName();
		String table2Name = joinRow.getJoinTable2().getTableName();
		String col1Name = joinRow.getJoinTable1().getColumn().getColumnName();
		String col2Name = joinRow.getJoinTable2().getColumn().getColumnName();
		String joinType = joinRow.getInnerJoinType();

		if (condition == 1) {
			value = "AND" + space + table1Name + "." + col1Name + " = "
					+ table2Name + "." + col2Name;
		}
		if (condition == 2) {
			value = table1Name + space + table1Name + space + joinType + space
					+ table2Name + space + table2Name + space + "ON" + space
					+ table1Name + "." + col1Name + " = " + table2Name + "."
					+ col2Name;
		}
		if (condition == 3) {

		}
		return value;
	}

	public static void removeLastFromInnerJoinMap() {
		innerJoinMap.remove(innerJoinMap.size() - 1);
		
		mainQuery.getJoinStmts().remove(mainQuery.getJoinStmts().size() - 1);
		buildQuery();
	}
}
