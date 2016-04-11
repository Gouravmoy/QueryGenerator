package com.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

public class TeslaFileBrowse extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JFileChooser fileChooser;
	int option = 0;

	public TeslaFileBrowse(String extension, String opereation) {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(782, 455);

		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(61, 11, 678, 420);
		contentPane.add(panel);
		String path = System.getProperty("user.home") + "//Desktop//Query";
		fileChooser = new JFileChooser(path);
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith("." + extension) || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "." + extension;
			}

		});

		if (opereation.equals("SAVE")) {
			fileChooser.setDialogTitle("Specify a file to save");
			option = fileChooser.showSaveDialog(this);

		} else {
			fileChooser.setDialogTitle("Open a Query");
			option = fileChooser.showOpenDialog(this);
		}

		panel.add(fileChooser);
	}

	public String getFilePath() {
		if (option == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getAbsolutePath();
		} else
			return "";
	}

	public void saveFile() {

	}
}
