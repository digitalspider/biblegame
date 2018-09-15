CREATE USER biblegame WITH PASSWORD 'biblegame' CREATEDB;
SET AUTOCOMMIT = ON;
CREATE DATABASE biblegame OWNER biblegame;
GRANT ALL PRIVILEGES on DATABASE biblegame to biblegame;
-- logout / login 
CREATE SCHEMA IF NOT EXISTS biblegame AUTHORIZATION biblegame;
