package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.controller.MasterCommon;

public class PropsLoader extends MasterCommon {

	public static void loadProps() {
		queriesProps = new Properties();
		keywordsProps=new Properties();
		try {
			queriesProps.load(new FileInputStream(new File(
					"./props/queries.properties")));
			keywordsProps.load(new FileInputStream(new File(
					"./props/keywords.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
