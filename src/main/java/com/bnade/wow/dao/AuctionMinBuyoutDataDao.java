package com.bnade.wow.dao;

import java.util.List;

import com.bnade.wow.client.model.JAuction;

public interface AuctionMinBuyoutDataDao {

	void save(List<JAuction> auctionData, int realmId);
	
	void deleteAll(int realmId);
}
