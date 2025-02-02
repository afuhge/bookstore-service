package com.bookstore_service.controller

import com.bookstore_service.dto.CategoryCreateRequestDTO
import com.bookstore_service.dto.CategoryResponseDTO
import com.bookstore_service.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController (val categoryService: CategoryService){
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated @RequestBody category: CategoryCreateRequestDTO): CategoryResponseDTO {
        return categoryService.addCategory(category);
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun getCategory(@PathVariable categoryId: UUID): CategoryResponseDTO {
      return categoryService.getCategory(categoryId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllCategories(): List<CategoryResponseDTO> {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory(@PathVariable categoryId: UUID) {
        categoryService.deleteCategory(categoryId);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory() {
        categoryService.deleteAllCategories();
    }

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCategory(@PathVariable categoryId: UUID, @Validated @RequestBody updateRequest: CategoryCreateRequestDTO): CategoryResponseDTO {
        return categoryService.updateCategory(categoryId, updateRequest);
    }
}