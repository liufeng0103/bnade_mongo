package com.bnade.wow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.bnade.util.MongoUtil;
import com.bnade.wow.dao.AuctionDataDao;
import com.bnade.wow.po.Auction;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;

public class AuctionDataDaoImpl implements AuctionDataDao {
	
	private static final String COLLECTION_NAME_PREFIX = "auction_";

	private static final List<IndexModel> INDEXES = new ArrayList<>();	
	
	static {
		// 查询某个玩家拍卖的所有物品
		INDEXES.add(new IndexModel(new Document("owner", 1))); 
	}
	
	@Override
	public void save(List<Auction> auctionData, int realmId) {
		MongoCollection<Document> collection = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + realmId);
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
		collection.insertMany(docs);
	}

	/**
	 * 删除某个服务器的collection, 
	 */
	@Override
	public void drop(int realmId) {
		MongoCollection<Document> collection = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + realmId);
		collection.drop();
	}

	@Override
	public void ensureIndex(int realmId) {
		MongoCollection<Document> collection = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + realmId);
		collection.createIndexes(INDEXES);
	}

}
