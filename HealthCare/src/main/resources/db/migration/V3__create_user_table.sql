CREATE TABLE user (

                        id BIGINT PRIMARY KEY AUTO_INCREMENT,

                        user_name VARCHAR(255) NOT NULL,

                        email VARCHAR(255) UNIQUE NOT NULL,

                        password VARCHAR(255) NOT NULL

);