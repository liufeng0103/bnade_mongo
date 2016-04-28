package com.bnade.wow.service.impl;

import java.util.List;

import com.bnade.wow.client.model.JAuction;
import com.bnade.wow.dao.AuctionMinBuyoutHistoryDataDao;
import com.bnade.wow.dao.impl.AuctionMinBuyoutHistoryDataDaoImpl;
import com.bnade.wow.service.AuctionMinBuyoutHistoryDataService;

public class AuctionMinBuyoutHistoryDataServiceImpl implements AuctionMinBuyoutHistoryDataService {
	
	private AuctionMinBuyoutHistoryDataDao auctionMinBuyoutHistoryDataDao;
	
	public AuctionMinBuyoutHistoryDataServiceImpl() {
		auctionMinBuyoutHistoryDataDao = new AuctionMinBuyoutHistoryDataDaoImpl();
	}

	@Override
	public void save(List<JAuction> auctionData, int realmId) {
		auctionMinBuyoutHistoryDataDao.save(auctionData, realmId);		
	}

}
