# Ecommerce-Microservices-Backend
## 🛒 Project Overview
This is a complete e-commerce system made of **three microservices**:

- **💰 Wallet Service:**  
  Manages user registration, login, wallet balance, deposits, withdrawals, and transaction history.

- **🏬 Shop Service:**  
  Handles products, shopping cart, orders, and payment flow.

- **📦 Inventory Service:**  
  Keeps track of product stock and availability.

---

## 🌐 Inter-service Communication
The services communicate seamlessly through:

- **🔷 Spring Cloud & Eureka:** Service discovery  
- **🔷 Feign Clients:** Clean inter-service calls  
- **🔷 Resilience4j:** Fault tolerance  
- **🔷 Spring Config Server:** Centralized configuration management  
- **🔷 Spring API Gateway:** Smart routing  

Each microservice was built, tested, and maintained independently, equipped with robust communication channels and fault-tolerance strategies. They work together flawlessly through REST APIs and a centralized service registry.

---

## 🛠️ Tech Stack & Tools
- **Backend:** Spring Boot, Spring Cloud, Spring Data JPA  
- **Database:** MySQL  
- **Service Discovery & Routing:** Eureka Server, Spring API Gateway  
- **Inter-service Communication:** Feign Client  
- **Fault Tolerance:** Resilience4j  
- **Configuration:** Spring Config Server  
- **Build & Testing:** Maven, Postman  

---

This project reflects my hands-on experience with **microservices, inter-service communication, and real-world backend development practices**.
