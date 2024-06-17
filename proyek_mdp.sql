-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 17, 2024 at 05:45 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `proyek_mdp`
--

-- --------------------------------------------------------

--
-- Table structure for table `artikel`
--

CREATE TABLE IF NOT EXISTS `artikel` (
`id` int(11) NOT NULL,
  `judul` text NOT NULL,
  `penulis` text NOT NULL,
  `isi` text NOT NULL,
  `image` longtext NOT NULL,
  `view` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `artikel`
--

INSERT INTO `artikel` (`id`, `judul`, `penulis`, `isi`, `image`, `view`, `created_at`) VALUES
(1, 'Cara menurunkan berat badan', 'yoanesrobah', 'test', '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', 100, '2024-06-17 10:42:26');

-- --------------------------------------------------------

--
-- Table structure for table `dokter_regis`
--

CREATE TABLE IF NOT EXISTS `dokter_regis` (
  `username` varchar(255) NOT NULL,
  `email` text NOT NULL,
  `fullname` text NOT NULL,
  `password` text NOT NULL,
  `gender` text NOT NULL,
  `specialist` text NOT NULL,
  `sekolah` text NOT NULL,
  `tahun_lulus` text NOT NULL,
  `lama_praktik` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dokter_regis`
--

INSERT INTO `dokter_regis` (`username`, `email`, `fullname`, `password`, `gender`, `specialist`, `sekolah`, `tahun_lulus`, `lama_praktik`, `created_at`) VALUES
('yoanesrobahdokter', 'yoanesrobahdokter@gmail.com', 'Yoanes Robah Dokter', 'ggasep', 'male', 'dokter anaconda', 'STTS', '2020', 4, '2024-06-16 23:25:25'),
('yoanesrobahdokter2', 'yoanesrobahdokter@gmail.com', 'Yoanes Robah Dokter2', 'ggasep', 'male', 'dokter anaconda', 'STTS', '2020', 4, '2024-06-16 23:25:25');

-- --------------------------------------------------------

--
-- Table structure for table `d_chat`
--

CREATE TABLE IF NOT EXISTS `d_chat` (
`id_hchat` int(11) NOT NULL,
  `isi` text NOT NULL,
  `attach_foodtrack` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `food_track`
--

CREATE TABLE IF NOT EXISTS `food_track` (
`id` int(11) NOT NULL,
  `nama` text NOT NULL,
  `jumlah` int(11) NOT NULL,
  `calories` int(11) NOT NULL,
  `protein` int(11) NOT NULL,
  `sugar` int(11) NOT NULL,
  `carbs` int(11) NOT NULL,
  `fat` int(11) NOT NULL,
  `cholesterol` int(11) NOT NULL,
  `sodium` int(11) NOT NULL,
  `date_add` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `h_chat`
--

CREATE TABLE IF NOT EXISTS `h_chat` (
`id` int(11) NOT NULL,
  `pengirim` text NOT NULL,
  `penerima` text NOT NULL,
  `selesai` tinyint(1) NOT NULL,
  `kesimpulan` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `resep`
--

CREATE TABLE IF NOT EXISTS `resep` (
`id` int(11) NOT NULL,
  `id_hchat` int(11) NOT NULL,
  `nama_obat` text NOT NULL,
  `deskripsi_obat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE IF NOT EXISTS `review` (
`id` int(11) NOT NULL,
  `username_pengirim` text NOT NULL,
  `username_target` text NOT NULL,
  `isi` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id`, `username_pengirim`, `username_target`, `isi`) VALUES
(1, 'yoanesrobah', 'yoanesrobah', 'user gila coy');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(255) NOT NULL,
  `email` text NOT NULL,
  `fullname` text NOT NULL,
  `password` text NOT NULL,
  `gender` text NOT NULL,
  `specialist` text NOT NULL,
  `sekolah` text NOT NULL,
  `tahun_lulus` text NOT NULL,
  `lama_praktik` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `email`, `fullname`, `password`, `gender`, `specialist`, `sekolah`, `tahun_lulus`, `lama_praktik`, `created_at`) VALUES
('yoanesrobah', 'yoanesrobah@gmail.com', 'Yoanes Robah', 'ggasep', 'male', '', '', '', 0, '2024-06-16 23:25:25');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `artikel`
--
ALTER TABLE `artikel`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dokter_regis`
--
ALTER TABLE `dokter_regis`
 ADD PRIMARY KEY (`username`);

--
-- Indexes for table `d_chat`
--
ALTER TABLE `d_chat`
 ADD PRIMARY KEY (`id_hchat`);

--
-- Indexes for table `food_track`
--
ALTER TABLE `food_track`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `h_chat`
--
ALTER TABLE `h_chat`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `resep`
--
ALTER TABLE `resep`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `artikel`
--
ALTER TABLE `artikel`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `d_chat`
--
ALTER TABLE `d_chat`
MODIFY `id_hchat` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `food_track`
--
ALTER TABLE `food_track`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `h_chat`
--
ALTER TABLE `h_chat`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `resep`
--
ALTER TABLE `resep`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
