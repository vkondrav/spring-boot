package com.library.spring.demo.repository

import com.library.spring.demo.data.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.books b WHERE b.id = :bookId")
    fun findUsersByBookId(bookId: Long): List<User>
}