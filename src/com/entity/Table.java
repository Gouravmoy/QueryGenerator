package com.entity;

import java.util.List;

public class Table {

	String tableName;
	List<String> primarykeys;
	List<String> foreignKeys;

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", primarykeys=" + primarykeys + ", foreignKeys=" + foreignKeys + "]";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getPrimarykeys() {
		return primarykeys;
	}

	public void setPrimarykeys(List<String> primarykeys) {
		this.primarykeys = primarykeys;
	}

	public List<String> getForeignKeys() {
		return foreignKeys;
	}

	public void setForeignKeys(List<String> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

}
