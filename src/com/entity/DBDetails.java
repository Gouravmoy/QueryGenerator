package com.entity;

import java.io.Serializable;

public class DBDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	String connectionName;
	String userName;
	String password;
	String hostName;
	String port;
	String database;
	String dbType;
	String dbSchema;

	public DBDetails(String connectionName, String userName, String password,
			String hostName, String port, String database, String dbType,
			String dbSchema) {
		super();
		this.connectionName = connectionName;
		this.userName = userName;
		this.password = password;
		this.hostName = hostName;
		this.port = port;
		this.database = database;
		this.dbType = dbType;
		this.dbSchema = dbSchema;
	}

	public DBDetails() {
	}

	public String getDbSchema() {
		return dbSchema;
	}

	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DBDetails [connectionName=" + connectionName + ", userName="
				+ userName + ", password=" + password + ", hostName="
				+ hostName + ", port=" + port + ", database=" + database
				+ ", dbType=" + dbType + ", dbSchema=" + dbSchema + "]";
	}

}
