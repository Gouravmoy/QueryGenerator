package com.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.pojo.POJOTable;

public class TableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable jTable, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof POJOTable) {
			POJOTable table = (POJOTable) value;
			setText(table.getTableName());
		}

		/*
		 * if (isSelected) { setBackground(jTable.getSelectionBackground()); }
		 * else { setBackground(jTable.getSelectionForeground()); }
		 */
		return this;
	}

}
