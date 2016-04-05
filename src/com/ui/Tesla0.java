package com.ui;

import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Tesla0 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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

		JMenu mnDatabase = new JMenu("Database");
		menuBar.add(mnDatabase);

		JMenuItem mntmAddDatabase = new JMenuItem("New Database Conncetion");
		mntmAddDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TeslaDBDetails().setVisible(true);
			}
		});

		mnDatabase.add(mntmAddDatabase);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);

		Panel panel = new Panel();
		panel.setBounds(10, 67, 191, 389);
		contentPane.add(panel);

		Panel panel_1 = new Panel();
		panel_1.setBounds(207, 67, 477, 389);
		contentPane.add(panel_1);
	}
}
