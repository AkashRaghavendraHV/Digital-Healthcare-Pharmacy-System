# 🏥 Integrated Digital Healthcare & Online Pharmacy Management System

A full-stack Java web application providing end-to-end digital healthcare services — remote doctor consultations, electronic prescriptions, and online medicine ordering — all within a single unified platform.

> **Course:** UE23CS352B — Object Oriented Analysis & Design  
> **Institution:** PES University

---

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Design Patterns](#design-patterns)
- [Design Principles (SOLID)](#design-principles-solid)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Team Members](#team-members)

---

## 📖 About the Project

This platform digitizes the complete healthcare workflow — from booking a doctor's appointment and attending an e-consultation, to receiving an electronic prescription and ordering medicines online with home delivery. It serves four distinct user roles, each with a dedicated dashboard and feature set:

| Role | Capabilities |
|------|-------------|
| **Patient** | Search doctors, book appointments, attend e-consultations, view prescriptions, order medicines, make payments, track orders |
| **Doctor** | Manage schedule, conduct consultations, view patient history, generate e-prescriptions |
| **Pharmacist** | View incoming orders, verify prescriptions, manage inventory, dispatch orders |
| **Admin** | Manage all user accounts, maintain medicine catalog, view system-wide reports |

---

## ✨ Features

### Core Workflows

- **🔐 Authentication & Authorization** — Role-based registration, login, session management, and access control via Spring Security
- **📅 Appointment Booking** — Search doctors by specialization, view available slots, book appointments with double-booking prevention
- **💬 E-Consultation** — Chat/notes-based consultation interface with appointment lifecycle management (`REQUESTED → CONFIRMED → IN_PROGRESS → COMPLETED`)
- **📝 E-Prescription** — Doctors generate digital prescriptions with diagnosis, medicines (name, dosage, frequency, duration), and special instructions
- **🛒 Online Pharmacy** — Browse medicine catalog, add to cart, checkout with prescription validation
- **💳 Payment Processing** — Multiple payment methods (UPI, Credit Card, Debit Card, Net Banking) via Strategy pattern
- **📦 Order Management** — Full order lifecycle with state machine (`PLACED → VERIFIED → DISPATCHED → DELIVERED`)
- **📊 Admin Dashboard** — System-wide reports, user management, and medicine catalog CRUD

### Additional Highlights

- Server-side rendered responsive UI with **Thymeleaf + Bootstrap 5**
- Password hashing with **BCrypt**
- Input validation on all forms (server-side)
- Role-based dashboard redirection after login
- Prescription validity tracking and fulfillment status
- Inventory/stock management with dispatch-time decrement
- Seed data for quick demo setup (admin account, sample doctors, medicines)

---

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Language** | Java 17 | Core application language |
| **Backend Framework** | Spring Boot 3.2.4 | MVC architecture, dependency injection, auto-configuration |
| **Web MVC** | Spring MVC | Controller–Service–Repository layered architecture |
| **Template Engine** | Thymeleaf | Server-side HTML rendering |
| **Security** | Spring Security 6 | Authentication, authorization, session management |
| **ORM** | Spring Data JPA / Hibernate | Object-relational mapping |
| **Database** | MySQL 8 | Persistent relational data store |
| **Validation** | Jakarta Bean Validation | Server-side form validation |
| **CSS Framework** | Bootstrap 5 | Responsive UI components |
| **Build Tool** | Maven | Dependency management and build lifecycle |
| **Utility** | Lombok | Boilerplate reduction (getters, setters, constructors) |

---

## 🏗️ Architecture

The application follows the **MVC (Model–View–Controller)** architectural pattern enforced by Spring Boot:

```
┌─────────────────────────────────────────────────┐
│                   Browser                       │
│              (Thymeleaf HTML)                   │
└──────────────────┬──────────────────────────────┘
                   │ HTTP Request/Response
┌──────────────────▼──────────────────────────────┐
│              Controller Layer                   │
│   @Controller — handles routes & request logic  │
├─────────────────────────────────────────────────┤
│               Service Layer                     │
│   @Service — contains business logic            │
├─────────────────────────────────────────────────┤
│             Repository Layer                    │
│   @Repository — Spring Data JPA interfaces      │
├─────────────────────────────────────────────────┤
│                  MySQL DB                       │
│           (healthcare_db schema)                │
└─────────────────────────────────────────────────┘
```

---

## 🎨 Design Patterns

The project demonstrates **5 design patterns** spanning Creational, Structural, and Behavioral categories:

### Primary Patterns

| # | Pattern | Category | Implementation | Description |
|---|---------|----------|---------------|-------------|
| 1 | **Factory Method** | Creational | `UserFactory` | Creates the correct `User` subclass (`Patient`, `Doctor`, `Pharmacist`) based on the selected role during registration |
| 2 | **Builder** | Creational | `AppointmentBuilder` | Constructs complex `Appointment` objects with optional fields (consultation type, notes, follow-up flag) |
| 3 | **Observer** | Behavioral | `PrescriptionObserver` | When a doctor completes a prescription, observers (`PatientNotificationObserver`, `PharmacyNotificationObserver`) are automatically notified |
| 4 | **Strategy** | Behavioral | `PaymentStrategy` | Different payment method implementations (`UpiPaymentStrategy`, `CardPaymentStrategy`) behind a common interface, selected at runtime |
| 5 | **State** | Behavioral | `OrderState` | Manages order lifecycle transitions through well-defined states (`PlacedState` → `VerifiedState` → `DispatchedState` → `DeliveredState` / `RejectedState`) |

### Additional Patterns

| Pattern | Category | Implementation | Description |
|---------|----------|---------------|-------------|
| **Facade** | Structural | `PatientDashboardFacade` | Simplifies access to orders, prescriptions, and appointments behind a unified interface |
| **Singleton** | Creational | `AdminReportGenerator` | Single centralized instance for generating system-wide reports |
| **MVC** | Architectural | Spring MVC (framework-enforced) | Separation of concerns across Controllers, Services, and Repositories |

---

## 📐 Design Principles (SOLID)

| # | Principle | Applied Where |
|---|-----------|--------------|
| 1 | **Single Responsibility (SRP)** | `AppointmentService` handles only appointment logic; `UserService` handles only user/auth logic — each class has one reason to change |
| 2 | **Open/Closed (OCP)** | New notification channels can be added as new `PrescriptionObserver` implementations without modifying `PrescriptionService` |
| 3 | **Liskov Substitution (LSP)** | `Patient`, `Doctor`, `Pharmacist` are all substitutable wherever `User` is expected (authentication, profile display, etc.) |
| 4 | **Dependency Inversion (DIP)** | `OrderService` depends on the `PaymentStrategy` abstraction, not on concrete `UpiPaymentStrategy` or `CardPaymentStrategy` implementations |

---

## 📁 Project Structure

```
healthcare-system/
├── pom.xml                                    # Maven build configuration
├── src/main/java/com/healthcare/
│   ├── HealthcareApplication.java             # Spring Boot entry point
│   │
│   ├── config/                                # Configuration classes
│   │   ├── SecurityConfig.java                #   Spring Security configuration
│   │   ├── SingletonConfig.java               #   Singleton bean configuration
│   │   └── DataSeeder.java                    #   Initial seed data loader
│   │
│   ├── model/                                 # JPA Entity classes
│   │   ├── User.java                          #   Abstract base user entity
│   │   ├── Patient.java                       #   Patient (extends User)
│   │   ├── Doctor.java                        #   Doctor (extends User)
│   │   ├── Pharmacist.java                    #   Pharmacist (extends User)
│   │   ├── Admin.java                         #   Admin (extends User)
│   │   ├── Appointment.java                   #   Appointment entity
│   │   ├── Prescription.java                  #   E-Prescription entity
│   │   ├── PrescriptionItem.java              #   Individual medicine in a prescription
│   │   ├── Medicine.java                      #   Medicine catalog entry
│   │   ├── Order.java                         #   Pharmacy order entity
│   │   ├── OrderItem.java                     #   Individual item in an order
│   │   └── Payment.java                       #   Payment transaction entity
│   │
│   ├── repository/                            # Spring Data JPA Repositories
│   │   ├── UserRepository.java
│   │   ├── PatientRepository.java
│   │   ├── DoctorRepository.java
│   │   ├── PharmacistRepository.java
│   │   ├── AppointmentRepository.java
│   │   ├── PrescriptionRepository.java
│   │   ├── MedicineRepository.java
│   │   ├── OrderRepository.java
│   │   └── PaymentRepository.java
│   │
│   ├── service/                               # Business logic layer
│   │   ├── UserService.java                   #   Auth, registration, profile
│   │   ├── AppointmentService.java            #   Appointment CRUD & lifecycle
│   │   ├── PrescriptionService.java           #   E-Prescription management
│   │   ├── OrderService.java                  #   Order processing & state mgmt
│   │   ├── PatientDashboardFacade.java        #   Facade for patient dashboard
│   │   └── AdminReportGenerator.java          #   Singleton report generator
│   │
│   ├── controller/                            # MVC Controllers
│   │   ├── AuthController.java                #   Login, register, logout
│   │   ├── DashboardController.java           #   Role-based dashboard routing
│   │   ├── ProfileController.java             #   View/edit user profile
│   │   ├── AppointmentController.java         #   Patient appointment actions
│   │   ├── DoctorController.java              #   Doctor dashboard & consultations
│   │   ├── OrderController.java               #   Cart, checkout, order tracking
│   │   ├── PharmacistController.java          #   Order verification & dispatch
│   │   └── AdminController.java               #   User mgmt, medicines, reports
│   │
│   ├── dto/                                   # Data Transfer Objects
│   │   ├── RegistrationDTO.java
│   │   ├── PrescriptionFormDTO.java
│   │   ├── PrescriptionItemDTO.java
│   │   ├── CartDTO.java
│   │   ├── CartItemDTO.java
│   │   └── OrderCheckoutDTO.java
│   │
│   ├── enums/                                 # Status & type enumerations
│   │   ├── Role.java                          #   PATIENT, DOCTOR, PHARMACIST, ADMIN
│   │   ├── AppointmentStatus.java             #   REQUESTED → COMPLETED / CANCELLED
│   │   ├── OrderStatus.java                   #   PLACED → DELIVERED / REJECTED
│   │   ├── PaymentMode.java                   #   UPI, CREDIT_CARD, DEBIT_CARD, NET_BANKING
│   │   └── PaymentStatus.java                 #   PENDING, SUCCESS, FAILED
│   │
│   ├── factory/                               # Factory Method pattern
│   │   └── UserFactory.java
│   │
│   ├── builder/                               # Builder pattern
│   │   └── AppointmentBuilder.java
│   │
│   ├── observer/                              # Observer pattern
│   │   ├── PrescriptionObserver.java          #   Observer interface
│   │   ├── PatientNotificationObserver.java   #   Notifies patient
│   │   └── PharmacyNotificationObserver.java  #   Notifies pharmacist queue
│   │
│   ├── strategy/                              # Strategy pattern
│   │   ├── PaymentStrategy.java               #   Strategy interface
│   │   ├── UpiPaymentStrategy.java
│   │   └── CardPaymentStrategy.java
│   │
│   └── state/                                 # State pattern
│       ├── OrderState.java                    #   State interface
│       ├── OrderContext.java                  #   Context holder
│       ├── PlacedState.java
│       ├── VerifiedState.java
│       ├── DispatchedState.java
│       ├── DeliveredState.java
│       └── RejectedState.java
│
├── src/main/resources/
│   ├── application.properties                 # App configuration
│   └── templates/                             # Thymeleaf HTML templates
│       ├── layout.html                        #   Common page layout
│       ├── login.html                         #   Login page
│       ├── register.html                      #   Registration page
│       ├── profile.html                       #   User profile page
│       ├── admin/
│       │   ├── dashboard.html                 #   Admin summary & stats
│       │   ├── users.html                     #   User management
│       │   └── medicines.html                 #   Medicine catalog CRUD
│       ├── doctor/
│       │   ├── dashboard.html                 #   Doctor home & today's appointments
│       │   ├── appointments.html              #   Appointment management
│       │   └── prescribe.html                 #   E-Prescription creation form
│       ├── patient/
│       │   ├── dashboard.html                 #   Patient home
│       │   ├── doctors.html                   #   Search & book doctors
│       │   ├── appointments.html              #   My appointments
│       │   ├── prescriptions.html             #   E-Prescription history
│       │   ├── orderForm.html                 #   Cart & checkout
│       │   └── orders.html                    #   Order tracking
│       └── pharmacist/
│           └── dashboard.html                 #   Pharmacist order queue
```

---

## 🗄️ Database Schema

The system uses **MySQL** with JPA/Hibernate ORM. Tables are auto-generated via `spring.jpa.hibernate.ddl-auto=update`.

### Entity Relationship Overview

```
User (Abstract)
  ├── Patient ──< Appointment >── Doctor
  │       │            │
  │       │            └── Prescription ──< PrescriptionItem
  │       │                     │
  │       └──< Order ──────────┘
  │              │
  │              ├──< OrderItem >── Medicine
  │              ├── Payment
  │              └── Pharmacist
  └── Admin
```

### Key Entities

| Entity | Description | Key Fields |
|--------|-------------|------------|
| `User` | Abstract base class (JOINED inheritance) | `userId`, `name`, `email`, `password`, `phone`, `role` |
| `Patient` | Extends User | `address`, `age`, `gender` |
| `Doctor` | Extends User | `specialization`, `experience`, `availableSlots` |
| `Pharmacist` | Extends User | `pharmacyName`, `pharmacyAddress`, `licenseNumber` |
| `Appointment` | Doctor-patient meeting | `date`, `time`, `status`, `consultationNotes` |
| `Prescription` | E-prescription issued post-consultation | `diagnosis`, `instructions`, `validUntil`, `isFulfilled` |
| `Medicine` | Master catalog entry | `name`, `category`, `price`, `stockQuantity` |
| `Order` | Medicine order from pharmacy | `totalAmount`, `status`, `deliveryAddress` |
| `Payment` | Payment transaction | `amount`, `mode`, `status`, `transactionRef` |

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher — [Download](https://adoptium.net/)
- **Maven 3.8+** — [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** — [Download](https://dev.mysql.com/downloads/)

### Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd ooad_proj
   ```

2. **Create the MySQL database**

   ```sql
   CREATE DATABASE healthcare_db;
   ```

3. **Configure database credentials**

   Edit `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

4. **Build the project**

   ```bash
   mvn clean install
   ```

5. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

6. **Open in browser**

   Navigate to: [http://localhost:8080](http://localhost:8080)

> **Note:** On first launch, the `DataSeeder` class automatically populates the database with a default admin account, sample doctors, and sample medicines for demo purposes.

---

## 💻 Usage

### Default Roles & Navigation

After launching, users can register with one of the following roles:

| Role | Registration | Dashboard URL |
|------|-------------|---------------|
| Patient | Self-registration | `/patient/dashboard` |
| Doctor | Self-registration | `/doctor/dashboard` |
| Pharmacist | Self-registration | `/pharmacist/dashboard` |
| Admin | Pre-seeded account | `/admin/dashboard` |

### Typical Workflow

```
1. Patient registers & logs in
2. Patient searches for a doctor → books an appointment
3. Doctor logs in → accepts the appointment
4. Doctor conducts consultation → generates e-prescription
5. Patient views prescription → orders medicines → makes payment
6. Pharmacist verifies prescription → dispatches order
7. Order delivered to patient
```

### Key Pages

| URL | Description |
|-----|-------------|
| `/login` | User login |
| `/register` | New user registration |
| `/profile` | View & edit profile |
| `/patient/doctors` | Search & book doctors |
| `/patient/appointments` | View patient appointments |
| `/patient/prescriptions` | View prescription history |
| `/patient/orders` | Track medicine orders |
| `/doctor/dashboard` | Doctor's daily appointments |
| `/doctor/appointments` | Manage all appointments |
| `/pharmacist/dashboard` | Pending order queue |
| `/admin/dashboard` | System stats & reports |
| `/admin/users` | Manage user accounts |
| `/admin/medicines` | Medicine catalog CRUD |

---

---

## 👥 Team Members

| Name | USN |
|------|-----|
| **Akash K** | PES1UG23CS043 |
| **Akshay R** | PES1UG23CS049 |
| **Amar S Nandi** | PES1UG23CS057 |
| **Amogh Shetty** | PES1UG23CS060 |

---

## 📄 License

This project is developed as part of the academic coursework for **UE23CS352B — Object Oriented Analysis & Design** at PES University. It is intended for educational purposes only.

---

<p align="center">
  Made with ❤️ by Team PES1UG23CS_043_049_057_060
</p>
