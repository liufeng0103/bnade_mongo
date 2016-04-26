package com.bnade.wow.service;

import com.bnade.wow.po.Realm;

public interface RealmService {

	Realm getByName(String name);
	
}
