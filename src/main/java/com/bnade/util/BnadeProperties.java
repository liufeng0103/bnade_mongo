package com.bnade.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BnadeProperties {

	private static final String BNADE_FILE_PATH = "bnade.properties";
	
	private static Properties bnadeProperties;
	
	private static void load() {
		if (bnadeProperties == null) {
			bnadeProperties = new Properties();
			try {
				InputStream is = BnadeProperties.class.getClassLoader().getResourceAsStream(BNADE_FILE_PATH);
				if (is == null) {
					is = new FileInputStream(BNADE_FILE_PATH);
				}
				bnadeProperties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static long getTask1Interval() {
		load();
		return Long.valueOf(bnadeProperties.getProperty("interval", "2100000"));
	}
	
	public static int getTask1ThreadCount() {
		load();
		return Integer.valueOf(bnadeProperties.getProperty("task1_thread_count", "8"));
	}
	
	public static int getTask2RunCountPerTime() {
		load();
		return Integer.valueOf(bnadeProperties.getProperty("task2_realm_count_per_time", "30"));
	}
	
	public static String getApiKey() {
		load();
		return bnadeProperties.getProperty("api_key");
	}
	
	public static String getAuctionHistoryDir() {
		load();
		return bnadeProperties.getProperty("auction_history_dir", "./auction_history");
	}
	
	public static String getRegion() {
		load();
		return bnadeProperties.getProperty("region","GF");
	}
	
	public static long getTask5WaitTime(){
		load();
		return Long.valueOf(bnadeProperties.getProperty("task_5_waittime", "300000"));
	}
	
}
