package com.util;

import com.controller.MasterCommon;

public class QueryColorUtil extends MasterCommon {

       public static String queryColorChange(String query) {
              System.out.println("query" + query);

              String[] queryParts = query.split("\n");
              String finalQuery = "";

              String keyWords = keywordsProps.getProperty("SQL_KEYWORDS");
              String[] parts = keyWords.split(",");
              for (int j = 0; j < queryParts.length; j++) {
                     String Query = "";
                     int flag = 0;
                     String[] queryPartsSplit = queryParts[j].split(" ");
                     for (int k = 0; k < queryPartsSplit.length; k++) {

                           int count = 0;

                           for (int i = 0; i < parts.length; i++) {
                                  if (queryPartsSplit[k].equalsIgnoreCase("'aaa'")) {
                                         System.out.println(queryPartsSplit[k]);
                                  }
                                  /*
                                  * if (parts[i].equals("SELECT")) {
                                  * System.out.println("BBKBK"); }
                                  */
                                  if ((queryPartsSplit[k].trim()).equalsIgnoreCase(parts[i])) {

                                         queryPartsSplit[k] = "<font size=\"3\" face=\"verdana\" color=\"blue\">"
                                                       + queryPartsSplit[k] + "</font>";
                                         Query = Query + queryPartsSplit[k] + " ";

                                         count++;
                                         break;

                                  } else if ((queryPartsSplit[k].startsWith("'"))) {

                                         count++;
                                         flag++;
                                         break;

                                  }
                           }
                           if (flag == 1) {
                                  if (queryPartsSplit[k].endsWith("'")) {
                                         flag = 0;

                                  }
                                  queryPartsSplit[k] = "<font size=\"3\" face=\"verdana\" color=\"green\">"
                                                + queryPartsSplit[k] + "</font>";
                                  Query = Query + queryPartsSplit[k] + " ";

                                  count++;

                           } else if (count == 0) {
                                  Query = Query + queryPartsSplit[k] + " ";
                           }
                     }

                     finalQuery = finalQuery + Query + "<br>";

              }
              System.out.println(finalQuery);
              finalQuery = "<html>" + finalQuery + "</html>";
              // System.out.println(finalQuery);
              return finalQuery;

       }
}

