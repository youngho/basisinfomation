package com.test.util;
import com.test.opirus.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */
public class DbUtils {

	public Connection conn;
		
	public DbUtils() {
		conn = null;
		
	}
	
	public Connection getConnection() {
		String astrDriver, astrUrl, astrUser, astrPass;
		
		Properties prop = new Properties();		
		prop.loadProperties("opirus.properties");
		
		astrDriver = prop.getDbDriver();
//		astrUrl = prop.getDbUrl()+"databaseName="+prop.getDbName(); //mssql
		astrUrl = prop.getDbUrl(); //oracle
		astrUser = prop.getDbUser();
		astrPass = prop.getDbPass();
		
		try {
			
			Class.forName(astrDriver);  
			conn = DriverManager.getConnection(astrUrl, astrUser, astrPass);
			
			if (conn == null) {
				Utils.makeLog("[DbUtils getConnection ERROR] ");
				Utils.makeLog("ERROR","[OPIRUS Main getConnection ERROR] ");
				return null;
			} else {
				return conn;
			}
			
		} catch (ClassNotFoundException ex) {
			Utils.makeLog("[DbUtils getConnection ERROR] "+ex);
			Utils.makeLog("ERROR","[DbUtils DbUtils getConnection ERROR] "+ex);
			return null;
		}
		catch (SQLException ex) {
			Utils.makeLog("[DbUtils getConnection ERROR] "+ex);
			Utils.makeLog("ERROR","[OPIRUS DbUtils getConnection ERROR] "+ex);
			return null;
		}
		
	}

	/**
	 * 
	 * 
	 */
	public void closeConnection() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException ex) {
			Utils.makeLog("[DbUtils closeConnection ERROR] "+ex);
			Utils.makeLog("ERROR","[OPIRUS DbUtils closeConnection ERROR] "+ex);
		}finally{
			conn = null;
		}
	
	}
}
