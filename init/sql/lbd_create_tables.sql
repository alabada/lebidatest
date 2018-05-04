
/*表结构插入*/


/* 用户表 `u_user` */

DROP TABLE IF EXISTS `u_user`;

CREATE TABLE `u_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `open_id` varchar(50) NOT NULL COMMENT '微信openid',
  `nickname` varchar(20) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别(1:男,2:女,0:未知)',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `country` varchar(20) DEFAULT NULL COMMENT '国家',
  `head_img_url` varchar(250) DEFAULT NULL COMMENT '用户头像',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱|登录帐号',
  `pswd` varchar(32) NOT NULL COMMENT '密码',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq号',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机电话号码',
  `score` int NOT NULL DEFAULT '0' COMMENT '用户积分',
  `status` tinyint(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 角色表 `u_role` */

DROP TABLE IF EXISTS `u_role`;

CREATE TABLE `u_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `type` varchar(10) NOT NULL COMMENT '角色类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 权限表 `u_permission` */

DROP TABLE IF EXISTS `u_permission`;

CREATE TABLE `u_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 用户角色表 `u_user_role` */

DROP TABLE IF EXISTS `u_user_role`;

CREATE TABLE `u_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` int(20) NOT NULL COMMENT '用户ID',
  `rid` int(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uid`) REFERENCES u_user(`id`),
  FOREIGN KEY (`rid`) REFERENCES u_role(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 角色权限中间表 `u_role_permission` */

DROP TABLE IF EXISTS `u_role_permission`;

CREATE TABLE `u_role_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `rid` int(20) NOT NULL COMMENT '角色ID',
  `pid` int(20) NOT NULL COMMENT '权限ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`rid`) REFERENCES u_role(`id`),
  FOREIGN KEY (`pid`) REFERENCES u_permission(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

