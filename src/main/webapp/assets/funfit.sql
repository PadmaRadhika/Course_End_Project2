CREATE DATABASE funfit;
use funfit;
CREATE TABLE Batch (
    id INT AUTO_INCREMENT PRIMARY KEY,
    batch_name VARCHAR(255) NOT NULL UNIQUE,
    batch_time VARCHAR(20) NOT NULL,
    capacity int,    
    instructor_name varchar(255)        
);

CREATE TABLE Participant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    email VARCHAR(255),
    batchid int,
    FOREIGN KEY (batchid) REFERENCES Batch(id)    
);