# 🛡️ Secure Notes API

A secure, production-grade Spring Boot REST API that allows users to register, login, and manage their personal notes securely with **JWT authentication**, **role-based access**, **custom response handling**, and **exception validation**.

---

## ✨ Features

- 🔐 **User Authentication** (JWT-based)
- 📒 **CRUD Notes** (Create, Read, Update, Delete)
- 👥 **Role-Based Access** (User & Admin)
- 🎯 **Custom DTOs & JSON Responses**
- 🛠️ **Input Validation + Exception Handling**
- 📂 **Layered Architecture** (Controller → Service → Repository)
- 📘 **Swagger API Documentation**
- 🧪 **Postman-tested APIs**

---

## 🚀 Tech Stack

| Layer       | Technology               |
|-------------|---------------------------|
| Backend     | Spring Boot               |
| Security    | Spring Security + JWT     |
| ORM & DB    | Spring Data JPA + MySQL   |
| Validation  | Hibernate Validator (JSR-380) |
| Docs        | Swagger (springdoc-openapi) |
| Testing     | Postman (manual testing)  |

---

## 🧰 Project Structure

SecureNotesAPI/

├── controller/
│ └── AuthController, NoteController

├── service/
│ └── AuthService, NoteService

├── model/
│ └── User, Note

├── dto/
│ └── RegisterRequest, AuthResponse, NoteDTO, etc.

├── repository/
│ └── UserRepository, NoteRepository

├── config/
│ └── JwtService, JwtAuthFilter, SecurityConfig

├── exception/
│ └── GlobalExceptionHandler, ResourceNotFound
└── SecureNotesApiApplication.java

---

## 📬 API Endpoints

### 🔐 Auth Endpoints (`/api/auth`)
| Method | Endpoint         | Description             |
|--------|------------------|-------------------------|
| POST   | `/register`      | Register new user       |
| POST   | `/login`         | Login & get JWT token   |

### 📒 Notes Endpoints (`/api/notes`)
| Method | Endpoint              | Description              | Auth Required |
|--------|-----------------------|--------------------------|----------------|
| GET    | `/api/notes`          | Get user’s notes         | ✅ Yes |
| GET    | `/api/notes/{id}`     | Get single note by ID    | ✅ Yes |
| POST   | `/api/notes`          | Create a note            | ✅ Yes |
| PUT    | `/api/notes/{id}`     | Update a note            | ✅ Yes |
| DELETE | `/api/notes/{id}`     | Delete a note            | ✅ Yes |

> 🔒 Notes are **user-specific** — users can only access their own notes.

---

## 🧪 How to Test in Postman

1. **Register**
   - POST `/api/auth/register`
   - Body: `{ "username": "abc", "email": "abc@example.com", "password": "1234" }`

2. **Login**
   - POST `/api/auth/login`
   - Body: `{ "email": "abc@example.com", "password": "1234" }`
   - 🔑 Copy the returned **JWT token**

3. **Set Authorization in Postman**
   - In all note-related APIs, set header:  
     `Authorization: Bearer <your_token>`

4. **Use Notes Endpoints**
   - Test `/api/notes`, `/api/notes/{id}` for CRUD operations.

---

## 🧱 Setup & Run Locally

### 1. Clone the repo
```bash
git clone https://github.com/Khushi0389/SpringSecureNotes-JWT.git
cd SecureNotesAPI
```
### 2. Configure application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/notes_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

### 3. Build and Run
./mvnw spring-boot:run

### 4. Access Swagger Docs
URL: http://localhost:8080/swagger-ui.html (if enabled)

### 🛡️ Role-Based Access

Role	Access Details
USER	Access only own notes
ADMIN	Access all notes (extendable)
