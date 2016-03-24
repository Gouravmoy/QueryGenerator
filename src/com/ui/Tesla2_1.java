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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

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
		// frmQuerybuilder.setBounds(100, 100, 657, 512);
		JPanel panel = new JPanel();
		frmQuerybuilder.getContentPane().add(panel);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 260, 621, 203);
		frmQuerybuilder.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		JButton btnAdd = new JButton("ADD");
		btnAdd.setBounds(209, 224, 108, 25);
		frmQuerybuilder.getContentPane().add(btnAdd);
		List<POJORow> listRow = new ArrayList<>();
		tableModel = new TableModel(listRow);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 621, 202);
		frmQuerybuilder.getContentPane().add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		table.setRowHeight(25);

		JButton btnNewButton = new JButton("NEXT BUTTON");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmQuerybuilder.dispose();
				new Tesla4().setVisible(true);
			}
		});
		btnNewButton.setBounds(505, 224, 126, 25);
		frmQuerybuilder.getContentPane().add(btnNewButton);
		
		JLabel lblQuery = new JLabel("QUERY");
		lblQuery.setBounds(10, 248, 46, 14);
		frmQuerybuilder.getContentPane().add(lblQuery);
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
		frmQuerybuilder.setBounds(100, 100, 657, 512);
		frmQuerybuilder.setVisible(true);
		frmQuerybuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
