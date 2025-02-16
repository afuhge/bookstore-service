package com.bookstore_service.service

import com.bookstore_service.dto.BookCreateRequestDTO
import com.bookstore_service.dto.BookResponseDTO
import com.bookstore_service.dto.CategoryResponseDTO
import com.bookstore_service.entity.BookEntity
import com.bookstore_service.entity.CategoryEntity
import com.bookstore_service.exceptions.BookNotFoundException
import com.bookstore_service.exceptions.CategoryNotFoundException
import com.bookstore_service.repository.BookRepository
import com.bookstore_service.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService (val bookRepository: BookRepository, val categoryRepository: CategoryRepository) {
    fun addBook(book: BookCreateRequestDTO): BookResponseDTO {
        // Check if the title already exists in the database for a different book
        if (bookRepository.existsByTitle(book.title)) {
            throw IllegalArgumentException("A book with the title '${book.title}' already exists.")
        }

        val category = book.categoryId?.let {
            categoryRepository.findById(it).orElseThrow { CategoryNotFoundException("Category not found with id: $it") }
        }

        val bookEntity = book.let {
            BookEntity(title = it.title, pages = it.pages, spice = it.spice, category = category)
        }
        val savedBook = bookRepository.save(bookEntity);

        return bookEntity.let {
            BookResponseDTO(it.id!!, it.title, savedBook.category?.toCategoryResponseDTO(), it.pages, it.spice)
        }
    }

    fun getAllBooks(title: String?, category: String?, spice: Int?): List<BookResponseDTO> {
        return bookRepository.findByTitleContainingIgnoreCaseAndCategoryNameContainingIgnoreCaseAndSpiceGreaterThanEqual(title ?: "", category?: "", spice ?: 0).map { BookResponseDTO(it.id!!, it.title,
            it.category?.toCategoryResponseDTO(), it.pages, it.spice) };
    }

    fun getBookById(bookId: UUID): BookResponseDTO {
        val book = bookRepository.findById(bookId)
            .orElseThrow { BookNotFoundException("No book found with id $bookId") }

        return book.let {
            BookResponseDTO(it.id!!, it.title, it.category?.toCategoryResponseDTO(), it.pages, it.spice)
        }
    }

    fun deleteBook(bookId: UUID) {
        if (!bookRepository.existsById(bookId)) {
            throw BookNotFoundException("No book found with id: $bookId")
        }
        bookRepository.deleteById(bookId);
    }

    fun deleteBooks() =
        bookRepository.deleteAll();

    fun updateBook(bookId: UUID, updateRequest: BookCreateRequestDTO): BookResponseDTO {
        // Check if the title already exists in the database for a different book
        if (bookRepository.existsByTitleAndIdNot(updateRequest.title, bookId)) {
            throw IllegalArgumentException("A book with the title '${updateRequest.title}' already exists.")
        }

        val book = bookRepository.findById(bookId)
            .orElseThrow { BookNotFoundException("No book found with id $bookId") }

        val category = updateRequest.categoryId?.let {
            categoryRepository.findById(it).orElseThrow { CategoryNotFoundException("Category not found with id: $it") }
        }

        book.apply {
            this.title = updateRequest.title;
            this.category = category;
            this.pages = updateRequest.pages;
            this.spice = updateRequest.spice;
        }
        val updatedBook = bookRepository.save(book);

        return updatedBook.let {
            BookResponseDTO(it.id!!, it.title, it.category?.toCategoryResponseDTO(), it.pages, it.spice)
        }
    }


    private fun CategoryEntity.toCategoryResponseDTO(): CategoryResponseDTO =
        CategoryResponseDTO(this.id!!, this.name, 0)
}