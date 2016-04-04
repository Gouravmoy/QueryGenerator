package com.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.controller.MasterCommon;
import com.pojo.POJORow;
import com.ui.Tesla2;
import com.util.QueryIOUtil;

public class FileIO extends MasterCommon {
	public static ArrayList<String> valueHolder = new ArrayList<String>();

	public static void writeToText(String input) {
		queryUtil = new QueryIOUtil();
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(
					"C:/Users/ssinghal/Desktop/Temp/abc.txt");
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

	public static Object getFromTextFile(String input) {
		QueryIOUtil reconMap = new QueryIOUtil();
		ArrayList<Object> returnValue = new ArrayList<Object>();
		FileInputStream fin;
		try {
			fin = new FileInputStream("C:/Users/GMohanty/Desktop/Temp/abc.txt");
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
		if (input.equals("Select")) {
			returnValue.addAll(reconMap.getSelectRows());
			for (int i = 0; i < returnValue.size(); i++) {
				POJORow row = (POJORow) returnValue.get(i);
				tableHolder.put(i, row.getTable().getTableName());
				if (valueHolder.contains(row.getTable().getTableName())) {

				} else {
					valueHolder.add(row.getTable().getTableName());
					listPojoTable.add(row.getTable());
				}
				String valueQuery = row.getTable().getTableName() + "."
						+ row.getTable().getColumn().getColumnName() + " as '"
						+ row.getElementname() + "' ,";
				Tesla2.displyQuery(i, valueQuery);
			}
		}
		return returnValue;

	}
}
