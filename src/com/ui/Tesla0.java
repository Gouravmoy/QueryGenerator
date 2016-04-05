package com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import com.celleditor.TableEditor;
import com.controller.Controller;
import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.model.DBConnectionsModel;
import com.renderer.TableCellRenderer;
import com.service.FileIO;
import com.util.DBConnectionUtil;

public class Tesla0 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<String> tableNames;
	private ArrayList<String> selectedTableNames = new ArrayList<String>();
	private ArrayList<DBDetails> dbConnection = new ArrayList<DBDetails>();
	private ArrayList<String> dbConnectionNames = new ArrayList<String>();
	private JCheckBox[] tablesCheckBoxList;
	JScrollPane dbNamesPane;
	JTable connNamesTable;
	DBConnectionsModel dbConnectionsModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tesla0 frame = new Tesla0();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tesla0() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 694, 21);
		contentPane.add(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoadQuery = new JMenuItem("Load Query");
		mntmLoadQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TeslaFileBrowse fileBrowse = new TeslaFileBrowse();
				String filePath = fileBrowse.getFilePath();
				tableNames = Controller.getTables("testschema", "", "", "root",
						"Welcome123");

				if (tablesCheckBoxList != null) {
					for (JCheckBox checkBox : tablesCheckBoxList) {
						if (checkBox.isSelected())
							selectedTableNames.add(checkBox.getText());
					}
				}
				FileIO.getFromTextFile(filePath);
				dispose();
				new Tesla2(selectedTableNames);
			}
		});
		mntmLoadQuery.setIcon(new ImageIcon(Tesla0.class
				.getResource("/png/db.png")));
		mnFile.add(mntmLoadQuery);

		JMenu mnDatabase = new JMenu("Database");
		menuBar.add(mnDatabase);

		JMenuItem mntmAddDatabase = new JMenuItem("New Database Conncetion");
		mntmAddDatabase.setIcon(new ImageIcon(Tesla0.class
				.getResource("/png/plus.png")));
		mntmAddDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TeslaDBDetails().setVisible(true);
			}
		});

		mnDatabase.add(mntmAddDatabase);

		Panel dbConnectionView = new Panel();
		dbConnectionView.setBounds(10, 67, 191, 389);
		contentPane.add(dbConnectionView);

		dbConnection = DBConnectionUtil.getAllConnection();
		dbConnection = new ArrayList<>();// to change
		dbConnectionNames = DBConnectionUtil.getAllConnectionNames();

		dbConnectionsModel = new DBConnectionsModel(dbConnection);
		connNamesTable = new JTable();
		connNamesTable.setModel(dbConnectionsModel);
		connNamesTable.setRowHeight(25);

		TableColumn connNamesTableName = connNamesTable
				.getColumn("Connection Names");
		connNamesTableName.setCellRenderer(new TableCellRenderer());
		connNamesTableName.setCellEditor(new TableEditor(
				MasterCommon.listPojoTable));

		dbNamesPane = new JScrollPane(connNamesTable);
		dbNamesPane.setBounds(10, 67, 191, 389);
		contentPane.add(dbNamesPane);

		Panel panel_1 = new Panel();
		panel_1.setBounds(207, 67, 477, 389);
		contentPane.add(panel_1);
	}
}
