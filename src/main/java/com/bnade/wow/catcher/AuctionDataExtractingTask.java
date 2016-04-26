package com.bnade.wow.catcher;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.BnadeProperties;
import com.bnade.wow.client.WowClient;
import com.bnade.wow.client.WowClientException;
import com.bnade.wow.client.model.AuctionDataFile;
import com.bnade.wow.client.model.JAuction;
import com.bnade.wow.po.Realm;
import com.bnade.wow.service.RealmService;
import com.bnade.wow.service.impl.RealmServiceImpl;

/**
 * 1. 读取数据库该服务器上一次运行的状态
 * 2. 读取配置文件，服务器的更新时间间隔
 * 3. 如果上一次的更新时间没有超过设置的时间间隔，则退出task
 * 4. 否则通过api获取服务器的拍卖行数据更新时间以及数据文件地址
 * 5. 如果api可用
 *   1. 读取api上数据更新时间和数据库中的时间比较，如果一样则退出task
 *   2. 否则，根据数据文件地址下载数据
 *   3. 更新服务器状态表，更新数据更新时间以及文件下载路径
 * 6. api不可用
 *   1. 通过数据库状态表中的地址下载数据
 *   2. 获取下载数据的最大aucid跟服务器状态表中的aucid比较，如果一样则不更新
 *   2. 否则更新服务器状态表，数据更新时间设为当前时间
 * 7. 清理并保存该服务器当前所有的所有拍卖数据到数据库
 * 8. 计算每个物品的最低价
 * 9. 清理并保存所有最低价到最新一次物品价格表
 * 10. 保存所有最低价到物品历史表
 * 11. 触发其它需要处理的任务
 *  
 * @author liufeng0103
 *
 */
public class AuctionDataExtractingTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(AuctionDataExtractingTask.class);

	private String realmName;
	private boolean isComplete;
	private WowClient wowClient;
	private RealmService realmService;
	

	public AuctionDataExtractingTask(String realmName) {
		this.realmName = realmName;
		wowClient = new WowClient();
		realmService = new RealmServiceImpl();
	}

	public void process(String realmName) throws CatcherException {
		if (realmName == null || "".equals(realmName)) {
			throw new CatcherException("要处理的服务器名为空");
		}
		addInfo("开始运行");
		Realm realm = realmService.getByName(realmName);
		if (realm != null) {
			long interval = BnadeProperties.getTask1Interval();
			if (System.currentTimeMillis() - realm.getLastModified() > interval) {
				try {
					AuctionDataFile auctionDataFile = wowClient.getAuctionDataFile(realmName);
					if (auctionDataFile.getLastModified() != realm.getLastModified()) {
						List<JAuction> auctions = wowClient.getAuctionData(auctionDataFile.getUrl());
					} else {
						addInfo("数据更新时间{}与api获取的更新时间一样，不更新", new Date(realm.getLastModified()));
					}
				} catch (WowClientException e) {
					addInfo("获取拍卖行数据文件信息api不好用，使用数据库中的url下载数据文件");
					List<JAuction> auctions = wowClient.getAuctionData(realm.getUrl());
				}
				
			} else {
				addInfo("上次更新时间{}，未超过设定的更新间隔时间{}，不更新", new Date(realm.getLastModified()), interval);
			}
		} else {
			addError("未添加到t_realm表");
		}
		try {
			int a = new Random().nextInt(3);
			logger.info("{}sleep {} 秒", realmName, a);
			Thread.sleep(1000 * a);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			process(realmName);
			isComplete = true;
		} catch (CatcherException e) {
			addError("运行出错：" + e.getMessage());
			e.printStackTrace();
		}
	}

	private void addInfo(String msg, Object... arguments) {
		logger.info("服务器[{}]" + msg, realmName, arguments);;
	}
	
	private void addError(String msg, Object... arguments) {
		logger.error("服务器[{}]" + msg, realmName, arguments);;
	}
	
	private void addDebug(String msg, Object... arguments) {
		logger.debug("服务器[{}]" + msg, realmName, arguments);;
	}
	
	public boolean isComplete() {
		return isComplete;
	}

	public String getRealmName() {
		return realmName;
	}

}
