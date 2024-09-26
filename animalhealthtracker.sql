-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 26, 2024 at 04:32 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `animalhealthtracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `email`, `username`, `password`, `full_name`, `image`, `gender`) VALUES
(20, 'john', 'john', 'john1234', NULL, NULL, NULL),
(21, 'shakila@gmail.com', 'shakila', '$2a$10$mesNJxgLr3yRoAhsNi6eA.h5tk9CMMVOjQIjUPRqg/24MaIYxJWGi', NULL, NULL, NULL),
(24, 'admin@email.com', 'admin', '$2a$10$7oHQD8phE.7A.1s7trGare7rCUDS7F9qiUAjjtv7bETQRa49c4wj.', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `cases`
--

CREATE TABLE `cases` (
  `caseId` varchar(100) NOT NULL,
  `species` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `age` int(11) NOT NULL,
  `location` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `heartRate` int(11) NOT NULL,
  `bodyTemp` int(11) NOT NULL,
  `tests` varchar(1000) NOT NULL,
  `treatments` varchar(1000) NOT NULL,
  `followDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cases`
--

INSERT INTO `cases` (`caseId`, `species`, `gender`, `age`, `location`, `date`, `heartRate`, `bodyTemp`, `tests`, `treatments`, `followDate`) VALUES
('CROC004', 'Mugger Crocodile', 'Male', 20, 'Bundala National Park', '2024-09-19', 25, 32, 'X-ray', 'Surgery to remove fishing hook', '2024-09-26'),
('DEER008', 'Sri Lankan Sambar Deer', 'Male', 6, 'Horton Plains National Park', '2024-05-10', 55, 38, 'X-ray', 'Treatment for a fractured antler', '2024-06-15'),
('ELEPH002', 'Sri Lankan Elephant', 'Female', 30, 'Udawalawe National Park', '2024-09-20', 35, 36, 'Ultrasound', 'Treatment for digestive issues', '2024-09-26'),
('HORN005', 'Sri Lankan Grey Hornbill', 'Female', 5, 'Sinharaja Forest Reserve', '2024-09-24', 150, 40, 'Feather examination, X-ray', 'Wing fracture treated with bandage', '2024-09-26'),
('LEOP001', 'Sri Lankan Leopard', 'Male', 7, 'Yala National Park', '2024-09-22', 60, 39, 'Blood Test, X-ray', 'Fracture treatment on leg', '2024-09-26'),
('MONK006', 'Toque Macaque', 'Male', 9, 'Dambulla Cave Temple', '2024-07-18', 80, 39, 'Blood Test', 'Skin infection treated with antibiotics', '2024-08-05'),
('PANG007', 'Indian Pangolin', 'Female', 5, 'Kumana National Park', '2024-06-15', 65, 37, 'Blood Test, Skin Scraping', 'Treatment for parasitic infection', '2024-06-30'),
('PEAF010', 'Indian Peafowl', 'Male', 3, 'Anuradhapura', '2024-08-22', 120, 40, 'Feather inspection', 'Wing injury treated with bandage', '2024-09-05'),
('SLOTH003', 'Sri Lankan Sloth Bear', 'Female', 12, 'Wilpattu National Park', '2024-09-21', 55, 37, 'Blood Test', 'Wound dressing and antibiotics for leg injury', '2024-09-26'),
('TORT009', 'Sri Lankan Star Tortoise', 'Female', 15, 'Yala National Park', '2024-06-20', 20, 28, 'Shell inspection, X-ray', 'Repair for cracked shell', '2024-07-10');

-- --------------------------------------------------------

--
-- Table structure for table `case_info`
--

CREATE TABLE `case_info` (
  `caseId` varchar(100) NOT NULL,
  `species` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `gender` varchar(100) NOT NULL,
  `followDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `case_info`
--

