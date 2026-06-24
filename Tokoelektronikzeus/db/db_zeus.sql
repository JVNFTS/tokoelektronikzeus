-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 24 Jun 2026 pada 05.25
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

CREATE DATABASE IF NOT EXISTS db_toko_elektronik_zeus;
USE db_toko_elektronik_zeus;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_toko_elektronik_zeus`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `kode_barang` varchar(20) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `harga` bigint(20) NOT NULL,
  `stok` int(11) NOT NULL DEFAULT 0,
  `aktif` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`kode_barang`, `nama_barang`, `kategori`, `harga`, `stok`, `aktif`) VALUES
('ELK001', 'Mouse Wireless', 'Aksesoris', 150000, 22, b'1'),
('ELK002', 'Keyboard Mechanical', 'Aksesoris', 450000, 15, b'1');

-- --------------------------------------------------------

--
-- Struktur dari tabel `customers`
--

CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `nama_customer` varchar(100) NOT NULL,
  `no_telepon` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `customers`
--

INSERT INTO `customers` (`id`, `alamat`, `nama_customer`, `no_telepon`) VALUES
(6, 'Negara Ngawi\r\n', 'Son', '086767676');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `no_transaksi` varchar(20) NOT NULL,
  `jenis_transaksi` varchar(10) NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `tanggal_transaksi` datetime(6) NOT NULL,
  `total_harga` bigint(20) NOT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan_detail`
--

CREATE TABLE `penjualan_detail` (
  `id` bigint(20) NOT NULL,
  `harga_satuan` bigint(20) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `subtotal` bigint(20) NOT NULL,
  `kode_barang` varchar(20) NOT NULL,
  `no_transaksi` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `stok_log`
--

CREATE TABLE `stok_log` (
  `id` bigint(20) NOT NULL,
  `jenis_mutasi` enum('KELUAR','MASUK') NOT NULL,
  `jumlah` int(11) NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `no_transaksi` varchar(20) DEFAULT NULL,
  `stok_sebelum` int(11) NOT NULL,
  `stok_sesudah` int(11) NOT NULL,
  `tanggal` datetime(6) NOT NULL,
  `kode_barang` varchar(20) NOT NULL,
  `kode_barang_ref` varchar(20) DEFAULT NULL,
  `nama_barang_ref` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nama_lengkap` varchar(255) DEFAULT NULL,
  `namalengkap` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `nama_lengkap`, `namalengkap`) VALUES
(3, 'admin', 'admin123', NULL, 'Administrator');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kode_barang`);

--
-- Indeks untuk tabel `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`no_transaksi`),
  ADD KEY `FKny48il22bbji5ykq8v68d5j88` (`customer_id`),
  ADD KEY `FKajalxj9o2k4iucrs302p7l17w` (`user_id`);

--
-- Indeks untuk tabel `penjualan_detail`
--
ALTER TABLE `penjualan_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjwd03ybdory404kx72xjl3qcm` (`kode_barang`),
  ADD KEY `FKti93d9vbstd9negbddgyyxdmi` (`no_transaksi`);

--
-- Indeks untuk tabel `stok_log`
--
ALTER TABLE `stok_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8dnsc58ieilq40ir5knr04xaa` (`kode_barang`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `customers`
--
ALTER TABLE `customers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `penjualan_detail`
--
ALTER TABLE `penjualan_detail`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT untuk tabel `stok_log`
--
ALTER TABLE `stok_log`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD CONSTRAINT `FKajalxj9o2k4iucrs302p7l17w` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKny48il22bbji5ykq8v68d5j88` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);

--
-- Ketidakleluasaan untuk tabel `penjualan_detail`
--
ALTER TABLE `penjualan_detail`
  ADD CONSTRAINT `FKjwd03ybdory404kx72xjl3qcm` FOREIGN KEY (`kode_barang`) REFERENCES `barang` (`kode_barang`),
  ADD CONSTRAINT `FKti93d9vbstd9negbddgyyxdmi` FOREIGN KEY (`no_transaksi`) REFERENCES `penjualan` (`no_transaksi`);

--
-- Ketidakleluasaan untuk tabel `stok_log`
--
ALTER TABLE `stok_log`
  ADD CONSTRAINT `FK8dnsc58ieilq40ir5knr04xaa` FOREIGN KEY (`kode_barang`) REFERENCES `barang` (`kode_barang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
