package com.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableColumn;
import javax.swing.text.html.HTMLEditorKit;

import com.celleditor.ColumnCellEditor;
import com.celleditor.TableEditor;
import com.controller.MasterCommon;
import com.model.CaseTableModel;
import com.pojo.CaseRow;
import com.pojo.POJORow;
import com.renderer.ColumnCellRenderer;
import com.renderer.TableCellRenderer;
import com.util.QueryColorUtil;

public class TeslaCase {

	private JFrame frame;
	private CaseTableModel tableModel;
	private JTable table;
	public static ArrayList<CaseRow> caseRows;
	private POJORow pojoRow;
	static JTextPane textPane=new JTextPane();
	static String query;

	public TeslaCase(POJORow rowCase) {
		this.pojoRow = rowCase;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 828, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// JPanel panel = new JPanel();
		// panel.setBounds(22, 22, 780, 261);
		// frame.getContentPane().add(panel);
		// panel.setLayout(null);

		table = new JTable();
		caseRows = new ArrayList<CaseRow>();
		tableModel = new CaseTableModel(caseRows);
		table.setModel(tableModel);
		initilizeColumns();
		table.setRowHeight(25);
		JScrollPane panel = new JScrollPane(table);
		panel.setBounds(10, 11, 792, 284);
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(22, 362, 780, 109);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBounds(10, 11, 760, 87);
		panel_1.add(textPane);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.editCellAt(-1, -1);
				displayQuery();
				tableModel.updateUI(pojoRow);

			}
		});
		btnAdd.setBounds(333, 317, 89, 23);
		frame.getContentPane().add(btnAdd);

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MasterCommon.completeCaseQuery = MasterCommon.completeCaseQuery
						+ query;
				table.editCellAt(-1, -1);
				frame.setVisible(false);

			}
		});
		btnDone.setBounds(490, 317, 89, 23);
		frame.getContentPane().add(btnDone);
		frame.setVisible(true);
	}

	private void initilizeColumns() {
		TableColumn table1Column = table.getColumn("TableOne");
		table1Column.setCellRenderer(new TableCellRenderer());
		table1Column.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col1Column = table.getColumn("ColumnOne");
		col1Column.setCellRenderer(new ColumnCellRenderer());
		col1Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));

		TableColumn joinTypeColumn = table.getColumn("TableTwo");
		joinTypeColumn.setCellRenderer(new TableCellRenderer());
		joinTypeColumn
				.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col2Column = table.getColumn("ColumnTwo");
		col2Column.setCellRenderer(new ColumnCellRenderer());
		col2Column
				.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));
	}

	public  void displayQuery() {
		textPane.setText("Case \n");
		query = "Case \n";
		String when = " When ";
		String then = " Then ";
		String value = "";
		for (int i = 0; i < caseRows.size(); i++) {
			CaseRow r = caseRows.get(i);
			if (r.getValueString().equals("")) {
				value = r.getTableTwo().getTableName() + "."
						+ r.getTableTwo().getTableName() + " \n";
			} else {
				value = r.getValueString();
			}
			query = query + when + r.getTableOne().getTableName() + "."
					+ r.getTableOne().getTableName() + r.getConditionString()
					+ then + value;
		}
		query = query + "End Case";
		textPane.setText(QueryColorUtil.queryColorChange(query));
	}

}
