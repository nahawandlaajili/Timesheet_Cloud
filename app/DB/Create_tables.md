# Postgres DB

## connect to postgres 
psql -U nahawandlaajili -d dashboard_app

psql postgres 
## Create database
CREATE DATABASE Dashboard_app;

## Create user with password
CREATE USER user_service WITH PASSWORD 'grgjUHI9';

CREATE USER timesheet_user WITH PASSWORD 'rootpwd';

## Give privileges
GRANT ALL PRIVILEGES ON DATABASE dashboard_app TO user_service;

GRANT ALL PRIVILEGES ON DATABASE dashboard_app TO timesheet_user;
## Grant ownership to root user
ALTER TABLE timesheets  OWNER TO root;


## Tables list
\dt


-- replace your_user with the username in application.properties
GRANT USAGE ON SCHEMA public TO timesheet_user
GRANT CREATE ON SCHEMA public TO your_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO timesheet_user
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON SEQUENCES TO timesheet_user;




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
    remaining_days_off INT DEFAULT 30;
);


CREATE TABLE timesheet (
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    days_requested INT NOT NULL,
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