## 说明
MongoDB的collection的索引创建

## 所有服务器最低一口价物品表

### 集合名  
auctionMinBuyout

### 索引
- 查询物品或宠物在所有服务器的最低一口价  
db.auctionMinBuyout.ensureIndex({"item":1, "petSpeciesId":1})
- 删除某个服务器所有数据  
db.auctionMinBuyout.ensureIndex({"realmId":1})
