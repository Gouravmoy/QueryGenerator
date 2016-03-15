package com.util;

import com.controller.MasterCommon;
import com.entity.Column;
import com.entity.Tables;
import com.pojo.POJOColumn;
import com.pojo.POJOTable;

public class ColsUtil extends MasterCommon {

	public static void getColumnsForTable(String tableName) {
		listPojoCols.clear();
		POJOColumn poColumn = null;
		for (Tables tableEntity : listTable) {
			if (tableEntity.getTableName().equalsIgnoreCase(tableName)) {
				for (Column colName : tableEntity.getColumnList()) {
					poColumn = new POJOColumn(colName.getColName());
					listPojoCols.add(poColumn);
				}

			}
		}
	}

	public static void setPOJOClass() {
		for (Tables tableEntity : listTable) {
			POJOTable pojoTable = new POJOTable();
			POJOColumn pojoColumn = new POJOColumn("Select Column");
			pojoTable.setTableName(tableEntity.getTableName());
			pojoTable.setColumn(pojoColumn);
			listPojoTable.add(pojoTable);
		}
	}
}
