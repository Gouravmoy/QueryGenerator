package com.pojo;

import java.io.Serializable;

public class InnerJoinRow implements Serializable{
	private static final long serialVersionUID = 1L;
	
	POJOTable joinTable1;
	POJOTable joinTable2;
	String innerJoinType;

	public POJOTable getJoinTable1() {
		return joinTable1;
	}

	public void setJoinTable1(POJOTable joinTable1) {
		this.joinTable1 = joinTable1;
	}

	public POJOTable getJoinTable2() {
		return joinTable2;
	}

	public void setJoinTable2(POJOTable joinTable2) {
		this.joinTable2 = joinTable2;
	}

	public String getInnerJoinType() {
		return innerJoinType;
	}

	public void setInnerJoinType(String innerJoinType) {
		this.innerJoinType = innerJoinType;
	}

	@Override
	public String toString() {
		return "InnerJoinRow [joinTable1=" + joinTable1 + ", joinTable2="
				+ joinTable2 + ", innerJoinType=" + innerJoinType + "]";
	}

	public InnerJoinRow(POJOTable joinTable1, POJOTable joinTable2,
			String innerJoinType) {
		super();
		this.joinTable1 = joinTable1;
		this.joinTable2 = joinTable2;
		this.innerJoinType = innerJoinType;
	}

}
