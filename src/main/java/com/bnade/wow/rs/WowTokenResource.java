package com.bnade.wow.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.wow.po.WowToken;
import com.bnade.wow.service.WowTokenService;
import com.bnade.wow.service.impl.WowTokenServiceImpl;

@Path("/ah")
public class WowTokenResource {
	
	private WowTokenService wowTokenService;
	
	public WowTokenResource() {
		wowTokenService = new WowTokenServiceImpl();
	}

	@GET
	@Path("/wowtokens")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getWowTokens() {
		List<WowToken> tokens = wowTokenService.getAll();
		if (tokens != null) {
			List<Object[]> tokenList = new ArrayList<>();
			for (WowToken token : tokens) {
				Object[] tokenArray = new Object[2];
				tokenArray[0] = token.getUpdated();
				tokenArray[1] = token.getBuy();				
				tokenList.add(tokenArray);
			}
			return tokenList;
		}
		return Response.status(404).entity("数据找不到").type(MediaType.TEXT_PLAIN).build();
	}
}
