package com.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.celleditor.ColumnCellEditor;
import com.celleditor.TableEditor;
import com.controller.Controller;
import com.controller.MasterCommon;
import com.entity.Tables;
import com.model.TableModel;
import com.pojo.POJORow;
import com.renderer.ColumnCellRenderer;
import com.renderer.TableCellRenderer;
import com.util.ColsUtil;

public class Tesla2 extends JFrame {

	private TableModel tableModel;
	private JScrollPane scrollpane;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<Tables> tables;
	private JTable table;

	// private JTable table;

	public Tesla2(ArrayList<String> tables) {
		this.tables = Controller.getTablesMetaInfo(tables);
		MasterCommon.listTable.clear();
		MasterCommon.listTable.addAll(this.tables);
		ColsUtil.setPOJOClass();
		initialize();

	}

	private void initialize() {
		table = new JTable();
		List<POJORow> listRow = new ArrayList<>();
		scrollpane = new JScrollPane(table);
		tableModel = new TableModel(listRow);
		table.setModel(tableModel);

		TableColumn countryColumn = table.getColumn("TableName");
		countryColumn.setCellRenderer(new TableCellRenderer());
		countryColumn
				.setCellEditor(new TableEditor(MasterCommon.listPojoTable));
		TableColumn languageColumn = table.getColumn("ColumnName");
		languageColumn.setCellRenderer(new ColumnCellRenderer());
		languageColumn.setCellEditor(new ColumnCellEditor(
				MasterCommon.listPojoCols));
		table.setRowHeight(25);
		scrollpane = new JScrollPane(table);

		scrollpane.setPreferredSize(new Dimension(400, 200));
		getContentPane().add(scrollpane, BorderLayout.CENTER);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tableModel.updateUI(createRow());
				TableColumn countryColumn = table.getColumn("TableName");
				countryColumn.setCellRenderer(new TableCellRenderer());
				countryColumn.setCellEditor(new TableEditor(
						MasterCommon.listPojoTable));
				TableColumn languageColumn = table.getColumn("ColumnName");
				languageColumn.setCellRenderer(new ColumnCellRenderer());
				languageColumn.setCellEditor(new ColumnCellEditor(
						MasterCommon.listPojoCols));
				validate();
				scrollpane.repaint();
			}

		});
		scrollpane.setRowHeaderView(btnAdd);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		validate();
		repaint();
	}

	private POJORow createRow() {
		return new POJORow(MasterCommon.listPojoTable.get(0), "");

	}
}
