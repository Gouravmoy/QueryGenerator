package com.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import com.celleditor.ColumnCellEditor;
import com.celleditor.DropDownCellEditor;
import com.celleditor.TableEditor;
import com.controller.MasterCommon;
import com.model.WhereModel;
import com.renderer.ColumnCellRenderer;
import com.renderer.DropDownRenderer;
import com.renderer.TableCellRenderer;
import com.service.FileIO;
import com.util.QueryUtil;

public class Tesla5 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private WhereModel whereModel;
	public static JTextArea textArea;

	public Tesla5() {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 265, 792, 198);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(10, 11, 772, 176);
		textArea.setLineWrap(true);

		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(1, 1, 791, 197);
		panel_1.add(scrollPane_1);

		QueryUtil.updateQuery(textArea);

		table = new JTable();
		whereModel = new WhereModel(MasterCommon.whereRows);
		table.setModel(whereModel);
		initilizeColumns();
		table.setRowHeight(25);

		JScrollPane panel = new JScrollPane(table);
		panel.setBounds(10, 11, 792, 199);
		contentPane.add(panel, BorderLayout.CENTER);

		JLabel lblQuery = new JLabel("QUERY");
		lblQuery.setBounds(10, 240, 46, 14);
		contentPane.add(lblQuery);

		JButton btnAddCoulmn = new JButton("ADD CONDITION");
		btnAddCoulmn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				whereModel.updateUI();
				table.editCellAt(-1, -1);
				QueryUtil.updateQuery(textArea);
			}
		});
		btnAddCoulmn.setBounds(277, 221, 132, 33);
		contentPane.add(btnAddCoulmn);

		JButton btnDelete = new JButton("DELETE LAST");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				whereModel.removeFromUI();
				QueryUtil.updateQuery(textArea);
			}
		});
		btnDelete.setBounds(82, 221, 132, 33);
		contentPane.add(btnDelete);

		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FileIO.writeToText();
			}
		});
		btnNewButton.setBounds(637, 221, 132, 33);
		contentPane.add(btnNewButton);

		JButton btnRefresh = new JButton("REFRESH");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.editCellAt(-1, -1);
				QueryUtil.updateQuery(textArea);
			}
		});
		btnRefresh.setBounds(469, 221, 132, 33);
		contentPane.add(btnRefresh);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initilizeColumns() {
		TableColumn table1Column = table.getColumn("TableName");
		table1Column.setCellRenderer(new TableCellRenderer());
		table1Column.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col1Column = table.getColumn("ColumnName");
		col1Column.setCellRenderer(new ColumnCellRenderer());
		col1Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));

		TableColumn joinTypeColumn = table.getColumn("Condition");
		joinTypeColumn.setCellRenderer(new DropDownRenderer());
		joinTypeColumn.setCellEditor(new DropDownCellEditor(
				MasterCommon.relationalOps));

		TableColumn col2Column = table.getColumn("And/Or");
		col2Column.setCellRenderer(new DropDownRenderer());
		col2Column.setCellEditor(new DropDownCellEditor(MasterCommon.andOrs));
	}

}
