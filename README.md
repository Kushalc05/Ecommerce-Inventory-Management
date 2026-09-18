# E-Commerce & Inventory Management System

A full-stack e-commerce and inventory management application built using Java and Spring Boot.

The system provides product and inventory management, user authentication, role-based authorization, shopping cart functionality, checkout, and order management through RESTful APIs and a responsive web interface.

---

## 🚀 Features

### Authentication & Authorization

- User registration and login
- BCrypt password hashing
- JWT-based authentication
- Role-based authorization
- ADMIN and CUSTOMER roles
- Protected REST endpoints

### Product & Inventory Management

- Add products
- View available products
- Delete products
- SKU-based product identification
- Duplicate SKU handling
- Automatically increases stock when an existing SKU is added
- Product price and stock validation

### Shopping Cart

- Add products to cart
- View cart
- Update cart item quantity
- Remove items from cart
- Calculate item subtotals
- Calculate cart total
- Cart checkout

### Orders

- Place single-product orders
- Place multi-product orders
- Checkout cart
- Automatic stock deduction
- Insufficient-stock validation
- View customer's order history

### Validation & Error Handling

- Request validation using Jakarta Bean Validation
- Centralized exception handling
- Meaningful HTTP status codes
- Product-not-found handling
- Invalid quantity and stock validation
- Duplicate username validation

---

## 🛠️ Tech Stack

### Backend

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- Maven

### Database

- MySQL

### Frontend

- HTML
- CSS
- JavaScript

### Development Tools

- Visual Studio Code
- Postman
- MySQL Workbench
- Git & GitHub

---

## 🏗️ Project Architecture

The backend follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
Main Layers
Controller

Handles HTTP requests and API endpoints.

Service

Contains application and business logic.

Repository

Handles database operations using Spring Data JPA.

Model

Contains JPA entities representing database tables.

DTO

Separates API request and response data from database entities.

Security

Handles JWT authentication and role-based authorization.

Exception

Provides centralized exception handling.

📁 Project Structure
Ecommerce/
│
├── .gitignore
├── .gitattributes
├── pom.xml
├── mvnw
├── mvnw.cmd
│
├── .mvn/
│   └── wrapper/
│
└── src/
    └── main/
        ├── java/
        │   └── com/example/ecommerce/
        │       │
        │       ├── config/
        │       │   └── SecurityConfig.java
        │       │
        │       ├── controller/
        │       │   ├── AuthController.java
        │       │   ├── CartController.java
        │       │   └── EcommerceController.java
        │       │
        │       ├── dto/
        │       │   ├── ProductRequestDTO.java
        │       │   ├── ProductResponseDTO.java
        │       │   ├── LoginRequestDTO.java
        │       │   ├── LoginResponseDTO.java
        │       │   ├── UserResponseDTO.java
        │       │   ├── OrderRequestDTO.java
        │       │   ├── OrderResponseDTO.java
        │       │   ├── MultiProductOrderDTO.java
        │       │   ├── OrderItemRequestDTO.java
        │       │   ├── OrderSummaryDTO.java
        │       │   ├── CartItemResponseDTO.java
        │       │   └── CheckoutResponseDTO.java
        │       │
        │       ├── exception/
        │       │   ├── GlobalExceptionHandler.java
        │       │   └── ResourceNotFoundException.java
        │       │
        │       ├── model/
        │       │   ├── Product.java
        │       │   ├── Users.java
        │       │   ├── Orders.java
        │       │   ├── OrderItems.java
        │       │   ├── Cart.java
        │       │   └── CartItem.java
        │       │
        │       ├── repository/
        │       │   ├── ProductRepository.java
        │       │   ├── UserRepository.java
        │       │   ├── OrderRepository.java
        │       │   ├── OrderItemRepository.java
        │       │   ├── CartRepository.java
        │       │   └── CartItemRepository.java
        │       │
        │       ├── security/
        │       │   ├── JwtService.java
        │       │   └── JwtAuthenticationFilter.java
        │       │
        │       └── service/
        │           ├── ProductService.java
        │           ├── UserService.java
        │           ├── OrderService.java
        │           └── CartService.java
        │
        └── resources/
            ├── application.properties
            └── static/
                └── index.html
