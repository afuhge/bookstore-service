package com.bookstore_service.service

import com.bookstore_service.dto.BookResponseDTO
import com.bookstore_service.dto.CategoryCreateRequestDTO
import com.bookstore_service.dto.CategoryResponseDTO
import com.bookstore_service.entity.BookEntity
import com.bookstore_service.entity.CategoryEntity
import com.bookstore_service.exceptions.CategoryNotFoundException
import com.bookstore_service.repository.BookRepository
import com.bookstore_service.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class CategoryService (val categoryRepository : CategoryRepository, val bookRepository: BookRepository) {
    fun addCategory(category: CategoryCreateRequestDTO): CategoryResponseDTO {
        // Check if the title already exists in the database for a different book
        if (categoryRepository.existsByName(category.name)) {
            throw IllegalArgumentException("A category with the name '${category.name}' already exists.")
        }

        val categoryEntity = CategoryEntity(name = category.name)

        categoryRepository.save(categoryEntity);

        return categoryEntity.let{ CategoryResponseDTO(it.id!!, it.name) }
    }

    fun getCategory(id: UUID): CategoryResponseDTO {
        val category = categoryRepository.findById(id)
            .orElseThrow { CategoryNotFoundException("No category found with id $id") }
        val books = bookRepository.findAllByCategoryId(category.id!!)
        return category.let { CategoryResponseDTO(it.id!!, it.name,  books.map{ book -> book.toBookResponseDTO() }.toMutableList()) }
    }

    fun getAllCategories(): List<CategoryResponseDTO> {
        return categoryRepository.findAll().map { category ->
            val books = category.books.map { book -> book.toBookResponseDTO() }
            CategoryResponseDTO(category.id!!, category.name, books.toMutableList()) }
    }

    fun deleteCategory(id: UUID) {
        val category = categoryRepository.findById(id)
            .orElseThrow { CategoryNotFoundException("Category with id $id not found.") }

        // Unlink the books from the category by setting category to null
        category.books.forEach { book ->
            book.category = null
            bookRepository.save(book)
        }

        categoryRepository.delete(category)
    }

    fun deleteAllCategories() {
        val categories = categoryRepository.findAll()

        categories.forEach { category ->
            // Unlink the books from the category by setting category to null
            category.books.forEach { book ->
                book.category = null
                bookRepository.save(book)
            }

            categoryRepository.delete(category)
        }
    }

   fun updateCategory(categoryId: UUID, updateRequest: CategoryCreateRequestDTO): CategoryResponseDTO {
        // Check if the title already exists in the database for a different book
        if (categoryRepository.existsByName(updateRequest.name)) {
            throw IllegalArgumentException("A category with the name '${updateRequest.name}' already exists.")
        }

       val category = categoryRepository.findById(categoryId)
           .orElseThrow { CategoryNotFoundException("No category found with id $categoryId") }

       category.apply { name = updateRequest.name }
       val categoryEntity = categoryRepository.save(category)

       return CategoryResponseDTO(categoryEntity.id!!, categoryEntity.name, categoryEntity.books.map{ book -> book.toBookResponseDTO() }.toMutableList());
    }

    private fun BookEntity.toBookResponseDTO(): BookResponseDTO {
        return BookResponseDTO(this.id!!, this.title, null, this.pages, this.spice)
    }
}