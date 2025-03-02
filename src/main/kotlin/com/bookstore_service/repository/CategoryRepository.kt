package com.bookstore_service.repository

import com.bookstore_service.entity.CategoryEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CategoryRepository : CrudRepository<CategoryEntity, UUID> {
    fun existsByName(name: String): Boolean
    fun findAll(pageable: Pageable): Page<CategoryEntity>
}