CREATE TABLE `t_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_number` varchar(255) DEFAULT NULL,
  `sku_code` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
