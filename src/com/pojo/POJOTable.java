package com.pojo;

import java.io.Serializable;

public class POJOTable implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tableName;
	private POJOColumn column;

	public POJOTable() {
		this.column = null;
		this.column = new POJOColumn("select Column");
	}

	public POJOTable(String _tableName, POJOColumn _column) {
		this.tableName = _tableName;
		this.column = _column;
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
