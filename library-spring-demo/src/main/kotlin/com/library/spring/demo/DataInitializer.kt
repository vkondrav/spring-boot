package com.library.spring.demo

import com.library.spring.demo.data.Book
import com.library.spring.demo.data.User
import com.library.spring.demo.repository.BookRepository
import com.library.spring.demo.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        userRepository.saveAll(
            generateUsers(
                bookRepository.saveAll(generateBooks(1_000))
            )
        )

    }

    private fun generateUsers(books: List<Book>): List<User> {
        val firstNames = listOf("John", "Jane", "Alex", "Emily", "Chris", "Katie", "Michael", "Sarah", "David", "Laura")
        val lastNames = listOf(
            "Smith",
            "Johnson",
            "Williams",
            "Brown",
            "Jones",
            "Garcia",
            "Miller",
            "Davis",
            "Rodriguez",
            "Martinez"
        )
        val emailProviders = listOf(
            "example.com",
            "mail.com",
            "test.com",
            "demo.com",
            "sample.com",
            "email.com",
            "web.com",
            "site.com",
            "net.com",
            "org.com"
        )

        return buildList {

            val booksIterator = books.iterator()

            repeat(books.size) {
                val firstName = firstNames.random()
                val lastName = lastNames.random()
                val email = "$firstName.$lastName@${emailProviders.random()}".lowercase()

                User(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    books = if (booksIterator.hasNext()) listOf(booksIterator.next()) else emptyList(),
                ).run { add(this) }
            }
        }
    }

    private fun generateBooks(n: Int): List<Book> {
        val titles = listOf(
            "The Great Adventure", "Mystery of the Old House", "Journey to the Unknown", "Secrets of the Past",
            "The Lost Treasure", "Tales of the Future", "Legends of the Forest", "The Hidden Truth",
            "The Last Battle", "The Forgotten Realm", "The Enchanted Garden", "The Dark Knight",
            "The Silent Whisper", "The Golden Key", "The Silver Sword", "The Crystal Cave",
            "The Phantom Ship", "The Haunted Castle", "The Secret Island", "The Magic Ring"
        )

        val authors = listOf(
            "James Patterson", "J.K. Rowling", "Stephen King", "John Grisham", "Agatha Christie",
            "George R.R. Martin", "J.R.R. Tolkien", "Dan Brown", "Ernest Hemingway", "Mark Twain"
        )

        return buildList {
            repeat(n) {
                Book(
                    title = titles.random(),
                    author = authors.random()
                ).run {
                    add(this)
                }
            }
        }
    }
}