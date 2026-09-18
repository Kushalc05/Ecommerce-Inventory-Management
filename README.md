# E-Commerce & Inventory Management System

A full-stack e-commerce and inventory management application built using Java and Spring Boot. The system provides product and inventory management, user authentication, role-based authorization, shopping cart functionality, checkout, and order management through RESTful APIs and a responsive web interface.

## Features

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

## Tech Stack

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

## Project Architecture

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
Separates API request/response data from database entities.

Security
Handles JWT authentication and authorization.

Exception
Provides centralized exception handling.

## Project Structure

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

## Security

The application uses Spring Security with JWT authentication.
Authentication flow:

User
 ↓
Login
 ↓
Spring Security validates credentials
 ↓
JWT generated
 ↓
Client sends JWT with requests
 ↓
JwtAuthenticationFilter validates token
 ↓
User role is loaded
 ↓
Spring Security authorizes request

## Roles

Operation	    ADMIN	CUSTOMER
Register/Login	 ✅	     ✅
View Products	 ✅	     ✅
Add Product	     ✅	     ❌
Delete Product	 ✅	     ❌
Cart Operations	 ✅	     ✅
Place Orders	 ✅	     ✅
View Own Orders	 ✅	     ✅

## API Endpoints

Authentication
POST /api/auth/register
POST /api/auth/login

Products
POST   /api/product
GET    /api/products
DELETE /api/product/{id}

Orders
POST /api/order
POST /api/order/multi
GET  /api/orders/my

Cart
GET    /api/cart
POST   /api/cart/add
PUT    /api/cart/update/{cartItemId}
DELETE /api/cart/remove/{cartItemId}
POST   /api/cart/checkout

API paths may depend on the current controller mappings in the application.

## Database

The application uses MySQL with the following main entities:
Users
Products
Orders
OrderItems
Cart
CartItems

Spring Data JPA and Hibernate are used for database interaction and ORM mapping.


## Configuration

Sensitive configuration values are not stored directly in the source code.

The application uses environment variables for:
DB_USERNAME
DB_PASSWORD
JWT_SECRET

Example:
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000

Windows PowerShell
Set the variables before starting the application:
$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
$env:JWT_SECRET="YOUR_LONG_RANDOM_SECRET"

Never commit actual passwords or JWT secrets to GitHub.


## How to Run

Prerequisites
Make sure you have installed:
Java 25
MySQL
Maven (optional because the project includes Maven Wrapper)
Git

1. Clone the repository
git clone https://github.com/YOUR_USERNAME/Ecommerce-Inventory-Management.git

2. Create the database
In MySQL:
CREATE DATABASE ecommerce;

3. Configure environment variables
Windows PowerShell:
$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
$env:JWT_SECRET="YOUR_LONG_RANDOM_SECRET"

4. Start the application
Windows PowerShell:
.\mvnw spring-boot:run

The application runs on:
http://localhost:8080


## Testing

The application was tested using Postman and the web frontend.

Tested areas include:
User registration
User login
Duplicate username validation
Invalid password handling
JWT authentication
Invalid JWT handling
Role-based authorization
Product creation
SKU-based stock handling
Product retrieval
Cart operations
Checkout
Single-product orders
Multi-product orders
Insufficient stock validation
Customer order history
Request validation
Exception handling


## Key Implementation Highlights

SKU-Based Inventory Handling
When a product with an existing SKU is added, the system does not create another product record. Instead, the existing product's stock is increased.

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

Order and inventory operations are handled transactionally to maintain consistency when processing orders.

DTO-Based API Design

Request and response DTOs are used to avoid directly exposing entity objects through the API and to provide controlled request validation.


## Future Improvements

Potential future improvements include:
Product search and filtering
Pagination
Product categories
Order status management
Admin dashboard analytics
Image upload for products
Payment gateway integration
Automated unit and integration test expansion
Deployment to a cloud platform


## Author

Kushal C

Java Full Stack Developer

Technologies:

Java • Spring Boot • Spring Security • REST APIs
Spring Data JPA • Hibernate • MySQL
HTML • CSS • JavaScript
