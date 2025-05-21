package com.library.library_management.model;

import java.time.LocalDate;
import java.util.Objects;

public class Borrow {

    private Long id;
    private Long memberId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    // Constructors
    public Borrow() {
    }

    public Borrow(Long id, Long memberId, Long bookId, LocalDate borrowDate, LocalDate dueDate) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // toString
    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", bookId=" + bookId +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Borrow)) return false;
        Borrow borrow = (Borrow) o;
        return Objects.equals(id, borrow.id) &&
                Objects.equals(memberId, borrow.memberId) &&
                Objects.equals(bookId, borrow.bookId) &&
                Objects.equals(borrowDate, borrow.borrowDate) &&
                Objects.equals(dueDate, borrow.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, bookId, borrowDate, dueDate);
    }
}
