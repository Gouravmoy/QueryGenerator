package com.util;

import com.controller.MasterCommon;

public class QueryColorUtil extends MasterCommon {

	public String queryColorChange(String query) {
		String[] queryParts = query.split("\n");
		String finalQuery = "";

		String keyWords = keywordsProps.getProperty("SQL_KEYWORDS");
		String[] parts = keyWords.split(",");
		for (int j = 0; j < queryParts.length; j++) {
			String Query = "";
			System.out.println(queryParts[j]);
			String[] queryPartsSplit = queryParts[j].split(" ");
			for (int k = 0; k < queryPartsSplit.length; k++) {
				System.out.println(queryPartsSplit.length);
				int count = 0;
				for (int i = 0; i < parts.length; i++) {
					/*
					 * if (parts[i].equals("SELECT")) {
					 * System.out.println("BBKBK"); }
					 */
					if ((queryPartsSplit[k].trim()).equalsIgnoreCase(parts[i])) {
						System.out.println(parts[i]);

						queryPartsSplit[k] = "<font size=\"3\" face=\"verdana\" color=\"blue\">"
								+ queryPartsSplit[k] + "</font>";
						Query = Query + queryPartsSplit[k]+" ";
						System.out.println(Query);
						count++;

					}
				}
				if (count == 0) {
					Query = Query + queryPartsSplit[k]+" ";
				}
			}
			
			finalQuery = finalQuery + Query + "<p></p>";

		}
		System.out.println(finalQuery);
		finalQuery = "<html>" + finalQuery + "</html>";
		System.out.println(finalQuery);
		return finalQuery;

	}
}
