🕒 Scheduled Email Reminder System (Spring Boot)

A Spring Boot–based scheduled email reminder system that automatically sends emails at scheduled times.
The application is safe for distributed environments, uses database-backed locking (ShedLock) to avoid duplicate execution, and logs execution details using Log4j2 with rolling file support.

📌 Architecture Overview
┌──────────────────────────┐
│ Spring Scheduler         │
│ (@Scheduled Tasks)       │
└───────────┬──────────────┘
            │
            ▼
┌──────────────────────────┐
│ RemainderService         │
│ - Fetch due reminders    │
│ - Send emails            │
│ - Update sent status     │
│ - ShedLock protection    │
└───────────┬──────────────┘
            │
            ▼
┌──────────────────────────┐
│ MySQL Database           │
│ - reminders table        │
│ - shedlock table         │
└───────────┬──────────────┘
            │
            ▼
┌──────────────────────────┐
│ Email Service (SMTP)     │
│ JavaMailSender           │
└──────────────────────────┘

✨ Features Implemented

⏰ Task Scheduling

Uses Spring’s @Scheduled annotation

Supports fixed-rate and fixed-delay execution

📧 Automated Email Reminders

Sends emails when scheduled_time <= current time

Uses JavaMailSender (SMTP)

🛑 Distributed Scheduler Lock

Uses ShedLock with MySQL

Prevents duplicate email sending in multi-instance deployments

🗄️ Database Persistence

Stores reminders and execution status (is_sent)

Uses Spring Data JPA with Hibernate

📜 Centralized Logging

Uses Log4j2

Logs written to file with 1 GB rolling policy

❌ No REST API

Reminders are currently inserted directly into the database

🛠️ Tech Stack
Category	Technology
Language	Java 17
Framework	Spring Boot
Scheduling	Spring @Scheduled
ORM	Spring Data JPA (Hibernate)
Database	MySQL
Email	JavaMailSender (SMTP)
Distributed Lock	ShedLock
Logging	Log4j2 (Rolling File – 1GB)
Build Tool	Maven
🚀 How the System Works

Scheduler runs at a fixed interval (every few seconds)

Acquires a ShedLock to ensure single execution

Fetches reminders where:

scheduled_time <= NOW()

is_sent = false

Sends email using SMTP

Marks reminder as sent (is_sent = true)

Logs all steps to console and file

🗄️ Database Setup
Create Database
CREATE DATABASE taskscheduling;
USE taskscheduling;

Create reminders table
CREATE TABLE reminders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255),
  message VARCHAR(255),
  scheduled_time DATETIME,
  is_sent BOOLEAN DEFAULT FALSE
);

Create shedlock table
CREATE TABLE shedlock (
  name VARCHAR(64) PRIMARY KEY,
  lock_until TIMESTAMP,
  locked_at TIMESTAMP,
  locked_by VARCHAR(255)
);

✉️ Email Configuration

This project uses Gmail SMTP with App Password.

application.properties
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


⚠️ Gmail requires App Passwords (normal passwords will fail).

📜 Logging Configuration

Logging is implemented using Log4j2

Logs are written to:

logs/application.log


When the file reaches 1 GB, it rolls automatically:

application-1.log
application-2.log


Example log output:

INFO  RemainderService - Scheduler triggered for sending reminders
INFO  RemainderService - Number of reminders found: 1
INFO  RemainderService - Sending reminder email to user@gmail.com
ERROR EmailService - Authentication failed

▶️ How to Run
mvn clean spring-boot:run


Make sure:

MySQL is running

Gmail App Password is configured

📌 Current Limitations

No REST API to create reminders

Reminders must be inserted directly into the database

No retry count for failed emails

🔮 Future Improvements

Add REST API to create reminders

Add retry mechanism for failed emails

Add validation and error handling

Dockerize the application

Add metrics and monitoring
