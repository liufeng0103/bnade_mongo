package com.bnade.wow.service;

import java.util.List;

import com.bnade.wow.po.Auction;

public interface AuctionMinBuyoutDataService {
	
	void save(List<Auction> auctionData, int realmId);
	
	List<Auction> getByItemIdAndBounsList(int itemId, String bounsList);
	
}
 