# 📚 Library Management System

A backend system designed exclusively for librarians to manage books, members, and borrow/return operations.
Built with **Spring Boot**, **Spring MVC**, **PostgreSQL**, and **JdbcTemplate** using raw SQL queries.

---

## ✅ Features

* **Book Management**
  CRUD operations for books (`id`, `title`, `author`, `isbn`, `publishedDate`, `availableCopies`)
  Search books by title, author, or ISBN

* **Member Management**
  CRUD operations for members (`id`, `name`, `phone`, `registeredDate`)

* **Borrowing Management**
  CRUD operations for borrow records (`id`, `memberId`, `bookId`, `borrowedDate`, `dueDate`)
  Lend books only if available (updates `availableCopies`)
  Accept book returns (updates `availableCopies`)
  API responses include joined book and member details

* **Exception Handling**
  Handles errors like borrowing unavailable books or exceeding borrow limits

* **Database**
  PostgreSQL with schema migration via Flyway
  Uses `JdbcTemplate` for raw SQL queries (no ORM)

* **Testing**
  Integration tests using `MockMvc` covering all APIs and edge cases
  Organized tests using `@ParameterizedTest` and `@Nested`

---

## 🛠️ Tech Stack

* Java 17+
* Spring Boot 2.x
* Spring MVC
* PostgreSQL
* JdbcTemplate (raw SQL)
* Flyway (database migrations)
* JUnit 5, Mockito, MockMvc (testing)

---

## 🚀 Setup & Run

### Prerequisites

* Java 17 or higher
* PostgreSQL installed and running
* Maven installed

### Steps

1. Clone the repo

   ```bash
   git clone https://github.com/Thirishaa/library-management-system-springboot-jdbc.git
   cd library-management-system-springboot-jdbc
   ```

2. Configure database connection in `src/main/resources/application.properties`:

   ```properties
   server.port=8089
   spring.application.name=library-management

   # PostgreSQL connection
   spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
   spring.datasource.username=postgres
   spring.datasource.password=your_password_here
   spring.sql.init.mode=always

   # JDBC Driver
   spring.datasource.driver-class-name=org.postgresql.Driver

   # Flyway
   spring.flyway.enabled=true
   spring.flyway.locations=classpath:db/migration
   spring.flyway.baseline-on-migrate=true
   ```

   > **Note:** Replace `your_password_here` with your actual PostgreSQL password.

3. Create the PostgreSQL database:

   ```sql
   CREATE DATABASE library_db;
   ```

4. Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Access API at: [http://localhost:8089](http://localhost:8089)

---

## 🔗 API Endpoints

### 📚 Books

* `GET /books` — List all books
* `GET /books/{id}` — Get book by ID
* `POST /books` — Add new book
* `PUT /books/{id}` — Update book
* `DELETE /books/{id}` — Delete book
* `GET /books/search?query=xxx` — Search by title, author, or ISBN

### 👤 Members

* `GET /members`
* `GET /members/{id}`
* `POST /members`
* `PUT /members/{id}`
* `DELETE /members/{id}`

### 🔄 Borrowing

* `GET /borrows` — List all borrow records (with book & member details)
* `POST /borrows` — Lend book (if available)
* `PUT /borrows/{id}` — Update borrow record (e.g., return book)
* `DELETE /borrows/{id}` — Delete borrow record

---

## 🗃️ Database Schema

The system uses three main tables in PostgreSQL:

### 📖 Book Table

```sql
CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    isbn VARCHAR(50),
    published_date DATE,
    available_copies INT
);
```

### 🧾 Member Table

```sql
CREATE TABLE IF NOT EXISTS member (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(20),
    registered_date DATE
);
```

### 📘 Borrow Table

```sql
CREATE TABLE IF NOT EXISTS borrow (
    id SERIAL PRIMARY KEY,
    member_id INT REFERENCES member(id),
    book_id INT REFERENCES book(id),
    borrowed_date DATE,
    due_date DATE
);
```

---

### 🧪 Testing

The project includes Postman test output in the [Postman Testing Document](https://github.com/Thirishaa/library-management-system-springboot-jdbc/blob/main/docs/library-management-postman-testing.pdf). It shows the results of key operations:

- Add / Get / Update / Delete books  
- Book availability count updates  
- Search books  
- Borrow & return books with availability updates  
- Member CRUD  
- Borrow transaction update & delete  
- Exception: Book Not Found with structured 404 error response  

📌 Descriptions are brief but operations are verified. See the PDF for full request/response details.

Run all integration tests with:

```bash
mvn test
```


---

## 📝 Notes

* Raw SQL with `JdbcTemplate` is used for fine-grained control.
* No authentication as it’s for librarian use only.
* Exception handling is implemented for edge cases like unavailable books or borrow limits.

---

## 👩‍💻 Author

**Thirishaa Swamynathan**
[GitHub](https://github.com/Thirishaa)
