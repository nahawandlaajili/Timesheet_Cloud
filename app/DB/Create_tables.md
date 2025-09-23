# Postgres DB
psql postgres 
## Create database
CREATE DATABASE userdb;

## Create user with password
CREATE USER user_service WITH PASSWORD 'grgjUHI9';

## Give privileges
GRANT ALL PRIVILEGES ON DATABASE userdb TO user_service;

## Grant ownership to root user
ALTER TABLE timesheets  OWNER TO root;

## exit
\q

-------------------------------------------------------
## Tables
User
Employe
Contrat - Contrat Type
Department
Entreprise 
Mission - Mission Externe 
Role (CHEF_DEPARTEMENT, ADMINISTRATEUR, INGENIEUR, TECHNICIEN) 
Timeshhet - TimesheetPK
-----------------------------------------------------------------
Database:
   
   CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'employee',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE timesheets (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    work_date DATE NOT NULL,
    hours_worked DECIMAL(4,2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

Timesheet table should include projetID as a foreign key:
ALTER TABLE timesheets ADD COLUMN projetID INT REFERENCES projects(project_id);
sheet: sheetId, projetID, date, hoursWorked, taskDescription, status (APPROVED, PENDING, REJECTED);  

CREATE TABLE vacations (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'pending', -- pending, approved, rejected
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE sickness (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    leave_date DATE NOT NULL,
    leave_type VARCHAR(50), -- e.g., sick, personal, emergency
    status VARCHAR(20) DEFAULT 'pending',
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE projects (
   project_id SERIAL PRIMARY KEY,
   project_name VARCHAR(255) NOT NULL,
   project_description TEXT
);

Each timesheet record can reference a project via projetID (foreign key).