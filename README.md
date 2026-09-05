# drone-delivery-service
> Spring Boot REST API for drone-based medication delivery service — drone registration, medication loading, and battery/state tracking. **This project serves as my submission for a technical exam on Software Engineer (Java) job application.

## Tech Stack

- **Language:** Java 17
- **Build tool:** Maven
- **Framework:** Spring Boot 4.1.1
- **Database:** H2 (in-memory)
- **Dependencies:**
    - Spring Web
    - Spring Data JPA
    - H2 Database
    - Spring Boot Starter Validation
    - Lombok

## Project Metadata

| Field | Value |
|---|---|
| Group | `com.assignment` |
| Artifact | `drone-delivery-service` |
| Base package | `com.assignment.drone-delivery-service` |
| Packaging | Jar |

## Prerequisites

- JDK 17+
- Maven 3.8+

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

The service will start on `http://localhost:8080` by default.


## API Endpoints

| Method | Endpoint                        | Description                                                                                                                                            |
|---|---------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST | `/api/drones/register`          | Register a new drone                                                                                                                                   |
| POST | `/api/drones/load`               | Load a registered drone with medication (drone carries 1 load at a time; reuses existing medication record if the medication already exists in the db) |
| GET | `/api/drones/loaded/{serialNumber}` | Check the loaded medication for a given drone, via the drone-medication mapping table                                                                  |
| GET | `/api/drones/available`             | Retrieve all drones currently in `IDLE` state (which are available for loading)                                                                        |
| GET | `/api/drones/{serialNumber}`        | Check a drone's information (includes the battery) by serial number                                                                                    |

All responses are wrapped in a `data` or `message`/`error` key depending on the endpoint. Not-found/invalid lookups return `400 Bad Request` with an `error` message.


## API Documentation & Testing References
The following reference materials are included inside the `docs` folder of this project for testing and validation:
- Postman Collection (filename: **Drone Delivery Service API.postman_collection**) - contains all the endpoint definitions mentioned above for manual API testing.
- Unit Testing Artifact (filename: **test-documentation**) - contains screenshots as evidence per testing scenario.

## License

This project is for evaluation purposes only as part of a job application process.
