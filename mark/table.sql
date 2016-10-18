CREATE TABLE `city_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '城市名称',
  `cnty` varchar(30) NOT NULL DEFAULT '' COMMENT '国家',
  `lat` varchar(30) NOT NULL DEFAULT '' COMMENT '维度',
  `lon` varchar(30) NOT NULL DEFAULT '' COMMENT '经度',
  `prov` varchar(30) NOT NULL DEFAULT '' COMMENT '省份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='全国城市表';
--   UNIQUE KEY `uniq_cityname` (`name`) USING BTREE

CREATE TABLE `flume_dict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `ip` varchar(300) NOT NULL DEFAULT '' COMMENT 'flume server IP,多个用逗号分隔',
  `port` varchar(300) NOT NULL DEFAULT '' COMMENT 'flume端口',
  `source` varchar(500) NOT NULL DEFAULT '' COMMENT 'flume source配置',
  `channel` varchar(500) NOT NULL DEFAULT '' COMMENT 'flume channel配置',
  `sink` varchar(500) NOT NULL DEFAULT '' COMMENT 'flume sink配置',
  `topic` varchar(10) NOT NULL DEFAULT '' COMMENT 'kafka主题',
  `ts_id` varchar(10) NOT NULL DEFAULT '' COMMENT '配置中心唯一id',
  `mark` varchar(10) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='flume配置表';

CREATE TABLE `kafka_dict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `ip` varchar(300) NOT NULL DEFAULT '' COMMENT 'kafka server IP,多个用逗号分隔',
  `conf` varchar(10) NOT NULL DEFAULT '' COMMENT 'kafka配置',
  `ts_id` varchar(10) NOT NULL DEFAULT '' COMMENT '配置中心唯一id',
  `mark` varchar(10) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='kafka配置表';


CREATE TABLE `resolve_dict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `ts_id` varchar(10) NOT NULL DEFAULT '' COMMENT '配置中心唯一id',
  `mark` varchar(10) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='解析规则配置表';

CREATE TABLE `compute_dict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `ts_id` varchar(10) NOT NULL DEFAULT '' COMMENT '配置中心唯一id',
  `mark` varchar(10) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='计算配置表';

-- 区分几种计算模型
