package com.test;

import java.util.ArrayList;
import java.util.List;

import com.controller.MasterCommon;
import com.entity.AutoJoinModel;
import com.exceptions.NoJoinPossible;

public class InnerJoinUtil {

	static final String JOIN_TEXT = " INNER JOIN ";
	List<Table> tables;
	List<String> recursiveInnerJoins;

	AutoJoinModel autoJoinModel;

	public InnerJoinUtil(List<Table> tables) {
		super();
		this.recursiveInnerJoins = new ArrayList<>();
		this.tables = tables;
	}

	public void fetchInnerJoinQuery(String table1, String table2) throws NoJoinPossible {
		String endPoinStatus;
		String startTable;
		String endTable;
		int dirrectRelStatus;

		autoJoinModel = new AutoJoinModel();
		recursiveInnerJoins = new ArrayList<>();
		endPoinStatus = checkIfTablesEndPoints(table1, table2);
		if (endPoinStatus.split("\\;").length == 2) {
			throw new NoJoinPossible("Cannot Join " + table1 + " and " + table2);
		} else {
			if (ifBridgeTables(table1, table2)) {
				return;
			}
			if (endPoinStatus.split("\\;").length == 1 && !endPoinStatus.isEmpty()) {
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
		}
		return;
	}

	private String recurseTables(String startTable, String endtable) {
		List<String> fOKs;
		String tableKey;
		String refTableName;
		String refColName;
		String[] fOKSplit;
		String joinStmt;
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
						joinStmt = startTable + " AS " + startTable + JOIN_TEXT + endtable + " AS " + endtable + " ON "
								+ endtable + "." + refColName + "=" + startTable + "." + tableKey;
						recursiveInnerJoins.add(joinStmt);
						MasterCommon.autoJoinModels
								.add(new AutoJoinModel(startTable, endtable, tableKey, refColName, joinStmt));
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
			if (table1.equalsIgnoreCase(table.getTableName()) || table2.equalsIgnoreCase(table.getTableName())) {
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

	private boolean ifBridgeTables(String table1, String table2) {
		String table1PK = null;
		String table2PK = null;
		String bridgeTableName;
		String joinStmt1;
		String joinStmt2;
		AutoJoinModel joinModel1;
		AutoJoinModel joinModel2;
		for (Table table : tables) {
			bridgeTableName = table.getTableName();
			if (table1.equalsIgnoreCase(bridgeTableName))
				table1PK = table.getPrimarykeys().get(0);
			if (table2.equalsIgnoreCase(bridgeTableName))
				table2PK = table.getPrimarykeys().get(0);
		}
		for (Table table : tables) {
			bridgeTableName = table.getTableName();
			if (table.getPrimarykeys().size() == 2 && table.getPrimarykeys().contains(table1PK)
					&& table.getPrimarykeys().contains(table2PK)) {
				joinStmt1 = table1 + " AS " + table1 + JOIN_TEXT + bridgeTableName + " AS " + bridgeTableName + " ON "
						+ table1 + "." + table1PK + "=" + bridgeTableName + "." + table1PK + "\n";
				joinStmt2 = JOIN_TEXT + table2 + " AS " + table2 + " ON " + table2 + "." + table2PK + "="
						+ bridgeTableName + "." + table2PK;
				joinModel1 = new AutoJoinModel(bridgeTableName, table1, table1PK, table1PK, joinStmt1);
				joinModel2 = new AutoJoinModel(bridgeTableName, table2, table2PK, table2PK, joinStmt2);
				MasterCommon.autoJoinModels.add(joinModel1);
				MasterCommon.autoJoinModels.add(joinModel2);
				return true;
			}
		}
		return false;
	}

	private String checkIfTablesEndPoints(String table1, String table2) {
		String status = "";
		for (Table table : tables) {
			if (table1.equalsIgnoreCase(table.getTableName()) && table.getForeignKeys() == null) {
				status += table1 + ";";
			}
			if (table2.equalsIgnoreCase(table.getTableName()) && table.getForeignKeys() == null) {
				status += table2 + ";";
			}
		}
		return status;
	}
}
