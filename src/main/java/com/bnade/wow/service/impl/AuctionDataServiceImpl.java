package com.bnade.wow.service.impl;

import java.util.List;

import com.bnade.wow.client.model.JAuction;
import com.bnade.wow.dao.AuctionDataDao;
import com.bnade.wow.dao.impl.AuctionDataDaoImpl;
import com.bnade.wow.service.AuctionDataService;

public class AuctionDataServiceImpl implements AuctionDataService {
	
	private AuctionDataDao auctionDataDao;
	
	public AuctionDataServiceImpl() {
		auctionDataDao = new AuctionDataDaoImpl();
	}

	/**
	 * 保存某个服务器的所有拍卖数据
	 * 一个服务器一个collection，保存前需要清空之前的数据，所有使用drop，感觉会快一些，没有经过测试
	 * drop完之后添加需要的索引然后保存数到数据库
	 */
	@Override
	public void save(List<JAuction> auctionData, int realmId) {
		auctionDataDao.drop(realmId);
		auctionDataDao.ensureIndex(realmId);
		auctionDataDao.save(auctionData, realmId);		
	}

}
