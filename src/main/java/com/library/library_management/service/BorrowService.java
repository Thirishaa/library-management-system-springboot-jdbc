package com.library.library_management.service;

import com.library.library_management.dao.BorrowDAO;
import com.library.library_management.exception.BookNotAvailableException;
import com.library.library_management.exception.BorrowLimitExceededException;
import com.library.library_management.model.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {

    private static final int MAX_BORROW_LIMIT = 3;
    private static final int DEFAULT_BORROW_DURATION_DAYS = 14;

    @Autowired
    private BorrowDAO borrowDAO;

    public void borrowBook(Borrow borrow) {
        int borrowedCount = borrowDAO.countBorrowedBooksByMember(borrow.getMemberId());
        if (borrowedCount >= MAX_BORROW_LIMIT) {
            throw new BorrowLimitExceededException("Borrow limit of 3 books exceeded.");
        }

        int availableCopies = borrowDAO.getAvailableCopies(borrow.getBookId());
        if (availableCopies <= 0) {
            throw new BookNotAvailableException("Book is not available for borrowing.");
        }


        LocalDate today = LocalDate.now();
        borrow.setBorrowDate(today);
        borrow.setDueDate(today.plusDays(DEFAULT_BORROW_DURATION_DAYS));

        borrowDAO.save(borrow);
        borrowDAO.decreaseBookCopies(borrow.getBookId());
    }
    
    public void updateBorrowDates(Long borrowId, LocalDate borrowDate, LocalDate dueDate) {
        Borrow borrow = borrowDAO.findById(borrowId);
        if (borrow == null) {
            throw new RuntimeException("Borrow record not found with id " + borrowId);
        }
        borrow.setBorrowDate(borrowDate);
        borrow.setDueDate(dueDate);
        borrowDAO.updateBorrowDates(borrow);
    }


    public List<Borrow> getAllBorrows() {
        return borrowDAO.findAll();
    }

    public void returnBook(Long borrowId, Long bookId) {
        borrowDAO.deleteById(borrowId); // Delete the borrow record
        borrowDAO.increaseBookCopies(bookId); // Increment the book's available copies
    }
}
