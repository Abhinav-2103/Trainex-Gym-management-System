# 🏋️ TRAINEX — Advanced Gym Management System

A production-grade, full-stack **Advanced Java** enterprise web application built with **Spring Boot 3.4.1**, **Spring Security 6**, **Spring Data JPA**, **Thymeleaf**, and **MySQL 8**. Designed with modern gym aesthetic, glassmorphism UI, interactive analytics, and member management workflows.

---

## ✨ Features & Capabilities

### 1. 🔐 Authentication & Access Control
- **Spring Security 6** with BCrypt password encryption.
- Multi-user authentication, Remember-Me cookie support, and custom Login/Register workflows.
- Full-screen immersive neon login experience with cosmic particles.
- Seeded administrator accounts:
  - `admin` / `admin123`
  - `flexzone_admin` / `flexzone_pass`

### 2. 📊 Interactive Analytics Dashboard
- **KPI Metrics**: Total Members, Active Subscriptions, Expired Subscriptions, and Monthly Revenue.
- **Interactive KPI Cards**: Clickable cards navigating directly into relevant modules.
- **Visual Analytics**: Interactive 6-week revenue and attendance trends powered by **Chart.js**.
- **Recently Joined Feed**: Quick-access member previews linking directly to profiles.

### 3. 👥 Comprehensive Member Management
- Full CRUD operations: Register, Edit, Delete, and View Member Profiles.
- Real-time search by member name or phone number.
- **Interactive Member Directory**: Click any member's avatar or name anywhere in the application to inspect their profile.
- **Member Profile Hub**:
  - Personal details, age, gender, address, and join date.
  - **Quick Action Workflows**:
    - 🏷️ **Assign / Renew Subscription** (auto-preselects member).
    - 💳 **Record Payment** (auto-preselects member).
    - 📅 **Mark Daily Attendance** (auto-preselects member & today's date).
  - **Direct Communication**: One-click phone calling (`tel:`) and email triggers (`mailto:`).

### 4. 💳 Membership Plans
- Configurable membership tiers:
  - *Basic Monthly* (1 Month — ₹999)
  - *Standard Quarterly* (3 Months — ₹2,499)
  - *Premium Half-Yearly* (6 Months — ₹4,499)
  - *Elite Annual* (12 Months — ₹7,999)
- Full flexibility to add custom durations and pricing tiers.

### 5. 🔄 Subscriptions Lifecycle
- Member-to-plan association with automated start/end date calculation.
- Real-time status evaluation (`Active` vs `Expired`).
- Filter and renew expired subscriptions in one click.

### 6. 💰 Payments & Revenue Ledger
- Track payments with multiple payment methods: **UPI**, **Cash**, **Card**, and **Online**.
- Real-time ledger showing total lifetime revenue and monthly income.
- Filterable and searchable transaction history.

### 7. 🏋️‍♂️ Trainers Management
- Dedicated trainer directory with specialization badges (*CrossFit*, *Weight Training*, *Yoga*, *Cardio & HIIT*).
- Salary records and direct contact information.

### 8. 📅 Daily Attendance Tracking
- Route `/attendance/mark` for fast daily check-ins.
- Prefills today's date with default `Present` status.
- Attendance history logs with filterable status badges.

### 9. 📈 Reports & Analytics
- Date-range filtering for transactions and revenue.
- Interactive breakdown of **Active** and **Expired** memberships with direct member profile links.
- Interactive KPI cards for rapid navigation.

---

## 🛠️ Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Backend Framework** | Spring Boot 3.4.1 |
| **Language** | Java 21 (LTS) |
| **Security** | Spring Security 6 (BCrypt, Session Management) |
| **ORM / Data Access** | Spring Data JPA / Hibernate 6 |
| **Database** | MySQL 8.x (Active) / H2 In-Memory (Zero-config fallback) |
| **Template Engine** | Thymeleaf + Thymeleaf Layout Dialect |
| **Charts & Visuals** | Chart.js 4.4 |
| **Styling & Theme** | Custom Dark Cyberpunk Glassmorphism CSS |
| **Icons & Typography** | Google Material Symbols Rounded, Space Grotesk, Inter |
| **Build & Dependency Tool** | Apache Maven 3.9+ |

---

## 🚀 Getting Started

### 1. Prerequisites
- **JDK 21** or higher installed and configured in `PATH`.
- **MySQL Server 8.x** running on `localhost:3306`.
- **Apache Maven 3.9+** (or use the configured Maven path).

### 2. Database Configuration
By default, TRAINEX connects to MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gympro?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Abhinav
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

> **Note**: If you want to use the H2 in-memory database instead, switch `application.properties` to the H2 profile block.

### 3. Default Login Credentials
| Username | Password | Role | Description |
| :--- | :--- | :--- | :--- |
| `admin` | `admin123` | Administrator | Default System Admin |
| `flexzone_admin` | `flexzone_pass` | Administrator | Custom Gym Operator |

---

## 🏃 Running the Application

### Option A: Using Maven
```powershell
mvn spring-boot:run
```

### Option B: Build and Run JAR
```powershell
# 1. Package the application
mvn clean package -DskipTests

# 2. Run the packaged executable JAR
java -jar target/gympro-1.0.0.jar
```

Once started, open your browser and visit:
- **Application**: [http://localhost:8080](http://localhost:8080)
- **Login Page**: [http://localhost:8080/login](http://localhost:8080/login)

---

## 📂 Project Structure

```
gympro/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/gympro/
    │   │   ├── config/              # SecurityConfig, DataInitializer
    │   │   ├── controller/          # Dashboard, Member, Attendance, Subscription, Payment, etc.
    │   │   ├── entity/              # Member, User, Subscription, Payment, Attendance, Trainer, Plan
    │   │   ├── repository/          # Spring Data JPA Repositories
    │   │   └── service/             # Business Logic & Analytics Services
    │   └── resources/
    │       ├── application.properties
    │       ├── static/
    │       │   ├── css/             # Custom Glassmorphic Styling
    │       │   └── images/          # Logo & Atmospheric Gym Watermark
    │       └── templates/
    │           ├── layout.html      # Master Layout & Sidebar Navigation
    │           ├── dashboard.html   # Main KPI & Trend View
    │           ├── login.html       # Full-screen Particle Login
    │           ├── register.html    # Member Registration
    │           ├── members/         # List, Add/Edit Form, Profile View
    │           ├── subscriptions/   # Active/Expired Subscriptions & Form
    │           ├── payments/        # Ledger & Add Form
    │           ├── attendance/      # Attendance Tracker & Mark Form
    │           ├── trainers/        # Trainer Directory & Form
    │           ├── plans/           # Plans Catalog & Form
    │           └── reports.html     # Date-range Revenue & Membership Analytics
    └── test/                        # Unit & Integration Tests (JUnit 5, Mockito)
```

---

## 🧪 Running Automated Tests

Run the test suite:
```powershell
mvn test
```

---

## 📝 License
Developed as an Advanced Java Full-Stack System. Open for academic and commercial gym management use.
