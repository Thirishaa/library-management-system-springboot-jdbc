package com.library.library_management.dao;

import com.library.library_management.mapper.BookRowMapper;
import com.library.library_management.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;
import java.util.List;

@Repository
public class BookDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BookRowMapper());
    }

   

    public Book findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", new BookRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null; // no book found for this id
        }
    }


    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, isbn, published_date, available_copies) VALUES (?, ?, ?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies());
    }

    public void update(Long id, Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, author = ?, isbn = ?, published_date = ?, available_copies = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public List<Book> search(String keyword) {
        String query = "SELECT * FROM book WHERE title ILIKE ? OR author ILIKE ? OR isbn ILIKE ?";
        String likeKeyword = "%" + keyword + "%";
        return jdbcTemplate.query(query, new BookRowMapper(), likeKeyword, likeKeyword, likeKeyword);
    }
}
