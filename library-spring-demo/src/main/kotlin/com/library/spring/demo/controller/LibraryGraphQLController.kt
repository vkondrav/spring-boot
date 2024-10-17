package com.library.spring.demo.controller

import com.library.spring.demo.data.Book
import com.library.spring.demo.data.User
import com.library.spring.demo.repository.BookRepository
import com.library.spring.demo.repository.UserRepository
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Controller

@Controller
@GraphQLApi
class LibraryGraphQLController(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
) {

    @GraphQLQuery(name = "users", description = "Get list of users")
    fun users(): List<User> = userRepository.findAll()

    @GraphQLMutation(name = "addUser", description = "Add a new user")
    fun addUser(
        firstName: String,
        lastName: String,
        email: String?
    ): User {
        val user = User(firstName = firstName, lastName = lastName, email = email)
        userRepository.save(user)
        return user
    }

    @GraphQLMutation(name = "deleteUser", description = "Delete a user")
    fun deleteUser(id: Long): Boolean {
        userRepository.deleteById(id)
        return true
    }

    @GraphQLQuery(name = "userById", description = "Get user by id")
    fun getUserById(id: Long): User? = userRepository
        .findById(id)
        .orElse(null)

    @GraphQLQuery(name = "books", description = "Get list of books")
    fun books(): List<Book> = bookRepository.findAll()

    @GraphQLQuery(name = "usersByBookId", description = "Get all users by book id")
    fun usersByBookId(bookId: Long): List<User> = userRepository
        .findUsersByBookId(bookId)
}