# 🚀 Critter Chronologer

A backend application built using Java and Spring Boot to manage pet scheduling, employee availability, and customer data. This project demonstrates REST API development, database integration, and clean backend architecture.

---

## 📌 Features

* Create and manage customers, pets, and employees
* Schedule activities for pets
* Check employee availability based on skills and date
* RESTful API design
* Clean layered architecture using Spring Boot

---

## 🛠️ Tech Stack

* Java
* Spring Boot
* Maven
* Hibernate (JPA)
* MySQL
* REST APIs

---

## 📂 Project Structure

* `src/main/java` → Application source code
* `src/main/resources` → Configuration files
* `pom.xml` → Project dependencies

---

## ▶️ How to Run

1. Clone the repository
2. Open in IntelliJ IDEA
3. Run `CritterApplication.java`
4. Server starts on `http://localhost:8082`

---

## 📡 API Testing (Postman)

### 🔹 Save Customer

POST `/user/customer`

### 🔹 Save Pet

POST `/pet`

### 🔹 Save Employee

POST `/user/employee`

### 🔹 Check Employee Availability

POST `/user/employee/availability`

Example Request:

```json
{
  "date": "2019-12-17",
  "skills": ["PETTING", "FEEDING"]
}
```


## 💡 Note

Although availability is a retrieval operation, POST is used instead of GET due to request body handling in the project design.

---

## 👩‍💻 Author

Bhanupriya
