package com.bnade.wow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.MongoUtil;
import com.bnade.wow.dao.WowTokenDao;
import com.bnade.wow.po.WowToken;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

/**
 * 时光徽章相关的数据库操作，mongodb实现
 * 
 * @author liufeng0103
 *
 */
public class WowTokenDaoImpl implements WowTokenDao {

	private static final String WOWTOKEN_COLLECTION_NAME = "wowtoken";
	
	private static Logger logger = LoggerFactory.getLogger(WowTokenDaoImpl.class);

	private MongoCollection<Document> collection;

	public WowTokenDaoImpl() {
		collection = MongoUtil.getWowDB().getCollection(
				WOWTOKEN_COLLECTION_NAME);
	}

	@Override
	public void save(WowToken wowToken) {
		Document document = new Document();
		document.append("buy", wowToken.getBuy()).append("updated",
				wowToken.getUpdated());
		collection.insertOne(document);
	}
	
	@Override
	public void save(List<WowToken> wowTokens) {
		if (wowTokens != null && wowTokens.size() != 0) {
			List<Document> docs = new ArrayList<>();
			for (WowToken wowToken : wowTokens) {
				Document doc = new Document();
				doc.append("buy", wowToken.getBuy()).append("updated",
						wowToken.getUpdated());
				docs.add(doc);
			}
			collection.insertMany(docs);	
		}		
	}

	@Override
	public WowToken getByUpdated(long updated) {
		Document document = collection.find(new Document("updated", updated))
				.first();
		if (document != null) {
			WowToken wowToken = new WowToken();
			wowToken.setBuy(document.getInteger("buy"));
			wowToken.setUpdated(document.getLong("updated"));
			return wowToken;
		}
		return null;
	}

	@Override
	public List<WowToken> getAll() {
		FindIterable<Document> wowtokens = collection.find();
		if (wowtokens != null) {
			ArrayList<WowToken> wowtokenList = new ArrayList<>();
			wowtokens.forEach(new Block<Document>() {
				@Override
				public void apply(Document document) {
					WowToken token = new WowToken();
					token.setBuy(document.getInteger("buy"));
					token.setUpdated(document.getLong("updated"));
					wowtokenList.add(token);
				}
			});
			return wowtokenList;
		} else {
			logger.info("没有从数据库获取到时光徽章信息");
		}		
		return null;
	}

	@Override
	public void deleteAll() {
		collection.drop();		
	}

}
