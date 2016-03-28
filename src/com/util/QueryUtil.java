package com.util;

import java.util.List;

import com.controller.MasterCommon;
import com.pojo.InnerJoinRow;

public class QueryUtil extends MasterCommon {

	public static StringBuilder buildQuery() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(completeQuery + "\n");
		for (int i = 0; i < innerJoinMap.size(); i++) {
			stringBuilder.append(innerJoinMap.get(i) + "\n");
		}
		return stringBuilder;
	}

	public static void updateInnerJoinMap(List<InnerJoinRow> innerJoinRows) {
		String innerJoinBuilder = "";
		InnerJoinRow joinRow;
		if (innerJoinRows.size() == 0 || innerJoinRows.size() == 1) {
			return;
		}
		for (int i = 0; i < innerJoinRows.size(); i++) {
			joinRow = innerJoinRows.get(i);
			if (joinRow.getInnerJoinType().equals("SELECT JOIN"))
				break;
			innerJoinBuilder = joinRow.getJoinTable1().getTableName() + " "
					+ joinRow.getInnerJoinType() + " "
					+ joinRow.getJoinTable2().getTableName() + " ON "
					+ joinRow.getJoinTable1().getTableName() + "."
					+ joinRow.getJoinTable1().getColumn().getColumnName() + "="
					+ joinRow.getJoinTable2().getTableName() + "."
					+ joinRow.getJoinTable2().getColumn().getColumnName();
			innerJoinMap.put(i, innerJoinBuilder);
		}

	}

	public static void removeLastFromInnerJoinMap() {
		innerJoinMap.remove(innerJoinMap.size() - 1);
	}
}
