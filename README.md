# Learning JPA, Hibernate & Spring Data — Hospital Management Project

This project is a hands-on learning exercise to understand how **JPA**, **Hibernate**, and **Spring Data** work together in a Spring Boot application. I built a Hospital Management System to practice entity mapping, relationships, repositories, and exposing data through a REST API.

## What I Learned

### 1. JPA Entity Mapping

JPA (Jakarta Persistence API) lets you map Java classes to database tables using annotations:

- `@Entity` — marks a class as a database table
- `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)` — auto-generated primary key
- `@Temporal(TemporalType.DATE)` — maps a `Date` field to a SQL `DATE` column
- `@Enumerated(EnumType.STRING)` — stores an enum as a readable string in the database instead of an integer

Example from my `Patient` entity:
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prenom;
    @Temporal(TemporalType.DATE)
    private Date DateOfBirth;
    private boolean malade;
}
```

### 2. Entity Relationships (The Core of JPA/Hibernate)

This is where I spent the most time. I learned how to map relationships between entities:

| Relationship | Example | Annotation |
|---|---|---|
| `@OneToMany` | A Patient has many Rendezvous | `@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)` |
| `@ManyToOne` | A Rendezvous belongs to one Patient | `@ManyToOne` |
| `@OneToOne` | A Rendezvous has one Consultation | `@OneToOne(mappedBy = "rendezvous")` |

The entity relationship diagram:
```
Patient  1 ──── *  Rendezvous  1 ──── 1  Consultation
Medecin  1 ──── *  Rendezvous
```

Key takeaways:
- **`mappedBy`** goes on the non-owning side (the side that does NOT have the foreign key column)
- **`FetchType.LAZY`** means related data is only loaded when you actually access it (better for performance)
- **`@JsonProperty(access = WRITE_ONLY)`** prevents infinite JSON loops when entities reference each other

### 3. Spring Data Repositories

Spring Data JPA removes the need to write SQL or DAO boilerplate. By just extending `JpaRepository`, you get CRUD operations for free:

```java
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByPrenom(String prenom);  // Spring generates the query from the method name!
}
```

What I get without writing any implementation:
- `save()`, `findById()`, `findAll()`, `delete()`, `count()` — all auto-generated
- **Derived queries** — Spring reads the method name `findByPrenom` and generates `SELECT * FROM patient WHERE prenom = ?`

### 4. Service Layer Pattern

I learned to separate business logic from data access using a service interface + implementation:

```java
public interface IHospitalService {
    Patient savePatient(Patient patient);
    Medecin saveMedecin(Medecin medecin);
    Rendezvous saveRDV(Rendezvous rendezvous);
    Consultation saveConsultation(Consultation consultation);
}
```

- **`@Service`** — registers the class as a Spring bean
- **`@Transactional`** — ensures database operations are atomic (all succeed or all rollback)
- **Constructor injection** — Spring injects repository dependencies automatically

### 5. REST Controller

Exposing data as a JSON API:

```java
@RestController
public class PatientRestController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public List<Patient> patientList() {
        return patientRepository.findAll();
    }
}
```

- `@RestController` = `@Controller` + `@ResponseBody` (returns JSON directly)
- `@GetMapping("/patients")` maps HTTP GET requests to this method

### 6. CommandLineRunner for Seeding Data

I used `CommandLineRunner` to populate the database with test data on startup — useful for testing without needing a persistent database:

```java
@Bean
CommandLineRunner start(IHospitalService hospitalService) {
    return args -> {
        Stream.of("MT", "Rs", "F1").forEach(name -> {
            Patient patient = new Patient();
            patient.setPrenom(name);
            hospitalService.savePatient(patient);
        });
        // ... create doctors, appointments, consultations
    };
}
```

### 7. Lombok to Reduce Boilerplate

Instead of writing getters, setters, constructors, `toString()`, `equals()`, and `hashCode()` manually:

- `@Data` — generates all of the above
- `@NoArgsConstructor` / `@AllArgsConstructor` — generates constructors

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 4.0.6 | Application framework |
| Spring Data JPA | Repository abstraction over JPA |
| Hibernate | ORM — translates Java objects to SQL |
| H2 Database | In-memory database for development/testing |
| Lombok | Code generation to reduce boilerplate |
| Maven | Build and dependency management |

## Project Structure

```
src/main/java/com/xtn/figo/
├── entities/           # JPA entities (mapped to DB tables)
│   ├── Patient.java
│   ├── Medecin.java
│   ├── Rendezvous.java
│   ├── Consultation.java
│   └── StatusRDV.java  # Enum: PENDING, APPROVED, REJECTED, DONE
├── repositories/       # Spring Data JPA interfaces
│   ├── PatientRepository.java
│   ├── MedecinRepository.java
│   ├── RendevousRepository.java
│   └── ConsultationRepository.java
├── service/            # Business logic layer
│   ├── IHospitalService.java
│   └── HospitalServiceImpl.java
├── web/                # REST API layer
│   └── PatientRestController.java
└── FigoApplication.java
```

## How to Run

```bash
git clone https://github.com/achrafbsibiss/figo.git
cd figo
mvn spring-boot:run
```

The app starts on port **8086**.

- Fetch patients: `http://localhost:8086/patients`
- H2 Console: `http://localhost:8086/h2-console` (JDBC URL: `jdbc:h2:mem:hospital`)
