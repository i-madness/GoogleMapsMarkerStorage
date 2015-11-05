# Host: localhost  (Version: 5.6.24)
# Date: 2015-11-05 16:50:49
# Generator: MySQL-Front 5.3  (Build 4.205)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "marker"
#

CREATE TABLE `marker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

#
# Data for table "marker"
#

INSERT INTO `marker` VALUES (81,'Турция',37.070374,39.550781),(82,'Швеция',59.03196,13.625511),(83,'Мой дом',81.089358,-16.523438),(84,'Дом моей бабушки',65.502639,-16.171875),(85,'Лондон',51.507351,-0.127758),(86,'Курорт',62.754726,-91.40625),(87,'Тёплые пески',22.796439,16.787109);
