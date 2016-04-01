package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.controller.MasterCommon;
import com.pojo.InnerJoinRow;
import com.pojo.POJORow;
import com.ui.Tesla2;
import com.util.QueryIOUtil;
import com.util.QueryUtil;

public class FileIO extends MasterCommon {
	public static ArrayList<String> valueHolder = new ArrayList<String>();

	public static void writeToText() {
		Date d = new Date();
		SimpleDateFormat form = new SimpleDateFormat("dd_mm_yyyy_hh_mm_ss");
		queryUtil = new QueryIOUtil();
		FileOutputStream fout;
		try {
			String path = System.getProperty("user.home") + "//Desktop//Query//Query" + form.format(d) + ".txt";
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			queryUtil.setSelectRows(selectRows);
			queryUtil.setConditionRows(joinRows);
			oos.writeObject(queryUtil);
			fout.close();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getFromTextFile(String input) {
		QueryIOUtil reconMap = new QueryIOUtil();
		FileInputStream fin;
		try {
			fin = new FileInputStream(input);
			ObjectInputStream ois = new ObjectInputStream(fin);
			reconMap = (QueryIOUtil) ois.readObject();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		selectRows.addAll(reconMap.getSelectRows());
		joinRows.addAll(reconMap.getConditionRows());
		for (InnerJoinRow innerJoinRow : joinRows) {
			innerJoinRow.setStatus(false);
			
		}
		QueryUtil.updateInnerJoinMap(joinRows);
		QueryUtil.buildQuery();
		for (int i = 0; i < selectRows.size(); i++) {
			POJORow row = (POJORow) selectRows.get(i);
			tableHolder.put(i, row.getTable().getTableName());
			if (valueHolder.contains(row.getTable().getTableName())) {

			} else {
				valueHolder.add(row.getTable().getTableName());
				listPojoTable.add(row.getTable());
			}
			String valueQuery = row.getTable().getTableName() + "." + row.getTable().getColumn().getColumnName()
					+ " as '" + row.getElementname() + "' ,";
			Tesla2.displyQuery(i, valueQuery);
		}

	}
}
