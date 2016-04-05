package com.entity;

import java.util.ArrayList;

public class Query {

	ArrayList<String> selectStmts;
	ArrayList<String> joinStmts;
	ArrayList<String> whereStmts;

	public Query() {
		super();
		selectStmts = new ArrayList<String>();
		joinStmts = new ArrayList<String>();
		whereStmts = new ArrayList<String>();
	}

	public ArrayList<String> getSelectStmts() {
		return selectStmts;
	}

	public void setSelectStmts(ArrayList<String> selectStmts) {
		this.selectStmts = selectStmts;
	}

	public ArrayList<String> getJoinStmts() {
		return joinStmts;
	}

	public void setJoinStmts(ArrayList<String> joinStmts) {
		this.joinStmts = joinStmts;
	}

	public ArrayList<String> getWhereStmts() {
		return whereStmts;
	}

	public void setWhereStmts(ArrayList<String> whereStmts) {
		this.whereStmts = whereStmts;
	}

	@Override
	public String toString() {
		final String newLine = "\n";
		StringBuilder query = new StringBuilder();
		query.append("SELECT " + newLine);
		for (String selectStmt : selectStmts) {
			query.append(selectStmt  + newLine);
		}
		query.append("FROM " + newLine);
		for (String joinStmt : joinStmts) {
			query.append(joinStmt + newLine);
		}
		query.append("WHERE " + newLine);
		for (String whereStmt : whereStmts) {
			query.append(whereStmt + newLine);
		}
		return query.toString();
	}
}
