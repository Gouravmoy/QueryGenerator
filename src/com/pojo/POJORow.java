package com.pojo;

import java.io.Serializable;

public class POJORow implements Serializable {
	private static final long serialVersionUID = 1L;
	POJOTable table;
	String elementname;

	public POJORow(POJOTable table, String elementname) {
		this.table = table;
		this.elementname = elementname;
	}

	public String getElementname() {
		return elementname;
	}

	public void setElementname(String elementname) {
		this.elementname = elementname;
	}

	public POJOTable getTable() {
		return table;
	}

	public void setTable(POJOTable table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return "Row [table=" + table + "]";
	}

}
