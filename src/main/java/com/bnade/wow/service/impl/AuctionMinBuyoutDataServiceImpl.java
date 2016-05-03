package com.bnade.wow.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.wow.dao.AuctionMinBuyoutDataDao;
import com.bnade.wow.dao.impl.AuctionMinBuyoutDataDaoImpl;
import com.bnade.wow.po.Auction;
import com.bnade.wow.service.AuctionMinBuyoutDataService;

public class AuctionMinBuyoutDataServiceImpl implements AuctionMinBuyoutDataService {
	
	private static Logger logger = LoggerFactory.getLogger(AuctionMinBuyoutDataServiceImpl.class);
	
	private AuctionMinBuyoutDataDao auctionMinBuyoutDataDao;
	
	public AuctionMinBuyoutDataServiceImpl() {
		auctionMinBuyoutDataDao = new AuctionMinBuyoutDataDaoImpl();
	}

	@Override
	public void save(List<Auction> auctionData, int realmId) {
		auctionMinBuyoutDataDao.deleteAll(realmId);
		auctionMinBuyoutDataDao.save(auctionData);		
	}

	@Override
	public List<Auction> getByItemIdAndBounsList(int itemId, String bounsList) {
		return auctionMinBuyoutDataDao.getByItemIdAndBounsList(itemId, bounsList);
	}

	@Override
	public List<Auction> getPetsByIdAndBreed(int petId, int breedId) {
		return auctionMinBuyoutDataDao.getPetsByIdAndBreed(petId, breedId);
	}

}
