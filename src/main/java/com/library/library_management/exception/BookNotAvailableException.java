package com.library.library_management.exception;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException() {
        super("Book is not available.");
    }

    public BookNotAvailableException(String message) {
        super(message);
    }

    public BookNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotAvailableException(Throwable cause) {
        super(cause);
    }
}
