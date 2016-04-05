package com.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CheckBoxCellRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value instanceof String) {
			String column2 = (String) value;
			setText(column2);
		}

		return this;
	}

}
