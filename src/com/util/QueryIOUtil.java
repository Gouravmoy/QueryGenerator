package com.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.entity.AutoJoinModel;
import com.pojo.InnerJoinRow;
import com.pojo.POJORow;
import com.pojo.POJOTable;
import com.pojo.WhereRow;

public class QueryIOUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	ArrayList<POJORow> selectRows;
	List<POJOTable> selectTables;
	ArrayList<InnerJoinRow> conditionRows;
	ArrayList<WhereRow> whereRows;
	List<AutoJoinModel> autoJoinModels;
	String dbName;

	public List<AutoJoinModel> getAutoJoinModels() {
		return autoJoinModels;
	}

	public void setAutoJoinModels(List<AutoJoinModel> autoJoinModels) {
		this.autoJoinModels = autoJoinModels;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public ArrayList<WhereRow> getWhereRows() {
		return whereRows;
	}

	public void setWhereRows(ArrayList<WhereRow> whereRows) {
		this.whereRows = whereRows;
	}

	public ArrayList<POJORow> getSelectRows() {
		return selectRows;
	}

	public void setSelectRows(ArrayList<POJORow> selectRows) {
		this.selectRows = selectRows;
	}

	public ArrayList<InnerJoinRow> getConditionRows() {
		return conditionRows;
	}

	public void setConditionRows(ArrayList<InnerJoinRow> conditionRows) {
		this.conditionRows = conditionRows;
	}

	public List<POJOTable> getSelectTables() {
		return selectTables;
	}

	public void setSelectTables(List<POJOTable> selectTables) {
		this.selectTables = selectTables;
	}

}
