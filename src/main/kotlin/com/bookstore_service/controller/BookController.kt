package com.bookstore_service.controller

import com.bookstore_service.dto.BookCreateRequestDTO
import com.bookstore_service.dto.BookResponseDTO
import com.bookstore_service.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/books")
class BookController (val bookService: BookService){
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody @Validated book: BookCreateRequestDTO): BookResponseDTO {
        return bookService.addBook(book);
    }

    @GetMapping("/{book_id}")
    @ResponseStatus(HttpStatus.OK)
    fun getBookById(@PathVariable("book_id") bookId: UUID): BookResponseDTO {
        return bookService.getBookById(bookId)
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun getAllBooks(): List<BookResponseDTO> {
        return bookService.getAllBooks()
    }

    @DeleteMapping("/{book_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable("book_id") bookId: UUID) {
        return bookService.deleteBook(bookId);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllBooks() {
        return bookService.deleteBooks();
    }

    @PutMapping("/{book_id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBook(@PathVariable("book_id") bookId: UUID, @RequestBody @Validated book: BookCreateRequestDTO): BookResponseDTO {
        return bookService.updateBook(bookId, book);
    }
}