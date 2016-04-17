package com.bnade.wow.dao;

import java.util.List;

import com.bnade.wow.po.WowToken;

public interface WowTokenDao {

	void save(WowToken wowToken);
	
	void save(List<WowToken> wowTokens);
	
	WowToken getByUpdated(long updated);
	
	List<WowToken> getAll();
	
	void deleteAll();
}
