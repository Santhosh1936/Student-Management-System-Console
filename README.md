# Student Management System (Console-Based)

## 📌 Project Description
This is a **Student Management System** built using **Java and MySQL**. It allows administrators to **add, view, update, delete, and search** students stored in a database. Deleted students are moved to a separate `deleted_students` table for record-keeping.

## 🔹 Features
- ✅ **Add Students** (Store student details in MySQL database)
- ✅ **View Students** (List all students from the database)
- ✅ **Update Student Details** (Modify student records)
- ✅ **Delete Students** (Move deleted students to `deleted_students` table)
- ✅ **Search Students** (By ID or Name)
- ✅ **Admin Authentication** (Ensures only authorized users access the system)

## 🛠️ Technologies Used
- **Java** (Core logic & database handling)
- **MySQL** (Database for storing student records)
- **JDBC** (Java Database Connectivity for interacting with MySQL)

## 📂 Project Structure
```
Student Management System/
│── src/
│   ├── Student.java  # Student class with properties
│   ├── StudentManagement.java  # Main application logic
│── library/
│   ├── mysql-connector-java-<version>.jar  # MySQL JDBC driver
│── README.md  # Project Documentation
│── database_setup.sql  # SQL script for database setup
```

## ⚙️ Setup Instructions
### 1️⃣ Clone the Repository
```sh
git clone https://github.com/YOUR_USERNAME/Student-Management-System-Console.git
cd Student-Management-System-Console
```

### 2️⃣ Install MySQL and Create Database
Open MySQL and run:
```sql
CREATE DATABASE studentdb;
USE studentdb;

CREATE TABLE students (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Department VARCHAR(50),
    Age INT
);

CREATE TABLE deleted_students (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Department VARCHAR(50),
    Age INT
);
```

### 3️⃣ Add MySQL Connector (JDBC)
Download `mysql-connector-java-<version>.jar` and place it inside the `library/` folder.

### 4️⃣ Compile and Run the Project
```sh
javac -cp .;library/mysql-connector-java-<version>.jar src/StudentManagement.java
java -cp .;library/mysql-connector-java-<version>.jar StudentManagement
```

## 🔎 Example Usage
```
Enter Admin Password: admin123
Authentication successful! ✅

📚 Student Management System
1. Add Student
2. View Students
3. Delete Student
4. Update Student
5. Search Student
6. Exit
Enter your choice: 2
```

## 📌 Next Steps
🔹 Adding a **Graphical User Interface (GUI)** using JavaFX 🚀
🔹 Improving search functionality with more filters 🔍

---

### 👨‍💻 Author: [KETHAVATH sANTHOSH]
📌 GitHub: (https://github.com/Santhosh1936)
📌 LinkedIn:(https://www.linkedin.com/in/santhosh-kethavath-8a0870271/)

