package com.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
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
import javax.swing.JLabel;

public class Tesla4 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private InnerJoinTableModel innerJoinTableModel;

	/**
	 * Create the frame.
	 */
	public Tesla4() {
		// ColsUtil.setPOJOClass();
		initialize();

	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 254, 621, 192);
		contentPane.add(panel_1);

		table = new JTable();
		List<InnerJoinRow> innerJoinRows = new ArrayList<InnerJoinRow>();
		innerJoinTableModel = new InnerJoinTableModel(innerJoinRows);
		table.setModel(innerJoinTableModel);

		initilizeColumns();

		table.setRowHeight(25);

		JScrollPane panel = new JScrollPane(table);
		panel.setBounds(10, 11, 621, 199);
		contentPane.add(panel, BorderLayout.CENTER);

		JButton btnAddCoulmn = new JButton("ADD COULMN");
		btnAddCoulmn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				innerJoinTableModel.updateUI();
			}
		});
		btnAddCoulmn.setBounds(232, 221, 116, 23);
		contentPane.add(btnAddCoulmn);

		JLabel lblQuery = new JLabel("QUERY");
		lblQuery.setBounds(10, 240, 46, 14);
		contentPane.add(lblQuery);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