🔐 Security

The application uses Spring Security with JWT authentication.

Authentication Flow
User
 ↓
Login
 ↓
Credentials validated
 ↓
JWT generated
 ↓
Client sends JWT with requests
 ↓
JwtAuthenticationFilter validates JWT
 ↓
User role is loaded
 ↓
Spring Security authorizes request
Role-Based Authorization
Operation	ADMIN	CUSTOMER
Register / Login	✅	✅
View Products	✅	✅
Add Product	✅	❌
Delete Product	✅	❌
Cart Operations	✅	✅
Place Orders	✅	✅
View Own Orders	✅	✅
📡 API Endpoints
Authentication
POST /api/auth/register
POST /api/auth/login
Products
POST   /api/product
GET    /api/products
DELETE /api/product/{id}
Orders
POST /api/order
GET  /api/orders/my
Cart
GET    /api/cart
POST   /api/cart/add
PUT    /api/cart/update/{cartItemId}
DELETE /api/cart/remove/{cartItemId}
POST   /api/cart/checkout

Additional controller mappings may be available for multi-product order processing depending on the current implementation.

🗄️ Database

The application uses MySQL with the following main entities:

Users
Products
Orders
OrderItems
Cart
CartItems

Spring Data JPA and Hibernate are used for database interaction and ORM mapping.

⚙️ Configuration

Sensitive configuration values are not stored directly in the source code.

The application uses environment variables for:

DB_USERNAME
DB_PASSWORD
JWT_SECRET

The application.properties file uses references to these environment variables:

spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000
Windows PowerShell

Set the required variables before starting the application:

$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
$env:JWT_SECRET="YOUR_LONG_RANDOM_SECRET"

Never commit actual passwords or JWT secrets to GitHub.

▶️ How to Run
Prerequisites

Make sure you have installed:

Java 25
MySQL
Git
Maven (optional because the project includes Maven Wrapper)
1. Clone the repository
git clone https://github.com/YOUR_USERNAME/Ecommerce-Inventory-Management.git
2. Create the database

Open MySQL and run:

CREATE DATABASE ecommerce;
3. Configure environment variables

Windows PowerShell:

$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
$env:JWT_SECRET="YOUR_LONG_RANDOM_SECRET"
4. Start the application
.\mvnw spring-boot:run

The application runs at:

http://localhost:8080
🧪 Testing

The application was tested using Postman and the web frontend.

Authentication Testing
User registration
User login
Duplicate username validation
Invalid password handling
JWT authentication
Invalid JWT handling
Role-based authorization
Product Testing
Product creation
Product retrieval
Product deletion
SKU-based stock handling
Product validation
Cart & Order Testing
Add products to cart
Update cart quantity
Remove cart items
Checkout
Single-product orders
Multi-product orders
Insufficient stock validation
Customer order history
Backend Validation
Request validation
Centralized exception handling
Resource-not-found handling
Authentication and authorization checks
💡 Key Implementation Highlights
SKU-Based Inventory Handling

When a product with an existing SKU is added, the system does not create another product record.

Instead, the existing product's stock is increased.

Existing SKU
     ↓
Find product
     ↓
Product exists?
   ↙       ↘
 YES        NO
 ↓           ↓
Increase     Create
stock        product
Transactional Order Processing

Order and inventory operations are handled transactionally to maintain consistency while processing orders.

DTO-Based API Design

Request and response DTOs are used to separate API data from database entities and provide controlled request validation.

Centralized Exception Handling

Application errors are handled through a global exception handler to return consistent API responses and appropriate HTTP status codes.

🔮 Future Improvements

Potential future improvements include:

Product search and filtering
Pagination
Product categories
Order status management
Admin dashboard analytics
Product image upload
Payment gateway integration
Automated unit and integration test expansion
Cloud deployment
👨‍💻 Author
Kushal C

Java Full Stack Developer

Java • Spring Boot • Spring Security • REST APIs
Spring Data JPA • Hibernate • MySQL
HTML • CSS • JavaScript
📌 Project Status

The application is currently functional with authentication, role-based authorization, product and inventory management, cart, checkout, order processing, validation, exception handling, and a responsive frontend.