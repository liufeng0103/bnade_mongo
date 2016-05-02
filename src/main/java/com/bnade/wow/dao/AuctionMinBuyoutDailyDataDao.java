package com.bnade.wow.dao;

import java.util.List;

import com.bnade.wow.po.Auction;

/**
 * 把每次计算的最低一口价拍卖信息保存起来的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionMinBuyoutDailyDataDao {

	void save(List<Auction> auctionData, int realmId);
	
	/**
	 * 获取某天的物品在某个服务器的所有历史数据
	 * @param day
	 * @param realmId
	 */
	List<Auction> get(int itemId, String bounsList, String day, int realmId);
	
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
