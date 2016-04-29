package com.entity;

import java.io.Serializable;

public class AutoJoinModel implements Serializable {

	private static final long serialVersionUID = 1L;

	String table1;
	String table2;
	String column1;
	String column2;
	String joinStmt;
	boolean isAddedToJoinRow;

	public AutoJoinModel(String table1, String table2, String column1,
			String column2, String joinStmt) {
		super();
		this.table1 = table1;
		this.table2 = table2;
		this.column1 = column1;
		this.column2 = column2;
		this.joinStmt = joinStmt;
	}

	public boolean isAddedToJoinRow() {
		return isAddedToJoinRow;
	}

	public void setAddedToJoinRow(boolean isAddedToJoinRow) {
		this.isAddedToJoinRow = isAddedToJoinRow;
	}

	public AutoJoinModel() {
		super();
	}

	@Override
	public String toString() {
		return "AutoJoinModel [table1=" + table1 + ", table2=" + table2
				+ ", column1=" + column1 + ", column2=" + column2
				+ ", joinStmt=" + joinStmt + "]";
	}

	public String getJoinStmt() {
		return joinStmt;
	}

	public void setJoinStmt(String joinStmt) {
		this.joinStmt = joinStmt;
	}

	public String getTable1() {
		return table1;
	}

	public void setTable1(String table1) {
		this.table1 = table1;
	}

	public String getTable2() {
		return table2;
	}

	public void setTable2(String table2) {
		this.table2 = table2;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

}
