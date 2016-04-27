package com.test;

import java.util.List;

import com.exceptions.NoJoinPossible;

public class InnerJoinUtil {

	public static String fetchInnerJoinQuery(String table1, String table2,
			List<Table> tables) throws NoJoinPossible {
		String joinCondition;
		int endPoinStatus;
		endPoinStatus = checkIfTablesEndPoints(table1, table2, tables);
		if (endPoinStatus == 2) {
			throw new NoJoinPossible("Cannot Join " + table1 + " and " + table2);
		} else {
			joinCondition = ifBridgeTables(table1, table2, tables);
			if (!joinCondition.isEmpty()) {
				return joinCondition;
			}
		}
		return joinCondition;
	}

	private static String ifBridgeTables(String table1, String table2,
			List<Table> tables) {
		String joinCondition = "";
		String table1PK = null;
		String table2PK = null;
		String bridgeTableName;
		for (Table table : tables) {
			bridgeTableName = table.getTableName();
			if (table1.equalsIgnoreCase(bridgeTableName))
				table1PK = table.getPrimarykeys().get(0);
			if (table2.equalsIgnoreCase(bridgeTableName))
				table2PK = table.getPrimarykeys().get(0);
		}
		for (Table table : tables) {
			bridgeTableName = table.getTableName();
			if (table.getPrimarykeys().size() == 2
					&& table.getPrimarykeys().contains(table1PK)
					&& table.getPrimarykeys().contains(table2PK)) {
				joinCondition = table1 + " AS " + table1 + " INNER JOIN "
						+ bridgeTableName + " AS " + bridgeTableName + " ON "
						+ table1 + "." + table1PK + "=" + bridgeTableName + "."
						+ table1PK + " INNER JOIN " + table2 + " AS " + table2
						+ " ON " + table2 + "." + table2PK + "="
						+ bridgeTableName + "." + table2PK;
				break;
			}
		}
		return joinCondition;
	}

	private static int checkIfTablesEndPoints(String table1, String table2,
			List<Table> tables) {
		int status = 0;
		for (Table table : tables) {
			if (table1.equalsIgnoreCase(table.getTableName())
					&& table.getForeignKeys() == null) {
				status++;
			}
			if (table2.equalsIgnoreCase(table.getTableName())
					&& table.getForeignKeys() == null) {
				status++;
			}
		}
		return status;
	}
}
