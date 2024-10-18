package com.micro.cards.entities

import jakarta.persistence.*
import org.springframework.stereotype.Component
import kotlin.random.Random

@Entity(name = "cards")
data class Card(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cardId: Long = 0L,
    @Column(name = "mobile_number")
    val mobileNumber: String,
    @Column(name = "card_number")
    val cardNumber: String,
    @Column(name = "card_type")
    val cardType: String,
    @Column(name = "total_limit")
    val totalLimit: Int,
    @Column(name = "amount_used")
    val amountUsed: Int,
    @Column(name = "available_amount")
    val availableAmount: Int,
) : BaseEntity() {

    @Component
    class Builder {
        operator fun invoke(mobileNumber: String) = Card(
            mobileNumber = mobileNumber,
            cardNumber = generateRandomCardNumber(),
            cardType = "VISA",
            totalLimit = 10_000,
            amountUsed = 0,
            availableAmount = 10_000
        )

        private fun generateRandomCardNumber(): String {
            val random = Random.Default
            return (1..4)
                .joinToString(" ") {
                    (1..4)
                        .map { random.nextInt(0, 10) }
                        .joinToString("")
                }
        }
    }
}