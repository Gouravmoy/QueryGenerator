package com.pojo;

import java.io.Serializable;

public class WhereRow implements Serializable {

	private static final long serialVersionUID = 1L;
	POJOTable whereTableCol;
	String relationalOp;
	String conditionalValue;
	String andOrCondition;

	public WhereRow(POJOTable whereTableCol, String relationalOp,
			String conditionalValue, String andOrCondition) {
		super();
		this.whereTableCol = whereTableCol;
		this.relationalOp = relationalOp;
		this.conditionalValue = conditionalValue;
		this.andOrCondition = andOrCondition;
	}

	@Override
	public String toString() {
		return "WhereRow [whereTableCol=" + whereTableCol + ", relationalOp="
				+ relationalOp + ", conditionalValue=" + conditionalValue
				+ ", andOrCondition=" + andOrCondition + "]";
	}

	public POJOTable getWhereTableCol() {
		return whereTableCol;
	}

	public void setWhereTableCol(POJOTable whereTableCol) {
		this.whereTableCol = whereTableCol;
	}

	public String getRelationalOp() {
		return relationalOp;
	}

	public void setRelationalOp(String relationalOp) {
		this.relationalOp = relationalOp;
	}

	public String getConditionalValue() {
		return conditionalValue;
	}

	public void setConditionalValue(String conditionalValue) {
		this.conditionalValue = conditionalValue;
	}

	public String getAndOrCondition() {
		return andOrCondition;
	}

	public void setAndOrCondition(String andOrCondition) {
		this.andOrCondition = andOrCondition;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
