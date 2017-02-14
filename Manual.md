## zhiwen's web crawler    version 1.0

### 当前功能介绍：
当前版本处于测试开发阶段，url未做定向限制，能实现通过给定的一个或多个种子url,基于深度优先，递归持续爬取种子url所链接到的页面。


### 初次使用前准备
1.存储所需文件夹准备:
在本机硬盘或者外接硬盘建立三个文件夹:`/tmp/file`, `/tmp/url`, `/tmp/bloomfilter`，分别存储Html页面内容、url、BloomFilter对象文件(用来url的去重);

2.数据库准备
a.在MySQL数据库中执行下面的sql语句，简历表格:
```
"CREATE TABLE `FileName` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) DEFAULT NULL,
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15022 DEFAULT CHARSET=utf8;"

b.在url-store模块中，将config/spring/local/appContext-mybatis.xml文件内以下三行中"***"内容代替为自己数据库相关参数即可
<property name="jdbcUrl" value="***" />;
<property name="user" value="WebTest" />;
<property name="password" value="WebTest@0901" />;
```
### 启动程序
1.找到bootstrap模块的`StartCrawler`类，在main方法中
```
UrlMarket urlMarket = new UrlMarketImpl();
urlMarket.deposit(url);
``` 
这行代码后调用,
其中url表示其中一条种子url的字符串形式，若有多条种子url，执行多次该代码;
2.运行该类main方法；
3.程序运行后，浏览器打开 "http://127.0.1.1:4098/" 可实时检测网页下载数量和下载内容总容量；


