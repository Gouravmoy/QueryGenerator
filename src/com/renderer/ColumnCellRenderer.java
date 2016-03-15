package com.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.pojo.POJOColumn;

public class ColumnCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable jTable, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof POJOColumn) {
			POJOColumn column2 = (POJOColumn) value;
			setText(column2.getColumnName());
		}

		/*if (isSelected) {
			setBackground(jTable.getSelectionBackground());
		} else {
			setBackground(jTable.getSelectionForeground());
		}*/

		return this;
	}

}
