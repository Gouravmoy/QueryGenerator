package com.celleditor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.controller.MasterCommon;
import com.pojo.POJOTable;
import com.util.ColsUtil;

public class TableEditor extends AbstractCellEditor implements TableCellEditor,
		ActionListener {
	private static final long serialVersionUID = 1L;
	private POJOTable table;
	private List<POJOTable> listTable;

	public TableEditor(List<POJOTable> listTable) {
		super();
		this.listTable = listTable;
	}

	@Override
	public Object getCellEditorValue() {
		return this.table;
	}

	@Override
	public Component getTableCellEditorComponent(JTable jTable, Object value,
			boolean isSelected, int row, int column) {
		if (value instanceof POJOTable) {
			this.table = (POJOTable) value;
		}

		JComboBox<POJOTable> comboCountry = new JComboBox<POJOTable>();

		for (POJOTable aTable : listTable) {
			comboCountry.addItem(aTable);
		}

		comboCountry.setSelectedItem(table);
		comboCountry.addActionListener(this);

		if (isSelected) {
			comboCountry.setBackground(jTable.getSelectionBackground());
		} else {
			comboCountry.setBackground(jTable.getSelectionForeground());
		}

		return comboCountry;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		@SuppressWarnings("unchecked")
		JComboBox<POJOTable> comboCountry = (JComboBox<POJOTable>) event
				.getSource();
		this.table = (POJOTable) comboCountry.getSelectedItem();
		ColsUtil.getColumnsForTable(table.getTableName());
		new ColumnCellEditor(MasterCommon.listPojoCols);
		System.out.println(comboCountry.getSelectedItem().toString());

	}

}
