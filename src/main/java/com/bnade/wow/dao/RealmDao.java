package com.bnade.wow.dao;

import com.bnade.wow.po.Realm;

public interface RealmDao {
	
	Realm getByName(String name);

}
