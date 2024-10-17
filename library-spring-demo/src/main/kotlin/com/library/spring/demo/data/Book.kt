package com.library.spring.demo.data

import jakarta.persistence.*

@Entity
@Table(name = "books")
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val title: String = "",
    val author: String = ""
)