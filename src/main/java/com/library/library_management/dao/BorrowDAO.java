package com.library.library_management.dao;

import com.library.library_management.mapper.BorrowRowMapper;
import com.library.library_management.model.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Inserts a new borrow record into the database.
     */
    public void save(Borrow borrow) {
        String sql = "INSERT INTO borrow (member_id, book_id, borrowed_date, due_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                borrow.getMemberId(),
                borrow.getBookId(),
                borrow.getBorrowDate(),
                borrow.getDueDate());
    }

    /**
     * Returns a list of all borrow records.
     */
    public List<Borrow> findAll() {
        String sql = "SELECT * FROM borrow";
        return jdbcTemplate.query(sql, new BorrowRowMapper());
    }

    /**
     * Finds a borrow record by ID.
     */
    public Borrow findById(Long id) {
        String sql = "SELECT * FROM borrow WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BorrowRowMapper(), id);
    }

    /**
     * Deletes a borrow record by ID.
     */
    public void deleteById(Long id) {
        String sql = "DELETE FROM borrow WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateBorrowDates(Borrow borrow) {
        String sql = "UPDATE borrow SET borrowed_date = ?, due_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, borrow.getBorrowDate(), borrow.getDueDate(), borrow.getId());
    }

    /**
     * Returns the number of books currently borrowed by a member.
     */
    public int countBorrowedBooksByMember(Long memberId) {
        String sql = "SELECT COUNT(*) FROM borrow WHERE member_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, memberId);
    }

    /**
     * Decreases the available copies of a book by 1 (if > 0).
     */
    public void decreaseBookCopies(Long bookId) {
        String sql = "UPDATE \"book\" SET available_copies = available_copies - 1 WHERE id = ? AND available_copies > 0";
        jdbcTemplate.update(sql, bookId);
    }

    /**
     * Increases the available copies of a book by 1.
     */
    public void increaseBookCopies(Long bookId) {
        String sql = "UPDATE \"book\" SET available_copies = available_copies + 1 WHERE id = ?";
        jdbcTemplate.update(sql, bookId);
    }

    /**
     * Returns the number of available copies for a book.
     */
    public int getAvailableCopies(Long bookId) {
        String sql = "SELECT available_copies FROM \"book\" WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, bookId);
    }

    /**
     * Handles the return of a book by:
     * 1. Deleting the borrow record.
     * 2. Increasing available copies of the book.
     */
    public void returnBook(Long borrowId, Long bookId) {
        // Delete the borrow record
        String deleteSql = "DELETE FROM borrow WHERE id = ?";
        jdbcTemplate.update(deleteSql, borrowId);

        // Increment book inventory
        increaseBookCopies(bookId);
    }
}
