-- BOOK TABLE
CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    isbn VARCHAR(50),
    published_date DATE,
    available_copies INT
);

-- MEMBER TABLE
CREATE TABLE IF NOT EXISTS member (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(20),
    registered_date DATE
);

-- BORROW TABLE
CREATE TABLE IF NOT EXISTS borrow (
    id SERIAL PRIMARY KEY,
    member_id INT REFERENCES member(id),
    book_id INT REFERENCES book(id),
    borrowed_date DATE,
    due_date DATE
);
