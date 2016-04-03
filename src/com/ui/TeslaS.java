package com.ui;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.controller.Controller;
import com.service.FileIO;

public class TeslaS extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	private JTextField textUrl;
	private JTextField textDbName;
	private JTextField textUserName;
	private JTextField textPassword;
	private JTextField textSchemaName;
	private ArrayList<String> tableNames;
	private ArrayList<String> selectedTableNames;
	private JCheckBox[] tablesCheckBoxList;
	JButton btnFetchColumns;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeslaS window = new TeslaS();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TeslaS() {
		tableNames = new ArrayList<String>();
		selectedTableNames = new ArrayList<String>();
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

		JLabel lblUrl = new JLabel("SERVER URL");
		lblUrl.setBounds(10, 27, 78, 14);
		frame.getContentPane().add(lblUrl);

		textUrl = new JTextField();
		textUrl.setBounds(84, 24, 86, 20);
		frame.getContentPane().add(textUrl);
		textUrl.setColumns(10);

		JLabel lblDbName = new JLabel("DB Name");
		lblDbName.setBounds(194, 27, 65, 14);
		frame.getContentPane().add(lblDbName);

		textDbName = new JTextField();
		textDbName.setBounds(269, 24, 86, 20);
		frame.getContentPane().add(textDbName);
		textDbName.setColumns(10);

		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(378, 27, 88, 14);
		frame.getContentPane().add(lblUsername);

		textUserName = new JTextField();
		textUserName.setBounds(476, 24, 86, 20);
		frame.getContentPane().add(textUserName);
		textUserName.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(592, 27, 69, 14);
		frame.getContentPane().add(lblPassword);

		textPassword = new JTextField();
		textPassword.setBounds(671, 24, 86, 20);
		frame.getContentPane().add(textPassword);
		textPassword.setColumns(10);

		Button button = new Button("Fetch Tables");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * if (textSchemaName.getText().equals("") ||
				 * textUserName.getText().equals("") ||
				 * textPassword.getText().equals("")) { JOptionPane
				 * .showMessageDialog(null,
				 * "Please fill UserName, Password and SchemaName the Feilds");
				 * } else {
				 */
				/*
				 * tableNames = Controller.getTables(textSchemaName.getText(),
				 * textUrl.getText(), textDbName.getText(),
				 * textUserName.getText(), textPassword.getText());
				 */
				tableNames = Controller.getTables("test", "", "", "root", "Welcome123");
				frame.repaint();
				tablesCheckBoxList = new JCheckBox[tableNames.size()];
				int loc = 106;
				for (int i = 0; i < tableNames.size(); i++) {
					tablesCheckBoxList[i] = new JCheckBox(tableNames.get(i).toUpperCase());
					tablesCheckBoxList[i].setBounds(39, loc, 150, 23);
					loc += 20;
					tablesCheckBoxList[i].setVisible(true);
					tablesCheckBoxList[i].setFocusTraversalKeysEnabled(false);
					frame.getContentPane().add(tablesCheckBoxList[i]);
				}
				btnFetchColumns = new JButton("Fetch Columns");
				btnFetchColumns.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						for (JCheckBox checkBox : tablesCheckBoxList) {
							if (checkBox.isSelected())
								selectedTableNames.add(checkBox.getText());
						}
						frame.dispose();
						new Tesla2(selectedTableNames);

					}
				});
				btnFetchColumns.setBounds(347, 421, 119, 31);
				frame.getContentPane().add(btnFetchColumns);
				frame.getContentPane().add(btnFetchColumns);
				frame.repaint();
			}
		});
		button.setBounds(671, 58, 86, 22);
		frame.getContentPane().add(button);

		textSchemaName = new JTextField();
		textSchemaName.setColumns(10);
		textSchemaName.setBounds(66, 55, 86, 20);
		frame.getContentPane().add(textSchemaName);

		JLabel label = new JLabel("SCHEMA");
		label.setBounds(10, 58, 46, 14);
		frame.getContentPane().add(label);

		JButton btnNewButton = new JButton("Brows File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileBrowse fileBrowse = new FileBrowse();
				String filePath = fileBrowse.getFilePath();
				tableNames = Controller.getTables("test", "", "", "root", "Welcome123");
				if (tablesCheckBoxList != null) {
					for (JCheckBox checkBox : tablesCheckBoxList) {
						if (checkBox.isSelected())
							selectedTableNames.add(checkBox.getText());
					}
				}
				FileIO.getFromTextFile(filePath);
				frame.dispose();
				new Tesla2(selectedTableNames);

			}
		});
		btnNewButton.setBounds(655, 86, 122, 38);
		frame.getContentPane().add(btnNewButton);

	}
}
