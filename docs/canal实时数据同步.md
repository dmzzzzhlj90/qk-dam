
## 简介

![](https://img-blog.csdnimg.cn/20191104101735947.png)

**canal [kə'næl]**，译意为水道/管道/沟渠，主要用途是基于 MySQL 数据库增量日志解析，提供增量数据订阅和消费

早期阿里巴巴因为杭州和美国双机房部署，存在跨机房同步的业务需求，实现方式主要是基于业务 trigger 获取增量变更。从 2010 年开始，业务逐步尝试数据库日志解析获取增量变更进行同步，由此衍生出了大量的数据库增量订阅和消费业务。

基于日志增量订阅和消费的业务包括
- 数据库镜像
- 数据库实时备份
- 索引构建和实时维护(拆分异构索引、倒排索引等)
- 业务 com.qk.dm.groovy.cache 刷新
- 带业务逻辑的增量数据处理

当前的 canal 支持源端 MySQL 版本包括 5.1.x , 5.5.x , 5.6.x , 5.7.x , 8.0.x

## 工作原理

#### MySQL主备复制原理
![](http://dl.iteye.com/upload/attachment/0080/3086/468c1a14-e7ad-3290-9d3d-44ac501a7227.jpg)

- MySQL master 将数据变更写入二进制日志( binary log, 其中记录叫做二进制日志事件binary log events，可以通过 show binlog events 进行查看)
- MySQL slave 将 master 的 binary log events 拷贝到它的中继日志(relay log)
- MySQL slave 重放 relay log 中事件，将数据变更反映它自己的数据

#### canal 工作原理

- canal 模拟 MySQL slave 的交互协议，伪装自己为 MySQL slave ，向 MySQL master 发送dump 协议
- MySQL master 收到 dump 请求，开始推送 binary log 给 slave (即 canal )
- canal 解析 binary log 对象(原始为 byte 流)

## 快速启动

- 对于自建 MySQL , 需要先开启 Binlog 写入功能，配置 binlog-format 为 ROW 模式，my.cnf 中配置如下
```
[mysqld]
log-bin=mysql-bin # 开启 binlog
binlog-format=ROW # 选择 ROW 模式
server_id=1 # 配置 MySQL replaction 需要定义，不要和 canal 的 slaveId 重复
```
- 授权 canal 链接 MySQL 账号具有作为 MySQL slave 的权限, 如果已有账户可直接 grant
```
CREATE USER canal IDENTIFIED BY 'canal';  
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
-- GRANT ALL PRIVILEGES ON *.* TO 'canal'@'%' ;
FLUSH PRIVILEGES;
```
- 配置修改
```
vi conf/example/instance.properties
```
```
## mysql serverId
canal.instance.mysql.slaveId = 1234
#position info，需要改成自己的数据库信息
canal.instance.master.address = 127.0.0.1:3306 
canal.instance.master.journal.name = 
canal.instance.master.position = 
canal.instance.master.timestamp = 
#canal.instance.standby.address = 
#canal.instance.standby.journal.name =
#canal.instance.standby.position = 
#canal.instance.standby.timestamp = 
#username/password，需要改成自己的数据库信息
canal.instance.dbUsername = canal  
canal.instance.dbPassword = canal
canal.instance.defaultDatabaseName =
canal.instance.connectionCharset = UTF-8
#table regex
canal.instance.filter.regex = .\*\\\\..\*
```
- 启动
```
sh bin/startup.sh
```
- 查看 server 日志
```
vi logs/canal/canal.log
```
- 查看 instance 的日志
```
vi logs/example/example.log
```

# 客户端Adapter
## 基本说明
canal 1.1.1版本之后, 增加客户端数据落地的适配及启动功能, 目前支持功能:
- 客户端启动器
- 同步管理REST接口
- 日志适配器, 作为DEMO
- 关系型数据库的数据同步(表对表同步), ETL功能
- HBase的数据同步(表对表同步), ETL功能
- (后续支持) ElasticSearch多表数据同步,ETL功能
## 适配器整体结构
client-adapter分为适配器和启动器两部分, 适配器为多个fat jar, 每个适配器会将自己所需的依赖打成一个包, 以SPI的方式让启动器动态加载, 目前所有支持的适配器都放置在plugin目录下

启动器为 SpringBoot 项目, 支持canal-client启动的同时提供相关REST管理接口, 运行目录结构为:
```
- bin
    restart.sh
    startup.bat
    startup.sh
    stop.sh
- lib
   ...
- plugin 
    client-adapter.logger-1.1.1-jar-with-dependencies.jar
    client-adapter.hbase-1.1.1-jar-with-dependencies.jar
    ...
- conf
    application.yml
    - hbase
        mytest_person2.yml
- logs
```
## 适配器配置的介绍
### 总配置文件 application.yml

adapter定义配置部分
```
canal.conf:
  canalServerHost: 127.0.0.1:11111          # 对应单机模式下的canal server的ip:port
  zookeeperHosts: slave1:2181               # 对应集群模式下的zk地址, 如果配置了canalServerHost, 则以canalServerHost为准
  mqServers: slave1:6667 #or rocketmq       # kafka或rocketMQ地址, 与canalServerHost不能并存
  flatMessage: true                         # 扁平message开关, 是否以json字符串形式投递数据, 仅在kafka/rocketMQ模式下有效
  batchSize: 50                             # 每次获取数据的批大小, 单位为K
  syncBatchSize: 1000                       # 每次同步的批数量
  retries: 0                                # 重试次数, -1为无限重试
  timeout:                                  # 同步超时时间, 单位毫秒
  mode: tcp # kafka rocketMQ                # canal client的模式: tcp kafka rocketMQ
  srcDataSources:                           # 源数据库
    defaultDS:                              # 自定义名称
      url: jdbc:mysql://127.0.0.1:3306/mytest?useUnicode=true   # jdbc url 
      username: root                                            # jdbc 账号
      password: 121212                                          # jdbc 密码
  canalAdapters:                            # 适配器列表
  - instance: example                       # canal 实例名或者 MQ topic 名
    groups:                                 # 分组列表
    - groupId: g1                           # 分组id, 如果是MQ模式将用到该值
      outerAdapters:                        # 分组内适配器列表
      - name: logger                        # 日志打印适配器
......           
```
###适配器表映射文件
修改 conf/es/mytest_user.yml文件:
```
dataSourceKey: defaultDS        # 源数据源的key, 对应上面配置的srcDataSources中的值
outerAdapterKey: exampleKey     # 对应application.yml中es配置的key 
destination: example            # cannal的instance或者MQ的topic
groupId:                        # 对应MQ模式下的groupId, 只会同步对应groupId的数据
esMapping:
  _index: mytest_user           # es 的索引名称
  _type: _doc                   # es 的type名称, es7下无需配置此项
  _id: _id                      # es 的_id, 如果不配置该项必须配置下面的pk项_id则会由es自动分配
#  pk: id                       # 如果不需要_id, 则需要指定一个属性为主键属性
  # sql映射
  sql: "select a.id as _id, a.name as _name, a.role_id as _role_id, b.role_name as _role_name,
        a.c_time as _c_time, c.labels as _labels from user a
        left join role b on b.id=a.role_id
        left join (select user_id, group_concat(label order by id desc separator ';') as labels from label
        group by user_id) c on c.user_id=a.id"
#  objFields:
#    _labels: array:;           # 数组或者对象属性, array:; 代表以;字段里面是以;分隔的
#    _obj: object               # json对象
  etlCondition: "where a.c_time>='{0}'"     # etl 的条件参数
  commitBatch: 3000                         # 提交批大小
```
sql映射说明:

sql支持多表关联自由组合, 但是有一定的限制:

- 主表不能为子查询语句
- 只能使用left outer join即最左表一定要是主表
- 关联从表如果是子查询不能有多张表
- 主sql中不能有where查询条件(从表子查询中可以有where条件但是不推荐, 可能会造成数据同步的不一致, 比如修改了where条件中的字段内容)
- 关联条件只允许主外键的'='操作不能出现其他常量判断比如: on a.role_id=b.id and b.statues=1
- 关联条件必须要有一个字段出现在主查询语句中比如: on a.role_id=b.id 其中的 a.role_id 或者 b.id 必须出现在主select语句中
- Elastic Search的mapping 属性与sql的查询值将一一对应(不支持 select *), 比如: select a.id as _id, a.name, a.email as _email from user, 其中name将映射到es mapping的name field, _email将 映射到mapping的_email field, 这里以别名(如果有别名)作为最终的映射字段. 这里的_id可以填写到配置文件的 _id: _id映射.

### 启动ES数据同步
- 启动canal-adapter启动器
```
    bin/startup.sh
```
### 验证
- 新增mysql mytest.user表的数据, 将会自动同步到es的mytest_user索引下面, 并会打出DML的log
- 修改mysql mytest.role表的role_name, 将会自动同步es的mytest_user索引中的role_name数据
- 新增或者修改mysql mytest.label表的label, 将会自动同步es的mytest_user索引中的labels数据

##  注意

- 只有canal1.5才支持es7
- 使用canal1.5要下载快照版本，而不要下载released版本，released版本有bug
- es Adapter的lib目录下的mysql-connector-java-5.1.48.jar替换成mysql-connector-java-5.1.49.jar