package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Item;

public interface ItemDao {

	List<Item> getItemsByName(String name) throws SQLException;
	
	List<Item> getItemsByName(String name, boolean isFuzzy) throws SQLException;
	
	Item getItemById(int id) throws SQLException;
	
	List<String> getBonusList(int itemId) throws SQLException;
	
}
