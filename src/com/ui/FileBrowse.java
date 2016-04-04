package com.ui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class FileBrowse extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JFileChooser fileChooser;

	public FileBrowse() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 782, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(61, 11, 678, 420);
		contentPane.add(panel);
		String path = System.getProperty("user.home") + "//Desktop//Query";
		fileChooser = new JFileChooser(path);
		panel.add(fileChooser);
	}

	public String getFilePath() {
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getAbsolutePath();
		} else
			return "";
	}

	public void saveFile() {

	}
}
