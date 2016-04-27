package com.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class POJORow implements Serializable {
	private static final long serialVersionUID = 1L;
	POJOTable table;
	String elementname;
	ArrayList<CaseRow> caseRow;
	ArrayList<CoalesceRow> coalesceRow;
	String coalesceString;
	String rowType;
	String conditionString;

	public POJORow(POJOTable table, String elementname) {
		this.table = table;
		this.elementname = elementname;
	}

	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}

	public String getCoalesceString() {
		return coalesceString;
	}

	public void setCoalesceString(String coalesceString) {
		this.coalesceString = coalesceString;
	}

	public ArrayList<CoalesceRow> getCoalesceRow() {
		return coalesceRow;
	}

	public void setCoalesceRow(ArrayList<CoalesceRow> coalesceRow) {
		this.coalesceRow = coalesceRow;
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
