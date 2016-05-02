package com.bnade.wow.dao.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

import org.bson.Document;

import com.bnade.util.MongoUtil;
import com.bnade.wow.dao.TaskStatusDao;
import com.bnade.wow.po.ArchivedTask;
import com.mongodb.client.MongoCollection;

public class TaskStatusDaoImpl implements TaskStatusDao {
	
	private static final String COLLECTION_NAME_PREFIX = "taskStatus_";
	private static final String ARCHIVE_TASK = "archiveTask";

	@Override
	public void addArchivedTask(ArchivedTask task) {
		MongoCollection<Document> coll = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + ARCHIVE_TASK);
		coll.insertOne(new Document("realmId", task.getRealmId()).append("date", task.getDate()));
	}

	@Override
	public ArchivedTask getArchivedTask(ArchivedTask task) {
		MongoCollection<Document> coll = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + ARCHIVE_TASK);
		Document doc = coll.find(and(eq("realmId", task.getRealmId()), eq("date", task.getDate()))).first();
		if (doc != null) {
			ArchivedTask tmpTask = new ArchivedTask();
			tmpTask.setRealmId(doc.getInteger("realmId"));
			tmpTask.setDate(doc.getString("date"));
			return tmpTask;
		}
		return null;
	}

}
