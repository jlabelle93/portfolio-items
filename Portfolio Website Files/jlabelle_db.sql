-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 11, 2021 at 07:53 PM
-- Server version: 5.7.34
-- PHP Version: 7.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jlabelle_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `ajaxDemo`
--

CREATE TABLE `ajaxDemo` (
  `Email` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ajaxDemo`
--

INSERT INTO `ajaxDemo` (`Email`) VALUES
('test3@test.com'),
('test@test2.com'),
('test@test.com'),
('test@test.com'),
('test@test.com'),
('test@test.net'),
('memedank@gmail.com'),
('t2@t2.com'),
('jacob@jlabelle.ca'),
('tesla@electricity'),
('tesla@electricity');

-- --------------------------------------------------------

--
-- Table structure for table `demoDB`
--

CREATE TABLE `demoDB` (
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(30) NOT NULL,
  `Email` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `demoDB`
--

INSERT INTO `demoDB` (`FirstName`, `LastName`, `Email`) VALUES
('Jacob', 'Labelle', 'raurgm@gmail.com'),
('Nikola', 'Tesla', 'nick@backintime.com'),
('Mickey', 'Mouse', 'mick@disney.com'),
('Minnie', 'Mouse', 'minn@disney.com'),
('Donald', 'Duck', 'don@cartoons.com'),
('Porky', 'Pig', 'pork@cartoons.ca'),
('Goofy', 'Dufus', 'goof@disney.com'),
('Mickey', 'Mouse', 'mouse@disney.com'),
('Bobby', 'Tables\');--', '4@lulz.com'),
('Mackey', 'Moose', 'moose@loose.com'),
('Bobby2', '<script type=\"text/javascript\"', '327@xkcd.com'),
('Mackey', 'Moose', 'moose@loose.com'),
('Morty', 'Man', 'mort@man.com'),
('Meow', 'Meow', 'meow@meow.com'),
('Meow', 'Meow', 'meow@meow.com'),
('Jacob', 'Labelle', 'j@j.ca'),
('Mortle', 'Mow', 'test@test.com'),
('g', 'g', 'gy@e5tf'),
('g', 'g', 'gy@e5tf'),
('g', 'g', 'gy@e5tf'),
('g', 'g', 'gy@e5tf'),
('g', 'g', 'gy@e5tf'),
('Hi', 'Jacob', 'Bye@good.com'),
('test', 'tester', 'test@test.test'),
('t', 't', 't@t.t'),
('Snow', 'White', 'sno@disney.cstle'),
('Test24', 'Test', 't@t.ca'),
('Chris', 'Anderson', 'chriswanderson676@gmail.com'),
('Test', 'Test2', 'test@tesst.com'),
('Test2', 'Test293', 'test@test.com'),
('Test237', 'Test766', 'test@tesat.com'),
('Chris', 'Anderson', 'chris@test.com'),
('snow', 'white', 's@disney.com'),
('snow', 'white', 's@disney.com');

-- --------------------------------------------------------

--
-- Table structure for table `projectUsers`
--

CREATE TABLE `projectUsers` (
  `username` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `projectUsers`
--

INSERT INTO `projectUsers` (`username`, `password`) VALUES
('test_user@test.com', 'b2e98ad6f6eb8508dd6a14cfa704bad7f05f6fb1'),
('raurgm@gmail.com', 'b2e98ad6f6eb8508dd6a14cfa704bad7f05f6fb1'),
('jacob.labelle93@gmail.com', 'b2e98ad6f6eb8508dd6a14cfa704bad7f05f6fb1'),
('debvwarren', 'cad5916c659d1d791939ddf0b437b367ef7107b8'),
('debviwarren', 'cad5916c659d1d791939ddf0b437b367ef7107b8');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
