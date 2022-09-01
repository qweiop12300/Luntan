-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: luntan
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comments_like`
--

DROP TABLE IF EXISTS `comments_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments_like` (
  `post_id` int DEFAULT NULL COMMENT '帖子id',
  `comments_id` int DEFAULT NULL COMMENT '评论id',
  `user_id` int DEFAULT NULL,
  KEY `comments_like_user_id_fk` (`user_id`),
  KEY `comments_like_post_comments_id_fk` (`comments_id`),
  KEY `comments_like_post_id_fk` (`post_id`),
  CONSTRAINT `comments_like_post_comments_id_fk` FOREIGN KEY (`comments_id`) REFERENCES `post_comments` (`id`),
  CONSTRAINT `comments_like_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `comments_like_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='帖子评论的点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments_like`
--

LOCK TABLES `comments_like` WRITE;
/*!40000 ALTER TABLE `comments_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news_type`
--

DROP TABLE IF EXISTS `news_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` text COMMENT '消息信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COMMENT='消息类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_type`
--

LOCK TABLES `news_type` WRITE;
/*!40000 ALTER TABLE `news_type` DISABLE KEYS */;
INSERT INTO `news_type` VALUES (1,'关注了你'),(2,'评论了你的帖子'),(3,'点赞了你的帖子'),(4,'收藏了你的帖子'),(5,'给你发送了一条消息');
/*!40000 ALTER TABLE `news_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '用户id',
  `type_id` int NOT NULL COMMENT '类型',
  `images` text COMMENT '图片',
  `title` text NOT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `comments` int DEFAULT '0' COMMENT '评论数',
  `collects` int DEFAULT '0' COMMENT '收藏数',
  `view` int DEFAULT '0' COMMENT '观看数',
  `like` int DEFAULT '0' COMMENT '点赞数',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `top` bit(1) DEFAULT b'0' COMMENT '是否置顶',
  `essence` bit(1) DEFAULT b'0' COMMENT '加精',
  PRIMARY KEY (`id`),
  KEY `post_user_id_fk` (`user_id`),
  KEY `post_post_type_id_fk` (`type_id`),
  CONSTRAINT `post_post_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `post_type` (`id`),
  CONSTRAINT `post_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COMMENT='帖子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (28,15,2,'[]','测试测试','tes萨克等哈就开始的卡号大家卡刷道具卡和数据库的哈萨克就和大家看山东卡速度还是按开机动画就卡死的和安居客上的哈克等哈就开始回到家卡省的礼金卡其实大家肯定是较快拉升大家快来十大科技老师大家快来很多时间卡了后打开降落伞等哈就开始了很多啦是打开拉萨好的较快拉升的艰苦拉萨回到家ask了获得奥克兰较好的拉克丝好的就卡拉斯回到家ask了好的急啊抗衰老慧大师金克拉等哈苏联空军等哈时间考虑的话案件考虑到手机卡死了很多较快拉升后端数据库安理会是的拉伸哈桑即可获得按时艰苦了发哈较快拉升发哈是捷克洛夫啥借口里飞沙尽快立法和较快拉升回房间卢卡斯发货案件考虑是否哈桑风口浪尖哈桑考了几分哈桑李开复安徽弗利萨很费解啊可是联合反恐萨拉回复立法会埃里克森发哈尽快里飞沙离开啥加快了方式啊回复较快拉升睡觉了卡哈佛是t',0,0,0,0,'2022-09-01 22:14:49','2022-09-01 22:14:49',_binary '',_binary ''),(29,15,2,'[]','测试测试','tes萨克等哈就开始的卡号大家卡刷道具卡和数据库的哈萨克就和大家看山东卡速度还是按开机动画就卡死的和安居客上的哈克等哈就开始回到家卡省的礼金卡其实大家肯定是较快拉升大家快来十大科技老师大家快来很多时间卡了后打开降落伞等哈就开始了很多啦是打开拉萨好的较快拉升的艰苦拉萨回到家ask了获得奥克兰较好的拉克丝好的就卡拉斯回到家ask了好的急啊抗衰老慧大师金克拉等哈苏联空军等哈时间考虑的话案件考虑到手机卡死了很多较快拉升后端数据库安理会是的拉伸哈桑即可获得按时艰苦了发哈较快拉升发哈是捷克洛夫啥借口里飞沙尽快立法和较快拉升回房间卢卡斯发货案件考虑是否哈桑风口浪尖哈桑考了几分哈桑李开复安徽弗利萨很费解啊可是联合反恐萨拉回复立法会埃里克森发哈尽快里飞沙离开啥加快了方式啊回复较快拉升睡觉了卡哈佛是t',0,0,0,0,'2022-09-01 22:18:24','2022-09-01 22:18:24',_binary '\0',_binary '\0');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_collects`
--

DROP TABLE IF EXISTS `post_collects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_collects` (
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  KEY `post_collects_user_id_fk` (`user_id`),
  KEY `post_collects_post_id_fk` (`post_id`),
  CONSTRAINT `post_collects_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `post_collects_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='帖子收藏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_collects`
--

LOCK TABLES `post_collects` WRITE;
/*!40000 ALTER TABLE `post_collects` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_collects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_comments`
--

DROP TABLE IF EXISTS `post_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  `reply_id` int DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `like` int DEFAULT '0',
  `title` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `post_comments_post_id_fk` (`post_id`),
  KEY `post_comments_user_id_fk` (`user_id`),
  CONSTRAINT `post_comments_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `post_comments_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COMMENT='帖子评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_comments`
--

LOCK TABLES `post_comments` WRITE;
/*!40000 ALTER TABLE `post_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_like`
--

DROP TABLE IF EXISTS `post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_like` (
  `user_id` int DEFAULT NULL,
  `post_id` int DEFAULT NULL,
  KEY `post_like_user_id_fk` (`user_id`),
  KEY `post_like_post_id_fk` (`post_id`),
  CONSTRAINT `post_like_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `post_like_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='帖子点赞';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_like`
--

LOCK TABLES `post_like` WRITE;
/*!40000 ALTER TABLE `post_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_type`
--

DROP TABLE IF EXISTS `post_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='帖子类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_type`
--

LOCK TABLES `post_type` WRITE;
/*!40000 ALTER TABLE `post_type` DISABLE KEYS */;
INSERT INTO `post_type` VALUES (1,'分组1'),(2,'分组2'),(3,'分组3'),(4,'分组4');
/*!40000 ALTER TABLE `post_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL COMMENT '账户',
  `password` varchar(33) NOT NULL COMMENT '密码',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `is_activation` bit(1) DEFAULT b'0' COMMENT '是否激活',
  `activation_data` varchar(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_account_uindex` (`account`),
  UNIQUE KEY `user_email_uindex` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (15,'admin','da9f7f271124a9d0ecec9ecde2031378','468373637@qq.com',_binary '','3T253RqCtSpW');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_attention`
--

DROP TABLE IF EXISTS `user_attention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_attention` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `followed_user_id` int NOT NULL COMMENT '被关注',
  PRIMARY KEY (`id`),
  KEY `user_attention_user_id_fk` (`user_id`),
  KEY `user_attention_user_id_fk_2` (`followed_user_id`),
  CONSTRAINT `user_attention_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_attention_user_id_fk_2` FOREIGN KEY (`followed_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='关注表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_attention`
--

LOCK TABLES `user_attention` WRITE;
/*!40000 ALTER TABLE `user_attention` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_attention` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_data` (
  `user_id` int DEFAULT NULL COMMENT '主键',
  `avatar` text COMMENT '图片',
  `name` tinytext COMMENT '名字',
  `sex` bit(1) DEFAULT b'0' COMMENT '性别(false为男)',
  `type` tinyint(1) DEFAULT '0' COMMENT '用户类型',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `user_describe` text COMMENT '描述',
  KEY `user_data_user_id_fk` (`user_id`),
  CONSTRAINT `user_data_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data`
--

LOCK TABLES `user_data` WRITE;
/*!40000 ALTER TABLE `user_data` DISABLE KEYS */;
INSERT INTO `user_data` VALUES (15,NULL,'hhhh',_binary '',0,'2022-09-01 22:13:37','这个人很懒');
/*!40000 ALTER TABLE `user_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_news`
--

DROP TABLE IF EXISTS `user_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_news` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `produce_user_id` int NOT NULL COMMENT '事件发生者',
  `type` tinyint DEFAULT '0' COMMENT '消息类型',
  `post_id` int DEFAULT NULL COMMENT '帖子id',
  `content` text COMMENT '消息内容',
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_news_user_id_fk` (`user_id`),
  KEY `user_news_user_id_fk_2` (`produce_user_id`),
  CONSTRAINT `user_news_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_news_user_id_fk_2` FOREIGN KEY (`produce_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COMMENT='消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_news`
--

LOCK TABLES `user_news` WRITE;
/*!40000 ALTER TABLE `user_news` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_news` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-01 22:19:35
