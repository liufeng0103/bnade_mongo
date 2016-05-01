package com.bnade.wow.dao;

import com.bnade.wow.po.ArchivedTask;

/**
 * 爬虫程序相关的数据库操作，比如记录程序状态信息等
 * @author liufeng0103
 *
 */
public interface TaskStatusDao {

	void addArchivedTask(ArchivedTask task);
	
	ArchivedTask getArchivedTask(ArchivedTask task);
	
}
