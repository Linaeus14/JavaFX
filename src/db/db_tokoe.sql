-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2023 at 08:14 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_tokoe`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE `akun` (
  `userid` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `status` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`userid`, `pass`, `nama`, `email`, `status`) VALUES
('admin1', '1111', 'Tito Darmawan', '', 'admin'),
('manager1', '1111', 'Tito Darmawan', 'null', 'manager'),
('pem1', '1111', 'Tito Darmawan', '-', 'pembeli'),
('pem2', '1111', 'Cia', '-', 'pembeli'),
('staff1', '1111', 'Tito Darmawan', 'null', 'staff'),
('staff2', '1111', 'Cia', 'null', 'staff');

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `kode` varchar(6) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `kodeP` varchar(6) DEFAULT NULL,
  `harga` int(20) NOT NULL,
  `stok` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode`, `nama`, `kodeP`, `harga`, `stok`) VALUES
('ac12', 'AC Dingin Sekali', 'apak1', 8000, 3),
('hg12', 'Handheld GPS mk12', 'uuc1', 10000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

CREATE TABLE `pembelian` (
  `idPembelian` int(11) NOT NULL,
  `userid` varchar(50) NOT NULL,
  `total` int(15) NOT NULL,
  `status` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pembelian`
--

INSERT INTO `pembelian` (`idPembelian`, `userid`, `total`, `status`) VALUES
(6, 'pem1', 10000, 'selsai'),
(7, 'pem1', 24000, 'selsai'),
(8, 'pem1', 46000, 'diproses'),
(9, 'pem2', 18000, 'diproses');

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE `pesanan` (
  `idPesanan` int(11) NOT NULL,
  `idPembelian` int(8) NOT NULL,
  `kodeBarang` varchar(6) NOT NULL,
  `jumlah` int(3) NOT NULL,
  `subtotal` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pesanan`
--

INSERT INTO `pesanan` (`idPesanan`, `idPembelian`, `kodeBarang`, `jumlah`, `subtotal`) VALUES
(6, 6, 'hg12', 1, 10000),
(7, 7, 'ac12', 3, 24000),
(8, 8, 'hg12', 3, 30000),
(9, 8, 'ac12', 2, 16000),
(10, 9, 'hg12', 1, 10000),
(11, 9, 'ac12', 1, 8000);

-- --------------------------------------------------------

--
-- Table structure for table `produsen`
--

CREATE TABLE `produsen` (
  `kodeP` varchar(6) NOT NULL,
  `produsen` varchar(50) NOT NULL,
  `alamat` varchar(150) NOT NULL,
  `kontak` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `produsen`
--

INSERT INTO `produsen` (`kodeP`, `produsen`, `alamat`, `kontak`) VALUES
('-', '-', '-', '-'),
('apak1', 'AntiPanasAntiKeringat', 'dimana', '0091'),
('uuc1', 'Utillity Universal Corps', 'Everywhere', '91091');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kode`),
  ADD KEY `barang_produsen` (`kodeP`);

--
-- Indexes for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD PRIMARY KEY (`idPembelian`),
  ADD KEY `pembelian_akun` (`userid`);

--
-- Indexes for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD PRIMARY KEY (`idPesanan`),
  ADD KEY `pesanan_pembelian` (`idPembelian`),
  ADD KEY `pesanan_kodeBarang` (`kodeBarang`);

--
-- Indexes for table `produsen`
--
ALTER TABLE `produsen`
  ADD PRIMARY KEY (`kodeP`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pembelian`
--
ALTER TABLE `pembelian`
  MODIFY `idPembelian` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `pesanan`
--
ALTER TABLE `pesanan`
  MODIFY `idPesanan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `barang_produsen` FOREIGN KEY (`kodeP`) REFERENCES `produsen` (`kodeP`) ON UPDATE NO ACTION;

--
-- Constraints for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD CONSTRAINT `pembelian_akun` FOREIGN KEY (`userid`) REFERENCES `akun` (`userid`) ON UPDATE NO ACTION;

--
-- Constraints for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD CONSTRAINT `pesanan_kodeBarang` FOREIGN KEY (`kodeBarang`) REFERENCES `barang` (`kode`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `pesanan_pembelian` FOREIGN KEY (`idPembelian`) REFERENCES `pembelian` (`idPembelian`) ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
