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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_history`
--

LOCK TABLES `chat_history` WRITE;
/*!40000 ALTER TABLE `chat_history` DISABLE KEYS */;
INSERT INTO `chat_history` VALUES (1,'Test raspuns','Test intrebare','2025-11-14 15:59:39.387642'),(2,'Test raspuns','Test intrebare','2025-11-14 15:59:48.483937'),(3,'Test raspuns','Test intrebare','2025-11-14 15:59:50.748773'),(4,'Test raspuns','Test intrebare','2025-11-14 15:59:52.580869'),(5,'Test raspuns','Test intrebare','2025-11-14 15:59:53.775054'),(6,'non_empty','Imagine Analizată','2025-11-14 16:26:23.199968'),(7,'Test raspuns','Test intrebare','2025-11-14 18:58:20.772209'),(8,'non_empty','Imagine Analizată','2025-11-14 18:59:32.853837'),(9,'non_empty','Imagine Analizată','2025-11-14 19:07:19.045233'),(10,'Test raspuns','Test intrebare','2025-11-14 19:32:35.298801'),(11,'Test raspuns','Test intrebare','2025-11-14 19:47:43.283090'),(12,'Test raspuns','Test intrebare','2025-11-14 19:47:44.516454'),(13,'Test raspuns','Test intrebare','2025-11-14 19:47:44.982181'),(14,'Test raspuns','Test intrebare','2025-11-14 19:47:45.234952'),(15,'Test raspuns','Test intrebare','2025-11-14 19:48:19.433501'),(16,'Test raspuns','Test intrebare','2025-11-14 19:51:19.391911'),(17,'Test raspuns','Test intrebare','2025-11-14 19:53:32.241626'),(18,'Test raspuns','Test intrebare','2025-11-14 19:54:07.209723'),(19,'Test raspuns','Test intrebare','2025-11-14 19:55:39.185151'),(20,'Test raspuns','Test intrebare','2025-11-14 19:58:18.859632'),(21,'Test raspuns','Test intrebare','2025-11-14 20:05:41.535697'),(22,'Test raspuns','Test intrebare','2025-11-14 20:07:30.275363'),(23,'Test raspuns','Test intrebare','2025-11-14 20:08:46.053459'),(24,'Test raspuns','Test intrebare','2025-11-14 20:29:30.939075'),(25,'Test raspuns','Test intrebare','2025-11-14 20:40:44.972287'),(26,'Test raspuns','Test intrebare','2025-11-14 21:10:10.622958'),(27,'Test raspuns','Test intrebare','2025-11-14 21:17:02.360710'),(28,'Test raspuns','Test intrebare','2025-11-14 21:21:16.777645'),(29,'Test raspuns','Test intrebare','2025-11-14 21:26:23.775051'),(30,'Test raspuns','Test intrebare','2025-11-14 21:30:27.205835'),(31,'Test raspuns','Test intrebare','2025-11-14 21:49:13.099624'),(32,'Test raspuns','Test intrebare','2025-11-14 21:50:40.796717'),(33,'Test raspuns','Test intrebare','2025-11-14 21:50:59.422379'),(34,'Test raspuns','Test intrebare','2025-11-14 21:51:19.313794'),(35,'Test raspuns','Test intrebare','2025-11-14 21:51:42.994497'),(36,'Test raspuns','Test intrebare','2025-11-15 11:04:13.871910'),(37,'Test raspuns','Test intrebare','2025-11-15 11:04:50.587541'),(38,'Test raspuns','Test intrebare','2025-11-15 11:05:02.988976'),(39,'Test raspuns','Test intrebare','2025-11-15 11:06:38.596845'),(40,'Test raspuns','Test intrebare','2025-11-15 11:10:26.386000'),(41,'Test raspuns','Test intrebare','2025-11-15 11:26:58.345888'),(42,'Test raspuns','Test intrebare','2025-11-15 11:37:18.182573'),(43,'Test raspuns','Test intrebare','2025-11-15 11:52:07.258582'),(44,'Test raspuns','Test intrebare','2025-11-15 12:01:53.061066'),(45,'Test raspuns','Test intrebare','2025-11-15 12:02:24.035351'),(46,'Test raspuns','Test intrebare','2025-11-15 12:19:27.774478'),(47,'Test raspuns','Test intrebare','2025-11-15 12:23:16.004531');
/*!40000 ALTER TABLE `chat_history` ENABLE KEYS */;
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
  `userRole` enum('participant','creator') COLLATE utf8mb3_roman_ci DEFAULT 'participant',
  `feedback` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
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
  `creatorType` enum('standardUser','organisation') COLLATE utf8mb3_roman_ci DEFAULT 'organisation',
  `creatorStandardID` bigint DEFAULT NULL,
  `creatorOrganisationID` bigint DEFAULT NULL,
  `creationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `visibility` enum('public','volunteers') COLLATE utf8mb3_roman_ci DEFAULT 'public',
  `status` enum('upcoming','ongoing','completed','cancelled') COLLATE utf8mb3_roman_ci DEFAULT 'upcoming',
  PRIMARY KEY (`eventID`),
  KEY `creatorStandardID` (`creatorStandardID`),
  KEY `creatorOrganisationID` (`creatorOrganisationID`),
  CONSTRAINT `events_ibfk_1` FOREIGN KEY (`creatorStandardID`) REFERENCES `standardusers` (`userID`),
  CONSTRAINT `events_ibfk_2` FOREIGN KEY (`creatorOrganisationID`) REFERENCES `organizations` (`userID`),
  CONSTRAINT `events_chk_1` CHECK ((((`creatorType` = _utf8mb4'organisation') and (`creatorOrganisationID` is not null) and (`creatorStandardID` is null)) or ((`creatorType` = _utf8mb4'standardUser') and (`creatorStandardID` is not null) and (`creatorOrganisationID` is null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
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
  `phone` varchar(20) COLLATE utf8mb3_roman_ci NOT NULL,
  `contactEmail` varchar(255) COLLATE utf8mb3_roman_ci NOT NULL,
  `description` varchar(500) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `website` varchar(100) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `adress` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `logo` varchar(500) COLLATE utf8mb3_roman_ci DEFAULT NULL,
  `totalEventsCreated` int DEFAULT '0',
  `canCreateEvents` tinyint(1) DEFAULT '1',
  `verified` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `contactEmail` (`contactEmail`),
  CONSTRAINT `organizations_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE,
  CONSTRAINT `ck_totalEventsCreatedValue` CHECK ((`totalEventsCreated` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
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
  `standardUserRank` enum('Seed','Sprout','Sapling','Tree') COLLATE utf8mb3_roman_ci DEFAULT 'Seed',
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_roman_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userssessions`
--

LOCK TABLES `userssessions` WRITE;
/*!40000 ALTER TABLE `userssessions` DISABLE KEYS */;
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

-- Dump completed on 2025-11-15 12:39:38
