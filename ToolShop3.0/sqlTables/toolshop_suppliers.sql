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
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (8001,'Grommet Builders','Fred','788 30th St., SE, Calgary'),(8002,'Pong Works','Bart','749 Dufferin Blvd., SE, Calgary'),(8003,'Wiley Inc.','BillyBob','26 40th St., SE, Calgary'),(8004,'Winork Manufacturing Inc.','Marty','156 51st Ave., SE, Calgary'),(8005,'Grab Bag Inc.','Monty','138 42nd Ave., NE, Calgary'),(8006,'Paddy\'s Manufacturing','Barney','311 McCall Way, NE, Calgary'),(8007,'Smickles Industries','Lisa','966 Smed Lane, SE, Calgary'),(8008,'Thangs Inc.','Thelma','879 37th Ave., NE, Calgary'),(8009,'Flip Works Inc.','Rory','472 Ogden Dale Rd., SE, Calgary'),(8010,'Irkle Industries','Irma','754 Sunridge Way, NE, Calgary'),(8011,'Biff Builders','Borjn','690 19th St., NE, Calgary'),(8012,'Twinkles Inc.','Barkley','318 29th St.,NE, Calgary'),(8013,'Piddles Industries','Parnell','238 112th Ave., NE, Calgary'),(8014,'Tic Tac Manufacturing','Teisha','598 Palmer Rd., NE, Calgary'),(8015,'Knock Knock Inc.','Ned','363 42nd Ave., NE, Calgary'),(8016,'Corky Things Inc.','Corrine','35 McCall Way, NE, Calgary'),(8017,'E & O Industries','Stan','883 44th St., SE, Calgary'),(8018,'Fiddleys Makes Stuff Inc.','Fredda','350 27th St., NE, Calgary'),(8019,'Horks and Stuff Manufacturing','Harold','997 42nd Ave., NE, Calgary'),(8020,'Wings Works','Wing','754 48th St., SE, Calgary');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
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
