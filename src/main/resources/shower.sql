-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shower
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `role` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` int DEFAULT '0',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`admin_id`, `admin_name`, `password`, `sex`, `phone`, `birth`, `role`, `create_time`, `is_delete`) VALUES (4,'老王','wdnmd','男','123123','2001-01-06',0,'2021-12-17 18:52:14',1),(6,'老王1','wdnmd','男','123123','2001-01-06',0,'2021-12-17 19:41:37',0),(8,'老王','wdnmd','男','123123','2001-01-06',0,'2021-12-17 20:00:20',0),(9,'老王3','wdnmd',NULL,NULL,NULL,NULL,'2021-12-18 15:26:41',0);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bathroom`
--

DROP TABLE IF EXISTS `bathroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bathroom` (
  `b_id` int NOT NULL,
  `b_name` varchar(20) NOT NULL,
  `people` int DEFAULT '0',
  PRIMARY KEY (`b_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bathroom`
--

LOCK TABLES `bathroom` WRITE;
/*!40000 ALTER TABLE `bathroom` DISABLE KEYS */;
INSERT INTO `bathroom` (`b_id`, `b_name`, `people`) VALUES (101,'男澡堂1',0),(102,'男澡堂2',0),(103,'男澡堂3',0),(201,'女澡堂1',0),(202,'女澡堂2',0),(203,'女澡堂3',0);
/*!40000 ALTER TABLE `bathroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history` (
  `user_id` int NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `b_name` varchar(32) NOT NULL,
  `enter_time` datetime DEFAULT NULL,
  `quit_time` datetime DEFAULT NULL,
  `timeout` datetime NOT NULL,
  `is_quit` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` (`user_id`, `user_name`, `b_name`, `enter_time`, `quit_time`, `timeout`, `is_quit`) VALUES (1004,'11','男澡堂1','2021-12-14 12:06:17','2021-12-14 12:06:38','2021-12-14 13:36:17',1),(1006,'老王','男澡堂1','2021-12-14 18:11:00','2021-12-14 18:12:06','2021-12-14 19:41:00',1),(1006,'老王','男澡堂1','2021-12-18 21:09:22','2021-12-18 21:11:07','2021-12-18 22:39:22',1),(1006,'老王','男澡堂1','2021-12-19 11:00:38','2021-12-19 11:01:11','2021-12-19 12:30:38',1),(1006,'老王','男澡堂1','2021-12-19 11:03:10','2021-12-19 11:03:27','2021-12-19 12:33:10',1),(1006,'老王','男澡堂1','2021-12-19 11:06:24','2021-12-19 11:06:40','2021-12-19 12:36:24',1),(1006,'老王','男澡堂1','2021-12-19 11:07:52','2021-12-19 11:08:17','2021-12-19 12:37:52',1),(1006,'老王','男澡堂1','2021-12-19 11:08:31','2021-12-19 11:08:46','2021-12-19 12:38:31',1),(1006,'老王','男澡堂1','2021-12-19 11:13:32','2021-12-19 11:13:56','2021-12-19 12:43:32',1),(1006,'老王','男澡堂1','2021-12-19 11:18:19','2021-12-19 11:18:31','2021-12-19 12:48:19',1),(1006,'老王','男澡堂2','2021-12-19 11:18:46','2021-12-19 11:19:00','2021-12-19 12:48:46',1),(1006,'老王','男澡堂2','2021-12-19 11:23:03','2021-12-19 11:23:12','2021-12-19 12:53:03',1),(1006,'老王','男澡堂2','2021-12-19 11:25:27','2021-12-19 11:25:39','2021-12-19 12:55:27',1),(1006,'老王','男澡堂2','2021-12-19 11:26:37','2021-12-19 11:35:54','2021-12-19 12:56:37',1),(1006,'老王','男澡堂2','2021-12-19 11:36:04','2021-12-19 11:36:09','2021-12-19 13:06:04',1),(1006,'老王','男澡堂2','2021-12-19 11:44:37','2021-12-19 11:44:51','2021-12-19 13:14:37',1),(1006,'老王','男澡堂2','2021-12-19 11:46:41','2021-12-19 11:46:51','2021-12-19 13:16:41',1),(1006,'老王','女澡堂3','2021-12-19 12:44:49','2021-12-19 12:45:13','2021-12-19 13:14:49',1),(1008,'小明','男澡堂3','2021-12-23 16:21:26','2021-12-23 16:21:46','2021-12-23 16:51:26',1),(1008,'小明','男澡堂3','2021-12-23 16:22:08','2021-12-23 16:22:17','2021-12-23 17:52:08',1);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `password` varchar(100) NOT NULL DEFAULT '666666',
  `birth` date NOT NULL DEFAULT '2020-01-01',
  `phone` varchar(20) NOT NULL,
  `sex` varchar(5) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `in_bath` int DEFAULT NULL,
  `is_shower` int DEFAULT '0',
  `money` int DEFAULT '0',
  `is_delete` int DEFAULT '0',
  PRIMARY KEY (`user_id`),
  KEY `user_bathroom_b_id_fk` (`in_bath`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `user_name`, `password`, `birth`, `phone`, `sex`, `create_time`, `in_bath`, `is_shower`, `money`, `is_delete`) VALUES (1000,'admin','admin1234','2000-01-06','0','男','2000-01-01 00:00:00',NULL,0,0,1),(1001,'lw','123456','2001-01-06','13982432448','男',NULL,NULL,0,500000,0),(1002,'lz','123456','2001-01-07','123123123','男',NULL,NULL,0,600000,0),(1003,'小明','111111','2000-01-01','111111','女',NULL,NULL,0,0,1),(1004,'11','111','2021-12-13','123123','男','2021-12-13 20:44:58',NULL,0,1390,1),(1005,'22','123','2021-12-13','123123','男','2021-12-13 21:16:48',NULL,0,0,1),(1006,'老王','123456','2021-12-14','123123','男','2021-12-14 18:02:45',203,0,1650,0),(1007,'小明','666666','2020-01-01','11111','男','2021-12-23 15:43:32',NULL,0,0,1),(1008,'小明','666666','2020-01-01','11111111','男','2021-12-23 16:20:48',103,0,65,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-23 17:40:49
