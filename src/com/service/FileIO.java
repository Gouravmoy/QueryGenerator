package com.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.controller.MasterCommon;
import com.pojo.POJORow;
import com.ui.Tesla2;

public class FileIO extends MasterCommon {
	public static ArrayList<String> valueHolder = new ArrayList<String>();

	public static void writeToText(String input) {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("C:/Users/VBasidon/Desktop/abc.txt");
			completeRows.put(input, selectRows);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(completeRows);
			fout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static ArrayList<POJORow> getFromTextFile(String input) {
		HashMap<String, ArrayList<POJORow>> reconMap = new HashMap<String, ArrayList<POJORow>>();
		ArrayList<POJORow> returnValue = new ArrayList<POJORow>();
		FileInputStream fin;
		try {
			fin = new FileInputStream("C:/Users/VBasidon/Desktop/abc.txt");
			ObjectInputStream ois = new ObjectInputStream(fin);
			reconMap = (HashMap<String, ArrayList<POJORow>>) ois.readObject();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		returnValue = reconMap.get(input);
		if (input.equals("Select")) {
			for (int i = 0; i < returnValue.size(); i++) {
				POJORow row = returnValue.get(i);
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
