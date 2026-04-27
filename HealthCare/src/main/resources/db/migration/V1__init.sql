-- =========================
-- V1 - INIT TABLES
-- =========================

CREATE TABLE patient (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nom VARCHAR(200) NOT NULL,
                         prenom VARCHAR(200) NOT NULL,
                         email VARCHAR(200) UNIQUE,
                         telephone VARCHAR(50),
                         date_naissance DATE
);

CREATE TABLE medecin (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nom VARCHAR(200) NOT NULL,
                         specialite VARCHAR(200) NOT NULL,
                         email VARCHAR(200) UNIQUE,
                         telephone VARCHAR(50)
);

CREATE TABLE dossier_medical (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 diagnostic VARCHAR(200),
                                 observation VARCHAR(200),
                                 date_creation DATE,
                                 patient_id BIGINT UNIQUE
);

CREATE TABLE rendez_vous (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             date_rendez_vous TIMESTAMP,
                             statut ENUM('PLANIFIE','CONFIRME','ANNULE','TERMINE','ABSENT') NOT NULL,
                             patient_id BIGINT,
                             medecin_id BIGINT
);