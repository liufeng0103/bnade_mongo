package com.bnade.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
	
	private static final String WOW_DB_NAME = "wow";

	private static final MongoClient mongoClient = new MongoClient();
	
	public static MongoClient getInstance() {
		return mongoClient;
	}
	
	public static MongoDatabase getWowDB() {
		return mongoClient.getDatabase(WOW_DB_NAME);
	}
}
