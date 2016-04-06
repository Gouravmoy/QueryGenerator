package com.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.controller.Controller;
import com.entity.DBDetails;
import com.entity.TablesSelect;
import com.model.DBConnectionsModel;
import com.model.TableNameModel;
import com.service.FileIO;
import com.util.DBConnectionUtil;
import com.util.PropsLoader;

public class Tesla0 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static ArrayList<String> tempTableNames = new ArrayList<String>();
	public static ArrayList<String> tableNames = new ArrayList<String>();
	private ArrayList<String> selectedTableNames = new ArrayList<String>();
	private ArrayList<DBDetails> dbConnection = new ArrayList<DBDetails>();
	private JCheckBox[] tablesCheckBoxList;
	JScrollPane dbNamesPane;
	JScrollPane tableNameScrollPane;
	JTable connNamesTable;
	JTable tablenameTable;
	DBConnectionsModel dbConnectionsModel;
	TableNameModel tableNameModel;
	JMenuItem mntmConnect;
	JPopupMenu popupMenu;
	StringBuilder selectedDB = new StringBuilder();
	ArrayList<TablesSelect> tablesSelects = new ArrayList<TablesSelect>();
	JButton btnBuildQuery;

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
					PropsLoader.loadProps();
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

		dbConnection = DBConnectionUtil.getAllConnection();

		dbConnectionsModel = new DBConnectionsModel(dbConnection);
		connNamesTable = new JTable();
		connNamesTable.setModel(dbConnectionsModel);
		connNamesTable.setRowHeight(25);

		/*
		 * TableColumn connNamesTableName = connNamesTable .getColumn(
		 * "Connection Names"); connNamesTableName.setCellRenderer(new
		 * TableCellRenderer()); connNamesTableName.setCellEditor(new
		 * TableEditor( MasterCommon.listPojoTable));
		 */

		dbNamesPane = new JScrollPane(connNamesTable);

		popupMenu = new JPopupMenu();
		addPopup(connNamesTable, popupMenu);

		mntmConnect = new JMenuItem("Connect");
		mntmConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnBuildQuery.setEnabled(true);
				tableNames.removeAll(tableNames);
				tempTableNames = Controller.getTables(DBConnectionUtil
						.getDBDetails(selectedDB, dbConnection));
				tableNames.addAll(tempTableNames);
				for (String tableName : tableNames) {
					tablesSelects.add(new TablesSelect(tableName, false));
				}
				tableNameModel.updateUI();
			}
		});

		mntmConnect.setIcon(new ImageIcon(Tesla0.class
				.getResource("/png/connect-no.png")));
		popupMenu.add(mntmConnect);

		JMenuItem mntmEditConnection = new JMenuItem("Edit Connection");
		popupMenu.add(mntmEditConnection);

		JMenuItem mntmDeleteConnection = new JMenuItem("Delete Connection");
		popupMenu.add(mntmDeleteConnection);

		popupMenu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						int rowAtPoint = connNamesTable
								.rowAtPoint(SwingUtilities.convertPoint(
										popupMenu, new Point(0, 0),
										connNamesTable));
						if (rowAtPoint > -1) {
							connNamesTable.setRowSelectionInterval(rowAtPoint,
									rowAtPoint);
							selectedDB.setLength(0);
							selectedDB.append(connNamesTable.getValueAt(
									rowAtPoint, 0));
						}
					}
				});

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
		});

		dbNamesPane.setBounds(10, 67, 191, 389);
		contentPane.add(dbNamesPane);

		tableNameModel = new TableNameModel(tablesSelects);

		tablenameTable = new JTable();
		tablenameTable.setModel(tableNameModel);
		tablenameTable.setRowHeight(25);

		tableNameScrollPane = new JScrollPane(tablenameTable);
		tableNameScrollPane.setBounds(211, 67, 473, 388);
		contentPane.add(tableNameScrollPane);

		btnBuildQuery = new JButton("Build Query");
		btnBuildQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tempTableNames = new ArrayList<>();
				for (TablesSelect tablesSelect : tablesSelects) {
					if (tablesSelect.isSelected())
						tempTableNames.add(tablesSelect.getTableName());
				}
				new Tesla2(tempTableNames);
				dispose();
			}
		});
		btnBuildQuery.setBounds(568, 32, 116, 23);
		btnBuildQuery.setEnabled(false);
		contentPane.add(btnBuildQuery);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
