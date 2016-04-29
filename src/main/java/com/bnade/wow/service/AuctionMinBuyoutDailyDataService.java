package com.bnade.wow.service;

import java.util.List;

import com.bnade.wow.po.Auction;

public interface AuctionMinBuyoutDailyDataService {

	void save(List<Auction> auctionData, int realmId);
	
	/**
	 * 获取物品在某个服务器最近2天的所有历史拍卖数据
	 * @param realmId
	 * @return
	 */
	List<Auction> getPast(int itemId, String bounsList, int realmId);
	
}
