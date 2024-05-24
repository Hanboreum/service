CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `status` VARCHAR(45) NOT NULL,
    `address` VARCHAR(150) NOT NULL,
    `registered_at` DATETIME NULL,
    `unregistered_at` DATETIME NULL,
    `last_login_at` DATETIME NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `store` (
     `id` BIGINT NOT NULL AUTO_INCREMENT,
     `name` VARCHAR(100) NOT NULL,
    `address` VARCHAR(150) NOT NULL,
    `status` VARCHAR(45) NOT NULL,
    `star` DOUBLE NULL DEFAULT 0,
    `category` VARCHAR(45) NOT NULL,
    `thumbnailUrl` VARCHAR(200) NOT NULL,
    `minimum_amount` DECIMAL(11,4) NOT NULL,
    `minimum_delivery_amount` DECIMAL(11,4) NOT NULL,
    `phone_number` VARCHAR(20) NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `store_menu` (
    `id` BIGINT(32) NOT NULL AUTO_INCREMENT,
    `store_id` BIGINT(32) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `amount` DECIMAL(11,4) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `thumbnail_url` VARCHAR(200) NOT NULL,
    `like_count` INT NULL DEFAULT 0,
    `sequence` INT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB;
ALTER TABLE `delivery`.`store_menu`
    ADD INDEX `idx_store_id` (`store_id` ASC);
;


CREATE TABLE IF NOT EXISTS `user_order_menu` (
   `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_order_id` BIGINT NOT NULL,
    `store_menu_id` BIGINT(32) NOT NULL,
    `status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_user_order_id` (`user_order_id` ASC),
    INDEX `idx_store_menu_id` (`store_menu_id` ASC)
    )
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `user_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
     `user_id` BIGINT NOT NULL,
     `status` VARCHAR(50) NOT NULL,
    `amount` DECIMAL(11,4) NOT NULL,
    `ordered_at` DATETIME NULL,
    `accepted_at` DATETIME NULL,
    `cooking_started_at` DATETIME NULL,
    `delivery_started_at` DATETIME NULL,
    `received_at` DATETIME NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id` ASC)
    )
    ENGINE = InnoDB;