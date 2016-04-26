package com.pojo;

import java.io.Serializable;

public class CoalesceRow implements Serializable {
	private static final long serialVersionUID = 1L;
	POJOTable tableOne;
	POJOColumn columnOne;
	String stringValue;

	public CoalesceRow(POJOTable tableOne, POJOColumn columnOne, String stringValue) {
		super();
		this.tableOne = tableOne;
		this.columnOne = columnOne;
		this.stringValue = stringValue;
	}

	public POJOTable getTableOne() {
		return tableOne;
	}

	public void setTableOne(POJOTable tableOne) {
		this.tableOne = tableOne;
	}

	public POJOColumn getColumnOne() {
		return columnOne;
	}

	public void setColumnOne(POJOColumn columnOne) {
		this.columnOne = columnOne;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	@Override
	public String toString() {
		return "CoalesceRow [tableOne=" + tableOne + ", columnOne=" + columnOne + ", stringValue=" + stringValue + "]";
	}

}