INSERT INTO `case_info` (`caseId`, `species`, `date`, `gender`, `followDate`) VALUES
('LEOP001', 'Sri Lankan Leopard', '2024-09-22', 'Male', '2024-09-26'),
('ELEPH002', 'Sri Lankan Elephant', '2024-09-20', 'Female', '2024-09-26'),
('SLOTH003', 'Sri Lankan Sloth Bear', '2024-09-21', 'Female', '2024-09-26'),
('CROC004', 'Mugger Crocodile', '2024-09-19', 'Male', '2024-09-26'),
('HORN005', 'Sri Lankan Grey Hornbill', '2024-09-24', 'Female', '2024-09-26'),
('MONK006', 'Toque Macaque', '2024-07-18', 'Male', '2024-07-30'),
('PANG007', 'Indian Pangolin', '2024-06-15', 'Female', '2024-06-28'),
('DEER008', 'Sri Lankan Sambar Deer', '2024-05-10', 'Male', '2024-05-29'),
('TORT009', 'Sri Lankan Star Tortoise', '2024-06-20', 'Female', '2024-07-01'),
('PEAF010', 'Indian Peafowl', '2024-08-22', 'Male', '2024-08-27');

-- --------------------------------------------------------

--
-- Table structure for table `diagnostics`
--

CREATE TABLE `diagnostics` (
  `caseId` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `tests` varchar(1000) NOT NULL,
  `treatments` varchar(1000) NOT NULL,
  `followDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `diagnostics`
--

INSERT INTO `diagnostics` (`caseId`, `date`, `tests`, `treatments`, `followDate`) VALUES
('LEOP001', '2024-09-22', 'Blood Test, X-ray', 'Fracture treatment on leg', '2024-09-26'),
('ELEPH002', '2024-09-20', 'Ultrasound', 'Treatment for digestive issues', '2024-09-26'),
('SLOTH003', '2024-09-21', 'Blood Test', 'Wound dressing and antibiotics for leg injury', '2024-09-26'),
('CROC004', '2024-09-19', 'X-ray', 'Surgery to remove fishing hook', '2024-09-26'),
('HORN005', '2024-09-24', 'Feather examination, X-ray', 'Wing fracture treated with bandage', '2024-09-26'),
('MONK006', '2024-07-18', 'Blood Test', 'Skin infection treated with antibiotics', '2024-07-30'),
('PANG007', '2024-06-15', 'Blood Test, Skin Scraping', 'Treatment for parasitic infection', '2024-06-28'),
('DEER008', '2024-05-10', 'X-ray', 'Treatment for a fractured antler', '2024-05-29'),
('TORT009', '2024-06-20', 'Shell inspection, X-ray', 'Repair for cracked shell', '2024-07-01'),
('PEAF010', '2024-08-22', 'Feather inspection', 'Wing injury treated with bandage', '2024-08-27'),
('PEAF010', '2024-08-27', 'X-ray', 'Bandage adjustment for wing injury', '2024-09-05'),
('MONK006', '2024-07-30', 'Heart Ultrasound, Blood Test', 'Antibiotics for cardiac infection', '2024-08-05'),
('TORT009', '2024-07-01', 'Ultrasound, Blood Test', 'Wound care with antiseptics for shell repair', '2024-07-10'),
('PANG007', '2024-06-28', 'X-ray', 'Medication for bone strength', '2024-06-30'),
('DEER008', '2024-05-29', 'MRI, Ultrasound', 'Pain relief medication for antler fracture', '2024-06-15');

-- --------------------------------------------------------

--
-- Table structure for table `vet`
--

CREATE TABLE `vet` (
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `phoneNo` varchar(100) NOT NULL,
  `specialization` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vet`
--

INSERT INTO `vet` (`firstName`, `lastName`, `gender`, `phoneNo`, `specialization`, `username`, `password`, `date`) VALUES
('Nuwan', 'Perera', 'Male', '0712345678', 'Large/Small', 'nuwan001', 'nuwan1234', '2024-09-24'),
('Chamali', 'Silva', 'Female', '0722345678', 'Avian & Exotic', 'chamali002', '12345678', '2024-09-24'),
('Kasun', 'Fernando', 'Male', '0753456789', 'Wildlife', 'kasun003', '12345678', '2024-09-24'),
('Amaya', 'Jayawardena', 'Female', '0774567890', 'Surgery', 'amaya004', '12345678', '2024-09-24'),
('Sanjeewa', 'Abeysekara', 'Male', '0785678901', 'Pathology', 'sanjeewa005', '12345678', '2024-09-24');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `cases`
--
ALTER TABLE `cases`
  ADD PRIMARY KEY (`caseId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
