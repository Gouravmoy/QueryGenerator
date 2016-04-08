package com.renderer;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class IconTextCellRemderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value instanceof String) {
			String column2 = (String) value;
			setText(column2);
		}
		setIcon(new ImageIcon("./resource/png/database_connect.png"));
		setToolTipText("Right Click to Connect");
		return this;
	}

}
