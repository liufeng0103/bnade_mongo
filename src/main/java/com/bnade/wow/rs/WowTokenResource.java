package com.bnade.wow.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
				tokenArray[0] = token.getBuy();
				tokenArray[1] = token.getUpdated();
				tokenList.add(tokenArray);
			}
			return tokenList;
		}
		return null;
	}
}
