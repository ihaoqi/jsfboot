CREATE TABLE `jmsite_registry_item` (
 `item_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '项ID',
 `item_level` int(11) NOT NULL COMMENT '第几层',
 `sort_num` int(10) unsigned NOT NULL COMMENT '排序号',
 `id_chain` varchar(256) DEFAULT NULL COMMENT 'ID串',
 `item_path` varchar(1024) DEFAULT NULL COMMENT '名称路径',
 `item_name` varchar(128) DEFAULT NULL COMMENT '项名称',
 `default_value` varchar(1024) DEFAULT NULL COMMENT '缺省值',
 `comment` varchar(512) DEFAULT NULL COMMENT '备注',
 PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='注册表项'

CREATE TABLE `jmsite_registry_value` (
 `item_id` int(10) unsigned NOT NULL COMMENT '项ID',
 `value_name` varchar(128) NOT NULL COMMENT '值名称',
 `value_content` varchar(1024) DEFAULT NULL COMMENT '值内容',
 KEY `item_index` (`item_id`),
 CONSTRAINT `item_value` FOREIGN KEY (`item_id`) REFERENCES `jmsite_registry_item` (`item_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='注册表值'