package com.bnade.wow.service;

import java.util.List;

import com.bnade.wow.po.WowToken;

/**
 * 时光徽章相关操作
 * 
 * @author liufeng0103
 *
 */
public interface WowTokenService {

	void save(WowToken wowToken);
	
	void save(List<WowToken> wowTokens);
	
	WowToken getByUpdated(long updated);
	
	List<WowToken> getAll();
	
	void deleteAll();
}
