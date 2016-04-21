# bnade

## 项目介绍
网站www.bnade.com用于魔兽世界拍卖行相关数据的查询和分析，这个计划重写整个[项目](https://github.com/liufeng0103/bnade-old)的前后端以及后台爬虫程序，数据库从MySQL迁移到MongoDB

## 网站功能：
- 物品在所有服务器的最低价
- 物品在某个服务器的历史价格
- 时光徽章的价格查询
- 各种类型的宠物在所有服务器的最低价
- 玩家拍卖的所有物品查询
- 魔兽世界插件用于游戏中查看物品的参考价格 

## 开发环境
- 开发语言: Java 1.7, JavaScript, html, css
- 使用的框架： JQuery, Jersey, HightChart
- 数据库： MongoDB
- Web容器： Tomcat7
- IDE及工具： Eclipse, Maven

## 开发环境搭建
1. Clone项目到本地目录上
2. 下载并安装Eclipse
3. 下载Maven, 配置环境变量M2_HOME, 把maven配置到eclipse中
4. 打开Eclipse导入项目
5. 下载MongoDB, 配置并启动MongoDB, 请配置默认端口
6. 运行WowTokenExtractingTask.java, 如果能获取并保存时光徽章信息到MongoDB则安装完成

环境搭建写的比较简洁，如果你看到了，有不明白的地方可以告诉我，我想对此写个详细的教程





