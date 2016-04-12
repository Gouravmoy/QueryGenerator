package com.util;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {
	public ExcelExporter() {
	}

	public void exportTable(JTable table, String filePath) throws IOException {
		// new WorkbookFactory();
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Export"); // WorkSheet
		XSSFRow row = sheet.createRow(2); // Row created at line 3
		TableModel model = table.getModel(); // Table model

		XSSFRow headerRow = sheet.createRow(0); // Create row at line 0
		for (int headings = 0; headings < model.getColumnCount(); headings++) { // For
																				// each
																				// column
			headerRow.createCell(headings).setCellValue(
					model.getColumnName(headings));// Write column name
		}

		for (int rows = 0; rows < model.getRowCount(); rows++) { // For each
																	// table row
			for (int cols = 0; cols < table.getColumnCount(); cols++) { // For
																		// each
																		// table
																		// column
				try {
					if (model.getValueAt(rows, cols) != null)
						row.createCell(cols).setCellValue(
								model.getValueAt(rows, cols).toString()); // Write
																			// value
					else
						row.createCell(cols).setCellValue("");
				} catch (Exception err) {
					System.out.println("Here");
				}
			}

			// Set the row to the next one in the sequence
			row = sheet.createRow((rows + 3));
		}
		wb.write(new FileOutputStream(filePath));// Save the file
		wb.close();
	}
}
