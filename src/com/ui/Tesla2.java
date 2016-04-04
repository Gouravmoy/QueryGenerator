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
import javax.swing.JTextArea;
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
import com.service.FileIO;
import com.util.ColsUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tesla2 {

	private JFrame frmQuerybuilder;
	private TableModel tableModel;
	private ArrayList<Tables> tables;
	private JTable table;
	private static JTextArea textArea = new JTextArea();;
	List<POJORow> listRow = new ArrayList<>();
	int caseCount = 0;

	public Tesla2(ArrayList<String> tables) {
		tables.addAll(FileIO.valueHolder);
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
		panel.setLayout(null);
		textArea.setBounds(0, 0, 769, 81);
		panel.add(textArea);
		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(0, 0, 769, 81);
		panel.add(scrollPane_1);
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
		tableModel = new TableModel(MasterCommon.selectRows);
		table.setModel(tableModel);
		table.setRowHeight(25);

		JButton btnNext = new JButton("NEXT");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				table.editCellAt(-1, -1);
				MasterCommon.completeQuery = MasterCommon.completeQuery
						.replaceAll(", $", "").toUpperCase() + " FROM \n";
				frmQuerybuilder.dispose();
				new Tesla4().setVisible(true);
			}
		});
		btnNext.setBounds(702, 302, 89, 23);
		frmQuerybuilder.getContentPane().add(btnNext);

		JButton btnAddTransformation = new JButton("ADD TRANSFORMATION");
		btnAddTransformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//frmQuerybuilder.setVisible(false);
				new TeslaCase(caseCount++);
			}
		});
		btnAddTransformation.setBounds(469, 302, 166, 23);
		frmQuerybuilder.getContentPane().add(btnAddTransformation);
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
				table.editCellAt(-1, -1);
				tableModel.updateUI();
			}

		});
		frmQuerybuilder.setVisible(false);
		frmQuerybuilder.setBounds(100, 100, 828, 520);
		frmQuerybuilder.setVisible(true);
		frmQuerybuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void displyQuery(int j, String valueQuery) {
		MasterCommon.selectQueryHolder.put(j, valueQuery);
		MasterCommon.completeQuery = "Select \n";
		textArea.setText("Select \n");
		for (int i = 0; i < MasterCommon.selectQueryHolder.size(); i++) {
			textArea.append(MasterCommon.selectQueryHolder.get(i) + " \n");
			MasterCommon.completeQuery = MasterCommon.completeQuery
					+ MasterCommon.selectQueryHolder.get(i) + " \n";
		}
	}
}
