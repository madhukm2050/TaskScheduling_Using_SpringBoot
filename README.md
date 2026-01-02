⏰ Task Scheduling Using Spring Boot

A Spring Boot–based scheduled email reminder system that automatically sends emails at scheduled times.
The application is designed to work safely even in distributed environments using database-based locking and maintains logs using Log4j2 rolling files.



📖 Project Overview

This project demonstrates how to:

Schedule background jobs using Spring Boot

Prevent duplicate execution in multi-instance deployments

Send automated email reminders

Persist and track reminder status in a database

Store application logs safely in rolling log files


🏗️ Architecture
Spring Scheduler (@Scheduled)
        |
        v
RemainderService (Business Logic + ShedLock)
        |
        v
MySQL Database
(reminders, shedlock tables)
        |
        v
Email Service (JavaMailSender - SMTP)



✅ Features Implemented

Task Scheduling

Fixed-rate and fixed-delay scheduling using @Scheduled

Email Reminder System

Automatically sends emails when scheduled time is reached

Distributed Locking

Uses ShedLock with MySQL

Ensures only one instance executes the scheduled task

Database Persistence

Stores reminders and their sent status (is_sent)

Uses Spring Data JPA (Hibernate)

Logging

Uses Log4j2

Logs are written to files with 1 GB rolling policy


🧰 Tech Stack
Layer	Technology
Language	Java 17
Framework	Spring Boot
Scheduling	Spring Scheduler (@Scheduled)
ORM	Spring Data JPA (Hibernate)
Database	MySQL
Email	JavaMailSender (SMTP)
Locking	ShedLock
Logging	Log4j2 (Rolling File)
Build Tool	Maven
⚙️ How the System Works

Scheduler runs at a configured interval

ShedLock acquires a database lock

Database is queried for pending reminders

Email is sent for due reminders

Reminder is marked as sent

Logs are written to file and console


🗄️ Database Setup
Create Database
CREATE DATABASE taskscheduling;
USE taskscheduling;

Create reminders Table
CREATE TABLE reminders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255),
  message VARCHAR(255),
  scheduled_time DATETIME,
  is_sent BOOLEAN DEFAULT FALSE
);

Create shedlock Table
CREATE TABLE shedlock (
  name VARCHAR(64) PRIMARY KEY,
  lock_until TIMESTAMP,
  locked_at TIMESTAMP,
  locked_by VARCHAR(255)
);



✉️ Email Configuration

This project uses Gmail SMTP with App Password.

Add the following to application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/taskscheduling
spring.datasource.username=root
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=YOUR_GMAIL_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


⚠️ Gmail requires App Passwords, not normal passwords.



📝 Logging

Logging is implemented using Log4j2

Logs are stored in:

logs/application.log


When the file size reaches 1 GB, a new log file is created automatically

Example log output:

INFO  RemainderService - Scheduler triggered
INFO  RemainderService - Sending reminder email
ERROR EmailService - Authentication failed

▶️ How to Run the Project
mvn clean spring-boot:run


Make sure:

MySQL server is running

Database tables are created

Gmail App Password is configured




⚠️ Current Limitations

No REST API to create reminders

Reminders must be inserted directly into the database

No retry mechanism for failed emails



🚀 Future Enhancements

Add REST API for creating reminders

Add retry logic for email failures

Add input validation

Dockerize the application

Add monitoring and metrics
