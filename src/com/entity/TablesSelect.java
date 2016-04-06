package com.entity;

public class TablesSelect {
	String tableName;
	boolean isSelected;

	@Override
	public String toString() {
		return "TablesSelect [tableName=" + tableName + ", isSelected="
				+ isSelected + "]";
	}

	public TablesSelect(String tableName, boolean isSelected) {
		super();
		this.tableName = tableName;
		this.isSelected = isSelected;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
