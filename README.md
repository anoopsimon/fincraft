
# 🚀 **FinCraft - Learning Project**

## **1. About the Project**
This is a simple backend project designed to help understand the basics of building REST APIs and working with databases in **Spring Boot**. The application provides endpoints to manage user data, such as creating and fetching user records.  

**Note**: This is not a production-grade project; it is purely for **learning purposes**.

---

## 🛠️ **Tech Stack**
- **Backend**: Java 17 with Spring Boot 3.4.0
- **Database**: H2 (in-memory database)
- **Build Tool**: Gradle
- **Testing**: Spring Boot Starter Test (JUnit 5)

---

## 📦 **Dependencies Used**
Here are the key dependencies used in this project:

- **Spring Boot Starter Web**: For building REST APIs.
- **Spring Boot Starter Data JPA**: For interacting with the database.
- **H2 Database**: In-memory database for development and testing.
- **Spring Boot Starter Test**: For unit and integration testing.
- **Lombok**: To reduce boilerplate code (e.g., getters, setters, constructors).

**`build.gradle` snippet**:
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    runtimeOnly 'com.h2database:h2'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

---

## 🖥️ **Backend Details**
The backend is built using **Spring Boot** and includes the following functionalities:
- **REST API Endpoints**:
  - `GET /api/users`: Fetch all users.
  - `GET /api/users/{id}`: Fetch a user by ID.
  - `POST /api/users`: Create a new user.

- **Data Model**:
  A `User` entity with the following fields:
  - `id` (Auto-generated)
  - `name`
  - `email`

---

## 🗄️ **How to Connect to H2 Database**

### H2 Console:
You can access the H2 database console using the following steps:
1. Start the application.
2. Open your browser and navigate to:
   ```
   http://localhost:8080/h2-console
   ```

3. Use the following credentials:
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **Username**: `sa`
   - **Password**: (leave blank)

![H2](./docs/static/img/h2-db-console.png)

### Sample Query to Verify Data:
Once connected, you can execute SQL queries to view the `user` table:
```sql
SELECT * FROM "user";
```

---

## 🚀 **How to Run the Project**
1. Clone the repository.
2. Open the project in IntelliJ IDEA or any other IDE.
3. Build and run the project:
   ```bash
   ./gradlew bootRun
   ```
4. Test the endpoints using tools like **Postman**, **IntelliJ HTTP Client**, or a browser.

---

## 📊 **Sample Data**
The project loads initial user data from an SQL file (`config/data.sql`) at startup. Example records:
- `John Doe (john.doe@example.com)`
- `Jane Doe (jane.doe@example.com)`
- Additional realistic users included.

---

## 🌐 **API Endpoints**

| Method | Endpoint            | Description              |
|--------|---------------------|--------------------------|
| GET    | `/api/users`        | Fetch all users          |
| GET    | `/api/users/{id}`   | Fetch a user by ID       |
| POST   | `/api/users`        | Create a new user        |

---

## 🤝 **Contributing**
This is a learning project, so feel free to explore, modify, and experiment.

---

### 📧 **Contact**
For queries or suggestions, feel free to contact:  
**Name**: Anoop Simon  
**Email**: anoop.simon@example.com

---
