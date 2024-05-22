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

CREATE TABLE IF NOT EXISTS `mydb`.`store` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `address` VARCHAR(150) NOT NULL,
    `status` VARCHAR(45) NULL,
    `star` DOUBLE NULL DEFAULT 0,
    `thumnail_url` VARCHAR(200) NOT NULL,
    `minium_amount` DECIMAL(11,4) NULL,
    `minimum_delivery_amount` DECIMAL(11,4) NULL,
    `phone_number` VARCHAR(20) NULL,
    `category` VARCHAR(45) NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB