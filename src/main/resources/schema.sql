SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS voucher_codes;
DROP TABLE IF EXISTS recipients;
DROP TABLE IF EXISTS special_offers;

SET FOREIGN_KEY_CHECKS = 1;

-- Create Recipients table
CREATE TABLE recipients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Create Special Offers table
CREATE TABLE special_offers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    percentage_discount DOUBLE NOT NULL,
    description VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- Create Voucher Codes table
CREATE TABLE voucher_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(15) NOT NULL UNIQUE,
    recipient_id BIGINT NOT NULL,
    special_offer_id BIGINT NOT NULL,
    expiration_date DATETIME NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    used_date DATETIME,
    FOREIGN KEY (recipient_id) REFERENCES recipients(id) ON DELETE CASCADE,
    FOREIGN KEY (special_offer_id) REFERENCES special_offers(id) ON DELETE CASCADE
) ENGINE=InnoDB;
