package com.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLEditorKit;

import com.controller.MasterCommon;
import com.exceptions.QueryExecutionException;
import com.model.QueryTableModel;
import com.util.DBUtil;
import com.util.QueryUtil;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Tesla6 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextPane textArea;
	public JTable queryResultTable;
	public QueryTableModel queryTableModel;

	public Tesla6() {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 561);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea = new JTextPane();
		textArea.setBounds(10, 11, 772, 176);
		textArea.setEditorKit(new HTMLEditorKit());

		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(10, 11, 791, 197);
		contentPane.add(scrollPane_1);

		QueryUtil.updateQuery(textArea);

		queryTableModel = new QueryTableModel();

		queryResultTable = new JTable();
		queryResultTable.setModel(queryTableModel);
		queryResultTable.setBounds(10, 264, 791, 248);
		queryResultTable.setRowHeight(25);

		JScrollPane queryResult = new JScrollPane(queryResultTable);
		queryResult.setBounds(10, 264, 791, 248);
		contentPane.add(queryResult);

		JButton btnNewButton = new JButton("BACK");
		btnNewButton.setIcon(new ImageIcon(Tesla6.class.getResource("/png/back.png")));
		btnNewButton.setBounds(158, 219, 135, 34);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("EXECUTE QUERY");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					if (DBUtil.testFinalQuery(MasterCommon.mainQuery.toString())) {
						queryTableModel.executeQueryAndUI();
					}
				} catch (HeadlessException | QueryExecutionException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Query Failed! Reason - " + e.getMessage());
				}

			}
		});
		btnNewButton_1.setIcon(new ImageIcon(Tesla6.class.getResource("/png/lightning.png")));
		btnNewButton_1.setBounds(314, 219, 135, 34);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("EXPORT RESULT");
		btnNewButton_2.setIcon(new ImageIcon(Tesla6.class.getResource("/png/excel-icon.png")));
		btnNewButton_2.setBounds(478, 219, 135, 34);
		contentPane.add(btnNewButton_2);
	}
}
