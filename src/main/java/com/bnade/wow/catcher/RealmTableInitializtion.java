package com.bnade.wow.catcher;

import java.sql.SQLException;
import java.util.List;

import com.bnade.util.FileUtil;
import com.bnade.wow.po.Realm;
import com.bnade.wow.service.RealmService;
import com.bnade.wow.service.impl.RealmServiceImpl;

/**
 * 使用realmlist.txt中的服务器名来初始化t_item表
 * realmlist.txt包含170个服务器名，保存到数据库中有顺序要求，老的程序
 * 按照这个顺序保存的数据
 * 
 * @author liufeng0103
 *
 */
public class RealmTableInitializtion {

	public static void main(String[] args) throws SQLException {		
		RealmService realmService = new RealmServiceImpl();
		List<String> realmNames = FileUtil.fileLineToList("realmlist.txt");
		if (realmNames.size() != 170) {
			System.err.println("国服一共170个服务器， 请确认使用了正确的文件");
			return;
		}
		for (String realmName : realmNames) {
			Realm realm = new Realm();
			realm.setName(realmName);
			realm.setUrl("");
			realmService.save(realm);
			System.out.println("添加成功：" + realmName);
		}
		System.out.println("t_realm初始化完成");
	}
}
