package com.bnade.wow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.bnade.util.MongoUtil;
import com.bnade.wow.client.model.JAuction;
import com.bnade.wow.dao.AuctionMinBuyoutDataDao;
import com.mongodb.client.MongoCollection;

public class AuctionMinBuyoutDataDaoImpl implements AuctionMinBuyoutDataDao {
	
	private static final String COLLECTION_NAME = "auctionMinBuyout";
	private MongoCollection<Document> coll;
	
	public AuctionMinBuyoutDataDaoImpl() {
		coll = MongoUtil.getWowDB().getCollection(COLLECTION_NAME);
	}

	@Override
	public void save(List<JAuction> auctionData, int realmId) {
		List<Document> docs = new ArrayList<Document>();
		for (JAuction auc : auctionData) {
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
					.append("realmId", realmId);
			docs.add(doc);
		}
		coll.insertMany(docs);
	}

	@Override
	public void deleteAll(int realmId) {
		coll.deleteMany(new Document("realmId", realmId));		
	}

}
