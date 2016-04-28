package com.bnade.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

public class DBUtil {
	
	private static final String CONFIG_FILE = "jdbc.properties";
	private static DataSource DS;
	
	public static DataSource getDataSource() {
		if (DS == null) {
			Properties pops = new Properties();
			try {
				pops.load(DBUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
				DS = BasicDataSourceFactory.createDataSource(pops);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return DS;
	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println(DBUtil.getDataSource().getConnection() != null);
	}
}
