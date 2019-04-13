-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: toolshop
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `items` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `suppId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1000,'Knock Bits',12.67,88,8015),(1001,'Widgets',35.5,10,8004),(1002,'Grommets',23.45,20,8001),(1003,'Wedges',10.15,15,8020),(1004,'Wing Bats',11.25,11,8003),(1005,'Twinkies',15.75,75,8012),(1006,'Wiggles',12.34,30,8020),(1007,'Bing Bobs',2.39,25,8005),(1008,'Wog Wits',19.99,300,8004),(1009,'Barney Bits',23.59,21,8006),(1010,'Willie Widgits',12.99,89,8003),(1011,'Barge Bogs',2.99,35,8011),(1012,'Poggy Pogs',1.1,99,8002),(1013,'Pardle Pins',0.69,400,8013),(1014,'Piddley Wicks',5.19,54,8013),(1015,'Iggy Orks',49.95,22,8010),(1016,'Crank Cribs',0.29,888,8005),(1017,'Thingies',45.98,67,8008),(1018,'Orf Dappers',19.98,32,8018),(1019,'Piff Knocks',4.95,82,8002),(1020,'Knit Piks',6.75,66,8015),(1021,'Piddley Pins',0.25,370,8020),(1022,'Tic Tocs',1.36,87,8014),(1023,'Tin Wits',5.67,23,8014),(1024,'Thinga-ma-bobs',10.98,40,8012),(1025,'Fling Flobs',2.33,254,8009),(1026,'Barn Bins',88.67,45,8006),(1027,'Flap Wrappers',44.88,89,8009),(1028,'Pong Pangs',0.1,2345,8002),(1029,'Oof Tongs',8.49,345,8011),(1030,'Nic Nacs',2.99,238,8015),(1031,'Tork Tins',0.95,376,8012),(1032,'Lilly Larks',12.99,56,8007),(1033,'Minnie Morks',1.95,800,8007),(1034,'Cork Handles',2.66,654,8016),(1035,'Ding Darns',0.15,1208,8019),(1036,'Erk Orks',3.99,498,8017),(1037,'Foo Figs',5.89,234,8018),(1038,'Googly Eyes',6.99,756,8001),(1039,'Handy Pandies',4.35,321,8017),(1040,'Inny Outies',3.45,219,8010);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-12 20:58:54
