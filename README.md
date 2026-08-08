# StoryFirst PH — Event Quotation Request System

Java Console Application | MySQL Database


---

## About the Project

*StoryFirst PH* is a Java console-based quotation request system developed as a practical exam project. It allows clients to submit event details and request price quotations from the StoryFirst PH events team. The application stores client information and quotation requests in a normalized relational MySQL database, validates all user inputs, generates a formatted quotation preview, and simulates an automatic email notification upon successful submission.

This project follows a clean layered architecture: *Model → Repository → Service → View*, ensuring separation of concerns, maintainability, and extensibility.

---

## Features

- *Client Information Capture* — full name, company/organization, email, contact number
- *Event Details Form* — event name, event type (9 categories via enum), date, venue, duration, estimated guests
- *Event Type Menu* — numbered selection with input validation (WEDDING, BIRTHDAY, CORPORATE, etc.)
- *Smart Input Validation* — required fields, email format, phone length, positive integers, date format (YYYY-MM-DD) with retry loops
- *Relational Database* — separate clients and quotation_requests tables with foreign key relationship; same email reuses existing client record
- *Duplicate Client Prevention* — ON DUPLICATE KEY UPDATE returns existing client_id when email matches
- *Formatted Quotation Preview* — clean ASCII-style quotation summary with client, event, and service details
- *Simulated Email Notification* — prints a realistic confirmation email that would be sent to the client
- *Status Tracking* — 6-stage quotation lifecycle via QuotationStatus enum (PENDING, REVIEWING, QUOTED, APPROVED, DECLINED, COMPLETED)
- *Indexed Database* — optimized queries on email, client, date, and status columns

---

## Technology Stack

| Component | Technology |
|---|---|
| Language | Java SE (JDK 8+) |
| Database | MySQL 8.0+ (via XAMPP) |
| Connectivity | JDBC (mysql-connector-j) |
| IDE | NetBeans IDE |
| Architecture | Layered (Model / Repository / Service / View) |
| Date Handling | Java Time API (LocalDate, DateTimeFormatter) |

---

## Database Schema
```

storyfirst_ph
│
├── clients
│   ├── client_id       INT PK AUTO_INCREMENT
│   ├── full_name       VARCHAR(100) NOT NULL
│   ├── company_org     VARCHAR(150) NULL
│   ├── email           VARCHAR(100) NOT NULL UNIQUE
│   ├── contact_number  VARCHAR(30) NOT NULL
│   └── created_at      DATETIME DEFAULT CURRENT_TIMESTAMP
│
└── quotation_requests
├── request_id          INT PK AUTO_INCREMENT
├── client_id           INT FK → clients.client_id ON DELETE CASCADE
├── event_name          VARCHAR(100) NOT NULL
├── event_type          VARCHAR(50) NOT NULL
├── event_date          DATE NOT NULL
├── event_venue         VARCHAR(200) NOT NULL
├── number_of_hours     INT NOT NULL
├── estimated_guests    INT NOT NULL
├── services_needed     TEXT NOT NULL
├── additional_notes    TEXT NULL
├── submitted_at        DATETIME DEFAULT CURRENT_TIMESTAMP
└── status              VARCHAR(30) DEFAULT 'PENDING'
*Indexes:* idx_client_email, idx_request_client, idx_request_date, idx_request_status

---
```


## Project Structure
```

StoryFirstPH/
├── src/
│   ├── config/
│   │   └── DbConnection.java
│   ├── model/
│   │   ├── Client.java
│   │   ├── QuotationRequest.java
│   │   ├── EventType.java
│   │   └── QuotationStatus.java
│   ├── repository/
│   │   ├── ClientRepo.java
│   │   ├── ClientRepoImpl.java
│   │   ├── QuotationRequestRepo.java
│   │   └── QuotationRequestRepoImpl.java
│   ├── service/
│   │   └── QuotationService.java
│   └── view/
│       └── QuotationRequestApp.java
├── build/
├── dist/
├── nbproject/
├── build.xml
└── manifest.mf
---
```

## Installation & Setup

