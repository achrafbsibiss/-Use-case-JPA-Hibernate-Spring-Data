# Hospital Management System

A Spring Boot application that manages hospital operations including patients, doctors (medecins), appointments (rendezvous), and consultations. Built with JPA/Hibernate and Spring Data to demonstrate entity relationships and RESTful API design.

## Technologies

- **Java 17**
- **Spring Boot 4.0.6**
- **Spring Data JPA** - Data access and repository layer
- **Hibernate** - ORM for entity mapping
- **H2 Database** - In-memory database for development
- **Lombok** - Reduces boilerplate code
- **Maven** - Build and dependency management

## Project Structure

```
src/main/java/com/xtn/figo/
├── entities/
│   ├── Patient.java          # Patient entity
│   ├── Medecin.java          # Doctor entity
│   ├── Rendezvous.java       # Appointment entity
│   ├── Consultation.java     # Consultation entity
│   └── StatusRDV.java        # Appointment status enum (PENDING, APPROVED, REJECTED, DONE)
├── repositories/
│   ├── PatientRepository.java
│   ├── MedecinRepository.java
│   ├── RendevousRepository.java
│   └── ConsultationRepository.java
├── service/
│   ├── IHospitalService.java       # Service interface
│   └── HospitalServiceImpl.java    # Service implementation
├── web/
│   └── PatientRestController.java  # REST API controller
└── FigoApplication.java            # Main application entry point
```

## Entity Relationships

```
Patient  1 ──── * Rendezvous
Medecin  1 ──── * Rendezvous
Rendezvous 1 ──── 1 Consultation
```

- A **Patient** can have multiple appointments
- A **Medecin** (doctor) can have multiple appointments
- Each **Rendezvous** (appointment) has one consultation
- Each **Consultation** contains a report and is linked to an appointment

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Setup and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/achrafbsibiss/figo.git
   cd figo
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application starts on port **8086**.

## Database Configuration

The application uses an in-memory H2 database with the following settings:

| Property | Value |
|---|---|
| Database URL | `jdbc:h2:mem:hospital` |
| H2 Console | Enabled |
| Server Port | `8086` |

Access the H2 console at: `http://localhost:8086/h2-console`

To connect, use JDBC URL `jdbc:h2:mem:hospital` with default credentials.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/patients` | List all patients |

## Sample Data

The application seeds sample data on startup:

- **3 Patients**: MT, Rs, F1
- **3 Doctors**: Aymane, Hassan, Hanane (with specialties Cardio or Generaliste)
- **1 Appointment**: linking the first patient and first doctor (status: PENDING)
- **1 Consultation**: with a report linked to the appointment

## Example Usage

After starting the application, fetch all patients:

```bash
curl http://localhost:8086/patients
```

Response:
```json
[
  {
    "id": 1,
    "prenom": "MT",
    "dateOfBirth": "2026-04-26",
    "malade": false,
    "rendezvous": [...]
  },
  ...
]
```
