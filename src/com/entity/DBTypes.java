package com.entity;

public enum DBTypes {

	SQL, DB2, ORACLE;

	public static DBTypes convert(String dbType) {
		DBTypes dbTypes;
		if (("SQL").equals(dbType))
			dbTypes = DBTypes.SQL;
		else if (("DB2").equals(dbType))
			dbTypes = DBTypes.DB2;
		else if (("Oracle").equals(dbType))
			dbTypes = DBTypes.ORACLE;
		else
			dbTypes = DBTypes.SQL;
		return dbTypes;
	}

}
