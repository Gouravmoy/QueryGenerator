package com.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import com.util.QueryUtil;
import javax.swing.JTextArea;

public class Tesla4 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private InnerJoinTableModel innerJoinTableModel;
	public static JTextArea textArea;
	List<InnerJoinRow> innerJoinRows;

	/**
	 * Create the frame.
	 */
	public Tesla4() {
		// ColsUtil.setPOJOClass();
		initialize();

	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();
		innerJoinRows = new ArrayList<InnerJoinRow>();
		innerJoinTableModel = new InnerJoinTableModel(innerJoinRows);
		table.setModel(innerJoinTableModel);

		initilizeColumns();

		table.setRowHeight(25);

		JScrollPane panel = new JScrollPane(table);
		panel.setBounds(10, 11, 792, 199);
		contentPane.add(panel, BorderLayout.CENTER);

		JLabel lblQuery = new JLabel("QUERY");
		lblQuery.setBounds(10, 240, 46, 14);
		contentPane.add(lblQuery);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 265, 792, 198);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(10, 11, 772, 176);
		textArea.setLineWrap(true);
		panel_1.add(textArea);

		JButton btnAddCoulmn = new JButton("ADD COULMN");
		btnAddCoulmn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				innerJoinTableModel.updateUI();
				table.editCellAt(-1, -1);
				QueryUtil.updateInnerJoinMap(innerJoinRows);
				QueryUtil.buildQuery();
				textArea.setText(MasterCommon.mainQuery.toString());
			}
		});

		btnAddCoulmn.setBounds(293, 221, 116, 23);
		contentPane.add(btnAddCoulmn);

		JButton btnDelete = new JButton("DELETE LAST");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				innerJoinTableModel.removeFromUI();
				// innerJoinRows = new ArrayList<InnerJoinRow>();
				textArea.setText(MasterCommon.mainQuery.toString());
			}
		});
		btnDelete.setBounds(484, 221, 146, 23);
		contentPane.add(btnDelete);
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
