package com.library.library_management.exception;

public class BorrowLimitExceededException extends RuntimeException {
    
    public BorrowLimitExceededException() {
        super("Borrow limit exceeded. You cannot borrow more books.");
    }
    
    public BorrowLimitExceededException(String message) {
        super(message);
    }
}
