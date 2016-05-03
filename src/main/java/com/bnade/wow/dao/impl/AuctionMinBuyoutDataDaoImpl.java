package com.bnade.wow.dao.impl;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.bnade.util.MongoUtil;
import com.bnade.wow.dao.AuctionMinBuyoutDataDao;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Pet;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class AuctionMinBuyoutDataDaoImpl implements AuctionMinBuyoutDataDao {
	
	private static final String COLLECTION_NAME = "auctionMinBuyout";
	private MongoCollection<Document> coll;
	
	public AuctionMinBuyoutDataDaoImpl() {
		coll = MongoUtil.getWowDB().getCollection(COLLECTION_NAME);
	}

	@Override
	public void save(List<Auction> auctionData) {
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
					.append("realmId", auc.getRealmId())
					.append("lastModifed", auc.getLastModifed());
			docs.add(doc);
		}
		coll.insertMany(docs);
	}

	@Override
	public void deleteAll(int realmId) {
		coll.deleteMany(new Document("realmId", realmId));		
	}

	@Override
	public List<Auction> getByItemIdAndBounsList(int itemId, String bounsList) {
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
		    	auc.setRealmId(doc.getInteger("realmId"));
		    	auc.setLastModifed(doc.getLong("lastModifed"));
		    	aucs.add(auc);
		    }
		});
		return aucs;
	}

	@Override
	public List<Auction> getPetsByIdAndBreed(int petId, int breedId) {
		FindIterable<Document> iterable = coll.find(and(eq("item", Pet.PET_ITEM_ID ),eq("petSpeciesId", petId),eq("petBreedId", breedId)));
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
		    	auc.setRealmId(doc.getInteger("realmId"));
		    	auc.setLastModifed(doc.getLong("lastModifed"));
		    	aucs.add(auc);
		    }
		});
		return aucs;
	}

}
