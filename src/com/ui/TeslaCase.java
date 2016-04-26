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
import javax.swing.ImageIcon;

public class TeslaCase {

	private JFrame frame;
	private CaseTableModel tableModel;
	private JTable table;
	public static ArrayList<CaseRow> caseRows;
	private POJORow pojoRow;
	static JTextPane textPane = new JTextPane();
	static String query;

	public TeslaCase(POJORow rowCase) {
		this.pojoRow = rowCase;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(828, 520);

		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.setTitle("Transformation Window");

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
		panel.setBounds(10, 11, 792, 217);
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(22, 294, 780, 177);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane(textPane);
		scrollPane_1.setBounds(0, 0, 769, 177);
		panel_1.add(scrollPane_1);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBounds(10, 11, 760, 87);

		JButton btnAdd = new JButton("ADD");
		btnAdd.setIcon(new ImageIcon(TeslaCase.class.getResource("/png/addd.png")));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.editCellAt(-1, -1);
				displayQuery();
				tableModel.updateUI(pojoRow);
				table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));
			}
		});
		btnAdd.setBounds(155, 252, 89, 31);
		frame.getContentPane().add(btnAdd);

		JButton btnDone = new JButton("Done");
		btnDone.setIcon(new ImageIcon(TeslaCase.class.getResource("/png/Exit.png")));
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MasterCommon.completeCaseQuery = MasterCommon.completeCaseQuery + query;
				table.editCellAt(-1, -1);
				frame.setVisible(false);
			}
		});
		btnDone.setBounds(374, 252, 95, 31);
		frame.getContentPane().add(btnDone);

		JButton btnElse = new JButton("ELSE");
		btnElse.setIcon(new ImageIcon(TeslaCase.class.getResource("/png/Locking.png")));
		btnElse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.editCellAt(-1, -1);
				displayQuery();
				tableModel.updateUI1(pojoRow);
				table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));
			}
		});
		btnElse.setBounds(572, 252, 95, 31);
		frame.getContentPane().add(btnElse);
		frame.setVisible(true);
	}

	private void initilizeColumns() {
		TableColumn table1Column = table.getColumn("TableOne");
		table1Column.setCellRenderer(new TableCellRenderer());
		table1Column.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col1Column = table.getColumn("ColumnOne");
		col1Column.setCellRenderer(new ColumnCellRenderer());
		col1Column.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));

		TableColumn joinTypeColumn = table.getColumn("TableTwo");
		joinTypeColumn.setCellRenderer(new TableCellRenderer());
		joinTypeColumn.setCellEditor(new TableEditor(MasterCommon.listPojoTable));

		TableColumn col2Column = table.getColumn("ColumnTwo");
		col2Column.setCellRenderer(new ColumnCellRenderer());
		col2Column.setCellEditor(new ColumnCellEditor(MasterCommon.listPojoCols));
	}

	public void displayQuery() {
		textPane.setText("Case \n");
		query = "Case \n";
		String when = " When ";
		String then = " Then ";
		for (int i = 0; i < caseRows.size(); i++) {
			String value = "";
			CaseRow r = caseRows.get(i);
			if (r.getTableOne().getTableName() == null) {
				caseRows.remove(i);
			} else {
				if (r.getConditionString().equals("ELSE")) {
					if (r.getValueString().length() == 0) {
						value = r.getTableTwo().getTableName() + "." + r.getTableTwo().getColumn().getColumnName()
								+ " \n";
					} else {
						value = "'" + r.getValueString() + "' \n";
					}
					query = query + " ELSE " + value;
					break;
				} else {
					if (r.getValueString().length() == 0) {
						value = r.getTableTwo().getTableName() + "." + r.getTableTwo().getColumn().getColumnName()
								+ " \n";
					} else {
						value = "'" + r.getValueString() + "' \n";
					}
					query = query + when + r.getTableOne().getTableName() + "."
							+ r.getTableOne().getColumn().getColumnName() + " = '" + r.getConditionString() + "'" + then
							+ value;
				}
			}
		}
		query = query + "End Case";
		textPane.setText(QueryColorUtil.queryColorChange(query));
	}
}
