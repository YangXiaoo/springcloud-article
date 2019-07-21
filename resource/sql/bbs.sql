/*
MySQL Backup
Source Server Version: 5.7.21
Source Database: bbs
Date: 2019/7/9 22:18:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `category_id` int(10) unsigned DEFAULT '0',
  `title` varchar(255) NOT NULL,
  `pic` varchar(255) DEFAULT '0' COMMENT '默认无标题图片',
  `description` varchar(512) DEFAULT '没有描述' COMMENT '短描述',
  `top` varchar(1) DEFAULT '0' COMMENT '默认非置顶文章',
  `content` text NOT NULL,
  `status` varchar(1) DEFAULT '1',
  `upvote` int(10) unsigned DEFAULT '0',
  `downvote` int(10) unsigned DEFAULT '0',
  `comment_count` int(10) unsigned DEFAULT '0',
  `browse` int(10) unsigned DEFAULT '0',
  `favorite` int(10) unsigned DEFAULT '0' COMMENT '收藏数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `article_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `browse`
-- ----------------------------
DROP TABLE IF EXISTS `browse`;
CREATE TABLE `browse` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `article_id` int(10) unsigned NOT NULL,
  `brower_type` varchar(1) DEFAULT '0' COMMENT '浏览器类型,默认无解析',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `browse_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `browse_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `description` varchar(255) NOT NULL,
  `paper_count` int(10) unsigned DEFAULT '0',
  `browse` int(10) unsigned DEFAULT '0',
  `status` varchar(1) DEFAULT '1',
  `sorts` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '发表评论的uid',
  `article_id` int(10) unsigned NOT NULL,
  `p_user_id` int(10) unsigned DEFAULT '0' COMMENT '被评论的uid',
  `pcomment_id` int(11) DEFAULT '0' COMMENT '0表示无父评论',
  `content` text NOT NULL,
  `upvote` int(10) unsigned DEFAULT '0',
  `downvote` int(10) unsigned DEFAULT '0',
  `status` varchar(1) DEFAULT '1',
  `viewed` varchar(1) DEFAULT '0' COMMENT 'read为关键字使用viewed,默认没有阅读',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `favorite`
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `article_id` int(10) unsigned NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `follow`
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `follower_id` int(10) unsigned NOT NULL COMMENT '粉丝id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `follower_id` (`follower_id`),
  CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `follow_ibfk_2` FOREIGN KEY (`follower_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `browse` int(10) unsigned DEFAULT '0',
  `upvote` int(10) unsigned DEFAULT '0',
  `signin` int(10) unsigned DEFAULT '0' COMMENT '登录签到',
  `comment` int(10) unsigned DEFAULT '0' COMMENT '评论',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `grade_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `guestbook`
-- ----------------------------
DROP TABLE IF EXISTS `guestbook`;
CREATE TABLE `guestbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `category_id` int(10) unsigned DEFAULT '9999' COMMENT '留言所在版块,默认9999无版块',
  `pcomment_id` int(11) DEFAULT '-1' COMMENT '-1表示无父评论',
  `content` text NOT NULL,
  `upvote` int(10) unsigned DEFAULT '0',
  `downvote` int(10) unsigned DEFAULT '0',
  `status` varchar(1) DEFAULT '1',
  `viewed` varchar(1) DEFAULT '0' COMMENT '默认没有阅读',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `pcomment_id` (`pcomment_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `guestbook_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `guestbook_ibfk_2` FOREIGN KEY (`pcomment_id`) REFERENCES `guestbook` (`id`),
  CONSTRAINT `guestbook_ibfk_3` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `loginlog`
-- ----------------------------
DROP TABLE IF EXISTS `loginlog`;
CREATE TABLE `loginlog` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `ip` varchar(16) DEFAULT '0' COMMENT '登录ip，默认无ip记录',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `loginlog_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `open_user`
-- ----------------------------
DROP TABLE IF EXISTS `open_user`;
CREATE TABLE `open_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `open_id` varchar(100) NOT NULL,
  `access_token` varchar(100) NOT NULL,
  `nick_name` varchar(100) NOT NULL,
  `avatar` varchar(500) NOT NULL,
  `open_type` varchar(1) DEFAULT '0' COMMENT '第三方类型,0-qq,1-weixin,2-weibo',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `open_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL,
  `role_val` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `role_user`
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `role_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `role_user_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `upfiles`
-- ----------------------------
DROP TABLE IF EXISTS `upfiles`;
CREATE TABLE `upfiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filetype` varchar(1) DEFAULT '0',
  `filename` varchar(255) NOT NULL,
  `filepath` varchar(255) NOT NULL,
  `filesize` varchar(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `upvote`
-- ----------------------------
DROP TABLE IF EXISTS `upvote`;
CREATE TABLE `upvote` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `article_id` int(10) unsigned NOT NULL,
  `type` varchar(1) DEFAULT '1' COMMENT '点赞类型，默认为点赞，`0`为踩',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `upvote_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `upvote_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL,
  `email` varchar(255) DEFAULT '0',
  `phone` varchar(13) DEFAULT '0',
  `profile` varchar(255) NOT NULL,
  `is_active` varchar(1) DEFAULT '1' COMMENT '默认激活',
  `is_admin` varchar(1) DEFAULT '0' COMMENT '默认普通用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `gender` enum('male','female','secret') DEFAULT 'secret',
  `following` int(10) unsigned DEFAULT '0' COMMENT '关注数量',
  `follower` int(10) unsigned DEFAULT '0' COMMENT '粉丝数量',
  `hobbies` varchar(100) DEFAULT '没有留下',
  `article` int(10) unsigned DEFAULT '0' COMMENT '文章数量',
  `description` varchar(200) DEFAULT '没有留下' COMMENT '个人介绍',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `article` VALUES ('5','3','14','test','0','test','0','test','1','0','0','0','0','0','2019-05-05 19:50:54'), ('15','3','17','test-2','0','test-2','0','test-2','1','0','0','0','0','0','2019-05-05 20:09:52'), ('16','3','20','category.setPaperCount','0','category.setPaperCount','0','category.setPaperCount','1','0','0','0','0','0','2019-05-05 20:19:43'), ('17','3','20','6y','0','category.setPaperCount','0','category.setPaperCount','1','0','0','0','0','0','2019-05-05 20:21:45'), ('20','3','14','啊啊','0','描述啊 啊 啊 啊啊啊啊啊啊啊啊 啊啊','0','> **\nString prePath = Constants.WEB_DOMAIN + Constants.CTX;\nMap<String, Object> msg = new HashMap<>();\n\n> // markdown\nif (type == null) {\n                     msg = ajaxReturnMarkdown(\"mardown图片上传成功\", prePath + saveUrl + newFileName);\n                } else {\n                    msg = ajaxReturn(\"上传成功\", prePath + saveUrl + newFileName);\n                }\n                log.info( \"savePath: \" + savePath + \", url: \" + saveUrl + newFileName );\n****','1','0','0','2','0','0','2019-05-06 15:26:29'), ('21','3','14','的前无','0','期望的群无','0','轻微的群翁多翁多翁多翁多风无\n为单位群多翁\n为单位','1','0','0','0','0','0','2019-05-07 09:26:39'), ('22','7',NULL,'去玩儿无群二',NULL,'阿瑟范围',NULL,'213\r\n123\r\n123\r\n123',NULL,NULL,NULL,NULL,NULL,NULL,NULL), ('23','7',NULL,'标题党',NULL,'我解耦万事大吉为',NULL,'好久不见\r\n换行\r\n  空格\r\n      再空格',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `category` VALUES ('14','33','33','0','0','1','3'), ('17','ertret','er','0','0','1','6'), ('20','try','yt','0','0','1','1'), ('26','惹她','如它饿','0','0','1','3');
INSERT INTO `comment` VALUES ('1','2','20','0','0','coment-test-infefgfgrfwrfwewewefwewfwef efdwefo','0','0','1','0','2019-05-06 16:56:27'), ('2','2','20','0','0','coment-wefdawsefseafasefasefawesf sfse\neeefo','0','0','1','0','2019-05-06 16:56:43'), ('3','3','20','2','2','comenfwewfwef efdwefo,2,2','0','0','1','0','2019-05-06 17:41:20'), ('5','7','20','0','0','qwed','0','0','1','0','2019-07-09 19:01:45'), ('6','7','20','0','0','我看青山多妩媚，料青山见我应如是','0','0','1','0','2019-07-09 19:32:14'), ('7','7','20','2','1','子评论','0','0','1','0','2019-07-09 19:36:09'), ('8','7','20','3','2','子子评论','0','0','1','0','2019-07-09 19:40:51'), ('9','7','20','0','0','123@qq.com','0','0','1','0','2019-07-09 20:14:03'), ('10','7','20','2','1','继续子评论','0','0','1','0','2019-07-09 20:16:27'), ('11','7','20','2','1','再继续','0','0','1','0','2019-07-09 20:22:58'), ('12','7','20','0','0','的维度若无','0','0','1','0','2019-07-09 20:25:27'), ('13','7','20','7','6','自己评论自己','0','0','1','0','2019-07-09 20:25:45');
INSERT INTO `upfiles` VALUES ('1','0','timg.jpg','resources/webfile/image/20190505/20190505164134_490.jpg','138546','2019-05-05 16:41:34'), ('2','0','timg.jpg','resources/webfile/image/20190505/20190505164540_572.jpg','138546','2019-05-05 16:45:40'), ('3','0','timg.jpg','resources/webfile/image/20190505/20190505164758_557.jpg','138546','2019-05-05 16:47:58'), ('4','0','timg.jpg','resources/webfile/image/20190505/20190505164850_509.jpg','138546','2019-05-05 16:48:50'), ('5','0','timg.jpg','resources/webfile/image/20190505/20190505165209_885.jpg','138546','2019-05-05 16:52:09'), ('6','0','timg.jpg','resources/webfile/image/20190505/20190505165520_461.jpg','138546','2019-05-05 16:55:20'), ('7','0','timg.jpg','resources/webfile/image/20190505/20190505170136_440.jpg','138546','2019-05-05 17:01:36'), ('8','0','timg.jpg','resources/webfile/image/20190505/20190505170204_51.jpg','138546','2019-05-05 17:02:04'), ('9','0','timg.jpg','resources/webfile/image/20190505/20190505171054_460.jpg','138546','2019-05-05 17:10:54'), ('10','0','timg.jpg','resources/webfile/image/20190505/20190505171526_889.jpg','138546','2019-05-05 17:15:26'), ('11','0','timg.jpg','resources/webfile/image/20190505/20190505172653_840.jpg','138546','2019-05-05 17:26:53'), ('12','0','timg.jpg','resources/webfile/image/20190505/20190505172944_652.jpg','138546','2019-05-05 17:29:44'), ('13','0','timg.jpg','resources/webfile/image/20190505/20190505173138_258.jpg','138546','2019-05-05 17:31:38'), ('14','0','timg.jpg','resources/webfile/image/20190505/20190505173207_40.jpg','138546','2019-05-05 17:32:07'), ('15','0','timg.jpg','resources/webfile/image/20190505/20190505173512_89.jpg','138546','2019-05-05 17:35:12'), ('16','0','timg.jpg','resources/webfile/image/20190505/20190505173557_591.jpg','138546','2019-05-05 17:35:58'), ('17','0','timg.jpg','resources/webfile/image/20190505/20190505173710_277.jpg','138546','2019-05-05 17:37:10'), ('18','0','timg.jpg','resources/webfile/image/20190505/20190505174048_932.jpg','138546','2019-05-05 17:40:48'), ('19','0','timg.jpg','resources/webfile/image/20190505/20190505174107_900.jpg','138546','2019-05-05 17:41:07'), ('20','0','timg.jpg','resources/webfile/image/20190505/20190505174221_492.jpg','138546','2019-05-05 17:42:21'), ('21','0','timg.jpg','resources/webfile/image/20190505/20190505175356_614.jpg','138546','2019-05-05 17:53:56'), ('22','0','timg.jpg','resources/webfile/image/20190505/20190505175531_124.jpg','138546','2019-05-05 17:55:31'), ('23','0','timg.jpg','resources/webfile/image/20190505/20190505175630_632.jpg','138546','2019-05-05 17:56:30'), ('24','0','timg.jpg','resources/webfile/image/20190505/20190505175648_277.jpg','138546','2019-05-05 17:56:48'), ('25','0','timg.jpg','resources/webfile/image/20190505/20190505180539_241.jpg','138546','2019-05-05 18:05:39'), ('26','0','timg.jpg','resources/webfile/image/20190505/20190505181437_671.jpg','138546','2019-05-05 18:14:37'), ('27','0','timg.jpg','resources/webfile/image/20190505/20190505182441_747.jpg','138546','2019-05-05 18:24:41'), ('28','0','timg.jpg','resources/webfile/image/20190505/20190505183857_815.jpg','138546','2019-05-05 18:38:57'), ('29','0','timg.jpg','resources/webfile/image/20190505/20190505183905_720.jpg','138546','2019-05-05 18:39:05'), ('30','0','timg.jpg','resources/webfile/image/20190506/20190506145714_133.jpg','138546','2019-05-06 14:57:14'), ('31','0','shan.jpg','resources/webfile/image/20190506/20190506145827_297.jpg','167900','2019-05-06 14:58:27'), ('32','0','timg.jpg','resources/webfile/image/20190506/20190506152526_19.jpg','138546','2019-05-06 15:25:26');
INSERT INTO `user` VALUES ('2','12326@qq.com','111111','12326@qq.com','0','/images/default_profile.jpg','1','1'), ('3','test','123456','1270009836@qq.com','0','/images/default_profile.jpg','1','1'), ('4','而','11','1593606228@qq.com','0','/images/default_profile.jpg','1','1'), ('6','admin','123456','1593606228@qq.com','0','/images/default_profile.jpg','1','1'), ('7','123@qq.com','123@qq.com','123@qq.com','0','/images/default_profile.jpg','1','0');
