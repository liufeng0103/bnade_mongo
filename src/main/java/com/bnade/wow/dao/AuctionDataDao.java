package com.bnade.wow.dao;

import java.util.List;

import com.bnade.wow.client.model.JAuction;

public interface AuctionDataDao {
	
	void save(List<JAuction> auctionData, int realmId);
	
	void drop(int realmId);
	
	void ensureIndex(int realmId);
}
