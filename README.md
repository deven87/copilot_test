# Swagger Petstore API Testing Framework (Java + Maven)

This project provides a boilerplate for API testing using Java, Maven, JUnit 5, and RestAssured, targeting the Swagger Petstore API.

## Structure

- `src/test/java/petstore/` - API test cases (Java)
- `pom.xml` - Maven project configuration

## Getting Started

1. Install dependencies and run tests:
   ```sh
   mvn clean test
   ```

## Sample Tests
- GET /pet/findByStatus
- POST /pet
- PUT /pet
- DELETE /pet/{petId}

## Customize
- Add more tests in the `src/test/java/petstore/` folder.

---

This structure follows industry standards for maintainable API test automation in Java.
