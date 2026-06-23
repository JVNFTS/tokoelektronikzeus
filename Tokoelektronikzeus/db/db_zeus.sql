-- ============================================================
--  DATABASE: db_toko_elektronik_zeus
--  Import file ini di phpMyAdmin: Import > pilih file ini
-- ============================================================

CREATE DATABASE IF NOT EXISTS db_toko_elektronik_zeus
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE db_toko_elektronik_zeus;

-- ------------------------------------------------------------
--  Tabel: users
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    namalengkap VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO users (username, password, namalengkap) VALUES
('admin', 'admin123', 'Administrator');

-- ------------------------------------------------------------
--  Tabel: barang
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS barang (
    kode_barang VARCHAR(20)    NOT NULL,
    nama_barang VARCHAR(100)   NOT NULL,
    kategori    VARCHAR(50)    NOT NULL,
    harga       DECIMAL(15, 2) NOT NULL DEFAULT 0,
    stok        INT            NOT NULL DEFAULT 0,
    PRIMARY KEY (kode_barang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO barang (kode_barang, nama_barang, kategori, harga, stok) VALUES
('ELK001', 'Mouse Wireless',       'Aksesoris',  150000.00, 20),
('ELK002', 'Keyboard Mechanical',  'Aksesoris',  450000.00, 10),
('ELK003', 'Monitor 24 inch FHD',  'Monitor',   2500000.00,  5),
('ELK004', 'Headset Gaming',       'Audio',      350000.00, 15),
('ELK005', 'USB Hub 4 Port',       'Aksesoris',   85000.00, 30);

-- ------------------------------------------------------------
--  Tabel: customers
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS customers (
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    nama_customer  VARCHAR(100) NOT NULL,
    no_telepon     VARCHAR(15),
    alamat         VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO customers (nama_customer, no_telepon, alamat) VALUES
('Budi Santoso',   '081234567890', 'Jl. Merdeka No. 10, Pontianak'),
('Dewi Rahayu',    '082345678901', 'Jl. Gajah Mada No. 5, Pontianak'),
('Ahmad Fauzi',    '083456789012', 'Jl. Diponegoro No. 22, Singkawang');

-- ------------------------------------------------------------
--  Tabel: penjualan
--  jenis_transaksi: JUAL | RESTOCK
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS penjualan (
    no_transaksi      VARCHAR(20)  NOT NULL,
    customer_id       BIGINT,
    user_id           BIGINT       NOT NULL,
    tanggal_transaksi DATETIME     NOT NULL,
    jenis_transaksi   VARCHAR(10)  NOT NULL,
    total_harga       DECIMAL(15,2) NOT NULL DEFAULT 0,
    keterangan        VARCHAR(255),
    PRIMARY KEY (no_transaksi),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL,
    FOREIGN KEY (user_id)     REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
--  Tabel: penjualan_detail
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS penjualan_detail (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    no_transaksi  VARCHAR(20)  NOT NULL,
    kode_barang   VARCHAR(20)  NOT NULL,
    harga_satuan  DECIMAL(15,2) NOT NULL DEFAULT 0,
    jumlah        INT          NOT NULL DEFAULT 1,
    subtotal      DECIMAL(15,2) NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (no_transaksi) REFERENCES penjualan(no_transaksi) ON DELETE CASCADE,
    FOREIGN KEY (kode_barang)  REFERENCES barang(kode_barang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------
--  Tabel: stok_log
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS stok_log (
    id           BIGINT      NOT NULL AUTO_INCREMENT,
    kode_barang  VARCHAR(20) NOT NULL,
    jenis_mutasi VARCHAR(10) NOT NULL,
    jumlah       INT         NOT NULL,
    stok_sebelum INT         NOT NULL,
    stok_sesudah INT         NOT NULL,
    no_transaksi VARCHAR(20),
    tanggal      DATETIME    NOT NULL,
    keterangan   VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (kode_barang) REFERENCES barang(kode_barang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

