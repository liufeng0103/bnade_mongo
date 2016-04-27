package com.bnade.wow.service;

import java.sql.SQLException;

import com.bnade.wow.po.Realm;

public interface RealmService {

	Realm getByName(String name) throws SQLException;
	
	int save(Realm realm) throws SQLException;
}
