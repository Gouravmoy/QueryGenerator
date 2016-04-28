package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoSuggestInnerJoin {

	public static List<Table> getTableMeta() {
		String currline;
		File file = new File("C:/Users/lenovo/Downloads/sakila-db/FK.txt");
		List<Table> tables = null;
		List<String> POK;
		List<String> FOK;
		Table table;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String[] split;
			tables = new ArrayList<>();
			while ((currline = reader.readLine()) != null) {
				split = currline.split("\\|");
				table = ifTableExists(tables, split);
				if (table == null) {
					table = new Table();
					table.setTableName(split[0]);
					if ("NULL".equals(split[2])) {
						POK = new ArrayList<>();
						POK.add(split[1]);
						table.setPrimarykeys(POK);
					} else {
						FOK = new ArrayList<>();
						FOK.add(split[1] + "|" + split[2] + "|" + split[3]);
						table.setForeignKeys(FOK);
					}
					tables.add(table);
				} else {
					if ("NULL".equals(split[2])) {
						POK = table.getPrimarykeys();
						if (POK == null)
							POK = new ArrayList<>();
						POK.add(split[1]);
						table.setPrimarykeys(POK);
					} else {
						FOK = table.getForeignKeys();
						if (FOK == null)
							FOK = new ArrayList<>();
						FOK.add(split[1] + "|" + split[2] + "|" + split[3]);
						table.setForeignKeys(FOK);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(tables);
		return tables;
	}

	private static Table ifTableExists(List<Table> tables, String[] split) {
		for (Table table : tables) {
			if (table.getTableName().equals(split[0])) {
				return table;
			}
		}
		return null;
	}

}
