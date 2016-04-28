package com.bnade.wow.service;

import java.util.List;

import com.bnade.wow.client.model.JAuction;

public interface AuctionMinBuyoutDataService {
	
	void save(List<JAuction> auctionData, int realmId);
	
}
