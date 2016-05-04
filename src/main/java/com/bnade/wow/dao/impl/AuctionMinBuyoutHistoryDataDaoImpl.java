package com.bnade.wow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.MongoUtil;
import com.bnade.wow.dao.AuctionMinBuyoutHistoryDataDao;
import com.bnade.wow.po.HistoryAuction;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;

public class AuctionMinBuyoutHistoryDataDaoImpl implements AuctionMinBuyoutHistoryDataDao {
	
	private static Logger logger = LoggerFactory.getLogger(AuctionMinBuyoutHistoryDataDaoImpl.class);

	private static final String COLLECTION_NAME_PREFIX = "historyAuction_";
	private static final List<IndexModel> INDEXES = new ArrayList<>();	
	
	static {
		// 查询物品的所有历史记录
		INDEXES.add(new IndexModel(new Document("item", 1).append("bonusLists", 1))); 
	}
	
	private void checkAndCreateIndex(MongoCollection<Document> coll) {
		ListIndexesIterable<Document> indexes = coll.listIndexes();
		if(!indexes.iterator().hasNext()) {
			logger.info("索引未创建,为集合{}创建索引", coll.getNamespace().getCollectionName());
			coll.createIndexes(INDEXES);
		}		
	}
	
	@Override
	public void save(List<HistoryAuction> aucs, int realmId, int year) {
		MongoCollection<Document> coll = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + year + realmId);
		checkAndCreateIndex(coll);
		List<Document> docs = new ArrayList<Document>();
		for (HistoryAuction auc : aucs) {
			Document doc = new Document("item", auc.getItem())
					.append("petSpeciesId", auc.getPetSpeciesId())
					.append("petBreedId", auc.getPetBreedId())
					.append("bonusLists", auc.getBonusLists())
					.append("buyout", auc.getBuyout())
					.append("quantity", auc.getQuantity())					
					.append("lastModifed", auc.getLastModifed());
			docs.add(doc);
		}
		coll.insertMany(docs);		
	}

	@Override
	public List<HistoryAuction> get(int itemId, String bounsList, int realmId, int year) {
		MongoCollection<Document> coll = MongoUtil.getWowDB().getCollection(COLLECTION_NAME_PREFIX + year + realmId);
		Document doc = new Document("item", itemId);
		if (bounsList != null) {
			doc.append("bonusLists", bounsList);
		}
		FindIterable<Document> iterable = coll.find(doc);
		List<HistoryAuction> aucs = new ArrayList<>();
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document doc) {
		    	HistoryAuction auc = new HistoryAuction();
		    	auc.setItem(doc.getInteger("item"));
		    	auc.setPetSpeciesId(doc.getInteger("petSpeciesId"));
		    	auc.setPetBreedId(doc.getInteger("petBreedId"));
		    	auc.setBonusLists(doc.getString("bonusLists"));
		    	auc.setBuyout(doc.getLong("buyout"));
		    	auc.setQuantity(doc.getInteger("quantity"));	
		    	auc.setLastModifed(doc.getLong("lastModifed"));	
		    	aucs.add(auc);
		    }
		});
		return aucs;
	}
	
}
