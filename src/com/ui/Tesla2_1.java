package com.ui;

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

public class Tesla2_1 {

	private JFrame frmQuerybuilder;
	private TableModel tableModel;
	private ArrayList<Tables> tables;
	private JTable table;

	public Tesla2_1(ArrayList<String> tables) {
		this.tables = Controller.getTablesMetaInfo(tables);
		MasterCommon.listTable.clear();
		MasterCommon.listTable.addAll(this.tables);
		ColsUtil.setPOJOClass();
		initialize();
	}

	private void initialize() {
		frmQuerybuilder = new JFrame();
		frmQuerybuilder.getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(22, 338, 769, 81);
		frmQuerybuilder.getContentPane().add(panel);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 11, 792, 280);
		frmQuerybuilder.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 772, 258);
		panel_1.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		JButton btnAdd = new JButton("ADD");
		btnAdd.setBounds(298, 302, 108, 25);
		frmQuerybuilder.getContentPane().add(btnAdd);
		List<POJORow> listRow = new ArrayList<>();
		tableModel = new TableModel(listRow);
		table.setModel(tableModel);

		JButton btnNext = new JButton("NEXT");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmQuerybuilder.dispose();
				new Tesla3().setVisible(true);
			}
		});
		btnNext.setBounds(702, 302, 89, 23);
		frmQuerybuilder.getContentPane().add(btnNext);
		TableColumn countryColumn = table.getColumn("TableName");
		TableColumn languageColumn = table.getColumn("ColumnName");
		countryColumn.setCellRenderer(new TableCellRenderer());
		countryColumn
				.setCellEditor(new TableEditor(MasterCommon.listPojoTable));
		languageColumn.setCellRenderer(new ColumnCellRenderer());
		languageColumn.setCellEditor(new ColumnCellEditor(
				MasterCommon.listPojoCols));
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tableModel.updateUI();
			}

		});
		frmQuerybuilder.setVisible(false);
		frmQuerybuilder.setBounds(100, 100, 828, 520);
		frmQuerybuilder.setVisible(true);
		frmQuerybuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
