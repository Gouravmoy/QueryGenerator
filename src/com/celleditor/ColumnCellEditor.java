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
import com.pojo.POJOColumn;
import com.util.ColsUtil;

public class ColumnCellEditor extends AbstractCellEditor implements
		TableCellEditor, ActionListener {
	private static final long serialVersionUID = 1L;
	private POJOColumn column;
	private List<POJOColumn> listColumn;

	public ColumnCellEditor(List<POJOColumn> listColumn) {
		this.listColumn = listColumn;
	}

	@Override
	public Object getCellEditorValue() {
		return this.column;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		if (value instanceof POJOColumn) {
			this.column = (POJOColumn) value;
		}
		ColsUtil.getColumnsForTable(MasterCommon.tableHolder.get(row));
		JComboBox<POJOColumn> comboCol = new JComboBox<>();

		for (POJOColumn cols : listColumn) {
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
		JComboBox<POJOColumn> comboLang = (JComboBox<POJOColumn>) event
				.getSource();
		this.column = (POJOColumn) comboLang.getSelectedItem();

	}

}
