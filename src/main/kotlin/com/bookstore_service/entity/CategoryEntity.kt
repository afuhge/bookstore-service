package com.bookstore_service.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "categories")
data class CategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    var name: String,
    @OneToMany( mappedBy = "category",)
    val books: MutableList<BookEntity>,
)
