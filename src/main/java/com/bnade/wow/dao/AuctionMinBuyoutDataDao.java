package com.bnade.wow.dao;

import java.util.List;

import com.bnade.wow.po.Auction;

/**
 * 所有服务器最新的物品最低一口价数据的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionMinBuyoutDataDao {

	void save(List<Auction> auctionData);
	
	void deleteAll(int realmId);
	
	List<Auction> getByItemIdAndBounsList(int itemId, String bounsList);
	
	List<Auction> getPetsByIdAndBreed(int petId, int breedId);
}
