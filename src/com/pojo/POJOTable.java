package com.pojo;

public class POJOTable {
	private String tableName;
	private POJOColumn column;

	public POJOTable() {
		this.column = new POJOColumn("Select Column");
	}

	public POJOTable(String tableName, POJOColumn column) {
		this.tableName = tableName;
		this.column = column;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public POJOColumn getColumn() {
		return column;
	}

	public void setColumn(POJOColumn column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return this.tableName;
	}

}
