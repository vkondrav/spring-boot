package com.library.spring.demo.controller

import com.library.spring.demo.API_VERSION
import com.library.spring.demo.data.Book
import com.library.spring.demo.data.User
import com.library.spring.demo.repository.BookRepository
import com.library.spring.demo.repository.UserRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.HttpURLConnection.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping(API_VERSION)
class LibraryRestController(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
) {

    @Operation(summary = "Get list of users")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = HTTP_OK.toString(),
                description = "returns all users",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Array<User>::class)
                )]
            )
        ]
    )
    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<User>> =
        ResponseEntity(userRepository.findAll(), HttpStatus.OK)

    @Operation(summary = "Add a new user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = HTTP_CREATED.toString(),
                description = "User created",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = User::class)
                )]
            )
        ]
    )
    @PostMapping("/users")
    fun addUser(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam email: String?
    ): ResponseEntity<User> {
        val user = User(firstName = firstName, lastName = lastName, email = email)
        userRepository.save(user)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @Operation(summary = "Delete a user by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = HTTP_OK.toString(),
                description = "User deleted",
                content = [Content(
                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                )]
            ),
        ]
    )
    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> = try {
        userRepository.deleteById(id)
        ResponseEntity(HttpStatus.OK)
    } catch (e: Exception) {
        ResponseEntity("User not found", HttpStatus.NOT_FOUND)
    }

    @Operation(summary = "Get a user by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = HTTP_OK.toString(),
                description = "User found",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = User::class)
                )]
            ),
            ApiResponse(
                responseCode = HTTP_NOT_FOUND.toString(),
                description = "User not found",
                content = [Content(
                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                )]
            )
        ]
    )
    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> =
        when (val user = userRepository.findById(id).getOrNull()) {
            null -> ResponseEntity(HttpStatus.NOT_FOUND)
            else -> ResponseEntity(user, HttpStatus.OK)
        }

    @Operation(summary = "Get list of books")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = HTTP_OK.toString(),
                description = "Returns all books",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Array<Book>::class)
                )]
            )
        ]
    )
    @GetMapping("/books")
    fun getAllBooks(): ResponseEntity<List<Book>> = ResponseEntity(bookRepository.findAll(), HttpStatus.OK)

    @Operation(summary = "Get users by book ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = HTTP_OK.toString(),
                description = "Returns users with the specified book",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Array<User>::class)
                )]
            )
        ]
    )
    @GetMapping("/users/book/{bookId}")
    fun getUsersByBookId(@PathVariable bookId: Long): ResponseEntity<List<User>> =
        ResponseEntity(
            userRepository.findUsersByBookId(bookId),
            HttpStatus.OK
        )
}

