package com.bnade.wow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.MongoUtil;
import com.bnade.util.TimeUtil;
import com.bnade.wow.dao.AuctionMinBuyoutDailyDataDao;
import com.bnade.wow.po.Auction;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;

public class AuctionMinBuyoutDailyDataDaoImpl implements AuctionMinBuyoutDailyDataDao {
	
	private static Logger logger = LoggerFactory.getLogger(AuctionMinBuyoutDailyDataDaoImpl.class);
	
	private static final String COLLECTION_NAME_PREFIX = "dailyAuction_";
	private static final List<IndexModel> INDEXES = new ArrayList<>();	
	
	static {
		// 查询服务器物品某天的所有历史记录
		INDEXES.add(new IndexModel(new Document("item", 1).append("petSpeciesId", 1))); 
	}
	
	@Override
	public void save(List<Auction> auctionData, int realmId) {
		MongoCollection<Document> coll = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + TimeUtil.getDay() + realmId);
		checkAndCreateIndex(coll);
		List<Document> docs = new ArrayList<Document>();
		for (Auction auc : auctionData) {
			Document doc = new Document("auc", auc.getAuc())
					.append("item", auc.getItem())
					.append("owner", auc.getOwner())
					.append("ownerRealm", auc.getOwnerRealm())
					.append("bid", auc.getBid())
					.append("buyout", auc.getBuyout())
					.append("quantity", auc.getQuantity())
					.append("timeLeft", auc.getTimeLeft())
					.append("rand", auc.getRand())
					.append("seed", auc.getSeed())
					.append("petSpeciesId", auc.getPetSpeciesId())
					.append("petLevel", auc.getPetLevel())
					.append("petBreedId", auc.getPetBreedId())
					.append("context", auc.getContext())
					.append("bonusLists", auc.getBonusLists())
					.append("lastModifed", auc.getLastModifed());
			docs.add(doc);
		}
		coll.insertMany(docs);
	}
	
	private void checkAndCreateIndex(MongoCollection<Document> coll) {
		ListIndexesIterable<Document> indexes = coll.listIndexes();
		if(!indexes.iterator().hasNext()) {
			logger.info("索引未创建,为集合{}创建索引", coll.getNamespace().getCollectionName());
			coll.createIndexes(INDEXES);
		}		
	}

	@Override
	public List<Auction> get(int itemId, String bounsList, String day, int realmId) {
		MongoCollection<Document> coll = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + day + realmId);
		Document doc = new Document("item", itemId);
		if (bounsList != null) {
			doc.append("bonusLists", bounsList);
		}
		FindIterable<Document> iterable = coll.find(doc);
		List<Auction> aucs = new ArrayList<>();
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document doc) {
		    	Auction auc = new Auction();
		    	auc.setAuc(doc.getInteger("auc"));
		    	auc.setItem(doc.getInteger("item"));
		    	auc.setOwner(doc.getString("owner"));
		    	auc.setOwnerRealm(doc.getString("ownerRealm"));
		    	auc.setBid(doc.getLong("bid"));
		    	auc.setBuyout(doc.getLong("buyout"));
		    	auc.setQuantity(doc.getInteger("quantity"));
		    	auc.setTimeLeft(doc.getString("timeLeft"));
		    	auc.setRand(doc.getInteger("rand"));
		    	auc.setSeed(doc.getLong("seed"));
		    	auc.setPetSpeciesId(doc.getInteger("petSpeciesId"));
		    	auc.setPetLevel(doc.getInteger("petLevel"));
		    	auc.setPetBreedId(doc.getInteger("petBreedId"));
		    	auc.setContext(doc.getInteger("context"));
		    	auc.setBonusLists(doc.getString("bonusLists"));
		    	auc.setRealmId(realmId);
		    	auc.setLastModifed(doc.getLong("lastModifed"));	
		    	aucs.add(auc);
		    }
		});
		return aucs;
	}
}
