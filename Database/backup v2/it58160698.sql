-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 24, 2017 at 08:10 PM
-- Server version: 5.5.58-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `it58160698`
--

-- --------------------------------------------------------

--
-- Table structure for table `Comments`
--

CREATE TABLE IF NOT EXISTS `Comments` (
  `Com_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  `Res_id` int(11) NOT NULL,
  `Com_text` varchar(200) NOT NULL,
  `Com_score` int(11) NOT NULL,
  PRIMARY KEY (`Com_id`),
  KEY `fk_Comments_Users1_idx` (`User_id`),
  KEY `fk_Comments_Resturant1_idx` (`Res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `FavoritResturants`
--

CREATE TABLE IF NOT EXISTS `FavoritResturants` (
  `Res_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  PRIMARY KEY (`Res_id`,`User_id`),
  KEY `fk_Resturant_has_Users_Users1_idx` (`User_id`),
  KEY `fk_Resturant_has_Users_Resturant1_idx` (`Res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `imguploadtest`
--

CREATE TABLE IF NOT EXISTS `imguploadtest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_path` varchar(256) NOT NULL,
  `image_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=38 ;

--
-- Dumping data for table `imguploadtest`
--

INSERT INTO `imguploadtest` (`id`, `image_path`, `image_name`) VALUES
(21, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/0.png', 'g'),
(22, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/21.png', 'g'),
(23, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/22.png', 'g'),
(24, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/23.png', 'g'),
(25, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/24.png', 'g'),
(26, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/25.png', 'g'),
(27, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/26.png', 'g'),
(28, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/27.png', 'g'),
(29, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/28.png', 'g'),
(30, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/29.png', 'g'),
(31, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/30.png', 'g'),
(32, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/31.png', 'g'),
(33, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/32.png', 'g'),
(34, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/33.png', 'g'),
(35, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/34.png', 'g'),
(36, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/35.png', 'g'),
(37, 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/36.png', 'g');

-- --------------------------------------------------------

--
-- Table structure for table `Resturant`
--

CREATE TABLE IF NOT EXISTS `Resturant` (
  `Res_id` int(11) NOT NULL AUTO_INCREMENT,
  `Res_name` varchar(100) NOT NULL,
  `Res_detail` varchar(200) NOT NULL,
  `Res_img_name` varchar(30) NOT NULL,
  `Res_img_path` varchar(256) NOT NULL,
  `Res_latitude` double NOT NULL,
  `Res_longitude` double NOT NULL,
  `Type_id` int(11) NOT NULL,
  PRIMARY KEY (`Res_id`),
  KEY `fk_Resturant_Types1_idx` (`Type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `Resturant`
--

INSERT INTO `Resturant` (`Res_id`, `Res_name`, `Res_detail`, `Res_img_name`, `Res_img_path`, `Res_latitude`, `Res_longitude`, `Type_id`) VALUES
(23, 'ndddjdj', 'tttt', 'no', 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/1.png', 13.290215093333, 100.93001749367, 2),
(24, 'แดงๆ', 'บะหมี่อร่อย', 'no', 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/24.png', 13.27334463552, 100.93180350959, 1),
(25, 'หิวโว้ยยย', 'หิวไงไอสัส', 'no', 'https://angsila.cs.buu.ac.th/~58160698/uploadimg/imageAndroid/25.png', 13.278269725652, 100.92901602387, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Types`
--

CREATE TABLE IF NOT EXISTS `Types` (
  `Type_id` int(11) NOT NULL AUTO_INCREMENT,
  `Type_name` varchar(25) NOT NULL,
  PRIMARY KEY (`Type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `Types`
--

INSERT INTO `Types` (`Type_id`, `Type_name`) VALUES
(1, 'อาหารตามสั่ง'),
(2, 'ก๊วยเตี๋ยว');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `User_id` int(11) NOT NULL AUTO_INCREMENT,
  `User_username` varchar(10) NOT NULL,
  `User_password` varchar(40) NOT NULL,
  `User_fullname` varchar(55) NOT NULL,
  PRIMARY KEY (`User_id`),
  UNIQUE KEY `User_username` (`User_username`),
  UNIQUE KEY `User_id` (`User_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=41 ;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`User_id`, `User_username`, `User_password`, `User_fullname`) VALUES
(31, 'g', '54fd1711209fb1c0781092374132c66e79e2241b', 'g g'),
(32, 'ggg', '07dcd371560bc43c48f56a2f55739ac66741d59d', 'ggg ggg'),
(33, 'nn', '07962e32beac4da179b30c06f1c1e71bd220f782', 'nn nn'),
(34, 'gun27311', '962c1673a6add3377f98e0de12c59578173c04b6', 'gun gun'),
(35, 'dada', 'fedd1d1122aa65028c81e16ceb85d9c73790a2fa', 'kuyda da'),
(36, 'xx', 'dd7b7b74ea160e049dd128478e074ce47254bde8', 'xx xx'),
(37, 'wathiwut', 'c45eceaec3ccf8a45404edb89ff924eae19a26e4', 'wathiwut kongjan'),
(38, 'l', '07c342be6e560e7f43842e2e21b774e61d85f047', 'l l'),
(39, 'kuy', 'aa9cbbc21e656e97381f99c6c955f71920f595f1', ' ร ร'),
(40, 'hello', '57b2ad99044d337197c0c39fd3823568ff81e48a', 'hello lol');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Comments`
--
ALTER TABLE `Comments`
  ADD CONSTRAINT `fk_Comments_Users1` FOREIGN KEY (`User_id`) REFERENCES `Users` (`User_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Comments_Resturant1` FOREIGN KEY (`Res_id`) REFERENCES `Resturant` (`Res_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `FavoritResturants`
--
ALTER TABLE `FavoritResturants`
  ADD CONSTRAINT `fk_Resturant_has_Users_Resturant1` FOREIGN KEY (`Res_id`) REFERENCES `Resturant` (`Res_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Resturant_has_Users_Users1` FOREIGN KEY (`User_id`) REFERENCES `Users` (`User_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Resturant`
--
ALTER TABLE `Resturant`
  ADD CONSTRAINT `fk_Resturant_Types1` FOREIGN KEY (`Type_id`) REFERENCES `Types` (`Type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
