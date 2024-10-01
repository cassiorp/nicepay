# Nicepay

## Technologies Used

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com)
- [OpenAPI](https://swagger.io/specification/)
- [Gradle](https://gradle.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/reference/cli/docker/compose/)

## Development Overview

This challenge was implemented using Spring Boot, Gradle, and MongoDB as the database. These technologies were chosen due to familiarity, aiming for faster and more efficient development.

### Usage Instructions

To run the project locally, a MongoDB instance is required. It is recommended to have Docker and Docker Compose installed so that you can use the provided `docker-compose` file to spin up the database with the following command:

1. Clone the repository, navigate to the `nicepay` folder, and execute:
```bash
docker-compose up
```

2. Run the project in your preferred IDE, and access the Swagger UI at `http://localhost:8080`.

## API

| Endpoint                                | Method | Description                     |
|-----------------------------------------|--------|---------------------------------|
| `/api/v1/user`                          | POST   | Creates a new user.             |
| `/api/v1/user`                          | GET    | Get users.               |
| `/api/v1/user/{id}/deposit/{amount}`    | PATCH  | Deposits into the user's wallet. |
| `/api/v1/transfer`                      | POST   | Initiates a transfer.           |
| `/api/v1/transfer`                      | GET    | Retrieves transfer records.     |
