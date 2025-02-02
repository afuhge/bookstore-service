package com.bookstore_service.repository

import com.bookstore_service.entity.BookEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface BookRepository : CrudRepository<BookEntity, UUID> {
    fun existsByTitleAndIdNot(title: String, id: UUID): Boolean
    fun existsByTitle(title: String): Boolean
    fun findAllByCategoryId(categoryId: UUID): MutableList<BookEntity>
}