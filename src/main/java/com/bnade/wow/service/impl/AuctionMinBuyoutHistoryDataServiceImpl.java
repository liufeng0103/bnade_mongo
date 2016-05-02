package com.bnade.wow.service.impl;

import java.util.List;

import com.bnade.wow.dao.AuctionMinBuyoutHistoryDataDao;
import com.bnade.wow.dao.impl.AuctionMinBuyoutHistoryDataDaoImpl;
import com.bnade.wow.po.HistoryAuction;
import com.bnade.wow.service.AuctionMinBuyoutHistoryDataService;

public class AuctionMinBuyoutHistoryDataServiceImpl implements AuctionMinBuyoutHistoryDataService {
	
	private AuctionMinBuyoutHistoryDataDao auctionMinBuyoutHistoryDataDao;
	
	public AuctionMinBuyoutHistoryDataServiceImpl() {
		auctionMinBuyoutHistoryDataDao = new AuctionMinBuyoutHistoryDataDaoImpl();	
	}	

	@Override
	public void save(List<HistoryAuction> aucs, int realmId, int year) {
		auctionMinBuyoutHistoryDataDao.save(aucs, realmId, year);		
	}

}
