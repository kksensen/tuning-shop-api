# 🏎️ Tuning Shop API - Issues Plan

**Project Overview:**
A lightweight RESTful API to manage cars and performance parts. The focus is on registering factory-spec cars, applying modifications (tuning) via a parts catalog, and calculating the actual horsepower gain based on backend business rules.

---

## 🏃 Sprint 1: Setup and Base Infrastructure
**Objective:** Establish the project foundation, ensuring the application boots up and connects to the database.

### Issue 1: Initialize Spring Boot project and configure dependencies
- **Description:** Generate the initial project structure and organize the tooling.
- **Acceptance Criteria:**
  - [x] Project generated via Spring Initializr.
  - [x] Dependencies added to `pom.xml`: Spring Web, Spring Data JPA, MySQL Driver.
  - [x] Application compiles and the `main` method runs without errors.

### Issue 2: Configure MySQL Connection
- **Description:** Define the connection properties for the local database.
- **Acceptance Criteria:**
  - [ ] Schema `tuning_db` created manually in the local MySQL server.
  - [ ] `application.properties` configured with `spring.datasource.url`, `username`, and `password`.
  - [ ] Set `spring.jpa.hibernate.ddl-auto=update` to speed up local development.

### Issue 3: Structure Layered Architecture Packages
- **Description:** Create the base directories to keep the code organized and cohesive.
- **Acceptance Criteria:**
  - [ ] Create `controllers` package (REST presentation layer).
  - [ ] Create `services` package (business rules).
  - [ ] Create `repositories` package (data access layer).
  - [ ] Create `entities` package (domain models mapped to the database).

---

## 🏃 Sprint 2: Domain Modeling and Persistence
**Objective:** Create the entities, their relationships (ORM), and the repositories for database communication.

### Issue 4: Configure `Part` Entity (Tuning Part)
- **Description:** Map the class representing the available parts catalog.
- **Acceptance Criteria:**
  - [ ] Class annotated with `@Entity`.
  - [ ] Attributes defined: `id` (PK, Auto-generated), `name`, `type` (e.g., 'Turbo', 'Filter'), `horsepowerBonus`.
  - [ ] Constructors, Getters, and Setters generated.

### Issue 5: Configure `Car` Entity
- **Description:** Map the car class, representing its factory state.
- **Acceptance Criteria:**
  - [x] Class annotated with `@Entity`.
  - [x] Attributes defined: `id` (PK, Auto-generated), `brand`, `model`, `baseHorsepower`.
  - [x] Constructors, Getters, and Setters generated.

### Issue 6: Establish N:M Relationship between `Car` and `Part`
- **Description:** Map the database rule where one car can receive multiple parts, and the same part (from the catalog) can be installed in multiple cars.
- **Acceptance Criteria:**
  - [x] Add a `List<Part>` inside the `Car` entity.
  - [x] Annotate the list with `@ManyToMany`.
  - [x] Use `@JoinTable` to create a clean junction table (e.g., `car_parts`) defining foreign keys.

### Issue 7: Create Spring Data JPA Repositories
- **Description:** Provide the standard integrated CRUD methods for our entities.
- **Acceptance Criteria:**
  - [ ] Create `PartRepository` interface extending `JpaRepository`.
  - [ ] Create `CarRepository` interface extending `JpaRepository`.

---

## 🏃 Sprint 3: Business Rules and Services
**Objective:** Implement use cases, isolating all business logic (the application's "core") away from the Controllers.

### Issue 8: Create `PartService` (Catalog)
- **Description:** Service to create new parts and list available items in the shop.
- **Acceptance Criteria:**
  - [ ] Class annotated with `@Service` and `PartRepository` injected.
  - [ ] Method to save a new part.
  - [ ] Method to fetch a part by ID.

### Issue 9: Create `CarService` (Base Garage)
- **Description:** Service to register new cars.
- **Acceptance Criteria:**
  - [ ] Class annotated with `@Service` and `CarRepository` injected.
  - [ ] Method to save a new car.
  - [ ] Method to fetch a car by ID (Throwing an exception if not found).

### Issue 10: Implement Installation Logic (Tuning)
- **Description:** Core system rule: add a catalog part to the specified car.
- **Acceptance Criteria:**
  - [ ] Create method `installPart(Long carId, Long partId)` in `CarService`.
  - [ ] Validate the existence of both car and part.
  - [ ] Rule: A car cannot install the same part twice. (If the part is already in the list, block the action).
  - [ ] Add the part to the car's list and save the car.

### Issue 11: Implement Dynamic Performance Calculation
- **Description:** Dynamically calculate the final horsepower based on the applied modifications.
- **Acceptance Criteria:**
  - [ ] Create logic (in the service or car entity) that sums the `baseHorsepower` with the `horsepowerBonus` of all installed parts.
  - [ ] Return the total updated horsepower value.

---

## 🏃 Sprint 4: API Exposure (Controllers)
**Objective:** Create REST endpoints so clients can interact with the system and consume business rules.

### Issue 12: Create `PartController`
- **Description:** Routes for shop catalog administration.
- **Acceptance Criteria:**
  - [ ] `@RestController` mapped to `/api/parts`.
  - [ ] `POST` endpoint to register a new part.
  - [ ] Return HTTP Status 201 (Created).

### Issue 13: Create Base Endpoints in `CarController`
- **Description:** Routes for vehicle viewing and registration.
- **Acceptance Criteria:**
  - [ ] `@RestController` mapped to `/api/cars`.
  - [ ] `POST` endpoint to register a car.
  - [ ] `GET /{id}` endpoint to view car details.

### Issue 14: Create Tuning Endpoint in `CarController`
- **Description:** Specific business action route (install part).
- **Acceptance Criteria:**
  - [ ] `POST /{carId}/tuning/{partId}` endpoint.
  - [ ] Must invoke the installation logic built in Issue 10.
  - [ ] Return HTTP 200 (OK) on success.

### Issue 15: Create Performance Report Endpoint
- **Description:** Route to provide the car's "Dyno Sheet" — its total performance status after upgrades.
- **Acceptance Criteria:**
  - [ ] `GET /{id}/performance` endpoint.
  - [ ] The JSON response must contain car data, a list of installed parts names, and the final horsepower calculation (developed in Issue 11).

### Issue 16: Basic Global Exception Handling (`ControllerAdvice`)
- **Description:** Intercept application errors to prevent Java stack traces from reaching the client.
- **Acceptance Criteria:**
  - [ ] Create a class annotated with `@RestControllerAdvice`.
  - [ ] Handle scenarios where Car or Part are not found (Return HTTP 404).
  - [ ] Handle business rule failures, like "Part already installed" (Return HTTP 400 or 409).