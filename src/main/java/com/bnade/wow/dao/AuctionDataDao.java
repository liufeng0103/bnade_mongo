package com.bnade.wow.dao;

import java.util.List;

import com.bnade.wow.po.Auction;

/**
 * 通过api获取的服务器所有的拍卖数据，通过该dao操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionDataDao {
	
	void save(List<Auction> auctionData, int realmId);
	
	void drop(int realmId);
	
	void ensureIndex(int realmId);
	
	List<Auction> getByRealmIdAndOwner(int realmId, String owner);
}
