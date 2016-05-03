package com.bnade.wow.service;

import java.util.List;

import com.bnade.wow.po.Auction;

/**
 * 拍卖行所有拍卖数据的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionDataService {

	void save(List<Auction> auctionData, int realmId);	
	
	List<Auction> getByRealmIdAndOwner(int realmId, String owner);
	
}
