# 🛒 Cartify – E‑Commerce Backend (Spring Boot + PostgreSQL)

Cartify is a modular, production‑ready e‑commerce backend application built using **Spring Boot**, **JPA/Hibernate**, and **PostgreSQL**. The project follows a clean 3‑layer architecture — **Controller → Service → Repository**, making the codebase easy to maintain, test, and scale.

---

## 📌 Architecture Overview

Below is the high‑level architecture of the application:

<img width="1322" height="662" alt="image" src="https://github.com/user-attachments/assets/3c1516e2-e267-48b9-a2ba-b6ec108d3e97" />

---

## 🚀 Features

### 👤 **User Module**

* Create new users
* Update user details
* Fetch all users
* Fetch user by ID

### 📦 **Product Module**

* Add new products
* Update product details
* Delete products
* Fetch all products
* Fetch product by ID
* Search products by keyword

### 🛒 **Cart Module**

* Add items to cart
* Get cart items for logged-in user
* Remove item from cart
* Stock validation during cart operations

### 📑 **Order Module**

* Create an order from user’s cart
* Validates user and cart before creating order

---

## 🧩 Project Structure (Controller → Service → Repository)

```
Controller Layer
    ├── UserController
    ├── ProductController
    ├── CartController
    └── OrderController

Service Layer
    ├── UserService
    ├── ProductService
    ├── CartService
    └── OrderService

Repository Layer
    ├── UserRepository
    ├── ProductRepository
    ├── CartRepository
    └── OrderRepository
```

---

## 🔥 API Endpoints

### 👤 **User APIs** (`/api/users`)

* `GET /api/users` – Get all users
* `GET /api/users/{id}` – Get user by ID
* `POST /api/users` – Add new user
* `PUT /api/users/{id}` – Update user

### 📦 **Product APIs** (`/api/products`)

* `POST /api/products` – Create product
* `GET /api/products` – Fetch all products
* `GET /api/products/{id}` – Fetch product by ID
* `PUT /api/products/{id}` – Update product
* `DELETE /api/products/{id}` – Delete product
* `GET /api/products/search?keyword=` – Search products

### 🛒 **Cart APIs** (`/api/cart`)

* `POST /api/cart` – Add to cart
* `GET /api/cart` – View cart
* `DELETE /api/cart/items/{productId}` – Remove item from cart

### 📑 **Order APIs** (`/api/order`)

* `POST /api/order` – Create order

> All cart and order APIs require header: `X-User-ID`.

---

## 🗄️ Database & Docker Setup

This project includes **Docker Compose** configuration for:

* PostgreSQL Database
* PGAdmin (DB management tool)

### 🐳 **Run using Docker**

```
docker compose up -d
```

### Default Credentials

| Service    | Username                                            | Password |
| ---------- | --------------------------------------------------- | -------- |
| PostgreSQL | cartify                                             | cartify  |
| PGAdmin    | [pgadmin4@pgadmin.org](mailto:pgadmin4@pgadmin.org) | admin    |

PGAdmin URL → [http://localhost:5050](http://localhost:5050)

---

## ⚙️ Spring Boot Configuration

The app uses PostgreSQL by default.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/cartifyDB
spring.datasource.username=cartify
spring.datasource.password=cartify
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ Running the Application

### **1️⃣ Clone the Repository**

```
git clone https://github.com/KamleshYadav875/cartify-monolithic.git
cd cartify-monolithic
```

### **2️⃣ Start PostgreSQL using Docker**

```
docker compose up -d
```

### **3️⃣ Run Spring Boot App**

* Using IntelliJ: Run the main class
* OR

```
mvn spring-boot:run
```

---

## 🔧 Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Web**
* **Spring Data JPA**
* **PostgreSQL**
* **Docker & Docker Compose**
* **PGAdmin**
