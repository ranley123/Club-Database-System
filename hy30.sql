-- MariaDB dump 10.19  Distrib 10.5.9-MariaDB, for osx10.15 (x86_64)
--
-- Host: localhost    Database: hy30_db
-- ------------------------------------------------------
-- Server version	10.5.9-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Court`
--

DROP TABLE IF EXISTS `Court`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Court` (
  `number` int(11) NOT NULL,
  `venue_name` varchar(50) NOT NULL,
  PRIMARY KEY (`number`,`venue_name`),
  KEY `venue_name` (`venue_name`),
  CONSTRAINT `court_ibfk_1` FOREIGN KEY (`venue_name`) REFERENCES `Venue` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Court`
--

LOCK TABLES `Court` WRITE;
/*!40000 ALTER TABLE `Court` DISABLE KEYS */;
INSERT INTO `Court` VALUES (1,'East Sands Leisure Centre'),(1,'Forthill Squash Club'),(1,'University Sports Centre'),(1,'Waterstone Crook Sports Centre'),(2,'East Sands Leisure Centre'),(2,'Forthill Squash Club'),(2,'University Sports Centre'),(2,'Waterstone Crook Sports Centre'),(3,'University Sports Centre'),(3,'Waterstone Crook Sports Centre'),(4,'Waterstone Crook Sports Centre');
/*!40000 ALTER TABLE `Court` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `League`
--

DROP TABLE IF EXISTS `League`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `League` (
  `name` varchar(50) NOT NULL,
  `year` int(11) NOT NULL,
  `prize_money` float DEFAULT NULL,
  `winner_email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`name`,`year`),
  KEY `winner_email` (`winner_email`),
  CONSTRAINT `league_ibfk_1` FOREIGN KEY (`winner_email`) REFERENCES `Player` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `League`
--

