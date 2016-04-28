package com.service;

import java.util.ArrayList;

import com.controller.MasterCommon;
import com.pojo.POJORow;
import com.ui.Tesla2;
import com.util.QueryColorUtil;

public class Tesla2Functions extends Tesla2 {

	public Tesla2Functions(ArrayList<String> tables) {
		super(tables);
	}

	public static void displyQuery() {
		textArea.setText("");
		MasterCommon.completeQuery = "Select \n";
		for (int i = 0; i < MasterCommon.selectRows.size(); i++) {
			POJORow r = MasterCommon.selectRows.get(i);
			if (r.getTable().getTableName() == null) {
				MasterCommon.selectRows.remove(i);
			} else {
				if (r.getRowType() == null) {
					String tableColumn = "";
					tableColumn = r.getTable().getTableName() + "."
							+ r.getTable().getColumn().getColumnName();
					if (r.getConditionString().equals("")) {
						MasterCommon.completeQuery += tableColumn;
					} else {
						MasterCommon.completeQuery += r.getConditionString()
								.replace("#", tableColumn);
					}
					if (r.getElementname().equals("")) {
						MasterCommon.completeQuery += " , \n";
					} else {
						MasterCommon.completeQuery += " as '"
								+ r.getElementname() + "' ,\n";
					}

				} else if (r.getRowType().equals("Case")) {
					String query = TeslaTransFunctions.displayCaseQuery(r
							.getCaseRow());
					MasterCommon.completeQuery = MasterCommon.completeQuery
							+ query + " as '" + r.getElementname() + "' , \n ";
					textArea.setText(query);

				} else if (r.getRowType().equals("Coalesce")) {
					String query = "";
					if (r.getCoalesceString() != null) {
						query = TeslaTransFunctions.displayCoalesceQuery(r
								.getCoalesceRow());
						query += TeslaTransFunctions.displayCoalesceQuery_1(r
								.getCoalesceString());
					} else {
						query = TeslaTransFunctions.displayCoalesceQuery(r
								.getCoalesceRow());
					}
					MasterCommon.completeQuery = MasterCommon.completeQuery
							+ query + " as '" + r.getElementname() + "' , \n";
					textArea.setText(query);
				}
			}
		}
		textArea.setText(QueryColorUtil.queryColorChange(
				MasterCommon.completeQuery).toUpperCase());
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}
