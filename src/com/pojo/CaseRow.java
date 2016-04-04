package com.pojo;

public class CaseRow {
	POJOTable tableOne;
	POJOTable tableTwo;
	String conditionString;
	String valueString;
	int caseCount;

	public CaseRow(POJOTable tableOne, POJOTable tableTwo,
			String conditionString, String valueString, int caseCount) {
		this.tableOne = tableOne;
		this.tableTwo = tableTwo;
		this.conditionString = conditionString;
		this.valueString = valueString;
		this.caseCount = caseCount;
	}

	public POJOTable getTableOne() {
		return tableOne;
	}

	public void setTableOne(POJOTable tableOne) {
		this.tableOne = tableOne;
	}

	public POJOTable getTableTwo() {
		return tableTwo;
	}

	public void setTableTwo(POJOTable tableTwo) {
		this.tableTwo = tableTwo;
	}

	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	@Override
	public String toString() {
		return "CaseRow [tableOne=" + tableOne + ", tableTwo=" + tableTwo
				+ ", conditionString=" + conditionString + ", valueString="
				+ valueString + ", caseCount=" + caseCount + "]";
	}

}
