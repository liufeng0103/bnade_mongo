package com.bnade.wow.dao;

import java.util.List;

import com.bnade.wow.po.HistoryAuction;


/**
 * 把每天的数据按每天几个时段计算后保存到历史表，对该历史表的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionMinBuyoutHistoryDataDao {

	/**
	 * 保存服务器的历史记录到哪年的集合
	 * @param aucs
	 * @param realmId
	 * @param year
	 */
	void save(List<HistoryAuction> aucs, int realmId, int year);
	
	/**
	 * 获取物品在某个服务器的所有历史拍卖数据
	 * @param realmId
	 * @return
	 */
	List<HistoryAuction> get(int itemId, String bounsList, int realmId, int year);
	
}
