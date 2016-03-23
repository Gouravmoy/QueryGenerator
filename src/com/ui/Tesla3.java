package com.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.celleditor.ColumnCellEditor;
import com.celleditor.DropDownCellEditor;
import com.celleditor.TableEditor;
import com.controller.MasterCommon;
import com.model.InnerJoinTableModel;
import com.pojo.InnerJoinRow;
import com.renderer.ColumnCellRenderer;
import com.renderer.DropDownRenderer;
import com.renderer.TableCellRenderer;
import com.util.ColsUtil;

public class Tesla3 extends JFrame {
	private static final long serialVersionUID = 1L;
	private InnerJoinTableModel innerJoinTableModel;
	private JScrollPane scrollpane;
	private JTable table;

	public Tesla3() {
		// ColsUtil.setPOJOClass();
		initialize();
	}

	private void initialize() {
		table = new JTable();
		List<InnerJoinRow> innerJoinRows = new ArrayList<InnerJoinRow>();
		scrollpane = new JScrollPane(table);
		innerJoinTableModel = new InnerJoinTableModel(innerJoinRows);
		table.setModel(innerJoinTableModel);

		initilizeColumns();

		table.setRowHeight(25);
		scrollpane = new JScrollPane(table);

		scrollpane.setPreferredSize(new Dimension(400, 200));
		getContentPane().add(scrollpane, BorderLayout.CENTER);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				innerJoinTableModel.updateUI();
			}

		});
		scrollpane.setRowHeaderView(btnAdd);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initilizeColumns() {
		TableColumn table1Column = table.getColumn("TableName1");
		table1Column.setCellRenderer(new TableCellRenderer());
		table1Column.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col1Column = table.getColumn("ColumnName1");
		col1Column.setCellRenderer(new ColumnCellRenderer());
		col1Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));

		TableColumn joinTypeColumn = table.getColumn("Join Type");
		joinTypeColumn.setCellRenderer(new DropDownRenderer());
		joinTypeColumn.setCellEditor(new DropDownCellEditor(
				MasterCommon.joinTypes));

		TableColumn table2Column = table.getColumn("TableName2");
		table2Column.setCellRenderer(new TableCellRenderer());
		table2Column.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col2Column = table.getColumn("ColumnName2");
		col2Column.setCellRenderer(new ColumnCellRenderer());
		col2Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));
	}
}
