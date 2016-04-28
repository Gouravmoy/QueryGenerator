package com.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableColumn;
import javax.swing.text.html.HTMLEditorKit;

import com.celleditor.ColumnCellEditor;
import com.celleditor.DropDownCellEditor;
import com.celleditor.TableEditor;
import com.controller.MasterCommon;
import com.entity.Tables;
import com.model.TableModel;
import com.pojo.POJORow;
import com.renderer.ColumnCellRenderer;
import com.renderer.TableCellRenderer;
import com.service.FileIO;
import com.service.Tesla2Functions;
import com.util.ColsUtil;
import com.util.DBUtil;

public class Tesla2 {

	private JFrame frmQuerybuilder;
	private TableModel tableModel;
	private List<Tables> tables;
	private JTable table;
	public static JTextPane textArea = new JTextPane();;
	List<POJORow> listRow = new ArrayList<>();
	int caseCount = 0;
	static int deleteRow = 0;
	JButton btnDelete;
	JButton btnEdit;

	public Tesla2(List<String> tempTableNames) {
		tempTableNames.addAll(FileIO.valueHolder);
		this.tables = DBUtil.getTablesMetaInfo(tempTableNames);
		MasterCommon.listTable.clear();
		MasterCommon.listTable.addAll(this.tables);
		ColsUtil.setPOJOClass();
		initialize();
	}

	private void initialize() {
		frmQuerybuilder = new JFrame();
		frmQuerybuilder.getContentPane().setLayout(null);
		frmQuerybuilder.setTitle("Select Window");
		JPanel panel = new JPanel();
		panel.setBounds(22, 264, 769, 207);
		frmQuerybuilder.getContentPane().add(panel);
		panel.setLayout(null);
		textArea.setEditorKit(new HTMLEditorKit());
		textArea.setBounds(1, 1, 767, 195);
		panel.add(textArea);
		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(0, 0, 769, 207);
		panel.add(scrollPane_1);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 11, 792, 195);
		frmQuerybuilder.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 772, 174);
		panel_1.add(scrollPane);

		JPopupMenu popupMenu = new JPopupMenu();

		table = new JTable();
		addPopup(table, popupMenu);
		scrollPane.setViewportView(table);
		JButton btnAdd = new JButton("ADD");
		btnAdd.setIcon(new ImageIcon(Tesla2.class.getResource("/png/addd.png")));
		btnAdd.setBounds(96, 216, 121, 37);
		frmQuerybuilder.getContentPane().add(btnAdd);
		tableModel = new TableModel(MasterCommon.selectRows);
		table.setModel(tableModel);
		table.setRowHeight(25);

		Tesla2Functions.displyQuery();
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MasterCommon.selectRows.remove(deleteRow);
				tableModel.updateUI();
				table.editCellAt(-1, -1);
				Tesla2Functions.displyQuery();
			}
		});
		popupMenu.add(btnDelete);

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		JButton btnNext = new JButton("NEXT");
		btnNext.setIcon(new ImageIcon(Tesla2.class.getResource("/png/next.png")));
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				table.editCellAt(-1, -1);
				Tesla2Functions.displyQuery();
				MasterCommon.completeQuery = MasterCommon.completeQuery
						.replaceAll(", $", "").toUpperCase() + " FROM \n";
				frmQuerybuilder.dispose();
				new Tesla4().setVisible(true);
			}
		});
		btnNext.setBounds(627, 216, 111, 37);
		frmQuerybuilder.getContentPane().add(btnNext);

		JButton btnAddTransformation = new JButton("ADD TRANSFORMATION");
		btnAddTransformation.setIcon(new ImageIcon(Tesla2.class
				.getResource("/png/transform_flip.png")));
		btnAddTransformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				POJORow rowCase = tableModel.updateUI1();
				table.scrollRectToVisible(table.getCellRect(
						table.getRowCount() - 1, 0, true));
				new TeslaTransforms(rowCase);
			}
		});
		btnAddTransformation.setBounds(423, 216, 184, 37);
		frmQuerybuilder.getContentPane().add(btnAddTransformation);

		JButton btnNewButton = new JButton("REFRESH");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				table.editCellAt(-1, -1);
				Tesla2Functions.displyQuery();
			}
		});
		btnNewButton.setIcon(new ImageIcon(Tesla2.class
				.getResource("/png/refresh.png")));
		btnNewButton.setBounds(260, 217, 133, 36);
		frmQuerybuilder.getContentPane().add(btnNewButton);
		TableColumn tableColumn = table.getColumn("TableName");
		TableColumn columnColumn = table.getColumn("ColumnName");
		TableColumn columnCondition = table.getColumn("Conditions");
		tableColumn.setCellRenderer(new TableCellRenderer());
		tableColumn.setCellEditor(new TableEditor(MasterCommon.listPojoTable));
		columnColumn.setCellRenderer(new ColumnCellRenderer());
		columnColumn.setCellEditor(new ColumnCellEditor(
				MasterCommon.listPojoCols));
		columnCondition.setCellEditor(new DropDownCellEditor(
				MasterCommon.stringConditions));
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				table.editCellAt(-1, -1);
				Tesla2Functions.displyQuery();
				tableModel.updateUI();
				table.scrollRectToVisible(table.getCellRect(
						table.getRowCount() - 1, 0, true));

			}

		});
		frmQuerybuilder.setVisible(false);
		frmQuerybuilder.setSize(828, 520);

		frmQuerybuilder.setLocationRelativeTo(null);

		frmQuerybuilder.setVisible(true);
		frmQuerybuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					System.out.println("here 1");
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					System.out.println("here 2");
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				System.out.println("here 3");
				popup.show(e.getComponent(), e.getX(), e.getY());
				deleteRow = e.getY() / 25;
				if (MasterCommon.selectRows.get(deleteRow).getRowType() != null) {
					popup.add(btnEdit);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

}
