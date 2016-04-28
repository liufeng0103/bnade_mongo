package com.bnade.wow.dao;

import java.sql.SQLException;

import com.bnade.wow.po.Realm;

public interface RealmDao {
	
	Realm getByName(String name) throws SQLException;

	int save(Realm realm) throws SQLException;
	
	int update(Realm realm) throws SQLException;
}
