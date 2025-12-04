# Review Service – Technical Documentation
 1. Overview
The Review Service is a Spring Boot 4 application responsible for managing book reviews within a library system.  
It exposes RESTful endpoints for creating, retrieving, updating, and deleting review entities.  
The service persists data using Spring Data JPA and a MySQL database.

 2. Technologies

- Java 17  
- Spring Boot 4.0.0  
- Spring Web  
- Spring Data JPA (Hibernate ORM 7.x)  
- Spring Validation  
- MySQL Connector/J  
- Lombok  
- JUnit 5 (unit testing)  
- Mockito (service testing)  
- MockMvc (API testing)  
- JPA Slice Tests (`@DataJpaTest`)  

 3. Project Structure
src/
├── main/java/com/example/reviewservice
│ ├── ReviewServiceApplication.java
│ ├── review/
│ │ ├── model/ (JPA entities)
│ │ ├── repository/ (JPA repositories)
│ │ └── service/ (business logic)
│ ├── web/
│ │ ├── controller/ (REST controllers)
│ │ └── dto/ (request DTOs)
│ └── exception/ (custom exceptions)
│
└── test/java/com/example/reviewservice
├── review/service/ (unit tests with Mockito)
├── review/repository/ (JPA slice tests)
└── web/controller/ (MockMvc standalone tests)

4. Database Configuration

The application uses a MySQL instance configured in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/reviews?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=yourPassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true


Notes

The project intentionally uses JUnit 5 instead of JUnit 6 to maintain full compatibility with IDE execution environments.

Full Spring context tests (@SpringBootTest) are optional and not required for core functionality validation.


