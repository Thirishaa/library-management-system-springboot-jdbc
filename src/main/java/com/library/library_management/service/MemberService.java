package com.library.library_management.service;

import com.library.library_management.dao.MemberDAO;
import com.library.library_management.exception.BorrowLimitExceededException;
import com.library.library_management.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    public void addMember(Member member) {
        boolean exists = memberDAO.existsByNameAndPhone(member.getName(), member.getPhone());
        if (exists) {
            throw new RuntimeException("Member with the same name and phone number already exists.");
        }
        memberDAO.save(member);
    }


    public Member getMemberById(int id) {
        return memberDAO.findById(id);
    }

    public List<Member> getAllMembers() {
        return memberDAO.findAll();
    }

    public void updateMember(Member member) {
        memberDAO.update(member);
    }

    public void deleteMember(int id) {
        memberDAO.delete(id);
    }

    public void checkBorrowLimit(int memberId) {
        int borrowedCount = memberDAO.countBorrowedBooksByMember(memberId);
        if (borrowedCount >= 3) {
            throw new BorrowLimitExceededException("Borrow limit exceeded for member ID: " + memberId);
        }
    }
}
