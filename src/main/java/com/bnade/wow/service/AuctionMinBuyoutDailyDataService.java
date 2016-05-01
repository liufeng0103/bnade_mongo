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
	
	/**
	 * 获取服务器某天的所有记录
	 * @param day
	 * @param realmId
	 * @return
	 */
	List<Auction> get(String day, int realmId);
	
	/**
	 * 删除服务器某天的集合
	 * @param day
	 * @param realmId
	 * @return
	 */
	void drop(String day, int realmId);
	
}
