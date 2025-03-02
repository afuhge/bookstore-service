package com.bookstore_service.repository

import com.bookstore_service.entity.BookEntity
import com.bookstore_service.entity.CategoryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BookRepository : CrudRepository<BookEntity, UUID> {
    fun existsByTitleAndIdNot(title: String, id: UUID): Boolean
    fun existsByTitle(title: String): Boolean
    fun findAllByCategoryId(categoryId: UUID): MutableList<BookEntity>
    fun findByTitleContainingIgnoreCaseAndCategoryNameContainingIgnoreCaseAndSpiceGreaterThanEqual(
        title: String,
        category: String,
        spice: Int,
        pageable: Pageable
    ): Page<BookEntity>
}