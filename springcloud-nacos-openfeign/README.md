# SpringCloud OpenFeign 使用Seata

 Spring Cloud 中使用 Seata，使用 Feign 实现远程调用，使用 mybatis 访问 MySQL 数据库

[seata文档](https://seata.io/zh-cn/docs/overview/what-is-seata.html)

###准备
1.下载启动 nacos-1.4.2 

```
(可选)编辑 /conf/application.properties 修改 nacos.core.auth.enabled=true 打开密码验证
nacos 目录执行
 ./bin/startup.cmd -m standalone
```

2. seata数据库脚本
```sql
-- ----------------------------
-- Table structure for branch_table
-- ----------------------------
DROP TABLE IF EXISTS `branch_table`;
CREATE TABLE `branch_table`  (
  `branch_id` bigint NOT NULL,
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `transaction_id` bigint NULL DEFAULT NULL,
  `resource_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `resource_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `lock_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `branch_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `application_data` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`branch_id`) USING BTREE,
  INDEX `idx_xid`(`xid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for global_table
-- ----------------------------
DROP TABLE IF EXISTS `global_table`;
CREATE TABLE `global_table`  (
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `transaction_id` bigint NULL DEFAULT NULL,
  `status` tinyint NOT NULL,
  `application_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `transaction_service_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `transaction_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `timeout` int NULL DEFAULT NULL,
  `begin_time` bigint NULL DEFAULT NULL,
  `application_data` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`xid`) USING BTREE,
  INDEX `idx_gmt_modified_status`(`gmt_modified`, `status`) USING BTREE,
  INDEX `idx_transaction_id`(`transaction_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lock_table
-- ----------------------------
DROP TABLE IF EXISTS `lock_table`;
CREATE TABLE `lock_table`  (
  `row_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `xid` varchar(96) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `transaction_id` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `branch_id` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `resource_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `table_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pk` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`row_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
```

3.下载启动seata-server-1.4.2

- 修改seata-server设置
```
/seata-server-1.4.2/conf/registry.conf
修改 registry, config为 nacos , 配置nacos服务地址, 分组, 密码等信息
```

- 复制下面这段为config.txt, 放到 seata-server-1.4.2目录下(直接将这些参数配置手动输入nacos也可以,这一步为快速导入配置信息)
- 执行 sh /nacos/nacos-config.sh -h localhost -p 8848 -g SEATA_GROUP -t 5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca (目录为nacos目录, -g -t 均换为自己的nacos信息. 如果开启了nacos密码验证，需带上账户密码)
```
transport.type=TCP
transport.server=NIO
transport.heartbeat=true
transport.thread-factory.boss-thread-prefix=NettyBoss
transport.thread-factory.worker-thread-prefix=NettyServerNIOWorker
transport.thread-factory.server-executor-thread-prefix=NettyServerBizHandler
transport.thread-factory.share-boss-worker=false
transport.thread-factory.client-selector-thread-prefix=NettyClientSelector
transport.thread-factory.client-selector-thread-size=1
transport.thread-factory.client-worker-thread-prefix=NettyClientWorkerThread
transport.thread-factory.boss-thread-size=1
transport.thread-factory.worker-thread-size=8
transport.shutdown.wait=3
service.vgroupMapping.order-service-seata-service-group=default
service.vgroupMapping.account-service-seata-service-group=default
service.vgroupMapping.storage-service-seata-service-group=default
service.vgroupMapping.business-service-seata-service-group=default
service.enableDegrade=false
service.disable=false
service.max.commit.retry.timeout=-1
service.max.rollback.retry.timeout=-1
client.async.commit.buffer.limit=10000
client.lock.retry.internal=10
client.lock.retry.times=30
store.mode=db
store.file.dir=file_store/data
store.file.max-branch-session-size=16384
store.file.max-global-session-size=512
store.file.file-write-buffer-cache-size=16384
store.file.flush-disk-mode=async
store.file.session.reload.read_size=100
store.db.driverClassName=com.mysql.cj.jdbc.Driver
store.db.datasource=dbcp
store.db.dbType=mysql
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useSSL=false&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&allowPublicKeyRetrieval=true&serverTimezone=Shanghai
store.db.user=root
store.db.password=root
store.db.min-conn=1
store.db.max-conn=3
store.db.global.table=global_table
store.db.branch.table=branch_table
store.db.query-limit=100
store.db.lock-table=lock_table
recovery.committing-retry-period=1000
recovery.asyn-committing-retry-period=1000
recovery.rollbacking-retry-period=1000
recovery.timeout-retry-period=1000
transaction.undo.data.validation=true
transaction.undo.log.serialization=jackson
transaction.undo.log.save.days=7
transaction.undo.log.delete.period=86400000
transaction.undo.log.table=undo_log
transport.serialization=seata
transport.compressor=none
metrics.enabled=false
metrics.registry-type=compact
metrics.exporter-list=prometheus
metrics.exporter-prometheus-port=9898
client.report.retry.count=5
service.disableGlobalTransaction=false
client.support.spring.datasource.autoproxy=true
```
- 启动seata server端
```
 ./bin/seata-server.bat (windows,Linux使用.sh)
```


### 业务代码
1. 数据库脚本 (undo_log表为 seata事务控制使用表, 每个分库都需要一个)
```sql
-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `amount` double(14, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `count` int NULL DEFAULT 0,
  `amount` double(14, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_storage
-- ----------------------------
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `commodity_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `count` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `commodity_code`(`commodity_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```
2. 修改配置信息
```
修改每个服务下的配置信息，包括MySQL连接信息, nacos连接信息, Seata连接信息
seata 连接信息, 需注意  tx-service-group: cloud-openfeign-seata-group和 cloud-openfeign-seata-group: default
上面2个配置信息, 对应前面准备时的config.text中的 service.vgroupMapping.storage-service-seata-service-group=default 如果启动之后报相关错误，注意查看nacos上的配置项是否对应
```

## 启动测试

启动4个服务 
访问 http://localhost:8104/business/dubbo/buy 成功
访问 http://localhost:8104/business/dubbo/buy2 报错回滚成功
查看数据库，确认数据

### 注意
FeignInterceptor 拦截器必须，用于调用过程传递全局事务id，全局事务才会生效

### 异常
1. seata-all 1.5.x 的版本中 AbstractDMLBaseExecutor.executeAutoCommitFalse() 方法加了 updateCount >0 的判断, 
<br>而mysql驱动包 executeBatch方法 会返回 NO_INFO = -2, 此时updateCount = -1, 批量插入更新的sql 无法生成undo_log, 最终无法回滚
2. 将seata-all 退回1.4.2 , 在mysql8.0 的驱动中, datetime 会处理为 LocalDateTime ,此时undo_log 会报序列化异常<br>
解决方法: java.resources.META-INF.services 路径下加 io.seata.rm.datasource.undo.parser.spi.JacksonSerializer文件, 内容: 添加的序列化器全路径 <br>
方案参照 test-business-service 下


