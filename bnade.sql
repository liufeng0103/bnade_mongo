USE bnade;

-- 服务器信息
DROP TABLE IF EXISTS t_realm;
CREATE TABLE IF NOT EXISTS t_realm (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,		-- 服务器ID	
	name VARCHAR(8) NOT NULL,						-- 服务器名
	url VARCHAR(128) NOT NULL,						-- 拍卖行文件地址
	lastModified BIGINT NOT NULL,					-- 文件更新时间	
	maxAucId INT NOT NULL,							-- 文件最大拍卖id
	auctionQuantity INT NOT NULL,					-- 拍卖总数量
	playerQuantity INT NOT NULL,					-- 玩家数量
	itemQuantity INT NOT NULL,						-- 物品种类数量	
	lastUpdateTime BIGINT NOT NULL,					-- 行更新时间
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_realm ADD INDEX(name);