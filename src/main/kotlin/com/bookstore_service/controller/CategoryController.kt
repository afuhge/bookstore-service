package com.bookstore_service.controller

import com.bookstore_service.dto.BookResponseDTO
import com.bookstore_service.dto.CategoryCreateRequestDTO
import com.bookstore_service.dto.CategoryResponseDTO
import com.bookstore_service.exceptions.CategoryNotFoundException
import com.bookstore_service.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category Management", description = "Operations related to categories")
class CategoryController (val categoryService: CategoryService){

    @Operation(summary = "Create a new category")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201", description = "Created category",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryResponseDTO::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400", description = "Bad request: A category with the name already exists.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = IllegalArgumentException::class)
                )
            ]
        )
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated @RequestBody category: CategoryCreateRequestDTO): CategoryResponseDTO {
        return categoryService.addCategory(category);
    }

    @Operation(summary = "Get a category by its id")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Category response",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryResponseDTO::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404", description = "Not found: No category found with id",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryNotFoundException::class)
                )
            ]
        )
    ])
    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun getCategory(@Parameter(description = "Category id") @PathVariable categoryId: UUID): CategoryResponseDTO {
      return categoryService.getCategory(categoryId);
    }

    @Operation(summary = "Get all categories")
    @ApiResponse(
        responseCode = "200", description = "OK",
        content = [
            Content(
                mediaType = "application/json",
                array = ArraySchema(
                    schema = Schema(implementation = CategoryResponseDTO::class)
                )
            )
        ]
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllCategories(): List<CategoryResponseDTO> {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Delete a category by its id")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "204", description = "No Content",
        ),
        ApiResponse(
            responseCode = "404", description = "Not found: No category found with id",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryNotFoundException::class)
                )
            ]
        )
    ])
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory(@Parameter(description = "Category id") @PathVariable categoryId: UUID) {
        categoryService.deleteCategory(categoryId);
    }

    @Operation(summary = "Delete all categories")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory() {
        categoryService.deleteAllCategories();
    }

    @Operation(summary = "Update a specific category")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Category response",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryResponseDTO::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400", description = "Bad request: A category with the name already exists.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = IllegalArgumentException::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404", description = "Not found: No category found with id",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryNotFoundException::class)
                )
            ]
        )
    ])
    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCategory(@Parameter(description = "Category id") @PathVariable categoryId: UUID, @Validated @RequestBody updateRequest: CategoryCreateRequestDTO): CategoryResponseDTO {
        return categoryService.updateCategory(categoryId, updateRequest);
    }
}