package com.bnade.wow.rs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.wow.po.Auction;
import com.bnade.wow.po.HistoryAuction;
import com.bnade.wow.po.Realm;
import com.bnade.wow.service.AuctionMinBuyoutDailyDataService;
import com.bnade.wow.service.AuctionMinBuyoutDataService;
import com.bnade.wow.service.AuctionMinBuyoutHistoryDataService;
import com.bnade.wow.service.RealmService;
import com.bnade.wow.service.impl.AuctionMinBuyoutDailyDataServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutDataServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutHistoryDataServiceImpl;
import com.bnade.wow.service.impl.RealmServiceImpl;
import com.bnade.wow.vo.RealmVo;

@Path("/auction")
public class AuctionDataResource {
	
	private RealmService realmService;
	private AuctionMinBuyoutDataService auctionMinBuyoutDataService;
	private AuctionMinBuyoutDailyDataService auctionMinBuyoutDailyDataService;
	private AuctionMinBuyoutHistoryDataService auctionMinBuyoutHistoryDataService;
	
	public AuctionDataResource() {
		realmService = new RealmServiceImpl();
		auctionMinBuyoutDataService = new AuctionMinBuyoutDataServiceImpl();
		auctionMinBuyoutDailyDataService = new AuctionMinBuyoutDailyDataServiceImpl();
		auctionMinBuyoutHistoryDataService = new AuctionMinBuyoutHistoryDataServiceImpl();
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
	 * 获取物品在服务器近期的价格
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/past/realm/{realmId}/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPastAuctionsByRealmAndItemId(@PathParam("realmId") int realmId, @PathParam("id") int itemId, @QueryParam("bl") String bl) {
		List<Auction> aucs = auctionMinBuyoutDailyDataService.getPast(itemId, bl, realmId);
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
	
	/**
	 * 获取物品在服务器的历史
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/history/realm/{realmId}/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getHistoryAuctionsByRealmAndItemId(@PathParam("realmId") int realmId, @PathParam("id") int itemId, @QueryParam("bl") String bl) {
		List<HistoryAuction> aucs = auctionMinBuyoutHistoryDataService.get(itemId, bl, realmId);
		Object[] result = new Object[aucs.size()];
		for (int i = 0; i < aucs.size(); i++) {
			HistoryAuction auc = aucs.get(i);
			Object[] item = new Object[3];
			item[0] = auc.getLastModifed();
			item[1] = auc.getBuyout();
			item[2] = auc.getQuantity();			
			result[i] = item;
		}
		return result; 
	}
	
	/**
	 * 获取物品在服务器的历史
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/realms/summary")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getRealmsSummary() {
		try {
			List<Realm> realms = realmService.getAll();
			List<RealmVo> result = new ArrayList<>();
			for (Realm realm : realms) {
				RealmVo realmVo = new RealmVo();
				realmVo.setId(realm.getId());
				realmVo.setAuctionQuantity(realm.getAuctionQuantity());
				realmVo.setPlayerQuantity(realm.getPlayerQuantity());
				realmVo.setItemQuantity(realm.getItemQuantity());
				realmVo.setLastModified(realm.getLastModified());
				result.add(realmVo);
			}	
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
}
