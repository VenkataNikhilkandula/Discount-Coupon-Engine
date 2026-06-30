# 🎟️ Coupon Discount Engine

A Spring Boot based Coupon Discount Engine that calculates the best applicable discounts for an order based on coupon codes, order amount, and product category.

The application demonstrates clean architecture, layered design, validation, exception handling, DTO mapping, and extensible coupon processing.

---

# 🚀 Features

- Apply one or more coupon codes
- Calculate total discount
- Return final payable amount
- Validation using Jakarta Validation
- Global Exception Handling
- RESTful APIs
- DTO based request/response
- Clean layered architecture
- Easy to extend with new coupon rules

---

# 🛠 Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Validation
- Spring Data JPA
- Maven
- Lombok
- H2 / MySQL (depending on configuration)

---

# 📁 Project Structure

```
src
├── main
│   ├── java
│   │   └── com.coupondiscountengine
│   │       ├── controller
│   │       ├── service
│   │       ├── service.impl
│   │       ├── repository
│   │       ├── entity
│   │       ├── dto
│   │       │    ├── request
│   │       │    └── response
│   │       ├── mapper
│   │       ├── exception
│   │       ├── util
│   │       └── config
│   │
│   └── resources
│       ├── application.properties
│       └── data.sql
```

---

# 🏗 Architecture

```
Client
   │
   ▼
REST Controller
   │
   ▼
Service Layer
   │
   ▼
Coupon Validation
   │
   ▼
Discount Calculation
   │
   ▼
Repository
   │
   ▼
Database
```

---

# ⚙️ Getting Started

## Clone Repository

```bash
git clone https://github.com/yourusername/coupon-discount-engine.git
```

```bash
cd coupon-discount-engine
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Application

```bash
mvn spring-boot:run
```

or

Run

```
CouponDiscountEngineApplication.java
```

---

# Server

```
http://localhost:8081
```

---

# 📮 REST API

## Checkout API

### POST

```
POST /api/checkout
```

Full URL

```
http://localhost:8081/api/checkout
```

---

## Request

```json
{
  "userId": 1,
  "couponCodes": [
    "NIKKI10",
    "FLAT100"
  ],
  "originalAmount": 2500,
  "category": "MOBILES"
}
```

---

## Response

```json
{
  "orderId": 101,
  "originalAmount": 2500,
  "totalDiscount": 350,
  "finalAmount": 2150,
  "appliedDiscounts": [
    {
      "couponCode": "NIKKI10",
      "discountAmount": 250
    },
    {
      "couponCode": "DIWALI100",
      "discountAmount": 100
    }
  ]
}
```

---

# Validation

The application validates:

- User ID
- Order Amount
- Coupon Codes
- Request Body

Example validation response

```json
{
  "status":400,
  "message":"User ID is required"
}
```

---

# Error Handling

Global Exception Handler manages:

- Validation Errors
- Entity Not Found
- Illegal Arguments
- Data Integrity Violations
- Generic Exceptions

Returns consistent JSON error responses.

---

# Business Flow

```
User Request

      │

      ▼

Checkout Controller

      │

      ▼

Checkout Service

      │

      ▼

Validate Coupons

      │

      ▼

Calculate Discounts

      │

      ▼

Generate Response

      │

      ▼

Return Final Amount
```

---

# Example

Original Amount

```
₹2500
```

Coupons

```
NIKKI10
DIWALI100
```

Total Discount

```
₹350
```

Final Amount

```
₹2150
```

---

# Future Enhancements

- Coupon Expiry
- Coupon Usage Limits
- User-specific Coupons
- Coupon Priority
- Percentage + Flat Discount Combination
- Admin APIs
- Swagger/OpenAPI
- Authentication & Authorization
- Redis Cache
- Docker Support
- Kubernetes Deployment
- Kafka Event Publishing

---

# Testing

Run

```bash
mvn test
```

---

# Build

```bash
mvn clean package
```

Generated JAR

```
target/coupon-discount-engine.jar
```

Run

```bash
java -jar target/coupon-discount-engine.jar
```

---

# API Testing

Use:

- Postman
- IntelliJ HTTP Client
- cURL

Example cURL

```bash
curl --location 'http://localhost:8081/api/checkout' \
--header 'Content-Type: application/json' \
--data '{
    "userId":1,
    "couponCodes":["NIKKI10","DIWALI100"],
    "originalAmount":2500,
    "category":"MOBILES"
}'
