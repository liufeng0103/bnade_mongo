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

import com.bnade.util.HttpClient;
import com.bnade.wow.po.Item;
import com.bnade.wow.service.ItemService;
import com.bnade.wow.service.impl.ItemServiceImpl;
import com.bnade.wow.vo.ItemVo;

@Path("/item")
public class ItemResource {
	
	private ItemService itemService;	
	
	public ItemResource() {
		itemService = new ItemServiceImpl();
	}

	/*
	 * 物品名称查询物品
	 */
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemsByName(@PathParam("name")String name, @QueryParam("fuzzy") boolean isFuzzy) {
		try {
			if (isFuzzy) {
				List<Item> items = itemService.getItemsByName(name, true);
				List<String> result = new ArrayList<>();
				for (Item item : items) {
					result.add(item.getName());
				}
				return result;
			} else {
				List<ItemVo> result = new ArrayList<>();
				List<Item> items = itemService.getItemsByName(name);			
				for (Item item : items) {
					ItemVo itemVo = new ItemVo();
					itemVo.setId(item.getId());
					itemVo.setName(item.getName());
					itemVo.setIcon(item.getIcon());
					itemVo.setItemLevel(item.getItemLevel());
					itemVo.setBonusList(item.getBonusList());
					result.add(itemVo);
				}		
				return result;	
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
	
	/*
	 * 物品ID查询物品描述
	 */
	@GET
	@Path("/{id}")	
	public Response getItemById(@PathParam("id")int id,  @QueryParam("bl") String bl) {
		try {
			if (bl != null) {
				String url = "https://www.battlenet.com.cn/wow/zh/item/" + id + "/tooltip";
				if(bl != null){
					url+="?u=529&bl=" + bl;
				}
				HttpClient client = new HttpClient();
				client.setGzipSupported(true);
				String itemHtml = client.get(url);				
				return Response.status(200).entity(itemHtml.replaceAll("href=\"[^\"]*\"", "href=\"\"")).type(MediaType.TEXT_PLAIN).build();
			} else {				
				return Response.status(200).entity(itemService.getItemById(id)).type(MediaType.APPLICATION_JSON).build();
			}					
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
}
