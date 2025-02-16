package com.bookstore_service.controller

import com.bookstore_service.dto.BookCreateRequestDTO
import com.bookstore_service.dto.BookResponseDTO
import com.bookstore_service.exceptions.BookNotFoundException
import com.bookstore_service.exceptions.CategoryNotFoundException
import com.bookstore_service.service.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book Management", description = "Operations related to books")
class BookController (val bookService: BookService){

    @Operation(summary = "Create a new book")
   @ApiResponses(
       value = [
           ApiResponse(
               responseCode = "201", description = "Created book",
               content = [
                   Content(
                       mediaType = "application/json",
                       schema = Schema(implementation = BookResponseDTO::class)
                   )
               ]
           ),
           ApiResponse(
                   responseCode = "400", description = "Bad request: A book with the title already exists.",
           content = [
               Content(
                   mediaType = "application/json",
                   schema = Schema(implementation = IllegalArgumentException::class)
               )
           ]
   )
           , ApiResponse(
               responseCode = "404", description = "Not found: Category not found with id",
               content = [
                   Content(
                       mediaType = "application/json",
                       schema = Schema(implementation = CategoryNotFoundException::class)
                   )
               ])
       ]
   )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody @Validated book: BookCreateRequestDTO): BookResponseDTO {
        return bookService.addBook(book);
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Book response",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = BookResponseDTO::class)
                    )
                ]
            )
            , ApiResponse(
                responseCode = "404", description = "Not found: Book not found with id",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = BookNotFoundException::class)
                    )
                ])
        ]
    )
    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    fun getBookById(@Parameter(description = "Book id") @PathVariable("bookId") bookId: UUID): BookResponseDTO {
        return bookService.getBookById(bookId)
    }

    @Operation(summary = "Get all books")
    @ApiResponse(
        responseCode = "200", description = "OK",
        content = [
            Content(
                mediaType = "application/json",
                array = ArraySchema(
                    schema = Schema(implementation = BookResponseDTO::class)
                )
            )
        ]
    )
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun getAllBooks(@Parameter(name = "title", description = "Book title", example = "Twisted lies" ) @RequestParam(required = false) title: String?, @Parameter(name = "category", description = "Category name", example = "Roman" ) @RequestParam(required = false) category: String?, @Parameter(name = "spice", description = "How spicy this book is", example = "3" ) @RequestParam(required = false)  @Min(0) @Max(5) spice: Int?): List<BookResponseDTO> {
        return bookService.getAllBooks(title, category, spice)
    }

    @Operation(summary = "Delete a book by its id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204", description = "No Content",
            ),
            ApiResponse(
                responseCode = "404", description = "Not found: Book not found with id",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = BookNotFoundException::class)
                    )
                ])
        ]
    )
    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@Parameter(description = "Book id") @PathVariable("bookId") bookId: UUID) =
        bookService.deleteBook(bookId)

    @Operation(summary = "Delete all books")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllBooks() {
        return bookService.deleteBooks();
    }

    @Operation(summary = "Update a specific book")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Book response",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = BookResponseDTO::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400", description = "Bad request: A book with the title already exists.",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = IllegalArgumentException::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404", description = "Not found: Book not found with id",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = BookNotFoundException::class)
                    )
                ])
        ]
    )
    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBook(@Parameter(description = "Book id") @PathVariable("bookId") bookId: UUID, @RequestBody @Validated book: BookCreateRequestDTO): BookResponseDTO {
        return bookService.updateBook(bookId, book);
    }
}