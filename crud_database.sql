-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 05, 2025 lúc 09:03 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `crud_database`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `authorities`
--

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(255) NOT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `authorities`
--

INSERT INTO `authorities` (`username`, `authority`, `id`) VALUES
('admin', 'ROLE_ADMIN', 0),
('admin', 'ROLE_MANAGER', 0),
('admin', 'ROLE_STUDENT', 0),
('admin', 'ROLE_USER', 0),
('hqy123', 'ROLE_ADMIN', 0),
('hqy123', 'ROLE_USER', 0),
('manager', 'ROLE_MANAGER', 0),
('manager', 'ROLE_STUDENT', 0),
('manager', 'ROLE_USER', 0),
('student', 'ROLE_STUDENT', 0),
('student', 'ROLE_USER', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customers`
--

CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL,
  `address` text DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customers`
--

INSERT INTO `customers` (`id`, `address`, `email`, `name`, `phone`) VALUES
(1, '123 Đường Lê Lợi, Quận 1, TP.HCM', 'nguyenvana@example.com', 'Nguyễn Văn A', '0912345678'),
(2, '456 Đường Nguyễn Huệ, Quận 3, TP.HCM', 'tranthib@example.com', 'Trần Thị B', '0987654321'),
(3, '789 Đường Cách Mạng Tháng 8, Quận 10, TP.HCM', 'levanc@example.com', 'Lê Văn C', '0905123456'),
(4, '321 Đường Hai Bà Trưng, Quận 1, TP.HCM', 'phamthid@example.com', 'Phạm Thị D', '0977123456'),
(5, '159 Đường Lý Thường Kiệt, Quận 5, TP.HCM', 'hoangvane@example.com', 'Hoàng Văn E', '0911122334'),
(6, '753 Đường Võ Văn Tần, Quận 3, TP.HCM', 'vuthif@example.com', 'Vũ Thị F', '0988999888'),
(7, '456 Đường Trần Hưng Đạo, Quận 5, TP.HCM', 'dangvang@example.com', 'Đặng Văn G', '0903123123'),
(8, '852 Đường Lê Văn Sỹ, Quận Phú Nhuận, TP.HCM', 'buithih@example.com', 'Bùi Thị H', '0915987654'),
(9, '147 Đường Nguyễn Thị Minh Khai, Quận 3, TP.HCM', 'maivani@example.com', 'Mai Văn I', '0978456123'),
(10, '369 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM', 'lythik@example.com', 'Lý Thị K', '0909123456');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `employee`
--

INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`) VALUES
(1, 'Leslie', 'Andrews', 'leslie@luv2code.com'),
(2, 'Emma', 'Baumgarten', 'emma@luv2code.com'),
(3, 'Avani', 'Gupta', 'avani@luv2code.com'),
(4, 'Yuri', 'Petrov', 'yuri@luv2code.com'),
(5, 'Juan', 'Vega', 'juan@luv2code.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` longtext DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `available` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `name`, `description`, `price`, `image_url`, `is_available`, `created_at`, `available`) VALUES
(1, 'Skewers thịt bò', 'Thịt bò nướng sốt đặc biệt, ăn kèm rau tươi', 120000.00, '/default/images/beef_skewers.jpg', b'1', '2025-03-31 07:21:39', 1),
(2, 'Nem nướng Nha Trang', 'Nem truyền thống Nha Trang, chấm mắm pha', 65000.00, '/default/images/nem_nuong.jpg', b'1', '2025-03-31 07:18:13', 1),
(3, 'Trà đào cam sả', 'Trà đào pha cùng cam sả tươi mát', 35000.00, '/default/images/tra_dao.jpg', b'1', '2025-03-31 07:18:21', 1),
(4, 'Bò nướng lá lốt', 'Thịt bò băm gói lá lốt nướng than hoa', 85000.00, '/default/images/bo_la_lot.jpg', b'1', '2025-03-31 07:18:30', 1),
(5, 'Cơm chiên hải sản', 'Cơm chiên cùng tôm, mực tươi', 75000.00, '/default/images/com_chien.jpg', b'1', '2025-03-31 07:18:36', 1),
(6, 'Bia Tiger', 'Bia Tiger chai 330ml', 45000.00, '/default/images/bia_tiger.jpg', b'1', '2025-03-31 07:18:42', 1),
(7, 'Chè khúc bạch', 'Chè khúc bạch thạch dừa mát lạnh', 40000.00, '/default/images/che_khuc_bach.jpg', b'0', '2025-03-31 07:18:50', 1),
(8, 'com', 'cơm', 200000.00, '/default/images/com_chien.jpg', b'1', '2025-03-31 07:22:03', 1),
(9, 'cơm', 'cơm', 20000.00, '/default/images/1743406942325_1743337163054_com_chien.jpg', b'1', '2025-03-31 07:42:22', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `student`
--

CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `student`
--

INSERT INTO `student` (`id`, `first_name`, `last_name`, `email`) VALUES
(12, 'Truong', 'Tran', 'truongtran@gmail.com'),
(14, 'Vu', 'Hoang\r\n', 'vuhoang@gmail.com'),
(20, 'Quynh', 'Lan', 'quynhlan@gmail.com'),
(21, 'Bao', 'Khoa', 'baokhoa@gmail.com'),
(22, 'abc', 'aaa', 'abc@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`username`, `password`, `enabled`) VALUES
('admin', '$2a$12$4KbdPdZihopYJEp5SGFSPuyBGoWzkIcrNYqYTqBr6xd6/rLeg2knq', b'1'),
('admin,admin', '$2a$12$4KbdPdZihopYJEp5SGFSPuyBGoWzkIcrNYqYTqBr6xd6/rLeg2knq', b'1'),
('hqy123', '$2a$12$8O/1t7uQT3gohsMjWF2nrO.hus.ufKI8dvaaNjtZ6s9JKsUZMpZXm', b'1'),
('manager', '$2a$12$4KbdPdZihopYJEp5SGFSPuyBGoWzkIcrNYqYTqBr6xd6/rLeg2knq', b'1'),
('student', '$2a$12$4KbdPdZihopYJEp5SGFSPuyBGoWzkIcrNYqYTqBr6xd6/rLeg2knq', b'1');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `authorities`
--
ALTER TABLE `authorities`
  ADD PRIMARY KEY (`username`,`authority`),
  ADD KEY `ix_auth_username` (`username`,`authority`);

--
-- Chỉ mục cho bảng `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKrfbvkrffamfql7cjmen8v976v` (`email`);

--
-- Chỉ mục cho bảng `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKh3w5r1mx6d0e5c6um32dgyjej` (`code`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `customers`
--
ALTER TABLE `customers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `student`
--
ALTER TABLE `student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `authorities`
--
ALTER TABLE `authorities`
  ADD CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
