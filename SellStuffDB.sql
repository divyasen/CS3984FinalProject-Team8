# -------------------------------------------
# SQL script to create the SellStuff database
# Created by Woo Jin Kye
# -------------------------------------------

/*
Tables to be dropped must be listed in a logical order based on dependency.
Listing and UserPhoto depend on User. Therefore, they must be dropped before User.
ListingPhoto depends on Listing. Therefore, it must be dropped before Listing.
*/
DROP TABLE IF EXISTS UserPhoto, User, ListingPhoto, Listing;

/* The User table contains attributes of interest of a User. */
CREATE TABLE User
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username VARCHAR (32) NOT NULL,
    password VARCHAR (32) NOT NULL,
    first_name VARCHAR (32) NOT NULL,
    middle_name VARCHAR (32),
    last_name VARCHAR (32) NOT NULL,
    address1 VARCHAR (128) NOT NULL,
    address2 VARCHAR (128),
    city VARCHAR (64) NOT NULL,
    state VARCHAR (2) NOT NULL,
    zipcode VARCHAR (10) NOT NULL, /* e.g., 24060-1804 */
    security_question INT NOT NULL, /* Refers to the number of the selected security question */
    security_answer VARCHAR (128) NOT NULL,
    email VARCHAR (128) NOT NULL,
    phone_number VARCHAR (32) NOT NULL,
    PRIMARY KEY (id)
);

/* The UserPhoto table contains attributes of interest of a user's profile photo. */
CREATE TABLE UserPhoto
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    extension ENUM('jpeg', 'jpg', 'png', 'gif') NOT NULL,
    user_id INT UNSIGNED,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

/* The Listing table contains attributes of interest of a user's item listing. */
CREATE TABLE Listing
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    item_name VARCHAR (256) NOT NULL,
    description TEXT NOT NULL,
    posting_date DATE NOT NULL,
    price DECIMAL (10,2) NOT NULL,
    category VARCHAR (64) NOT NULL,
    user_id INT UNSIGNED,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

/* The ListingPhoto table contains attributes of interest of an item's photo. */
CREATE TABLE ListingPhoto
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    extension ENUM('jpeg', 'jpg', 'png', 'gif') NOT NULL,
    listing_id INT UNSIGNED,
    FOREIGN KEY (listing_id) REFERENCES Listing(id) ON DELETE CASCADE
);
