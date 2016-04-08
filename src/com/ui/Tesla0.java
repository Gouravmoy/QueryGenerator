package com.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableColumn;

import com.controller.Controller;
import com.controller.MasterCommon;
import com.entity.TablesSelect;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.model.DBConnectionsModel;
import com.model.TableNameModel;
import com.renderer.IconTextCellRemderer;
import com.service.FileIO;
import com.util.DBConnectionUtil;
import com.util.PropsLoader;

public class Tesla0 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static ArrayList<String> tempTableNames = new ArrayList<String>();
	public static ArrayList<String> tableNames = new ArrayList<String>();
	private ArrayList<String> selectedTableNames = new ArrayList<String>();
	// private JCheckBox[] tablesCheckBoxList;
	JScrollPane dbNamesPane;
	JScrollPane tableNameScrollPane;
	JTable connNamesTable;
	JTable tablenameTable;
	public static DBConnectionsModel dbConnectionsModel;
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
			Properties props = new Properties();
			props.put("logoString", "VIGO");
			AcrylLookAndFeel.setCurrentTheme(props);
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//PropsLoader.loadProps();
					PropsLoader loader = new PropsLoader();
					loader.loadProps();
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
		setTitle("Multiverse Query Generator");

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 694, 21);
		contentPane.add(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoadQuery = new JMenuItem("Load Query");

		KeyStroke keyStrokeToLoadQuery = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
		mntmLoadQuery.setAccelerator(keyStrokeToLoadQuery);

		mntmLoadQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TeslaFileBrowse fileBrowse = new TeslaFileBrowse("ser", "OPEN");
				String filePath = fileBrowse.getFilePath();
				if (filePath.length() != 0) {
					FileIO.getFromTextFile(filePath);
					dispose();
					new Tesla2(selectedTableNames);
				}
			}
		});
		mntmLoadQuery.setIcon(new ImageIcon(Tesla0.class.getResource("/png/load.png")));
		mnFile.add(mntmLoadQuery);

		JMenu space = new JMenu("");
		space.setEnabled(false);
		menuBar.add(space);

		JMenu mnDatabase = new JMenu("Database");
		menuBar.add(mnDatabase);

		JMenuItem mntmAddDatabase = new JMenuItem("New Database Conncetion");

		KeyStroke keyStrokeToNewConnection = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
		mntmAddDatabase.setAccelerator(keyStrokeToNewConnection);

		mntmAddDatabase.setIcon(new ImageIcon(Tesla0.class.getResource("/png/database_add.png")));
		mntmAddDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TeslaDBDetails().setVisible(true);
				System.out.println("Here");
			}
		});

		mnDatabase.add(mntmAddDatabase);

		MasterCommon.dbConnection = DBConnectionUtil.getAllConnection();

		dbConnectionsModel = new DBConnectionsModel(MasterCommon.dbConnection);
		connNamesTable = new JTable();
		connNamesTable.setModel(dbConnectionsModel);
		connNamesTable.setRowHeight(25);
		initilizeColumn();

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
				tempTableNames = Controller.getTables(DBConnectionUtil.getDBDetails(MasterCommon.selectedDBName));
				tableNames.addAll(tempTableNames);
				tablesSelects.clear();
				for (String tableName : tableNames) {
					tablesSelects.add(new TablesSelect(tableName, false));
				}
				tableNameModel.updateUI();
			}
		});

		mntmConnect.setIcon(new ImageIcon(Tesla0.class.getResource("/png/database_connect.png")));
		popupMenu.add(mntmConnect);

		JMenuItem mntmEditConnection = new JMenuItem("Edit Connection");
		mntmEditConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MasterCommon.dbConnection = DBConnectionUtil.getAllConnection();
				new TeslaDBDetails(MasterCommon.selectedDBName).setVisible(true);
			}
		});
		mntmEditConnection.setIcon(new ImageIcon(Tesla0.class.getResource("/png/edit.png")));
		popupMenu.add(mntmEditConnection);

		JMenuItem mntmDeleteConnection = new JMenuItem("Delete Connection");
		mntmDeleteConnection.setIcon(new ImageIcon(Tesla0.class.getResource("/png/delete.png")));
		mntmDeleteConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Are you Sure you want to delete this Connection?", "Warning", dialogButton);

				if (dialogResult == JOptionPane.YES_OPTION) {
					DBConnectionUtil.deleteDBConnection(MasterCommon.selectedDBName);
					MasterCommon.dbConnection = DBConnectionUtil.getAllConnection();
					dbConnectionsModel.updateUI(MasterCommon.dbConnection);
				}
			}
		});
		popupMenu.add(mntmDeleteConnection);

		popupMenu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						int rowAtPoint = connNamesTable
								.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), connNamesTable));
						if (rowAtPoint > -1) {
							connNamesTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
							selectedDB.setLength(0);
							selectedDB.append(connNamesTable.getValueAt(rowAtPoint, 0));
							MasterCommon.selectedDBName = selectedDB.toString();
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
		btnBuildQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tempTableNames = new ArrayList<>();
				for (TablesSelect tablesSelect : tablesSelects) {
					if (tablesSelect.isSelected())
						tempTableNames.add(tablesSelect.getTableName());
				}
				new Tesla2(tempTableNames);
				dispose();
			}
		});
		btnBuildQuery.setIcon(new ImageIcon(Tesla0.class.getResource("/png/sql-query.png")));
		btnBuildQuery.setBounds(568, 32, 116, 23);
		btnBuildQuery.setEnabled(false);
		contentPane.add(btnBuildQuery);
	}

	private void initilizeColumn() {
		TableColumn table1Column = connNamesTable.getColumn("Connection Names");
		table1Column.setCellRenderer(new IconTextCellRemderer());

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
