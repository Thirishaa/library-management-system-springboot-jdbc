package com.library.library_management.controller;

import com.library.library_management.model.Borrow;
import com.library.library_management.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping
    public ResponseEntity<String> borrowBook(@RequestBody Borrow borrow) {
        borrowService.borrowBook(borrow);
        return ResponseEntity.ok("Book borrowed successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Borrow>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }
    
    @PutMapping("/{borrowId}")
    public ResponseEntity<String> updateBorrowDates(
            @PathVariable Long borrowId,
            @RequestBody Borrow updatedBorrow) {

        borrowService.updateBorrowDates(borrowId, updatedBorrow.getBorrowDate(), updatedBorrow.getDueDate());
        return ResponseEntity.ok("Borrow dates updated successfully.");
    }


    @DeleteMapping("/{borrowId}/return")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowId, @RequestParam Long bookId) {
        borrowService.returnBook(borrowId, bookId);
        return ResponseEntity.ok("Book returned successfully.");
    }
}
