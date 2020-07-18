CREATE DATABASE  IF NOT EXISTS `FinalProject` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `FinalProject`;
-- MySQL dump 10.13  Distrib 8.0.20, for macos10.15 (x86_64)
--
-- Host: localhost    Database: FinalProject
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `idaccount` varchar(45) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `role` int NOT NULL,
  PRIMARY KEY (`idaccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `idcategory` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idcategory`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dog_breed`
--

DROP TABLE IF EXISTS `dog_breed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dog_breed` (
  `iddog_breed` int NOT NULL AUTO_INCREMENT,
  `photo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` varchar(45) DEFAULT NULL,
  `puppy` varchar(45) DEFAULT NULL,
  `life_span` varchar(45) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `adaptability` float DEFAULT NULL,
  `apartment_friendly` float DEFAULT NULL,
  `barking_tendency` float DEFAULT NULL,
  `cat_friendly` float DEFAULT NULL,
  `child_friendly` float DEFAULT NULL,
  `dog_friendly` float DEFAULT NULL,
  `exercise_need` float DEFAULT NULL,
  `grooming` float DEFAULT NULL,
  `health_issuse` float DEFAULT NULL,
  `intelligence` float DEFAULT NULL,
  `playfulness` float DEFAULT NULL,
  `shedding_level` float DEFAULT NULL,
  `stranger_friendly` float DEFAULT NULL,
  `trainability` float DEFAULT NULL,
  `watchdog_ability` float DEFAULT NULL,
  PRIMARY KEY (`iddog_breed`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2877 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dog_supplies`
--

DROP TABLE IF EXISTS `dog_supplies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dog_supplies` (
  `iddog_supplies` int NOT NULL AUTO_INCREMENT,
  `photo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `size` varchar(100) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `category` int NOT NULL,
  PRIMARY KEY (`iddog_supplies`,`category`),
  KEY `fk_dog_supplies_category1_idx` (`category`),
  CONSTRAINT `fk_dog_supplies_category1` FOREIGN KEY (`category`) REFERENCES `category` (`idcategory`)
) ENGINE=InnoDB AUTO_INCREMENT=1402 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `temperament`
--

DROP TABLE IF EXISTS `temperament`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `temperament` (
  `idtemperament` int NOT NULL AUTO_INCREMENT,
  `content` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtemperament`),
  UNIQUE KEY `content_UNIQUE` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `temperament_dog`
--

DROP TABLE IF EXISTS `temperament_dog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `temperament_dog` (
  `idtemperament` int NOT NULL,
  `iddog_breed` int NOT NULL,
  PRIMARY KEY (`idtemperament`,`iddog_breed`),
  KEY `fk_temperament_has_dog_breed_dog_breed1_idx` (`iddog_breed`),
  KEY `fk_temperament_has_dog_breed_temperament_idx` (`idtemperament`),
  CONSTRAINT `fk_temperament_has_dog_breed_dog_breed1` FOREIGN KEY (`iddog_breed`) REFERENCES `dog_breed` (`iddog_breed`),
  CONSTRAINT `fk_temperament_has_dog_breed_temperament` FOREIGN KEY (`idtemperament`) REFERENCES `temperament` (`idtemperament`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'FinalProject'
--

--
-- Dumping routines for database 'FinalProject'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-18 18:11:47
