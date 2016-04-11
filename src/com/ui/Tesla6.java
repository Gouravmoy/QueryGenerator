package com.ui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLEditorKit;

import org.jsoup.Jsoup;

import com.controller.MasterCommon;
import com.exceptions.QueryExecutionException;
import com.model.QueryTableModel;
import com.util.DBUtil;
import com.util.ExcelExporter;
import com.util.QueryUtil;

public class Tesla6 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextPane textArea;
	public JTable queryResultTable;
	public QueryTableModel queryTableModel;
	public JButton exportButton;
	public JButton executeQueryBtn;
	public static String queryToExecute;

	public Tesla6(String status) {
		initialize(status);
	}

	@SuppressWarnings("serial")
	private void initialize(String status) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(828, 561);

		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("Test Your Query");
		queryToExecute = "";
		textArea = new JTextPane();
		textArea.setBounds(10, 11, 772, 176);

		textArea.setEditorKit(new HTMLEditorKit());
		if (!status.equals("DIRRECT")) {
			QueryUtil.updateQuery(textArea, "INDIRECT");
		}

		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(10, 11, 791, 197);
		contentPane.add(scrollPane_1);

		queryTableModel = new QueryTableModel();

		queryResultTable = new JTable() {
			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}
		};
		queryResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		queryResultTable.setModel(queryTableModel);
		queryResultTable.setBounds(10, 264, 791, 248);
		queryResultTable.setRowHeight(25);

		JScrollPane queryResult = new JScrollPane(queryResultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		queryResult.setBounds(10, 264, 791, 248);
		contentPane.add(queryResult);

		JButton backButton = new JButton("BACK");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Tesla5().setVisible(true);
				dispose();
			}
		});
		backButton.setIcon(new ImageIcon(Tesla6.class.getResource("/png/back.png")));
		backButton.setBounds(39, 219, 135, 34);
		contentPane.add(backButton);

		executeQueryBtn = new JButton("EXECUTE QUERY");
		executeQueryBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					if (status.equals("INDIRRECT")) {
						queryToExecute = MasterCommon.mainQuery.toString();
					} else {
						queryToExecute = Jsoup.parse(textArea.getText().replace(";", "")).text();
					}
					if (DBUtil.testFinalQuery(queryToExecute)) {
						queryTableModel.executeQueryAndUI(queryToExecute);
						exportButton.setEnabled(true);
					}
				} catch (HeadlessException | QueryExecutionException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Query Failed! Reason - " + e.getMessage());
				}

			}
		});
		executeQueryBtn.setIcon(new ImageIcon(Tesla6.class.getResource("/png/lightning.png")));
		executeQueryBtn.setBounds(245, 219, 135, 34);
		contentPane.add(executeQueryBtn);

		exportButton = new JButton("EXPORT RESULT");
		exportButton.setEnabled(false);
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TeslaFileBrowse fileBrowse = new TeslaFileBrowse("xlsx", "SAVE");
				String filePath = fileBrowse.getFilePath();
				if (!filePath.endsWith(".xlsx"))
					filePath = filePath + ".xlsx";
				ExcelExporter exp = new ExcelExporter();
				try {
					exp.exportTable(queryResultTable, filePath);
					JOptionPane.showMessageDialog(null, "Result Successfully Exported!");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Export Failed! Reason - " + e1.getMessage());
				}

			}
		});
		exportButton.setIcon(new ImageIcon(Tesla6.class.getResource("/png/excel-icon.png")));
		exportButton.setBounds(641, 219, 135, 34);
		contentPane.add(exportButton);

		JButton btnRefresh = new JButton("REFRESH");
		btnRefresh.setIcon(new ImageIcon(Tesla6.class.getResource("/png/refresh.png")));
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				queryToExecute = textArea.getText().replace("\\;", "");
				QueryUtil.updateQuery(textArea, status);
			}
		});
		btnRefresh.setBounds(446, 219, 135, 34);
		contentPane.add(btnRefresh);

	}
}
