ServiceSphere
-Offline Enterprise Service Desk & Incident Management System
ServiceSphere is a Java Swing desktop application that simulates a real-world enterprise IT Service Desk.Employees can raise IT support tickets, while the administrator manages users, assets, technicians, assignments, SLA deadlines, escalations, knowledge-base articles and reports.

Project Flow -

Employee Login
      ↓
Raise Ticket
      ↓
Ticket Created
      ↓
Admin Reviews Ticket
      ↓
Technician Assigned
      ↓
SLA Monitoring
      ↓
SLA Breach → Escalation
      ↓
Resolution
      ↓
Closed

Main Features-

1.Authentication & Roles
- One fixed ADMIN account
- Multiple EMPLOYEE accounts
- Role-based dashboard routing
- Active-account validation
- Login validation

2.Employee
- Employee dashboard
- Raise service tickets
- Select category and priority
- Select related asset
- View own tickets
- View SLA information
- View assigned assets
- Logout

3.Admin
- Dashboard and ticket statistics
- Employee management
- Technician management
- Asset management
- Ticket management
- Technician assignment/reassignment
- Ticket status updates
- SLA monitoring
- SLA breach/escalation monitoring
- Reports and technician workload
- Knowledge Base
- Logout

- Ticket Lifecycle
OPEN → ASSIGNED → IN PROGRESS → WAITING → RESOLVED → CLOSED

- SLA Monitoring
SLA = Service Level Agreement
Current SLA targets:

Priority                     Target
Critical                  60 minutes
High                      240 minutes
Medium                    480 minutes
Low                        1440 minutes

When an active ticket passes its deadline:

Due Time Passed
      ↓
SLA Breached
      ↓
Escalated

- Asset Management
Supports laptops, desktops, monitors, printers and network equipment. Assets can be assigned to employees and linked to tickets.

- Knowledge Base
Searchable troubleshooting articles for common issues such as:

- Laptop not turning on
- Internet connection problems
- Application problems
- Password reset
- Printer problems

- Reports
Provides:
-Total tickets
-Open tickets
-Assigned tickets
-In-progress tickets
-Resolved tickets
-Closed tickets
-SLA-breached tickets
-Technician workload

Technology Stack--  Java 21, Java Swing, JFrame, JDBC, MySQL, Eclipse, MySQL Connector, DbConnJar.jar(Reusable database connection)

** The project is offline-first and does not require React, Node.js, Spring Boot, JavaFX, a web server or cloud hosting.

** Required JAR Files - DbConnJar.jar, MySQL Connector/J
The project uses a reusable custom database connection JAR.
- JAR: DbConnJar.jar
- Package: JarJdbc
- Class: DBConn
- Method: MyConnection()

**  How to Run

1. Install prerequisites
JDK 21
Eclipse
MySQL

2. Import the project
Import ServiceSphere into Eclipse.

3. Add JAR dependencies
Add:
DbConnJar.jar
mysql-connector-j-26.7.0.jar
to the Eclipse Build Path.

4. Create the database
Run the database SQL above.

5. Create the admin account
INSERT INTO users
(full_name, username, password, role, department, phone)
VALUES
('System Administrator','admin','admin123','ADMIN','IT','9999999999');

6. Add demo data
For a populated demonstration, add employees, technicians, assets, knowledge-base articles and tickets.

7. Start MySQL
Make sure the MySQL server is running.

8. Run the application
Run:LoginFrame.java
LoginFrame.java is the application entry point.

** Application Navigation

Admin -
Login
 ↓
Admin Dashboard
 ├── Tickets
 ├── Technicians
 ├── Assets
 ├── Users
 ├── SLA Monitor
 ├── Reports
 ├── Knowledge Base
 └── Logout

Employee -
Login
 ↓
Employee Dashboard
 ├── Raise Ticket
 ├── My Tickets
 ├── My Assets
 └── Logout


** Project Relationships

Employee
   ↓
Asset
   ↓
Service Ticket
   ↓
Technician
   ↓
SLA
   ↓
Resolution
   ↓
Reports

A ticket therefore connects the complete support story:
Who reported the issue → what asset is affected → what the problem is → who is responsible → when it should be resolved → whether it was late → what happened finally.

***** Security Note - 
This is an educational/project demonstration application.
The current implementation uses simple database authentication and stores passwords directly in the database. A production enterprise system should use password hashing, secure secret storage, stronger authorization, audit logging and encrypted communication.

