package com.library.library_management.service;
import com.library.library_management.exception.BookNotAvailableException;

import com.library.library_management.dao.BookDAO;
import com.library.library_management.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDAO bookDAO;

    public List<Book> getAllBooks() { return bookDAO.findAll(); }

    public Book getBookById(Long id) {
        Book book = bookDAO.findById(id);
        if (book == null) {
            throw new BookNotAvailableException("Book with id " + id + " is not available.");
        }
        return book;
    }


    public void addBook(Book book) { bookDAO.save(book); }

    public void updateBook(Long id, Book book) { bookDAO.update(id, book); }

    public void deleteBook(Long id) { bookDAO.delete(id); }

    public List<Book> searchBooks(String keyword) { return bookDAO.search(keyword); }
}
