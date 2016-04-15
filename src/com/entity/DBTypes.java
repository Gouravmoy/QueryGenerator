package com.entity;

public enum DBTypes {

	SQL, DB2, Oracle;

	public static DBTypes convert(String dbType) {
		DBTypes dbTypes = null;
		if (dbType.equals("SQL"))
			dbTypes = DBTypes.SQL;
		else if (dbType.equals("DB2"))
			dbTypes = DBTypes.DB2;
		else if (dbType.equals("Oracle"))
			dbTypes = DBTypes.Oracle;
		else
			dbTypes = DBTypes.SQL;
		return dbTypes;
	}

}
