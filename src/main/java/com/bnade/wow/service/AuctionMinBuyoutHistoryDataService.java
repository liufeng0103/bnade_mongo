package com.bnade.wow.service;

import java.util.List;

import com.bnade.wow.po.HistoryAuction;

public interface AuctionMinBuyoutHistoryDataService {

	/**
	 * 保存服务器历史记录，归档到当天的年份
	 * @param aucs
	 * @param realmId
	 */
	void save(List<HistoryAuction> aucs, int realmId, int year);
	
	/**
	 * 获取物品在某个服务器最近的所有历史拍卖数据
	 * @param realmId
	 * @return
	 */
	List<HistoryAuction> get(int itemId, String bounsList, int realmId);
	
}
