package com.util;

import java.io.IOException;
import java.util.Properties;

import com.controller.MasterCommon;

public class PropsLoader extends MasterCommon {

	public void loadProps() {
		queriesProps = new Properties();
		keywordsProps = new Properties();
		try {
			queriesProps = new Properties();
			keywordsProps = new Properties();
			queriesProps.load(getClass().getResourceAsStream("/props/queries.properties"));
			keywordsProps.load(getClass().getResourceAsStream("/props/keywords.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
