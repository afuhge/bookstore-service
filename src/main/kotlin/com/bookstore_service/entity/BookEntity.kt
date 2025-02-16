package com.bookstore_service.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.util.UUID

@Entity
@Table(name = "books")
data class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(unique = true)
    var title: String,
    @ManyToOne (fetch = FetchType.LAZY) // fetch stuff nur wenn benötigt
    @JoinColumn(name = "category_id", nullable = true) // category kann leer sein
    var category: CategoryEntity? = null,
    var pages: Int?,
    @Min(0)
    @Max(5)
    var spice: Int?,
)
