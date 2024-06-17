-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 17, 2024 at 03:04 PM
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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

-- --------------------------------------------------------

--
-- Table structure for table `d_chat`
--

CREATE TABLE IF NOT EXISTS `d_chat` (
`id` int(11) NOT NULL,
  `id_hchat` int(11) NOT NULL,
  `pengirim` text NOT NULL,
  `penerima` text NOT NULL,
  `isi` text NOT NULL,
  `attach_foodtrack` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `d_chat`
--

INSERT INTO `d_chat` (`id`, `id_hchat`, `pengirim`, `penerima`, `isi`, `attach_foodtrack`) VALUES
(1, 2, 'yoanesrobah', 'yoanesrobahdokter', 'halo', ''),
(2, 2, 'yoanesrobahdokter', 'yoanesrobah', 'halo pasien kocak', ''),
(3, 2, 'yoanesrobah', 'yoanesrobahdokter', '', '1');

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `food_track`
--

INSERT INTO `food_track` (`id`, `nama`, `jumlah`, `calories`, `protein`, `sugar`, `carbs`, `fat`, `cholesterol`, `sodium`, `date_add`) VALUES
(1, 'nasi goreng', 1, 2, 2, 2, 2, 2, 2, 2, '2024-06-17 17:23:50');

-- --------------------------------------------------------

--
-- Table structure for table `h_chat`
--

CREATE TABLE IF NOT EXISTS `h_chat` (
`id` int(11) NOT NULL,
  `user1` text NOT NULL,
  `user2` text NOT NULL,
  `selesai` tinyint(1) NOT NULL DEFAULT '0',
  `kesimpulan` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `h_chat`
--

INSERT INTO `h_chat` (`id`, `user1`, `user2`, `selesai`, `kesimpulan`) VALUES
(2, 'yoanesrobah', 'yoanesrobahdokter', 0, '');

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
('yoanesrobah', 'yoanesrobah@gmail.com', 'Yoanes Robah', 'ggasep', 'male', '', '', '', 0, '2024-06-16 23:25:25'),
('yoanesrobahdokter', 'yoanesrobahdokter@gmail.com', 'Yoanes Robah Dokter', 'ggasep', 'male', 'dokter anaconda', 'STTS', '2020', 4, '2024-06-16 23:25:25');

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
 ADD PRIMARY KEY (`id`);

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
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `d_chat`
--
ALTER TABLE `d_chat`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `food_track`
--
ALTER TABLE `food_track`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `h_chat`
--
ALTER TABLE `h_chat`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
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
