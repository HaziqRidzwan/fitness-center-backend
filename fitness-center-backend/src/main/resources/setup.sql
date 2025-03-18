-- Create the database first
CREATE DATABASE fitness_center;

-- Select the database to use
USE fitness_center;

-- Create the users table
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL
);

-- Create the members table
CREATE TABLE members (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE,
  phone VARCHAR(20),
  address VARCHAR(255),
  date_of_birth DATE,
  membership_type VARCHAR(50),
  membership_start_date DATE,
  membership_end_date DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Add initial admin user (password: admin123)
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$A7F0.xkSYG0wlCz4UkABOeZQd6QQyHB8CUE4L7K.Z57KjU6SX1xOi', 'ADMIN');

-- Create the user_roles table for role-based access
CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role VARCHAR(255),
  FOREIGN KEY (user_id) REFERENCES users(id)
);
