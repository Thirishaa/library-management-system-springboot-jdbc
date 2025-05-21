package com.library.library_management.dao;

import com.library.library_management.model.Member;
import com.library.library_management.mapper.MemberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Member member) {
        String sql = "INSERT INTO member (name, phone, registered_date) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, member.getName(), member.getPhone(), member.getRegisteredDate());
    }

    public Member findById(int id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new MemberRowMapper(), id);
    }

    public List<Member> findAll() {
        String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }

    public int update(Member member) {
        String sql = "UPDATE member SET name = ?, phone = ?, registered_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, member.getName(), member.getPhone(), member.getRegisteredDate(), member.getId());
    }

    public int delete(int id) {
        String sql = "DELETE FROM member WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
    
    public boolean existsByNameAndPhone(String name, String phone) {
        String sql = "SELECT COUNT(*) FROM member WHERE name = ? AND phone = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name, phone);
        return count != null && count > 0;
    }


    public int countBorrowedBooksByMember(int memberId) {
        String sql = "SELECT COUNT(*) FROM borrow WHERE member_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, memberId);
    }
}
