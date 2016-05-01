package com.bnade.wow.service.impl;

import java.util.List;

import com.bnade.util.TimeUtil;
import com.bnade.wow.dao.AuctionMinBuyoutDailyDataDao;
import com.bnade.wow.dao.impl.AuctionMinBuyoutDailyDataDaoImpl;
import com.bnade.wow.po.Auction;
import com.bnade.wow.service.AuctionMinBuyoutDailyDataService;

public class AuctionMinBuyoutDailyDataServiceImpl implements AuctionMinBuyoutDailyDataService {
	
	private AuctionMinBuyoutDailyDataDao auctionMinBuyoutDailyDataDao;
	
	public AuctionMinBuyoutDailyDataServiceImpl() {
		auctionMinBuyoutDailyDataDao = new AuctionMinBuyoutDailyDataDaoImpl();
	}

	@Override
	public void save(List<Auction> auctionData, int realmId) {
		auctionMinBuyoutDailyDataDao.save(auctionData, realmId);		
	}

	@Override
	public List<Auction> getPast(int itemId, String bounsList, int realmId) {
		// 获取2天的数据
		List<Auction> aucs = auctionMinBuyoutDailyDataDao.get(itemId, bounsList, TimeUtil.getDate(-1), realmId);
		aucs.addAll(auctionMinBuyoutDailyDataDao.get(itemId, bounsList, TimeUtil.getDate(), realmId));
		return aucs;
	}

	@Override
	public List<Auction> get(String day, int realmId) {		
		return auctionMinBuyoutDailyDataDao.get(day, realmId);
	}

	@Override
	public void drop(String day, int realmId) {
		auctionMinBuyoutDailyDataDao.drop(day, realmId);		
	}
}
