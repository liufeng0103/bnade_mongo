package com.bnade.wow.service;

import java.util.List;

import com.bnade.wow.client.model.JAuction;

/**
 * 拍卖行所有拍卖数据的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionDataService {

	void save(List<JAuction> auctionData, int realmId);
}
