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
	
}
