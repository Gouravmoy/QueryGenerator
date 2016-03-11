package com.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.controller.Controller;
import com.entity.Column;
import com.entity.Tables;

import javax.swing.JTree;

public class Tesla2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<Tables> tables;
	// private JTable table;
	private JComboBox<String> jComboTableNames;
	private JComboBox<String> jComboColNames;
	private String[] tableNames;
	private String[] colNames;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;

	private int yBound;

	/*
	 * String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years"
	 * , "Vegetarian" }; Object[][] data = { { "Kathy", "Smith", "Snowboarding",
	 * new Integer(5), new Boolean(false) }, { "John", "Doe", "Rowing", new
	 * Integer(3), new Boolean(true) }, { "Sue", "Black", "Knitting", new
	 * Integer(2), new Boolean(false) }, { "Jane", "White", "Speed reading", new
	 * Integer(20), new Boolean(true) }, { "Joe", "Brown", "Pool", new
	 * Integer(10), new Boolean(false) } };
	 */

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Tesla2 frame = new Tesla2();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 * }); }
	 */

	/**
	 * Create the frame.
	 */
	public Tesla2(ArrayList<String> tables) {
		this.tables = Controller.getTablesMetaInfo(tables);
		initialize();

	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Query");
		lblNewLabel.setBounds(525, 22, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblInnerJoins = new JLabel("INNER JOINS");
		lblInnerJoins.setBounds(28, 22, 110, 14);
		contentPane.add(lblInnerJoins);
		tableNames = new String[tables.size()];
		int i = 0;
		for (Tables table : tables) {
			tableNames[i++] = table.getTableName().toUpperCase();
		}

		jComboTableNames = new JComboBox(tableNames);
		jComboTableNames.setBounds(28, 69, 110, 20);
		jComboTableNames.setMaximumSize(jComboTableNames.getPreferredSize());
		jComboTableNames.addActionListener(new ActionListener() {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				String selectedTableName = comboBox.getSelectedItem().toString();
				for (Tables table : tables) {
					if (table.getTableName().equalsIgnoreCase(selectedTableName)) {
						List<Column> columns = table.getColumnList();
						int j = 0;
						colNames = new String[columns.size()];
						for (Column column : columns) {
							colNames[j++] = column.getColName();
						}
						if (jComboColNames != null) {
							contentPane.remove(jComboColNames);
							jComboColNames = null; 
						}
						jComboColNames = new JComboBox(colNames);
						jComboColNames.setBounds(148, 69, 110, 20);
						jComboColNames.setMaximumSize(jComboColNames.getPreferredSize());
						contentPane.add(jComboColNames);
						contentPane.repaint();
					}
				}
			}
		});
		contentPane.add(jComboTableNames);

		/*
		 * comboBox_2 = new JComboBox(); comboBox_2.setBounds(148, 69, 110, 20);
		 * contentPane.add(comboBox_2);
		 */

		/*
		 * table = new JTable(); table.setBackground(Color.LIGHT_GRAY);
		 * table.setModel(new DefaultTableModel(new Object[][] { { "",
		 * jComboTableNames, null, null, null }, }, new String[] {
		 * "Table Name 1", "Col Name 1", "Join Type", "Table Name 2",
		 * "Col 2 Name" })); table.setBounds(28, 59, 469, 371);
		 * contentPane.add(table);
		 */
	}

	public class GetTablesSelectedListner implements ActionListener {
		List<Tables> tables;

		public GetTablesSelectedListner(List<Tables> tables) {
			this.tables = tables;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			JComboBox comboBox = (JComboBox) event.getSource();
			String selectedTableName = comboBox.getSelectedItem().toString();
			for (Tables table : tables) {
				if (table.getTableName().equalsIgnoreCase(selectedTableName)) {
					List<Column> columns = table.getColumnList();
					int j = 0;
					yBound += 10;
					colNames = new String[columns.size()];
					for (Column column : columns) {
						colNames[j++] = column.getColName();
					}
					jComboColNames = new JComboBox(colNames);

					jComboColNames.setBounds(148, yBound, 110, 20);
					jComboColNames.setMaximumSize(jComboColNames.getPreferredSize());
					contentPane.add(jComboColNames);
				}
			}

		}
	}
}
