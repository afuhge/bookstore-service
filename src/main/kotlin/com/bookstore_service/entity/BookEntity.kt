package com.bookstore_service.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "books")
data class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    var title: String,
    @ManyToOne (fetch = FetchType.LAZY) // fetch stuff nur wenn benötigt
    @JoinColumn(name = "category_id", nullable = true) // category kann leer sein
    var category: CategoryEntity? = null,
    var pages: Int,
    var spice: Int,
)
