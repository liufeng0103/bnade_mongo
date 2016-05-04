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

-- 物品信息
DROP TABLE IF EXISTS t_item;
CREATE TABLE t_item (
	id	INT UNSIGNED NOT NULL,			-- ID
	description VARCHAR(255) NOT NULL,	-- 描述
	name VARCHAR(80) NOT NULL,			-- 物品名
	icon VARCHAR(64) NOT NULL,			-- 图标名
	itemClass INT NOT NULL,				-- 类型
	itemSubClass INT NOT NULL,			-- 子类型
	inventoryType INT NOT NULL,			-- 装备位置
	itemLevel INT NOT NULL,				-- 物品等级
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 数据导入使用items.sql

-- 物品信息内存表， 由于经常使用数据保存到内存中
DROP TABLE IF EXISTS mt_item;
CREATE TABLE mt_item (
	id	INT UNSIGNED NOT NULL,			-- ID
	name VARCHAR(80) NOT NULL,			-- 物品名
	icon VARCHAR(64) NOT NULL,			-- 图标名
	itemLevel INT NOT NULL,				-- 物品等级
	PRIMARY KEY(id)
) ENGINE=Memory DEFAULT CHARSET=utf8;
ALTER TABLE mt_item ADD INDEX(name); -- 通过物品名查询物品信息时使用
truncate mt_item
-- 输入导入到内存中
insert into mt_item (id,name,icon,itemLevel) select id,name,icon,itemLevel from t_item

-- 装备奖励表
-- 6.0制造业和fb物品都是拥有相同的itemId但不同的等级，副属性等通过bonus来表示
DROP TABLE IF EXISTS t_item_bonus;
CREATE TABLE t_item_bonus (
	itemId	INT UNSIGNED NOT NULL,		-- 物品ID
	bonusList VARCHAR(20) NOT NULL, 	-- 装备奖励
	PRIMARY KEY(itemId, bonusList)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 宠物信息表
DROP TABLE IF EXISTS t_pet;
CREATE TABLE IF NOT EXISTS t_pet (	
	id INT UNSIGNED NOT NULL,		-- 宠物id				
	name VARCHAR(16) NOT NULL,		-- 宠物名
	icon VARCHAR(64) NOT NULL,		-- 宠物图标
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_pet ADD INDEX(name); -- 通过宠物名查询宠物信息
-- 数据导入使用pets.sql

-- 宠物类型以及属性值
DROP TABLE IF EXISTS t_pet_stats;
CREATE TABLE t_pet_stats (
	speciesId INT UNSIGNED NOT NULL,				-- 宠物id
	breedId	INT UNSIGNED NOT NULL,					-- 成长类型
	petQualityId INT UNSIGNED NOT NULL default 3,	-- 品质
	level	INT UNSIGNED NOT NULL default 25,		-- 等级
	health INT UNSIGNED NOT NULL,					-- 生命值
	power	INT UNSIGNED NOT NULL,					-- 攻击力
	speed INT NOT NULL,								-- 速度
	PRIMARY KEY(speciesId,breedId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 数据导入petStats.sql