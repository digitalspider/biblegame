/** PostgreSQL */
CREATE USER biblegame WITH PASSWORD 'biblegame' CREATEDB;
SET AUTOCOMMIT = ON;
CREATE DATABASE biblegame OWNER biblegame;
GRANT ALL PRIVILEGES on DATABASE biblegame to biblegame;
-- logout / login 
CREATE SCHEMA IF NOT EXISTS biblegame AUTHORIZATION biblegame;

/** MySQL **/
CREATE DATABASE biblegame CHARACTER SET utf8 COLLATE utf8_bin;
CREATE USER 'biblegame'@'%' IDENTIFIED BY 'biblegame';
GRANT ALL PRIVILEGES on biblegame.* to 'biblegame'@'%';
FLUSH PRIVILEGES;
