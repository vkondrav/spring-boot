package com.library.spring.demo.data

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val email: String? = null,
    @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    val books: List<Book> = emptyList()
)