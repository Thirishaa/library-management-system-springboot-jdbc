package com.library.library_management.controller;

import com.library.library_management.model.Member;
import com.library.library_management.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        memberService.addMember(member);
        return ResponseEntity.ok("Member added successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable int id, @RequestBody Member member) {
        member.setId(id);
        memberService.updateMember(member);
        return ResponseEntity.ok("Member updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable int id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok("Member deleted successfully.");
    }
}