### Prerequisites
- JDK 8 or higher
- NetBeans IDE
- XAMPP (Apache + MySQL)
- MySQL JDBC Driver (mysql-connector-j)

### Step 1 — Open in NetBeans
1. Open project in NetBeans
2. Right-click *Libraries → Add JAR/Folder* → add MySQL JDBC connector

### Step 2 — Create the Database
1. Start *Apache* and *MySQL* in XAMPP
2. Open http://localhost/phpmyadmin
3. Create database: storyfirst_ph
4. Run the SQL from the Database Schema section above

### Step 3 — Configure Connection
Verify config/DbConnection.java matches your XAMPP credentials:

URL = "jdbc:mysql://localhost:3306/storyfirst_ph"
USER = "root"
PASSWORD = ""

### Step 4 — Run
1. Click *Clean & Build*
2. Right-click QuotationRequestApp.java → *Run File*
3. The application starts in the NetBeans Output console

### Usage Walkthrough
```


========================================================
        STORYFIRST PH - EVENT QUOTATION REQUEST          
========================================================
Please fill in all required details below.

Full Name *: Juan Dela Cruz
Company / Organization (optional): ABC Corp
Email Address *: juan@example.com
Contact Number *: 09171234567
Event Name *: 10th Anniversary

--- Select Event Type ---
  1. WEDDING
  2. BIRTHDAY
  3. CORPORATE
  ...
Enter your choice: 5

Event Date * (YYYY-MM-DD, e.g. 2026-12-25): 2026-12-15
Event Venue *: Manila Hotel
Number of Hours *: 6
Estimated Number of Guests *: 150
Services Needed *: Photography, Video, Photo Booth
Additional Notes / Requirements: Need same-day edit

Submitting your request...

SUCCESS! Your request has been saved.
Client Reference ID: 1

[ FORMATTED QUOTATION PREVIEW ]
[ SIMULATED EMAIL NOTIFICATION ]

Thank you! You may close this window.
```

## Validation Rules

| Field | Validation Rule |
|---|---|
| Full Name | Required, cannot be blank |
| Email Address | Required, must contain @ symbol |
| Contact Number | Required, minimum 7 characters |
| Event Name | Required, cannot be blank |
| Event Type | Required, must select from menu (1–9 only) |
| Event Date | Required, must be valid YYYY-MM-DD format |
| Event Venue | Required, cannot be blank |
| Number of Hours | Required, must be a positive integer (> 0) |
| Estimated Guests | Required, must be a positive integer (> 0) |
| Services Needed | Required, cannot be blank |
| Additional Notes | Optional |
| Company / Organization | Optional |

### Key Design Decisions

Relational Normalization — Client data stored once; multiple quotations can reference the same client via client_id
Enum over Strings — EventType and QuotationStatus as enums prevent invalid database values
Transient Form Fields — QuotationRequest carries client input fields temporarily; only client_id is persisted to the requests table
Idempotent Client Save — ON DUPLICATE KEY UPDATE with LAST_INSERT_ID safely handles returning clients
Manual JDBC Resource Management — Explicit finally blocks ensure connections, statements, and result sets are always closed, compatible with all Java 8+ environments
Layered Architecture — Each layer has a single responsibility; swapping MySQL for another database only touches the repository layer

### Future Improvements

Planned enhancements:

Automated Pricing Engine — Services catalog with base rates + hourly/guest tiers; auto-generate itemized quotation with VAT and grand total
Admin Dashboard & Backoffice — Staff login, live statistics dashboard, request table with filters, one-click status updates, calendar view to prevent double-bookings
Security Hardening — BCrypt password hashing, role-based access control (ADMIN / STAFF / CLIENT), session timeouts, audit logs, input sanitization against SQL injection and XSS
Technical Improvements — Connection pooling (HikariCP), SLF4J structured logging, Flyway database migrations, JUnit 5 tests, environment-based configuration
Additional Features — Date availability checking, PDF quotation generation, real email via JavaMail API, GCash/Maya payment links, client self-service portal

License
This project was developed as an academic practical exam requirement. For educational and portfolio use only.

StoryFirst PH — Capturing Your Story, One Event at a Time.

Built with care using Java + MySQL.
