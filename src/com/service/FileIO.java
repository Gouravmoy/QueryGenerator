package com.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.controller.MasterCommon;
import com.entity.DBDetails;
import com.exceptions.DBAlreadyExists;
import com.exceptions.NoJoinPossible;
import com.pojo.InnerJoinRow;
import com.pojo.POJOTable;
import com.ui.Tesla2;
import com.util.QueryIOUtil;
import com.util.QueryUtil;

public class FileIO extends MasterCommon {
	public static ArrayList<String> valueHolder = new ArrayList<String>();
	static final Logger logger = Logger.getLogger(FileIO.class);

	public static void writeToText(String filePath) {
		queryUtil = new QueryIOUtil();
		FileOutputStream fout;
		String path = "";
		try {

			path = filePath;
			if (!filePath.endsWith(".ser"))
				path = filePath + ".ser";

			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			queryUtil.setSelectRows(selectRows);
			queryUtil.setConditionRows(joinRows);
			queryUtil.setSelectTables(listPojoTable);
			queryUtil.setWhereRows(whereRows);
			queryUtil.setDbName(MasterCommon.selectedDBName);
			oos.writeObject(queryUtil);
			fout.close();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeFullQueryToFile(String filePath) {
		BufferedWriter writer;
		String path = "";
		try {
			if (!filePath.endsWith(".sql"))
				path = filePath + ".sql";
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(MasterCommon.mainQuery.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeTempData() {
		Date d = new Date();
		SimpleDateFormat form = new SimpleDateFormat("dd_mm_yyyy_hh_mm_ss");
		queryUtil = new QueryIOUtil();
		FileOutputStream fout;
		try {

			String path = masterPath + "Query" + form.format(d) + ".txt";

			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			queryUtil.setSelectRows(selectRows);
			queryUtil.setConditionRows(joinRows);
			queryUtil.setSelectTables(listPojoTable);
			queryUtil.setWhereRows(whereRows);
			queryUtil.setDbName(MasterCommon.selectedDBName);
			oos.writeObject(queryUtil);
			fout.close();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void saveDBDetails(DBDetails dbDetails, boolean isEdit)
			throws DBAlreadyExists {
		FileOutputStream fout;
		String path = masterPath + "DBCredentials//"
				+ dbDetails.getConnectionName() + ".txt";

		File file = new File(path);
		if (!file.exists() || isEdit == false) {
			try {
				if (isEdit == true) {
					file.createNewFile();
				}
				fout = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(dbDetails);
				fout.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new DBAlreadyExists("Duplicate Connection Name");
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
		listPojoTable.addAll(reconMap.getSelectTables());
		whereRows.addAll(reconMap.getWhereRows());
		MasterCommon.selectedDBName = reconMap.getDbName();
		for (InnerJoinRow innerJoinRow : joinRows) {
			innerJoinRow.setStatus(false);
		}
		QueryUtil.updateInnerJoinMap(joinRows);
		QueryUtil.buildQuery();
		for (int i = 0; i < listPojoTable.size(); i++) {
			POJOTable table = (POJOTable) listPojoTable.get(i);
			tableHolder.put(i, table.getTableName());
			valueHolder.add(table.getTableName());
		}
		Tesla2Functions.displyQuery(Tesla2.textArea);
	}

	public static ArrayList<DBDetails> getDBConnectionsFromText() {
		ArrayList<DBDetails> dbDetails = new ArrayList<>();
		DBDetails dbDetail;
		File folder = new File(masterPath + "DBCredentials//");
		FileInputStream fin = null;
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isFile()) {
				dbDetail = new DBDetails();
				try {
					fin = new FileInputStream(fileEntry);
					ObjectInputStream ois = new ObjectInputStream(fin);
					dbDetail = (DBDetails) ois.readObject();
					dbDetails.add(dbDetail);
					fin.close();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						fin.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return dbDetails;
	}

	public static void deleteDBConnection(String selectedDBName) {
		try {

			File file = new File(masterPath + "DBCredentials//"
					+ selectedDBName + ".txt");

			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
