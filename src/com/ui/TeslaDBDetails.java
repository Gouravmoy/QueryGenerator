package com.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.entity.DBDetails;
import com.entity.DBTypes;
import com.exceptions.DBAlreadyExists;
import com.exceptions.DBConnectionError;
import com.service.FileIO;
import com.util.DBConnectionUtil;
import com.util.DBUtil;

public class TeslaDBDetails extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField connectionNameText;
	private JTextField usernameText;
	private JPasswordField passwordText;
	private JTextField hostNameText;
	private JTextField portNameText;
	private JTextField databaseNameText;
	private JLabel lblConnectionName;
	private JLabel lblHostName;
	private JLabel lblUserName;
	private JLabel lblPassword;
	private JLabel lblPortName;
	private JLabel lblDatabase;
	private JLabel lblDbType;
	private JLabel lblSchema;
	private JButton btnClear;
	private JButton btnTest;
	private JButton btnCancel;
	private DBDetails dbDetails;

	private ArrayList<String> schemaNameList = new ArrayList<>();

	@SuppressWarnings("rawtypes")
	JComboBox dbType;
	@SuppressWarnings("rawtypes")
	JComboBox schemaCombo;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TeslaDBDetails() {
		connectionNameText = new JTextField();
		usernameText = new JTextField();
		passwordText = new JPasswordField();
		hostNameText = new JTextField();
		portNameText = new JTextField();
		databaseNameText = new JTextField();
		dbType = new JComboBox();
		dbType.setModel(new DefaultComboBoxModel(DBTypes.values()));
		schemaCombo = new JComboBox(schemaNameList.toArray());
		initialize();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TeslaDBDetails(String selectedDBName) {
		DBDetails dbDetail = DBConnectionUtil.getDBDetails(selectedDBName);
		connectionNameText = new JTextField(dbDetail.getConnectionName());
		connectionNameText.setEditable(false);
		usernameText = new JTextField(dbDetail.getUserName());
		passwordText = new JPasswordField(dbDetail.getPassword());
		hostNameText = new JTextField(dbDetail.getHostName());
		portNameText = new JTextField(dbDetail.getPort());
		databaseNameText = new JTextField(dbDetail.getDatabase());
		dbType = new JComboBox();
		DBTypes selectedDbTypes = DBTypes.convert(dbDetail.getDbType());
		dbType.setModel(new DefaultComboBoxModel(DBTypes.values()));
		dbType.setSelectedItem(selectedDbTypes);
		schemaCombo = new JComboBox(schemaNameList.toArray());
		schemaNameList.add(dbDetail.getDbSchema());
		schemaCombo.setSelectedItem(dbDetail.getDbSchema());
		initialize();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		setTitle("New DatabaseConnection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(565, 386);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblConnectionName = new JLabel("Connection Name");
		lblConnectionName.setBounds(31, 14, 106, 14);
		contentPane.add(lblConnectionName);

		connectionNameText.setBounds(147, 11, 373, 20);
		contentPane.add(connectionNameText);
		connectionNameText.setColumns(10);

		lblUserName = new JLabel("User Name");
		lblUserName.setBounds(31, 36, 85, 14);
		contentPane.add(lblUserName);

		usernameText.setColumns(10);
		usernameText.setBounds(147, 33, 373, 20);
		contentPane.add(usernameText);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(31, 61, 85, 14);
		contentPane.add(lblPassword);

		passwordText.setColumns(10);
		passwordText.setBounds(147, 55, 373, 20);
		contentPane.add(passwordText);

		lblHostName = new JLabel("Host Name");
		lblHostName.setBounds(147, 118, 71, 14);
		contentPane.add(lblHostName);

		hostNameText.setBounds(247, 115, 134, 20);
		contentPane.add(hostNameText);
		hostNameText.setColumns(10);

		portNameText.setColumns(10);
		portNameText.setBounds(247, 143, 134, 20);
		contentPane.add(portNameText);

		lblPortName = new JLabel("Port Name");
		lblPortName.setBounds(147, 146, 71, 14);
		contentPane.add(lblPortName);

		databaseNameText.setColumns(10);
		databaseNameText.setBounds(247, 172, 134, 20);
		contentPane.add(databaseNameText);

		lblDatabase = new JLabel("Database");
		lblDatabase.setBounds(147, 175, 71, 14);
		contentPane.add(lblDatabase);

		JButton btnSave = new JButton("Save");
		btnSave.setIcon(new ImageIcon(TeslaDBDetails.class
				.getResource("/png/save.png")));
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				assignDBEntity();

				if (DBConnectionUtil.checkConnectivity(dbDetails)) {
					try {
						FileIO.saveDBDetails(dbDetails,
								connectionNameText.isEditable());
						if (connectionNameText.isEditable())
							Tesla0.dbConnectionsModel.updateUI(dbDetails);
						dispose();
					} catch (DBAlreadyExists e) {
						JOptionPane.showMessageDialog(null,
								"Connection Save Failed! " + e.getMessage());
					}
				} else {
					JOptionPane
							.showMessageDialog(null,
									"Connection Test Failed! Please check the Connection Details");
				}

			}

		});
		btnSave.setBounds(64, 281, 90, 23);
		contentPane.add(btnSave);

		btnClear = new JButton("Clear");
		btnClear.setIcon(new ImageIcon(TeslaDBDetails.class
				.getResource("/png/clear.png")));
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				connectionNameText.setText("");
				usernameText.setText("");
				passwordText.setText("");
				hostNameText.setText("");
				portNameText.setText("");
				databaseNameText.setText("");
			}
		});
		btnClear.setBounds(180, 281, 89, 23);
		contentPane.add(btnClear);

		btnTest = new JButton("Test");
		btnTest.setIcon(new ImageIcon(TeslaDBDetails.class
				.getResource("/png/connect-icon.png")));
		btnTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				assignDBEntity();
				if (DBConnectionUtil.checkConnectivity(dbDetails)) {
					JOptionPane.showMessageDialog(null,
							"Connection Test Success!");
				} else {
					JOptionPane
							.showMessageDialog(null,
									"Connection Test Failed! Please check the Connection Details");
				}
			}
		});
		btnTest.setBounds(292, 281, 89, 23);
		contentPane.add(btnTest);

		btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(TeslaDBDetails.class
				.getResource("/png/Exit.png")));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(410, 281, 89, 23);
		contentPane.add(btnCancel);

		lblDbType = new JLabel("DB Type");
		lblDbType.setBounds(147, 93, 46, 14);
		contentPane.add(lblDbType);

		lblSchema = new JLabel("Schema Name");
		lblSchema.setBounds(147, 203, 85, 14);
		contentPane.add(lblSchema);

		/*
		 * schemaNameText.setColumns(10); schemaNameText.setBounds(224, 203,
		 * 134, 19); contentPane.add(schemaNameText);
		 * 
		 * lblSchema.setVisible(true); schemaNameText.setVisible(true);
		 */

		// dbType = new JComboBox();
		dbType.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (dbType.getSelectedItem().toString().equals("SQL")
						|| dbType.getSelectedItem().toString().equals("DB2")) {
					lblSchema.setVisible(true);
					schemaCombo.setVisible(true);
				} else {
					lblSchema.setVisible(false);
					schemaCombo.setVisible(false);
				}

			}
		});

		// dbType.setModel(new DefaultComboBoxModel(DBTypes.values()));
		dbType.setBounds(247, 84, 134, 20);
		contentPane.add(dbType);

		schemaCombo = new JComboBox(schemaNameList.toArray());
		schemaCombo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("inside Mouse Action Listner");
				try {
					schemaNameList = DBUtil.getSchemaName(hostNameText
							.getText(), usernameText.getText(), portNameText
							.getText(), databaseNameText.getText(), String
							.valueOf(passwordText.getPassword()), dbType
							.getSelectedItem().toString());
					schemaCombo.removeAllItems();
					for (String schemaName : schemaNameList) {
						schemaCombo.addItem(schemaName);
					}
					schemaCombo.revalidate();
					contentPane.revalidate();
					contentPane.repaint();
				} catch (DBConnectionError e) {
					JOptionPane
							.showMessageDialog(
									null,
									"Connection Test Failed! Reason -"
											+ e.getMessage());
				}
			}
		});
		schemaCombo.setBounds(247, 200, 134, 20);
		contentPane.add(schemaCombo);
	}

	/*
	 * class ItemChangeListener implements ItemListener {
	 * 
	 * @Override public void itemStateChanged(ItemEvent event) { if
	 * (event.getStateChange() == ItemEvent.SELECTED) { Object item =
	 * event.getItem(); System.out.println(item.toString()); } } }
	 */

	public void assignDBEntity() {

		dbDetails = new DBDetails(connectionNameText.getText(),
				usernameText.getText(), String.valueOf(passwordText
						.getPassword()), hostNameText.getText(),
				portNameText.getText(), databaseNameText.getText(), dbType
						.getSelectedItem().toString(), schemaCombo
						.getSelectedItem().toString());
	}
}
