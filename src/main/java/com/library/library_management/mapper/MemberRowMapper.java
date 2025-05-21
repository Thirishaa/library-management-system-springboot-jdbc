package com.library.library_management.mapper;

import com.library.library_management.model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("name"));
        member.setPhone(rs.getString("phone"));
        member.setRegisteredDate(rs.getDate("registered_date").toLocalDate());
        return member;
    }
}
