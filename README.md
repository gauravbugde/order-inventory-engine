# Order & Inventory Management Engine

A robust, enterprise-grade distributed order processing and inventory management backend built with **Java 17** and **Spring Boot 3**. Designed to solve high-concurrency race conditions and demonstrate advanced transactional consistency patterns.

## 🚀 Core Features & Architecture

* **Optimistic Concurrency Control:** Utilizes JPA `@Version` locking combined with Spring Retry mechanisms to prevent inventory overselling during high-volume concurrent transactions[cite: 2].
* **Distributed Saga Patterns:** Orchestrates state machine transitions across Orders, Inventory, and Payment processing services[cite: 2].
* **Reliable Persistence:** Spring Data JPA with PostgreSQL support and H2 database slicing for high-performance integration testing[cite: 2, 3, 4].
* **Comprehensive Test Suite:** Includes multi-threaded concurrency stress tests (`OrderSagaConcurrencyTest`) validating thread-safe inventory decrementing under load[cite: 2, 6].

## 🛠️ Tech Stack
* **Language:** Java 17[cite: 2]
* **Framework:** Spring Boot 3.2.5 (Web, Data JPA, Validation, Retry)[cite: 2, 3, 7]
* **Database:** PostgreSQL (Production) / H2 (Test)[cite: 3, 4, 5]
* **Testing:** JUnit 5, Spring Boot Test[cite: 2, 3, 6]

## 📁 Project Structure

```text
order-inventory-engine/
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── enterprise
    │   │           └── orderengine
    │   │               ├── OrderInventoryEngineApplication.java
    │   │               ├── domain
    │   │               │   ├── Inventory.java
    │   │               │   ├── Order.java
    │   │               │   └── OrderItem.java
    │   │               ├── exception
    │   │               │   └── InsufficientInventoryException.java
    │   │               ├── repository
    │   │               │   ├── InventoryRepository.java
    │   │               │   └── OrderRepository.java
    │   │               └── service
    │   │                   └── InventoryService.java
    │   └── resources
    │       └── application.yml
    └── test
        ├── java
        │   └── com
        │       └── enterprise
        │           └── orderengine
        │               └── OrderSagaConcurrencyTest.java
        └── resources
            └── application-test.yml
