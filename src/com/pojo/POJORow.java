package com.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class POJORow implements Serializable {
	private static final long serialVersionUID = 1L;
	POJOTable table;
	String elementname;
	ArrayList<CaseRow> caseRow = new ArrayList<CaseRow>();
	String rowType;

	public POJORow(POJOTable table, String elementname) {
		this.table = table;
		this.elementname = elementname;
	}

	public String getRowType() {
		return rowType;
	}

	public void setRowType(String rowType) {
		this.rowType = rowType;
	}

	public ArrayList<CaseRow> getCaseRow() {
		return caseRow;
	}

	public void setCaseRow(ArrayList<CaseRow> caseRow) {
		this.caseRow = caseRow;
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
