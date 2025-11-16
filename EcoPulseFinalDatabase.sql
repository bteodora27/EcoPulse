CREATE DATABASE  IF NOT EXISTS `ecopulse` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_roman_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ecopulse`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: ecopulse
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chat_history`
--

DROP TABLE IF EXISTS `chat_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `answer` text COLLATE utf8mb3_roman_ci,
  `question` text COLLATE utf8mb3_roman_ci,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_history`
--

LOCK TABLES `chat_history` WRITE;
/*!40000 ALTER TABLE `chat_history` DISABLE KEYS */;
INSERT INTO `chat_history` VALUES (1,'Test raspuns','Test intrebare','2025-11-14 15:59:39.387642'),(2,'Test raspuns','Test intrebare','2025-11-14 15:59:48.483937'),(3,'Test raspuns','Test intrebare','2025-11-14 15:59:50.748773'),(4,'Test raspuns','Test intrebare','2025-11-14 15:59:52.580869'),(5,'Test raspuns','Test intrebare','2025-11-14 15:59:53.775054'),(6,'non_empty','Imagine Analizată','2025-11-14 16:26:23.199968'),(7,'Test raspuns','Test intrebare','2025-11-14 18:58:20.772209'),(8,'non_empty','Imagine Analizată','2025-11-14 18:59:32.853837'),(9,'non_empty','Imagine Analizată','2025-11-14 19:07:19.045233'),(10,'Test raspuns','Test intrebare','2025-11-14 19:32:35.298801'),(11,'Test raspuns','Test intrebare','2025-11-14 19:47:43.283090'),(12,'Test raspuns','Test intrebare','2025-11-14 19:47:44.516454'),(13,'Test raspuns','Test intrebare','2025-11-14 19:47:44.982181'),(14,'Test raspuns','Test intrebare','2025-11-14 19:47:45.234952'),(15,'Test raspuns','Test intrebare','2025-11-14 19:48:19.433501'),(16,'Test raspuns','Test intrebare','2025-11-14 19:51:19.391911'),(17,'Test raspuns','Test intrebare','2025-11-14 19:53:32.241626'),(18,'Test raspuns','Test intrebare','2025-11-14 19:54:07.209723'),(19,'Test raspuns','Test intrebare','2025-11-14 19:55:39.185151'),(20,'Test raspuns','Test intrebare','2025-11-14 19:58:18.859632'),(21,'Test raspuns','Test intrebare','2025-11-14 20:05:41.535697'),(22,'Test raspuns','Test intrebare','2025-11-14 20:07:30.275363'),(23,'Test raspuns','Test intrebare','2025-11-14 20:08:46.053459'),(24,'Test raspuns','Test intrebare','2025-11-14 20:29:30.939075'),(25,'Test raspuns','Test intrebare','2025-11-14 20:40:44.972287'),(26,'Test raspuns','Test intrebare','2025-11-14 21:10:10.622958'),(27,'Test raspuns','Test intrebare','2025-11-14 21:17:02.360710'),(28,'Test raspuns','Test intrebare','2025-11-14 21:21:16.777645'),(29,'Test raspuns','Test intrebare','2025-11-14 21:26:23.775051'),(30,'Test raspuns','Test intrebare','2025-11-14 21:30:27.205835'),(31,'Test raspuns','Test intrebare','2025-11-14 21:49:13.099624'),(32,'Test raspuns','Test intrebare','2025-11-14 21:50:40.796717'),(33,'Test raspuns','Test intrebare','2025-11-14 21:50:59.422379'),(34,'Test raspuns','Test intrebare','2025-11-14 21:51:19.313794'),(35,'Test raspuns','Test intrebare','2025-11-14 21:51:42.994497'),(36,'Test raspuns','Test intrebare','2025-11-15 11:04:13.871910'),(37,'Test raspuns','Test intrebare','2025-11-15 11:04:50.587541'),(38,'Test raspuns','Test intrebare','2025-11-15 11:05:02.988976'),(39,'Test raspuns','Test intrebare','2025-11-15 11:06:38.596845'),(40,'Test raspuns','Test intrebare','2025-11-15 11:10:26.386000'),(41,'Test raspuns','Test intrebare','2025-11-15 11:26:58.345888'),(42,'Test raspuns','Test intrebare','2025-11-15 11:37:18.182573'),(43,'Test raspuns','Test intrebare','2025-11-15 11:52:07.258582'),(44,'Test raspuns','Test intrebare','2025-11-15 12:01:53.061066'),(45,'Test raspuns','Test intrebare','2025-11-15 12:02:24.035351'),(46,'Test raspuns','Test intrebare','2025-11-15 12:19:27.774478'),(47,'Test raspuns','Test intrebare','2025-11-15 12:23:16.004531'),(48,'Test raspuns','Test intrebare','2025-11-15 13:05:31.643945'),(49,'Test raspuns','Test intrebare','2025-11-15 13:06:17.317162'),(50,'Test raspuns','Test intrebare','2025-11-15 13:45:57.326627'),(51,'Test raspuns','Test intrebare','2025-11-15 13:47:39.751262'),(52,'Test raspuns','Test intrebare','2025-11-15 13:57:41.512381'),(53,'Test raspuns','Test intrebare','2025-11-15 14:05:08.634326'),(54,'Test raspuns','Test intrebare','2025-11-15 14:05:42.755881'),(55,'Test raspuns','Test intrebare','2025-11-15 14:16:29.858361'),(56,'Test raspuns','Test intrebare','2025-11-15 14:18:41.387535'),(57,'Test raspuns','Test intrebare','2025-11-15 14:19:20.157162'),(58,'Test raspuns','Test intrebare','2025-11-15 14:21:00.457996'),(59,'Test raspuns','Test intrebare','2025-11-15 14:24:36.155801'),(60,'Test raspuns','Test intrebare','2025-11-15 15:11:29.181154'),(61,'Test raspuns','Test intrebare','2025-11-15 15:12:21.741292'),(62,'Test raspuns','Test intrebare','2025-11-15 15:12:25.021832'),(63,'Test raspuns','Test intrebare','2025-11-15 15:14:44.653321'),(64,'Test raspuns','Test intrebare','2025-11-15 15:23:34.420926'),(65,'Test raspuns','Test intrebare','2025-11-15 15:44:53.502273'),(66,'Test raspuns','Test intrebare','2025-11-15 16:09:30.510892'),(67,'Test raspuns','Test intrebare','2025-11-15 16:10:15.033279'),(68,'Test raspuns','Test intrebare','2025-11-15 16:12:16.534012'),(69,'Test raspuns','Test intrebare','2025-11-15 16:20:28.267271'),(70,'Test raspuns','Test intrebare','2025-11-15 16:22:58.498539'),(71,'Test raspuns','Test intrebare','2025-11-15 16:24:20.956182'),(72,'Test raspuns','Test intrebare','2025-11-15 16:25:01.686097'),(73,'Test raspuns','Test intrebare','2025-11-15 16:28:25.449041'),(74,'Test raspuns','Test intrebare','2025-11-15 16:28:57.443419'),(75,'Test raspuns','Test intrebare','2025-11-15 16:29:15.576209'),(76,'Test raspuns','Test intrebare','2025-11-15 16:32:33.767954'),(77,'Test raspuns','Test intrebare','2025-11-15 16:58:42.462476'),(78,'Test raspuns','Test intrebare','2025-11-15 17:00:21.271527'),(79,'Test raspuns','Test intrebare','2025-11-15 17:06:59.552891'),(80,NULL,'Imagine Analizată','2025-11-15 17:53:39.635901'),(81,NULL,'Imagine Analizată','2025-11-15 17:55:07.259070');
/*!40000 ALTER TABLE `chat_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cleaningimages`
--

DROP TABLE IF EXISTS `cleaningimages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cleaningimages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` enum('BEFORE','AFTER') COLLATE utf8mb3_roman_ci NOT NULL,
  `image_url` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `cleaning_session_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cleaning_session_id` (`cleaning_session_id`),
  CONSTRAINT `cleaningimages_ibfk_1` FOREIGN KEY (`cleaning_session_id`) REFERENCES `cleaningsessions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cleaningimages`
--

LOCK TABLES `cleaningimages` WRITE;
/*!40000 ALTER TABLE `cleaningimages` DISABLE KEYS */;
/*!40000 ALTER TABLE `cleaningimages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cleaningsessions`
--

DROP TABLE IF EXISTS `cleaningsessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cleaningsessions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `session_time` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `user_id` bigint NOT NULL,
  `afterPhotoUrl` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `bagCount` int DEFAULT NULL,
  `bagsPhotoUrl` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `beforePhotoUrl` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `endTime` datetime(6) DEFAULT NULL,
  `pointsGained` int DEFAULT NULL,
  `startTime` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cleaningsessions_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cleaningsessions`
--

LOCK TABLES `cleaningsessions` WRITE;
/*!40000 ALTER TABLE `cleaningsessions` DISABLE KEYS */;
INSERT INTO `cleaningsessions` VALUES (6,'COMPLETED',44.5,26.01234,6,'simulated_path/1763243503076_pozaclaraclean.jpg',3,'simulated_path/1763243503076_pozatestfinal1.jpg','simulated_path/1763242566412_gunoi.jpg','2025-11-15 23:51:43.076236',30,'2025-11-15 23:36:06.419690'),(7,'COMPLETED',44.5,26.01234,6,'simulated_path/1763244064163_pozaclaraclean.jpg',3,'simulated_path/1763244064163_pozatestfinal1.jpg','simulated_path/1763243910123_testdirty.jpg','2025-11-16 00:01:04.163694',30,'2025-11-15 23:58:30.132022'),(8,'STARTED',44.5,26.01234,6,NULL,0,NULL,'simulated_path/1763269923792_testdirty.jpg',NULL,0,'2025-11-16 07:12:03.809899'),(9,'STARTED',44.5,26.01234,6,NULL,0,NULL,'simulated_path/1763270259891_testdirty.jpg',NULL,0,'2025-11-16 07:17:39.898497'),(10,'COMPLETED',44.5,26.01234,11,'simulated_path/1763270526415_pozaclaraclean.jpg',3,'simulated_path/1763270526415_uni.jpg','simulated_path/1763270423741_testdirty.jpg','2025-11-16 07:22:06.415736',30,'2025-11-16 07:20:23.749165'),(11,'COMPLETED',44.5,26.01234,11,'simulated_path/1763292005085_pozaclaraclean.jpg',3,'simulated_path/1763292005085_pozatestfinal1.jpg','simulated_path/1763271126855_testdirty.jpg','2025-11-16 13:20:05.085856',30,'2025-11-16 07:32:06.856016'),(12,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763274569180_upload_1763274568055.jpg',NULL,0,'2025-11-16 08:29:29.183354'),(13,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763275049237_upload_1763275048530.jpg',NULL,0,'2025-11-16 08:37:29.237025'),(14,'STARTED',45.7575,21.2288,13,NULL,0,NULL,'simulated_path/1763275544233_upload_1763275543024.jpg',NULL,0,'2025-11-16 08:45:44.235977'),(15,'STARTED',45.7575,21.2288,13,NULL,0,NULL,'simulated_path/1763275571603_upload_1763275570067.jpg',NULL,0,'2025-11-16 08:46:11.604335'),(16,'STARTED',44.5,26.01234,6,NULL,0,NULL,'simulated_path/1763275728476_testdirty.jpg',NULL,0,'2025-11-16 08:48:48.476550'),(17,'STARTED',44.5,26.01234,6,NULL,0,NULL,'simulated_path/1763275762426_testdirty.jpg',NULL,0,'2025-11-16 08:49:22.426549'),(18,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763275819485_upload_1763275818199.jpg',NULL,0,'2025-11-16 08:50:19.485806'),(19,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763276129041_upload_1763276127473.jpg',NULL,0,'2025-11-16 08:55:29.041352'),(20,'STARTED',45.7513,21.2291,15,NULL,0,NULL,'simulated_path/1763276750954_upload_1763276749487.jpg',NULL,0,'2025-11-16 09:05:50.956271'),(21,'STARTED',44.5,26.01234,6,NULL,0,NULL,'simulated_path/1763276783543_testdirty.jpg',NULL,0,'2025-11-16 09:06:23.543195'),(22,'STARTED',44.5,26.01234,6,NULL,0,NULL,'simulated_path/1763276790347_testdirty.jpg',NULL,0,'2025-11-16 09:06:30.348004'),(23,'STARTED',44.5,26.01234,6,NULL,0,NULL,'simulated_path/1763276800672_testdirty.jpg',NULL,0,'2025-11-16 09:06:40.672243'),(24,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763277235301_upload_1763277234484.jpg',NULL,0,'2025-11-16 09:13:55.303686'),(25,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763284856754_upload_1763284855304.jpg',NULL,0,'2025-11-16 11:20:56.756420'),(26,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763284962274_upload_1763284960842.jpg',NULL,0,'2025-11-16 11:22:42.274915'),(27,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763285580597_upload_1763285579087.jpg',NULL,0,'2025-11-16 11:33:00.597006'),(28,'COMPLETED',45.7575,21.2288,15,'simulated_path/1763285908585_afterPhoto.jpg',2,'simulated_path/1763285908585_bagsPhoto.jpg','simulated_path/1763285874963_upload_1763285872790.jpg','2025-11-16 11:38:28.585314',20,'2025-11-16 11:37:54.964338'),(29,'STARTED',45.7575,21.2288,15,NULL,0,NULL,'simulated_path/1763286092552_upload_1763286091136.jpg',NULL,0,'2025-11-16 11:41:32.552668'),(30,'COMPLETED',45.7575,21.2288,15,'simulated_path/1763286246327_afterPhoto.jpg',3,'simulated_path/1763286246327_bagsPhoto.jpg','simulated_path/1763286235306_upload_1763286233689.jpg','2025-11-16 11:44:06.327442',30,'2025-11-16 11:43:55.307835'),(31,'COMPLETED',45.7575,21.2288,15,'simulated_path/1763290913125_afterPhoto.jpg',3,'simulated_path/1763290913125_bagsPhoto.jpg','simulated_path/1763290899486_upload_1763290898241.jpg','2025-11-16 13:01:53.125499',30,'2025-11-16 13:01:39.489970'),(32,'COMPLETED',45.7575,21.2288,15,'simulated_path/1763291711554_afterPhoto.jpg',3,'simulated_path/1763291711554_bagsPhoto.jpg','simulated_path/1763291697574_upload_1763291696171.jpg','2025-11-16 13:15:11.554189',30,'2025-11-16 13:14:57.576922'),(33,'COMPLETED',44.5,26.01234,6,'simulated_path/1763291998242_pozaclaraclean.jpg',3,'simulated_path/1763291998242_pozatestfinal1.jpg','simulated_path/1763291919588_testdirty.jpg','2025-11-16 13:19:58.242029',30,'2025-11-16 13:18:39.588855'),(34,'STARTED',45.7513,21.2291,15,NULL,0,NULL,'simulated_path/1763292022048_upload_1763292021044.jpg',NULL,0,'2025-11-16 13:20:22.049977'),(35,'STARTED',45.7575,21.2288,24,NULL,0,NULL,'simulated_path/1763292229165_upload_1763292228613.jpg',NULL,0,'2025-11-16 13:23:49.166639');
/*!40000 ALTER TABLE `cleaningsessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversationparticipants`
--

DROP TABLE IF EXISTS `conversationparticipants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conversationparticipants` (
  `conversationID` bigint NOT NULL,
  `userID` bigint NOT NULL,
  `joinedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastReadAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`conversationID`,`userID`),
  KEY `userID` (`userID`),
  CONSTRAINT `conversationparticipants_ibfk_1` FOREIGN KEY (`conversationID`) REFERENCES `conversations` (`conversationID`) ON DELETE CASCADE,
  CONSTRAINT `conversationparticipants_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversationparticipants`
--

LOCK TABLES `conversationparticipants` WRITE;
/*!40000 ALTER TABLE `conversationparticipants` DISABLE KEYS */;
/*!40000 ALTER TABLE `conversationparticipants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversations`
--

DROP TABLE IF EXISTS `conversations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conversations` (
  `conversationID` bigint NOT NULL AUTO_INCREMENT,
  `conversationType` enum('direct','group') COLLATE utf8mb3_roman_ci DEFAULT 'direct',
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastMessageAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`conversationID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversations`
--

LOCK TABLES `conversations` WRITE;
/*!40000 ALTER TABLE `conversations` DISABLE KEYS */;
/*!40000 ALTER TABLE `conversations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventparticipants`
--

DROP TABLE IF EXISTS `eventparticipants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventparticipants` (
  `userID` bigint NOT NULL,
  `eventID` bigint NOT NULL,
  `userRole` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `feedback` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `rating` int DEFAULT NULL,
  PRIMARY KEY (`userID`,`eventID`),
  KEY `eventID` (`eventID`),
  CONSTRAINT `eventparticipants_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE,
  CONSTRAINT `eventparticipants_ibfk_2` FOREIGN KEY (`eventID`) REFERENCES `events` (`eventID`) ON DELETE CASCADE,
  CONSTRAINT `eventparticipants_chk_1` CHECK (((`rating` >= 1) and (`rating` <= 5)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventparticipants`
--

LOCK TABLES `eventparticipants` WRITE;
/*!40000 ALTER TABLE `eventparticipants` DISABLE KEYS */;
INSERT INTO `eventparticipants` VALUES (6,1,'participant',NULL,NULL);
/*!40000 ALTER TABLE `eventparticipants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `eventID` bigint NOT NULL AUTO_INCREMENT,
  `eventName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `location` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `creationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `visibility` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `creatorID` bigint NOT NULL,
  `totalPoints` int DEFAULT '0',
  `adress` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `contactEmail` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `organizationName` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `website` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  PRIMARY KEY (`eventID`),
  KEY `fk_event_creator` (`creatorID`),
  CONSTRAINT `fk_event_creator` FOREIGN KEY (`creatorID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,'Curățenie Parc Herăstrău','Acțiune de ecologizare în zona Herăstrău','2025-11-16 08:00:00','2025-11-16 12:00:00','Parc Herăstrău, București','2025-11-16 07:22:38','public','completed',6,30,NULL,NULL,NULL,NULL,NULL),(2,'Plantare copaci în Parcul Carol','Plantăm 50 de copaci pentru a verzi orașul','2025-11-16 09:00:00','2025-11-16 14:00:00','Parcul Carol, București','2025-11-16 07:22:38','public','ongoing',15,0,NULL,NULL,NULL,NULL,NULL),(3,'Curățare Parcul Rozelor','Acțiune de igienizare în parteneriat cu Primăria.','2025-12-01 10:00:00','2025-12-01 14:00:00','Parcul Rozelor, Timișoara','2025-11-16 08:37:05','public','upcoming',17,0,NULL,NULL,NULL,NULL,NULL),(4,'Curățare Parcul Rozelor','Acțiune de igienizare în parteneriat cu Primăria.','2025-12-01 10:00:00','2025-12-01 14:00:00','Parcul Rozelor, Timișoara','2025-11-16 08:40:32','public','upcoming',17,0,NULL,NULL,NULL,NULL,NULL),(5,'Curățare Parcul Rozelor','Acțiune de igienizare în parteneriat cu Primăria.','2025-12-01 10:00:00','2025-12-01 14:00:00','Parcul Rozelor, Timișoara','2025-11-16 09:08:22',NULL,NULL,17,0,NULL,NULL,NULL,NULL,NULL),(6,'Test Eveniment Expirat (Ongoing)','Acest eveniment ar trebui să fie finalizat automat de server.','2025-11-16 10:00:00','2025-11-16 12:00:00','Zona de Test','2025-11-16 11:04:07','public','completed',17,0,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendships`
--

DROP TABLE IF EXISTS `friendships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friendships` (
  `firstStandardUserID` bigint NOT NULL,
  `secondStandardUserID` bigint NOT NULL,
  `friendshipCreationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `initiatedBy` bigint NOT NULL,
  `friendshipStatus` enum('pending','accepted','rejected') COLLATE utf8mb3_roman_ci DEFAULT 'pending',
  PRIMARY KEY (`firstStandardUserID`,`secondStandardUserID`),
  KEY `secondStandardUserID` (`secondStandardUserID`),
  CONSTRAINT `friendships_ibfk_1` FOREIGN KEY (`firstStandardUserID`) REFERENCES `standardusers` (`userID`) ON DELETE CASCADE,
  CONSTRAINT `friendships_ibfk_2` FOREIGN KEY (`secondStandardUserID`) REFERENCES `standardusers` (`userID`) ON DELETE CASCADE,
  CONSTRAINT `friendships_chk_1` CHECK ((`firstStandardUserID` <> `secondStandardUserID`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendships`
--

LOCK TABLES `friendships` WRITE;
/*!40000 ALTER TABLE `friendships` DISABLE KEYS */;
/*!40000 ALTER TABLE `friendships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginattempts`
--

DROP TABLE IF EXISTS `loginattempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loginattempts` (
  `attemptID` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `ipAdress` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `attemptedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `succes` tinyint(1) DEFAULT '0',
  `failureReason` enum('invalidEmail','invalidPassword') COLLATE utf8mb3_roman_ci DEFAULT NULL,
  PRIMARY KEY (`attemptID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginattempts`
--

LOCK TABLES `loginattempts` WRITE;
/*!40000 ALTER TABLE `loginattempts` DISABLE KEYS */;
/*!40000 ALTER TABLE `loginattempts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `messageID` bigint NOT NULL AUTO_INCREMENT,
  `conversationID` bigint NOT NULL,
  `senderID` bigint NOT NULL,
  `content` text COLLATE utf8mb3_roman_ci NOT NULL,
  `contentType` enum('text','image') COLLATE utf8mb3_roman_ci DEFAULT 'text',
  `mediaURL` varchar(500) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `sendAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`messageID`),
  KEY `conversationID` (`conversationID`),
  KEY `senderID` (`senderID`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`conversationID`) REFERENCES `conversations` (`conversationID`) ON DELETE CASCADE,
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`senderID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organisationvolunteers`
--

DROP TABLE IF EXISTS `organisationvolunteers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organisationvolunteers` (
  `organisationID` bigint NOT NULL,
  `volunteerID` bigint NOT NULL,
  `dateJoined` date DEFAULT (curdate()),
  PRIMARY KEY (`organisationID`,`volunteerID`),
  KEY `volunteerID` (`volunteerID`),
  CONSTRAINT `organisationvolunteers_ibfk_1` FOREIGN KEY (`organisationID`) REFERENCES `organizations` (`userID`) ON DELETE CASCADE,
  CONSTRAINT `organisationvolunteers_ibfk_2` FOREIGN KEY (`volunteerID`) REFERENCES `standardusers` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organisationvolunteers`
--

LOCK TABLES `organisationvolunteers` WRITE;
/*!40000 ALTER TABLE `organisationvolunteers` DISABLE KEYS */;
/*!40000 ALTER TABLE `organisationvolunteers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizations` (
  `userID` bigint NOT NULL,
  `organizationName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `contactEmail` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `website` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `adress` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `logo` varchar(500) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `totalEventsCreated` int DEFAULT '0',
  `canCreateEvents` tinyint(1) DEFAULT '1',
  `verified` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `contactEmail` (`contactEmail`),
  UNIQUE KEY `phone` (`phone`),
  CONSTRAINT `organizations_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE,
  CONSTRAINT `ck_totalEventsCreatedValue` CHECK ((`totalEventsCreated` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` VALUES (17,'Reciclare România','0755667788','contact@reciclare.ro','Promovăm reciclarea activă.','http://www.reciclare.ro','Str. Viitorului, Nr. 10',NULL,0,1,0),(19,'Green Future','0774685934','info@greenfuture.org','Plantăm viitorul.','http://www.greenfuture.org','Bulevardul Păcii, Nr. 50',NULL,0,1,0),(20,'Voluntarii României','0722334455','contact@voluntarii.ro',NULL,NULL,'Piața Unirii, Nr. 1',NULL,0,1,0),(21,'Org 1','0755650610','org1contact@gmail.com',NULL,NULL,'Str. Marului nr. 1',NULL,0,1,0),(22,'Org 2','0712345678','org2contact@gmail.com','organizatia 2','http://www.org2.ro','Str. Parului nr. 2',NULL,0,1,0);
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `standardusers`
--

DROP TABLE IF EXISTS `standardusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `standardusers` (
  `userID` bigint NOT NULL,
  `firstName` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `lastName` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `profilePicture` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `standardUserRank` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `totalEventsJoined` int DEFAULT '0',
  `totalPoints` int DEFAULT '0',
  `canCreateEvents` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `phone` (`phone`),
  CONSTRAINT `standardusers_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE,
  CONSTRAINT `ck_totalEventsJoinedValue` CHECK ((`totalEventsJoined` >= 0)),
  CONSTRAINT `ck_totalPointsValue` CHECK ((`totalPoints` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `standardusers`
--

LOCK TABLES `standardusers` WRITE;
/*!40000 ALTER TABLE `standardusers` DISABLE KEYS */;
INSERT INTO `standardusers` VALUES (6,'Teodora','Back','0755650610','1990-02-15',NULL,'Sprout',0,122,0),(8,'Andru','Aluculesi','0755650611','1990-02-15',NULL,'Seed',0,0,0),(9,'Vasi','Ples','0712345678','2005-07-07',NULL,'Seed',0,0,0),(10,'Vasi1','Ples1','0712345679','2005-07-07',NULL,'Seed',0,0,0),(11,'Ana','Mere','0712468999','1990-02-04',NULL,'Sprout',0,60,0),(12,'Ama','MMM','0766655555','1998-08-08',NULL,'Seed',0,0,0),(13,'Mara','Popescu','0798765432','2005-07-07',NULL,'Seed',0,0,0),(14,'Mihai','Eminescu','0700000000','1990-01-15',NULL,'Seed',0,0,0),(15,'Andreea','Ionescu','0788665544','2005-06-07',NULL,'Sprout',0,110,0),(23,'Gigel','Frone','0711223344','1992-03-10',NULL,'Seed',0,0,0),(24,'Ionut','Ionescu','0712365498','2005-06-27',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `standardusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `passHash` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `userType` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `userCreationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastLogin` timestamp NULL DEFAULT NULL,
  `loginCount` int DEFAULT '0',
  `failedLoginAttempts` int DEFAULT '0',
  `passwordChangedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (6,'teo','teo@gmail.com','$2a$10$GqQp7UEoZGUbeN04p1xJ4e/WhjlKlKp1S1Qp3.laJO72WGYacfeRG','standard','2025-11-15 11:05:06',NULL,NULL,NULL,NULL),(8,'andru','andru@gmail.com','$2a$10$BgzZVVpypNuK/k9rYgKp/OK874NzMAx0G11HQDuKaLgzSfkFyDnY6','standard','2025-11-15 11:06:58',NULL,NULL,NULL,NULL),(9,'ples','ples@gmail.com','$2a$10$EH9O.nsrb5x9TRPyrbSGeex6UGStNhAOZ3hR72vrpXiMDUKoZXDKy','standard','2025-11-15 11:36:12',NULL,NULL,NULL,NULL),(10,'ples1','ples1@gmail.com','$2a$10$GxRIeQda6pRU92sb7.6yvemuzIjtWEQ3lKCxuZNm40wEAXwARQeGm','standard','2025-11-15 11:36:47',NULL,NULL,NULL,NULL),(11,'ana','ana@gmail.com','$2a$10$OBpxILEIzIWFf7DS40ksGOi/fAvTZGTxqmhmQngfCMPbiy3awc88i','standard','2025-11-15 11:40:55',NULL,NULL,NULL,NULL),(12,'ama','ama@gmail.com','$2a$10$Q/QBd6DsnRbXcVsZNAdrSuPtMmGU8lvKnk9NGMknom3RoLqT0xDVi','standard','2025-11-15 11:47:41',NULL,NULL,NULL,NULL),(13,'mara','mara@gmail.com','$2a$10$DhP6y4aFei6Y1U9htMvrNu.4vOiOZQDT4upRwNfMyuooTkGs8sI9e','standard','2025-11-15 11:55:48',NULL,NULL,NULL,NULL),(14,'test_final_user','test.user.final@example.com','$2a$10$EAtKXizTev9.DjioME4aGux4RRHcqM4FA5Cr9z3xUoIJMbTq7Y/mS','standard','2025-11-15 12:06:25',NULL,NULL,NULL,NULL),(15,'andreea','andreea@gmail.com','$2a$10$CRLRXbiVvilKUYE.b74XUuhbCEFXDKPe2ycuTHEcywZaAORAyoybG','standard','2025-11-15 12:34:23',NULL,NULL,NULL,NULL),(17,'reciclare_ro','contact@reciclare.ro','$2a$10$yEAQ6o5H0Ukdk0hloVTKUO89/qpGVt4hB6VYKddkqBIu1YFzQVYJ2','organization','2025-11-15 13:57:50',NULL,NULL,NULL,NULL),(19,'green_future_ong','info@greenfuture.org','$2a$10$CUJWU9xcdkTYzhyv.jY5I.wQyMaW2Sya2YygsP0PLY19yC6hKWSS.','organization','2025-11-15 14:05:43',NULL,NULL,NULL,NULL),(20,'voluntarii_ro','ong.voluntari@example.com','$2a$10$YwqbzzweG6sQFmTpSlRHV.5c/lcz9piE1JXJMHVrSXDbS7O02iIIS','organization','2025-11-15 14:21:21',NULL,NULL,NULL,NULL),(21,'org1','org1@gmail.com','$2a$10$HoMmwF34YPYxUkHnbIWIQuiQb8Q3HBeCK2aFyUmAcaFhqG8AJVJO.','organization','2025-11-15 14:51:02',NULL,NULL,NULL,NULL),(22,'org2','org2@gmail.com','$2a$10$hdCqa5.83FZ6kVYmv9zXL.hhb34eezwmn9b74UXfOWn4uom1sJHku','organization','2025-11-15 14:53:44',NULL,NULL,NULL,NULL),(23,'test_final_user_v2','test.user.final.v2@example.com','$2a$10$DWYAOJ/Y36H7GJxvUkPbUulF81qvzmJfhTRPrV8c16dTSKscAOZHy','standard','2025-11-15 17:17:32',NULL,NULL,NULL,NULL),(24,'ionut27','ionut27@gmail.com','$2a$10$Hhq3l2synvQXmCAzUY2s/OSu0b0F5E/5Ko3YuRwxPBVC6KLzpx3ca','standard','2025-11-16 11:23:11',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userssessions`
--

DROP TABLE IF EXISTS `userssessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userssessions` (
  `sessionID` bigint NOT NULL AUTO_INCREMENT,
  `userID` bigint NOT NULL,
  `accessToken` varchar(500) COLLATE utf8mb3_roman_ci NOT NULL,
  `refreshToken` varchar(500) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `ipAdress` varchar(45) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `deviceInfo` varchar(255) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `userAgent` text COLLATE utf8mb3_roman_ci,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastActivity` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expiresAt` timestamp NOT NULL,
  PRIMARY KEY (`sessionID`),
  KEY `userID` (`userID`),
  CONSTRAINT `userssessions_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userssessions`
--

LOCK TABLES `userssessions` WRITE;
/*!40000 ALTER TABLE `userssessions` DISABLE KEYS */;
INSERT INTO `userssessions` VALUES (1,14,'59a5e53a-3ccb-4a74-b00a-22fcde35a7d8','78f80dee-5b3b-4eaf-b755-851d65d847da',NULL,NULL,NULL,'2025-11-15 12:18:02',NULL,'2025-11-15 20:18:03'),(2,15,'37a4c645-bbf1-46f4-965c-d5bccfeac34c','63ab3436-2be4-41bd-a856-f99f61f50573',NULL,NULL,NULL,'2025-11-15 12:35:59',NULL,'2025-11-15 20:35:59'),(3,15,'9b3367ec-34dc-47ad-b890-bcc4dfc6caaf','77cdd880-7ece-44f3-9d1f-6b45f07423cc',NULL,NULL,NULL,'2025-11-15 12:38:32',NULL,'2025-11-15 20:38:33'),(4,15,'e3bfa028-99a9-4004-9266-a584e9ad2dbf','8065e27d-deb6-4573-9b9f-d995f8bb7deb',NULL,NULL,NULL,'2025-11-15 12:39:07',NULL,'2025-11-15 20:39:07'),(5,15,'a5c1613e-e56f-4dca-8bd8-9168d88a646e','a5cf0b45-187c-4bc0-b476-7fbc67cfdb85',NULL,NULL,NULL,'2025-11-15 13:13:22',NULL,'2025-11-15 21:13:23'),(6,15,'191751d1-2e26-4d18-b1a9-ba56848df77e','6de50b5e-e447-4b4d-9621-2950e5595577',NULL,NULL,NULL,'2025-11-15 13:40:48',NULL,'2025-11-15 21:40:48'),(7,15,'8bfcbaaa-bcec-416a-be69-61477706479e','e9fd65d3-0db2-43c4-8044-14890a39c764',NULL,NULL,NULL,'2025-11-15 13:45:33',NULL,'2025-11-15 21:45:33'),(8,17,'f882b9f8-6f17-4949-aa8f-c8a4325c42de','5cca6019-f325-4127-85c5-c21a3f4e482c',NULL,NULL,NULL,'2025-11-15 14:13:28',NULL,'2025-11-15 22:13:28'),(9,15,'b7656b8a-3c6e-42f7-a804-546cd4c4173c','033866e6-8068-4904-82ec-eef8f0417c52',NULL,NULL,NULL,'2025-11-15 14:18:17',NULL,'2025-11-15 22:18:17'),(10,15,'9e5e258d-0c3c-4356-a06c-ac7e946183a7','465cb5e1-c6e8-4abe-b919-e2e59309fd4f',NULL,NULL,NULL,'2025-11-15 14:24:34',NULL,'2025-11-15 22:24:34'),(11,15,'1231c64a-84de-479c-970d-a77d1afe7ca0','006d3212-99ca-40cd-8ba2-62b7e3fb4121',NULL,NULL,NULL,'2025-11-15 14:29:24',NULL,'2025-11-15 22:29:25'),(12,21,'ae453463-ab6a-41b9-9295-90cf1c9c987e','707bd517-5977-423c-9c91-2d1051bb6ca4',NULL,NULL,NULL,'2025-11-15 14:51:16',NULL,'2025-11-15 22:51:16'),(13,22,'6087a478-f6c1-4916-a74d-b8aeaed2d326','3b7003be-f4ed-4eeb-ad10-1054b195b4a3',NULL,NULL,NULL,'2025-11-15 14:53:53',NULL,'2025-11-15 22:53:53'),(14,15,'25b24622-3521-4a31-8fd0-2a1b3aede507','2efd1e19-327d-4c4b-bebd-a15d2cedb114',NULL,NULL,NULL,'2025-11-15 15:07:10',NULL,'2025-11-15 23:07:10'),(15,15,'1d93821f-d6f2-4ef4-8eac-424aa3201807','3995368d-67ae-4d99-80da-2ea62cf3b074',NULL,NULL,NULL,'2025-11-15 16:54:06',NULL,'2025-11-16 00:54:06'),(16,15,'67193057-9b11-42fd-91ac-c47ce8e1201f','7a4404ae-b352-487b-a775-01fd966e922f',NULL,NULL,NULL,'2025-11-15 16:54:54',NULL,'2025-11-16 00:54:53'),(17,15,'a1b3567f-3625-431e-85d0-f5ecc1c4725a','415cae01-5240-4611-817a-6c5aa24e5d30',NULL,NULL,NULL,'2025-11-15 16:56:36',NULL,'2025-11-16 00:56:36'),(18,15,'0b14570d-7160-400b-acea-0423a651d2d4','433ebab3-c956-440e-8c19-8a08406d55af',NULL,NULL,NULL,'2025-11-15 17:00:11',NULL,'2025-11-16 01:00:11'),(19,15,'f4a3f40b-923b-4835-8155-ade98dc52ed7','8347fea5-fb19-4182-b4e3-aa258fe841d9',NULL,NULL,NULL,'2025-11-15 19:32:35',NULL,'2025-11-16 03:32:35'),(20,15,'4eca364e-a8ac-4546-be9f-8ab03cd468ea','8464329f-bbb9-4438-9e6a-a384ef2ffd6d',NULL,NULL,NULL,'2025-11-15 19:32:35',NULL,'2025-11-16 03:32:35'),(21,15,'285b0bf5-704c-435a-a426-e8f6571f2652','94db6207-adbd-483d-b0eb-d1f6c15d7c5b',NULL,NULL,NULL,'2025-11-15 19:51:49',NULL,'2025-11-16 03:51:49'),(22,15,'fd070254-4bd8-4569-8154-0a78e0346a0f','677404e4-7ff8-431a-828b-e71054998b98',NULL,NULL,NULL,'2025-11-15 19:54:14',NULL,'2025-11-16 03:54:14'),(23,15,'5ea53fa3-249a-4ffc-ac3c-f00d137d5195','2043d764-2824-4346-bd24-7f0bc0082935',NULL,NULL,NULL,'2025-11-15 19:54:41',NULL,'2025-11-16 03:54:40'),(24,15,'4ef35245-6e37-4946-8959-c243a7b02bdc','0f9722e6-ef7e-4743-b9ac-8546bd952b70',NULL,NULL,NULL,'2025-11-15 20:03:27',NULL,'2025-11-16 04:03:26'),(25,15,'db166cf6-5bbb-4f63-9f47-dd0c71515250','5e7cee4a-9afe-4ae3-a6b9-0450386e742a',NULL,NULL,NULL,'2025-11-15 20:05:19',NULL,'2025-11-16 04:05:19'),(26,15,'e35e4467-8f94-4232-bde4-b375cd20275c','0a2f0692-b5c7-45b3-97a9-6a75b9198ede',NULL,NULL,NULL,'2025-11-15 20:08:22',NULL,'2025-11-16 04:08:21'),(27,15,'f3e84d20-e2da-4842-82a9-0a9e2e98ed19','52182786-b135-4734-b809-b11d42fa97b2',NULL,NULL,NULL,'2025-11-15 20:26:52',NULL,'2025-11-16 04:26:52'),(28,15,'b90eb135-de25-42e0-869b-ecf3510c6bbe','8296c698-9945-4d8b-b842-1e5351c84ec5',NULL,NULL,NULL,'2025-11-15 20:31:02',NULL,'2025-11-16 04:31:02'),(29,15,'48c64cc7-5f60-413e-835e-6840f14899d8','e67f5b62-c8f0-4ec0-a2c7-c4d5ac04c8a1',NULL,NULL,NULL,'2025-11-15 20:31:37',NULL,'2025-11-16 04:31:37'),(30,15,'41122f6e-a255-4e3c-8a09-d602ff4ba893','5fcf6bb6-fd7a-4fe3-9b2a-f3f8ccd485ba',NULL,NULL,NULL,'2025-11-15 20:35:48',NULL,'2025-11-16 04:35:47'),(31,15,'cac441ba-b874-46f1-a377-c6d77ce8db83','7ffaf696-54c4-45be-98cf-669c6987013a',NULL,NULL,NULL,'2025-11-15 20:39:55',NULL,'2025-11-16 04:39:55'),(32,15,'2389c8c4-30ca-4e92-9a70-45b1ce7d528c','c4b27e88-71c6-4715-975d-172c4a44531e',NULL,NULL,NULL,'2025-11-15 20:40:32',NULL,'2025-11-16 04:40:32'),(33,15,'3a7c1b6c-666a-4c68-b8db-143824862d4c','2c964a1e-615e-4bf0-8fe8-62f45b7169f0',NULL,NULL,NULL,'2025-11-15 20:40:32',NULL,'2025-11-16 04:40:32'),(34,15,'0c59c5f2-bc1e-46c1-b66e-aecdaca19cbd','1e1928b2-320e-448f-bdeb-4a1fb6610597',NULL,NULL,NULL,'2025-11-15 20:42:52',NULL,'2025-11-16 04:42:52'),(35,15,'476ac617-a81e-468b-94fb-2793c30e5573','582be199-3893-43dd-9777-869320514d8b',NULL,NULL,NULL,'2025-11-15 20:47:39',NULL,'2025-11-16 04:47:39'),(36,15,'5036eb4c-3b9c-4b74-aab2-d38d8401ae3a','a8c0255b-c59c-4f0d-949e-fa87f84272a7',NULL,NULL,NULL,'2025-11-15 20:50:10',NULL,'2025-11-16 04:50:10'),(37,15,'39770ff2-0815-4349-9a89-ec2a870197bc','f7610f25-e79b-4f4d-a5d1-33e80fc3bd50',NULL,NULL,NULL,'2025-11-15 20:55:03',NULL,'2025-11-16 04:55:03'),(38,15,'ee53a3dd-13f7-4b52-b243-215c0c2f66f5','38bb7433-bfe1-4ad8-b0ad-58319560c3d3',NULL,NULL,NULL,'2025-11-15 20:55:20',NULL,'2025-11-16 04:55:20'),(39,15,'7fadebc2-8af8-4a5a-9fee-beb41ee75cc6','8339e547-7765-4a79-a4a0-8455fc5e3683',NULL,NULL,NULL,'2025-11-15 20:58:54',NULL,'2025-11-16 04:58:54'),(40,15,'1fec062d-76dd-43ce-afd3-5537e01efd82','3bdb5655-b344-4fae-8e24-2945b74d6272',NULL,NULL,NULL,'2025-11-15 20:59:58',NULL,'2025-11-16 04:59:57'),(41,15,'54747fdd-5ce7-4482-88b9-ff261cfa7c34','be8ef0ab-b79a-4580-ae06-9fe3074894a4',NULL,NULL,NULL,'2025-11-15 21:01:11',NULL,'2025-11-16 05:01:11'),(42,15,'3ae16ed1-717b-42be-abd3-4d420a95a4be','a1f9040a-42f8-45bf-8800-5030cba366f0',NULL,NULL,NULL,'2025-11-15 21:08:22',NULL,'2025-11-16 05:08:22'),(43,15,'fcb72a0c-4c6c-45ff-983a-feac682b088f','6e62540d-45b6-4445-b2c3-ac920bab595b',NULL,NULL,NULL,'2025-11-15 21:18:15',NULL,'2025-11-16 05:18:14'),(44,15,'6fd70119-3684-47bf-8d20-b5f0cf106084','3b367acc-4fce-42a8-85f8-4a0b95e25b70',NULL,NULL,NULL,'2025-11-15 21:20:46',NULL,'2025-11-16 05:20:46'),(45,15,'25d23c77-f6dd-4441-b592-c50c1fd280c5','3d6e1211-f4ce-493e-bfc3-681e079f39fe',NULL,NULL,NULL,'2025-11-15 21:25:59',NULL,'2025-11-16 05:25:59'),(46,15,'77c552c4-8efd-4d68-a9e2-495d32c9dfe1','82b54644-05a1-4c36-8735-2709e4b197a9',NULL,NULL,NULL,'2025-11-15 21:27:07',NULL,'2025-11-16 05:27:06'),(47,15,'5e42f88f-b86d-4e21-a0b6-2cff5fbf6c12','bf29b86d-de4a-4adb-aa68-ea5c71b04d4c',NULL,NULL,NULL,'2025-11-15 21:29:26',NULL,'2025-11-16 05:29:25'),(48,15,'092249fb-5daf-47af-86ad-9c828836e023','5cdc9be3-2003-4a49-8ceb-f685598edea4',NULL,NULL,NULL,'2025-11-15 21:29:55',NULL,'2025-11-16 05:29:54'),(49,15,'4af11390-edd6-410d-a73e-b945cd874e23','fb712bca-ec96-4ef9-b7f4-9b9dac6b3ebf',NULL,NULL,NULL,'2025-11-15 21:34:52',NULL,'2025-11-16 05:34:52'),(50,15,'607cd30d-96cc-4457-b6a3-26ea4f35f4f8','e310e5a2-7cb6-49bb-9a6a-bda72fc15f54',NULL,NULL,NULL,'2025-11-15 21:36:45',NULL,'2025-11-16 05:36:44'),(51,15,'dcbb5723-2b1f-41a1-b47f-db13e120fcbe','f69eb15a-71fd-4db2-9210-be0b41f85a74',NULL,NULL,NULL,'2025-11-15 21:37:46',NULL,'2025-11-16 05:37:45'),(52,15,'ac21a9e3-707a-4047-87f2-747971e210b0','57655583-7281-4046-ae23-86c3f52dbf51',NULL,NULL,NULL,'2025-11-15 21:38:25',NULL,'2025-11-16 05:38:25'),(53,15,'1dd5dc29-63b5-4a79-b5a7-1dce76bd94a4','b8d7ddc7-8f81-42c8-92d0-af80db377db8',NULL,NULL,NULL,'2025-11-15 21:40:21',NULL,'2025-11-16 05:40:21'),(54,15,'1ce1617d-9905-4c5c-b668-84fc3fdb64ac','467f29db-522b-4a8d-a490-5aff92a5f274',NULL,NULL,NULL,'2025-11-15 21:44:57',NULL,'2025-11-16 05:44:57'),(55,15,'b20007f8-0760-40aa-92c3-a6319f01b93c','c89b3be2-e069-4c00-b860-3960e5c75445',NULL,NULL,NULL,'2025-11-15 21:46:42',NULL,'2025-11-16 05:46:42'),(56,15,'23ed3680-f1bd-4481-8a38-66513d6a8ed0','930b117e-a036-4791-9ff6-66716d27ad95',NULL,NULL,NULL,'2025-11-15 21:54:16',NULL,'2025-11-16 05:54:16'),(57,15,'47e6b6b2-dd00-4bbf-8f7f-55cccfee0ecf','69bea967-4dcc-4c6a-95ad-9438bf3a4f5b',NULL,NULL,NULL,'2025-11-15 21:57:19',NULL,'2025-11-16 05:57:18'),(58,15,'c61546a5-571e-4d42-bec1-a430e7331c50','b648d222-f90c-4425-ace1-dd6e19541165',NULL,NULL,NULL,'2025-11-15 22:11:45',NULL,'2025-11-16 06:11:44'),(59,15,'6f62d742-a6e2-4ebf-8607-d4d3f9c63d69','9630fb97-c445-4dd4-99e7-09ed7ff598ac',NULL,NULL,NULL,'2025-11-15 22:13:04',NULL,'2025-11-16 06:13:03'),(60,15,'27120949-5272-4f9a-8243-c8b2bae6efa6','9e23c87f-72df-47e4-8b16-d8514cf11bab',NULL,NULL,NULL,'2025-11-15 22:23:24',NULL,'2025-11-16 06:23:24'),(61,15,'f997f69c-0834-480b-842b-d524a2fd8d7a','97f98aaa-bf76-4ad6-83e3-3157ddf0f15e',NULL,NULL,NULL,'2025-11-15 22:35:03',NULL,'2025-11-16 06:35:02'),(62,15,'e70bc031-e76c-4b81-9171-1c08c6b87a36','ab2493cc-e765-414b-a709-d7e879a171d5',NULL,NULL,NULL,'2025-11-15 22:35:03',NULL,'2025-11-16 06:35:02'),(63,15,'ba0dcb5c-59c9-42b9-85dc-07c6946bcf92','30628c93-e97f-4b7c-b2e7-a325f10d8a5b',NULL,NULL,NULL,'2025-11-15 22:35:03',NULL,'2025-11-16 06:35:02'),(64,15,'24f3fe09-6361-4507-911f-bf8bc5bd2e7f','1ef36938-28d7-4e43-8a14-1904d32842ce',NULL,NULL,NULL,'2025-11-15 23:16:30',NULL,'2025-11-16 07:16:29'),(65,15,'a14adf84-826e-41d7-8596-b3c615ef7798','8e79632e-76d3-4195-bfcc-140537621971',NULL,NULL,NULL,'2025-11-16 04:22:46',NULL,'2025-11-16 12:22:47'),(66,15,'7fda71ec-5d24-41e0-a856-ed261c6627f8','4a74af97-7e46-409a-8bda-998382e6561e',NULL,NULL,NULL,'2025-11-16 04:51:58',NULL,'2025-11-16 12:51:59'),(67,15,'4f6ac315-a609-4a70-a049-f3e61c653610','1b402487-5466-48b0-9366-e30ac252902f',NULL,NULL,NULL,'2025-11-16 04:53:18',NULL,'2025-11-16 12:53:19'),(68,15,'3953c56c-3d1e-420b-8a8f-b4e30ebe425b','2e9f1b70-0f70-4c64-bf62-b9a64bf54e42',NULL,NULL,NULL,'2025-11-16 05:00:31',NULL,'2025-11-16 13:00:32'),(69,15,'d4f2c1d8-bfb7-4f65-b975-c41a36b76673','26f35ebb-a556-4004-a404-4990b71883f3',NULL,NULL,NULL,'2025-11-16 05:01:49',NULL,'2025-11-16 13:01:50'),(70,15,'41087b21-c26d-4f2c-a90f-db5b5e651079','4a8cea5d-42bd-48df-9aae-5626f80fe20b',NULL,NULL,NULL,'2025-11-16 05:06:53',NULL,'2025-11-16 13:06:54'),(71,15,'57ef523c-fb50-4655-8b1a-a592e6075d03','1818a314-abb6-463d-8e52-01a4994b46f8',NULL,NULL,NULL,'2025-11-16 05:23:10',NULL,'2025-11-16 13:23:11'),(72,15,'e7656105-00d9-4ccd-8c43-6e41be1cab6c','1614941c-58bf-4208-94ea-a186872edaf9',NULL,NULL,NULL,'2025-11-16 05:24:49',NULL,'2025-11-16 13:24:49'),(73,15,'6ca68016-76f7-4990-88f8-71a377aac068','eacc6e05-d7be-48ad-b3ed-48aa9ebd4b09',NULL,NULL,NULL,'2025-11-16 06:19:06',NULL,'2025-11-16 14:19:07'),(74,15,'7cdfe7f9-4e7a-4948-b88a-d317bc591b48','c22b36e5-7022-48e8-b617-e0f5e9f768a1',NULL,NULL,NULL,'2025-11-16 06:20:29',NULL,'2025-11-16 14:20:30'),(75,15,'d262f544-1f2d-46a8-b41d-39f216658a1a','03c8998b-5719-46b7-a8ed-58504ea4ccbd',NULL,NULL,NULL,'2025-11-16 06:29:15',NULL,'2025-11-16 14:29:16'),(76,15,'e71fbab5-4bc4-495a-9c52-98cfd3bb6d7c','f731847b-6ee3-49a9-9853-7e5cf01cf33d',NULL,NULL,NULL,'2025-11-16 06:37:17',NULL,'2025-11-16 14:37:17'),(77,15,'265611ea-2924-470f-915f-571cb4ffcd4b','5f0f1057-b56d-4606-a62c-d052fda3e379',NULL,NULL,NULL,'2025-11-16 06:41:00',NULL,'2025-11-16 14:41:01'),(78,13,'2b86393b-7530-4cbd-a52a-127d515203ef','7a710d74-ff28-4bc3-ac5f-c9fde3c5832f',NULL,NULL,NULL,'2025-11-16 06:45:30',NULL,'2025-11-16 14:45:31'),(79,15,'d8543ad8-aa9f-4747-a451-86bebbe8fea0','34b183d5-b25f-4f7a-8f98-dfc4fafb7533',NULL,NULL,NULL,'2025-11-16 06:50:02',NULL,'2025-11-16 14:50:03'),(80,15,'c4a65b03-9e91-490b-9d3a-72b4156a658e','2799d024-75c0-46f0-a43e-f3b9d9d83728',NULL,NULL,NULL,'2025-11-16 06:55:10',NULL,'2025-11-16 14:55:11'),(81,15,'f784f897-7d65-4f5c-967a-1c71089551f4','8e449a67-4255-4e57-a260-95795ff7a11d',NULL,NULL,NULL,'2025-11-16 07:05:35',NULL,'2025-11-16 15:05:36'),(82,15,'7bcad6d1-a79a-480a-a234-6cf40b8549d2','9076925d-2ac8-4b6c-86f8-0d9225c97513',NULL,NULL,NULL,'2025-11-16 07:13:40',NULL,'2025-11-16 15:13:41'),(83,15,'918925aa-7b55-423c-9301-7b16204a8c93','d0d4234e-65a7-4b42-a543-ec312ffb94d3',NULL,NULL,NULL,'2025-11-16 07:17:34',NULL,'2025-11-16 15:17:35'),(84,15,'d371ed53-8252-4088-9a86-25a84d3a7440','7673e434-88ec-4a82-951d-524b8c428b54',NULL,NULL,NULL,'2025-11-16 08:12:56',NULL,'2025-11-16 16:12:57'),(85,15,'0661057d-a6b0-4670-9def-8f596fca91b1','df0cd68b-944e-4513-acd1-81bfd6680b9b',NULL,NULL,NULL,'2025-11-16 08:15:45',NULL,'2025-11-16 16:15:46'),(86,15,'ad20e8d2-20e2-40b4-b0f4-ab70a860bf7e','61b4b7b9-0a2c-4e58-9b93-6572c54b4dc2',NULL,NULL,NULL,'2025-11-16 08:22:04',NULL,'2025-11-16 16:22:05'),(87,15,'4c999f58-efbd-495f-a762-de5676834aea','ccbced87-59fc-4950-85f7-4589b8959cf5',NULL,NULL,NULL,'2025-11-16 08:25:41',NULL,'2025-11-16 16:25:42'),(88,15,'1ab07543-86d2-47b9-b308-caecbd774877','38d2e8df-8bd6-449d-b2a6-11fb0b7bd8d3',NULL,NULL,NULL,'2025-11-16 08:39:32',NULL,'2025-11-16 16:39:32'),(89,15,'ad1fbd08-9944-45a3-b097-d58c007f5f49','b9684d83-5d2b-4f3e-a259-1afdbce5365d',NULL,NULL,NULL,'2025-11-16 09:02:20',NULL,'2025-11-16 17:02:21'),(90,15,'ae81d37b-11f8-40fc-9df6-3b34cecf1532','d08c73fd-01b0-437a-b983-8e91e22c1b3e',NULL,NULL,NULL,'2025-11-16 09:03:14',NULL,'2025-11-16 17:03:14'),(91,15,'362040aa-3131-43f5-b878-97931987ac9a','cc7164b2-a596-4067-a057-b506faae0f6d',NULL,NULL,NULL,'2025-11-16 09:04:06',NULL,'2025-11-16 17:04:07'),(92,15,'d6a3e048-6134-4989-9f70-57ce9ec29154','f0cb15ef-64b7-4fe3-9610-f2b49e301bf6',NULL,NULL,NULL,'2025-11-16 09:20:40',NULL,'2025-11-16 17:20:41'),(93,15,'cf8a0146-5f26-4211-ae6a-c3db11c21705','99b14ffc-8505-4ac1-82d3-29baa5855dbe',NULL,NULL,NULL,'2025-11-16 09:22:27',NULL,'2025-11-16 17:22:28'),(94,15,'c7318d10-b123-4cc4-b1b7-7a23062842fc','63099af5-3f38-4fc5-bc85-fe7806b3cff7',NULL,NULL,NULL,'2025-11-16 09:23:59',NULL,'2025-11-16 17:24:00'),(95,15,'9cfed162-19db-4ba5-9254-c55b28ecbc73','6300edc8-a22c-4091-b848-6b2bed28a5f8',NULL,NULL,NULL,'2025-11-16 09:31:57',NULL,'2025-11-16 17:31:58'),(96,15,'142b3a26-1e28-4b89-8ab2-56d943a2a1dc','7ece9743-bc38-4589-81f2-8b2e52427429',NULL,NULL,NULL,'2025-11-16 09:32:44',NULL,'2025-11-16 17:32:44'),(97,15,'1f3cf862-c34e-4aaa-9363-81d01d3eef7e','1b09b815-4eb0-4ab6-9831-887bac9d7d9f',NULL,NULL,NULL,'2025-11-16 09:37:36',NULL,'2025-11-16 17:37:36'),(98,15,'f5b438a8-90ec-47eb-a8f6-4bdfe5170a29','99a63c0a-6024-46c3-8f02-5ff05ca99dc7',NULL,NULL,NULL,'2025-11-16 09:41:12',NULL,'2025-11-16 17:41:13'),(99,15,'a0d10cd8-76aa-4d20-ad4e-6711e93e3c53','5a4f65eb-069a-4e03-b0ab-19d5f96bea2b',NULL,NULL,NULL,'2025-11-16 09:43:02',NULL,'2025-11-16 17:43:03'),(100,15,'9e53f859-8c03-4719-bb88-dad385fe5075','5959b855-4510-4dcb-82a8-fab2c361c5b2',NULL,NULL,NULL,'2025-11-16 09:56:56',NULL,'2025-11-16 17:56:57'),(101,15,'83f7699b-7705-476c-ab5c-6c8dffc11f79','36d4f27c-0568-497a-a8a2-cc16d4000543',NULL,NULL,NULL,'2025-11-16 09:57:29',NULL,'2025-11-16 17:57:30'),(102,15,'45f15521-9770-41bf-b54c-3988722bd1fe','63754c35-c12e-42e1-804e-d250ea8a93f1',NULL,NULL,NULL,'2025-11-16 10:06:29',NULL,'2025-11-16 18:06:29'),(103,15,'5f053f84-6237-474e-9fdc-37a4dc52dda3','d4d35b87-2870-4ef1-b3ac-7dde2213a53b',NULL,NULL,NULL,'2025-11-16 10:07:50',NULL,'2025-11-16 18:07:51'),(104,15,'2b102f98-9035-4305-be20-b2e153d6b7c2','e66006dc-57a5-4dbc-923f-3ba979dddac0',NULL,NULL,NULL,'2025-11-16 10:08:10',NULL,'2025-11-16 18:08:10'),(105,15,'38f65267-af9d-44a1-a707-728ee378ad49','7588572a-43e0-408e-9f03-6d5a90242f96',NULL,NULL,NULL,'2025-11-16 10:08:36',NULL,'2025-11-16 18:08:37'),(106,15,'0ad51682-7359-4182-9951-786f34cb6aa6','aaca7d71-b265-4000-8634-1665a5ebf0b5',NULL,NULL,NULL,'2025-11-16 10:16:32',NULL,'2025-11-16 18:16:32'),(107,15,'ad2a1cd1-244e-4e2a-9228-74c51f6436a2','6f7bab2e-7c43-47d0-b064-fd6dae99b56c',NULL,NULL,NULL,'2025-11-16 10:19:57',NULL,'2025-11-16 18:19:57'),(108,15,'47b81de7-3220-4ebd-8e15-49f5c6ad7d78','b0006411-24d1-47a5-88c8-8edee8264729',NULL,NULL,NULL,'2025-11-16 10:20:13',NULL,'2025-11-16 18:20:14'),(109,15,'68950324-1da0-4e51-a624-3fed2e2567cb','b92f91cd-f12c-4f88-a31a-af4fa4d56fd9',NULL,NULL,NULL,'2025-11-16 10:20:32',NULL,'2025-11-16 18:20:32'),(110,15,'3b15a1b9-ff57-4697-a96a-9bf6edaf290e','2644aea6-ac8f-462f-b8d4-23b8dfde02e7',NULL,NULL,NULL,'2025-11-16 10:29:00',NULL,'2025-11-16 18:29:00'),(111,15,'ecd1b2ae-5ccd-4383-9e44-81fbcbcf37a4','6a9774b9-c942-4ae9-803a-da49bf1958fc',NULL,NULL,NULL,'2025-11-16 10:31:00',NULL,'2025-11-16 18:31:01'),(112,15,'dcab4ecd-302e-498e-af8e-be0c6c1d86b4','09980601-0bf2-4fa0-b4c4-6ceb436e62b6',NULL,NULL,NULL,'2025-11-16 10:32:46',NULL,'2025-11-16 18:32:47'),(113,15,'3faa8d16-756b-426c-aef2-d379fde7501d','a1792f20-1eee-4ef8-a87c-419e8adaefe7',NULL,NULL,NULL,'2025-11-16 10:35:04',NULL,'2025-11-16 18:35:05'),(114,15,'9c615d99-9531-45b9-b21c-f38c7c24de38','cc8a9c6c-43c6-407c-98f3-d969b98d668d',NULL,NULL,NULL,'2025-11-16 10:38:28',NULL,'2025-11-16 18:38:28'),(115,15,'612806fe-a632-479f-8d5f-080f87547c46','eef5fe1d-0036-412c-918a-57d9dd1b9cf4',NULL,NULL,NULL,'2025-11-16 10:40:34',NULL,'2025-11-16 18:40:34'),(116,15,'2b7da113-ff0d-43f4-92a3-8fdca86ca5bf','25d203b7-6a64-4a08-abd0-31dcc5cc02b9',NULL,NULL,NULL,'2025-11-16 11:01:15',NULL,'2025-11-16 19:01:15'),(117,15,'56d97d63-6ee0-4b2c-ba50-15bb51c9c30b','3de664f6-60e1-42e0-ae71-b7a54f17f116',NULL,NULL,NULL,'2025-11-16 11:07:10',NULL,'2025-11-16 19:07:10'),(118,15,'e2bacf63-bfac-42c2-8124-4f5751aa1e35','50ea0a41-7911-4b32-a9f0-333034a1636c',NULL,NULL,NULL,'2025-11-16 11:07:48',NULL,'2025-11-16 19:07:48'),(119,15,'307dbb8b-71ba-4c0f-8946-fe8b36aa8e53','7867c9a2-3574-460d-a472-cad03e4ad980',NULL,NULL,NULL,'2025-11-16 11:14:32',NULL,'2025-11-16 19:14:33'),(120,15,'2ba29739-e222-4cef-8bfa-1f946865a83f','eddf9f66-5938-40b0-ae07-ab7fb9b3869e',NULL,NULL,NULL,'2025-11-16 11:16:25',NULL,'2025-11-16 19:16:25'),(121,15,'f796b46a-f6f7-4c7c-a495-3d4fe5ef7a6d','8b1f53d9-8209-4e29-90f1-4e7fd61caa5e',NULL,NULL,NULL,'2025-11-16 11:17:29',NULL,'2025-11-16 19:17:29'),(122,15,'801b2f58-5be5-4ed8-a6d9-cb21cdd17c88','3f0f11d0-13a4-4f58-b8b7-3ff4b1863c6f',NULL,NULL,NULL,'2025-11-16 11:20:04',NULL,'2025-11-16 19:20:04'),(123,24,'a861f004-1797-4c0d-8b38-85302523ca42','cbcc118c-f3ae-4354-a813-7756a1ec2167',NULL,NULL,NULL,'2025-11-16 11:23:21',NULL,'2025-11-16 19:23:22');
/*!40000 ALTER TABLE `userssessions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-16 13:24:50