LOCK TABLES `League` WRITE;
/*!40000 ALTER TABLE `League` DISABLE KEYS */;
INSERT INTO `League` VALUES ('Alexander McLintoch trophy',2018,250,'louis.payne@gmail.com'),('Alexander McLintoch trophy',2019,100,'tabitha.stacey@gmail.com'),('Alexander McLintoch trophy',2020,300,'sylvia.hathaway@gmail.com'),('Oldies cup',2018,50,'srrogers@yahoo.co.uk'),('Oldies cup',2019,50,'srrogers@yahoo.co.uk'),('Oldies cup',2020,0,'u_marsden@gmail.com');
/*!40000 ALTER TABLE `League` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `League_Player`
--

DROP TABLE IF EXISTS `League_Player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `League_Player` (
  `email` varchar(50) NOT NULL,
  `league_name` varchar(50) NOT NULL,
  `league_year` int(11) NOT NULL,
  PRIMARY KEY (`email`,`league_name`,`league_year`),
  KEY `league_name` (`league_name`,`league_year`),
  CONSTRAINT `league_player_ibfk_1` FOREIGN KEY (`email`) REFERENCES `Player` (`email`),
  CONSTRAINT `league_player_ibfk_2` FOREIGN KEY (`league_name`, `league_year`) REFERENCES `League` (`name`, `year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `League_Player`
--

LOCK TABLES `League_Player` WRITE;
/*!40000 ALTER TABLE `League_Player` DISABLE KEYS */;
INSERT INTO `League_Player` VALUES ('butch@xyz.club','Alexander McLintoch trophy',2019),('butch@xyz.club','Alexander McLintoch trophy',2020),('final_fantasy_freak1993@hotmail.com','Alexander McLintoch trophy',2018),('final_fantasy_freak1993@hotmail.com','Alexander McLintoch trophy',2019),('gary_the_man@yahoo.co.uk','Alexander McLintoch trophy',2019),('gary_the_man@yahoo.co.uk','Alexander McLintoch trophy',2020),('gary_the_man@yahoo.co.uk','Oldies cup',2018),('gary_the_man@yahoo.co.uk','Oldies cup',2019),('gary_the_man@yahoo.co.uk','Oldies cup',2020),('jwh@hotmail.com','Alexander McLintoch trophy',2018),('jwh@hotmail.com','Alexander McLintoch trophy',2019),('louis.payne@gmail.com','Alexander McLintoch trophy',2018),('srrogers@yahoo.co.uk','Oldies cup',2018),('srrogers@yahoo.co.uk','Oldies cup',2019),('sylvia.hathaway@gmail.com','Alexander McLintoch trophy',2018),('sylvia.hathaway@gmail.com','Alexander McLintoch trophy',2019),('sylvia.hathaway@gmail.com','Alexander McLintoch trophy',2020),('tabitha.stacey@gmail.com','Alexander McLintoch trophy',2018),('tabitha.stacey@gmail.com','Alexander McLintoch trophy',2019),('tabitha.stacey@gmail.com','Alexander McLintoch trophy',2020),('tasha.marsden@gmail.com','Oldies cup',2018),('tasha.marsden@gmail.com','Oldies cup',2019),('tasha.marsden@gmail.com','Oldies cup',2020),('u_marsden@gmail.com','Oldies cup',2018),('u_marsden@gmail.com','Oldies cup',2019),('u_marsden@gmail.com','Oldies cup',2020);
/*!40000 ALTER TABLE `League_Player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Played_Match`
--

DROP TABLE IF EXISTS `Played_Match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Played_Match` (
  `p1_email` varchar(50) DEFAULT NULL,
  `p2_email` varchar(50) DEFAULT NULL,
  `p1_games_won` int(11) DEFAULT NULL,
  `p2_games_won` int(11) DEFAULT NULL,
  `date_played` date DEFAULT NULL,
  `court_number` int(11) DEFAULT NULL,
  `venue_name` varchar(50) DEFAULT NULL,
  `league_name` varchar(50) DEFAULT NULL,
  `league_year` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `p2_email` (`p2_email`),
  KEY `venue_name` (`venue_name`),
  KEY `court_number` (`court_number`,`venue_name`),
  KEY `league_name` (`league_name`,`league_year`),
  KEY `p1_email` (`p1_email`,`league_name`,`league_year`),
  CONSTRAINT `played_match_ibfk_1` FOREIGN KEY (`p1_email`) REFERENCES `Player` (`email`),
  CONSTRAINT `played_match_ibfk_2` FOREIGN KEY (`p2_email`) REFERENCES `Player` (`email`),
  CONSTRAINT `played_match_ibfk_3` FOREIGN KEY (`venue_name`) REFERENCES `Venue` (`name`),
  CONSTRAINT `played_match_ibfk_4` FOREIGN KEY (`p1_email`) REFERENCES `Player` (`email`),
  CONSTRAINT `played_match_ibfk_5` FOREIGN KEY (`court_number`, `venue_name`) REFERENCES `Court` (`number`, `venue_name`),
  CONSTRAINT `played_match_ibfk_6` FOREIGN KEY (`league_name`, `league_year`) REFERENCES `League` (`name`, `year`),
  CONSTRAINT `played_match_ibfk_7` FOREIGN KEY (`p1_email`, `league_name`, `league_year`) REFERENCES `League_Player` (`email`, `league_name`, `league_year`),
  CONSTRAINT `valid_games` CHECK (`p1_games_won` = 3 and `p2_games_won` >= 0 and `p2_games_won` < 3 or `p2_games_won` = 3 and `p1_games_won` >= 0 and `p1_games_won` < 3),
  CONSTRAINT `valid_year` CHECK (year(`date_played`) = `league_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Played_Match`
--

LOCK TABLES `Played_Match` WRITE;
/*!40000 ALTER TABLE `Played_Match` DISABLE KEYS */;
INSERT INTO `Played_Match` VALUES ('jwh@hotmail.com','tabitha.stacey@gmail.com',2,3,'2018-04-05',1,'University Sports Centre','Alexander McLintoch trophy',2018,1),('louis.payne@gmail.com','final_fantasy_freak1993@hotmail.com',3,0,'2018-04-11',3,'University Sports Centre','Alexander McLintoch trophy',2018,2),('jwh@hotmail.com','sylvia.hathaway@gmail.com',1,3,'2018-04-17',1,'University Sports Centre','Alexander McLintoch trophy',2018,3),('louis.payne@gmail.com','jwh@hotmail.com',3,0,'2018-05-07',3,'University Sports Centre','Alexander McLintoch trophy',2018,4),('tabitha.stacey@gmail.com','final_fantasy_freak1993@hotmail.com',1,3,'2018-05-23',2,'Forthill Squash Club','Alexander McLintoch trophy',2018,5),('sylvia.hathaway@gmail.com','tabitha.stacey@gmail.com',3,2,'2018-05-25',2,'Waterstone Crook Sports Centre','Alexander McLintoch trophy',2018,6),('louis.payne@gmail.com','tabitha.stacey@gmail.com',3,1,'2018-05-28',1,'University Sports Centre','Alexander McLintoch trophy',2018,7),('final_fantasy_freak1993@hotmail.com','sylvia.hathaway@gmail.com',2,3,'2018-06-13',1,'University Sports Centre','Alexander McLintoch trophy',2018,8),('jwh@hotmail.com','final_fantasy_freak1993@hotmail.com',3,0,'2018-06-21',1,'University Sports Centre','Alexander McLintoch trophy',2018,9),('sylvia.hathaway@gmail.com','louis.payne@gmail.com',2,3,'2018-07-09',3,'University Sports Centre','Alexander McLintoch trophy',2018,10),('tabitha.stacey@gmail.com','butch@xyz.club',3,0,'2019-04-11',1,'University Sports Centre','Alexander McLintoch trophy',2019,11),('butch@xyz.club','final_fantasy_freak1993@hotmail.com',0,3,'2019-04-12',1,'University Sports Centre','Alexander McLintoch trophy',2019,12),('sylvia.hathaway@gmail.com','butch@xyz.club',0,3,'2019-04-16',3,'University Sports Centre','Alexander McLintoch trophy',2019,13),('gary_the_man@yahoo.co.uk','tabitha.stacey@gmail.com',1,3,'2019-04-19',1,'University Sports Centre','Alexander McLintoch trophy',2019,14),('jwh@hotmail.com','sylvia.hathaway@gmail.com',3,2,'2019-04-24',2,'Waterstone Crook Sports Centre','Alexander McLintoch trophy',2019,15),('gary_the_man@yahoo.co.uk','final_fantasy_freak1993@hotmail.com',3,2,'2019-04-30',2,'Waterstone Crook Sports Centre','Alexander McLintoch trophy',2019,16),('sylvia.hathaway@gmail.com','final_fantasy_freak1993@hotmail.com',1,3,'2019-05-03',4,'Waterstone Crook Sports Centre','Alexander McLintoch trophy',2019,17),('tabitha.stacey@gmail.com','final_fantasy_freak1993@hotmail.com',3,0,'2019-05-13',3,'University Sports Centre','Alexander McLintoch trophy',2019,18),('jwh@hotmail.com','final_fantasy_freak1993@hotmail.com',1,3,'2019-05-21',3,'University Sports Centre','Alexander McLintoch trophy',2019,19),('gary_the_man@yahoo.co.uk','sylvia.hathaway@gmail.com',1,3,'2019-06-06',4,'Waterstone Crook Sports Centre','Alexander McLintoch trophy',2019,20),('tabitha.stacey@gmail.com','sylvia.hathaway@gmail.com',3,2,'2019-06-28',1,'University Sports Centre','Alexander McLintoch trophy',2019,21),('jwh@hotmail.com','tabitha.stacey@gmail.com',0,3,'2019-07-15',2,'Waterstone Crook Sports Centre','Alexander McLintoch trophy',2019,22),('jwh@hotmail.com','gary_the_man@yahoo.co.uk',0,3,'2019-07-17',1,'University Sports Centre','Alexander McLintoch trophy',2019,23),('jwh@hotmail.com','butch@xyz.club',2,3,'2019-07-22',1,'University Sports Centre','Alexander McLintoch trophy',2019,24),('gary_the_man@yahoo.co.uk','butch@xyz.club',3,1,'2019-07-26',1,'University Sports Centre','Alexander McLintoch trophy',2019,25),('tabitha.stacey@gmail.com','butch@xyz.club',3,1,'2020-05-05',1,'Waterstone Crook Sports Centre','Alexander McLintoch trophy',2020,26),('butch@xyz.club','sylvia.hathaway@gmail.com',1,3,'2020-05-15',2,'Waterstone Crook Sports Centre','Alexander McLintoch trophy',2020,27),('gary_the_man@yahoo.co.uk','sylvia.hathaway@gmail.com',1,3,'2020-05-26',1,'East Sands Leisure Centre','Alexander McLintoch trophy',2020,28),('tabitha.stacey@gmail.com','sylvia.hathaway@gmail.com',0,3,'2020-05-28',1,'East Sands Leisure Centre','Alexander McLintoch trophy',2020,29),('tabitha.stacey@gmail.com','gary_the_man@yahoo.co.uk',3,2,'2020-07-03',2,'East Sands Leisure Centre','Alexander McLintoch trophy',2020,30),('butch@xyz.club','gary_the_man@yahoo.co.uk',3,2,'2020-07-06',3,'University Sports Centre','Alexander McLintoch trophy',2020,31),('srrogers@yahoo.co.uk','tasha.marsden@gmail.com',3,0,'2018-09-30',1,'Waterstone Crook Sports Centre','Oldies cup',2018,32),('gary_the_man@yahoo.co.uk','u_marsden@gmail.com',3,2,'2018-10-28',2,'Forthill Squash Club','Oldies cup',2018,33),('srrogers@yahoo.co.uk','u_marsden@gmail.com',3,1,'2018-11-02',1,'Waterstone Crook Sports Centre','Oldies cup',2018,34),('gary_the_man@yahoo.co.uk','tasha.marsden@gmail.com',0,3,'2018-11-04',4,'Waterstone Crook Sports Centre','Oldies cup',2018,35),('tasha.marsden@gmail.com','u_marsden@gmail.com',1,3,'2018-11-06',2,'Forthill Squash Club','Oldies cup',2018,36),('gary_the_man@yahoo.co.uk','srrogers@yahoo.co.uk',1,3,'2018-11-09',2,'Waterstone Crook Sports Centre','Oldies cup',2018,37),('tasha.marsden@gmail.com','u_marsden@gmail.com',3,1,'2019-09-27',2,'Waterstone Crook Sports Centre','Oldies cup',2019,38),('srrogers@yahoo.co.uk','u_marsden@gmail.com',3,2,'2019-09-29',2,'Waterstone Crook Sports Centre','Oldies cup',2019,39),('gary_the_man@yahoo.co.uk','tasha.marsden@gmail.com',2,3,'2019-09-29',4,'Waterstone Crook Sports Centre','Oldies cup',2019,40),('srrogers@yahoo.co.uk','tasha.marsden@gmail.com',3,0,'2019-10-02',1,'Forthill Squash Club','Oldies cup',2019,41),('gary_the_man@yahoo.co.uk','srrogers@yahoo.co.uk',0,3,'2019-10-09',1,'Waterstone Crook Sports Centre','Oldies cup',2019,42),('gary_the_man@yahoo.co.uk','u_marsden@gmail.com',3,1,'2019-10-30',3,'University Sports Centre','Oldies cup',2019,43),('tasha.marsden@gmail.com','u_marsden@gmail.com',2,3,'2020-10-02',1,'Waterstone Crook Sports Centre','Oldies cup',2020,44),('gary_the_man@yahoo.co.uk','tasha.marsden@gmail.com',3,0,'2020-10-09',2,'Waterstone Crook Sports Centre','Oldies cup',2020,45),('gary_the_man@yahoo.co.uk','u_marsden@gmail.com',1,3,'2020-11-05',1,'Forthill Squash Club','Oldies cup',2020,46);
/*!40000 ALTER TABLE `Played_Match` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Player`
--

DROP TABLE IF EXISTS `Player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Player` (
  `forename` varchar(50) DEFAULT NULL,
  `middlenames` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Player`
--

LOCK TABLES `Player` WRITE;
/*!40000 ALTER TABLE `Player` DISABLE KEYS */;
INSERT INTO `Player` VALUES ('Jamie','Eugene Korey','Butcher','1985-09-21','butch@xyz.club'),('Kirsten','Aileen Louise','Jackman','1993-10-28','final_fantasy_freak1993@hotmail.com'),('Gary','Carl','Marsden','1985-10-12','gary_the_man@yahoo.co.uk'),('Jeremy','Wardell','Huddleston','1991-02-13','jwh@hotmail.com'),('Leighton','Alan','Buzzard','1980-05-17','leighton.buzzard@gmail.com'),('Louis','Kennard','Payne','2000-05-31','louis.payne@gmail.com'),('Madeleine','','Daubney','1991-03-08','mad_maddy@gmail.com'),('Sue','Rosemary','Rogers','1965-07-30','srrogers@yahoo.co.uk'),('Sylvia','Loraine','Hathaway','2004-01-02','sylvia.hathaway@gmail.com'),('Tabitha','','Stacey','2005-09-10','tabitha.stacey@gmail.com'),('Natasha','Joy Bernardette Louise','Marsden','1960-04-28','tasha.marsden@gmail.com'),('Ulysses','','Marsden','1977-05-07','u_marsden@gmail.com');
/*!40000 ALTER TABLE `Player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Player_Phone`
--

DROP TABLE IF EXISTS `Player_Phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Player_Phone` (
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `phone_type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`email`,`phone_number`),
  CONSTRAINT `player_phone_ibfk_1` FOREIGN KEY (`email`) REFERENCES `Player` (`email`),
  CONSTRAINT `valid_email` CHECK (`email`  not like '@%' and `email`  not like '%@' and `email` like '%@%'),
  CONSTRAINT `phone_types` CHECK (`phone_type` in ('work','home','mobile'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Player_Phone`
--

LOCK TABLES `Player_Phone` WRITE;
/*!40000 ALTER TABLE `Player_Phone` DISABLE KEYS */;
INSERT INTO `Player_Phone` VALUES ('butch@xyz.club','079 6943 8448','mobile'),('final_fantasy_freak1993@hotmail.com','07700 900909','mobile'),('gary_the_man@yahoo.co.uk','0151 496 0777','home'),('jwh@hotmail.com','0131 496 0470','home'),('leighton.buzzard@gmail.com','0117 496 0714','home'),('leighton.buzzard@gmail.com','0131 496 0962','work'),('louis.payne@gmail.com','07700 900654','mobile'),('mad_maddy@gmail.com','0115 496 0961','work'),('mad_maddy@gmail.com','020 7946 0501','home'),('srrogers@yahoo.co.uk','07700 900949','mobile'),('sylvia.hathaway@gmail.com','07700 900939','mobile'),('tabitha.stacey@gmail.com','07837 585417','mobile'),('tasha.marsden@gmail.com','078 8934 4229','mobile'),('u_marsden@gmail.com','0131 496 0745','home');
/*!40000 ALTER TABLE `Player_Phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Venue`
--

DROP TABLE IF EXISTS `Venue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Venue` (
  `name` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Venue`
--

LOCK TABLES `Venue` WRITE;
/*!40000 ALTER TABLE `Venue` DISABLE KEYS */;
INSERT INTO `Venue` VALUES ('East Sands Leisure Centre','2 St Mary St St Andrews KY16 8LH'),('Forthill Squash Club','20 Forthill Road Broughty Ferry Dundee DD5 3SR'),('University Sports Centre','9 St Leonard’s Rd St Andrews KY16 9DY'),('Waterstone Crook Sports Centre','69 Kirk Rd NewportonTay DD6 8HY');
/*!40000 ALTER TABLE `Venue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'hy30_db'
--
/*!50003 DROP PROCEDURE IF EXISTS `proc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`dengpeiyao`@`localhost` PROCEDURE `proc`(OUT param INT)
begin
select count(*) into param from player;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-20 18:33:36
