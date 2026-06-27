CREATE DATABASE IF NOT EXISTS db_toko_elektronik_zeus
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

USE db_toko_elektronik_zeus;

SET SQL_MODE  = 'NO_AUTO_VALUE_ON_ZERO';
SET time_zone = '+00:00';
START TRANSACTION;

-- ------------------------------------------------------------
--  1. USERS
-- ------------------------------------------------------------
CREATE TABLE `users` (
  `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `username`    VARCHAR(255)     NULL,
  `password`    VARCHAR(255)     NULL,
  `namalengkap` VARCHAR(255)     NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id`, `username`, `password`, `namalengkap`) VALUES
  (3, 'admin', 'admin123', 'Administrator');

ALTER TABLE `users`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

-- ------------------------------------------------------------
--  2. CUSTOMERS
-- ------------------------------------------------------------
CREATE TABLE `customers` (
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `nama_customer` VARCHAR(100) NOT NULL,
  `no_telepon`    VARCHAR(15)      NULL,
  `alamat`        VARCHAR(255)     NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `customers` (`id`, `alamat`, `nama_customer`, `no_telepon`) VALUES
  (6, 'Negara Ngawi', 'Son',    '086767676'),
  (7, '',             'NICOOO', '');

ALTER TABLE `customers`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

-- ------------------------------------------------------------
--  3. BARANG
-- ------------------------------------------------------------
CREATE TABLE `barang` (
  `kode_barang` VARCHAR(20)  NOT NULL,
  `nama_barang` VARCHAR(100) NOT NULL,
  `kategori`    VARCHAR(50)  NOT NULL,
  `harga`       BIGINT(20)   NOT NULL,
  `stok`        INT(11)      NOT NULL DEFAULT 0,
  `aktif`       BIT(1)       NOT NULL,
  PRIMARY KEY (`kode_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `barang` (`kode_barang`, `nama_barang`, `kategori`, `harga`, `stok`, `aktif`) VALUES
  ('CTH01',  'AAA',                 'Lainnya',   100000000, 23, b'1'),
  ('ELK001', 'Mouse Wireless',      'Aksesoris',   1500000, 20, b'1'),
  ('ELK002', 'Keyboard Mechanical', 'Aksesoris',    450000, 14, b'1');

-- ------------------------------------------------------------
--  4. PENJUALAN
-- ------------------------------------------------------------
CREATE TABLE `penjualan` (
  `no_transaksi`      VARCHAR(20)  NOT NULL,
  `jenis_transaksi`   VARCHAR(10)  NOT NULL,
  `keterangan`        VARCHAR(255)     NULL,
  `tanggal_transaksi` DATETIME(6)  NOT NULL,
  `total_harga`       BIGINT(20)   NOT NULL,
  `customer_id`       BIGINT(20)       NULL,
  `user_id`           BIGINT(20)   NOT NULL,
  PRIMARY KEY (`no_transaksi`),
  KEY `fk_penjualan_customer` (`customer_id`),
  KEY `fk_penjualan_user`     (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `penjualan` (`no_transaksi`, `jenis_transaksi`, `keterangan`, `tanggal_transaksi`, `total_harga`, `customer_id`, `user_id`) VALUES
  ('JL20260624001', 'JUAL',    'E',  '2026-06-24 10:32:50.000000', 150000,   6, 3),
  ('JL20260624002', 'JUAL',    '',   '2026-06-24 11:21:56.000000', 1950000,  7, 3),
  ('RS20260624001', 'RESTOCK', '',   '2026-06-24 11:22:45.000000', 0,     NULL, 3);

-- ------------------------------------------------------------
--  5. PENJUALAN_DETAIL
-- ------------------------------------------------------------
CREATE TABLE `penjualan_detail` (
  `id`           BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `harga_satuan` BIGINT(20)  NOT NULL,
  `jumlah`       INT(11)     NOT NULL,
  `subtotal`     BIGINT(20)  NOT NULL,
  `kode_barang`  VARCHAR(20) NOT NULL,
  `no_transaksi` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detail_barang`    (`kode_barang`),
  KEY `fk_detail_transaksi` (`no_transaksi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `penjualan_detail` (`id`, `harga_satuan`, `jumlah`, `subtotal`, `kode_barang`, `no_transaksi`) VALUES
  (26, 150000,   1, 150000,  'ELK001', 'JL20260624001'),
  (27, 1500000,  1, 1500000, 'ELK001', 'JL20260624002'),
  (28, 450000,   1, 450000,  'ELK002', 'JL20260624002'),
  (29, 0,        3, 0,       'CTH01',  'RS20260624001');

ALTER TABLE `penjualan_detail`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

-- ------------------------------------------------------------
--  6. STOK_LOG
-- ------------------------------------------------------------
CREATE TABLE `stok_log` (
  `id`              BIGINT(20)             NOT NULL AUTO_INCREMENT,
  `jenis_mutasi`    ENUM('KELUAR','MASUK') NOT NULL,
  `jumlah`          INT(11)                NOT NULL,
  `keterangan`      VARCHAR(255)               NULL,
  `no_transaksi`    VARCHAR(20)                NULL,
  `stok_sebelum`    INT(11)                NOT NULL,
  `stok_sesudah`    INT(11)                NOT NULL,
  `tanggal`         DATETIME(6)            NOT NULL,
  `kode_barang`     VARCHAR(20)                NULL,
  `kode_barang_ref` VARCHAR(20)                NULL,
  `nama_barang_ref` VARCHAR(100)               NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stoklog_barang` (`kode_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `stok_log` (`id`, `jenis_mutasi`, `jumlah`, `keterangan`, `no_transaksi`, `stok_sebelum`, `stok_sesudah`, `tanggal`, `kode_barang`, `kode_barang_ref`, `nama_barang_ref`) VALUES
  (29, 'KELUAR', 1, 'JUAL',    'JL20260624001', 22, 21, '2026-06-24 10:32:50.000000', 'ELK001', 'ELK001', 'Mouse Wireless'),
  (30, 'KELUAR', 1, 'JUAL',    'JL20260624002', 21, 20, '2026-06-24 11:21:56.000000', 'ELK001', 'ELK001', 'Mouse Wireless'),
  (31, 'KELUAR', 1, 'JUAL',    'JL20260624002', 15, 14, '2026-06-24 11:21:56.000000', 'ELK002', 'ELK002', 'Keyboard Mechanical'),
  (32, 'MASUK',  3, 'RESTOCK', 'RS20260624001', 20, 23, '2026-06-24 11:22:45.000000', 'CTH01',  'CTH01',  'AAA');

ALTER TABLE `stok_log`
  MODIFY `id` BIGINT(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

-- ============================================================
--  FOREIGN KEY CONSTRAINTS
-- ============================================================
ALTER TABLE `penjualan`
  ADD CONSTRAINT `fk_penjualan_customer`
      FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  ADD CONSTRAINT `fk_penjualan_user`
      FOREIGN KEY (`user_id`)     REFERENCES `users`     (`id`);

ALTER TABLE `penjualan_detail`
  ADD CONSTRAINT `fk_detail_barang`
      FOREIGN KEY (`kode_barang`)  REFERENCES `barang`    (`kode_barang`),
  ADD CONSTRAINT `fk_detail_transaksi`
      FOREIGN KEY (`no_transaksi`) REFERENCES `penjualan` (`no_transaksi`);

ALTER TABLE `stok_log`
  ADD CONSTRAINT `fk_stoklog_barang`
      FOREIGN KEY (`kode_barang`) REFERENCES `barang` (`kode_barang`);

COMMIT;