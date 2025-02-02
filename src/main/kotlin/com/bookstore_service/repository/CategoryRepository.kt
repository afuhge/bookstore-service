package com.bookstore_service.repository

import com.bookstore_service.entity.CategoryEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface CategoryRepository : CrudRepository<CategoryEntity, UUID> {
    fun existsByNameAndIdNot(name: String, id: UUID): Boolean
    fun existsByName(name: String): Boolean
}