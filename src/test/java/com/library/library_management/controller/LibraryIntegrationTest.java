package com.library.library_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LibraryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    record BookRequest(String title, String author, String isbn, String publishedDate, int availableCopies) {}
    record MemberRequest(String name, String phone, String registeredDate) {}
    record BorrowRequest(int memberId, int bookId, String borrowedDate, String dueDate) {}

    @Nested
    @DisplayName("Book Controller Integration Tests")
    class BookTests {

        @Test
        void testCreateBook() throws Exception {
            var book = new BookRequest("JUnit Pocket Guide", "Erich Gamma", "9780596007430", "2004-06-28", 2);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(book)))
                    .andExpect(status().isOk());
        }

        @Test
        void testUpdateBook() throws Exception {
            var book = new BookRequest("Refactoring", "Martin Fowler", "9780201485677", "1999-07-08", 3);
            mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(book)))
                    .andExpect(status().isOk());
        }

        @Test
        void testDeleteBookById() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/3"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void testBorrowBookWhenNotAvailable() throws Exception {
            var borrow = new BorrowRequest(5, 4, "2025-05-21", "2025-05-30");
            mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(borrow)))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(Matchers.containsString("Book is not available for borrowing.")));
        }

        @Test
        void testSearchBooks() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search?keyword=Gamma"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].author").value("Erich Gamma"));
        }

        @Test
        void testGetBookById() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").exists());
        }
    }

    @Nested
    @DisplayName("Member Controller Integration Tests")
    class MemberTests {

        @Test
        void testCreateMember() throws Exception {
            var member = new MemberRequest("Dia", "9876543210", "2025-05-21");
            mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(member)))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void testDeleteMemberById() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/members/4"))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void testGetMemberById() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/members/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").exists());
        }
    }

    @Nested
    @DisplayName("Borrow Controller Integration Tests")
    class BorrowTests {

        @Test
        void testCreateBorrow() throws Exception {
            var borrow = new BorrowRequest(3, 3, "2025-05-15", "2025-05-21");
            mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(borrow)))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void testUpdateBorrow() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/borrows/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "borrowDate": "2025-05-01",
                                        "dueDate": "2025-05-15"
                                    }
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(content().string(Matchers.containsString("Borrow dates updated successfully.")));
        }

        @Test
        void testBorrowMoreThanThreeBooks() throws Exception {
            int memberId = 1;
            int bookId = 3;

            Integer borrowCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM borrow WHERE member_id = ?",
                Integer.class,
                memberId
            );

            var borrow = new BorrowRequest(memberId, bookId, "2025-05-21", "2025-06-01");

            mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(borrow)))
                    .andExpect(borrowCount < 3 ? status().isOk() : status().isBadRequest());
        }

        @Test
        void testReturnBook() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/borrows/2/return?bookId=4"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(Matchers.containsString("Book returned successfully.")));
        }

        @Test
        void testGetAllBorrows() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/borrows"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].memberId").exists());
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        void testCreateBookWithMissingFields() throws Exception {
        	String invalidBookJson = "{\"author\":\"Anonymous\"}";
            mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidBookJson))
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @CsvSource({
                "'','Author','9781234567890','2020-01-01',1",
                "'Book','Author','9781234567890','invalid-date',1",
                "'Book','Author','9781234567890','2020-01-01',-1"
        })
        void testCreateBookInvalidInputs(String title, String author, String isbn, String publishedDate, int copies) throws Exception {
            var book = new BookRequest(title, author, isbn, publishedDate, copies);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(book)))
                    .andExpect(status().isBadRequest());
        }
    }
}
