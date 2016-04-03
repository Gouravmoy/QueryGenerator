package com.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pojo.InnerJoinRow;
import com.pojo.POJORow;
import com.pojo.POJOTable;

public class QueryIOUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	ArrayList<POJORow> selectRows;
	List<POJOTable> selectTables;
	ArrayList<InnerJoinRow> conditionRows;

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
