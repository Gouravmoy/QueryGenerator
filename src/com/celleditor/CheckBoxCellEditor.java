package com.celleditor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.ui.Tesla0;

public class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

	private static final long serialVersionUID = 1L;
	private String checkBoxValue;

	@Override
	public Object getCellEditorValue() {
		return checkBoxValue;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		if (value instanceof String) {
			this.checkBoxValue = (String) value;
		}
		JCheckBox checkBox = new JCheckBox(checkBoxValue);
		checkBox.addActionListener(this);
		return checkBox;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JCheckBox checkBox = (JCheckBox) event.getSource();
		System.out.println(checkBox.getText() + " - > " + checkBox.isSelected());

	}

}
