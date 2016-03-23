package com.celleditor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.pojo.POJOColumn;

public class DropDownCellEditor extends AbstractCellEditor implements
		TableCellEditor, ActionListener {

	private static final long serialVersionUID = 1L;
	private String dropDownString = "";
	private List<String> dropDownStringList = new ArrayList<String>();

	public DropDownCellEditor(String[] joinTypes) {
		this.dropDownStringList = Arrays.asList(joinTypes);
	}

	@Override
	public Object getCellEditorValue() {
		return this.dropDownString;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		if (value instanceof String) {
			this.dropDownString = (String) value;
		}

		JComboBox<String> comboCol = new JComboBox<String>();

		for (String cols : dropDownStringList) {
			comboCol.addItem(cols);
		}

		comboCol.addActionListener(this);

		if (isSelected) {
			comboCol.setBackground(table.getSelectionBackground());
		} else {
			comboCol.setBackground(table.getSelectionForeground());
		}
		return comboCol;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		@SuppressWarnings("unchecked")
		JComboBox<POJOColumn> dropDown = (JComboBox<POJOColumn>) event
				.getSource();
		this.dropDownString = (String) dropDown.getSelectedItem();
		System.out.println(dropDown.getSelectedItem().toString());

	}

}
