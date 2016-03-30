package com.pojo;

import java.io.Serializable;

public class POJOColumn implements Serializable {
	private static final long serialVersionUID = 1L;
	String columnName;

	public POJOColumn(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public String toString() {
		return this.columnName;
	}

}
