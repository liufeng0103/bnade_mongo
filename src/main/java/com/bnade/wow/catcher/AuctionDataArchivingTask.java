package com.bnade.wow.catcher;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.FileUtil;
import com.bnade.util.TimeUtil;
import com.bnade.wow.dao.TaskStatusDao;
import com.bnade.wow.dao.impl.TaskStatusDaoImpl;
import com.bnade.wow.po.ArchivedTask;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.HistoryAuction;
import com.bnade.wow.po.Realm;
import com.bnade.wow.service.AuctionMinBuyoutDailyDataService;
import com.bnade.wow.service.AuctionMinBuyoutHistoryDataService;
import com.bnade.wow.service.RealmService;
import com.bnade.wow.service.impl.AuctionMinBuyoutDailyDataServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutHistoryDataServiceImpl;
import com.bnade.wow.service.impl.RealmServiceImpl;

/**
 * 归档昨天，清除前天
 * 1. 从文件或数据库读取所有服务器信息
 * 2. 获取前天的日期，从数据库查询是否已处理过该服务器该天的归档
 * 3. 如果没有就获取该服务器该天的所有记录
 * 4. 按每天4个时段计算平均价格和平均数量
 * 5. 保存数据到年历史纪录表
 * 6. 保存该天到数据库表示已经处理过该天记录了
 * 7. drop掉该天的集合
 * 
 * @author liufeng0103
 *
 */
public class AuctionDataArchivingTask {
	
	private static Logger logger = LoggerFactory.getLogger(AuctionDataArchivingTask.class);
	
	private File shutdownFile = new File("t2shutdown");
	
	private String logHeader;
	private TaskStatusDao taskStatusDao;
	private RealmService realmService;
	private AuctionMinBuyoutDailyDataService auctionMinBuyoutDailyDataService;
	private AuctionMinBuyoutHistoryDataService auctionMinBuyoutHistoryDataService;
	private AuctionDataArchivingProcessor auctionDataArchivingProcessor;
	
	public AuctionDataArchivingTask() {
		taskStatusDao = new TaskStatusDaoImpl();
		realmService = new RealmServiceImpl();
		auctionMinBuyoutDailyDataService = new AuctionMinBuyoutDailyDataServiceImpl();
		auctionMinBuyoutHistoryDataService = new AuctionMinBuyoutHistoryDataServiceImpl();
		auctionDataArchivingProcessor = new AuctionDataArchivingProcessor();
	}
	
	public boolean isShutdown() {
		return shutdownFile.exists();
	}

	public void process(String realmName, String handleDate) throws CatcherException, SQLException {
		if (realmName == null || "".equals(realmName)) {
			throw new CatcherException("要处理的服务器名为空");
		}	
		logHeader = "服务器[" + realmName + "]";
		addInfo("开始归档{}的数据", handleDate);
		Realm realm = realmService.getByName(realmName);		
		ArchivedTask taskStatus = new ArchivedTask(realm.getId(), handleDate);
		if(taskStatusDao.getArchivedTask(taskStatus) == null) {
			addInfo("开始获取{}的数据", handleDate);
			List<Auction> aucs = auctionMinBuyoutDailyDataService.get(handleDate, realm.getId());
			if (aucs.size() > 0) {
				addInfo("获取数据完毕, 共{}条", aucs.size());
				try {
					List<HistoryAuction> result = auctionDataArchivingProcessor.process(aucs, handleDate);
					addInfo("数据分析完毕共{}条", result.size());	
					int year = TimeUtil.getYear(TimeUtil.parse(handleDate));
					addInfo("把数据归档到{}年的集合", year);	
					auctionMinBuyoutHistoryDataService.save(result, realm.getId(), year);
					taskStatusDao.addArchivedTask(taskStatus);
					addInfo("数据添加为已归档", handleDate);
				} catch (ParseException e) {
					addError("出错：{}", e.getMessage());
					e.printStackTrace();
				}
			} else {
				addInfo("获取{}的数据0条，不处理", realmName);
			}			
		} else {
			addInfo("已处理过");
		}
		addInfo("完毕");
	}
	
	public void clean(String realmName, String handleDate) throws SQLException {
		logHeader = "服务器[" + realmName + "]";
		Realm realm = realmService.getByName(realmName);		
		ArchivedTask taskStatus = new ArchivedTask(realm.getId(), handleDate);
		if(taskStatusDao.getArchivedTask(taskStatus) != null) {
			addInfo("开始删除{}的集合", handleDate);
			auctionMinBuyoutDailyDataService.drop(handleDate, realm.getId());
			addInfo("删除{}的集合完毕", handleDate);
		} else {
			addError("未归档过{}的数据或数据不存在", handleDate);
		}		
	}
	
	private void addInfo(String msg, Object... arguments) {
		logger.info(logHeader + msg, arguments);
	}
	
	private void addError(String msg, Object... arguments) {
		logger.error(logHeader + msg, arguments);
	}

	public static void main(String[] args) throws CatcherException, SQLException {
		logger.info("启动");
		long start = System.currentTimeMillis();
		if (args != null && args.length > 1) {
			String realmName = args[0];
			String handleDate = args[1];		
			AuctionDataArchivingTask task = new AuctionDataArchivingTask();
			task.process(realmName, handleDate);
		} else {
			AuctionDataArchivingTask task = new AuctionDataArchivingTask();
			List<String> realmNames = FileUtil.fileLineToList("realmlist.txt");
			String handleDate = TimeUtil.getDate(-1);
			String cleanDate = TimeUtil.getDate(-2);
			for (String realmName : realmNames) {
				task.process(realmName, handleDate);
				task.clean(realmName, cleanDate);
				if (task.isShutdown()) {
					logger.info("准备退出，当前运行服务器[{}]", realmName);
					break;
				}
			}			
		}	
		logger.info("运行结束，用时{}", TimeUtil.format(System.currentTimeMillis() - start));
	}
}
