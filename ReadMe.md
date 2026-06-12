# 📝 Todo CRUD Application

A full-stack **Todo Management System** built with **Spring Boot + React.js**, featuring JWT Authentication, Role-Based Access Control, and RESTful APIs.

---

## 🛠️ Tech Stack

### Backend
| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 3.3.5 |
| Spring Security | 6.x |
| JWT (jjwt) | 0.11.5 |
| MySQL | 8.x |
| Lombok | Latest |
| Springdoc OpenAPI | 2.3.0 |

### Frontend
| Technology | Version |
|---|---|
| React.js | 18.x |
| Axios | Latest |
| Bootstrap | 5.x |

---

## ⚙️ Prerequisites

- Java 21+
- Node.js 18+
- MySQL 8+
- Maven 3.8+

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/MrAjayGangwar2001/Job_Assignment.git
cd Job_Assignment/CRUD
```

### 2. MySQL Database Setup

```sql
CREATE DATABASE crud;
```

### 3. Backend Setup

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crud
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=MySuperSecretKeyForJWTTokenGenerationThatIsLongEnough123456
jwt.expiration=86400000
```

Run the backend:

```bash
mvn clean install
mvn spring-boot:run
```

Backend starts at: `http://localhost:8080`

### 4. Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend starts at: `http://localhost:5173`

---

## 📁 Project Structure

CRUD/
├── src/main/java/com/Assesment/CRUD/
│   ├── Config/
│   │   ├── PasswordConfig.java       # BCrypt Bean
│   │   ├── SecurityConfig.java       # Spring Security + JWT Filter
│   │   └── SwaggerConfig.java        # OpenAPI Configuration
│   ├── Controller/
│   │   ├── UserController.java       # Auth APIs (signup/login)
│   │   ├── TodoController.java       # CRUD APIs
│   │   └── AdminController.java      # Admin only APIs
│   ├── Dto/
│   │   ├── LoginDto.java
│   │   ├── SignupDto.java
│   │   └── TodoDto.java
│   ├── Exception/
│   │   ├── GlobalExceptionHandler.java
│   │   └── TodoNotFoundException.java
│   ├── Model/
│   │   ├── UserModel.java            # Role: USER / ADMIN
│   │   └── TodoModel.java
│   ├── Repository/
│   │   ├── UserRepo.java
│   │   └── TodoRepo.java
│   ├── Response/
│   │   ├── UserResponseDto.java
│   │   ├── TodoResponse.java
│   │   └── ErrorResponse.java
│   ├── Security/
│   │   ├── JwtUtil.java              # Token generate/validate
│   │   └── JwtAuthFilter.java        # JWT request filter
│   └── Service/
│       ├── UserService.java
│       └── TodoService.java
└── frontend/
├── src/
│   ├── api/api.js                # Axios instance + interceptor
│   ├── components/
│   │   ├── Login.jsx
│   │   ├── Signup.jsx
│   │   ├── TodoList.jsx
│   │   └── Navbar.jsx
│   └── App.jsx
└── package.json

---

## 🔐 API Endpoints

### Authentication (Public)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/auth/home` | Public home page |
| POST | `/api/v1/auth/signup` | Register new user |
| POST | `/api/v1/auth/login` | Login + get JWT token |

### Todo APIs (JWT Required)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/todo/allTodo` | Get all todos |
| POST | `/api/v1/todo/create` | Create new todo |
| PATCH | `/api/v1/todo/{id}` | Update todo by ID |
| DELETE | `/api/v1/todo/{id}` | Delete todo by ID |

### Admin APIs (ADMIN Role Required)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/admin/users` | Get all registered users |

---

## 🧪 API Testing

### Swagger UI

http://localhost:8080/swagger-ui/index.html

### Sample Requests

**Register User:**
```json
POST /api/v1/auth/signup
{
  "userName": "Ajay",
  "email": "ajay@example.com",
  "password": "password123"
}
```

**Register Admin:**
```json
POST /api/v1/auth/signup
{
  "userName": "Admin",
  "email": "admin@example.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

**Login:**
```json
POST /api/v1/auth/login
{
  "email": "ajay@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "userId": 1,
  "userName": "Ajay",
  "email": "ajay@example.com",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "USER"
}
```

**Create Todo (JWT required):**

Header: Authorization: Bearer <token>
POST /api/v1/todo/create
{
"title": "Complete Assignment",
"description": "Build full stack CRUD app"
}

---

## 🔒 Security Features

- ✅ BCrypt password hashing
- ✅ JWT token authentication (24hr expiry)
- ✅ Role-based access control (USER / ADMIN)
- ✅ Protected REST endpoints
- ✅ Input validation (`@Valid`, `@NotBlank`, `@Email`)
- ✅ Global exception handling

---

## 📈 Scalability Note

### Current Architecture
Single Spring Boot monolith with MySQL — suitable for small to medium workloads.

### How This App Can Scale

#### 1. Microservices Architecture
Split into independent services:

Auth Service      → handles login, signup, JWT
Todo Service      → handles CRUD operations
Admin Service     → handles admin operations
API Gateway       → single entry point (Spring Cloud Gateway)

Each service deploys independently — one service failure won't crash others.

#### 2. Caching (Redis)
```java
// Frequently fetched todos cache mein store karo
@Cacheable("todos")
public List<TodoDto> getAllTodo() { ... }
```
- Database hits drastically kam honge
- Response time milliseconds mein aayega

#### 3. Load Balancing

User Request
↓
Load Balancer (Nginx / AWS ALB)
↓
[Instance 1] [Instance 2] [Instance 3]

- Multiple app instances run karenge
- Traffic evenly distribute hoga
- Zero downtime deployment possible

#### 4. Database Scaling
- **Read Replicas** — read queries alag server pe
- **Connection Pooling** — HikariCP already configured
- **Indexing** — email column already unique indexed

#### 5. Cloud Deployment (AWS)

EC2          → Backend hosting
RDS          → Managed MySQL
ElastiCache  → Redis caching
S3 + CloudFront → Frontend hosting
ALB          → Load balancer

#### 6. Containerization (Docker)
```dockerfile
FROM eclipse-temurin:21-jre
COPY target/CRUD-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```
```yaml
# docker-compose.yml
services:
  backend:
    build: .
    ports:
      - "8080:8080"
  mysql:
    image: mysql:8
    environment:
      MYSQL_DATABASE: crud
```

---

## 👨‍💻 Author

**Ajay Gangwar**
- Portfolio: [portfolio-ajay-gangwar.vercel.app](https://portfolio-ajay-gangwar.vercel.app)
- GitHub: [@MrAjayGangwar2001](https://github.com/MrAjayGangwar2001)