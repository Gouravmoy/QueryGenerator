package com.util;

import java.util.ArrayList;
import java.util.List;

import com.entity.Table;
import com.exceptions.NoJoinPossible;

public class InnerJoinUtil {

	static final String JOIN_TEXT = " INNER JOIN ";
	List<Table> tables;
	List<String> recursiveInnerJoins;

	public InnerJoinUtil(List<Table> tables) {
		super();
		this.recursiveInnerJoins = new ArrayList<>();
		this.tables = tables;
	}

	public String fetchInnerJoinQuery(String table1, String table2)
			throws NoJoinPossible {
		String joinCondition;
		String endPoinStatus;
		String startTable;
		String endTable;
		int dirrectRelStatus;
		recursiveInnerJoins = new ArrayList<>();
		endPoinStatus = checkIfTablesEndPoints(table1, table2);
		if (endPoinStatus.split("\\;").length == 2) {
			throw new NoJoinPossible("Cannot Join " + table1 + " and " + table2);
		} else {
			joinCondition = ifBridgeTables(table1, table2);
			if (!joinCondition.isEmpty()) {
				return joinCondition;
			}
			if (endPoinStatus.split("\\;").length == 1
					&& !endPoinStatus.isEmpty()) {
				endTable = endPoinStatus.replaceAll("\\;", "");
				if (table1.equals(endTable))
					startTable = table2;
				else
					startTable = table1;
			} else {
				dirrectRelStatus = checkIfDirrectRelExist(table1, table2);
				if (dirrectRelStatus == 1) {
					startTable = table1;
					endTable = table2;
				} else if (dirrectRelStatus == 2) {
					startTable = table2;
					endTable = table1;
				} else {
					startTable = table1;
					endTable = table2;
				}
			}
			recursiveInnerJoins.add(startTable + " AS " + startTable + "\n");
			recurseTables(startTable, endTable);
			for (String joinParts : recursiveInnerJoins)
				joinCondition += joinParts;
		}
		return joinCondition;
	}

	private String recurseTables(String startTable, String endtable) {
		List<String> fOKs;
		String tableKey;
		String refTableName;
		String refColName;
		String[] fOKSplit;
		for (Table table : tables) {
			if (startTable.equalsIgnoreCase(table.getTableName())) {
				fOKs = table.getForeignKeys();
				if (fOKs == null) {

				}
				for (String fOK : fOKs) {
					fOKSplit = fOK.split("\\|");
					tableKey = fOKSplit[0];
					refTableName = fOKSplit[1];
					refColName = fOKSplit[2];
					if (refTableName.equalsIgnoreCase(endtable)) {
						recursiveInnerJoins.add(JOIN_TEXT + endtable + " AS "
								+ endtable + " ON " + endtable + "."
								+ refColName + "=" + startTable + "."
								+ tableKey);
						return "";
					}
				}
			}
		}
		return "";
	}

	private int checkIfDirrectRelExist(String table1, String table2) {
		List<String> fOKs;
		String refTableName;
		String[] fOKSplit;
		for (Table table : tables) {
			if (table1.equalsIgnoreCase(table.getTableName())
					|| table2.equalsIgnoreCase(table.getTableName())) {
				fOKs = table.getForeignKeys();
				if (fOKs == null) {

				}
				for (String fOK : fOKs) {
					fOKSplit = fOK.split("\\|");
					refTableName = fOKSplit[1];
					if (table2.equalsIgnoreCase(table.getTableName())) {
						if (refTableName.equalsIgnoreCase(table1)) {
							return 2;
						}
					}
					if (refTableName.equalsIgnoreCase(table2)) {
						return 1;
					}
				}
			}
		}
		return 3;
	}

	private String ifBridgeTables(String table1, String table2) {
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
				joinCondition = table1 + " AS " + table1 + JOIN_TEXT
						+ bridgeTableName + " AS " + bridgeTableName + " ON "
						+ table1 + "." + table1PK + "=" + bridgeTableName + "."
						+ table1PK + JOIN_TEXT + table2 + " AS " + table2
						+ " ON " + table2 + "." + table2PK + "="
						+ bridgeTableName + "." + table2PK;
				break;
			}
		}
		return joinCondition;
	}

	private String checkIfTablesEndPoints(String table1, String table2) {
		String status = "";
		for (Table table : tables) {
			if (table1.equalsIgnoreCase(table.getTableName())
					&& table.getForeignKeys() == null) {
				status += table1 + ";";
			}
			if (table2.equalsIgnoreCase(table.getTableName())
					&& table.getForeignKeys() == null) {
				status += table2 + ";";
			}
		}
		return status;
	}
}
