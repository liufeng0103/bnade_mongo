package com.bnade.wow.rs;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.bnade.wow.po.Auction;
import com.bnade.wow.service.AuctionMinBuyoutDailyDataService;
import com.bnade.wow.service.AuctionMinBuyoutDataService;
import com.bnade.wow.service.impl.AuctionMinBuyoutDailyDataServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutDataServiceImpl;

@Path("/ah2")
public class AuctionDataResource {
	
	private AuctionMinBuyoutDataService auctionMinBuyoutDataService;
	private AuctionMinBuyoutDailyDataService auctionMinBuyoutDailyDataService;
	
	public AuctionDataResource() {
		auctionMinBuyoutDataService = new AuctionMinBuyoutDataServiceImpl();
		auctionMinBuyoutDailyDataService = new AuctionMinBuyoutDailyDataServiceImpl();
	}
	
	/**
	 * 获取物品在所有服务器的最低价
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionsByItemId(@PathParam("id") int itemId, @QueryParam("bl") String bl) {
		List<Auction> aucs = auctionMinBuyoutDataService.getByItemIdAndBounsList(itemId, bl);
		Object[] result = new Object[aucs.size()];
		for (int i = 0; i < aucs.size(); i++) {
			Auction auc = aucs.get(i);
			Object[] item = new Object[6];
			item[0] = auc.getRealmId();
			item[1] = auc.getBuyout();
			item[2] = auc.getOwner();
			item[3] = auc.getQuantity();
			item[4] = auc.getLastModifed();
			item[5] = auc.getTimeLeft();
			result[i] = item;
		}
		return result;
	}
	
	/**
	 * 获取物品在所有服务器的最低价
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/past/realm/{realm}/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPastAuctionsByRealmAndItemId(@PathParam("realm") String realmName, @PathParam("id") int itemId, @QueryParam("bl") String bl) {
		List<Auction> aucs = auctionMinBuyoutDailyDataService.getPast(itemId, bl, 38);
		Object[] result = new Object[aucs.size()];
		for (int i = 0; i < aucs.size(); i++) {
			Auction auc = aucs.get(i);
			Object[] item = new Object[3];
			item[0] = auc.getBuyout();
			item[1] = auc.getQuantity();
			item[2] = auc.getLastModifed();
			result[i] = item;
		}
		return result; 
	}
}
