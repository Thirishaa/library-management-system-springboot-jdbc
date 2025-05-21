package com.library.library_management.mapper;

import com.library.library_management.model.Borrow;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowRowMapper implements RowMapper<Borrow> {
    @Override
    public Borrow mapRow(ResultSet rs, int rowNum) throws SQLException {
        Borrow borrow = new Borrow();
        borrow.setId(rs.getLong("id"));
        borrow.setMemberId(rs.getLong("member_id"));
        borrow.setBookId(rs.getLong("book_id"));
        borrow.setBorrowDate(rs.getDate("borrowed_date").toLocalDate());
        borrow.setDueDate(rs.getDate("due_date").toLocalDate());  
        return borrow;
    }
}
