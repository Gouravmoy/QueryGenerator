package com.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;

import com.controller.MasterCommon;
import com.model.CaseTableModel;
import com.model.CoalesceTableModel;
import com.pojo.CaseRow;
import com.pojo.CoalesceRow;
import com.pojo.POJORow;
import com.service.TeslaTransFunctions;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class TeslaTransforms {

	private JFrame frame;
	private CaseTableModel tableCaseModel;
	private CoalesceTableModel tableCoalesceModel;
	public static ArrayList<CaseRow> caseRows;
	public static ArrayList<CoalesceRow> coalesceRows;
	private POJORow pojoRow;
	protected static JTextPane textPane = new JTextPane();
	protected static String query;
	JTable tableCase;
	private JTable tableCoalesce;
	private JTextField txtEnterStringField;

	public TeslaTransforms(POJORow pojoRow) {
		this.pojoRow = pojoRow;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(828, 520);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 792, 301);
		frame.getContentPane().add(tabbedPane);

		JPanel panelCase = new JPanel();
		tabbedPane.addTab("Case Tab", null, panelCase, null);
		panelCase.setLayout(null);

		tableCase = new JTable();
		tableCase.setBounds(10, 11, 634, 219);
		panelCase.add(tableCase);
		caseRows = new ArrayList<CaseRow>();
		tableCaseModel = new CaseTableModel(caseRows);
		tableCase.setModel(tableCaseModel);

		JScrollPane scrollPaneCase = new JScrollPane(tableCase);
		scrollPaneCase.setBounds(10, 11, 754, 164);
		panelCase.add(scrollPaneCase);

		JPanel panelCoalesce = new JPanel();
		tabbedPane.addTab("Coalesce Tab", null, panelCoalesce, null);

		tableCoalesce = new JTable();
		tableCoalesce.setBounds(1, 36, 765, 0);
		panelCoalesce.add(tableCoalesce);
		panelCase.setLayout(null);
		coalesceRows = new ArrayList<CoalesceRow>();
		tableCoalesceModel = new CoalesceTableModel(coalesceRows);
		panelCoalesce.setLayout(null);
		tableCoalesce.setModel(tableCoalesceModel);

		JScrollPane scrollPaneCoalesce = new JScrollPane(tableCoalesce);
		scrollPaneCoalesce.setBounds(10, 11, 767, 131);
		panelCoalesce.add(scrollPaneCoalesce);

		JPanel coalesceButtonPanel = new JPanel();
		coalesceButtonPanel.setBounds(10, 201, 757, 50);
		panelCoalesce.add(coalesceButtonPanel);
		coalesceButtonPanel.setLayout(null);

		JButton coalesceAddBtn = new JButton("ADD");
		coalesceAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pojoRow.getRowType() == null) {
					pojoRow.setRowType("Coalesce");
					TeslaTransFunctions.initializeCoalesceTables(tableCoalesce);
				}
				tableCoalesce.editCellAt(-1, -1);
				TeslaTransFunctions.displayQuery();
				tableCoalesceModel.updateUI(pojoRow);
				tableCoalesce.scrollRectToVisible(tableCoalesce.getCellRect(tableCoalesce.getRowCount() - 1, 0, true));

			}
		});
		coalesceAddBtn.setBounds(286, 16, 115, 29);
		coalesceButtonPanel.add(coalesceAddBtn);

		txtEnterStringField = new JTextField();
		txtEnterStringField.setBounds(287, 158, 146, 26);
		panelCoalesce.add(txtEnterStringField);
		txtEnterStringField.setColumns(10);

		JLabel lblEnterText = new JLabel("Enter Text");
		lblEnterText.setBounds(125, 161, 119, 20);
		panelCoalesce.add(lblEnterText);

		JPanel panelCaseButton = new JPanel();
		panelCaseButton.setBounds(26, 202, 726, 44);
		panelCase.add(panelCaseButton);

		JButton btnAddCase = new JButton("ADD");
		btnAddCase.setBounds(288, 11, 89, 23);
		btnAddCase.setIcon(new ImageIcon(TeslaCase.class.getResource("/png/addd.png")));
		btnAddCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pojoRow.getRowType() == null) {
					pojoRow.setRowType("Case");
					TeslaTransFunctions.initializeCaseTables(tableCase);
				}
				tableCase.editCellAt(-1, -1);
				TeslaTransFunctions.displayQuery();
				tableCaseModel.updateUI(pojoRow);
				tableCase.scrollRectToVisible(tableCase.getCellRect(tableCase.getRowCount() - 1, 0, true));

			}
		});
		panelCaseButton.setLayout(null);
		panelCaseButton.add(btnAddCase);

		JButton btnNewButton = new JButton("ELSE");
		btnNewButton.setIcon(new ImageIcon(TeslaCase.class.getResource("/png/Locking.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableCase.editCellAt(-1, -1);
				TeslaTransFunctions.displayQuery();
				tableCaseModel.updateUI1(pojoRow);
				tableCase.scrollRectToVisible(tableCase.getCellRect(tableCase.getRowCount() - 1, 0, true));

			}
		});
		btnNewButton.setBounds(496, 11, 89, 23);
		panelCaseButton.add(btnNewButton);

		JButton btnElse = new JButton("DONE");
		btnElse.setIcon(new ImageIcon(TeslaCase.class.getResource("/png/Exit.png")));
		btnElse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MasterCommon.completeCaseQuery = MasterCommon.completeCaseQuery + query;
				tableCase.editCellAt(-1, -1);
				frame.setVisible(false);

			}
		});
		btnElse.setBounds(72, 11, 89, 23);
		panelCaseButton.add(btnElse);

		JPanel panel_Query = new JPanel();
		panel_Query.setBounds(10, 323, 792, 141);
		frame.getContentPane().add(panel_Query);
		textPane.setEditorKit(new HTMLEditorKit());
		panel_Query.add(textPane);
		frame.setVisible(true);
	}
}
